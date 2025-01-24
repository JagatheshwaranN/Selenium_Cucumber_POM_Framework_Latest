@addToCart
Feature: Add To Cart

  @single
  Scenario: Verify user able to add product to the cart.
    Given I'm on the Store page
    When I add a "Blue Shoes" to the cart
    Then I should see 1 "Blue Shoes" in the cart

#  @multiple
#  Scenario Outline: Verify user able to add product to the cart.
#    Given I'm on the Store page
#    When I add a "<product_name>" to the cart
#    Then I should see 1 "<product_name>" in the cart
#    Examples:
#      | product_name    |
#      | Anchor Bracelet |
#      | Blue Shoes      |