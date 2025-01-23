Feature: Add To Cart

  Scenario: Verify user able to add product to the cart.
    Given I'm on the Store page
    When I add a "Blue Shoes" to the cart
    Then I should see 1 "Blue Shoes" in the cart