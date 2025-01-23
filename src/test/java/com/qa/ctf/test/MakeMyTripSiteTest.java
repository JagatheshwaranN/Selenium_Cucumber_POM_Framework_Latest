package com.qa.ctf.test;

import com.qa.ctf.pages.TicketBookingPage;
import com.qa.ctf.pages.TicketDetailsPage;
import org.testng.Assert;

import com.qa.ctf.base.BaseTest;
import com.qa.ctf.pages.HomePage;
import com.qa.ctf.utilities.FileReaderUtility;

/**
 * 
 * @author Jaga
 *
 */
public class MakeMyTripSiteTest {

	public void homePageTitle() {
		BaseTest.page.getInstance(HomePage.class).verifyHomePageTitle();
	}

	public void enterBookingDetails(String form, String to, String month, String day, String passengers,
			String travelClass) {
		TicketBookingPage ticketBookingPage = BaseTest.page.getInstance(HomePage.class).enterTravelDetails(
				FileReaderUtility.getTestData(form), FileReaderUtility.getTestData(to),
				FileReaderUtility.getTestData(month), FileReaderUtility.getTestData(day),
				FileReaderUtility.getTestData(passengers), FileReaderUtility.getTestData(travelClass));
		var ticketBookingPageTitle = ticketBookingPage.getTicketBookingPageTitle();
		Assert.assertEquals(ticketBookingPageTitle, FileReaderUtility.getTestData("ticket.booking.page.title"));
	}

	public void ticketBookingTitle() {
		BaseTest.page.getInstance(TicketBookingPage.class).verifyTicketBookingTitle();
	}

	public void bookTicket() {
		TicketDetailsPage ticketDetailsPage = BaseTest.page.getInstance(TicketBookingPage.class).bookTicket();
		var ticketDetailsPageHeader = ticketDetailsPage.getTicketDetailsHeader();
		Assert.assertEquals(ticketDetailsPageHeader, FileReaderUtility.getTestData("ticket.details.page.header"));
	}

	public void ticketDetailsHeader() {
		BaseTest.page.getInstance(TicketDetailsPage.class).verifyTicketDetailsHeader();
	}

	public void ticketDetails() {
		BaseTest.page.getInstance(TicketDetailsPage.class).verifyTicketDetails();
	}
}
