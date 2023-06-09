package org.igniterealtime.openfire.s2stest;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class S2STestPlugin implements Plugin {

    private static final Logger Log = LoggerFactory.getLogger( S2STestPlugin.class );
    public void initializePlugin(PluginManager pluginManager, File file) {
        try {
            S2STestRunner.runTests();
        } catch (Exception e) {
            Log.error("Got an exception running the tests", e);
        }
    }

    public void destroyPlugin() {

    }
}
