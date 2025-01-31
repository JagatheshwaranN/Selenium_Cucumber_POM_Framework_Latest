package com.qa.ctf.util;

import com.qa.ctf.context.TestContext;
import com.qa.ctf.factory.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import static com.qa.ctf.constant.TestConstants.*;

/**
 * The {@code ScreenCapture} class provides utility methods for capturing screenshots
 * of web pages using Selenium WebDriver. It supports capturing both full-page and
 * visible area screenshots and saves them with a unique filename.
 *
 * <p>Features:
 * <ul>
 *   <li><b>Full Page Screenshot:</b> Captures the entire scrollable page using the
 *   AShot library when the system property {@code FULL_PAGE_SCREENSHOT} is set to "Yes".</li>
 *   <li><b>Regular Screenshot:</b> Captures only the visible portion of the browser
 *   window using Selenium’s {@code TakesScreenshot} interface.</li>
 *   <li><b>Automatic File Naming:</b> Screenshots are saved with a unique timestamp
 *   and UUID to prevent overwriting.</li>
 *   <li><b>Logging:</b> Provides logging for successful and failed screenshot captures.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Throws {@code ExceptionHub.ScreenshotException} if an error occurs during
 *   screenshot capture or file saving.</li>
 *   <li>Logs detailed error messages to assist with debugging failures.</li>
 * </ul>
 *
 * <p>Note:
 * Ensure that the WebDriver instance is properly initialized before invoking the screenshot
 * methods.
 *
 * <p>Example:
 * <pre>
 * {@code
 * ScreenCapture screenCapture = new ScreenCapture(driverManager);
 * String screenshotPath = screenCapture.takeScreenshot();
 * System.out.println("Screenshot saved at: " + screenshotPath);
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.0
 */
public class ScreenCapture {

    // Instance of DriverFactory to manage the WebDriver for interacting with the browser
    private final DriverFactory driverFactory;

    // Importing the logger to enable logging for the TestListener class
    private static final Logger log = LogManager.getLogger(ScreenCapture.class);

    // Path to store the screenshot files, constructed using the project's root directory and a constant path
    private static final String SCREENSHOT_PATH = CWD + SNAPSHOT_PATH;

    // Constant representing the image format for the screenshots
    private static final String IMG_FORMAT = ".png";

    // Constant representing the image format for the Ashot screenshots
    private static final String PNG_IMG_FORMAT = "png";

    /**
     * Constructs a ScreenCapture instance and initializes it with the provided
     * DriverManager.
     * <p>
     * This constructor sets up the necessary dependencies, including the DriverManager
     * for managing WebDriver instances.
     * </p>
     *
     * @param driverFactory       The DriverManager instance for managing WebDriver.
     * @throws IllegalArgumentException If the provided DriverManager is null.
     */
    public ScreenCapture(DriverFactory driverFactory) {
        if (driverFactory == null) {
            throw new IllegalArgumentException("DriverManager cannot be null.");
        }
        this.driverFactory = driverFactory;
    }

    /**
     * Captures a screenshot of the current browser window and saves it to the specified directory.
     * <p>
     * This method supports two types of screenshots:
     * <ul>
     *     <li><b>Full Page Screenshot:</b> Captures the entire scrollable page using the AShot library when
     *     the system property {@code FULL_PAGE_SCREENSHOT} is set to "Yes".</li>
     *     <li><b>Regular Screenshot:</b> Captures only the visible portion of the screen using Selenium's
     *     {@code TakesScreenshot} interface.</li>
     * </ul>
     * The screenshot is saved with a unique filename containing a timestamp and a UUID.
     * </p>
     *
     * @return The absolute file path of the saved screenshot.
     * @throws ExceptionHub.ScreenshotException If an error occurs while capturing or saving the screenshot.
     */
    public String takeScreenshot() {
        String timestamp = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());
        String uniqueId = UUID.randomUUID().toString();
        File destination = new File(SCREENSHOT_PATH + timestamp + "_" + uniqueId + IMG_FORMAT);
        try {
            if(System.getProperty(FULL_PAGE_SCREENSHOT).equalsIgnoreCase("Yes")) {
                Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driverFactory.getDriver());
                ImageIO.write(screenshot.getImage(), PNG_IMG_FORMAT, destination);
                log.info("Full page screenshot saved: '{}'", destination.getAbsolutePath());
            } else {
                File source = ((TakesScreenshot) driverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(source, destination);
                log.info("Screenshot saved: '{}'", destination.getAbsolutePath());
            }
        } catch (IOException ex) {
            log.error("Failed to save screenshot: {}", ex.getMessage(), ex);
            throw new ExceptionHub.ScreenshotException("Failed to create the screenshot", ex);
        }
        return destination.getAbsolutePath();
    }

}
