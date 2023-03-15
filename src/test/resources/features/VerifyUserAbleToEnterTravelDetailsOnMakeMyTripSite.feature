@makeMyTrip
Feature: MakeMyTrip Flights Search

	@entertraveldetails  @smoke @regression
  Scenario Outline: Verify user is able to enter travel details and search for flights
    Given user is on MakeMyTrip home page
    Then user verify MakeMyTrip home page title
    When user enter travel details "<from>" "<to>" "<month>" "<day>" "<travelclass>" and enter search
    

    Examples: 
      | from          | to          | month        | day          | travelclass |
      | from.location | to.location | travel.class | travel.month | travel.day  |