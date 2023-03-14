package com.jtaf.qa.utilities;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * 
 * @author Jaga
 *
 */
public class ExtentUtility {

	private static ExtentReports extent;

	private static Logger log = LoggerUtility.getLog(ExtentUtility.class);

	public static ExtentReports getInstance() {
		if (extent == null) {
			return createInstance("test-output/report/extent-report.html");
		} else {
			return extent;
		}
	}

	public static ExtentReports createInstance(String fileName) {
		try {
			ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(fileName);
			extentSparkReporter.config().setTheme(Theme.STANDARD);
			extentSparkReporter.config().setDocumentTitle("Automation Execution Report");
			extentSparkReporter.config().setEncoding("utf-8");
			extentSparkReporter.config().setReportName("Automation Execution Report");
			extent = new ExtentReports();
			extent.attachReporter(extentSparkReporter);
		} catch (Exception ex) {
			log.info("Error occured while prepare extent report" + "\n" + ex);
			Assert.fail();
		}
		return extent;
	}
}
