package com.qa.ctf.factories;

import com.qa.ctf.constants.EnvironmentType;
import com.qa.ctf.utils.ExceptionHub;
import com.qa.ctf.utils.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.qa.ctf.constants.TestConstants.*;

/**
 * The EnvironmentFactory class manages the environment configurations for test execution
 * by retrieving and validating the environment type (e.g., STAGE or PROD) based on
 * environment variables or property files.
 *
 * <p>Features:
 * <ul>
 *     <li>Set and get the environment type for test execution.</li>
 *     <li>Retrieve the corresponding EnvType enumeration based on the environment set.</li>
 *     <li>Fetch environment configuration values from environment variables or property files.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions from the {@link ExceptionHub.ConfigTypeException} class
 *       are thrown for unrecognized or invalid environment types.</li>
 *   <li>Detailed logging is provided for environment configurations and error scenarios.</li>
 * </ul>
 *
 * <p>Note:
 * The class assumes proper setup for environment configuration and property files.
 * Users must handle environment setup and termination separately.
 *
 * <p>Example:
 * <pre>
 * {@code
 * EnvironmentFactory EnvironmentFactory = new EnvironmentFactory();
 * EnvironmentFactory.setEnv("stage");
 * EnvType envType = EnvironmentFactory.getEnvType();
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.0
 */
public class EnvironmentFactory extends FileReader {

    // Logger instance for the EnvironmentFactory class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(EnvironmentFactory.class);

    // Instance variable to store the environment configuration for the application
    private String env;

    /**
     * Sets the environment type for the test execution.
     * <p>
     * This method assigns the specified environment to the instance variable
     * `env`, which will be used later to determine the environment type for
     * test execution.
     * </p>
     *
     * @param env The environment name to set (e.g., "stage" or "prod").
     */
    public void setEnv(String env) {
        this.env = env;
    }

    /**
     * Retrieves the environment type currently set for the test execution.
     * <p>
     * This method returns the environment that has been set using the `setEnv`
     * method. It returns values like "stage" or "prod".
     * </p>
     *
     * @return The name of the currently set environment.
     */
    public String getEnv() {
        return env;
    }

    /**
     * Retrieves the EnvironmentType enumeration for the environment set for the test
     * execution.
     * <p>
     * This method fetches the environment type from either environment variables
     * or a property file, then determines the corresponding `EnvironmentType` enum (such
     * as STAGE or PROD). The environment type is logged for informational purposes.
     * </p>
     *
     * @return The corresponding `EnvironmentType` for the currently set environment.
     * @throws ExceptionHub.ConfigTypeException If the environment type is invalid or
     *                                          not recognized.
     */
    public EnvironmentType getEnvType() {
        setEnv(getValue(EnvironmentType.TEST_ENV.getEnvType())); // Set environment from value
        if (getEnv() == null || getEnv().isEmpty()) {
            log.error("Environment type is not specified or is empty.");
            throw new ExceptionHub.ConfigTypeException("Environment type is not specified.");
        }
        return switch (getEnv().toUpperCase()) {
            case STAGE -> {
                log.info("Stage environment is opted for test execution");
                yield EnvironmentType.STAGE;
            }
            case PROD -> {
                log.info("Prod environment is opted for test execution");
                yield EnvironmentType.PROD;
            }
            default -> {
                log.error("Invalid environment type: '{}'", getEnv());
                throw new ExceptionHub.ConfigTypeException("Invalid environment type: " + getEnv());
            }
        };
    }

    /**
     * Retrieves the value associated with the provided key from either environment
     * variables or a property file.
     * <p>
     * This method first checks if the key exists in the environment variables, and
     * if not, it fetches the value from the property file.
     * </p>
     *
     * @param key The key whose associated value is to be retrieved.
     * @return The value associated with the provided key.
     */
    private String getValue(String key) {
        String value = System.getProperty(key);
        if (value != null && !value.isEmpty()) {
            log.info("Environment is specified from the MVN CMD LINE ARGS.");
            return value;
        }
        value = fetchDataFromPropFile(key);
        log.info("Environment is specified from the CONFIG FILE.");
        if (value == null || value.isEmpty()) {
            log.warn("Value for key '{}' not found in environment or property file.", key);
        }
        return value;
    }

}
