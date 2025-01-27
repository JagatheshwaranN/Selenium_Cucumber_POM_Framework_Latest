package com.qa.ctf.constants;

public enum EnvironmentType {

    TEST_ENV("TestEnv"),
    PROD("Prod"),
    STAGE("Stage");

    private final String envType;

    EnvironmentType(String envType) {
        if(envType == null || envType.isEmpty()){
            throw new IllegalArgumentException("Test Environment Type cannot not be null or empty.");
        }
        this.envType = envType;
    }

    public String getEnvType() {
        return envType;
    }

}
