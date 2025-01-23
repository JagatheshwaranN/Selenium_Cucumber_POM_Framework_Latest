package com.qa.ctf.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * 
 * @author Jaga
 *
 */
public class RetryUtility implements IRetryAnalyzer {

	private int retryCount = 0;
	private int maxRetryCount = 2;

	private static final Logger log = LogManager.getLogger(RetryUtility.class.getName());

	@Override
	public boolean retry(ITestResult test) {
		try {
			if (retryCount < maxRetryCount) {
				log.info("Retring Test " + test.getName() + " with status " + getResultStatusName(test.getStatus())
						+ " for the " + (retryCount + 1) + " time.");
				retryCount++;
				return true;
			}
		} catch (Exception ex) {
			log.info("Error occured while implement retry failed test case logic" + "\n" + ex);
			Assert.fail();
		}
		return false;
	}

	public String getResultStatusName(int status) {
		String resultName = null;
		try {
			if (status == 1) {
				resultName = "SUCCESS";
			} else if (status == 2) {
				resultName = "FAILURE";
			} else if (status == 3) {
				resultName = "SKIP";
			}
		} catch (Exception ex) {
			log.info("Error occured while get the test case result" + "\n" + ex);
			Assert.fail();
		}
		return resultName;
	}
}
