package com.qa.ctf.utils;

import com.qa.ctf.constants.EnvironmentType;

import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;
    private static ConfigLoader configLoader;

    public ConfigLoader() {
        String env = System.getProperty("env", String.valueOf(EnvironmentType.STAGE));
        switch (EnvironmentType.valueOf(env)){
            case PROD -> properties = PropertyUtil.propertyLoader("src/test/resources/prod_config.properties");
            case STAGE -> properties = PropertyUtil.propertyLoader("src/test/resources/stage_config.properties");
            default -> throw new IllegalArgumentException("INVALID ENV:" + env);
        }
    }

    public static ConfigLoader getInstance() {
        if(configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getBaseUrl() {
        String prop = properties.getProperty("baseURL");
        if(prop != null) return prop;
        else throw new RuntimeException("Property - baseURL is not specified in the config.properties file");
    }

}
