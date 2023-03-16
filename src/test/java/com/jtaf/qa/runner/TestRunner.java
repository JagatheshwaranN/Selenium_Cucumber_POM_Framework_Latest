package com.jtaf.qa.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * 
 * @author Jaga
 *
 */

@CucumberOptions(features = "src/test/resources/features/", tags = "@entertraveldetails", glue = { "com/jtaf/qa/steps",
		"com/jtaf/qa/test", "com/jtaf/qa/pages", "com/jtaf/qa/base" }, plugin = { "pretty",
				"json:target/cucumber/report/report.json",
				"junit:target/cucumber/report/report.xml" }, publish = true, monochrome = true)
public class TestRunner extends AbstractTestNGCucumberTests {

}
