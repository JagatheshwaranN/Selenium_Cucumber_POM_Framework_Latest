package com.qa.ctf.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Interface for defining interactions with web elements on a webpage.
 * It provides methods to perform common actions such as clearing, clicking,
 * and typing into web elements.
 *
 * @author Jagatheshwaran N
 * @version 1.0
 */
public interface ElementActions {

    /**
     * Clears the content of the specified web element.
     *
     * @param element      the {@link WebElement} to clear
     * @param elementLabel a descriptive label for the element, used for logging or reporting
     */
    void clearElement(WebElement element, String elementLabel);

    /**
     * Clicks on the specified web element.
     *
     * @param element      the {@link WebElement} to click
     * @param elementLabel a descriptive label for the element, used for logging or reporting
     */
    void clickElement(WebElement element, String elementLabel);

    /**
     * Clicks on an element located by the specified locator.
     *
     * @param locator      the {@link By} locator of the element
     * @param value        a dynamic value for the locator, if applicable
     * @param elementLabel a descriptive label for the element, used for logging or reporting
     */
    void clickElement(By locator, String value, String elementLabel);

    /**
     * Types the given text into the specified web element.
     *
     * @param element      the {@link WebElement} to type into
     * @param text         the text to type into the element
     * @param elementLabel a descriptive label for the element, used for logging or reporting
     */
    void typeText(WebElement element, String text, String elementLabel);
}
