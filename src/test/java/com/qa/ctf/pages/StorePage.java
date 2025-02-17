package com.qa.ctf.pages;

import com.qa.ctf.base.PageComponent;
import com.qa.ctf.base.PageFactory;
import com.qa.ctf.context.TestContext;
import com.qa.ctf.handler.VerificationHandler;
import com.qa.ctf.handler.WaitHandler;
import com.qa.ctf.objects.StorePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StorePage extends StorePageObject {

    private final VerificationHandler verificationHandler;
    private final PageComponent pageComponent;
    private final WaitHandler waitHandler;

    WebDriver driver;

    public StorePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.verificationHandler = PageFactory.getVerificationHelper();
        this.pageComponent = new PageFactory().getPageComponent();
        this.waitHandler = PageFactory.getWaitHandler();
    }

    public void addToCart(String productName) {
        System.out.println("Product::: "+productName);
        verificationHandler.isElementDisplayed(getTitleText(), getTitleTextLabel());
        PageComponent pageComponent1 = new PageComponent(driver);
        pageComponent1.clickElement(getAddToCartBtn(), productName, getAddToCartBtnLabel());
        waitHandler.waitForElementVisible(getViewCartLink(), getViewCartLinkLabel());
        pageComponent1.clickElement(getViewCartLink(), getViewCartLinkLabel());
//        By addToCartBtn = By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']");
//        verificationHandler.isElementDisplayed(getTitleText(), getTitleTextLabel());
//        WebElement element = driver.findElement(By.xpath(String.format(getAddToCartBtn(), productName)));
        //wait.until(ExpectedConditions.elementToBeClickable(element)).click();
//        element.click();
//        waitHandler.waitForElementVisible(getViewCartLink(), getViewCartLinkLabel());
//        pageComponent.clickElement(getViewCartLink(), getViewCartLinkLabel());
    }

}
