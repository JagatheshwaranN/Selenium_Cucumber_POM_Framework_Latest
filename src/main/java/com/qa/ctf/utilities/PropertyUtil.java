package com.qa.ctf.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {

    public static Properties propertyLoader(String filePath) {
        Properties properties = new Properties();
        BufferedReader bufferedReader;
        try{
            bufferedReader = new BufferedReader(new FileReader(filePath));
            try{
                properties.load(bufferedReader);
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException("Failed to load the prop file "+filePath);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("The prop file is not found at "+filePath);
        }
        return properties;
    }

}
