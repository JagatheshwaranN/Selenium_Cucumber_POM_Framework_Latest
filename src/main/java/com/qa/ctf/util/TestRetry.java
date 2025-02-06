package com.qa.ctf.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * The {@code TestRetry} class implements the {@link IRetryAnalyzer} interface to
 * provide custom retry logic for failed test cases in TestNG. It allows a test to
 * be retried a specified number of times before it is considered failed.
 *
 * <p>Features:
 * <ul>
 *     <li>Retries failed tests a predefined number of times (configured via {@link
 *     	#MAX_RETRY_COUNT}).</li>
 *     <li>Logs detailed information about retry attempts, including test name, status,
 *     	and attempt count.</li>
 *     <li>Logs errors and exceptions that occur during the retry process.</li>
 * </ul>
 *
 * <p>Usage:
 * This class is typically used in conjunction with a {@link org.testng.IAnnotationTransformer}
 * to modify test annotations and add retry logic automatically to the test methods.
 *
 * @author Jagatheshwaran N
 * @version 1.1
 */
public class TestRetry implements IRetryAnalyzer {

    // Logger instance for the TestRetry class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(TestRetry.class);

    // The number of retry attempts made for a failed test
    private int retryCount = 0;

    // The maximum number of retry attempts before the test is considered failed
    private static final int MAX_RETRY_COUNT = 2;

    /**
     * Determines whether a failed test should be retried.
     * <p>
     * If the retry count is less than {@link #MAX_RETRY_COUNT}, the test will be retried.
     * The method logs each retry attempt with the test name, status, and the current retry
     * count.
     * <p>
     * The retry count is incremented with each retry attempt. Once the retry count exceeds
     * the maximum retry limit, no further retries will be attempted, and the test will fail.
     *
     * @param test The {@link ITestResult} object representing the test result for the current
     *             test.
     * @return {@code true} if the test should be retried, {@code false} if it should not.
     */
    @Override
    public boolean retry(ITestResult test) {
        try {
            if (retryCount < MAX_RETRY_COUNT) {
                log.info("Retrying Test '{}' with status '{}' for the {} time.",
                        test.getName(), getResultStatusName(test.getStatus()), retryCount + 1);
                retryCount++;
                return true;
            }
        } catch (Exception ex) {
            log.error("Error occurred while implementing retry logic for failed test " +
                    "case: {}", ex.getMessage(), ex);
            Assert.fail("Error during retry process: " + ex.getMessage(), ex);
        }
        return false;
    }

    /**
     * Retrieves the status name of a test result based on its status code.
     * <p>
     * This method takes the status code from the test result and returns a string
     * that represents the status: "SUCCESS", "FAILURE", "SKIP", or "UNKNOWN" for
     * any unrecognized status code.
     * <p>
     * If an unrecognized status code is encountered, a warning is logged.
     *
     * @param status The status code of the test result.
     * @return A string representing the status ("SUCCESS", "FAILURE", "SKIP", or
     * "UNKNOWN").
     */
    public String getResultStatusName(int status) {
        String resultName = "UNKNOWN";
        try {
            switch (status) {
                case 1:
                    resultName = "SUCCESS";
                    break;
                case 2:
                    resultName = "FAILURE";
                    break;
                case 3:
                    resultName = "SKIP";
                    break;
                default:
                    log.warn("Unrecognized test status code: {}", status);
            }
        } catch (Exception ex) {
            log.error("Error occurred while determining the test case result: {}", ex.getMessage(), ex);
            Assert.fail("Error during result status determination: " + ex.getMessage(), ex);
        }
        return resultName;
    }

}
