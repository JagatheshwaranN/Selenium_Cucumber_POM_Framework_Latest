package com.qa.ctf.constant;

/**
 * Enum representing supported browser types for test execution.
 * <p>
 * This enum defines constants for various web browsers such as Chrome, Firefox, and Edge.
 * It also provides a generic "Browser" type for flexibility.
 * </p>
 *
 * @author Jagatheshwaran N
 * @version 1.0
 */
public enum BrowserType {

    /** Generic browser type, used as Key for environment variable */
    BROWSER("Browser"),

    /** Represents the Google Chrome browser. */
    CHROME("Chrome"),

    /** Represents the Mozilla Firefox browser. */
    FIREFOX("Firefox"),

    /** Represents the Microsoft Edge browser. */
    EDGE("Edge");

    // Holds the string representation of the browser type
    private final String browserType;

    /**
     * Constructor to initialize the browser type.
     *
     * @param browserType The string representation of the browser type.
     * @throws IllegalArgumentException If the provided run type is null or empty.
     */
    BrowserType(String browserType) {
        if (browserType == null || browserType.isEmpty()) {
            throw new IllegalArgumentException("Run Type cannot be null or empty.");
        }
        this.browserType = browserType;
    }

    /**
     * Retrieves the string representation of the browser type.
     *
     * @return The browser type as a string.
     */
    public String getBrowserType() {
        return browserType;
    }

}
