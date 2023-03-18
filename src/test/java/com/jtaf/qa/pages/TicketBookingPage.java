package com.jtaf.qa.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.jtaf.qa.helpers.BrowserHelper;
import com.jtaf.qa.helpers.ReusableHelper;
import com.jtaf.qa.objects.TicketBookingPageElements;
import com.jtaf.qa.utilities.FileReaderUtility;
import com.jtaf.qa.utilities.LoggerUtility;

/**
 * 
 * @author Jaga
 *
 */
public class TicketBookingPage extends HomePage {

	private static Logger log = LoggerUtility.getLog(TicketBookingPage.class);
	TicketBookingPageElements ticketBookingPageElements;

	public TicketBookingPage(WebDriver driver) {
		super(driver);
		this.ticketBookingPageElements = new TicketBookingPageElements();
		PageFactory.initElements(driver, this.ticketBookingPageElements);
	}

	public String getTicketBookingPageTitle() {
		return getPageTitle();
	}

	public WebElement getTicketBookPopupClose() {
		return ticketBookingPageElements.ticketBookPopupClose;
	}

	public WebElement getOneWayPriceSlider() {
		return ticketBookingPageElements.oneWayPriceSlider;
	}

	public WebElement getPriceSort() {
		return ticketBookingPageElements.priceSort;
	}

	public WebElement getPriceList() {
		return ticketBookingPageElements.priceList;
	}

	public WebElement getviewPrices() {
		return ticketBookingPageElements.viewPrices;
	}

	public WebElement getBook() {
		return ticketBookingPageElements.book;
	}

	public WebElement getFlightName() {
		return ticketBookingPageElements.flightName;
	}

	public void verifyTicketBookingTitle() {
		try {
			waitForElementVisible(getTicketBookPopupClose());
			reusableHelper.elementClick(getTicketBookPopupClose(), "ticketBookPopupClose");
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
			javaScriptHelper.scrollToElement(getOneWayPriceSlider());
			verificationHelper.verifyElementPresent(getPriceList(), "priceList");
			price = Integer.parseInt(getPriceList().getText().toString().substring(1).replaceAll(",", "").trim());
			lowestPrice = getLowestPrice();
			if (price == lowestPrice) {
				ReusableHelper.setAnyElement("flightName",
						verificationHelper.readTextValueFromElement(getFlightName(), "flightName"));
				verificationHelper.verifyElementPresent(getviewPrices(), "viewPricesButton");
				reusableHelper.elementClick(getviewPrices(), "viewPricesButton");
				verificationHelper.verifyElementPresent(getBook(), "bookButton");
				reusableHelper.elementClick(getBook(), "bookButton");
				browserHelper.SwitchToWindow(BrowserHelper.CHILD_PAGE1);
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
			String priceList = "(//div[@class='priceSection']//p)";
			int size = getDriver().findElements(By.xpath(priceList)).size();
			for (int i = 1; i <= size; i++) {
				price = getDriver().findElement(By.xpath(priceList + "[" + i + "]")).getText();
				price = price.substring(1).replaceAll(",", "").trim();
				priceInNum = Integer.parseInt(price);
				priceBucket.add(priceInNum);
			}
		} catch (Exception ex) {
			log.info("Error occured while get the lowest price list" + "\n" + ex);
			Assert.fail();
		}
		Collections.sort(priceBucket);
		int lowestPrice = priceBucket.get(0);
		return lowestPrice;
	}
}
