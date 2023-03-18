package com.jtaf.qa.test;

import org.testng.Assert;

import com.jtaf.qa.base.BaseTest;
import com.jtaf.qa.pages.HomePage;
import com.jtaf.qa.pages.TicketBookingPage;
import com.jtaf.qa.pages.TicketDetailsPage;
import com.jtaf.qa.utilities.FileReaderUtility;

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
		String ticketBookingPageTitle = ticketBookingPage.getTicketBookingPageTitle();
		Assert.assertEquals(ticketBookingPageTitle, FileReaderUtility.getTestData("ticket.booking.page.title"));
	}

	public void ticketBookingTitle() {
		BaseTest.page.getInstance(TicketBookingPage.class).verifyTicketBookingTitle();
	}

	public void bookTicket() {
		TicketDetailsPage ticketDetailsPage = BaseTest.page.getInstance(TicketBookingPage.class).bookTicket();
		String ticketDetailsPageHeader = ticketDetailsPage.getTicketDetailsHeader();
		Assert.assertEquals(ticketDetailsPageHeader, FileReaderUtility.getTestData("ticket.details.page.header"));
	}

	public void ticketDetailsHeader() {
		BaseTest.page.getInstance(TicketDetailsPage.class).verifyTicketDetailsHeader();
	}

	public void ticketDetails() {
		BaseTest.page.getInstance(TicketDetailsPage.class).verifyTicketDetails();
	}

}
