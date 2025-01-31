package com.qa.ctf.listener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.qa.ctf.constant.TestConstants;
import com.qa.ctf.report.ExtentReportManager;
import com.qa.ctf.util.ExceptionHub;
import com.qa.ctf.util.FileReader;
import com.qa.ctf.util.ScreenCapture;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.ctf.factory.DriverFactory;

import static com.qa.ctf.constant.TestConstants.*;

/**
 * The TestListener class implements the TestNG {@link ITestListener} and {@link ISuiteListener}
 * interfaces to provide custom behavior during test execution and suite lifecycle events.
 *
 * <p>Features:
 * <ul>
 *     <li>Logs the start and end of test suites and test contexts.</li>
 *     <li>Integrates with the ExtentReports library to generate detailed HTML reports.</li>
 *     <li>Captures and attaches screenshots for failed or passed test cases.</li>
 *     <li>Customizes logging and reporting for each test result (start, success, failure,
 *     skipped).</li>
 *     <li>Enhances TestNG reports with screenshots and formatted messages.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Uses the {@link ExceptionHub} class for handling exceptions during screenshot
 *   capture.</li>
 *   <li>Provides detailed logging for unexpected behaviors or failures.</li>
 * </ul>
 *
 * <p>Note:
 * The class assumes proper setup of WebDriver and ExtentReports through {@link DriverFactory}.
 * Users must ensure correct configuration of the snapshot path and date format constants
 * defined in {@link TestConstants}.
 *
 * <p>Example Usage:
 * <pre>
 * {@code
 * public class TestExecution {
 *     @BeforeClass
 *     public void setup() {
 *         DriverFactory driverFactory = new DriverFactory();
 *         TestListener listener = new TestListener(driverFactory);
 *         TestNG.addListener(listener);
 *     }
 * }
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.10
 */
public class TestListener extends DriverFactory implements ITestListener, ISuiteListener {

    // Importing the logger to enable logging for the TestListener class
    private static final Logger log = LogManager.getLogger(TestListener.class);

    // Instance of ExtentReports to manage and generate test execution reports
    ExtentReports extentReports;

    // Instance of ExtentTest to log individual test steps and results in the reports
    ExtentTest extentTest;

    // Singleton instance of DriverManager to handle WebDriver management
    DriverFactory driverFactory = DriverFactory.getInstance();

    // Singleton instance of ExtentReportManager to handle Extent Report management
    ExtentReportManager extentReportManager = ExtentReportManager.getInstance();

    // Constant key to configure the ReportNG property to disable output escaping in reports
    private static final String REPORT_CONFIG_KEY = "org.uncommons.reportng.escape-output";

    // Value for the REPORT_CONFIG_KEY to disable output escaping
    private static final String REPORT_CONFIG_VALUE = "false";

    // Constant representing the base64 image format for the screenshots
    private static final String BASE64_ENCODE = "data:image/png;base64,";

    // Path to store the screenshot files, constructed using the project's root directory and a constant path
    private static final String SCREENSHOT_PATH = CWD + SNAPSHOT_PATH;

    // Default constructor for the TestListener class
    public TestListener() {
        // Empty constructor to allow TestNG to instantiate this listener class
    }

    /**
     * Called when a test suite starts execution.
     *
     * @param suite The test suite that is starting.
     */
    @Override
    public void onStart(ISuite suite) {
        FileReader.loadPropertyFile();
        try {
            FileUtils.cleanDirectory(new File(SCREENSHOT_PATH));
            FileUtils.cleanDirectory(new File(CWD + EXTENT_REPORT_PATH));
        } catch (IOException ex) {
            log.error("Error cleaning directories: {}", ex.getMessage());
        }
        extentReports = ExtentReportManager.setupExtentReport();
        log.info("Test Suite started: {}", suite.getName());
    }

    /**
     * Called when a test suite finishes execution.
     *
     * @param suite The test suite that has finished.
     */
    @Override
    public void onFinish(ISuite suite) {
        log.info("Test Suite finished: '{}'", suite.getName());
    }

    /**
     * Called when a test context (set of test methods) starts execution.
     *
     * @param context The test context that is starting.
     */
    @Override
    public void onStart(ITestContext context) {
//        extentReports = ExtentReportManager.setupExtentReport();
        log.info("Test Context started: '{}'", context.getName());
    }

    /**
     * Called when a test context (set of test methods) finishes execution.
     *
     * @param context The test context that has finished.
     */
    @Override
    public void onFinish(ITestContext context) {
        log.info("Test Context finished: '{}'", context.getName());
        extentReports.flush();
    }

    /**
     * Called when a test method starts execution.
     *
     * @param result The result object containing details of the test method.
     */
    @Override
    public void onTestStart(ITestResult result) {
        extentTest = extentReports.createTest(StringUtils.capitalize(result.getMethod().getMethodName()));
        extentReportManager.setExtentTest(extentTest);
        extentReportManager.getExtentTest().log(Status.INFO, () -> StringUtils.capitalize(result.getName()) + TEST_START);
        Reporter.log(StringUtils.capitalize(result.getName()) + TEST_START);
    }

    /**
     * Called when a test method passes.
     *
     * @param result The result object containing details of the test method.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        handleTestResult(result, Status.PASS, TEST_PASS);
        extentReportManager.closeExtentTest();
        log.info("Test Passed: '{}'", StringUtils.capitalize(result.getName()));
    }

    /**
     * Called when a test method fails.
     *
     * @param result The result object containing details of the test method.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        handleTestResult(result, Status.FAIL, TEST_FAIL);
        extentReportManager.closeExtentTest();
        log.error("Test Failed: '{}'", StringUtils.capitalize(result.getName()));
    }

    /**
     * Called when a test method is skipped.
     *
     * @param result The result object containing details of the test method.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        extentReportManager.getExtentTest().log(Status.SKIP, () -> StringUtils.capitalize(result.getName()) + TEST_SKIP);
        log.warn("Test Skipped: '{}'", StringUtils.capitalize(result.getName()));
    }

    /**
     * Handles test results by logging the status, capturing screenshots, and updating
     * TestNG reports.
     *
     * @param result  The result object containing details of the test method.
     * @param status  The {@link Status} of the test (PASS, FAIL, or SKIP).
     * @param message The message to log for the test status.
     */
    private void handleTestResult(ITestResult result, Status status, String message) {
        System.setProperty(REPORT_CONFIG_KEY, REPORT_CONFIG_VALUE);
        String snapshotPath = new ScreenCapture(driverFactory).takeScreenshot();
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(snapshotPath));
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            extentReportManager.getExtentTest().log(status, StringUtils.capitalize(result.getName()) + message,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
        } catch (Exception ex) {
            log.error("Failed to capture Base64 screenshot: {}", ex.getMessage(), ex);
            throw new ExceptionHub.ScreenshotException("Failed to create the Base64 screenshot", ex);
        }
        // BasePage.waitForSeconds();
        testNGReporterUpdate(StringUtils.capitalize(result.getName()) + message, snapshotPath);
    }

    /**
     * Updates the TestNG Reporter with the test status and an embedded screenshot.
     *
     * @param testStatus     The test status message to log.
     * @param screenshotPath The path of the screenshot to embed.
     */
    private void testNGReporterUpdate(String testStatus, String screenshotPath) {
        Reporter.log("<br>");
        Reporter.log(testStatus);
        Reporter.log("<br>");
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(Paths.get(screenshotPath));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        String base64Src = BASE64_ENCODE + base64Image;
        Reporter.log("<a href=" + base64Src + "><img src=" + base64Src
                + " height='100' width='100' /></a>");
    }

}
