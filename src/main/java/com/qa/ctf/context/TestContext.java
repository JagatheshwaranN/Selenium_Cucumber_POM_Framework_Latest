package com.qa.ctf.context;

import com.qa.ctf.factory.DriverFactory;
import com.qa.ctf.util.FileReader;
import org.openqa.selenium.WebDriver;

/**
 * TestContext class manages shared test dependencies across different test components.
 * <p>
 * This class holds a WebDriver instance, ensuring that all page objects and test classes
 * can access and utilize the same WebDriver instance during test execution.
 * </p>
 *
 * @author Jagatheshwaran N
 * @version 1.0
 */
//public class TestContext {
//
//    /** WebDriver instance used for interacting with web elements in test execution. */
//    public WebDriver driver;
//}

/**
 * The TestContext class serves as a context holder for sharing test-related objects
 * across different parts of the test framework, ensuring proper dependency injection.
 */
public class TestContext {

    private final DriverFactory driverFactory;

    /**
     * Constructor initializes required test dependencies.
     */
    public TestContext() {
        // FileReader.loadPropertyFile();
        this.driverFactory = DriverFactory.getInstance();
        //this.driverFactory.initializeDriver();
    }

    /**
     * Gets the WebDriver instance from DriverFactory.
     *
     * @return WebDriver instance for the current thread.
     */
    public WebDriver getDriver() {
        return driverFactory.getDriver();
    }
}

