package com.qa.ctf.hook;

import com.qa.ctf.context.TestContext;
import com.qa.ctf.factory.DriverFactory;
import com.qa.ctf.util.ExceptionHub;
import com.qa.ctf.util.FileReader;
import com.qa.ctf.util.ScreenCapture;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.File;

import static com.qa.ctf.constant.TestConstants.*;

/**
 * The TestHooks class provides hooks for test lifecycle management in Cucumber
 * tests, including actions to perform before and after each scenario.
 *
 * <p>Features:
 * <ul>
 *     <li>Initializes WebDriver before each scenario and ensures cleanup after.</li>
 *     <li>Captures and attaches screenshots for failed scenarios.</li>
 *     <li>Logs detailed information on scenario success or failure.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Throws {@link ExceptionHub} in case of failure during WebDriver
 *      initialization.</li>
 *   <li>Logs warnings and exceptions during screenshot capture and WebDriver
 *      cleanup.</li>
 * </ul>
 *
 * <p>Note:
 * The class assumes the correct configuration of WebDriver through {@link DriverFactory}
 * and the usage of {@link ScreenCapture} for screenshot handling.
 * It also relies on {@link TestContext} for managing shared test data and dependencies.
 *
 * <p>Example Usage:
 * <pre>
 * {@code
 * public class CucumberTest {
 *     @Before
 *     public void setup() {
 *         TestContext testContext = new TestContext();
 *         TestHooks hooks = new TestHooks(testContext);
 *         // Test setup and initialization
 *     }
 * }
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.1
 */
public class TestHooks {

    // Logger instance for the TestHooks class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(TestHooks.class);

    // WebDriver instance to interact with web elements on the web pages
    private WebDriver driver;

    // TestContext instance to manage shared test data and dependencies
    private final TestContext testContext;

    /**
     * Constructs a TestHooks instance and initializes it with the provided TestContext.
     * <p>
     * This constructor sets up the necessary test context, which is used to share
     * data between different test components and manage dependencies.
     * </p>
     *
     * @param testContext The TestContext instance that holds shared test data and
     *                    dependencies.
     */
    public TestHooks(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Executes before each scenario starts.
     * <p>
     * This method logs the scenario details, loads the property file, and initializes
     * the WebDriver instance. If WebDriver initialization fails, it throws an
     * {@link ExceptionHub}.
     * </p>
     *
     * @param scenario The Cucumber {@link Scenario} that is about to start.
     * @throws ExceptionHub If WebDriver initialization fails due to any exception.
     */
    @Before
    public void before(Scenario scenario) {
        log.info("BEFORE SCENARIO - THREAD ID: {} & SCENARIO NAME: {}",
                Thread.currentThread().threadId(), scenario.getName());
        try {
            // FileUtils.cleanDirectory(new File(SCREENSHOT_PATH));
            FileReader.loadPropertyFile();
            driver = DriverFactory.getInstance().initializeDriver();
            this.testContext.driver = driver;
        } catch (Exception ex) {
            log.error("Error initializing WebDriver: {}", ex.getMessage(), ex);
            throw new ExceptionHub("WebDriver initialization failed", ex);
        }
    }

    /**
     * Executes after each scenario ends.
     * <p>
     * This method logs the scenario details, captures a screenshot if the scenario
     * fails, and ensures the WebDriver instance is properly closed.
     * </p>
     *
     * @param scenario The Cucumber {@link Scenario} that has just finished execution.
     */
    @After
    public void after(Scenario scenario) {
        log.info("AFTER SCENARIO - THREAD ID: {} & SCENARIO NAME: {}",
                Thread.currentThread().threadId(), scenario.getName());
        try {
            if (scenario.isFailed() && driver != null) {
                log.error("Scenario failed: {}", scenario.getName());
                attachScreenToScenario(scenario);
            } else {
                log.info("Scenario passed: {}", scenario.getName());
            }
        } catch (Exception ex) {
            log.warn("Error while capturing screenshot: {}", ex.getMessage(), ex);
        } finally {
            if(driver != null) {
                driver.quit();
                log.info("WebDriver instance closed successfully.");
            }
        }
    }

    /**
     * Captures a screenshot and attaches it to the scenario report.
     * <p>
     * This method utilizes the {@link ScreenCapture} utility to take a screenshot
     * and attach it to the given Cucumber scenario for reporting purposes.
     * </p>
     *
     * @param scenario The Cucumber {@link Scenario} to which the screenshot should
     *                 be attached.
     */
    private void attachScreenToScenario(Scenario scenario) {
        ScreenCapture screenCapture = new ScreenCapture(DriverFactory.getInstance());
        byte[] screenshot = screenCapture.convertImageToByteArray(screenCapture.takeScreenshot());
        scenario.attach(screenshot, IMG_PNG_FORMAT, scenario.getName());
        log.info("Screenshot attached for scenario: {}", scenario.getName());
    }

}
