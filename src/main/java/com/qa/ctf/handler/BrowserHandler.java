package com.qa.ctf.handler;

import java.util.LinkedList;
import java.util.Set;

import com.aventstack.extentreports.Status;
import com.qa.ctf.context.TestContext;
import com.qa.ctf.report.ExtentReportManager;
import com.qa.ctf.util.ExceptionHub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.*;

/**
 * The BrowserHandler class provides utility methods to interact with
 * and manage browser-specific operations in Selenium WebDriver.
 * It includes methods to handle browser navigation, manage browser windows,
 * switch between frames, and retrieve page information like the URL and title.
 *
 * <p>Features:
 * <ul>
 *   <li>Navigation: Methods for navigating back, forward, and refreshing the
 *   browser.</li>
 *   <li>Window Management: Methods to retrieve and switch between browser window
 *   handles.</li>
 *   <li>Frame Handling: Methods to switch to frames using name, index, or WebElement
 *   reference.</li>
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
 * BrowserHandler browserHandler = new BrowserHandler();
 * browserHandler.navigateBack();
 * browserHandler.switchToFrame("frameName");
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.1
 */
public class BrowserHandler {

    // Logger instance for the BrowserHandler class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(BrowserHandler.class);

    // WebDriver instance to interact with web elements on the web pages
    private final WebDriver driver;

    // Instance of ExtentReportManager to manage the extent report
    private final ExtentReportManager extentReportManager;

    /**
     * Constructs a BrowserHandler instance and initializes it with the provided
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
    public BrowserHandler(TestContext testContext) {
        if (testContext == null) {
            throw new IllegalArgumentException("TestContext cannot be null.");
        }
        this.driver = testContext.driver;
        extentReportManager = ExtentReportManager.getInstance();
    }

    /**
     * Navigates the browser back to the previous page in the session history.
     * <p>
     * This method attempts to navigate to the previous page in the browser's history.
     * If the navigation fails, a BaseException.NavigationException is thrown.
     * </p>
     */
    public void navigateBack() {
        driver.navigate().back();
        log.info("Navigated to the previous page in the browser.");
        extentReportManager.getExtentTest().log(Status.PASS, "Navigated to the previous page in the browser.");
    }

    /**
     * Navigates the browser forward to the next page in the session history.
     * <p>
     * This method attempts to navigate to the next page in the browser's history.
     * If the navigation fails, a BaseException.NavigationException is thrown.
     * </p>
     */
    public void navigateForward() {
        driver.navigate().forward();
        log.info("Navigated to the next page in the browser.");
        extentReportManager.getExtentTest().log(Status.PASS, "Navigated to the next page in the browser.");
    }

    /**
     * Refreshes the current page in the browser.
     * <p>
     * This method attempts to refresh the current page. If the refresh fails, a
     * BaseException.NavigationException is thrown.
     * </p>
     */
    public void refresh() {
        driver.navigate().refresh();
        log.info("The current page in the browser is refreshed.");
        extentReportManager.getExtentTest().log(Status.PASS, "The current page in the browser is refreshed.");
    }

    /**
     * Retrieves the handle of the currently active browser window.
     * <p>
     * This method fetches the unique window handle of the current browser window.
     * If there is an issue retrieving the handle, a BaseException.WindowException is
     * thrown.
     * </p>
     *
     * @return The handle of the current browser window.
     */
    public String getBrowserWindowHandle() {
        String handle = driver.getWindowHandle();
        log.info("Captured browser window handle: '{}'", handle);
        extentReportManager.getExtentTest().log(Status.PASS, String.format("Captured browser window handle: '%s'", handle));
        return handle;
    }

    /**
     * Retrieves the handles of all currently open browser windows.
     * <p>
     * This method fetches all window handles for the currently open browser windows.
     * If there is an issue retrieving the window handles, a BaseException.WindowException
     * is thrown.
     * </p>
     *
     * @return A set of handles for all open browser windows.
     */
    public Set<String> getBrowserWindowHandles() {
        Set<String> handles = driver.getWindowHandles();
        log.info("Captured '{}' browser window handles :: '{}'", handles.size(), handles);
        extentReportManager.getExtentTest().log(Status.PASS, String.format("Captured '%s' browser window handles: '%s'", handles.size(), handles));
        return handles;
    }

    /**
     * Switches to a browser window based on the specified index.
     * <p>
     * Delegates to a helper method to switch the browser's context to
     * the specified window index.
     * </p>
     *
     * @param index The index of the window to switch to (0-based).
     */
    public void switchToWindow(int index) {
        switchToWindowByIndex(index);
    }

    /**
     * Switches control to the parent browser window.
     * <p>
     * This method switches the browser's context to the first window in the list of
     * open windows.
     * </p>
     */
    public void switchToParentWindow() {
        var windowsId = getWindowsList();
        switchToWindowByIndex(Integer.parseInt(windowsId.getFirst())); // First window index is 0
    }

    /**
     * Switches to a browser window based on the specified index.
     * <p>
     * This method retrieves the list of browser window handles and switches
     * to the window at the specified index.
     * </p>
     *
     * @param index The index of the window to switch to.
     * @throws IllegalArgumentException     If the provided index is out of bounds.
     * @throws ExceptionHub.WindowException If the window cannot be switched due to an exception.
     */
    private void switchToWindowByIndex(int index) {
        var windowsId = new LinkedList<>(getBrowserWindowHandles());
        if (index < 0 || index >= windowsId.size()) {
            throw new IllegalArgumentException("Browser window handle has invalid index :: " + "'" + index + "'");
        }
        try {
            driver.switchTo().window(windowsId.get(index));
            log.info("Switched to browser window at index: '{}'", index);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Switched to browser window at index: '%s'", index));
        } catch (NoSuchWindowException ex) {
            log.error("Failed to switch to window at index '{}'. Exception: {}", index, ex.getMessage(), ex);
            extentReportManager.getExtentTest().log(Status.FAIL, String.format("Failed to switch to window at index '%s'", index));
            throw new ExceptionHub.WindowException("Failed to switch to window", ex);
        }
    }

    /**
     * Switches to the parent browser window after closing all child windows.
     * <p>
     * This method closes all child windows (except the first) and then switches to the
     * first window, which is assumed to be the parent window. If there are no windows to
     * close, an IllegalArgumentException is thrown. If there is an error closing child
     * windows or switching to the parent window, a BaseException.WindowException is thrown.
     * </p>
     *
     * @throws IllegalArgumentException     If there are no browser windows available to close.
     * @throws ExceptionHub.WindowException If there is an error closing child windows or
     *                                      switching to the parent window.
     */
    public void switchToParentWithChildClose() {
        var windowsId = getWindowsList();
        log.info("Total windows before closing the children: '{}'", windowsId.size());
        try {
            for (var i = 1; i < windowsId.size(); i++) {
                var childWindow = windowsId.get(i);
                log.info("Closing child browser window with handle: '{}'", childWindow);
                extentReportManager.getExtentTest().log(Status.PASS, String.format("Closing child browser window with handle: '%s'", childWindow));
                driver.switchTo().window(childWindow).close();
            }
            driver.switchTo().window(windowsId.getFirst());
            log.info("Switched back to the parent browser window with handle: '{}'", windowsId.getFirst());
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Switched back to the parent browser window with handle: '%s'", windowsId.getFirst()));
        } catch (NoSuchWindowException ex) {
            log.error("Error closing child windows or switching to parent window. Exception: {}", ex.getMessage(), ex);
            extentReportManager.getExtentTest().log(Status.FAIL, "Error closing child windows or switching to parent window.");
            throw new ExceptionHub.WindowException("Error closing child windows or switching to parent window", ex);
        }
    }

    /**
     * Retrieves a list of browser window handles as a linked list.
     * <p>
     * This method fetches the current browser's window handles and returns
     * them as a {@link LinkedList}. If no window handles are available, an
     * {@link IllegalArgumentException} is thrown.
     * </p>
     *
     * @return A {@link LinkedList} containing the browser window handles.
     * @throws IllegalArgumentException If no browser windows are available.
     */
    private LinkedList<String> getWindowsList() {
        var windowsId = new LinkedList<>(getBrowserWindowHandles());
        if (windowsId.isEmpty()) {
            throw new IllegalArgumentException("No browser windows are available.");
        }
        return windowsId;
    }

    /**
     * Switches to a frame based on the type of the provided input.
     * <p>
     * This method determines the frame to switch to based on the type of the input:
     * <ul>
     *     <li>{@link String}: Switches to the frame using its name or ID.</li>
     *     <li>{@link Integer}: Switches to the frame using its index.</li>
     *     <li>{@link WebElement}: Switches to the frame using a WebElement reference.</li>
     * </ul>
     * If the input is null or invalid, an {@link IllegalArgumentException} is thrown.
     * If switching fails, appropriate exceptions are thrown.
     * </p>
     *
     * @param frameIdentifier The frame to switch to (String, Integer, or WebElement).
     * @throws IllegalArgumentException       If the input is null or invalid.
     * @throws NoSuchFrameException           If the frame is not found.
     * @throws StaleElementReferenceException If the WebElement is stale.
     * @throws ExceptionHub.FrameException    If switching to the frame fails due to other reasons.
     */
    public void switchToFrame(Object frameIdentifier) {
        if (frameIdentifier == null) {
            throw new IllegalArgumentException("Frame identifier cannot be null.");
        }
        try {
            switch (frameIdentifier) {
                case String frameNameOrId -> {
                    if (frameNameOrId.trim().isEmpty()) {
                        throw new IllegalArgumentException("Frame name or ID cannot be empty.");
                    }
                    driver.switchTo().frame(frameNameOrId);
                    log.info("The control is switched to the frame using name or ID :: '{}'", frameNameOrId);
                    extentReportManager.getExtentTest().log(Status.PASS, String.format("The control is switched to the frame using name or ID: '%s'", frameNameOrId));
                }
                case Integer frameIndex -> {
                    if (frameIndex < 0) {
                        throw new IllegalArgumentException("Frame index cannot be negative.");
                    }
                    driver.switchTo().frame(frameIndex);
                    log.info("The control is switched to the frame using index: '{}'", frameIndex);
                    extentReportManager.getExtentTest().log(Status.PASS, String.format("The control is switched to the frame using index: '%s'", frameIndex));
                }
                case WebElement frameElement -> {
                    driver.switchTo().frame(frameElement);
                    log.info("The control is switched to the frame using WebElement: '{}'", frameElement);
                    extentReportManager.getExtentTest().log(Status.PASS, String.format("The control is switched to the frame using WebElement: '%s'", frameElement));
                }
                default ->
                        throw new IllegalArgumentException("Unsupported frame identifier type: " + frameIdentifier.getClass().getName());
            }
        } catch (StaleElementReferenceException ex) {
            log.error("Failed to switch to frame due to stale element reference. Exception: {}", ex.getMessage(), ex);
            extentReportManager.getExtentTest().log(Status.FAIL, "Failed to switch to frame due to stale element reference.");
            throw new ExceptionHub.FrameException("Error switching to frame due to stale element reference", ex);
        } catch (NoSuchFrameException ex) {
            log.error("Failed to switch to frame. Exception: {}", ex.getMessage(), ex);
            extentReportManager.getExtentTest().log(Status.FAIL, "Failed to switch to frame.");
            throw new ExceptionHub.FrameException("Error switching to frame", ex);
        }
    }

}
