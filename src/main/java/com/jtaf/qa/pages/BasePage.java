package com.jtaf.qa.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.jtaf.qa.base.Page;
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
	public String getPageHeader(By locator) {
		return getElement(locator).getText();

	}

	@Override
	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			waitForElementPresent(locator);
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
			waitForElementPresent(locator);
			element = getDriver().findElement(By.xpath(locator));
		} catch (Exception ex) {
			log.info("Error occured while construct of and element : " + locator.toString() + "\n" + ex);
			Assert.fail();
		}
		return element;
	}

	@Override
	public List<WebElement> getElements(By locator) {
		List<WebElement> elements = null;
		try {
			waitForElementPresent(locator);
			elements = getDriver().findElements(locator);
		} catch (Exception ex) {
			log.info("Error occured while construct of and element : " + locator.toString() + "\n" + ex);
			Assert.fail();
		}
		return elements;
	}

	@Override
	public void waitForElementPresent(By locator) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception ex) {
			log.info("Error occured while wait for an element : " + locator.toString() + "\n" + ex);
			Assert.fail();
		}
	}
	
	@Override
	public void waitForElementPresent(String locator) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
		} catch (Exception ex) {
			log.info("Error occured while wait for an element : " + locator.toString() + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void waitForElementVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception ex) {
			log.info("Error occured while wait for an element : " + locator.toString() + "\n" + ex);
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

}
