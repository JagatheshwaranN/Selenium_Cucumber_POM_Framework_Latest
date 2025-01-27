package com.qa.ctf.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * This Class is OutDated !!
 * @author Jaga
 *
 */
public class ExtentUtility {

	private static ExtentSparkReporter extentSparkReporter;
	private static ExtentReports extentReports;

	private static final Logger log = LogManager.getLogger(ExtentUtility.class.getName());

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
