package org.igniterealtime.openfire.s2stest;

import org.jivesoftware.util.SystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class S2STestRunner {

    private static final Logger Log = LoggerFactory.getLogger( S2STestRunner.class );

    public static final SystemProperty<String> SUCCESSFUL_DOMAINS = SystemProperty.Builder.ofType(String.class)
            .setPlugin("S2S Test")
            .setKey("plugin.s2stest.successful-domains")
            .setDefaultValue("xsf.org")
            .setDynamic(false)
            .build();

    public static final SystemProperty<String> UNSUCCESSFUL_DOMAINS = SystemProperty.Builder.ofType(String.class)
            .setPlugin("S2S Test")
            .setKey("plugin.s2stest.unsuccessful-domains")
            .setDefaultValue("expired.badxmpp.eu")
            .setDynamic(false)
            .build();

    public static void runTests() throws Exception {
        final String[] successfulDomains = SUCCESSFUL_DOMAINS.getValue().split(",");
        final String[] unsuccessfulDomains = UNSUCCESSFUL_DOMAINS.getValue().split(",");
        for (String domain : successfulDomains) {
            final Map<String, String> successfulResults = new S2STestService(domain).run();
            Log.info("Domain: [{}] Result: [{}]", domain, successfulResults.get("status"));
        }
        for (String domain : unsuccessfulDomains) {
            final Map<String, String> unsuccessfulResults = new S2STestService(domain).run();
            Log.info("Domain: [{}] Result: [{}]", domain, unsuccessfulResults.get("status"));
        }
    }
}
