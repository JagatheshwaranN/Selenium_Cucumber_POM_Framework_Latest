@goibibo
Feature: Goibibo Launch

  @launch @sanity @smoke @regression
  Scenario: Verify user is able to launch Goibibo site
    Given user is on Goibibo home page
    Then user verify Goibibo home page title