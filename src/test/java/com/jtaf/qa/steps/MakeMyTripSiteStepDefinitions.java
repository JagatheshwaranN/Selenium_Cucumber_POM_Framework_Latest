package com.jtaf.qa.steps;

import com.jtaf.qa.base.BaseTest;
import com.jtaf.qa.test.MakeMyTripSiteTest;

import io.cucumber.java8.En;

/**
 * 
 * @author Jaga
 *
 */
public class MakeMyTripSiteStepDefinitions implements En {

	BaseTest baseTest = new BaseTest();
	MakeMyTripSiteTest makeMyTripSiteTest = new MakeMyTripSiteTest();

	public MakeMyTripSiteStepDefinitions() {

		Given("^user is on MakeMyTrip home page$", () -> {
			baseTest.launchApplication();
		});

		Then("^user verify MakeMyTrip home page title$", () -> {
			makeMyTripSiteTest.homePageTitle();
		});

		When("^user enter travel details \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\\\"]*)\" \"([^\"]*)\" and enter search$",
				(String form, String to, String month, String day, String travelPassengers, String travelClass) -> {
					makeMyTripSiteTest.enterBookingDetails(form, to, month, day, travelPassengers, travelClass);
				});

		Then("^travel booking page open and verify title$", () -> {
			makeMyTripSiteTest.ticketBookingTitle();
		});

		Then("^user book ticket$", () -> {
			makeMyTripSiteTest.bookTicket();
		});

		Then("^travel details page open and verify header$", () -> {
			makeMyTripSiteTest.ticketDetailsHeader();
		});

		Then("^user checks ticket details$", () -> {
			makeMyTripSiteTest.ticketDetails();
		});
	}

}
