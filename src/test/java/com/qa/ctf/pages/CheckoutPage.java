package com.qa.ctf.pages;

import com.qa.ctf.objects.BillingDetails;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage  extends BasePage {

    @FindBy(id="billing_first_name") private WebElement billFirstNameFld;
    @FindBy(id="billing_last_name") private WebElement billLastNameFld;
    @FindBy(id="billing_address_1") private WebElement billAddressFld;
    @FindBy(id="billing_city") private WebElement billCityFld;
    @FindBy(id="billing_state") private WebElement billStateDropdown;
    @FindBy(id="billing_postcode") private WebElement billZipFld;
    @FindBy(id="billing_email") private WebElement billEmailFld;
    @FindBy(id="place_order") private WebElement placeOrderBtn;
    @FindBy(css=".woocommerce-notice") private WebElement noticeTxt;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage enterBillingFirstName(String billingFirstName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(billFirstNameFld));
        element.clear();
        element.sendKeys(billingFirstName);
        return this;
    }

    public CheckoutPage enterBillingLastName(String billingLastName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(billLastNameFld));
        element.clear();
        element.sendKeys(billingLastName);
        return this;
    }

    public CheckoutPage enterBillingAddress(String billingAddress) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(billAddressFld));
        element.clear();
        element.sendKeys(billingAddress);
        return this;
    }

    public CheckoutPage enterBillingCity(String billingCity) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(billCityFld));
        element.clear();
        element.sendKeys(billingCity);
        return this;
    }

    public CheckoutPage selectBillingState(String billingState) {
        Select select = new Select(wait.until(ExpectedConditions.visibilityOf(billStateDropdown)));
        select.selectByVisibleText(billingState);
        return this;
    }

    public CheckoutPage enterBillingZipcode(String billingZipcode) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(billZipFld));
        element.clear();
        element.sendKeys(billingZipcode);
        return this;
    }

    public CheckoutPage enterBillingEmail(String billingEmail) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(billEmailFld));
        element.clear();
        element.sendKeys(billingEmail);
        return this;
    }

    public CheckoutPage setBillingDetails(BillingDetails billingDetails) {
        return enterBillingFirstName(billingDetails.getBillingFirstName())
                .enterBillingLastName(billingDetails.getBillingLastName())
                .enterBillingAddress(billingDetails.getBillingAddressLine())
                .enterBillingCity(billingDetails.getBillingCity())
                .selectBillingState(billingDetails.getBillingState())
                .enterBillingZipcode(billingDetails.getBillingZipcode())
                .enterBillingEmail(billingDetails.getBillingEmail());
    }

    public CheckoutPage placeOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn)).click();
        return this;
    }

    public String getNotice() {
        return wait.until(ExpectedConditions.visibilityOf(noticeTxt)).getText();
    }

}
