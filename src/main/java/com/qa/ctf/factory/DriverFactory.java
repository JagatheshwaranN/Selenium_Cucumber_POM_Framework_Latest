package com.qa.ctf.factory;

import java.net.MalformedURLException;
import java.net.URI;

import com.qa.ctf.constant.BrowserType;
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
 * The DriverFactory class provides utility methods for managing WebDriver instances
 * for different browsers (Chrome, Firefox, and Edge), supporting both local and remote
 * execution.
 *
 * <p>Features:
 * <ul>
 *     <li>Initialize WebDriver instances for Chrome, Firefox, and Edge browsers.</li>
 *     <li>Support for both local and remote WebDriver configurations.</li>
 *     <li>Thread-safe singleton pattern to provide a single instance of DriverFactory.</li>
 *     <li>Logging of driver initialization processes for better debugging and traceability.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions from the {@link ExceptionHub} class are thrown for invalid data
 *   or unsupported configurations.</li>
 *   <li>Detailed logging of the driver initialization process, including any errors or
 *   misconfigurations.</li>
 * </ul>
 *
 * <p>Note:
 * The class assumes proper configuration for browser types, WebDriver setup, and system
 * properties.
 * Users must handle WebDriver termination and ensure correct system property values are
 * set for successful execution.
 *
 * <p>Example:
 * <pre>
 * {@code
 * DriverFactory driverFactory = DriverFactory.getInstance();
 * WebDriver driver = driverFactory.initializeDriver();
 * driver.get("https://example.com");
 * driver.quit();
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.2
 */
public class DriverFactory extends BrowserFactory {

    // Logger instance for the DriverFactory class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    // WebDriver instance to interact with web elements on the web pages
    private WebDriver driver;

    // Instance of ChromeOptions to configure Chrome-specific WebDriver options
    private ChromeOptions gcOptions;

    // Instance of FirefoxOptions to configure Firefox-specific WebDriver options
    private FirefoxOptions ffOptions;

    // Instance of EdgeOptions to configure Microsoft Edge-specific WebDriver options
    private EdgeOptions meOptions;

    // Instance of RunFactory to manage and retrieve run configurations
    private final RunFactory runFactory;

    // Static instance of ExcelReader to read data from Excel files for test execution
    //public static ExcelReader excelReader;

    /**
     * Constructs a DriverFactory instance and initializes the RunFactory
     * and ExcelReader.
     * <p>
     * This constructor sets up the RunFactory to manage run type configurations
     * and initializes the ExcelReader to read test data from an Excel file located
     * at the specified path in the TestConstants class.
     * </p>
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
     * Sets the WebDriver instance.
     * <p>
     * This method assigns the provided WebDriver instance to the class-level variable,
     * ensuring to provide access to the WebDriver across different test components.
     * </p>
     *
     * @param driver The WebDriver instance to be set.
     */
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Retrieves the WebDriver instance.
     * <p>
     * This method returns the current WebDriver instance, allowing test components
     * to access the WebDriver.
     * </p>
     *
     * @return The WebDriver instance currently being used.
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Initializes and returns a WebDriver instance.
     * <p>
     * This method creates a new WebDriver instance using the {@link #createDriver()} method,
     * sets it using the {@link #setDriver(WebDriver)} method, and then returns the initialized
     * WebDriver instance for use in test components.
     * </p>
     *
     * @return The newly initialized WebDriver instance.
     */
    public WebDriver initializeDriver() {
        WebDriver driver = createDriver();
        setDriver(driver);
        return driver;
    }

    /**
     * Creates a WebDriver instance based on the run type (local or remote).
     * <p>
     * This method determines whether to create a local or remote WebDriver instance
     * based on the run type (LOCAL or REMOTE) and calls the appropriate
     * method to create the driver.
     * </p>
     *
     * @return The created WebDriver instance (either local or remote).
     * @throws ExceptionHub.InvalidDataException If the run type is not
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
                if(getSystemProperty(BROWSER_PRIVATE_MODE).equalsIgnoreCase(OPTION_YES)){
                    gcOptions.addArguments(CHROME_INCOGNITO);
                }
                gcOptions.addArguments(BROWSER_MAXIMIZE);
                yield new ChromeDriver(gcOptions);
            }
            case FIREFOX -> {
                log.info("Initializing Firefox driver for local execution.");
                ffOptions = new FirefoxOptions();
                if(getSystemProperty(BROWSER_PRIVATE_MODE).equalsIgnoreCase(OPTION_YES)){
                    ffOptions.addArguments(FIREFOX_PRIVATE);
                }
                ffOptions.addArguments(BROWSER_MAXIMIZE);
                yield new FirefoxDriver(ffOptions);
            }
            case EDGE -> {
                log.info("Initializing Edge driver for local execution.");
                meOptions = new EdgeOptions();
                if(getSystemProperty(BROWSER_PRIVATE_MODE).equalsIgnoreCase(OPTION_YES)){
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
                    if(getSystemProperty(BROWSER_PRIVATE_MODE).equalsIgnoreCase(OPTION_YES)){
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
                    if(getSystemProperty(BROWSER_PRIVATE_MODE).equalsIgnoreCase(OPTION_YES)){
                        ffOptions.addArguments(FIREFOX_PRIVATE);
                    }
                    yield new RemoteWebDriver(URI.create(fetchDataFromPropFile(GRID_URL)).toURL(), ffOptions);
                }
                case EDGE -> {
                    log.info("Initializing Edge driver for remote execution.");
                    meOptions = new EdgeOptions();
                    meOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
                    meOptions.setCapability(CapabilityType.BROWSER_NAME, BrowserType.EDGE.getBrowserType());
                    if(getSystemProperty(BROWSER_PRIVATE_MODE).equalsIgnoreCase(OPTION_YES)){
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

