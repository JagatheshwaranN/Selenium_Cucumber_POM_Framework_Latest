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
					System.getProperty("user.dir") + "//target//report//ExtentSpark.html");
			try {
				extentSparkReporter.loadXMLConfig(
						new File(System.getProperty("user.dir") + "//src//test//resources//extent//report-config.xml"));
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
