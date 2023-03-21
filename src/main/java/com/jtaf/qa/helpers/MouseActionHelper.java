package com.jtaf.qa.helpers;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.jtaf.qa.base.BasePage;
import com.jtaf.qa.utilities.LoggerUtility;

/**
 * 
 * @author Jaga
 *
 */
public class MouseActionHelper extends BasePage {

	private static Logger log = LoggerUtility.getLog(MouseActionHelper.class);

	public MouseActionHelper(WebDriver driver) {
		super(driver);
	}

	public void mouseHover(WebElement element1, WebElement element2, String elementName) {
		try {
			var builder = new Actions(getDriver());
			builder.moveToElement(element1).build().perform();
			element2.click();
			log.info("The control is mouse hovered and clicked on an " + elementName + " element");
		} catch (Exception ex) {
			log.info("Error occured while mouse hover and click on " + elementName + " element" + "\n" + ex);
			Assert.fail();
		}
	}
}
