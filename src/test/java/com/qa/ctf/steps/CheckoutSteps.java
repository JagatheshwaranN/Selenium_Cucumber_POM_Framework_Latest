package com.qa.ctf.steps;

import com.qa.ctf.base.PageFactory;
import com.qa.ctf.constant.Endpoint;
import com.qa.ctf.context.AppContext;
import com.qa.ctf.context.TestContext;
import com.qa.ctf.pages.CheckoutPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class CheckoutSteps extends  BaseSteps {

    private final WebDriver driver;
    private final CheckoutPage checkoutPage;
    private final AppContext appContext;

    public CheckoutSteps(AppContext appContext, TestContext testContext) {
        super(testContext); // Calls BaseStep constructor to initialize PageFactory
        this.appContext = appContext;

        if (testContext.getDriver() == null) {
            throw new NullPointerException("Driver is not initialized");
        }

        this.driver = testContext.getDriver();
        this.checkoutPage = PageFactory.getCheckoutPage(); // Fetch StorePage via PageFactory
    }

    @Given("I'm on the checkout page")
    public void i_m_on_the_checkout_page() {
    checkoutPage.load(Endpoint.CHECKOUT.url);
    }

    @When("I provide the billing details")
    public void i_provide_the_billing_details() {
        checkoutPage.setBillingDetails(appContext.billingDetails);
    }

    @When("I place an order")
    public void i_place_an_order() {
        checkoutPage.placeOrder();
    }

    @Then("the order should be placed successfully")
    public void the_order_should_be_placed_successfully() {
        Assert.assertEquals("Thank you. Your order has been received.",
                checkoutPage.getNotice());
    }

}
