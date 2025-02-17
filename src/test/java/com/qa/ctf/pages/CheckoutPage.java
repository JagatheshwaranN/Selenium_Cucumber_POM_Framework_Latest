package com.qa.ctf.pages;

import com.qa.ctf.base.PageComponent;
import com.qa.ctf.base.PageFactory;
import com.qa.ctf.data.BillingDetails;
import com.qa.ctf.handler.DropDownHandler;
import com.qa.ctf.handler.VerificationHandler;
import com.qa.ctf.objects.CheckoutPageObject;
import org.openqa.selenium.WebDriver;

public class CheckoutPage  extends CheckoutPageObject {

    private final VerificationHandler verificationHandler;
    private final PageComponent pageComponent;
    private final DropDownHandler dropDownHandler;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.verificationHandler = PageFactory.getVerificationHelper();
        this.pageComponent = new PageFactory().getPageComponent();
        this.dropDownHandler = PageFactory.getDropDownHandler();
    }

    public CheckoutPage enterBillingFirstName(String billingFirstName) {
        pageComponent.typeText(getBillFirstNameFld(), billingFirstName, getBillFirstNameFldLabel());
        return this;
    }

    public CheckoutPage enterBillingLastName(String billingLastName) {
        pageComponent.typeText(getBillLastNameFld(), billingLastName, getBillFirstNameFldLabel());
        return this;
    }

    public CheckoutPage enterBillingAddress(String billingAddress) {
        pageComponent.typeText(getBillAddressFld(), billingAddress, getBillAddressFldLabel());
        return this;
    }

    public CheckoutPage enterBillingCity(String billingCity) {
        pageComponent.typeText(getBillCityFld(), billingCity, getBillCityFldLabel());
        return this;
    }

    public CheckoutPage selectBillingState(String billingState) {
        dropDownHandler.selectOptionByVisibleText(getBillStateDropdown(), billingState, getBillStateDropdownLabel());
        return this;
    }

    public CheckoutPage enterBillingZipcode(String billingZipcode) {
        pageComponent.typeText(getBillZipFld(), billingZipcode, getBillZipFldLabel());
        return this;
    }

    public CheckoutPage enterBillingEmail(String billingEmail) {
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
        pageComponent.clickElement(getPlaceOrderBtn(), getPlaceOrderBtnLabel());
        return this;
    }

    public String getNotice() {
        return verificationHandler.readTextValueFromElement(getNoticeTxt(), getNoticeTxtLabel());
    }

}
