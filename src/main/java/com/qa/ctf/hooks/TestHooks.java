package com.qa.ctf.hooks;

import com.qa.ctf.context.TestContext;
import com.qa.ctf.factories.DriverFactory;
import com.qa.ctf.factories.DriverFactory1;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class TestHooks {

    private WebDriver driver;
    private final TestContext appContext;

    public TestHooks(TestContext appContext) {
        this.appContext = appContext;
    }

    @Before
    public void before(Scenario scenario) {
        System.out.println("BEFORE THREAD ID: "+Thread.currentThread().threadId());
        System.out.println("BEFORE SCENARIO NAME: "+scenario.getName());
        driver = DriverFactory.getInstance().initializeDriver();
        appContext.driver = driver;
    }

    @After
    public void after(Scenario scenario) {
        System.out.println("AFTER THREAD ID: "+Thread.currentThread().threadId());
        System.out.println("AFTER SCENARIO NAME: "+scenario.getName());
        driver.quit();
    }
}
