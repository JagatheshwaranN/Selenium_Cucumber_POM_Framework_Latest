package com.jtaf.qa.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.jtaf.qa.helpers.ReusableHelper;
import com.jtaf.qa.utilities.FileReaderUtility;
import com.jtaf.qa.utilities.LoggerUtility;

/**
 * 
 * @author Jaga
 *
 */
public class TicketBookingPage extends HomePage {

	private static Logger log = LoggerUtility.getLog(TicketBookingPage.class);

	private By priceSort = By.xpath("//div[@class='SortOptionsstyles__SortOption-tji0t1-3 ivjAsX']");
	private By priceList = By.xpath("(//div[contains(@class,'srp-card-uistyles__Price-sc-3flq99-17')])[1]");
	private By viewFaresButton = By.xpath(
			"(//div[contains(@class,'srp-card-uistyles__CardRight')]//button[contains(@class,'srp-card-uistyles__BookButton') and text()='VIEW FARES'])[1]");
	private By bookButton = By.xpath("(//input[contains(@class,'srp-card-uistyles__Fltbook-sc-3flq99-35')])[1]");
	private By flightName = By.xpath("(//div[@class='dF alignItemsCenter']//span[@class='font14 padL5 black'])[1]");

	public TicketBookingPage(WebDriver driver) {
		super(driver);
	}

	public String getTicketBookingPageTitle() {
		return getPageTitle();
	}

	public WebElement getPriceSort() {
		return getElement(priceSort);
	}

	public WebElement getPriceList() {
		return getElement(priceList);
	}

	public WebElement getViewFaresButton() {
		return getElement(viewFaresButton);
	}

	public WebElement getBookButton() {
		return getElement(bookButton);
	}

	public WebElement getFlightName() {
		return getElement(flightName);
	}

	public void verifyTicketBookingTitle() {
		try {
			browserHelper.getCurrentPageUrl();
			Assert.assertEquals(getTicketBookingPageTitle(),
					FileReaderUtility.getTestData("ticket.booking.page.title"));
		} catch (Exception ex) {
			log.info("Error occured while check ticket booking page title" + "\n" + ex);
			Assert.fail();
		}
	}

	public TicketDetailsPage bookTicket() {
		int price = 0;
		int lowestPrice = 0;
		try {
			verificationHelper.verifyElementPresent(getPriceList(), "priceList");
			price = Integer.parseInt(getPriceList().getText().toString().substring(0).replaceAll(",", ""));
			lowestPrice = getLowestPrice();
			if (price == lowestPrice) {
				ReusableHelper.setAnyElement("flightName",
						verificationHelper.readTextValueFromElement(getFlightName(), "flightName"));
				reusableHelper.elementClick(getViewFaresButton(), "viewFaresButton");
				verificationHelper.verifyElementPresent(getBookButton(), "bookButton");
				reusableHelper.elementClick(getBookButton(), "bookButton");
			}
		} catch (Exception ex) {
			log.info("Error occured while book the ticket" + "\n" + ex);
			Assert.fail();
		}
		return getInstance(TicketDetailsPage.class);
	}

	public int getLowestPrice() {
		List<Integer> priceBucket = new ArrayList<Integer>();
		String price = null;
		int priceInNum = 0;
		try {
			String priceList = "(//div[contains(@class,'srp-card-uistyles__Price-sc-3flq99-17')])";
			int size = getDriver().findElements(By.xpath(priceList)).size();
			for (int i = 1; i <= size; i++) {
				price = getDriver().findElement(By.xpath(priceList + "[" + i + "]")).getText();
				price = price.substring(0).replaceAll(",", "");
				priceInNum = Integer.parseInt(price);
				priceBucket.add(priceInNum);
			}
		} catch (Exception ex) {
			log.info("Error occured while get the price list" + "\n" + ex);
			Assert.fail();
		}
		Collections.sort(priceBucket);
		int lowestPrice = priceBucket.get(0);
		return lowestPrice;
	}

}
