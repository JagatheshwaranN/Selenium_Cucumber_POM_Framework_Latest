package com.jtaf.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.testng.Assert;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.jtaf.qa.base.BaseTest;
import com.jtaf.qa.utilities.Constants;
import com.jtaf.qa.utilities.LoggerUtility;

//import com.jtaf.qa.helpers.ReusableHelper;
//import com.jtaf.qa.utilities.ExtentUtility;
//import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.Status;

/**
 * 
 * @author Jaga
 *
 */
public class ReportListener implements ITestListener, ISuiteListener {

	private static Logger log = LoggerUtility.getLog(ReportListener.class);
	private String snapshotCategory = null;

	@Override
	public void onFinish(ITestContext context) {
		try {
			// OutDated
			// test.log(Status.INFO, "The " + context.getName() + " Execution Finished");
			// report.flush();
			Reporter.log("The " + context.getName() + " Execution Finished");

		} catch (Exception ex) {
			log.info("Exception occured while finish of the TEST" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void onStart(ITestContext context) {
		try {
			// OutDated
			// report = ExtentUtility.getInstance();
			// test = report.createTest(context.getName());
			// test.log(Status.INFO, "The " + context.getName() + " Execution Started");
			Reporter.log("The " + context.getName() + " Execution started");
		} catch (Exception ex) {
			log.info("Exception occured while start of the TEST" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		try {
			// OutDated
			// test.log(Status.SKIP,
			// "The " + result.getMethod().getMethodName() + " Test Skipped" + "\n" +
			// result.getThrowable());
			Reporter.log("The " + result.getMethod().getMethodName() + " Test Skipped" + "\n" + result.getThrowable());
		} catch (Exception ex) {
			log.info("Exception occured while test skip of the test" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void onTestStart(ITestResult testResult) {
		try {
			// OutDated
			// test.log(Status.INFO, "The " + testResult.getName() + " Test Started");
			Reporter.log("The " + testResult.getMethod().getMethodName() + " Test Started");
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
	public void onTestFailure(ITestResult result) {
		snapshotCategory = "failure/";
		try {
			if (!result.isSuccess()) {
				// Extent Report Code - OutDated
				// System.setProperty("org.uncommons.reportng.escape-output", "false");
				// ReusableHelper.waitForSomeTime();
				// String failTestCaseBase64Snapshot = ((TakesScreenshot) BaseTest.getDriver())
				// .getScreenshotAs(OutputType.BASE64);
				// test.fail("The " + result.getName().toUpperCase() + " Test Failed",
				// MediaEntityBuilder.createScreenCaptureFromBase64String(failTestCaseBase64Snapshot).build());
				// test.log(Status.FAIL, result.getThrowable());

				// ReportNG Report Code - NOT WORKING
				String screenToAttach = captureSnapShot(snapshotCategory);
				Reporter.log("<br>");
				Reporter.log("The " + result.getMethod().getMethodName() + " Test Failed..!!" + "\n"
						+ result.getThrowable());
				Reporter.log("<br>");
				Reporter.log("<a target='_blank' href='" + screenToAttach + "'><img src= '" + screenToAttach
						+ "' height='100' width='100' /></a>");
				log.info("The test failure screenshot is captured to attach in report and testNG report");
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
	public void onTestSuccess(ITestResult result) {
		snapshotCategory = "success/";
		try {
			if (result.isSuccess()) {
				// Extent Report Code - OutDated
				// System.setProperty("org.uncommons.reportng.escape-output", "false");
				// ReusableHelper.waitForSomeTime();
				// String passTestCaseBase64Snapshot = ((TakesScreenshot) BaseTest.getDriver())
				// .getScreenshotAs(OutputType.BASE64);
				// test.pass("The " + result.getName().toUpperCase() + " Test Passed",
				// MediaEntityBuilder.createScreenCaptureFromBase64String(passTestCaseBase64Snapshot).build());

				// ReportNG Report Code - NOT WORKING
				String screenToAttach = captureSnapShot(snapshotCategory);
				Reporter.log("<br>");
				Reporter.log("The " + result.getMethod().getMethodName() + " Test Passed..!!");
				Reporter.log("<br>");
				Reporter.log("<a target='_blank' href='" + screenToAttach + "'><img src= '" + screenToAttach
						+ "' height='100' width='100' /></a>");
				log.info("The test success screenshot is captured to attach in report and testNG report");
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

	public void onStart(ISuite suite) {
		// not implemented
	}

	public void onFinish(ISuite suite) {

//		EmailTriggerUtil emailTriggerUtil = new EmailTriggerUtil();
//		try {
//			String[] reportPath = getDataFromPropFile("automationReportPath").split("\\$");
//			messageBody = reportPath[0] + InetAddress.getLocalHost().getHostAddress() + reportPath[1];
//			emailTriggerUtil.sendEmail(EmailConfig.mailServer, EmailConfig.from, EmailConfig.to, EmailConfig.subject,
//					messageBody);
//		} catch (UnknownHostException ex) {
//			ex.printStackTrace();
//		}
	}

	public String captureSnapShot(String snapshotCategory) {
		var calendar = Calendar.getInstance();
		var simpleDateFormat = new SimpleDateFormat("dd_MM_YYYY_hh_mm_ss");
		var source = ((TakesScreenshot) BaseTest.getDriver()).getScreenshotAs(OutputType.FILE);
		var destination = new File(System.getProperty("user.dir") + Constants.SNAPSHOT_PATH + snapshotCategory
				+ simpleDateFormat.format(calendar.getTime()) + Constants.SNAPSHOT_TYPE);
		try {
			FileUtils.copyFile(source, destination);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return destination.getAbsolutePath();
	}
}
