package com.qa.ctf.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * 
 * @author Jaga
 * @version 1.1
 *
 */
public class TestRetry implements IRetryAnalyzer {

	private int retryCount = 0;

    private static final Logger log = LogManager.getLogger(TestRetry.class);

	@Override
	public boolean retry(ITestResult test) {
		try {
            int maxRetryCount = 2;
            if (retryCount < maxRetryCount) {
                log.info("Retrying Test {} with status {} for the {} time.", test.getName(), getResultStatusName(test.getStatus()), retryCount + 1);
				retryCount++;
				return true;
			}
		} catch (Exception ex) {
            log.info("Error occurred while implement retry failed test case logic: {}", ex.getMessage());
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
            log.info("Error occurred while get the test case result: {}", ex.getMessage());
			Assert.fail();
		}
		return resultName;
	}

}
