package com.jtaf.qa.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.jtaf.qa.base.WebPage;
import com.jtaf.qa.base.BasePage;

/**
 * 
 * @author Jaga
 *
 */
public class JavaScriptHelper extends BasePage implements WebPage {

	private static final Logger log = LogManager.getLogger(JavaScriptHelper.class.getName());

	JavascriptExecutor executor;

	public JavaScriptHelper(WebDriver driver) {
		super(driver);
	}

	@Override
	public void elementClick(WebElement element, String elementName) {
		try {
			executor = (JavascriptExecutor) getDriver();
			executor.executeScript("arguments[0].click();", element);
			log.info("The " + elementName + " element is clicked by javascript");
		} catch (Exception ex) {
			log.info("Error occured while click on an " + elementName + " element by javascript" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void elementClick(By locator, String value, String elementName) {
		String locatorToString = null;
		String updatedLocatorToString = null;
		WebElement element;
		try {
			executor = (JavascriptExecutor) getDriver();
			locatorToString = locator.toString();
			if (locatorToString.contains("@1@")) {
				updatedLocatorToString = locatorToString.replaceAll("@1@", value);
			}
			updatedLocatorToString = updatedLocatorToString.split(":")[1].trim();
			log.info("The updated locator string with the value : " + updatedLocatorToString);
			element = getElement(updatedLocatorToString);
			executor.executeScript("arguments[0].click();", element);
			log.info("The " + elementName + " element is clicked by javascript");
		} catch (Exception ex) {
			log.info("Error occured while click on an " + elementName + " element by javascript" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void enterText(WebElement element, String text, String elementName) {
		try {
			executor = (JavascriptExecutor) getDriver();
			executor.executeScript("arguments[0].value='" + text + "';", element);
			log.info("The text " + text + " is entered into an " + elementName + " element by javascript");
		} catch (Exception ex) {
			log.info("Error occured while enter text into an " + elementName + " element by javascript" + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public void elementClear(WebElement element, String elementName) {
		try {
			executor = (JavascriptExecutor) getDriver();
			executor.executeScript("arguments[0].value = '';", element);
		} catch (Exception ex) {
			log.info("Error occured while clear on an " + elementName + " element by javascript" + "\n" + ex);
			Assert.fail();
		}
	}

	public Object executeScript(String script) {
		try {
			log.info("The script is : " + script);
		} catch (Exception ex) {
			log.info("Error occured while execute script by javascript" + "\n" + ex);
			Assert.fail();
		}
		executor = (JavascriptExecutor) getDriver();
		return executor.executeScript(script);

	}

	public Object executeScript(String script, Object... arguments) {
		try {
			log.info("The Script is : " + script);
		} catch (Exception ex) {
			log.info("Error occured while execute script by javascript" + "\n" + ex);
			Assert.fail();
		}
		executor = (JavascriptExecutor) getDriver();
		return executor.executeScript(script, arguments);
	}

	public void scrollToElement(WebElement element) {
		try {
			executeScript("window.scrollTo(arguments[0],arguments[1])", element.getLocation().x,
					element.getLocation().y);
			log.info("The control is scrolled to an element by javascript");
		} catch (Exception ex) {
			log.info("Error occured while scroll to an element by javascript" + "\n" + ex);
			Assert.fail();
		}
	}

	public void scrollToElementAndClick(WebElement element, String elementName) {
		try {
			scrollToElement(element);
			element.click();
			log.info("The control is scrolled to an " + elementName + " element and clicked by javascript");
		} catch (Exception ex) {
			log.info(
					"Error occured while scroll to an " + elementName + " element and click by javascript" + "\n" + ex);
			Assert.fail();
		}
	}

	public void scrollIntoView(WebElement element, String elementName) {
		try {
			executeScript("arguments[0].scrollIntoView()", element);
			log.info("The control is scrolled into the view of an " + elementName + " element by javascript");
		} catch (Exception ex) {
			log.info(
					"Error occured while scroll into view of an " + elementName + " element by javascript" + "\n" + ex);
			Assert.fail();
		}
	}

	public void scrollIntoViewAndClick(WebElement element, String elementName) {
		try {
			scrollIntoView(element, elementName);
			element.click();
			log.info("The control is scrolled into the view of an " + elementName
					+ " element and clicked by javascript");
		} catch (Exception ex) {
			log.info("Error occured while scroll into view of an " + elementName + " element and click by javascript"
					+ "\n" + ex);
			Assert.fail();
		}
	}

	public void scrollUpVertical() {
		try {
			executeScript("window.scrollTo(0, -document.body.scrollHeight)");
			log.info("The page is scrolled up vertically to the top by javscript");
		} catch (Exception ex) {
			log.info("Error occured while page scroll up vertically by javascript" + "\n" + ex);
			Assert.fail();
		}
	}

	public void scrollDownVertical() {
		try {
			executeScript("window.scrollTo(0, document.body.scrollHeight)");
			log.info("The page is scrolled down vertically to the bottom by javascript");
		} catch (Exception ex) {
			log.info("Error occured while page scroll down vertically by javascript" + "\n" + ex);
			Assert.fail();
		}
	}

	public void ScrolUpByPixel(String pixel) {
		try {
			executeScript("window.scrollBy(0, -'" + pixel + "')");
			log.info("The page is scrolled up by pixel by javascript");
		} catch (Exception ex) {
			log.info("Error occured while page scroll up by pixel by javascript" + "\n" + ex);
			Assert.fail();
		}
	}

	public void ScrolDownByPixel(String pixel) {
		try {
			executeScript("window.scrollBy(0, '" + pixel + "')");
			log.info("The page is scrolled down by pixel by javascript");
		} catch (Exception ex) {
			log.info("Error occured while page scroll down by pixel by javascript" + "\n" + ex);
			Assert.fail();
		}
	}

	public void zoomInByPercentage(String percent) {
		try {
			executeScript("document.body.style.zoom='" + percent + "'");
			log.info("The page is Zoom in by percent on the page by javascript");
		} catch (Exception ex) {
			log.info("Error occured while page zoom in by percent by javascript" + "\n" + ex);
			Assert.fail();
		}
	}
}
