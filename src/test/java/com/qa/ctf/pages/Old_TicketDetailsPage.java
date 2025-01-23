//package com.qa.ctf.pages;
//
//import com.qa.ctf.objects.Old_TicketDetailsPageElements;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.PageFactory;
//import org.testng.Assert;
//
//import com.qa.ctf.helpers.ReusableHelper;
//import com.qa.ctf.utilities.FileReaderUtility;
//
///**
// * @author Jaga
// *
// */
//public class Old_TicketDetailsPage extends Old_TicketBookingPage {
//
//	private static final Logger log = LogManager.getLogger(Old_TicketDetailsPage.class.getName());
//	Old_TicketDetailsPageElements oldTicketDetailsPageElements;
//
//	public Old_TicketDetailsPage(WebDriver driver) {
//		super(driver);
//		this.oldTicketDetailsPageElements = new Old_TicketDetailsPageElements();
//		PageFactory.initElements(driver, this.oldTicketDetailsPageElements);
//	}
//
//	public String getTicketDetailsPageTitle() {
//		return getPageTitle();
//	}
//
//	public String getTicketDetailsHeader() {
//		reusableHelper.waitForElementVisible(oldTicketDetailsPageElements.TicketDetailsHeader);
//		return getPageHeader(oldTicketDetailsPageElements.TicketDetailsHeader);
//	}
//
//	public WebElement getTicketDetailsFlightName() {
//		return oldTicketDetailsPageElements.TicketDetailsFlightName;
//	}
//
//	public WebElement getTicketDetailsFromPlace() {
//		return oldTicketDetailsPageElements.TicketDetailsFromPlace;
//	}
//
//	public WebElement getTicketDetailsToPlace() {
//		return oldTicketDetailsPageElements.TicketDetailsToPlace;
//	}
//
//	public WebElement getTicketDetailsTravelClass() {
//		return oldTicketDetailsPageElements.TicketDetailsTravelClass;
//	}
//
//	public WebElement getTicketFareSummary() {
//		return oldTicketDetailsPageElements.TicketFareSummary;
//	}
//
//	public WebElement getTicketTotalAmount() {
//		return oldTicketDetailsPageElements.TicketTotalAmount;
//	}
//
//	public void verifyTicketDetailsHeader() {
//		try {
//			browserHelper.getCurrentPageUrl();
//			Assert.assertEquals(getTicketDetailsHeader(), FileReaderUtility.getTestData("ticket.details.page.header"));
//		} catch (Exception ex) {
//			log.info("Error occured while check ticket details page header" + "\n" + ex);
//			Assert.fail();
//		}
//	}
//
//	public void verifyTicketDetails() {
//		try {
//			verificationHelper.verifyElementPresent(getTicketDetailsFlightName(), "ticketDetailsFlightName");
//			verificationHelper.verifyElementPresent(getTicketDetailsTravelClass(), "ticketDetailsTravelClass");
//			verificationHelper.verifyElementPresent(getTicketDetailsFromPlace(), "ticketDetailsFromPlace");
//			verificationHelper.verifyElementPresent(getTicketDetailsToPlace(), "ticketDetailsToPlace");
//			verificationHelper.verifyElementPresent(getTicketFareSummary(), "ticketFareSummary");
//			verificationHelper.verifyElementPresent(getTicketTotalAmount(), "ticketTotalAmount");
//			Assert.assertEquals(verificationHelper.readTextValueFromElement(getTicketDetailsFlightName(),
//					"ticketDetailsFlightName"), ReusableHelper.getAnyElement().get("flightName"));
//			Assert.assertEquals(ReusableHelper.getAnyElement().get("fromLocation").contains(
//					verificationHelper.readTextValueFromElement(getTicketDetailsFromPlace(), "ticketDetailsFromPlace")),
//					true);
//			Assert.assertEquals(ReusableHelper.getAnyElement().get("toLocation").contains(
//					verificationHelper.readTextValueFromElement(getTicketDetailsToPlace(), "ticketDetailsToPlace")),
//					true);
//			Assert.assertEquals(ReusableHelper.getAnyElement().get("travelClass")
//					.contains(verificationHelper
//							.readTextValueFromElement(getTicketDetailsTravelClass(), "ticketDetailsTravelClass")
//							.split(">")[0].trim()),
//					true, "Travel Class Details Not Matched");
//		} catch (Exception ex) {
//			log.info("Error occured while check ticket details" + "\n" + ex);
//			Assert.fail();
//		}
//	}
//}
