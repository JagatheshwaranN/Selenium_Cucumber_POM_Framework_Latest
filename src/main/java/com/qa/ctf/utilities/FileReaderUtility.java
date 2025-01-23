package com.qa.ctf.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

/***
 * 
 * @author Jaga
 *
 */
public class FileReaderUtility {

	private static Properties properties;
	private static File file;
	private static FileInputStream fileInputStream;

	private static final Logger log = LogManager.getLogger(FileReaderUtility.class.getName());

	public void loadPropertyFile() throws IOException {
		try {
			properties = new Properties();
			file = new File(System.getProperty("user.dir") + Constants.TEST_CONFIG_FILE_PATH);
			try {
				fileInputStream = new FileInputStream(file);
			} catch (FileNotFoundException ex) {
				log.info("======================== [ Property file " + Constants.TEST_CONFIG_FILE_NAME
						+ " not found ] ========================");
				ex.printStackTrace();
			}
			try {
				properties.load(fileInputStream);
			} catch (IOException ex) {
				log.info("Error occured while load the property file" + "\n" + ex);
				Assert.fail();
			}

		} finally {
			fileInputStream.close();
		}
	}

	public static String getTestData(String property) {
		String dataFromPropFile = null;
		try {
			dataFromPropFile = properties.getProperty(property).trim();
		} catch (Exception ex) {
			log.info("Error occured while read data from property file" + "\n" + ex);
			Assert.fail();
		}
		return dataFromPropFile;
	}
}