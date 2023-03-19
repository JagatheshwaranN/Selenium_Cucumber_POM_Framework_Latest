package com.jtaf.qa.utilities;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * 
 * @author Jaga
 *
 */
public class ExtentUtility {

	private static ExtentSparkReporter extentSparkReporter;
	private static ExtentReports extentReports;

	private static Logger log = LoggerUtility.getLog(ExtentUtility.class);

	public static ExtentReports getInstance() {

		if (extentSparkReporter == null) {
			extentSparkReporter = new ExtentSparkReporter(
					System.getProperty("user.dir") + Constants.EXTENT_REPORT_PATH);
			try {
				extentSparkReporter
						.loadXMLConfig(new File(System.getProperty("user.dir") + Constants.EXTENT_CONFIG_PATH));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			extentReports = new ExtentReports();
			extentReports.attachReporter(extentSparkReporter);
			log.info("Extent Report instance is created successfully");
		}
		return extentReports;
	}
}
