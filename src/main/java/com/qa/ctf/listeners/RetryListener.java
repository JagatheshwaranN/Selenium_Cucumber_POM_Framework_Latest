//package com.qa.ctf.listeners;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Method;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.testng.Assert;
//import org.testng.IAnnotationTransformer;
//import org.testng.IRetryAnalyzer;
//import org.testng.annotations.ITestAnnotation;
//
//import com.qa.ctf.utilities.RetryUtility;
//
///**
// *
// * @author Jaga
// *
// */
//public class RetryListener implements IAnnotationTransformer {
//
//	private static final Logger log = LogManager.getLogger(RetryListener.class.getName());
//
//	@SuppressWarnings("rawtypes")
//	@Override
//	public void transform(ITestAnnotation testAnnotation, Class clas, Constructor constructor, Method method) {
//		try {
//			Class<? extends IRetryAnalyzer> retry = testAnnotation.getRetryAnalyzerClass();
//			if (retry == null) {
//				testAnnotation.setRetryAnalyzer(RetryUtility.class);
//			}
//		} catch (Exception ex) {
//			log.info("Exception occured while setting up retry analyzer" + "\n" + ex);
//			Assert.fail();
//		}
//	}
//}
