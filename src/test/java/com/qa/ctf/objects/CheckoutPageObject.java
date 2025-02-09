package com.qa.ctf.objects;

import com.qa.ctf.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPageObject extends BasePage {

    @FindBy(id = "billing_first_name")
    protected WebElement billFirstNameFld;
    protected String billFirstNameFldLabel = "Billing First Name";

    @FindBy(id = "billing_last_name")
    protected WebElement billLastNameFld;
    protected String billLastNameFldLabel = "Billing Last Name";

    @FindBy(id = "billing_address_1")
    protected WebElement billAddressFld;
    protected String billAddressFldLabel = "Billing Address";

    @FindBy(id = "billing_city")
    protected WebElement billCityFld;
    protected String billCityFldLabel = "Billing City";

    @FindBy(id = "billing_state")
    protected WebElement billStateDropdown;
    protected String billStateDropdownLabel = "Billing State";

    @FindBy(id = "billing_postcode")
    protected WebElement billZipFld;
    protected String billZipFldLabel = "Billing Postcode";

    @FindBy(id = "billing_email")
    protected WebElement billEmailFld;
    protected String billEmailFldLabel = "Billing Email";

    @FindBy(id = "place_order")
    protected WebElement placeOrderBtn;
    protected String placeOrderBtnLabel = "Place Order";

    @FindBy(css = ".woocommerce-notice")
    protected WebElement noticeTxt;
    protected String noticeTxtLabel = "Place Order Message";


    public CheckoutPageObject(WebDriver driver) {
        super(driver);
    }

    public WebElement getBillFirstNameFld() {
        return billFirstNameFld;
    }

    public String getBillFirstNameFldLabel() {
        return billFirstNameFldLabel;
    }

    public WebElement getBillLastNameFld() {
        return billLastNameFld;
    }

    public String getBillLastNameFldLabel() {
        return billLastNameFldLabel;
    }

    public WebElement getBillAddressFld() {
        return billAddressFld;
    }

    public String getBillAddressFldLabel() {
        return billAddressFldLabel;
    }

    public WebElement getBillCityFld() {
        return billCityFld;
    }

    public String getBillCityFldLabel() {
        return billCityFldLabel;
    }

    public WebElement getBillStateDropdown() {
        return billStateDropdown;
    }

    public String getBillStateDropdownLabel() {
        return billStateDropdownLabel;
    }

    public WebElement getBillZipFld() {
        return billZipFld;
    }

    public String getBillZipFldLabel() {
        return billZipFldLabel;
    }

    public WebElement getBillEmailFld() {
        return billEmailFld;
    }

    public String getBillEmailFldLabel() {
        return billEmailFldLabel;
    }

    public WebElement getPlaceOrderBtn() {
        return placeOrderBtn;
    }

    public String getPlaceOrderBtnLabel() {
        return placeOrderBtnLabel;
    }

    public WebElement getNoticeTxt() {
        return noticeTxt;
    }

    public String getNoticeTxtLabel() {
        return noticeTxtLabel;
    }
}
