package com.qa.ctf.handler;

import com.aventstack.extentreports.Status;
import com.qa.ctf.context.TestContext;
import com.qa.ctf.report.ExtentReportManager;
import com.qa.ctf.util.ExceptionHub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.Objects;

/**
 * The VerificationHandler class provides utility methods for validating and
 * interacting with web elements in Selenium WebDriver. Ensure that elements passed
 * to these methods are properly located using Selenium locators.
 *
 * <p>Features:
 * <ul>
 *   <li>Element Display Verification: Methods to check if an element is displayed on
 *   the page.</li>
 *   <li>Text Verification: Methods to validate if the text of an element matches the
 *   expected value.</li>
 *   <li>Text and Attribute Retrieval: Methods to fetch text and attributes (like value)
 *   from elements.</li>
 *   <li>Element Validation: Utility methods to validate elements for null checks and
 *   visibility.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions from the {@link ExceptionHub} class
 *       are thrown for descriptive error handling in case of element-related failures.</li>
 *   <li>Detailed logging is provided for both successful operations and error scenarios.</li>
 * </ul>
 *
 * <p>Note:
 * The class assumes proper WebDriver setup and initialization for element interactions.
 * Ensure elements are located within the correct DOM state to avoid stale element exceptions.
 *
 * <p>Example:
 * <pre>
 * {@code
 * VerificationHandler verificationHandler = new VerificationHandler();
 * WebElement element = driver.findElement(By.id("example"));
 * boolean isDisplayed = verificationHandler.isElementDisplayed(element,
 * "Example Element");
 * boolean isTextEqual = verificationHandler.isTextEqualTo(element, "Expected Text",
 * "Example Element");
 * String elementText = verificationHandler.readTextValueFromElement(element,
 * "Example Element");
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.1
 */
public class VerificationHandler {

    // Logger instance for the VerificationHandler class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(VerificationHandler.class);

    // TestContext instance to manage shared test data and dependencies
    private final TestContext testContext;

    // Instance of ExtentReportManager to manage the extent report
    private final ExtentReportManager extentReportManager;

    /**
     * Constructs a VerificationHandler instance and initializes it with the provided
     * TestContext.
     * <p>
     * This constructor ensures that the TestContext is not null before assigning
     * it to the instance variable. It is used for managing the WebDriver instance
     * and shared test data across different page objects. Additionally, it initializes
     * an ExtentReportManager to handle reporting.
     * </p>
     *
     * @param testContext The TestContext instance to be used for interacting with
     *                    the WebDriver.
     * @throws IllegalArgumentException If the provided TestContext is null.
     */
    public VerificationHandler(TestContext testContext) {
        if (testContext == null) {
            throw new IllegalArgumentException("TestContext cannot be null.");
        }
        this.testContext = testContext;
        extentReportManager = ExtentReportManager.getInstance();
    }

    /**
     * Checks if the specified element is displayed.
     * <p>
     * This method verifies whether a given web element is displayed on the page.
     * If the element is null, an error is logged, and the method returns false.
     * If an exception occurs during the check, it is logged and rethrown as a
     * BaseException.ElementNotFoundException.
     * BaseException.ElementNotFoundException.
     * </p>
     *
     * @param element      The WebElement to check.
     * @param elementLabel The label for logging purposes.
     * @return true if the element is displayed, false otherwise.
     * @throws ExceptionHub.ElementNotFoundException If the element is not found or
     *                                               inaccessible.
     */
    public boolean isElementDisplayed(WebElement element, String elementLabel) {
        new WaitHandler(testContext).waitForElementVisible(element, elementLabel);
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException ex) {
            log.error("Error occurred while checking the presence of the '{}' element. Exception: {}", elementLabel, ex.getMessage(), ex);
//            extentReportManager.getExtentTest().log(Status.FAIL, String.format("Error occurred while checking the presence of the '%s' element", elementLabel));
            throw new ExceptionHub.ElementNotFoundException(elementLabel, ex);
        }
    }

    /**
     * Verifies that the text of the element matches the expected text.
     * <p>
     * This method checks if the actual text of a given web element matches the
     * expected text. If the element is invalid or its text is empty, it logs an
     * error and returns false. Any exceptions during the check are logged and
     * rethrown as a BaseException.ElementNotFoundException.
     * </p>
     *
     * @param element      The WebElement to check.
     * @param expectedText The expected text.
     * @param elementLabel The label for logging purposes.
     * @return true if the text matches, false otherwise.
     */
    public boolean isTextEqualTo(WebElement element, String expectedText, String elementLabel) {
        String actualText = null;
        if (isElementDisplayed(element, elementLabel)) {
            actualText = element.getText();
            if (actualText.isEmpty()) {
                log.error("The '{}' element's text is empty, expected text: '{}'", elementLabel, expectedText);
//                extentReportManager.getExtentTest().log(Status.FAIL, String.format("The '%s' element's text is empty, expected text: '%s'", elementLabel, expectedText));
                return false;
            }
        }
        return Objects.equals(actualText, expectedText);
    }

    /**
     * Reads the text value from an element.
     * <p>
     * This method retrieves the visible text of a given web element. If the element
     * is invalid, it logs an error and returns null.
     * </p>
     *
     * @param element      The WebElement to read from.
     * @param elementLabel The label for logging purposes.
     * @return The text of the element, or null if the element is invalid.
     */
    public String readTextValueFromElement(WebElement element, String elementLabel) {
        String text = null;
        if (isElementDisplayed(element, elementLabel)) {
            text = element.getText();
            log.info("The '{}' element's text is: '{}'", elementLabel, text);
//            extentReportManager.getExtentTest().log(Status.PASS, String.format("The '%s' element's text is: '%s'", elementLabel, text));
        }
        return text;
    }

    /**
     * Reads the value attribute from an input element.
     * <p>
     * This method retrieves the value of the "value" attribute from a given input
     * web element. If the element is invalid, it logs an error and returns null.
     * If the value is empty or null, it logs a warning.
     * </p>
     *
     * @param element      The WebElement to read from.
     * @param elementLabel The label for logging purposes.
     * @return The value of the input element, or null if the element is invalid.
     */
    public String readValueFromInput(WebElement element, String elementLabel) {
        String value = null;
        if (isElementDisplayed(element, elementLabel)) {
            value = element.getDomAttribute("value");
            if (value == null || value.isEmpty()) {
                log.info("The '{}' element has no value or is empty.", elementLabel);
//                extentReportManager.getExtentTest().log(Status.PASS, String.format("The '%s' element has no value or is empty.", elementLabel));
            } else {
                log.info("The '{}' element's value is: '{}'", elementLabel, value);
//                extentReportManager.getExtentTest().log(Status.PASS, String.format("The '%s' element's value is: '%s'", elementLabel, value));
            }
        }
        return value;
    }

    /**
     * Checks if the element is not null and logs an error if it is.
     * <p>
     * This method ensures that a given web element is not null. If the element is
     * null, it logs an error and returns false.
     * </p>
     *
     * @param element      The WebElement to check.
     * @param elementLabel The label for logging purposes.
     * @return true if the element is not null, false otherwise.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isElementNotNull(WebElement element, String elementLabel) {
        if (element == null) {
            log.error("The '{}' element is null.", elementLabel);
//            extentReportManager.getExtentTest().log(Status.PASS, String.format("The '%s' element is null.", elementLabel));
            return false;
        }
        return true;
    }

}
