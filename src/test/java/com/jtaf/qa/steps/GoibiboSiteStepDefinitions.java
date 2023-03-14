package com.jtaf.qa.steps;

import com.jtaf.qa.base.BaseTest;
import com.jtaf.qa.test.GoibiboSiteTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author Jaga
 *
 */
public class GoibiboSiteStepDefinitions {

	BaseTest baseTest = new BaseTest();
	GoibiboSiteTest goibiboSiteTest = new GoibiboSiteTest();

	@Given("^user is on Goibibo home page$")
	public void user_is_on_Goibibo_home_page() throws Exception {
		baseTest.launchApplication();
	}

	@Then("^user verify Goibibo home page title$")
	public void user_verify_Goibibo_home_page_title() throws Exception {
		goibiboSiteTest.homePageTitle();
	}

	@When("^user enter travel details \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" and enter search$")
	public void user_enter_travel_details_and_enter_search(String form, String to, String month, String day,
			String travelClass) throws Exception {
		goibiboSiteTest.enterBookingDetails(form, to, month, day, travelClass);
	}

	@Then("^travel booking page open and verify title$")
	public void travel_booking_page_open_and_verify_title() throws Exception {
		goibiboSiteTest.ticketBookingTitle();
	}

	@Then("^user book ticket$")
	public void user_book_ticket() throws Exception {
		goibiboSiteTest.bookTicket();
	}

	@Then("^travel details page open and verify header$")
	public void travel_details_page_open_and_verify_header() throws Exception {
		goibiboSiteTest.ticketDetailsHeader();
	}

	@Then("^user checks ticket details$")
	public void user_checks_ticket_details() throws Exception {
		goibiboSiteTest.ticketDetails();
	}

}
