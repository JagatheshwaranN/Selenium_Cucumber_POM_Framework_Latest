//package com.qa.ctf.test;
//
//import com.qa.ctf.pages.Old_TicketBookingPage;
//import com.qa.ctf.pages.Old_TicketDetailsPage;
//import org.testng.Assert;
//
//import com.qa.ctf.base.BaseTest;
//import com.qa.ctf.pages.Old_HomePage;
//import com.qa.ctf.utilities.FileReaderUtility;
//
///**
// *
// * @author Jaga
// *
// */
//public class Old_MakeMyTripSiteTest {
//
//	public void homePageTitle() {
//		BaseTest.page.getInstance(Old_HomePage.class).verifyHomePageTitle();
//	}
//
//	public void enterBookingDetails(String form, String to, String month, String day, String passengers,
//			String travelClass) {
//		Old_TicketBookingPage ticketBookingPage = BaseTest.page.getInstance(Old_HomePage.class).enterTravelDetails(
//				FileReaderUtility.getTestData(form), FileReaderUtility.getTestData(to),
//				FileReaderUtility.getTestData(month), FileReaderUtility.getTestData(day),
//				FileReaderUtility.getTestData(passengers), FileReaderUtility.getTestData(travelClass));
//		var ticketBookingPageTitle = ticketBookingPage.getTicketBookingPageTitle();
//		Assert.assertEquals(ticketBookingPageTitle, FileReaderUtility.getTestData("ticket.booking.page.title"));
//	}
//
//	public void ticketBookingTitle() {
//		BaseTest.page.getInstance(Old_TicketBookingPage.class).verifyTicketBookingTitle();
//	}
//
//	public void bookTicket() {
//		Old_TicketDetailsPage ticketDetailsPage = BaseTest.page.getInstance(Old_TicketBookingPage.class).bookTicket();
//		var ticketDetailsPageHeader = ticketDetailsPage.getTicketDetailsHeader();
//		Assert.assertEquals(ticketDetailsPageHeader, FileReaderUtility.getTestData("ticket.details.page.header"));
//	}
//
//	public void ticketDetailsHeader() {
//		BaseTest.page.getInstance(Old_TicketDetailsPage.class).verifyTicketDetailsHeader();
//	}
//
//	public void ticketDetails() {
//		BaseTest.page.getInstance(Old_TicketDetailsPage.class).verifyTicketDetails();
//	}
//}
