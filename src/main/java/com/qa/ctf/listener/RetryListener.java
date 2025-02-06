package com.qa.ctf.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import com.qa.ctf.util.TestRetry;

/**
 * The RetryListener class implements the TestNG {@link IAnnotationTransformer}
 * interface to provide custom behavior for transforming test annotations during
 * the execution lifecycle.
 *
 * <p>Features:
 * <ul>
 *     <li>Automatically sets a retry analyzer for test methods that do not have
 *     	one configured.</li>
 *     <li>Uses the {@link TestRetry} class as the default retry analyzer to handle
 *     	test retries.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Catches exceptions during the retry analyzer setup and logs an error
 *   	message.</li>
 *   <li>Fails the test if there is an error while setting the retry analyzer.</li>
 * </ul>
 *
 * <p>Note:
 * The class assumes that the {@link TestRetry} class is properly implemented and
 * configured to handle retry logic.
 *
 * <p>Example Usage:
 * <pre>
 * {@code
 * public class TestExecution {
 *     public void setup() {
 *         RetryListener listener = new RetryListener();
 *         TestNG.addListener(listener);
 *     }
 * }
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.0
 */
public class RetryListener implements IAnnotationTransformer {

    // Logger instance for the RetryListener class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(RetryListener.class);

    /**
     * Transforms the test annotation to set a retry analyzer for test methods that do not
     * have one.
     *
     * <p>This method is called by TestNG during the test execution lifecycle to modify the
     * test annotations. It checks if the retry analyzer is set for the test method, and if
     * not, it assigns the {@link TestRetry} class as the retry analyzer.
     *
     * <p>Exception Handling:
     * <ul>
     *   <li>Logs an error message if an exception occurs while setting the retry analyzer.</li>
     *   <li>Fails the test in case of any failure during the transformation process.</li>
     * </ul>
     *
     * @param testAnnotation The {@link ITestAnnotation} for the test method being transformed.
     * @param clas           The class that contains the test method.
     * @param constructor    The constructor of the test class.
     * @param method         The test method being transformed.
     */
    @Override
    public void transform(ITestAnnotation testAnnotation, Class clas, Constructor constructor,
                          Method method) {
        try {
            // Retrieve the retry analyzer class already associated with the test method
            Class<? extends IRetryAnalyzer> retry = testAnnotation.getRetryAnalyzerClass();

            // If no retry analyzer is set, assign the default retry analyzer
            if (retry == null) {
                testAnnotation.setRetryAnalyzer(TestRetry.class);
                log.info("Assigned default retry analyzer {} to test method: {}",
                        TestRetry.class.getName(), method.getName());
            }
        } catch (Exception ex) {
            // Log the full exception for better traceability
            log.error("Exception occurred while setting up retry analyzer for method: {}." +
                    " Error: {}", method.getName(), ex.getMessage(), ex);

            // Fail the test with an informative message
            Assert.fail("Failed to set up retry analyzer for method: " +
                    method.getName(), ex);
        }
    }


}
