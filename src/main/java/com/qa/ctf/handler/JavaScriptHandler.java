package com.qa.ctf.handler;

import com.aventstack.extentreports.Status;
import com.qa.ctf.context.TestContext;
import com.qa.ctf.report.ExtentReportManager;
import com.qa.ctf.util.ExceptionHub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

import com.qa.ctf.base.ElementActions;

/**
 * The JavaScriptHandler class provides utility methods for executing JavaScript
 * code within the context of a Selenium WebDriver session. These methods allow
 * for enhanced interaction with the web page beyond the native WebDriver commands.
 *
 * <p>Features:
 * <ul>
 *   <li>JavaScript Execution: Methods to execute custom JavaScript code in the
 *   context of the current page.</li>
 *   <li>Returning Element Data: Methods to fetch properties of elements using
 *   JavaScript, such as text or attributes.</li>
 *   <li>Page Manipulation: Methods to manipulate the page or perform actions
 *   that require JavaScript execution (e.g., scrolling).</li>
 *   <li>Custom Script Handling: Utility methods to execute complex JavaScript
 *   logic that may not be directly achievable through WebDriver API calls.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions from the {@link ExceptionHub} class
 *       are thrown for descriptive error handling in case of JavaScript execution failures.</li>
 *   <li>Detailed logging is provided for both successful script executions and error scenarios.</li>
 * </ul>
 *
 * <p>Note:
 * This class assumes the WebDriver is properly set up with support for JavaScript
 * execution in the browser. Ensure that JavaScript execution is enabled and supported
 * in the current browser context.
 *
 * <p>Example:
 * <pre>
 * {@code
 * JavaScriptHandler jsHandler = new JavaScriptHandler();
 * WebElement element = driver.findElement(By.id("example"));
 * jsHandler.executeJavaScript("arguments[0].style.backgroundColor = 'red';", element);
 * String elementText = jsHandler.executeJavaScript("return arguments[0].innerText;", element);
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.6
 */
public class JavaScriptHandler implements ElementActions {

    // Logger instance for the JavaScriptHandler class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(JavaScriptHandler.class);

    // Instance of JavascriptExecutor to execute JavaScript commands within the browser context
    private final JavascriptExecutor executor;

    // Instance of VerificationHandler to perform verification actions on elements within the page
    private final VerificationHandler verificationHandler;

    // Instance of ExtentReportManager to manage the extent report
    protected ExtentReportManager extentReportManager;

    private final WebDriver driver;

    // JavaScript command to click on an element using arguments[0] (the element)
    protected static final String CLICK_ELEMENT = "arguments[0].click();";

    // JavaScript command to type a value into an element using arguments[0] (the element)
    protected static final String TYPE_INTO_ELEMENT = "arguments[0].value='%s';";

    // JavaScript command to clear the value of an element using arguments[0] (the element)
    protected static final String CLEAR_ELEMENT = "arguments[0].value = '';";

    // JavaScript command to scroll to a specific point on the page using arguments[0] (x-axis) and arguments[1] (y-axis)
    protected static final String SCROLL_TO_ELEMENT = "window.scrollTo(arguments[0],arguments[1])";

    // JavaScript command to scroll an element into view
    protected static final String SCROLL_INTO_VIEW = "arguments[0].scrollIntoView()";

    // JavaScript command to scroll up vertically by the height of the document
    protected static final String SCROLL_UP_VERTICAL = "window.scrollTo(0, -document.body.scrollHeight)";

    // JavaScript command to scroll down vertically by the height of the document
    protected static final String SCROLL_DOWN_VERTICAL = "window.scrollTo(0, document.body.scrollHeight)";

    // JavaScript command to scroll vertically by a specified number of pixels
    protected static final String SCROLL_VERTICAL_PIXEL = "window.scrollBy(0, %s);";

    // JavaScript command to zoom the page by a specified percentage
    protected static final String PAGE_ZOOM = "document.body.style.zoom='%s';";


    /**
     * Constructs a JavaScriptHandler instance and initializes it with the provided
     * DriverManager and VerificationHandler.
     * <p>
     * This constructor sets up the necessary dependencies, including the DriverManager
     * for managing WebDriver instances, the JavascriptExecutor for executing JavaScript
     * commands, and the VerificationHandler for handling verification tasks.
     * </p>
     *
     * @param testContext       The DriverManager instance for managing WebDriver.
     * @param verificationHandler The VerificationHandler instance for handling verification
     *                            tasks.
     * @throws IllegalArgumentException If the provided DriverManager is null.
     */
    public JavaScriptHandler(TestContext testContext, VerificationHandler verificationHandler) {
//        if (driverManager == null) {
//            throw new IllegalArgumentException("DriverManager cannot be null.");
//        }
        this.driver = testContext.driver;
        this.executor = (JavascriptExecutor) driver;
        this.verificationHandler = verificationHandler;
        extentReportManager = ExtentReportManager.getInstance();
    }

    /**
     * Clicks on the specified WebElement using JavaScriptExecutor.
     * <p>
     * Verifies the visibility of the element before performing the click operation.
     * </p>
     *
     * @param element      The WebElement to be clicked.
     * @param elementLabel The label describing the element.
     */
    @Override
    public void clickElement(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            executor.executeScript(CLICK_ELEMENT, element);
            log.info("Clicked the '{}' element using JavaScriptExecutor", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Clicked the '%s' element using JavaScriptExecutor", elementLabel));
        }
    }

    /**
     * Clicks on an element located dynamically using a provided locator and value.
     * <p>
     * Constructs a dynamic locator using the value, retrieves the element, verifies its
     * visibility, and performs the click operation using JavaScriptExecutor.
     * </p>
     *
     * @param locator      The dynamic locator for the element.
     * @param value        The value to substitute in the locator.
     * @param elementLabel The label describing the element.
     * @throws ExceptionHub.JavascriptExecutorException If an error occurs while click on an
     *                                                  element.
     */
    @Override
    public void clickElement(By locator, String value, String elementLabel) {
        try {
            WebElement element = driver.findElement(By.xpath(String.format(locator.toString().replace("By.xpath: ", ""), value)));
            if (verificationHandler.isElementDisplayed(element, elementLabel)) {
                executor.executeScript(CLICK_ELEMENT, element);
                log.info("Clicked the '{}' element using JavaScriptExecutor", elementLabel);
                extentReportManager.getExtentTest().log(Status.PASS, String.format("Clicked the '%s' element using JavaScriptExecutor", elementLabel));
            }
        } catch (ElementClickInterceptedException ex) {
            log.error("Failed to click the '{}' element using JavaScriptExecutor", elementLabel, ex);
            extentReportManager.getExtentTest().log(Status.FAIL, String.format("Failed to click the '%s' element using JavaScriptExecutor", elementLabel));
            throw new ExceptionHub.JavascriptExecutorException("Exception occurred while clicking " + elementLabel + " element using JavaScriptExecutor", ex);
        }
    }

    /**
     * Types text into the specified WebElement using JavaScriptExecutor.
     * <p>
     * Verifies the visibility of the element before performing the type operation.
     * </p>
     *
     * @param element      The WebElement to enter text into.
     * @param text         The text to be entered.
     * @param elementLabel The label describing the element.
     */
    @Override
    public void typeText(WebElement element, String text, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            if (text != null) {
                executor.executeScript(String.format(TYPE_INTO_ELEMENT, text), element);
                log.info("Entered '{}' text into the '{}' element using JavaScriptExecutor", text, elementLabel);
                extentReportManager.getExtentTest().log(Status.PASS, String.format("Entered '%s' text into the '%s' element using JavaScriptExecutor", text, elementLabel));
            }
        }
    }

    /**
     * Clears the content of the specified WebElement using JavaScriptExecutor.
     * <p>
     * Verifies the visibility of the element before clearing its content.
     * </p>
     *
     * @param element      The WebElement to clear.
     * @param elementLabel The label describing the element.
     */
    @Override
    public void clearElement(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            executor.executeScript(CLEAR_ELEMENT, element);
            log.info("Cleared the content of '{}' element using JavaScriptExecutor", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Cleared the content of '%s' element using JavaScriptExecutor", elementLabel));
        }
    }

    /**
     * Scrolls to a specific web element using its x and y coordinates.
     * <p>
     * Verifies the visibility of the element before scrolling to it.
     * </p>
     *
     * @param element      The WebElement to scroll to.
     * @param elementLabel A descriptive label for the element (used for logging).
     */
    public void scrollToElement(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            executor.executeScript(SCROLL_TO_ELEMENT, element.getLocation().x, element.getLocation().y);
            log.info("Scrolled to the '{}' element using JavaScriptExecutor", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Scrolled to the '%s' element using JavaScriptExecutor", elementLabel));
        }
    }

    /**
     * Scrolls to a specific web element and clicks on it.
     * <p>
     * Verifies the visibility of the element before scrolling and clicking.
     * </p>
     *
     * @param element      The WebElement to scroll to and click.
     * @param elementLabel A descriptive label for the element (used for logging).
     */
    public void scrollToElementAndClick(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            scrollToElement(element, elementLabel);
            clickElement(element, elementLabel);
            log.info("Scrolled to the '{}' element and clicked using JavaScriptExecutor", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Scrolled to the '%s' element and clicked using JavaScriptExecutor", elementLabel));
        }
    }

    /**
     * Scrolls a specific web element into the viewport using JavaScriptExecutor.
     * <p>
     * Verifies the visibility of the element before scrolling it into view.
     * </p>
     *
     * @param element      The WebElement to scroll into view.
     * @param elementLabel A descriptive label for the element (used for logging).
     */
    public void scrollIntoView(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            executor.executeScript(SCROLL_INTO_VIEW, element);
            log.info("Scrolled into view of '{}' element using JavaScriptExecutor", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Scrolled into view of '%s' element using JavaScriptExecutor", elementLabel));
        }
    }

    /**
     * Scrolls a specific web element into the viewport and clicks on it.
     * <p>
     * Verifies the visibility of the element before scrolling and clicking.
     * </p>
     *
     * @param element      The WebElement to scroll into view and click.
     * @param elementLabel A descriptive label for the element (used for logging).
     */
    public void scrollIntoViewAndClick(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            scrollIntoView(element, elementLabel);
            clickElement(element, elementLabel);
            log.info("Scrolled into view of '{}' element and clicked using JavaScriptExecutor", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Scrolled into view of '%s' element and clicked using JavaScriptExecutor", elementLabel));
        }
    }

    /**
     * Scrolls to the top of the web page.
     * <p>
     * Uses JavaScriptExecutor to perform a vertical scroll to the top of the page.
     * </p>
     */
    public void scrollToTop() {
        executor.executeScript(SCROLL_UP_VERTICAL);
        log.info("Scrolled up vertically to top of the page using JavaScriptExecutor");
        extentReportManager.getExtentTest().log(Status.PASS, "Scrolled up vertically to top of the page using JavaScriptExecutor");
    }

    /**
     * Scrolls to the bottom of the web page.
     * <p>
     * Uses JavaScriptExecutor to perform a vertical scroll to the bottom of the page.
     * </p>
     */
    public void scrollToBottom() {
        executor.executeScript(SCROLL_DOWN_VERTICAL);
        log.info("Scrolled down vertically to bottom of the page using JavaScriptExecutor");
        extentReportManager.getExtentTest().log(Status.PASS, "Scrolled down vertically to bottom of the page using JavaScriptExecutor");
    }

    /**
     * Scrolls the page by a specified number of pixels vertically.
     * <p>
     * Positive values scroll down, while negative values scroll up.
     * </p>
     *
     * @param pixels The number of pixels to scroll (positive for down, negative for up).
     */
    public void scrollByPixel(int pixels) {
        String direction = pixels > 0 ? "down" : "up";
        executor.executeScript(String.format(SCROLL_VERTICAL_PIXEL, pixels));
        log.info("Scrolled '{}' vertically by '{}' pixels using JavaScriptExecutor", direction, Math.abs(pixels));
        extentReportManager.getExtentTest().log(Status.PASS, String.format("Scrolled '%s' vertically by '%s' pixels using JavaScriptExecutor", direction, Math.abs(pixels)));
    }

    /**
     * Zooms the page by the specified percentage.
     * <p>
     * This method uses JavaScriptExecutor to zoom in or out the page by the provided
     * zoom percentage.
     * </p>
     *
     * @param zoomPercentage The percentage to zoom the page (e.g., 110 for 110% zoom).
     */
    public void zoomInByPercentage(double zoomPercentage) {
        executor.executeScript(String.format(PAGE_ZOOM, zoomPercentage + "%"));
        log.info("Page zoomed to '{}' percent using JavaScriptExecutor", zoomPercentage);
        extentReportManager.getExtentTest().log(Status.PASS, String.format("Page zoomed to '%s' percent using JavaScriptExecutor", zoomPercentage));
    }

    /**
     * Checks if the specified WebElement is within the viewport.
     * <p>
     * This method calculates the cumulative position of the element and its ancestors,
     * then compares these positions to the viewport's boundaries to determine if the element
     * is visible within the viewport.
     * </p>
     *
     * @param element The WebElement to check for visibility in the viewport.
     * @return {@code true} if the element is within the viewport; {@code false} otherwise.
     */
    public boolean inViewport(WebElement element) {
        String script = """
                    // Calculate the cumulative offset positions of the element and its ancestors
                    for (var e = arguments[0], f = e.offsetTop, t = e.offsetLeft, o = e.offsetWidth, n = e.offsetHeight;
                        e.offsetParent;) {
                        f += (e = e.offsetParent).offsetTop;
                        t += e.offsetLeft;
                    }

                    // Check if the element's top and left positions are within the viewport's boundaries
                    return f < window.pageYOffset + window.innerHeight &&
                        t < window.pageXOffset + window.innerWidth &&
                        f + n > window.pageYOffset &&
                        t + o > window.pageXOffset;
                """;
        return Boolean.TRUE.equals(executor.executeScript(script, element));
    }

}

/*
    ==========================
    For future work
    ==========================
    private By generateDynamicLocator(By locator, String value) {
        return switch (locator) {
            case By.ByXPath byXPath -> By.xpath(String.format(locator.toString().replace("By.xpath: ", ""), value));
            case By.ByCssSelector byCssSelector ->
                    By.cssSelector(String.format(byCssSelector.toString().replace("By.cssSelector: ", ""), value));
            case By.ById byId -> By.id(String.format(byId.toString().replace("By.id: ", ""), value));
            case By.ByClassName byClassName ->
                    By.className(String.format(byClassName.toString().replace("By.className: ", ""), value));
            case By.ByTagName byTagName ->
                    By.tagName(String.format(byTagName.toString().replace("By.tagName: ", ""), value));
            case By.ByLinkText byLinkText ->
                    By.linkText(String.format(byLinkText.toString().replace("By.linkText: ", ""), value));
            case By.ByPartialLinkText byPartialLinkText ->
                    By.partialLinkText(String.format(byPartialLinkText.toString().replace("By.partialLinkText: ", ""), value));
            case null, default ->
                    throw new UnsupportedOperationException("Dynamic locator generation is not supported for the provided locator type: " + locator.getClass().getName());
        };
    }
*/