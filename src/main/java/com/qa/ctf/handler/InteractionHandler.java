package com.qa.ctf.handler;

import com.aventstack.extentreports.Status;
import com.qa.ctf.base.ElementActions;
import com.qa.ctf.context.TestContext;
import com.qa.ctf.report.ExtentReportManager;
import com.qa.ctf.util.ExceptionHub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Collections;

/**
 * The InteractionHandler class provides utility methods for performing advanced
 * interactions with web elements using Selenium WebDriver. These interactions
 * extend beyond basic WebDriver capabilities to include actions like mouse
 * hover, drag-and-drop, and keyboard events.
 *
 * <p>Features:
 * <ul>
 *   <li>Mouse Interactions: Methods for simulating mouse actions, such as hover,
 *       right-click, and drag-and-drop.</li>
 *   <li>Keyboard Actions: Utility methods to perform keyboard-based interactions,
 *       such as sending special keys or simulating key press events.</li>
 *   <li>Multi-action Support: Support for complex interactions that involve
 *       multiple actions performed sequentially or simultaneously.</li>
 *   <li>Custom Interaction Flows: Capability to create and execute custom
 *       interaction sequences tailored to specific test scenarios.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions from the {@link ExceptionHub} class
 *       are thrown for descriptive error handling during interaction failures.</li>
 *   <li>Detailed logging is provided for successful interactions and error scenarios
 *       to facilitate debugging and traceability.</li>
 * </ul>
 *
 * <p>Note:
 * This class assumes that the WebDriver instance is properly initialized and the
 * required elements are available in the current browser context. Ensure that the
 * browser and WebDriver versions are compatible to perform advanced interactions.
 *
 * <p>Example:
 * <pre>
 * {@code
 * InteractionHandler interactionHandler = new InteractionHandler();
 * WebElement sourceElement = driver.findElement(By.id("source"));
 * WebElement targetElement = driver.findElement(By.id("target"));
 * interactionHandler.dragAndDrop(sourceElement, targetElement);
 * interactionHandler.hoverOverElement(driver.findElement(By.id("menu")));
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.4
 */
public class InteractionHandler implements ElementActions {

    // Logger instance for the InteractionHandler class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(InteractionHandler.class);

    // Instance of Actions to build complex user interactions (e.g., mouse movements, clicks, keyboard actions)
    private final Actions builder;

    // Instance of VerificationHandler to handle verification actions, likely for validating elements on the page
    private final VerificationHandler verificationHandler;

    // Instance of ExtentReportManager to manage the extent report
    protected ExtentReportManager extentReportManager;

    private final WebDriver driver;

    /**
     * Constructs an InteractionHandler instance and initializes it with the provided
     * DriverManager and VerificationHandler.
     * <p>
     * This constructor sets up the necessary dependencies, including the DriverManager
     * for managing WebDriver instances, the Actions builder for simulating user
     * interactions with elements, and the VerificationHandler for performing verification
     * tasks.
     * </p>
     *
     * @param testContext       The DriverManager instance for managing WebDriver.
     * @param verificationHandler The VerificationHandler instance for handling verification
     *                            tasks.
     * @throws IllegalArgumentException If the provided DriverManager is null.
     */
    public InteractionHandler(TestContext testContext, VerificationHandler verificationHandler) {
//        if (driverManager == null) {
//            throw new IllegalArgumentException("DriverManager cannot be null.");
//        }
        this.driver = testContext.driver;
        this.builder = new Actions(driver);
        this.verificationHandler = verificationHandler;
        extentReportManager = ExtentReportManager.getInstance();
    }

    /**
     * Clicks on the specified WebElement using Actions.
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
            builder.click(element).perform();
            log.info("Clicked the '{}' element using Actions", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Clicked the '%s' element using Actions", elementLabel));
        }
    }

    /**
     * Clicks on an element located dynamically using a provided locator and value.
     * <p>
     * Constructs a dynamic locator using the value, retrieves the element, verifies its
     * visibility, and performs the click operation using Actions.
     * </p>
     *
     * @param locator      The dynamic locator for the element.
     * @param value        The value to substitute in the locator.
     * @param elementLabel The label describing the element.
     * @throws ExceptionHub.InteractionException If an error occurs while click on an
     *                                           element.
     */
    @Override
    public void clickElement(By locator, String value, String elementLabel) {
        try {
            WebElement element = driver.findElement(By.xpath(String.format(locator.toString().replace("By.xpath: ", ""), value)));
            if (verificationHandler.isElementDisplayed(element, elementLabel)) {
                builder.click(element).perform();
                log.info("Clicked the '{}' element using Actions", elementLabel);
                extentReportManager.getExtentTest().log(Status.PASS, String.format("Clicked the '%s' element using Actions", elementLabel));
            }
        } catch (ElementClickInterceptedException ex) {
            log.error("Failed to click the '{}' element using Actions", elementLabel, ex);
            extentReportManager.getExtentTest().log(Status.FAIL, String.format("Failed to click the '%s' element using Actions", elementLabel));
            throw new ExceptionHub.InteractionException("Exception occurred while clicking " + elementLabel + " element using JavaScriptExecutor", ex);
        }
    }

    /**
     * Types text into the specified WebElement using Actions.
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
                builder.sendKeys(element, text).perform();
                log.info("Entered '{}' text into the '{}' element using Actions", text, elementLabel);
                extentReportManager.getExtentTest().log(Status.PASS, String.format("Entered '%s' text into the '%s' element using Actions", text, elementLabel));
            }
        }
    }

    /**
     * Clears the content of the specified WebElement using Actions.
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
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.DELETE);
            log.info("Cleared the content of '{}' element using Actions", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Cleared the content of '%s' element using Actions", elementLabel));
        }
    }

    /**
     * Hovers the mouse over the specified element.
     * <p>
     * Verifies the visibility of the element before performing the hover action.
     * </p>
     *
     * @param element      The WebElement to hover over.
     * @param elementLabel The label describing the element.
     */
    public void hoverOverElement(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            builder.moveToElement(element).perform();
            log.info("The mouse hovered and clicked on the '{}' element", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("The mouse hovered and clicked on the '%s' element", elementLabel));
        }
    }

    /**
     * Performs a right-click (context click) on the specified element.
     * <p>
     * Verifies the visibility of the element before performing the right-click action.
     * </p>
     *
     * @param element      The WebElement to right-click on.
     * @param elementLabel The label describing the element.
     */
    public void rightClickElement(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            builder.contextClick(element).perform();
            log.info("The mouse right clicked on the '{}' element", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("The mouse right clicked on the '%s' element", elementLabel));
        }
    }

    /**
     * Performs a double click on the specified element.
     * <p>
     * Verifies the visibility of the element before performing the double-click action.
     * </p>
     *
     * @param element      The WebElement to double-click on.
     * @param elementLabel The label describing the element.
     */
    public void doubleClickElement(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            builder.doubleClick(element).perform();
            log.info("The mouse double clicked on the '{}' element", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("The mouse double clicked on the '%s' element", elementLabel));
        }
    }

    /**
     * Hovers the mouse over and then clicks the specified element.
     * <p>
     * Verifies the visibility of the element before performing the hover and click actions.
     * </p>
     *
     * @param element      The WebElement to hover over and click.
     * @param elementLabel The label describing the element.
     */
    public void hoverOverElementAndClick(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            hoverOverElement(element, elementLabel);
            builder.click(element).perform();
            log.info("The mouse hovered and clicked on the '{}' element", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("The mouse hovered and clicked on the '%s' element", elementLabel));
        }
    }

    /**
     * Clicks on the specified element and then releases the mouse.
     * <p>
     * Verifies the visibility of the element before performing the click and release actions.
     * </p>
     *
     * @param element      The WebElement to click and release.
     * @param elementLabel The label describing the element.
     */
    public void clickAndReleaseElement(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            builder.click(element)
                    .release(element)
                    .perform();
            log.info("The mouse click and released on the '{}' element", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("The mouse click and released on the '%s' element", elementLabel));
        }
    }

    /**
     * Clicks and holds the mouse on the specified element.
     * <p>
     * Verifies the visibility of the element before performing the click and hold action.
     * </p>
     *
     * @param element      The WebElement to click and hold.
     * @param elementLabel The label describing the element.
     */
    public void clickAndHoldElement(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            builder.clickAndHold(element).perform();
            log.info("The mouse clicked and held on the '{}' element", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("The mouse clicked and held on the '%s' element", elementLabel));
        }
    }

    /**
     * Drags the specified element and drops it onto another element.
     * <p>
     * Verifies the visibility of both the draggable and droppable elements before
     * performing the drag and drop actions.
     * </p>
     *
     * @param draggableElement The WebElement to drag.
     * @param droppableElement The WebElement to drop onto.
     * @param elementLabel1    The label describing the draggable element.
     * @param elementLabel2    The label describing the droppable element.
     */
    public void dragAndDropElement(WebElement draggableElement, WebElement droppableElement, String elementLabel1, String elementLabel2) {
        if (verificationHandler.isElementDisplayed(draggableElement, elementLabel1) && verificationHandler.isElementDisplayed(droppableElement, elementLabel2)) {
            builder.clickAndHold(draggableElement)
                    .moveToElement(droppableElement)
                    .release(droppableElement)
                    .perform();
            log.info("The '{}' element is drag and dropped on the '{}' element", elementLabel1, elementLabel2);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("The '%s' element is drag and dropped on the '%s' element", elementLabel1, elementLabel2));
        }
    }

    /**
     * Drags the specified element and drops it onto another element using the built-in
     * dragAndDrop method.
     * <p>
     * Verifies the visibility of both the draggable and droppable elements before
     * performing the drag and drop actions.
     * </p>
     *
     * @param draggableElement The WebElement to drag.
     * @param droppableElement The WebElement to drop onto.
     * @param elementLabel1    The label describing the draggable element.
     * @param elementLabel2    The label describing the droppable element.
     */
    public void dragAndDropUsingActions(WebElement draggableElement, WebElement droppableElement, String elementLabel1, String elementLabel2) {
        if (verificationHandler.isElementDisplayed(draggableElement, elementLabel1) && verificationHandler.isElementDisplayed(droppableElement, elementLabel2)) {
            builder.dragAndDrop(draggableElement, droppableElement).perform();
            log.info("The '{}' element is drag and dropped on the '{}' element", elementLabel1, elementLabel2);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("The '%s' element is drag and dropped on the '%s' element", elementLabel1, elementLabel2));
        }
    }

    /**
     * Simulates a mouse back button click using pointer input.
     * <p>
     * This action mimics the user clicking the back button on the browser.
     * </p>
     */
    public void browserBackAction() {
        PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
        Sequence sequence = new Sequence(mouse, 0)
                .addAction(mouse.createPointerDown(PointerInput.MouseButton.BACK.asArg()))
                .addAction(mouse.createPointerUp(PointerInput.MouseButton.BACK.asArg()));
        ((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
        log.info("The mouse clicked on the back button.");
        extentReportManager.getExtentTest().log(Status.PASS, "The mouse clicked on the back button.");
    }

    /**
     * Simulates a mouse forward button click using pointer input.
     * <p>
     * This action mimics the user clicking the forward button on the browser.
     * </p>
     */
    public void browserForwardAction() {
        PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
        Sequence sequence = new Sequence(mouse, 0)
                .addAction(mouse.createPointerDown(PointerInput.MouseButton.FORWARD.asArg()))
                .addAction(mouse.createPointerUp(PointerInput.MouseButton.FORWARD.asArg()));
        ((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
        log.info("The mouse clicked on the forward button.");
        extentReportManager.getExtentTest().log(Status.PASS, "The mouse clicked on the forward button.");
    }

    /**
     * Scrolls the page to the specified element.
     * <p>
     * Verifies the visibility of the element before performing the scroll action.
     * </p>
     *
     * @param element      The WebElement to scroll to.
     * @param elementLabel The label describing the element.
     */
    public void scrollToElement(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            builder.scrollToElement(element).perform();
            log.info("Scrolled to the '{}' element using Actions", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Scrolled to the '%s' element using Actions", elementLabel));
        }
    }

    /**
     * Scrolls to the specified element by its pixel position.
     * <p>
     * Verifies the visibility of the element before performing the scroll action.
     * The scroll is performed by calculating the Y-axis position of the element and
     * scrolling to that position.
     * </p>
     *
     * @param element      The WebElement to scroll to.
     * @param elementLabel The label describing the element.
     */
    public void scrollToElementByPixel(WebElement element, String elementLabel) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            int xAxis = 0;
            int yAxis = element.getRect().y;
            builder.scrollByAmount(xAxis, yAxis).perform();
            log.info("Scrolled to the '{}' element using Actions", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Scrolled to the '%s' element using Actions", elementLabel));
        }
    }

    /**
     * Scrolls from the specified element by a given number of pixels.
     * <p>
     * Verifies the visibility of the element before performing the scroll action.
     * The scroll is performed starting from the element's position and moving by the
     * specified X and Y pixel values.
     * </p>
     *
     * @param element      The WebElement to scroll from.
     * @param elementLabel The label describing the element.
     * @param xAxis        The number of pixels to scroll horizontally.
     * @param yAxis        The number of pixels to scroll vertically.
     */
    public void scrollFromElementByPixel(WebElement element, String elementLabel, int xAxis, int yAxis) {
        if (verificationHandler.isElementDisplayed(element, elementLabel)) {
            WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(element);
            builder.scrollFromOrigin(scrollOrigin, xAxis, yAxis).perform();
            log.info("Scrolled from the '{}' element using Actions", elementLabel);
            extentReportManager.getExtentTest().log(Status.PASS, String.format("Scrolled from the '%s' element using Actions", elementLabel));
        }
    }

}