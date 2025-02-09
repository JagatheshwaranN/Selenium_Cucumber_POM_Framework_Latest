package com.qa.ctf.handler;

import com.aventstack.extentreports.Status;
import com.qa.ctf.context.TestContext;
import com.qa.ctf.util.ExceptionHub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

/**
 * The AlertHandler class provides utility methods for interacting with JavaScript
 * alerts, confirms, and prompts on a web page using Selenium WebDriver.
 *
 * <p>Features:
 * <ul>
 *     <li>Retrieve the currently active alert on the page.</li>
 *     <li>Accept the active alert.</li>
 *     <li>Dismiss the active alert.</li>
 *     <li>Retrieve the text from the active alert.</li>
 *     <li>Send text to a prompt alert and accept it.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions from the {@link ExceptionHub} class
 *       are thrown for more descriptive error handling.</li>
 *   <li>Detailed logging is provided for successful operations and error scenarios.</li>
 * </ul>
 *
 * <p>Note:
 * The class assumes proper WebDriver setup and configuration. Users must handle
 * WebDriver initialization and termination separately.
 *
 * <p>Example:
 * <pre>
 * {@code
 * AlertHandler alertHandler = new AlertHandler();
 * alertHandler.getAlert();
 * alertHandler.acceptAlert();
 * alertHandler.dismissAlert();
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.1
 */
public class AlertHandler {

    // Logger instance for the AlertHandler class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(AlertHandler.class);

    // WebDriver instance to interact with web elements on the web pages
    private final WebDriver driver;

    /**
     * Constructs an AlertHandler instance and initializes it with the provided
     * TestContext
     * <p>
     * This constructor ensures that the TestContext is not null before assigning
     * it to the instance variable. It is used for managing the WebDriver instance
     * and shared test data across different page objects.
     * </p>
     *
     * @param testContext The TestContext instance to be used for interacting with
     *                    the WebDriver.
     * @throws IllegalArgumentException If the provided TestContext is null.
     */
    public AlertHandler(TestContext testContext) {
        if (testContext == null) {
            throw new IllegalArgumentException("TestContext cannot be null.");
        }
        this.driver = testContext.driver;
    }

    /**
     * Retrieves the currently active alert on the page.
     * <p>
     * This method attempts to switch to the currently active alert and retrieve it.
     * If no alert is found, it throws a custom exception (AlertNotFoundException).
     * </p>
     *
     * @return An Optional containing the Alert if present.
     * @throws ExceptionHub.AlertNotFoundException If no alert is found on the page.
     */
    public Optional<Alert> getAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            log.info("Alert found and retrieved.");
            return Optional.of(alert);
        } catch (NoAlertPresentException ex) {
            log.error("No alert present on the page.");
            throw new ExceptionHub.AlertNotFoundException(ex);
        }
    }

    /**
     * Accepts the currently active alert if present.
     * <p>
     * This method checks if an alert is present. If an alert is found, it accepts
     * the alert. If no alert is found, the method performs no action.
     * </p>
     */
    public void acceptAlert() {
        getAlert().ifPresent(alert -> {
            alert.accept();
            log.info("The alert popup is accepted.");
        });
    }

    /**
     * Dismisses the currently active alert if present.
     * <p>
     * This method checks if an alert is present. If an alert is found, it dismisses
     * the alert. If no alert is found, the method performs no action.
     * </p>
     */
    public void dismissAlert() {
        getAlert().ifPresent(alert -> {
            alert.dismiss();
            log.info("The alert popup is dismissed.");
        });
    }

    /**
     * Retrieves the text from the currently active alert.
     * <p>
     * This method retrieves the text of the active alert. If no alert is present,
     * it returns the string "No Alert Present".
     * </p>
     *
     * @return The text of the alert, or "No Alert Present" if no alert is active.
     */
    public String getAlertText() {
        Optional<Alert> alert = getAlert();
        String text = alert.map(Alert::getText).orElse("No Alert Present");
        if (text.isEmpty()) {
            log.error("No alert is active, returning empty string.");
        }
        log.info("Alert text retrieved: '{}'", text);
        return text;
    }

    /**
     * Sends the specified text to the alert (prompt) and accepts it.
     * <p>
     * This method checks if a prompt alert is present. If found, it sends the
     * specified text to the prompt and accepts the alert. If no prompt alert
     * is found, the method performs no action.
     * </p>
     *
     * @param text The text to send to the alert prompt.
     */
    public void acceptPrompt(String text) {
        getAlert().ifPresent(alert -> {
            alert.sendKeys(text);
            alert.accept();
            log.info("The prompt alert window is accepted.");
        });
    }

}
