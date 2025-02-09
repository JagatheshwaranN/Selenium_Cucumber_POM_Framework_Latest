package com.qa.ctf.objects;

import com.qa.ctf.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPageObject extends BasePage {

    @FindBy(css = "td[class='product-name'] a")
    protected WebElement productNameFld;
    protected String productNameFldLabel = "Product Name";

    @FindBy(css = "input[type='number']")
    protected WebElement productQuantityFld;
    protected String productQuantityFldLabel = "Product Qty";

    @FindBy(css = ".checkout-button")
    protected WebElement proceedToCheckoutBtn;
    protected String proceedToCheckoutBtnLabel = "Proceed To Checkout Button";

    public CartPageObject(WebDriver driver) {
        super(driver);
    }

    public WebElement getProductNameFld() {
        return productNameFld;
    }

    public String getProductNameFldLabel() {
        return productNameFldLabel;
    }

    public WebElement getProductQuantityFld() {
        return productQuantityFld;
    }

    public String getProductQuantityFldLabel() {
        return productQuantityFldLabel;
    }

    public WebElement getProceedToCheckoutBtn() {
        return proceedToCheckoutBtn;
    }

    public String getProceedToCheckoutBtnLabel() {
        return proceedToCheckoutBtnLabel;
    }

}
