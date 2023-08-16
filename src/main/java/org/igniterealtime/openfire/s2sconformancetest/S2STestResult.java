package org.igniterealtime.openfire.s2sconformancetest;

import java.util.Map;

public class S2STestResult {
    private final String domain;
    private final Map<String,String> results;

    public S2STestResult(String domain, Map<String,String> results) {
        this.domain = domain;
        this.results = results;
    }

    public String getDomain() {
        return domain;
    }

    public Map<String,String> getResults() {
        return results;
    }

}
