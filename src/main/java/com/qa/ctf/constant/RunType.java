package com.qa.ctf.constant;

public enum RunType {

    TEST_RUN("RunType"),
    LOCAL("Local"),
    REMOTE("Remote");

    private final String runType;

    RunType(String runType) {
        if(runType == null || runType.isEmpty()){
            throw new IllegalArgumentException("Run Type cannot not be null or empty.");
        }
        this.runType = runType;
    }

    public String getRunType() {
        return runType;
    }

}
