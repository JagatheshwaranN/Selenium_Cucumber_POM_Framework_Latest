package com.jtaf.qa.base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Jaga
 *
 */
public abstract class Page {

	private WebDriver driver;
	public WebDriverWait wait;

	public Page(WebDriver driver) {
		this.setDriver(driver);
		this.wait = new WebDriverWait(this.getDriver(), Duration.ofSeconds(15));
	}

	public abstract String getPageTitle();

	public abstract String getPageHeader(WebElement element);

	public abstract WebElement getElement(By locator);

	public abstract WebElement getElement(String locator);

	public abstract List<WebElement> getElements(By locator);

	public abstract void waitForElementPresent(WebElement element);

	public abstract void waitForElementPresent(String locator);

	public abstract void waitForElementVisible(WebElement element);

	public abstract void waitForPageTitle(String title);

	public <TPage extends BasePage> TPage getInstance(Class<TPage> pageClass) {
		try {
			return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(this.getDriver());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

}
