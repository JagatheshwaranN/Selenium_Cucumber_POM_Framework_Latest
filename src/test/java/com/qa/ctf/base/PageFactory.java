package com.qa.ctf.base;

import com.qa.ctf.context.TestContext;
import com.qa.ctf.handler.*;
import com.qa.ctf.pages.CartPage;
import com.qa.ctf.pages.CheckoutPage;
import com.qa.ctf.pages.StorePage;
import com.qa.ctf.util.EncryptionManager;
import org.openqa.selenium.WebDriver;

public class PageFactory {

    private static TestContext testContext;

    private static PageComponent pageComponent;

    private static DateTimeHandler datePickerHandler;

    private static DropDownHandler dropDownHandler;

    private static InteractionHandler interactionHandler;

    private static VerificationHandler verificationHelper;

    private static WaitHandler waitHandler;

    private static EncryptionManager encryptionManager;

    private static StorePage storePage;

    private static CartPage cartPage;

    private static CheckoutPage checkoutPage;

    public PageFactory() {
    }

    public PageFactory(TestContext testContext) {
        PageFactory.testContext = testContext;
    }

    public static VerificationHandler getVerificationHelper() {
        return (verificationHelper == null) ? verificationHelper = new VerificationHandler(testContext) : verificationHelper;
    }

    public PageComponent getPageComponent() {
        return (pageComponent == null) ? pageComponent = new PageComponent(testContext, getVerificationHelper()) : pageComponent;
    }

    public static DateTimeHandler getDatePickerHandler() {
        return (datePickerHandler == null) ? datePickerHandler = new DateTimeHandler(testContext, getVerificationHelper()) : datePickerHandler;
    }

    public static DropDownHandler getDropDownHandler() {
        return (dropDownHandler == null) ? dropDownHandler = new DropDownHandler(testContext, getVerificationHelper()) : dropDownHandler;
    }

    public static InteractionHandler getInteractionHandler() {
        return (interactionHandler == null) ? interactionHandler = new InteractionHandler(testContext, getVerificationHelper()) : interactionHandler;
    }

    public static WaitHandler getWaitHandler() {
        return (waitHandler == null) ? waitHandler = new WaitHandler(testContext) : waitHandler;
    }

    public static EncryptionManager getEncryptionManager() {
        return (encryptionManager == null) ? encryptionManager = new EncryptionManager() : encryptionManager;
    }

    public static StorePage getStorePage(WebDriver driver) {
        return storePage == null ? new StorePage(driver) : storePage;
    }

    public static CartPage getCartPage(WebDriver driver) {
        return cartPage == null ? new CartPage(driver) : cartPage;
    }

    public static CheckoutPage getCheckoutPage(WebDriver driver) {
        return checkoutPage == null ? new CheckoutPage(driver) : checkoutPage;
    }

}
