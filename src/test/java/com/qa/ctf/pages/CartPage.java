package com.qa.ctf.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class CartPage extends BasePage {

    @FindBy(css="td[class='product-name'] a") private WebElement productNameField;
    @FindBy(css="input[type='number']") private WebElement productQuantityField;
    @FindBy(css=".checkout-button") private WebElement proceedToCheckoutBtn;


    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOf(productNameField)).getText();
    }

    public int getProductQuantity() {
        return Integer.parseInt(Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOf(productQuantityField)).getAttribute("value")));
    }

    public void checkout() {
        wait.until(ExpectedConditions.visibilityOf(proceedToCheckoutBtn)).click();
    }

}
