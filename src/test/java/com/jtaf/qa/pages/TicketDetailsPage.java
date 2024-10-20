package com.jtaf.qa.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.jtaf.qa.helpers.ReusableHelper;
import com.jtaf.qa.objects.TicketDetailsPageElements;
import com.jtaf.qa.utilities.FileReaderUtility;

/**
 * @author Jaga
 *
 */
public class TicketDetailsPage extends TicketBookingPage {

	private static final Logger log = LogManager.getLogger(TicketDetailsPage.class.getName());
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
		reusableHelper.waitForElementVisible(ticketDetailsPageElements.TicketDetailsHeader);
		return getPageHeader(ticketDetailsPageElements.TicketDetailsHeader);
	}

	public WebElement getTicketDetailsFlightName() {
		return ticketDetailsPageElements.TicketDetailsFlightName;
	}

	public WebElement getTicketDetailsFromPlace() {
		return ticketDetailsPageElements.TicketDetailsFromPlace;
	}

	public WebElement getTicketDetailsToPlace() {
		return ticketDetailsPageElements.TicketDetailsToPlace;
	}

	public WebElement getTicketDetailsTravelClass() {
		return ticketDetailsPageElements.TicketDetailsTravelClass;
	}

	public WebElement getTicketFareSummary() {
		return ticketDetailsPageElements.TicketFareSummary;
	}

	public WebElement getTicketTotalAmount() {
		return ticketDetailsPageElements.TicketTotalAmount;
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
		} catch (Exception ex) {
			log.info("Error occured while check ticket details" + "\n" + ex);
			Assert.fail();
		}
	}
}
