package com.qa.ctf.steps;

import com.qa.ctf.base.PageFactory;
import com.qa.ctf.context.AppContext;
import com.qa.ctf.context.TestContext;
import com.qa.ctf.data.Product;
import com.qa.ctf.pages.CartPage;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class CartSteps extends BaseSteps{

    private final WebDriver driver;
    private final CartPage cartPage;
    private final AppContext appContext;

    public CartSteps(AppContext appContext, TestContext testContext) {
        super(testContext); // Calls BaseStep constructor to initialize PageFactory
        this.appContext = appContext;

        if (testContext.getDriver() == null) {
            throw new NullPointerException("Driver is not initialized");
        }

        this.driver = testContext.getDriver();
        this.cartPage = PageFactory.getCartPage(); // Fetch StorePage via PageFactory
    }

    @Then("I should see {int} {product} in the cart")
    public void i_should_see_in_the_cart(Integer quantity, Product product) {
        Assert.assertEquals(product.getProductName(), cartPage.getProductName());
        Assert.assertEquals(quantity, cartPage.getProductQuantity());
    }

//    @Given("I'm on the checkout page")
//    public void i_m_on_the_checkout_page() {
//        //cartPage.checkout();
//    }

}
