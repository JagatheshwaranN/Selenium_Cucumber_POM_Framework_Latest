package com.jtaf.qa.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.jtaf.qa.utilities.LoggerUtility;

/**
 * @author Jaga
 *
 */
public class BasePage extends Page {

	private static Logger log = LoggerUtility.getLog(BasePage.class);

	public BasePage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String getPageTitle() {
		return getDriver().getTitle();
	}

	@Override
	public String getPageHeader(WebElement element) {
		return element.getText();

	}

	@Override
	public void waitForElementVisible(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception ex) {
			log.info("Error occured while wait for an element : " + element.toString() + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void waitForPageTitle(String title) {
		try {
			wait.until(ExpectedConditions.titleContains(title));
		} catch (Exception ex) {
			log.info("Error occured while wait for the page title : " + title + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			element = getDriver().findElement(locator);
		} catch (Exception ex) {
			log.info("Error occured while construct of and element : " + locator.toString() + "\n" + ex);
			Assert.fail();
		}
		return element;
	}

	@Override
	public WebElement getElement(String locator) {
		WebElement element = null;
		try {
			element = getDriver().findElement(By.xpath(locator));
		} catch (Exception ex) {
			log.info("Error occured while construct of and element : " + locator.toString() + "\n" + ex);
			Assert.fail();
		}
		return element;
	}

}
