//package com.qa.ctf.steps;
//
//import com.qa.ctf.base.BaseTest;
//import com.qa.ctf.test.Old_MakeMyTripSiteTest;
//
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
///**
// *
// * @author Jaga
// *
// */
//public class Old_MakeMyTripSiteStepDefinitions {
//
//	BaseTest baseTest = new BaseTest();
//	Old_MakeMyTripSiteTest oldMakeMyTripSiteTest = new Old_MakeMyTripSiteTest();
//
//	@Given("user is on MakeMyTrip home page")
//	public void user_is_on_make_my_trip_home_page() {
//		baseTest.launchApplication();
//	}
//
//	@Then("user verify MakeMyTrip home page title")
//	public void user_verify_make_my_trip_home_page_title() {
//		oldMakeMyTripSiteTest.homePageTitle();
//	}
//
//	@When("user enter travel details {string} {string} {string} {string} {string} {string} and enter search")
//	public void user_enter_travel_details_and_enter_search(String form, String to, String month, String day,
//			String travelPassengers, String travelClass) {
//		oldMakeMyTripSiteTest.enterBookingDetails(form, to, month, day, travelPassengers, travelClass);
//	}
//
//	@Then("travel booking page open and verify title")
//	public void travel_booking_page_open_and_verify_title() {
//		oldMakeMyTripSiteTest.ticketBookingTitle();
//	}
//
//	@When("user book ticket")
//	public void user_book_ticket() {
//		oldMakeMyTripSiteTest.bookTicket();
//	}
//
//	@Then("travel details page open and verify header")
//	public void travel_details_page_open_and_verify_header() {
//		oldMakeMyTripSiteTest.ticketDetailsHeader();
//	}
//
//	@Then("user checks ticket details")
//	public void user_checks_ticket_details() {
//		oldMakeMyTripSiteTest.ticketDetails();
//	}
//}
