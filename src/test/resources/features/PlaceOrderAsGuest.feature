Feature: Place Order

  Scenario: Verify user able to place order as guest user.
    Given I'm a guest user
    And I have a product in the cart
    And I'm on the checkout page
    When I provide the billing details
      | firstname | lastname | country            | address_line      | city  | state | zipcode | email              |
      | Test      | User     | United States (US) | 5300 Spring Creek | Plano | Texas | 75024   | tester@gmail.com |
    And I place an order
    Then the order should be placed successfully