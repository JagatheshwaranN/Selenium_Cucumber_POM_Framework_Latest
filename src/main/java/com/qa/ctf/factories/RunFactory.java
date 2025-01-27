package com.qa.ctf.factories;

import com.qa.ctf.constants.RunType;
import com.qa.ctf.utils.ExceptionHub;
import com.qa.ctf.utils.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.qa.ctf.constants.TestConstants.*;

/**
 * The EnvironmentManager class manages the environment configurations for test execution
 * by retrieving and validating the environment type (e.g., LOCAL or REMOTE) based on
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
 * EnvironmentManager environmentManager = new EnvironmentManager();
 * environmentManager.setEnv("local");
 * EnvType envType = environmentManager.getEnvType();
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.0
 */
public class RunFactory extends FileReader {

    // Logger instance for the EnvironmentManager class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(RunType.class);

    // Instance variable to store the environment configuration for the application
    private String runType;

    /**
     * Sets the run environment type for the test execution.
     * <p>
     * This method assigns the specified run environment to the instance variable
     * `runType`, which will be used later to determine the run environment type for
     * test execution.
     * </p>
     *
     * @param runType The run environment name to set (e.g., "local" or "remote").
     */
    public void setRunType(String runType) {
        this.runType = runType;
    }

    /**
     * Retrieves the run environment type currently set for the test execution.
     * <p>
     * This method returns the run environment that has been set using the `setRunType`
     * method. It returns values like "local" or "remote".
     * </p>
     *
     * @return The name of the currently set run environment.
     */
    public String getRunType() {
        return runType;
    }

    /**
     * Retrieves the RunType enumeration for the run environment set for the test
     * execution.
     * <p>
     * This method fetches the run environment type from either environment variables
     * or a property file, then determines the corresponding `RunType` enum (such
     * as LOCAL or REMOTE). The run environment type is logged for informational purposes.
     * </p>
     *
     * @return The corresponding `RunType` for the currently set run environment.
     * @throws ExceptionHub.ConfigTypeException If the run environment type is invalid or
     *                                          not recognized.
     */
    public RunType getTestRunType() {
        setRunType(getValue(RunType.TEST_RUN.getRunType())); // Set environment from value
        if (getRunType() == null || getRunType().isEmpty()) {
            log.error("Test Run Type is not specified or is empty.");
            throw new ExceptionHub.ConfigTypeException("Test Run Type is not specified.");
        }
        return switch (getRunType().toUpperCase()) {
            case LOCAL -> {
                log.info("LOCAL Run is opted for test execution");
                yield RunType.LOCAL;
            }
            case REMOTE -> {
                log.info("REMOTE Run is opted for test execution");
                yield RunType.REMOTE;
            }
            default -> {
                log.error("Invalid test run type: '{}'", getRunType());
                throw new ExceptionHub.ConfigTypeException("Invalid test run type: " + getRunType());
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
            log.info("Environment is specified from mvn cmd line argument.");
            return value;
        }
        value = fetchDataFromPropFile(key);
        log.info("Environment is specified from config file.");
        if (value == null || value.isEmpty()) {
            log.warn("Value for key '{}' not found in environment or property file.", key);
        }
        return value;
    }

}
