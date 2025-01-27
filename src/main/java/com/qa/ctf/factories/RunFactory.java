package com.qa.ctf.factories;

import com.qa.ctf.constants.RunType;
import com.qa.ctf.utils.ExceptionHub;
import com.qa.ctf.utils.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.qa.ctf.constants.TestConstants.*;

/**
 * The RunFactory class manages the run configurations for test execution
 * by retrieving and validating the run type (e.g., LOCAL or REMOTE) based on
 * environment variables or property files.
 *
 * <p>Features:
 * <ul>
 *     <li>Set and get the run type for test execution.</li>
 *     <li>Retrieve the corresponding RunType enumeration based on the run set.</li>
 *     <li>Fetch run configuration values from environment variables or property files.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions from the {@link ExceptionHub.ConfigTypeException} class
 *       are thrown for unrecognized or invalid run types.</li>
 *   <li>Detailed logging is provided for run configurations and error scenarios.</li>
 * </ul>
 *
 * <p>Note:
 * The class assumes proper setup for run configuration and property files.
 * Users must handle run setup and termination separately.
 *
 * <p>Example:
 * <pre>
 * {@code
 * RunFactory RunFactory = new RunFactory();
 * RunFactory.setRunType("local");
 * RunType runType = RunFactory.getRunType();
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.0
 */
public class RunFactory extends FileReader {

    // Logger instance for the RunFactory class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(RunType.class);

    // Instance variable to store the run configuration for the application
    private String runType;

    /**
     * Sets the run type for the test execution.
     * <p>
     * This method assigns the specified run type to the instance variable
     * `runType`, which will be used later to determine the run type for
     * test execution.
     * </p>
     *
     * @param runType The run type name to set (e.g., "local" or "remote").
     */
    public void setRunType(String runType) {
        this.runType = runType;
    }

    /**
     * Retrieves the run type currently set for the test execution.
     * <p>
     * This method returns the run type that has been set using the `setRunType`
     * method. It returns values like "local" or "remote".
     * </p>
     *
     * @return The name of the currently set run type.
     */
    public String getRunType() {
        return runType;
    }

    /**
     * Retrieves the RunType enumeration for the run type set for the test
     * execution.
     * <p>
     * This method fetches the run type from either environment variables
     * or a property file, then determines the corresponding `RunType` enum (such
     * as LOCAL or REMOTE). The run type is logged for informational purposes.
     * </p>
     *
     * @return The corresponding `RunType` for the currently set run type.
     * @throws ExceptionHub.ConfigTypeException If the run type is invalid or
     *                                          not recognized.
     */
    public RunType getTestRunType() {
        setRunType(getValue(RunType.TEST_RUN.getRunType())); // Set run from value
        if (getRunType() == null || getRunType().isEmpty()) {
            log.error("Run type is not specified or is empty.");
            throw new ExceptionHub.ConfigTypeException("Run type is not specified.");
        }
        return switch (getRunType().toUpperCase()) {
            case LOCAL -> {
                log.info("Local run is opted for test execution");
                yield RunType.LOCAL;
            }
            case REMOTE -> {
                log.info("Remote run is opted for test execution");
                yield RunType.REMOTE;
            }
            default -> {
                log.error("Invalid run type: '{}'", getRunType());
                throw new ExceptionHub.ConfigTypeException("Invalid run type: " + getRunType());
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
            log.info("Run type is specified from the MVN CMD LINE ARGS.");
            return value;
        }
        value = fetchDataFromPropFile(key);
        log.info("Run type is specified from the CONFIG FILE.");
        if (value == null || value.isEmpty()) {
            log.warn("Value for key '{}' not found in environment or property file.", key);
        }
        return value;
    }

}
