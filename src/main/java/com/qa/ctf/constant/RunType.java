package com.qa.ctf.constant;

/**
 * Enum representing different execution modes for test runs.
 * <p>
 * This enum defines constants for various run types such as Local and Remote.
 * It also includes a general "RunType" option for flexibility.
 * </p>
 *
 * @author Jagatheshwaran N
 * @version 1.0
 */
public enum RunType {

    /** Generic run type, used as a key for environment variable. */
    TEST_RUN("RunType"),

    /** Represents execution on a local machine. */
    LOCAL("Local"),

    /** Represents execution on a remote server. */
    REMOTE("Remote");

    // Holds the string representation of the run type
    private final String runType;

    /**
     * Constructor to initialize the run type.
     *
     * @param runType The string representation of the run type.
     * @throws IllegalArgumentException If the provided run type is null or empty.
     */
    RunType(String runType) {
        if (runType == null || runType.isEmpty()) {
            throw new IllegalArgumentException("Run Type cannot be null or empty.");
        }
        this.runType = runType;
    }

    /**
     * Retrieves the string representation of the run type.
     *
     * @return The run type as a string.
     */
    public String getRunType() {
        return runType;
    }

}
