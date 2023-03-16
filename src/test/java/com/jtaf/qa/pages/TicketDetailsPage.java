package com.jtaf.qa.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.jtaf.qa.helpers.ReusableHelper;
import com.jtaf.qa.pageObjects.TicketDetailsPageElements;
import com.jtaf.qa.utilities.FileReaderUtility;
import com.jtaf.qa.utilities.LoggerUtility;

/**
 * @author Jaga
 *
 */
public class TicketDetailsPage extends TicketBookingPage {

	private static Logger log = LoggerUtility.getLog(TicketDetailsPage.class);
	TicketDetailsPageElements ticketDetailsPageElements;

	public TicketDetailsPage(WebDriver driver) {
		super(driver);
		this.ticketDetailsPageElements = new TicketDetailsPageElements();
		PageFactory.initElements(driver, this.ticketDetailsPageElements);
	}

	public String getTicketDetailsPageTitle() {
		return getPageTitle();
	}

	public String getTicketDetailsHeader() {
		reusableHelper.waitForElementVisible(ticketDetailsPageElements.ticketDetailsHeader);
		return getPageHeader(ticketDetailsPageElements.ticketDetailsHeader);
	}

	public WebElement getTicketDetailsFlightName() {
		return ticketDetailsPageElements.ticketDetailsFlightName;
	}

	public WebElement getTicketDetailsTravelClass() {
		return ticketDetailsPageElements.ticketDetailsTravelClass;
	}

	public WebElement getTicketDetailsFromPlace() {
		return ticketDetailsPageElements.ticketDetailsFromPlace;
	}

	public WebElement getTicketDetailsToPlace() {
		return ticketDetailsPageElements.ticketDetailsToPlace;
	}

	public WebElement getTicketFareSummary() {
		return ticketDetailsPageElements.ticketFareSummary;
	}

	public WebElement getTicketTotalAmount() {
		return ticketDetailsPageElements.ticketTotalAmount;
	}

	public void verifyTicketDetailsHeader() {
		try {
			browserHelper.getCurrentPageUrl();
			Assert.assertEquals(getTicketDetailsHeader(), FileReaderUtility.getTestData("ticket.details.page.header"));
		} catch (Exception ex) {
			log.info("Error occured while check ticket details page header" + "\n" + ex);
			Assert.fail();
		}
	}

	public void verifyTicketDetails() {
		try {
			verificationHelper.verifyElementPresent(getTicketDetailsFlightName(), "ticketDetailsFlightName");
			verificationHelper.verifyElementPresent(getTicketDetailsTravelClass(), "ticketDetailsTravelClass");
			verificationHelper.verifyElementPresent(getTicketDetailsFromPlace(), "ticketDetailsFromPlace");
			verificationHelper.verifyElementPresent(getTicketDetailsToPlace(), "ticketDetailsToPlace");
			verificationHelper.verifyElementPresent(getTicketFareSummary(), "ticketFareSummary");
			verificationHelper.verifyElementPresent(getTicketTotalAmount(), "ticketTotalAmount");
			Assert.assertEquals(verificationHelper.readTextValueFromElement(getTicketDetailsFlightName(),
					"ticketDetailsFlightName"), ReusableHelper.getAnyElement().get("flightName"));
			Assert.assertEquals(ReusableHelper.getAnyElement().get("fromLocation").contains(
					verificationHelper.readTextValueFromElement(getTicketDetailsFromPlace(), "ticketDetailsFromPlace")),
					true);
			Assert.assertEquals(ReusableHelper.getAnyElement().get("toLocation").contains(
					verificationHelper.readTextValueFromElement(getTicketDetailsToPlace(), "ticketDetailsToPlace")),
					true);
			Assert.assertEquals(ReusableHelper.getAnyElement().get("travelClass")
					.contains(verificationHelper
							.readTextValueFromElement(getTicketDetailsTravelClass(), "ticketDetailsTravelClass")
							.split(">")[0].trim()),
					true, "Travel Class Details Not Matched");
//			Assert.assertEquals(verificationHelper.readTextValueFromElement(getTicketDetailsTravelClass(),
//					"ticketDetailsTravelClass"), ReusableHelper.getAnyElement().get("travelClass"));
		} catch (Exception ex) {
			log.info("Error occured while check ticket details" + "\n" + ex);
			Assert.fail();
		}
	}

}
