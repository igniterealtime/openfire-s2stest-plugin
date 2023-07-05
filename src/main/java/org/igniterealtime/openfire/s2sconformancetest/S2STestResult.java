package org.igniterealtime.openfire.s2sconformancetest;

import java.util.Map;

public class S2STestResult {
    private final String domain;
    private final Map<String,String> results;
    private final String expectedResult;

    public S2STestResult(String domain, Map<String,String> results, String expectedResult) {
        this.domain = domain;
        this.results = results;
        this.expectedResult = expectedResult;
    }

    public String getDomain() {
        return domain;
    }

    public Map<String,String> getResults() {
        return results;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

}
