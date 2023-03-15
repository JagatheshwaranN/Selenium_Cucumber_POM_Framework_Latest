@makeMyTrip
Feature: MakeMyTrip Ticket Booking

	@bookticket @regression
  Scenario Outline: Verify user is able to enter travel details, search filghts, book and view ticket details
    Given user is on MakeMyTrip home page
    Then user verify MakeMyTrip home page title
    When user enter travel details "<from>" "<to>" "<month>" "<day>" "<travelclass>" and enter search
    Then travel booking page open and verify title
    And user book ticket
    And travel details page open and verify header
    And user checks ticket details

    Examples: 
      | from          | to          | month        | day          | travelclass |
      | from.location | to.location | travel.class | travel.month | travel.day  |