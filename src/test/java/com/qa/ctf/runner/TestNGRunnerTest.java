package com.qa.ctf.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/",
        tags = "@reg",
        glue = {"com/qa/ctf"},
        plugin = {"pretty",
                "html:target/cucumber/report/cucumber.html",
                "json:target/cucumber/report/report.json",
                "junit:target/cucumber/report/report.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        publish = true,
        monochrome = true)
public class TestNGRunnerTest extends AbstractTestNGCucumberTests {

    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
