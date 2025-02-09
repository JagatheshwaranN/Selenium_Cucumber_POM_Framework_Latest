package com.qa.ctf.pages;

import com.qa.ctf.base.PageComponent;
import com.qa.ctf.base.PageFactory;
import com.qa.ctf.data.BillingDetails;
import com.qa.ctf.handler.DropDownHandler;
import com.qa.ctf.handler.VerificationHandler;
import com.qa.ctf.objects.CheckoutPageObject;
import io.cucumber.java8.Pa;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage  extends CheckoutPageObject {

    private final VerificationHandler verificationHandler;
    private final PageComponent pageComponent;
    private final DropDownHandler dropDownHandler;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.verificationHandler = PageFactory.getVerificationHelper();
        this.pageComponent = PageFactory.getPageComponent();
        this.dropDownHandler = PageFactory.getDropDownHandler();
    }

    public CheckoutPage enterBillingFirstName(String billingFirstName) {
//        WebElement element = wait.until(ExpectedConditions.visibilityOf(getBillFirstNameFld()));
//        element.clear();
//        element.sendKeys(billingFirstName);
        pageComponent.typeText(getBillFirstNameFld(), billingFirstName, getBillFirstNameFldLabel());
        return this;
    }

    public CheckoutPage enterBillingLastName(String billingLastName) {
//        WebElement element = wait.until(ExpectedConditions.visibilityOf(getBillLastNameFld()));
//        element.clear();
//        element.sendKeys(billingLastName);
        pageComponent.typeText(getBillLastNameFld(), billingLastName, getBillFirstNameFldLabel());
        return this;
    }

    public CheckoutPage enterBillingAddress(String billingAddress) {
//        WebElement element = wait.until(ExpectedConditions.visibilityOf(getBillAddressFld()));
//        element.clear();
//        element.sendKeys(billingAddress);
        pageComponent.typeText(getBillAddressFld(), billingAddress, getBillAddressFldLabel());
        return this;
    }

    public CheckoutPage enterBillingCity(String billingCity) {
//        WebElement element = wait.until(ExpectedConditions.visibilityOf(getBillCityFld()));
//        element.clear();
//        element.sendKeys(billingCity);
        pageComponent.typeText(getBillCityFld(), billingCity, getBillCityFldLabel());
        return this;
    }

    public CheckoutPage selectBillingState(String billingState) {
//        Select select = new Select(wait.until(ExpectedConditions.visibilityOf(getBillStateDropdown())));
//        select.selectByVisibleText(billingState);
        dropDownHandler.selectOptionByVisibleText(getBillStateDropdown(), billingState, getBillStateDropdownLabel());
        return this;
    }

    public CheckoutPage enterBillingZipcode(String billingZipcode) {
//        WebElement element = wait.until(ExpectedConditions.visibilityOf(getBillZipFld()));
//        element.clear();
//        element.sendKeys(billingZipcode);
        pageComponent.typeText(getBillZipFld(), billingZipcode, getBillZipFldLabel());
        return this;
    }

    public CheckoutPage enterBillingEmail(String billingEmail) {
//        WebElement element = wait.until(ExpectedConditions.visibilityOf(getBillEmailFld()));
//        element.clear();
//        element.sendKeys(billingEmail);
        pageComponent.typeText(getBillEmailFld(), billingEmail, getBillEmailFldLabel());
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
        //wait.until(ExpectedConditions.elementToBeClickable(getPlaceOrderBtn())).click();
        pageComponent.clickElement(getPlaceOrderBtn(), getPlaceOrderBtnLabel());
        return this;
    }

    public String getNotice() {
        //return wait.until(ExpectedConditions.visibilityOf(getNoticeTxt())).getText();
        return verificationHandler.readTextValueFromElement(getNoticeTxt(), getNoticeTxtLabel());
    }

}
