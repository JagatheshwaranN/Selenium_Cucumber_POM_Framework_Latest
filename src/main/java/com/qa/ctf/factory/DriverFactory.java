package com.qa.ctf.factory;

import java.net.MalformedURLException;
import java.net.URI;

import com.qa.ctf.constant.BrowserType;
//import com.qa.ctf.utils.ExcelReader;
import com.qa.ctf.util.ExceptionHub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.qa.ctf.constant.TestConstants.*;

/**
 * The DriverFactory class manages the WebDriver instances for various browsers
 * and environments (local or remote). It provides methods for initializing,
 * retrieving, and closing WebDriver instances, as well as managing thread-local
 * storage for WebDriver and ExtentTest instances.
 *
 * <p>Features:
 * <ul>
 *     <li>Creates and manages WebDriver instances for Chrome, Firefox, and Edge
 *         browsers.</li>
 *     <li>Supports local and remote WebDriver execution based on environment
 *         configuration.</li>
 *     <li>Provides thread-safe WebDriver and ExtentTest instances using thread-local
 *         storage.</li>
 *     <li>Supports integration with ExtentReports for logging test results.</li>
 *     <li>Handles initialization and closing of WebDriver instances in multi-threaded
 *         test environments.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions from the {@link ExceptionHub} class are thrown for invalid
 *       data and environmental configurations.</li>
 *   <li>Detailed logging is provided for the initialization and closure of WebDriver
 *       instances.</li>
 *   <li>Malformed URL errors are caught and logged when creating remote WebDriver
 *       instances.</li>
 * </ul>
 *
 * <p>Note:
 * This class assumes proper WebDriver setup and configuration. The users must ensure
 * that the WebDriver executable and environment configurations are correctly set up
 * before invoking the methods. Additionally, WebDriver initialization and termination
 * must be managed separately using the provided methods.
 *
 * <p>Example:
 * <pre>
 * {@code
 * DriverFactory DriverFactory = DriverFactory.getInstance();
 * DriverFactory.initializeDriver();
 * WebDriver driver = DriverFactory.getDriver();
 * DriverFactory.closeDriver();
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.1
 */
public class DriverFactory extends BrowserFactory {

    // Logger instance for the DriverFactory class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    // Instance of ChromeOptions to configure Chrome-specific WebDriver options
    private ChromeOptions gcOptions;

    // Instance of FirefoxOptions to configure Firefox-specific WebDriver options
    private FirefoxOptions ffOptions;

    // Instance of EdgeOptions to configure Microsoft Edge-specific WebDriver options
    private EdgeOptions meOptions;

    // ThreadLocal variable to store WebDriver instance specific to the current thread (for multithreaded execution)
    //private static final ThreadLocal<WebDriver> driverLocal = new ThreadLocal<>();

    // Instance of RunFactory to manage and retrieve run configurations
    private final RunFactory runFactory;

    private WebDriver driver;

    // Static instance of ExcelReader to read data from Excel files for test execution
    //public static ExcelReader excelReader;


    /**
     * Constructs a DriverFactory instance and initializes the EnvironmentManager
     * and ExcelReader.
     * <p>
     * This constructor sets up the EnvironmentManager to manage environment configurations
     * and initializes the ExcelReader to read test data from an Excel file located at the
     * specified path in the TestConstants class.
     * </p>
     *
     */
    public DriverFactory() {
        this.runFactory = new RunFactory();
        //excelReader = new ExcelReader(CWD + EXCEL_FILE_PATH);
    }

    /**
     * Singleton instance holder for the DriverFactory class.
     * <p>
     * This inner static class is used to implement the Singleton design pattern
     * in a thread-safe manner. The DriverFactory instance is created lazily when
     * the `getInstance()` method is called for the first time.
     * </p>
     */
    private static final class InstanceHolder {
        private static final DriverFactory instance = new DriverFactory();
    }

    /**
     * Retrieves the singleton instance of the DriverFactory class.
     * <p>
     * This method provides a thread-safe way to access the single instance of
     * DriverFactory, ensuring that only one instance exists throughout the
     * application lifecycle.
     * </p>
     *
     * @return The singleton instance of DriverFactory.
     */
    public static DriverFactory getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * Sets the WebDriver instance for the current thread.
     * <p>
     * This method stores the WebDriver instance in a thread-local variable
     * to ensure that each thread gets its own instance of WebDriver,
     * avoiding concurrency issues in a multi-threaded test environment.
     * </p>
     *
     * @param driver The WebDriver instance to be set for the current thread.
     */
     public void setDriver(WebDriver driver) {
//        driverLocal.set(driver);
         this.driver = driver;

    }

    /**
     * Retrieves the WebDriver instance for the current thread.
     * <p>
     * This method fetches the WebDriver instance stored in the thread-local
     * variable for the current thread. Each thread maintains its own
     * WebDriver instance, ensuring thread safety.
     * </p>
     *
     * @return The WebDriver instance for the current thread.
     */
     public WebDriver getDriver() {
        return driver;
    }

    /**
     * Initializes the WebDriver by creating a new driver instance and setting it for
     * the current thread.
     * <p>
     * This method initializes the WebDriver instance based on the environment type
     * (local or remote) and opens the application URL. It also creates a new TestListener
     * and a BasePage instance.
     * </p>
     *
     * @throws RuntimeException If any error occurs while creating the WebDriver instance.
     */
    public WebDriver initializeDriver() {
        WebDriver driver = createDriver();
        setDriver(driver);
        return driver;
    }

    /**
     * Creates a WebDriver instance based on the environment type (local or remote).
     * <p>
     * This method determines whether to create a local or remote WebDriver instance
     * based on the environment type (LOCAL or REMOTE) and calls the appropriate
     * method to create the driver.
     * </p>
     *
     * @return The created WebDriver instance (either local or remote).
     * @throws ExceptionHub.InvalidDataException If the environment type is not
     *                                           recognized.
     */
    private WebDriver createDriver() {
        return switch (runFactory.getTestRunType()) {
            case LOCAL -> createLocalDriver();
            case REMOTE -> createRemoteDriver();
            default -> throw new ExceptionHub.InvalidDataException(runFactory.getRunType());
        };
    }

    /**
     * Creates a local WebDriver instance based on the specified browser type.
     * <p>
     * This method initializes and returns a WebDriver for local execution based
     * on the browser type. It handles Chrome, Firefox, and Edge browsers by
     * setting appropriate options and creating the respective driver instances.
     * </p>
     *
     * @return A WebDriver instance for the specified browser type (Chrome, Firefox,
     * or Edge).
     * @throws ExceptionHub.InvalidDataException If the browser type is not recognized.
     */
    private WebDriver createLocalDriver() {
        return switch (getBrowserType()) {
            case CHROME -> {
                log.info("Initializing Chrome driver for local execution.");
                gcOptions = new ChromeOptions();
                if(getSystemProperty(BROWSER_PRIVATE_MODE).equalsIgnoreCase(BROWSER_PRIVATE_MODE_YES)){
                    gcOptions.addArguments(CHROME_INCOGNITO);
                }
                gcOptions.addArguments(BROWSER_MAXIMIZE);
                yield new ChromeDriver(gcOptions);
            }
            case FIREFOX -> {
                log.info("Initializing Firefox driver for local execution.");
                ffOptions = new FirefoxOptions();
                if(getSystemProperty(BROWSER_PRIVATE_MODE).equalsIgnoreCase(BROWSER_PRIVATE_MODE_YES)){
                    ffOptions.addArguments(FIREFOX_PRIVATE);
                }
                ffOptions.addArguments(BROWSER_MAXIMIZE);
                yield new FirefoxDriver(ffOptions);
            }
            case EDGE -> {
                log.info("Initializing Edge driver for local execution.");
                meOptions = new EdgeOptions();
                if(getSystemProperty(BROWSER_PRIVATE_MODE).equalsIgnoreCase(BROWSER_PRIVATE_MODE_YES)){
                    meOptions.addArguments(EDGE_PRIVATE);
                }
                meOptions.addArguments(EDGE_BROWSER_MAXIMIZE);
                yield new EdgeDriver(meOptions);
            }
            default -> throw new ExceptionHub.InvalidDataException(getBrowserType().toString());
        };
    }

    /**
     * Creates a remote WebDriver instance based on the specified browser type.
     * <p>
     * This method initializes and returns a remote WebDriver for remote execution
     * based on the browser type. It sets the required capabilities and connects
     * to the remote WebDriver server using the remote URL.
     * </p>
     *
     * @return A RemoteWebDriver instance for the specified browser type (Chrome, Firefox,
     * or Edge).
     * @throws RuntimeException If a MalformedURLException is encountered while connecting
     *                          to the remote WebDriver.
     * @throws ExceptionHub.InvalidDataException If the browser type is not recognized.
     */
    private WebDriver createRemoteDriver() {
        try {
            return switch (getBrowserType()) {
                case CHROME -> {
                    log.info("Initializing Chrome driver for remote execution.");
                    gcOptions = new ChromeOptions();
                    gcOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
                    gcOptions.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME.getBrowserType().toLowerCase());
                    if(getSystemProperty(BROWSER_PRIVATE_MODE).equalsIgnoreCase(BROWSER_PRIVATE_MODE_YES)){
                        gcOptions.addArguments(CHROME_INCOGNITO);
                    }
                    gcOptions.addArguments(BROWSER_MAXIMIZE);
                    yield new RemoteWebDriver((URI.create(fetchDataFromPropFile(GRID_URL)).toURL()), gcOptions);
                }
                case FIREFOX -> {
                    log.info("Initializing Firefox driver for remote execution.");
                    ffOptions = new FirefoxOptions();
                    ffOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
                    ffOptions.setCapability(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX.getBrowserType().toLowerCase());
                    if(getSystemProperty(BROWSER_PRIVATE_MODE).equalsIgnoreCase(BROWSER_PRIVATE_MODE_YES)){
                        ffOptions.addArguments(FIREFOX_PRIVATE);
                    }
                    yield new RemoteWebDriver(URI.create(fetchDataFromPropFile(GRID_URL)).toURL(), ffOptions);
                }
                case EDGE -> {
                    log.info("Initializing Edge driver for remote execution.");
                    meOptions = new EdgeOptions();
                    meOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
                    meOptions.setCapability(CapabilityType.BROWSER_NAME, BrowserType.EDGE.getBrowserType());
                    if(getSystemProperty(BROWSER_PRIVATE_MODE).equalsIgnoreCase(BROWSER_PRIVATE_MODE_YES)){
                        meOptions.addArguments(EDGE_PRIVATE);
                    }
                    meOptions.addArguments(EDGE_BROWSER_MAXIMIZE);
                    yield new RemoteWebDriver(URI.create(fetchDataFromPropFile(GRID_URL)).toURL(), meOptions);
                }
                default -> throw new ExceptionHub.InvalidDataException(getBrowserType().toString());
            };
        } catch (MalformedURLException ex) {
            log.error("Malformed URL for Remote WebDriver: {}", ex.getMessage());
            throw new RuntimeException("Invalid URL for Remote WebDriver.", ex);
        }
    }

    /**
     * Closes the currently active WebDriver and removes it from the thread-local
     * storage.
     * <p>
     * This method safely quits the WebDriver instance if it's active, ensuring that
     * the browser is closed properly. It also removes the WebDriver instance from the
     * thread-local storage to avoid memory leaks and maintain proper resource management.
     * </p>
     */
//    public void closeDriver() {
//        if (getDriver() != null) {
//            getDriver().quit();
//            driverLocal.remove();
//        }
//    }

    /**
     * Retrieves the system property value for the given key.
     * <p>
     * This method fetches the value of the system property specified by the given key.
     * If the property is not found, it returns a default value ("default_value").
     * </p>
     *
     * @param key The name of the system property to retrieve.
     * @return The value of the system property, or "default_value" if the property is
     * not found.
     */
    public static String getSystemProperty(String key) {
        return System.getProperty(key, "default_value");
    }

}

