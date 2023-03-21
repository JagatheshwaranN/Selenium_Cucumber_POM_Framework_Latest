package com.jtaf.qa.helpers;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.jtaf.qa.base.BasePage;
import com.jtaf.qa.utilities.LoggerUtility;

/**
 * 
 * @author Jaga
 *
 */
public class VerificationHelper extends BasePage {

	public VerificationHelper(WebDriver driver) {
		super(driver);
	}

	private static Logger log = LoggerUtility.getLog(VerificationHelper.class);

	public boolean verifyElementPresent(WebElement element, String elementName) {
		boolean isDisplayed = false;
		try {
			isDisplayed = element.isDisplayed();
			log.info("The element " + elementName + " is present on the page");
		} catch (Exception ex) {
			log.info("Error occured while check for presence of an " + elementName + " element" + "\n" + ex);
			Assert.fail();
		}
		return isDisplayed;
	}

	public boolean verifyTextEquals(WebElement element, String text, String elementName) {
		boolean flag = false;
		try {
			var actualText = element.getText();
			if (actualText.equals(text)) {
				log.info("The element " + elementName + " text and given text is equal");
				return flag = true;
			} else {
				return flag;
			}
		} catch (Exception ex) {
			log.info("Error occured while check an " + elementName + " element text" + "\n" + ex);
			Assert.fail();
		}
		return flag;
	}

	public String readTextValueFromElement(WebElement element, String elementName) {
		boolean displayed = false;
		String text = null;
		try {
			displayed = isDisplayed(element, elementName);
			if (!displayed)
				return null;
			text = element.getText();
			log.info("The element " + elementName + " text is : " + text);
		} catch (Exception ex) {
			log.info("Error occured while read value of an " + elementName + " element" + "\n" + ex);
			Assert.fail();
		}
		return text;
	}

	public String readValueFromInput(WebElement element, String elementName) {
		String value = null;
		try {
			if (!isDisplayed(element, elementName))
				return null;
			value = element.getAttribute("value");
			log.info("The element " + elementName + " text is : " + value);
		} catch (Exception ex) {
			log.info("Error occured while read text of an " + elementName + " element" + "\n" + ex);
			Assert.fail();
		}
		return value;
	}

	public boolean isDisplayed(WebElement element, String elementName) {
		boolean flag = false;
		try {
			element.isDisplayed();
			flag = true;
			log.info("The element " + elementName + " is displayed on the page");
		} catch (Exception ex) {
			log.info("Error occured while check for display of an " + elementName + " element" + "\n" + ex);
			Assert.fail();
		}
		return flag;
	}

}
