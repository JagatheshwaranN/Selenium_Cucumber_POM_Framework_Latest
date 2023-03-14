@goibibo
Feature: Goibibo Enter Ticket Details

	@entertraveldetails  @smoke @regression
  Scenario Outline: Verify user is able to enter travel details
    Given user is on Goibibo home page
    Then user verify Goibibo home page title
    When user enter travel details "<from>" "<to>" "<month>" "<day>" "<travelclass>" and enter search
    

    Examples: 
      | from          | to          | month        | day          | travelclass |
      | from.location | to.location | travel.class | travel.month | travel.day  |