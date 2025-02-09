package com.qa.ctf.pages;

import com.qa.ctf.base.PageComponent;
import com.qa.ctf.base.PageFactory;
import com.qa.ctf.handler.VerificationHandler;
import com.qa.ctf.handler.WaitHandler;
import com.qa.ctf.objects.StorePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StorePage extends StorePageObject {

    private final VerificationHandler verificationHandler;
    private final PageComponent pageComponent;
    private final WaitHandler waitHandler;

    public StorePage(WebDriver driver) {
        super(driver);
        this.verificationHandler = PageFactory.getVerificationHelper();
        this.pageComponent = PageFactory.getPageComponent();
        this.waitHandler = PageFactory.getWaitHandler();
    }

    public void addToCart(String productName) {
        By addToCartBtn = By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']");
//        wait.until(ExpectedConditions.visibilityOf(getTitleText()));
//        wait.until(ExpectedConditions.elementToBeClickable(getViewCartLink())).click();
        verificationHandler.isElementDisplayed(getTitleText(), getTitleTextLabel());
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
        waitHandler.waitForElementVisible(getViewCartLink(), getViewCartLinkLabel());
        pageComponent.clickElement(getViewCartLink(), getViewCartLinkLabel());
    }

}
