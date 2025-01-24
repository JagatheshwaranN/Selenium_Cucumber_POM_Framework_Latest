package com.qa.ctf.utilities;

import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;
    private static ConfigLoader configLoader;

    public ConfigLoader() {
        properties = PropertyUtil.propertyLoader("src/test/resources/config.properties");
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
