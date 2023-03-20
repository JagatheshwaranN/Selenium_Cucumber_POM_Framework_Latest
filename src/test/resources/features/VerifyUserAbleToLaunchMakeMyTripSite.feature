@makeMyTrip
Feature: MakeMyTrip Launch

  @launch @sanity @smoke @regression
  Scenario: Verify user is able to launch MakeMyTrip site
    Given user is on MakeMyTrip home page
    Then user verify homeMakeMyTrip page title