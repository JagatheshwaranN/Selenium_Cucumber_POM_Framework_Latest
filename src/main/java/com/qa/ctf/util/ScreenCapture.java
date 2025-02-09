package com.qa.ctf.util;

import com.qa.ctf.factory.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.UUID;

import static com.qa.ctf.constant.TestConstants.*;

/**
 * The {@code ScreenCapture} class provides utility methods for capturing, saving, and
 * processing screenshots in the automation framework.
 *
 * <p>Features:
 * <ul>
 *   <li><b>Full Page Screenshot:</b> Captures the entire scrollable page using the
 *   AShot library when the system property {@code FULL_PAGE_SCREENSHOT} is set to "Yes".</li>
 *   <li><b>Regular Screenshot:</b> Captures only the visible portion of the browser
 *   window using Selenium’s {@code TakesScreenshot} interface.</li>
 *   <li><b>Base64 Image Conversion:</b> Converts a saved screenshot to a Base64-encoded
 *   string, useful for embedding images in reports or logs.</li>
 *   <li><b>Byte Array Image Conversion:</b> Converts an image file to a byte array
 *   for further processing or storage.</li>
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
 * <p>Example Usage:
 * <pre>
 * {@code
 * ScreenCapture screenCapture = new ScreenCapture(driverFactory);
 * String screenshotPath = screenCapture.takeScreenshot();
 * System.out.println("Screenshot saved at: " + screenshotPath);
 *
 * String base64Image = screenCapture.convertImageToBase64Format(screenshotPath);
 * System.out.println("Base64 Encoded Image: " + base64Image);
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.1
 */
public class ScreenCapture {

    // Logger instance for the ScreenCapture class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(ScreenCapture.class);

    // Instance of DriverFactory to manage the WebDriver for interacting with the browser
    private final DriverFactory driverFactory;

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
     * Captures a screenshot of the current browser window and saves it to the specified
     * directory.
     * <p>
     * This method supports two types of screenshots:
     * <ul>
     *     <li><b>Full Page Screenshot:</b> Captures the entire scrollable page using the
     *     AShot library when the system property {@code FULL_PAGE_SCREENSHOT} is set to
     *     "Yes".</li>
     *     <li><b>Regular Screenshot:</b> Captures only the visible portion of the screen
     *     using Selenium's {@code TakesScreenshot} interface.</li>
     * </ul>
     * The screenshot is saved with a unique filename containing a timestamp and a UUID.
     * </p>
     *
     * @return The absolute file path of the saved screenshot.
     * @throws ExceptionHub.ScreenshotException If an error occurs while capturing or saving
     *                                          the screenshot.
     */
    public String takeScreenshot() {
        String timestamp = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());
        String uniqueId = UUID.randomUUID().toString();
        File destination = new File(SCREENSHOT_PATH + timestamp + "_" + uniqueId + IMG_FORMAT);
        try {
            if(System.getProperty(FULL_PAGE_SCREENSHOT).equalsIgnoreCase(OPTION_YES)) {
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

    /**
     * Converts an image file to its Base64-encoded string representation.
     * <p>
     * This method reads the specified image file and encodes its content into a Base64
     * string. It is useful for embedding images in HTML, JSON, or reports without needing
     * external file references.
     * </p>
     *
     * @param imageFilePath The absolute path of the image file.
     * @return A Base64-encoded string representing the image content.
     */
    public String convertImageToBase64Format(String imageFilePath) {
        log.info("Converting image to Base64: {}", imageFilePath);
        byte[] imageBytes = readImageFile(imageFilePath);
        String base64String = Base64.getEncoder().encodeToString(imageBytes);
        log.info("Image successfully converted to Base64 format.");
        return base64String;
    }

    /**
     * Converts an image file to a byte array.
     * <p>
     * This method reads the specified image file and returns its content as a byte
     * array. It is useful for handling images in automation frameworks, reports,
     * or file processing tasks.
     * </p>
     *
     * @param imageFilePath The absolute path of the image file.
     * @return A byte array containing the image data.
     */
    public byte[] convertImageToByteArray(String imageFilePath) {
        log.info("Converting image to byte array: {}", imageFilePath);
        return readImageFile(imageFilePath);
    }

    /**
     * Reads an image file and returns its content as a byte array.
     * <p>
     * This helper method reads the specified image file into a byte array. If
     * the file path is null or the file cannot be read, an error is logged, and
     * an exception is thrown.
     * </p>
     *
     * @param imageFilePath The absolute path of the image file.
     * @return A byte array containing the image data.
     *
     * @throws ExceptionHub If the image file path is null or an error occurs while
     * reading the file.
     */
    private byte[] readImageFile(String imageFilePath) {
        if (imageFilePath == null) {
            log.error("Image file path is null.");
            throw new ExceptionHub.ScreenshotException("Image file path should not be null.");
        }
        try {
            return Files.readAllBytes(Paths.get(imageFilePath));
        } catch (IOException ex) {
            log.error("Error reading image file: {}", imageFilePath, ex);
            throw new ExceptionHub.ScreenshotException("Error reading image file", ex);
        }
    }

}