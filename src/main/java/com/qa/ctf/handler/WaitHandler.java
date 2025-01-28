package com.qa.ctf.handler;

import com.aventstack.extentreports.Status;
import com.qa.ctf.context.TestContext;
import com.qa.ctf.report.ExtentReportManager;
import com.qa.ctf.utils.ExceptionHub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.qa.ctf.constants.TestConstants.*;

/**
 * The WaitHandler class provides utility methods for implementing explicit,
 * and custom wait strategies within a Selenium WebDriver session. These methods
 * enable efficient synchronization of test scripts with dynamic web elements.
 *
 * <p>Features:
 * <ul>
 *   <li><b>Explicit Waits:</b> Predefined methods to wait for specific conditions
 *   such as element visibility, clickable, or presence in the DOM.</li>
 *   <li><b>Custom Waits:</b> Utility methods to define custom conditions for
 *   advanced synchronization needs.</li>
 *   <li><b>Error Handling:</b> Graceful handling of timeout scenarios with
 *   descriptive error messages for improved debugging.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions from the {@link ExceptionHub} class are thrown for
 *       detailed error reporting in case of wait failures.</li>
 *   <li>Comprehensive logging is provided for successful wait conditions and
 *       error scenarios.</li>
 * </ul>
 *
 * <p>Note:
 * This class assumes that the WebDriver is properly initialized and supports
 * the desired wait operations. Ensure appropriate timeout configurations
 * to balance performance and reliability.
 *
 * <p>Example:
 * <pre>
 * {@code
 * WaitHandler waitHandler = new WaitHandler();
 * WebElement element = waitHandler.waitForElementToBeVisible(By.id("example"), 10);
 * waitHandler.waitForCondition(ExpectedConditions.elementToBeClickable(element), 15);
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.4
 */
public class WaitHandler {

    // Logger instance for the WaitHandler class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(WaitHandler.class);

    // Instance of WebDriverWait to handle waiting for elements to appear on the page
    protected WebDriverWait wait;

    // Instance of ExtentReportManager to manage the extent report
    protected ExtentReportManager extentReportManager;

    private final WebDriver driver;

    /**
     * Constructs a WaitHandler instance and initializes it with the provided
     * DriverManager.
     * <p>
     * This constructor ensures that the DriverManager is not null before assigning
     * it to the instance variable. It also initializes the WebDriverWait instance
     * for handling explicit waits and sets up the ExtentReportManager for reporting.
     * </p>
     *
     * @param  testContext DriverManager instance to be used for interacting with
     *                      the WebDriver.
     * @throws IllegalArgumentException If the provided DriverManager is null.
     */
    public WaitHandler(TestContext testContext) {
//        if (driverManager == null) {
//            throw new IllegalArgumentException("DriverManager cannot be null.");
//        }
        this.driver = testContext.driver;
        extentReportManager = ExtentReportManager.getInstance();
        this.wait = new WebDriverWait(driver,
                Duration.ofSeconds(EXPLICIT_WAIT_TIME));
    }

    /**
     * Waits for the specified element to be visible on the page.
     * <p>
     * This method waits until the given element becomes visible. If the element
     * is not visible within the specified timeout, an exception is thrown.
     * </p>
     *
     * @param element      The WebElement to wait for.
     * @param elementLabel The label or description of the element.
     * @throws ExceptionHub.ElementNotFoundException if the element is not found
     *                                               within the timeout.
     * @throws ExceptionHub                          if the provided element is null.
     */
    public void waitForElementVisible(WebElement element, String elementLabel) {
        if (element == null) {
            throw new ExceptionHub(elementLabel + " element is null.");
        }
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            log.info("Element is visible: '{}'", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Element is visible: '%s'", elementLabel));
        } catch (NoSuchElementException ex) {
            log.error("Element not found: '{}'", elementLabel, ex);
            extentReportManager.getExtentTest().log(Status.FAIL, String.format("Element not found: '%s'", elementLabel));
            throw new ExceptionHub.ElementNotFoundException(elementLabel, ex);
        }
    }

    /**
     * Waits for the presence of all elements located by the specified locator
     * on the page.
     * <p>
     * This method waits until all elements matching the given locator are present
     * on the DOM. If the elements are not present within the specified timeout,
     * an exception is thrown.
     * </p>
     *
     * @param locator      The locator used to find the elements.
     * @param elementLabel The label or description of the elements being waited for.
     * @throws ExceptionHub.ElementNotFoundException if the elements are not found
     *                                               within the timeout.
     * @throws ExceptionHub                          if the provided element is null.
     */
    public void waitForPresenceOfElements(By locator, String elementLabel) {
        if (locator == null) {
            throw new ExceptionHub(elementLabel + " element is null.");
        }
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            log.info("Elements are present: '{}'", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Elements are present: '%s'", elementLabel));
        } catch (NoSuchElementException ex) {
            log.error("Elements not found: '{}'", elementLabel, ex);
            extentReportManager.getExtentTest().log(Status.FAIL, String.format("Elements not found: '%s'", elementLabel));
            throw new ExceptionHub.ElementNotFoundException(elementLabel, ex);
        }
    }

    /**
     * Waits for the specified element to become clickable on the page.
     * <p>
     * This method waits until the given element is both visible and enabled such that
     * it can be clicked. If the element does not become clickable within the specified
     * timeout, an exception is thrown.
     * </p>
     *
     * @param element      The WebElement to wait for.
     * @param elementLabel The label or description of the element.
     * @throws ExceptionHub.ElementNotFoundException if the element is not found on the page.
     * @throws ExceptionHub.TimeoutException         if the element does not become clickable
     *                                               within the timeout.
     * @throws ExceptionHub                          if the provided element is null.
     */
    private void waitForElementToBeClickable(WebElement element, String elementLabel) {
        if (element == null) {
            throw new ExceptionHub(elementLabel + " element is null.");
        }
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            log.info("Element is clickable: '{}'", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Element is clickable: '%s'", elementLabel));
        } catch (NoSuchElementException ex) {
            log.error("Element not clickable: '{}'. Element was not found.", elementLabel, ex);
            extentReportManager.getExtentTest().log(Status.FAIL, String.format("Element not clickable: '%s'. Element was not found.", elementLabel));
            throw new ExceptionHub.ElementNotFoundException(elementLabel, ex);
        } catch (TimeoutException ex) {
            log.error("Element not clickable within the timeout: '{}'", elementLabel, ex);
            extentReportManager.getExtentTest().log(Status.FAIL, String.format("Element not clickable within the timeout: '%s'", elementLabel));
            throw new ExceptionHub.TimeoutException(String.format("Element not clickable within the timeout: %s", elementLabel), ex);
        }
    }

    /**
     * Waits for the DOM to stabilize by continuously comparing its current state
     * with its previous state.
     * <p>
     * This method repeatedly checks the DOM's page source at regular intervals until
     * the DOM stops changing or the specified timeout is reached. It uses an
     * AtomicReference to manage the previous state of the DOM to ensure compatibility
     * with lambda expressions.
     * </p>
     *
     * @throws TimeoutException if the DOM does not stabilize within the timeout period.
     * @throws ExceptionHub     if any unexpected error occurs during the DOM stability check.
     */
    public void waitForDOMToBeStable() {
        try {
            AtomicReference<String> initialDom = new AtomicReference<>(driver.getPageSource());
            boolean isStable = wait.until(driver -> {
                String currentDom = driver.getPageSource();
                boolean domChanged = !Objects.equals(currentDom, initialDom.get());
                initialDom.set(currentDom); // Update initial DOM for the next comparison
                return !domChanged;
            });
            if (isStable) {
                log.info("DOM has stabilized successfully.");
                extentReportManager.getExtentTest().log(Status.PASS, "DOM has stabilized successfully.");
            }
        } catch (TimeoutException ex) {
            log.error("DOM did not stabilize within the timeout period.", ex);
            extentReportManager.getExtentTest().log(Status.FAIL, "DOM did not stabilize within the timeout period.");
            throw new ExceptionHub.TimeoutException("DOM did not stabilize within the timeout period.", ex);
        } catch (Exception ex) {
            log.error("An error occurred while waiting for the DOM to stabilize.", ex);
            extentReportManager.getExtentTest().log(Status.FAIL, "An error occurred while waiting for the DOM to stabilize.");
            throw new ExceptionHub("An error occurred while waiting for the DOM to stabilize.", ex);
        }
    }

    /**
     * Waits for the page to load by checking the document's ready state.
     * <p>
     * This method waits for the page to reach either the "loading" or "complete" state
     * by executing JavaScript in the browser.
     * </p>
     *
     * @throws TimeoutException if the page does not load within the specified timeout.
     */
    public void waitForPageToLoad() {
        try {
            log.info("Waiting for the page to reach 'loading' state.");
            waitForPageReadyState("loading");
        } catch (TimeoutException ex) {
            log.error("Timeout occurred while waiting for the 'loading' state. " +
                    "Now checking for the 'complete' state.", ex);
            waitForPageReadyState("complete");
        }
    }

    /**
     * Waits for the page to reach the specified document ready state.
     * <p>
     * This method waits until the page's `document.readyState` matches the given state
     * (either "loading" or "complete"). It uses JavaScript execution to retrieve the
     * ready state and waits until it reaches the specified value.
     * </p>
     *
     * @param state The expected document ready state to wait for (e.g., "loading" or
     *              "complete").
     * @throws IllegalStateException if the WebDriver is null and cannot execute the
     *                               JavaScript.
     * @throws TimeoutException      if the page does not reach the specified ready state within
     *                               the timeout.
     */
    private void waitForPageReadyState(String state) {
        wait.until((ExpectedCondition<Boolean>) wd -> {
            if (wd == null) {
                log.error("WebDriver is null, cannot check document.readyState.");
                throw new IllegalStateException("WebDriver is null");
            }
            Object readyState = ((JavascriptExecutor) wd).executeScript("return document.readyState");
            log.info("Current document.readyState: {}", readyState);
            return readyState != null && readyState.toString().equals(state);
        });
        log.info("Page has reached the '{}' state.", state);
    }

    /**
     * Waits for the page title to contain the specified string.
     * <p>
     * This method waits until the title of the page contains the specified
     * string. The page title is logged for informational purposes.
     * </p>
     *
     * @param title The title or part of the title to wait for.
     */
    public void waitForPageTitle(String title) {
        wait.until(ExpectedConditions.titleContains(title));
        log.info("Page title contains: '{}'", title);
        extentReportManager.getExtentTest().log(Status.PASS, String.format("Page title contains: '%s'", title));
    }

    /**
     * Pauses the execution for 5 seconds.
     * <p>
     * This method uses the Uninterruptibles.sleepUninterruptibly method to make the
     * current thread sleep for 5 seconds without being interrupted.
     * </p>
     */
    public void waitForSeconds() {
        synchronized (driver) {
            try {
                driver.wait(WAIT_TIME);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
