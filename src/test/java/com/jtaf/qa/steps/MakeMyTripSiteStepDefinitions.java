package com.jtaf.qa.steps;

import com.jtaf.qa.base.BaseTest;
import com.jtaf.qa.test.MakeMyTripSiteTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author Jaga
 *
 */
public class MakeMyTripSiteStepDefinitions {

	BaseTest baseTest = new BaseTest();
	MakeMyTripSiteTest makeMyTripSiteTest = new MakeMyTripSiteTest();

	@Given("^user is on MakeMyTrip home page$")
	public void user_is_on_MakeMyTrip_home_page() throws Exception {
		baseTest.launchApplication();
	}

	@Then("^user verify MakeMyTrip home page title$")
	public void user_verify_MakeMyTrip_home_page_title() throws Exception {
		makeMyTripSiteTest.homePageTitle();
	}

	@When("^user enter travel details \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\\\"]*)\" \"([^\"]*)\" and enter search$")
	public void user_enter_travel_details_and_enter_search(String form, String to, String month, String day,
			String travelPassengers, String travelClass) throws Exception {
		makeMyTripSiteTest.enterBookingDetails(form, to, month, day, travelPassengers, travelClass);
	}

	@Then("^travel booking page open and verify title$")
	public void travel_booking_page_open_and_verify_title() throws Exception {
		makeMyTripSiteTest.ticketBookingTitle();
	}

	@Then("^user book ticket$")
	public void user_book_ticket() throws Exception {
		makeMyTripSiteTest.bookTicket();
	}

	@Then("^travel details page open and verify header$")
	public void travel_details_page_open_and_verify_header() throws Exception {
		makeMyTripSiteTest.ticketDetailsHeader();
	}

	@Then("^user checks ticket details$")
	public void user_checks_ticket_details() throws Exception {
		makeMyTripSiteTest.ticketDetails();
	}

}
