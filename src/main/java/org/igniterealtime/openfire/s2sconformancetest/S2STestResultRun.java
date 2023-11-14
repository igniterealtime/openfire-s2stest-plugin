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

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a Conformance Test run.
 *
 * @author Dan Caseley
 */
public class S2STestResultRun {
    private List<S2STestResult> successfulResults;
    private List<S2STestResult> unsuccessfulResults;

    public S2STestResultRun() {
        successfulResults = new ArrayList<>();
        unsuccessfulResults = new ArrayList<>();
    }

    public void addSuccessfulResult(S2STestResult result) {
        successfulResults.add(result);
    }

    public void addUnsuccessfulResult(S2STestResult result) {
        unsuccessfulResults.add(result);
    }

    public List<S2STestResult> getSuccessfulResults() {
        return successfulResults;
    }

    public List<S2STestResult> getUnsuccessfulResults() {
        return unsuccessfulResults;
    }
}
