package com.qa.ctf.steps;

import com.qa.ctf.factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class StepDefinition {

    private WebDriver driver;

    @Given("I'm on the Store page")
    public void i_m_on_the_store_page() {
       driver = DriverFactory.getDriver();
       driver.get("https://askomdch.com/");
    }

    @When("I add a {string} to the cart")
    public void i_add_a_to_the_cart(String string) {
        System.out.println("Yet to Implement");
    }

    @Then("I should see {int} {string} in the cart")
    public void i_should_see_in_the_cart(Integer int1, String string) {
        System.out.println("Yet to Implement");
    }

}
