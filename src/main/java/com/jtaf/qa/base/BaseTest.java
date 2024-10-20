package com.jtaf.qa.base;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import com.jtaf.qa.utilities.FileReaderUtility;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * 
 * @author Jaga
 *
 */
@SuppressWarnings("All")
public class BaseTest extends FileReaderUtility {

	private static WebDriver driver;
	private static ChromeOptions options;
	public static Page page;

	// OutDated
	// public static ExtentReports report;
	// public static ExtentTest test;

	private static final Logger log = LogManager.getLogger(BaseTest.class.getName());

	@Before
	public void before() throws IOException {

		try {
			loadPropertyFile();
			log.info("======================== [ Property file is loaded Successfully ] =========================");
			invokeBrowser(FileReaderUtility.getTestData("browser.name"));
		} catch (Exception ex) {
			log.info("Exception occurred while initialization of the TEST" + "\n" + ex);
			Assert.fail();
		}
	}

	public WebDriver invokeBrowser(String browser) {
		try {
			if (System.getProperty("os.name").contains(getTestData("operating.system"))) {
				if (browser.equalsIgnoreCase("Chrome")) {
					log.info("======================== [ Launching " + browser
							+ " Browser] ==============================");
					options = new ChromeOptions();
					options.addArguments("--remote-allow-origins=*");
					setDriver(new ChromeDriver(options));
					getDriver().manage().window().maximize();
				} else if (browser.equalsIgnoreCase("Firefox")) {
					System.setProperty("webdriver.gecko.driver", getTestData("firefox.driver"));
					log.info("======================== [ Launching " + browser
							+ " Browser] ==============================");
					setDriver(new FirefoxDriver());
					getDriver().manage().window().maximize();
				} else {
					log.info("Browser is not defined in the TestConfig file");
					Assert.fail();
				}
				page = new BasePage(getDriver());
				log.info("The browser is launched successfully");
			} else {
				log.info("======================== [ The operating system is not WINDOWS ] ==================");
				Assert.fail();
			}
		} catch (Exception ex) {
			log.info("Exception occurred while invoke the browser" + "\n" + ex);
			Assert.fail();
		}
		return getDriver();
	}

	@After
	public void after(Scenario scenario) {
		try {
			if (scenario.isFailed()) {
				try {
					log.info("FAILED =====>> " + scenario.getName());
					final byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
					scenario.attach(screenshot, "image/jpeg", scenario.getName());
				} catch (Exception ex) {
					log.info("Exception occured while capture screenshot : " + ex);
				}
			} else {
				try {
					log.info("PASSED =====>> " + scenario.getName());
					final byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
					scenario.attach(screenshot, "image/jpeg", scenario.getName());
				} catch (Exception ex) {
					log.info("Exception occured while capture screenshot : " + ex);
				}
			}
		} catch (Exception ex) {
			log.info("Error occurred while TEST close" + "\n" + ex);
		}
	}

	public static void setDriver(WebDriver driver) {
		BaseTest.driver = driver;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public void launchApplication() {
		try {
			getDriver().get(getTestData("app.url"));
			page = new BasePage(getDriver());
			log.info("The MakeMyTrip site is launched successfully");
		} catch (Exception ex) {
			log.info("Error occurred while launch of application" + "\n" + ex);
			Assert.fail();
		}
	}

}
