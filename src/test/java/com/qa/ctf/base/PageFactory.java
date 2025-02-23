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

    private static final ThreadLocal<PageComponent> pageComponentThreadLocal = ThreadLocal.withInitial(()->new PageComponent(testContext, getVerificationHelper()));

    private static final ThreadLocal<DateTimeHandler> datePickerHandlerThreadLocal = ThreadLocal.withInitial(()->new DateTimeHandler(testContext, getVerificationHelper()));

    private static final ThreadLocal<DropDownHandler> dropDownHandlerThreadLocal = ThreadLocal.withInitial(() -> new DropDownHandler(testContext, getVerificationHelper()));

    private static final ThreadLocal<InteractionHandler> interactionHandlerThreadLocal = ThreadLocal.withInitial(() -> new InteractionHandler(testContext, getVerificationHelper()));

    private static final ThreadLocal<VerificationHandler> verificationHelperThreadLocal = ThreadLocal.withInitial(()->new VerificationHandler(testContext));

    private static final ThreadLocal<WaitHandler> waitHandlerThreadLocal = ThreadLocal.withInitial(()-> new WaitHandler(testContext));

    private static final ThreadLocal<EncryptionManager> encryptionManagerThreadLocal = ThreadLocal.withInitial(EncryptionManager::new);

    private static final ThreadLocal<StorePage> storePageThreadLocal = ThreadLocal.withInitial(() -> new StorePage(testContext.getDriver()));

    private static final ThreadLocal<CartPage> cartPageThreadLocal = ThreadLocal.withInitial(() -> new CartPage(testContext.getDriver()));

    private static final ThreadLocal<CheckoutPage> checkoutPageThreadLocal = ThreadLocal.withInitial(() -> new CheckoutPage(testContext.getDriver()));

//    private static PageComponent pageComponent;
//
//    private static DateTimeHandler datePickerHandler;
//
//    private static DropDownHandler dropDownHandler;
//
//    private static InteractionHandler interactionHandler;
//
//    private static VerificationHandler verificationHelper;
//
//    private static WaitHandler waitHandler;
//
//    private static EncryptionManager encryptionManager;
//
//    private static StorePage storePage;
//
//    private static CartPage cartPage;
//
//    private static CheckoutPage checkoutPage;

    public PageFactory() {
    }

    public PageFactory(TestContext testContext) {
        PageFactory.testContext = testContext;
    }

    public static VerificationHandler getVerificationHelper() {
//        return (verificationHelper == null) ? verificationHelper = new VerificationHandler(testContext) : verificationHelper;
        return verificationHelperThreadLocal.get();
    }


    public static PageComponent getPageComponent() {
//        return (pageComponent == null) ? pageComponent = new PageComponent(testContext, getVerificationHelper()) : pageComponent;
        return pageComponentThreadLocal.get();
    }

    public static DateTimeHandler getDatePickerHandler() {
        //return (datePickerHandler == null) ? datePickerHandler = new DateTimeHandler(testContext, getVerificationHelper()) : datePickerHandler;
        return datePickerHandlerThreadLocal.get();
    }

    public static DropDownHandler getDropDownHandler() {
        //return (dropDownHandler == null) ? dropDownHandler = new DropDownHandler(testContext, getVerificationHelper()) : dropDownHandler;
        return dropDownHandlerThreadLocal.get();
    }

    public static InteractionHandler getInteractionHandler() {
        //return (interactionHandler == null) ? interactionHandler = new InteractionHandler(testContext, getVerificationHelper()) : interactionHandler;
        return interactionHandlerThreadLocal.get();
    }

    public static WaitHandler getWaitHandler() {
        //return (waitHandler == null) ? waitHandler = new WaitHandler(testContext) : waitHandler;
        return waitHandlerThreadLocal.get();
    }

    public static EncryptionManager getEncryptionManager() {
        //return (encryptionManager == null) ? encryptionManager = new EncryptionManager() : encryptionManager;
        return encryptionManagerThreadLocal.get();
    }

    public static StorePage getStorePage(WebDriver driver) {
        //return storePage == null ? new StorePage(driver) : storePage;
        return storePageThreadLocal.get();
    }

    public static CartPage getCartPage(WebDriver driver) {
        //return cartPage == null ? new CartPage(driver) : cartPage;
        return cartPageThreadLocal.get();
    }

    public static CheckoutPage getCheckoutPage(WebDriver driver) {
        //return checkoutPage == null ? new CheckoutPage(driver) : checkoutPage;
        return checkoutPageThreadLocal.get();
    }

}
