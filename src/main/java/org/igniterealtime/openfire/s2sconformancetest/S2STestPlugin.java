package org.igniterealtime.openfire.s2sconformancetest;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.util.SystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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

    public S2STestResultRun runTests() throws Exception {
        S2STestResultRun thisRun = new S2STestResultRun();
        ArrayList<CompletableFuture<S2STestResult>> results = new ArrayList<>();

        for (String domain : getSuccessfulDomainsAsArray()) {
            results.add(makeFuture(domain, "success"));
        }
        for (String domain : getUnsuccessfulDomainsAsArray()) {
            results.add(makeFuture(domain, "failure"));
        }

        CompletableFuture.allOf(results.toArray(new CompletableFuture[0])).join();
        for (CompletableFuture<S2STestResult> result : results) {
            if(result.get().getExpectedResult().equals("success")) {
                thisRun.addSuccessfulResult(result.get());
            } else {
                thisRun.addUnsuccessfulResult(result.get());
            }
        }
        return thisRun;
    }

    private CompletableFuture<S2STestResult> makeFuture(String domain, String expectedResult){
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new S2STestService(domain).run();
            } catch (Exception e) {
                Log.error("Error running S2S test for domain [{}]", domain, e);
                return null;
            }
        }).thenApply(result -> {
            Log.info("S2S Test Result - Domain: [{}] Result: [{}]", domain, result.get("status"));
            return new S2STestResult(domain, result, expectedResult);
        });
    }

}
