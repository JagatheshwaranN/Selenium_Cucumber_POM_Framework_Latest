package com.qa.ctf.steps;

import com.qa.ctf.base.PageFactory;
import com.qa.ctf.constant.Endpoint;
import com.qa.ctf.context.AppContext;
import com.qa.ctf.context.TestContext;
import com.qa.ctf.data.BillingDetails;
import com.qa.ctf.pages.StorePage;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;

public class CustomerSteps extends BaseSteps {

    private final WebDriver driver;
    private final StorePage storePage;
    private final AppContext appContext;

    public CustomerSteps(AppContext appContext, TestContext testContext) {
        super(testContext); // Calls BaseStep constructor to initialize PageFactory
        this.appContext = appContext;

        if (testContext.getDriver() == null) {
            throw new NullPointerException("Driver is not initialized");
        }

        this.driver = testContext.getDriver();
        this.storePage = PageFactory.getStorePage(); // Fetch StorePage via PageFactory
    }

    @Given("I'm a guest user")
    public void i_m_a_guest_user() {
        //driver = DriverFactory.getDriver();
        storePage.load(Endpoint.STORE.url);
    }

    @Given("my billing details are")
    public void my_billing_details_are(BillingDetails billingDetails) {
        appContext.billingDetails = billingDetails;
    }

}
