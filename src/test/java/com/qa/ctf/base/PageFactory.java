package com.qa.ctf.base;

import com.qa.ctf.context.TestContext;
import com.qa.ctf.handler.*;
import com.qa.ctf.pages.CartPage;
import com.qa.ctf.pages.CheckoutPage;
import com.qa.ctf.pages.StorePage;
import com.qa.ctf.util.EncryptionManager;
import org.openqa.selenium.WebDriver;

public class PageFactory {

    private static final ThreadLocal<TestContext> testContextThreadLocal = new ThreadLocal<>();

    public void setTestContext(TestContext testContext) {
        testContextThreadLocal.set(testContext);
    }

    private static TestContext getTestContext() {
        return testContextThreadLocal.get();
    }

//    private static WebDriver getDriver() {
//        return getTestContext() != null ? getTestContext().getDriver() : null;
//    }

    private static WebDriver getDriver() {
        TestContext testContext = getTestContext();
        return (testContext != null) ? testContext.getDriver() : null;
    }

    private static final ThreadLocal<PageComponent> pageComponentThreadLocal =
            ThreadLocal.withInitial(() -> new PageComponent(getTestContext(), getVerificationHelper()));

    private static final ThreadLocal<DateTimeHandler> datePickerHandlerThreadLocal =
            ThreadLocal.withInitial(() -> new DateTimeHandler(getTestContext(), getVerificationHelper()));

    private static final ThreadLocal<DropDownHandler> dropDownHandlerThreadLocal =
            ThreadLocal.withInitial(() -> new DropDownHandler(getTestContext(), getVerificationHelper()));

    private static final ThreadLocal<InteractionHandler> interactionHandlerThreadLocal =
            ThreadLocal.withInitial(() -> new InteractionHandler(getTestContext(), getVerificationHelper()));

    private static final ThreadLocal<VerificationHandler> verificationHelperThreadLocal =
            ThreadLocal.withInitial(() -> new VerificationHandler(getTestContext()));

    private static final ThreadLocal<WaitHandler> waitHandlerThreadLocal =
            ThreadLocal.withInitial(() -> new WaitHandler(getTestContext()));

    private static final ThreadLocal<EncryptionManager> encryptionManagerThreadLocal =
            ThreadLocal.withInitial(EncryptionManager::new);

//    private static final ThreadLocal<StorePage> storePageThreadLocal =
//            ThreadLocal.withInitial(() -> new StorePage(getDriver()));

    private static final ThreadLocal<StorePage> storePageThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<CartPage> cartPageThreadLocal =
            ThreadLocal.withInitial(() -> new CartPage(getDriver()));

    private static final ThreadLocal<CheckoutPage> checkoutPageThreadLocal =
            ThreadLocal.withInitial(() -> new CheckoutPage(getDriver()));

    public static VerificationHandler getVerificationHelper() {
        return verificationHelperThreadLocal.get();
    }

    public static PageComponent getPageComponent() {
        return pageComponentThreadLocal.get();
    }

    public static DateTimeHandler getDatePickerHandler() {
        return datePickerHandlerThreadLocal.get();
    }

    public static DropDownHandler getDropDownHandler() {
        return dropDownHandlerThreadLocal.get();
    }

    public static InteractionHandler getInteractionHandler() {
        return interactionHandlerThreadLocal.get();
    }

    public static WaitHandler getWaitHandler() {
        return waitHandlerThreadLocal.get();
    }

    public static EncryptionManager getEncryptionManager() {
        return encryptionManagerThreadLocal.get();
    }

//    public static StorePage getStorePage() {
//        return storePageThreadLocal.get();
//    }

//    public static StorePage getStorePage() {
//        if (storePageThreadLocal.get() == null) {
//            storePageThreadLocal.set(new StorePage(getTestContext().getDriver())); // Ensure a fresh driver per thread
//        }
//        return storePageThreadLocal.get();
//    }

//    public static StorePage getStorePage() {
//        if (storePageThreadLocal.get() == null) {
//            WebDriver driver = getDriver();
//            if (driver != null) {
//                storePageThreadLocal.set(new StorePage(driver)); // Ensure fresh driver
//            } else {
//                throw new NullPointerException("Driver is not available in TestContext.");
//            }
//        }
//        return storePageThreadLocal.get();
//    }

    public static StorePage getStorePage(WebDriver driver) {
        //WebDriver driver = getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver is not initialized.");
        }

        //if (storePageThreadLocal.get() == null) {
            storePageThreadLocal.set(new StorePage(driver)); // Ensure fresh driver per thread
        //}
        return storePageThreadLocal.get();
    }

    public static CartPage getCartPage() {
        return cartPageThreadLocal.get();
    }

    public static CheckoutPage getCheckoutPage() {
        return checkoutPageThreadLocal.get();
    }
}
