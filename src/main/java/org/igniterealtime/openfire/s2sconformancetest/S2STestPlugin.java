package org.igniterealtime.openfire.s2sconformancetest;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.util.SystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

public class S2STestPlugin implements Plugin {

    private static final Logger Log = LoggerFactory.getLogger( S2STestPlugin.class );

    private static final String pluginName = "S2S Conformance Test";
    private static final String pluginPropertyPrefix = "plugin.s2sconformancetest.";

    public static final SystemProperty<String> SUCCESSFUL_DOMAINS = SystemProperty.Builder.ofType(String.class)
            .setPlugin(pluginName)
            .setKey(pluginPropertyPrefix + "successful-domains")
            .setDefaultValue("xsf.org")
            .setDynamic(true)
            .build();

    public static final SystemProperty<String> UNSUCCESSFUL_DOMAINS = SystemProperty.Builder.ofType(String.class)
            .setPlugin(pluginName)
            .setKey(pluginPropertyPrefix + "unsuccessful-domains")
            .setDefaultValue("expired.badxmpp.eu")
            .setDynamic(true)
            .build();

    public void setSuccessfulDomains(String successfulDomains) {
        SUCCESSFUL_DOMAINS.setValue(successfulDomains);
    }

    public void setSuccessfulDomains(String[] successfulDomains) {
        SUCCESSFUL_DOMAINS.setValue(String.join(",", successfulDomains));
    }

    public String getSuccessfulDomains() {
        return SUCCESSFUL_DOMAINS.getValue();
    }

    public String[] getSuccessfulDomainsAsArray() {
        return SUCCESSFUL_DOMAINS.getValue().split(",");
    }

    public void setUnsuccessfulDomains(String unsuccessfulDomains) {
        UNSUCCESSFUL_DOMAINS.setValue(unsuccessfulDomains);
    }

    public void setUnsuccessfulDomains(String[] unsuccessfulDomains) {
        UNSUCCESSFUL_DOMAINS.setValue(String.join(",", unsuccessfulDomains));
    }

    public String getUnsuccessfulDomains() {
        return UNSUCCESSFUL_DOMAINS.getValue();
    }

    public String[] getUnsuccessfulDomainsAsArray() {
        return UNSUCCESSFUL_DOMAINS.getValue().split(",");
    }

    public void initializePlugin(PluginManager pluginManager, File file) {

    }

    public void destroyPlugin() {

    }

    public void runTests() throws Exception {
        for (String domain : getSuccessfulDomainsAsArray()) {
            final Map<String, String> successfulResults = new S2STestService(domain).run();
            Log.info("Domain: [{}] Result: [{}]", domain, successfulResults.get("status"));
        }
        for (String domain : getUnsuccessfulDomainsAsArray()) {
            final Map<String, String> unsuccessfulResults = new S2STestService(domain).run();
            Log.info("Domain: [{}] Result: [{}]", domain, unsuccessfulResults.get("status"));
        }
    }
}
