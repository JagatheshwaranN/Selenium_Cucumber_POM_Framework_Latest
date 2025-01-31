package com.qa.ctf.hook;

import com.qa.ctf.context.TestContext;
import com.qa.ctf.factory.DriverFactory;
import com.qa.ctf.util.FileReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestHooks {

    // Logger instance for the TestHooks class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(TestHooks.class);

    private WebDriver driver;

    private final TestContext testContext;

    public TestHooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void before(Scenario scenario) {
        log.info("BEFORE THREAD ID: {}", Thread.currentThread().threadId());
        log.info("BEFORE SCENARIO NAME: {}", scenario.getName());
        FileReader.loadPropertyFile();
        driver = DriverFactory.getInstance().initializeDriver();
        this.testContext.driver = driver;
    }

    @After
    public void after(Scenario scenario) {
        log.info("AFTER THREAD ID: {}", Thread.currentThread().threadId());
        log.info("AFTER SCENARIO NAME: {}", scenario.getName());
        if(driver != null) {
            driver.quit();
        }
        try {
            if (scenario.isFailed()) {
                log.info("FAILED =====>> {}", scenario.getName());
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
            } else {
                log.info("PASSED =====>> {}", scenario.getName());
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
            }
        } catch (Exception ex) {
            log.info("Error occurred while capture the screenshot : {}", ex.getMessage());
        }
    }

}
