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

	@FindBy(how = How.XPATH, using = "//h2[contains(@class,'headerTitle')]")
	public WebElement ticketDetailsHeader;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'flightItenaryHdr')]//p//span[1]")
	public WebElement ticketDetailsFlightName;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'flightItenaryHdr')]//div[@class='makeFlex']//span[1]")
	public WebElement ticketDetailsTravelClass;
	@FindBy(how = How.XPATH, using = "(//div[contains(@class,'itenaryLeft')]//div[contains(@class,'makeFlex')]//div//span)[3]")
	public WebElement ticketDetailsFromPlace;
	@FindBy(how = How.XPATH, using = "(//div[contains(@class,'itenaryLeft')]//div[contains(@class,'makeFlex')]//div//span)[7]")
	public WebElement ticketDetailsToPlace;
	@FindBy(how = How.ID, using = "FARE_SUMMARY")
	public WebElement ticketFareSummary;
	@FindBy(how = How.XPATH, using = "//p[@class='fareRow']//span[2]")
	public WebElement ticketTotalAmount;

	public TicketDetailsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public String getTicketDetailsPageTitle() {
		return getPageTitle();
	}

	public String getTicketDetailsHeader() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
