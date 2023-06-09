package org.igniterealtime.openfire.s2stest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class S2STestRunner {

    private static final Logger Log = LoggerFactory.getLogger( S2STestRunner.class );

    private static final String SUCCESSFUL_DOMAIN = "xsf.org";
    private static final String EXPIRED_DOMAIN = "expired.badxmpp.eu";

    public static void runTests() throws Exception {
        final Map<String, String> successfulResults = new S2STestService(SUCCESSFUL_DOMAIN).run();
        Log.info("Domain: [{}] Result: [{}]", SUCCESSFUL_DOMAIN, successfulResults.get("status"));
    }
}
