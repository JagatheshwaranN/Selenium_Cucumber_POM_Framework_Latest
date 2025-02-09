package com.qa.ctf.pages;

import com.qa.ctf.base.PageComponent;
import com.qa.ctf.base.PageFactory;
import com.qa.ctf.handler.VerificationHandler;
import com.qa.ctf.objects.CartPageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class CartPage extends CartPageObject {

    private final VerificationHandler verificationHandler;
    private final PageComponent pageComponent;

    public CartPage(WebDriver driver) {
        super(driver);
        this.verificationHandler = PageFactory.getVerificationHelper();
        this.pageComponent = PageFactory.getPageComponent();
    }

    public String getProductName() {
        //return wait.until(ExpectedConditions.visibilityOf(getProductNameFld())).getText();
        return verificationHandler.readTextValueFromElement(getProductNameFld(), getProductNameFldLabel());
    }

    public int getProductQuantity() {
        //return Integer.parseInt(Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOf(getProductQuantityFld())).getAttribute("value")));
        return Integer.parseInt(verificationHandler.readValueFromInput(getProductQuantityFld(), getProductQuantityFldLabel()));
    }

    public void checkout() {
        //wait.until(ExpectedConditions.visibilityOf(getProceedToCheckoutBtn())).click();
        pageComponent.clickElement(getProceedToCheckoutBtn(), getProceedToCheckoutBtnLabel());
    }

}
