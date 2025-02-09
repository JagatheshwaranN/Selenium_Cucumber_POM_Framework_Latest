package com.qa.ctf.steps;

import com.qa.ctf.constant.Endpoint;
import com.qa.ctf.context.AppContext;
import com.qa.ctf.context.TestContext;
import com.qa.ctf.data.BillingDetails;
import com.qa.ctf.base.PageFactory;
import com.qa.ctf.pages.StorePage;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;

public class CustomerSteps {

    private final AppContext appContext;
    private final StorePage storePage;

    public CustomerSteps(AppContext appContext, TestContext testContext) {
        WebDriver driver = testContext.driver;
        this.appContext = appContext;
        storePage = PageFactory.getStorePage(driver);
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
