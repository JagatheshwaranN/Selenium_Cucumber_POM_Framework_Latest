package com.jtaf.qa.helpers;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.jtaf.qa.pages.BasePage;
import com.jtaf.qa.utilities.LoggerUtility;

/**
 * 
 * @author Jaga
 *
 */
public class AlertHelper extends BasePage {

	private static Logger log = LoggerUtility.getLog(AlertHelper.class);

	public AlertHelper(WebDriver driver) {
		super(driver);
	}

	public Alert getAlert() {
		try {
			log.info("The control switched to alert popup window");
		} catch (Exception ex) {
			log.info("Error occured while the get the alert window" + "\n" + ex);
			Assert.fail();
		}
		return getDriver().switchTo().alert();
	}

	public void acceptAlert() {
		try {
			getAlert().accept();
			log.info("The alert popup window is accepted");
		} catch (Exception ex) {
			log.info("Error occured while accept the alert window" + "\n" + ex);
			Assert.fail();
		}
	}

	public void dismissAlert() {
		try {
			getAlert().dismiss();
			log.info("The alert popup window is dismissed");
		} catch (Exception ex) {
			log.info("Error occured while dismiss the alert window" + "\n" + ex);
			Assert.fail();
		}
	}

	public String getAlertText() {
		String text = null;
		try {
			text = getAlert().getText();
			log.info("The text " + text + " from alert window is captured");
		} catch (Exception ex) {
			log.info("Error occured while get the alert window text" + "\n" + ex);
			Assert.fail();
		}
		return text;

	}

	public boolean isAlertPresent() {
		try {
			getDriver().switchTo().alert();
			log.info("The alert popup window is present on the page");
			return true;
		} catch (NoAlertPresentException ex) {
			log.info("The alert popup window is not present on the page");
			return false;
		}

	}

	public void acceptAlertIfPresent() {
		try {
			if (!isAlertPresent()) {
				return;
			}
			log.info("The alert popup window is present on the page and accepted");
			getAlertText();
			acceptAlert();
		} catch (Exception ex) {
			log.info("Error occured while check for alert window to accept" + "\n" + ex);
			Assert.fail();
		}
	}

	public void dismissAlertIfPresent() {
		try {
			if (!isAlertPresent()) {
				return;
			}
			log.info("The alert popup window is present on the page and dismissed");
			dismissAlert();
		} catch (Exception ex) {
			log.info("Error occured while check for alert window to dismiss" + "\n" + ex);
			Assert.fail();
		}
	}

	public void acceptPrompt(String text) {
		try {
			if (!isAlertPresent()) {
				return;
			}
			Alert alert = getAlert();
			alert.sendKeys(text);
			alert.accept();
			log.info("The alert prompt window is present on the page and accepted");
		} catch (Exception ex) {
			log.info("Error occured while accept the alert prompt window" + "\n" + ex);
			Assert.fail();
		}
	}

}
