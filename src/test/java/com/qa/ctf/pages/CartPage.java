package com.qa.ctf.pages;

import com.qa.ctf.base.PageComponent;
import com.qa.ctf.base.PageFactory;
import com.qa.ctf.handler.VerificationHandler;
import com.qa.ctf.objects.CartPageObject;
import org.openqa.selenium.WebDriver;

public class CartPage extends CartPageObject {

    private final VerificationHandler verificationHandler;
    private final PageComponent pageComponent;

    public CartPage(WebDriver driver) {
        super(driver);
        this.verificationHandler = PageFactory.getVerificationHelper();
        this.pageComponent = new PageFactory().getPageComponent();
    }

    public String getProductName() {
        return verificationHandler.readTextValueFromElement(getProductNameFld(), getProductNameFldLabel());
    }

    public int getProductQuantity() {
        return Integer.parseInt(verificationHandler.readValueFromInput(getProductQuantityFld(), getProductQuantityFldLabel()));
    }

    public void checkout() {
        pageComponent.clickElement(getProceedToCheckoutBtn(), getProceedToCheckoutBtnLabel());
    }

}
