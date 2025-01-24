package com.qa.ctf.steps;

import com.qa.ctf.constants.Endpoint;
import com.qa.ctf.factory.DriverFactory;
import com.qa.ctf.objects.BillingDetails;
import com.qa.ctf.objects.Product;
import com.qa.ctf.pages.CartPage;
import com.qa.ctf.pages.CheckoutPage;
import com.qa.ctf.pages.StorePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class StepDefinition {

    private WebDriver driver;
    private BillingDetails billingDetails;


    @Given("I'm on the Store page")
    public void i_m_on_the_store_page() {
        driver = DriverFactory.getDriver();
        new StorePage(driver).load(Endpoint.STORE.url);
    }

    @When("I add a {product} to the cart")
    public void i_add_a_to_the_cart(Product product) {
        new StorePage(driver).addToCart(product.getProductName());
    }

    @Then("I should see {int} {product} in the cart")
    public void i_should_see_in_the_cart(Integer quantity, Product product) {
        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(product.getProductName(), cartPage.getProductName());
        Assert.assertEquals(quantity, cartPage.getProductQuantity());
    }

    @Given("I'm a guest user")
    public void i_m_a_guest_user() {
        driver = DriverFactory.getDriver();
        new StorePage(driver).load(Endpoint.STORE.url);
    }

    @Given("my billing details are")
    public void my_billing_details_are(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
    }

    @Given("I have a product in the cart")
    public void i_have_a_product_in_the_cart() {
        new StorePage(driver).addToCart("Blue Shoes");
    }

    @Given("I'm on the checkout page")
    public void i_m_on_the_checkout_page() {
        new CartPage(driver).checkout();
    }

    @When("I provide the billing details")
    public void i_provide_the_billing_details() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.setBillingDetails(billingDetails);
    }

    @When("I place an order")
    public void i_place_an_order() {
        new CheckoutPage(driver).placeOrder();
    }

    @Then("the order should be placed successfully")
    public void the_order_should_be_placed_successfully() {
        Assert.assertEquals("Thank you. Your order has been received.",
                new CheckoutPage(driver).getNotice());
    }

}
