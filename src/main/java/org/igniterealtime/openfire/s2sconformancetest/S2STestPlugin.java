/*
 * Copyright (C) 2023 Ignite Realtime Foundation. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.igniterealtime.openfire.s2sconformancetest;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.util.SystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Plugin implementation for S2S Conformance Test.
 *
 * @author Dan Caseley
 */
public class S2STestPlugin implements Plugin {

    private static final Logger Log = LoggerFactory.getLogger( S2STestPlugin.class );

    private static final String pluginName = "S2S Conformance Test";
    private static final String pluginPropertyPrefix = "plugin.s2sconformancetest.";

    public static final SystemProperty<List<String>> SUCCESSFUL_DOMAINS = SystemProperty.Builder.ofType(List.class)
            .setPlugin(pluginName)
            .setKey(pluginPropertyPrefix + "successful-domains")
            .setDefaultValue(List.of("xmpp.org"))
            .setDynamic(true)
            .buildList(String.class);

    public static final SystemProperty<List<String>> UNSUCCESSFUL_DOMAINS = SystemProperty.Builder.ofType(List.class)
            .setPlugin(pluginName)
            .setKey(pluginPropertyPrefix + "unsuccessful-domains")
            .setDefaultValue(List.of("expired.badxmpp.eu"))
            .setDynamic(true)
          .buildList(String.class);

    public void initializePlugin(PluginManager pluginManager, File file) {

    }

    public void destroyPlugin() {

    }

    public S2STestResultRun runTests() throws Exception {
        S2STestResultRun thisRun = new S2STestResultRun();
        for (String domain : SUCCESSFUL_DOMAINS.getValue()) {
            final Map<String,String> thisResult = new S2STestService(domain).run();
            Log.info("S2S Test Result - Domain: [{}] Result: [{}]", domain, thisResult.get("status"));
            thisRun.addSuccessfulResult(new S2STestResult(domain, thisResult));
        }
        for (String domain : UNSUCCESSFUL_DOMAINS.getValue()) {
            final Map<String,String> thisResult = new S2STestService(domain).run();
            Log.info("S2S Test Result - Domain: [{}] Result: [{}]", domain, thisResult.get("status"));
            thisRun.addUnsuccessfulResult(new S2STestResult(domain, thisResult));
        }
        return thisRun;
    }
}
