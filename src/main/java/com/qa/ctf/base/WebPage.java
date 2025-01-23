package com.qa.ctf.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author Jaga
 *
 */
public interface WebPage {

	public void enterText(WebElement element, String text, String elementName);

	public void elementClick(WebElement element, String elementName);

	public void elementClick(By locator, String value, String elementName);

	public void elementClear(WebElement element, String elementName);
}
