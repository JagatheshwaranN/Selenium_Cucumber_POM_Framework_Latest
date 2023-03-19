package com.jtaf.qa.utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * @author Jaga
 *
 */
public class LoggerUtility {

	private static boolean root = false;

	@SuppressWarnings("rawtypes")
	public static Logger getLog(Class getclass) {
		if (root) {
			return Logger.getLogger(getclass);
		}
		PropertyConfigurator
				.configure(System.getProperty("user.dir") + FileReaderUtility.getTestData("log.config.path"));
		root = true;
		return Logger.getLogger(getclass);

	}

}
