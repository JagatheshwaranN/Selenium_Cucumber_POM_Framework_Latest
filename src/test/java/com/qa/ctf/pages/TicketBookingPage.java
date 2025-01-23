package com.qa.ctf.pages;

import java.util.ArrayList;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.qa.ctf.helpers.ReusableHelper;
import com.qa.ctf.objects.TicketBookingPageElements;
import com.qa.ctf.utilities.Constants;
import com.qa.ctf.utilities.FileReaderUtility;

/**
 * 
 * @author Jaga
 *
 */
public class TicketBookingPage extends HomePage {

	private static final Logger log = LogManager.getLogger(TicketBookingPage.class.getName());
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
		return ticketBookingPageElements.TicketBookPopupClose;
	}

	public WebElement getTicketBookingBanner() {
		return ticketBookingPageElements.TicketBookingBanner;
	}

	public WebElement getPriceSort() {
		return ticketBookingPageElements.PriceSort;
	}

	public WebElement getPriceList() {
		return ticketBookingPageElements.PriceList;
	}

	public WebElement getviewPrices() {
		return ticketBookingPageElements.ViewPrices;
	}

	public WebElement getBook() {
		return ticketBookingPageElements.Book;
	}

	public WebElement getFlightName() {
		return ticketBookingPageElements.FlightName;
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
		var price = 0;
		var lowestPrice = 0;
		try {
			javaScriptHelper.scrollToElement(getTicketBookingBanner());
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
				browserHelper.SwitchToWindow(Constants.CHILD_PAGE1);
			}
		} catch (Exception ex) {
			log.info("Error occured while book the ticket" + "\n" + ex);
			Assert.fail();
		}
		return getInstance(TicketDetailsPage.class);
	}

	public int getLowestPrice() {
		var priceBucket = new ArrayList<Integer>();
		String price = null;
		var priceInNum = 0;
		try {
			var priceList = "(//div[@class='priceSection']//p)";
			var size = getDriver().findElements(By.xpath(priceList)).size();
			for (var i = 1; i <= size; i++) {
				price = getDriver().findElement(By.xpath(priceList + "[" + i + "]")).getText();
				price = price.substring(1).replaceAll(",", "").trim();
				priceInNum = Integer.parseInt(price);
				priceBucket.add(priceInNum);
			}
		} catch (Exception ex) {
			log.info("Error occured while get the lowest price list" + "\n" + ex);
			Assert.fail();
		}

		var lowestPrice = priceBucket.stream().sorted().collect(Collectors.toList()).get(0);
		return lowestPrice;
	}
}
