package com.qa.ctf.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.Properties;

import com.qa.ctf.constant.TestConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.qa.ctf.constant.TestConstants.*;

/**
 * The FileReader class provides utility methods for reading and managing properties
 * from a configuration file in a Java application.
 *
 * <p>Features:
 * <ul>
 *     <li>Load a properties file from a specified path into a {@code Properties}
 *      object.</li>
 *     <li>Retrieve configuration values associated with specific keys from the
 *      properties file.</li>
 *     <li>Handle exceptions related to file reading and key retrieval with custom
 *      exception handling.</li>
 *     <li>Support logging for successful file loading and error scenarios using
 *      Apache Log4j.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions, such as {@link ExceptionHub.ConfigTypeException} and
 *       {@link ExceptionHub.InvalidDataException}, are thrown for missing files or
 *       missing keys with detailed error information.</li>
 *   <li>Errors during file reading and key retrieval are logged with error messages
 *      for troubleshooting.</li>
 * </ul>
 *
 * <p>Note:
 * This class relies on the {@link TestConstants} class for file path and configuration
 * details. Ensure that the correct configuration file path is provided and that the
 * Apache Log4j library is properly set up for logging.
 *
 * <p>Example:
 * <pre>
 * {@code
 * FileReader.loadPropertyFile(); // Load properties file
 * String apiUrl = FileReader.getDataFromPropFile("api.url"); // Get value for key 'api.url'
 * System.out.println(apiUrl);
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.1
 */
public class FileReader {

    // Logger instance for the FileReader class to enable logging during execution
    private static final Logger log = LogManager.getLogger(FileReader.class);

    // Properties object to store key-value pairs from a properties file
    public static Properties properties = new Properties();

    /**
     * Loads the configuration property file into the {@code properties} object.
     * <p>
     * This method attempts to read the configuration file from the specified path and
     * load its key-value pairs into the {@code properties} object. If the file is not
     * found or an error occurs during the loading process, it throws a custom exception
     * with relevant details.
     * </p>
     *
     * @throws ExceptionHub.ConfigTypeException If the file is not found or an error
     *                                          occurs while loading it.
     */
    public static void loadPropertyFile() {
        String envType = System.getProperty(ENV_TYPE, STAGE).toLowerCase();
        String updatedFilePath = CONFIG_FILE_PATH.replace("$", envType);
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(CWD + updatedFilePath);
            properties.load(fileInputStream);
            log.info("The configuration file is loaded!!");
        } catch (FileNotFoundException ex) {
            log.error("The configuration file not found on the given path: '{}'", CONFIG_FILE_PATH, ex);
            throw new ExceptionHub.ConfigTypeException(CONFIG_FILE_PATH, ex);
        } catch (IOException ex) {
            log.error("Error occurred while loading the configuration file", ex);
            throw new ExceptionHub.ConfigTypeException("Error occurred while loading configuration file", ex);
        }
    }

    /**
     * Retrieves the value associated with the specified key from the property file.
     * <p>
     * This method fetches the value corresponding to the given key from the loaded configuration
     * property file. If the key or its value is not present, it logs an error and throws a custom
     * exception. The fetched value is stripped of leading and trailing spaces before being returned.
     * </p>
     *
     * @param key The key to search for in the property file.
     * @return The stripped value associated with the key, or {@code null} if the key is not provided.
     * @throws ExceptionHub.InvalidDataException If the key is not found in the property file.
     */
    public static String fetchDataFromPropFile(String key) {
        String data = null;
        if (key != null) {
            data = properties.getProperty(key);
            if (data != null) {
                data = data.strip();
                log.info("The '{}' data fetched from the configuration file", data);
            } else {
                log.error("The key '{}' is not present in the configuration file", key);
                throw new ExceptionHub.InvalidDataException(key, new NullPointerException());
            }
        }
        return data;
    }

}


