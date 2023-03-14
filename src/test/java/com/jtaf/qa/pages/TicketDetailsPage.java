package com.jtaf.qa.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
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

	@FindBy(how = How.XPATH, using = "//span[contains(@class,'flex1') and text()='TICKET DETAILS']")
	public WebElement ticketDetailsHeader;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'common-elementsstyles__Wid13')]//span[1]")
	public WebElement ticketDetailsFlightName;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'common-elementsstyles__Wid13')]//span[2]")
	public WebElement ticketDetailsTravelClass;
	@FindBy(how = How.XPATH, using = "(//div[contains(@class,'common-elementsstyles__Wid31')]//span[contains(@class,'padR5')])[1]")
	public WebElement ticketDetailsFromPlace;
	@FindBy(how = How.XPATH, using = "(//div[contains(@class,'common-elementsstyles__Wid31')]//span[contains(@class,'padR5')])[4]")
	public WebElement ticketDetailsToPlace;
	@FindBy(how = How.ID, using = "fareSummary")
	public WebElement ticketFareSummary;
	@FindBy(how = How.XPATH, using = "//div[@class='price-breakupstyles__BreakupTitle-sc-fjdxc9-14 eovIVn fb go_blue']")
	public WebElement ticketTotalAmount;

	public TicketDetailsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public String getTicketDetailsPageTitle() {
		return getPageTitle();
	}

	public String getTicketDetailsHeader() {
		return getPageHeader(ticketDetailsHeader);
	}

	public WebElement getTicketDetailsFlightName() {
		return ticketDetailsFlightName;
	}

	public WebElement getTicketDetailsTravelClass() {
		return ticketDetailsTravelClass;
	}

	public WebElement getTicketDetailsFromPlace() {
		return ticketDetailsFromPlace;
	}

	public WebElement getTicketDetailsToPlace() {
		return ticketDetailsToPlace;
	}

	public WebElement getTicketFareSummary() {
		return ticketFareSummary;
	}

	public WebElement getTicketTotalAmount() {
		return ticketTotalAmount;
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
