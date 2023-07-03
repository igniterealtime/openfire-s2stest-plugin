package org.igniterealtime.openfire.s2sconformancetest;

import java.util.ArrayList;
import java.util.Map;

public class S2STestResultRun {
    private final ArrayList<S2STestResult> successfulResults;
    private final ArrayList<S2STestResult> unsuccessfulResults;

    public S2STestResultRun() {
        successfulResults = new ArrayList<S2STestResult>();
        unsuccessfulResults = new ArrayList<S2STestResult>();
    }

    public void addSuccessfulResult(S2STestResult result) {
        successfulResults.add(result);
    }

    public void addUnsuccessfulResult(S2STestResult result) {
        unsuccessfulResults.add(result);
    }

    public ArrayList<S2STestResult> getSuccessfulResults() {
        return successfulResults;
    }

    public ArrayList<S2STestResult> getUnsuccessfulResults() {
        return unsuccessfulResults;
    }
}
