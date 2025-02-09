package com.qa.ctf.steps;

import com.qa.ctf.apis.CartApi;
import com.qa.ctf.constant.Endpoint;
import com.qa.ctf.context.AppContext;
import com.qa.ctf.context.TestContext;
import com.qa.ctf.data.Product;
import com.qa.ctf.base.PageFactory;
import com.qa.ctf.pages.StorePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class StoreSteps {

    private final WebDriver driver;
    private final StorePage storePage;
    private final AppContext appContext;

    public StoreSteps(AppContext appContext, TestContext testContext) {
        this.appContext = appContext;
        driver = testContext.driver;
        new PageFactory(testContext);
        storePage = PageFactory.getStorePage(driver);
    }

    @Given("I'm on the Store page")
    public void i_m_on_the_store_page() {
        //driver = DriverFactory.getDriver();
        storePage.load(Endpoint.STORE.url);
    }

    @When("I add a {product} to the cart")
    public void i_add_a_to_the_cart(Product product) {
        storePage.addToCart(product.getProductName());
    }

    @Given("I have a product in the cart")
    public void i_have_a_product_in_the_cart() {
        // storePage.addToCart("Blue Shoes");
        CartApi cartApi = new CartApi(appContext.cookies.getCookies());
        cartApi.addToCart(1215, 1);
        appContext.cookies.setCookies(cartApi.getCookies());
        appContext.cookies.injectCookiesToBrowser(driver);
    }

}
