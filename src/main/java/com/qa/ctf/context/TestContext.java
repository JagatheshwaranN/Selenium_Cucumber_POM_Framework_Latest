package com.qa.ctf.context;

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
public class TestContext {

    /** WebDriver instance used for interacting with web elements in test execution. */
    public WebDriver driver;
}
