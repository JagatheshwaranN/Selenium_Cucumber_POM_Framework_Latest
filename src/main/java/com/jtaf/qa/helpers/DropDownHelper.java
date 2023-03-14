package com.jtaf.qa.helpers;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.jtaf.qa.base.BaseTest;
import com.jtaf.qa.utilities.LoggerUtility;

/**
 * 
 * @author Jaga
 *
 */
public class DropDownHelper extends BaseTest {

	private static Logger log = LoggerUtility.getLog(DropDownHelper.class);

	public void selectByValue(WebElement element, String value, String elementName) {
		try {
			Select select = new Select(element);
			select.selectByValue(value);
			log.info("The value " + value + " is selected from " + elementName + " dropdown");
		} catch (Exception ex) {
			log.info("Error occured while select option by value from " + elementName + " dropdown" + "\n" + ex);
			Assert.fail();
		}
	}

	public void selectByIndex(WebElement element, int index, String elementName) {
		try {
			Select select = new Select(element);
			select.selectByIndex(index);
			log.info("The value at index " + index + " is selected from " + elementName + " dropdown");
		} catch (Exception ex) {
			log.info("Error occured while select option by index from " + elementName + " dropdown" + "\n" + ex);
			Assert.fail();
		}
	}

	public void selectByVisibleText(WebElement element, String visibleText, String elementName) {
		try {
			Select select = new Select(element);
			select.selectByVisibleText(visibleText);
			log.info("The visible text " + visibleText + " is selected from " + elementName + " dropdown");
		} catch (Exception ex) {
			log.info("Error occured while select option by visible text from " + elementName + " dropdown" + "\n" + ex);
			Assert.fail();
		}
	}

	public String getSelectValue(WebElement element, String elementName) {
		String value = null;
		try {
			value = new Select(element).getFirstSelectedOption().getText();
			log.info("The selected value in " + elementName + " dropdown is : " + value);
		} catch (Exception ex) {
			log.info("Error occured while get selected value from " + elementName + " dropdown" + "\n" + ex);
			Assert.fail();
		}
		return value;
	}

	public List<String> getAllDropDownValue(WebElement element, String elementName) {
		List<String> dropdownvalues = null;
		try {
			Select select = new Select(element);
			List<WebElement> listelements = select.getOptions();
			dropdownvalues = new LinkedList<String>();
			for (WebElement elements : listelements) {
				log.info("The option values of the " + elementName + " dropdown are : " + elements.getText());
				dropdownvalues.add(elements.getText());
			}
		} catch (Exception ex) {
			log.info("Error occured while get the option values from " + elementName + " dropdown" + "\n" + ex);
			Assert.fail();
		}
		return dropdownvalues;
	}

}
