package com.jtaf.qa.listeners;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.jtaf.qa.base.BaseTest;
import com.jtaf.qa.utilities.ExtentUtility;
import com.jtaf.qa.utilities.LoggerUtility;

/**
 * 
 * @author Jaga
 *
 */
public class ExtentListener implements ITestListener {

	public static ExtentReports extent;
	public static ExtentTest test;
	Calendar calendar;
	SimpleDateFormat simpleDateFormat;
	String methodName;
	String reportDirectory;
	File src;
	File dest;

	private static Logger log = LoggerUtility.getLog(ExtentListener.class);

	@Override
	public void onFinish(ITestContext testContext) {
		try {
			extent.flush();
			Reporter.log("The " + testContext.getName() + " test is finished");
		} catch (Exception ex) {
			log.info("Exception occured while finish of the TEST" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void onStart(ITestContext testContext) {
		try {
			extent = ExtentUtility.getInstance();
			test = extent.createTest(testContext.getName());
			Reporter.log("The " + testContext.getName() + " test is started");
		} catch (Exception ex) {
			log.info("Exception occured while start of the TEST" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
		try {
			test.log(Status.SKIP, "The " + testResult.getThrowable() + " execution is skipped");
			Reporter.log(
					"The " + testResult.getMethod().getMethodName() + " test skipped " + testResult.getThrowable());
		} catch (Exception ex) {
			log.info("Exception occured while test skip of the test" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void onTestStart(ITestResult testResult) {
		try {
			test.log(Status.INFO, "The " + testResult.getName() + " execution is started");
			Reporter.log("The " + testResult.getMethod().getMethodName() + " test is started");
		} catch (Exception ex) {
			log.info("Exception occured while test start" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailure(ITestResult testResult) {
		try {
			test.log(Status.FAIL, testResult.getThrowable());
			Reporter.log(
					"The " + testResult.getMethod().getMethodName() + " test is failed " + testResult.getThrowable());
			if (!testResult.isSuccess()) {
				calendar = Calendar.getInstance();
				simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
				methodName = testResult.getName();
				src = ((TakesScreenshot) BaseTest.getDriver()).getScreenshotAs(OutputType.FILE);
				reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/test/resources/";
				dest = new File((String) reportDirectory + "/screenshots/failure/" + methodName + "_"
						+ simpleDateFormat.format(calendar.getTime()) + ".png");
				FileUtils.copyFile(src, dest);
				test.addScreenCaptureFromPath(dest.getAbsolutePath());
				Reporter.log("<a href='" + dest.getAbsolutePath() + "'> <img src='" + dest.getAbsolutePath()
						+ "' height='100' width='100'/></a>");
				log.info("The test failure screenshot is captured to attach in extent and testNG report");
			}
		} catch (Exception ex) {
			log.info("Exception occured while capture screenshot on test failure" + "\n" + ex);
			Assert.fail();
		} finally {
			if (BaseTest.getDriver() == null) {
				return;
			}
			BaseTest.getDriver().quit();
		}
	}

	@Override
	public void onTestSuccess(ITestResult testResult) {
		try {
			test.log(Status.PASS, testResult.getName() + " passed");

			Reporter.log("The " + testResult.getMethod().getMethodName() + " test passed");
			if (testResult.isSuccess()) {
				calendar = Calendar.getInstance();
				simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
				methodName = testResult.getName();
				src = ((TakesScreenshot) BaseTest.getDriver()).getScreenshotAs(OutputType.FILE);
				reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/test/resources/";
				dest = new File((String) reportDirectory + "/screenshots/success/" + methodName + "_"
						+ simpleDateFormat.format(calendar.getTime()) + ".png");
				FileUtils.copyFile(src, dest);
				test.addScreenCaptureFromPath(dest.getAbsolutePath());
				Reporter.log("<a href='" + dest.getAbsolutePath() + "'> <img src='" + dest.getAbsolutePath()
						+ "' height='100' width='100'/></a>");
				log.info("The test success screenshot is captured to attach in extent and testNG report");
			}
		} catch (Exception ex) {
			log.info("Exception occured while capture screenshot on test success" + "\n" + ex);
			Assert.fail();
		} finally {
			if (BaseTest.getDriver() == null) {
				return;
			}
			BaseTest.getDriver().quit();
		}
	}

}
