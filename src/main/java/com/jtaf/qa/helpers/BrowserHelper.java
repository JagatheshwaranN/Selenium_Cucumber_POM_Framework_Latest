package com.jtaf.qa.helpers;

import java.util.LinkedList;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.jtaf.qa.base.BasePage;

/**
 * 
 * @author Jaga
 *
 */
public class BrowserHelper extends BasePage {

	private static final Logger log = LogManager.getLogger(BrowserHelper.class.getName());

	public BrowserHelper(WebDriver driver) {
		super(driver);
	}

	public void goBack() {
		try {
			getDriver().navigate().back();
			log.info("The browser navigated to previous page");
		} catch (Exception ex) {
			log.info("Error occured while browser navigate to previous page" + "\n" + ex);
			Assert.fail();
		}
	}

	public void goForward() {
		try {
			getDriver().navigate().forward();
			log.info("The browser navigated to front page");
		} catch (Exception ex) {
			log.info("Error occured while browser navigate to front page" + "\n" + ex);
			Assert.fail();
		}
	}

	public void refresh() {
		try {
			getDriver().navigate().refresh();
			log.info("The browser refreshed the current page");
		} catch (Exception ex) {
			log.info("Error occured while browser refresh the current page" + "\n" + ex);
			Assert.fail();
		}
	}

	public Set<String> getWindowHandles() {
		try {
			log.info("The browser window handles are captured");
		} catch (Exception ex) {
			log.info("Error occured while capturing the browser window handles" + "\n" + ex);
			Assert.fail();
		}
		return getDriver().getWindowHandles();
	}

	public void SwitchToWindow(int index) {
		try {
			var windowsId = new LinkedList<String>(getWindowHandles());
			if (index < 0 || index > windowsId.size())
				throw new IllegalArgumentException("Window handle has invalid index : " + index);
			getDriver().switchTo().window(windowsId.get(index));
			log.info("The control switched to window with index : " + index);
		} catch (Exception ex) {
			log.info("Error occured while control switch between windows" + "\n" + ex);
			Assert.fail();
		}
	}

	public void switchToParentWindow() {
		try {
			var windowsId = new LinkedList<String>(getWindowHandles());
			getDriver().switchTo().window(windowsId.get(0));
			log.info("The control switched to the parent window");
		} catch (Exception ex) {
			log.info("Error occured while the control switch to parent window" + "\n" + ex);
			Assert.fail();
		}
	}

	public void switchToParentWithChildClose() {
		try {
			switchToParentWindow();
			var windowsId = new LinkedList<String>(getWindowHandles());
			for (var i = 1; i < windowsId.size(); i++) {
				log.info("Child window id : " + windowsId.get(i));
				getDriver().switchTo().window(windowsId.get(i));
				getDriver().close();
			}
			switchToParentWindow();
		} catch (Exception ex) {
			log.info("Error occured while the control switch to parent window" + "\n" + ex);
			Assert.fail();
		}
	}

	public void switchToFrame(String nameOrid) {
		try {
			getDriver().switchTo().frame(nameOrid);
			log.info("The control switch to frame with name or id : " + nameOrid);
		} catch (Exception ex) {
			log.info("Error occured while the control switch to frame" + "\n" + ex);
			Assert.fail();
		}
	}

	public String getCurrentPageUrl() {
		String url = null;
		try {
			url = getDriver().getCurrentUrl();
			log.info("The browser current page url : " + url);
		} catch (Exception ex) {
			log.info("Error occured while the get the current page URL" + "\n" + ex);
			Assert.fail();
		}
		return url;
	}
}
