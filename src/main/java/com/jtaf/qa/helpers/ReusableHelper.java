package com.jtaf.qa.helpers;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.jtaf.qa.utilities.LoggerUtility;
import com.jtaf.qa.base.WebPage;
import com.jtaf.qa.base.BasePage;

/**
 * 
 * @author Jaga
 *
 */
public class ReusableHelper extends BasePage implements WebPage {

	public static HashMap<String, String> anyObject;
	List<String> list = null;
	public static String objValue;
	public static String objKey;

	private static Logger log = LoggerUtility.getLog(ReusableHelper.class);

	public ReusableHelper(WebDriver driver) {
		super(driver);
	}

	public static void setAnyElement(String key, String value) {
		try {
			if (anyObject == null) {
				anyObject = new HashMap<String, String>();
			}
			objKey = key;
			objValue = value;
			anyObject.put(objKey, objValue);
			log.info("The stored object : " + anyObject);
		} catch (Exception ex) {
			log.info("Error occured while setting an object value" + "\n" + ex);
		}
	}

	public static HashMap<String, String> getAnyElement() {
		return anyObject;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	@Override
	public void elementClick(WebElement element, String elementName) {
		try {
			if (element.isEnabled()) {
				element.click();
				log.info("The element " + elementName + " is clicked");
			}
		} catch (Exception ex) {
			log.info("Error occured while click on an " + elementName + " element" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void elementClick(By locator, String value, String elementName) {
		String locatorToString = null;
		String updatedLocatorToString = null;
		WebElement element;
		try {
			locatorToString = locator.toString();
			if (locatorToString.contains("@1@")) {
				updatedLocatorToString = locatorToString.replaceAll("@1@", value);
			}
			updatedLocatorToString = updatedLocatorToString.split(":")[1].trim();
			log.info("The updated locator string with the value : " + updatedLocatorToString);
			element = getElement(updatedLocatorToString);
			element.click();
		} catch (Exception ex) {
			log.info("Error occured while click on an " + elementName + " element" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void enterText(WebElement element, String text, String elementName) {
		try {
			if (element.isEnabled()) {
				element.sendKeys(text);
				log.info("The text " + text + " is entered into an " + elementName + " element");
			}
		} catch (Exception ex) {
			log.info("Error occured while enter text into an " + elementName + " element" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void elementClear(WebElement element, String elementName) {
		try {
			if (element.isEnabled()) {
				element.clear();
				log.info("The element " + elementName + " is cleared");
			}
		} catch (Exception ex) {
			log.info("Error occured while clear on an " + elementName + " element" + "\n" + ex);
			Assert.fail();
		}
	}

}
