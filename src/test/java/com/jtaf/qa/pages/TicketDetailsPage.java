package com.jtaf.qa.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.jtaf.qa.helpers.ReusableHelper;
import com.jtaf.qa.utilities.FileReaderUtility;
import com.jtaf.qa.utilities.LoggerUtility;

/**
 * @author Jaga
 *
 */
public class TicketDetailsPage extends TicketBookingPage {

	private static Logger log = LoggerUtility.getLog(TicketDetailsPage.class);

	private By ticketDetailsHeader = By.xpath("//span[contains(@class,'flex1') and text()='TICKET DETAILS']");
	private By ticketDetailsFlightName = By.xpath("//div[contains(@class,'common-elementsstyles__Wid13')]//span[1]");
	private By ticketDetailsTravelClass = By.xpath("//div[contains(@class,'common-elementsstyles__Wid13')]//span[2]");
	private By ticketDetailsFromPlace = By
			.xpath("(//div[contains(@class,'common-elementsstyles__Wid31')]//span[contains(@class,'padR5')])[1]");
	private By ticketDetailsToPlace = By
			.xpath("(//div[contains(@class,'common-elementsstyles__Wid31')]//span[contains(@class,'padR5')])[2]");
	private By ticketFareSummary = By.xpath("//div[@class='padL10 padR10 padT10 BrdrBotDsh flexCol']");
	private By ticketTotalAmount = By.xpath(
			"//div[contains(@class,'fare-summarystyles__TotalAmount')]//div[@class='dF width100 padB15 justifyBetween']");

	public TicketDetailsPage(WebDriver driver) {
		super(driver);
	}

	public String getTicketDetailsPageTitle() {
		return getPageTitle();
	}

	public String getTicketDetailsHeader() {
		return null;
		//return getPageHeader(ticketDetailsHeader);
	}

	public WebElement getTicketDetailsFlightName() {
		return getElement(ticketDetailsFlightName);
	}

	public WebElement getTicketDetailsTravelClass() {
		return getElement(ticketDetailsTravelClass);
	}

	public WebElement getTicketDetailsFromPlace() {
		return getElement(ticketDetailsFromPlace);
	}

	public WebElement getTicketDetailsToPlace() {
		return getElement(ticketDetailsToPlace);
	}

	public WebElement getTicketFareSummary() {
		return getElement(ticketFareSummary);
	}

	public WebElement getTicketTotalAmount() {
		return getElement(ticketTotalAmount);
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
			Thread.sleep(5000);
			verificationHelper.verifyElementPresent(getTicketDetailsFlightName(), "ticketDetailsFlightName");
			verificationHelper.verifyElementPresent(getTicketDetailsTravelClass(), "ticketDetailsTravelClass");
			verificationHelper.verifyElementPresent(getTicketDetailsFromPlace(), "ticketDetailsFromPlace");
			verificationHelper.verifyElementPresent(getTicketDetailsToPlace(), "ticketDetailsToPlace");
			verificationHelper.verifyElementPresent(getTicketFareSummary(), "ticketFareSummary");
			verificationHelper.verifyElementPresent(getTicketTotalAmount(), "ticketTotalAmount");
			Assert.assertEquals(verificationHelper.readTextValueFromElement(getTicketDetailsFlightName(),
					"ticketDetailsFlightName"), ReusableHelper.getAnyElement().get("flightName"));
			Assert.assertEquals(verificationHelper.readTextValueFromElement(getTicketDetailsTravelClass(),
					"ticketDetailsTravelClass"), ReusableHelper.getAnyElement().get("travelClass"));
			Assert.assertEquals(ReusableHelper.getAnyElement().get("fromLocation").contains(
					verificationHelper.readTextValueFromElement(getTicketDetailsFromPlace(), "ticketDetailsFromPlace")),
					true);
			Assert.assertEquals(ReusableHelper.getAnyElement().get("toLocation").contains(
					verificationHelper.readTextValueFromElement(getTicketDetailsToPlace(), "ticketDetailsToPlace")),
					true);
		} catch (Exception ex) {
			log.info("Error occured while check ticket details" + "\n" + ex);
			Assert.fail();
		}
	}

}
