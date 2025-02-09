package com.qa.ctf.objects;

import com.qa.ctf.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StorePageObject extends BasePage {

    @FindBy(css = "a[title='View cart']")
    protected WebElement viewCartLink;
    protected String viewCartLinkLabel = "View Cart Link";

    @FindBy(xpath = "//h1[text()='Store']")
    protected WebElement titleText;
    protected String titleTextLabel = "Store Page Title";

    public StorePageObject(WebDriver driver) {
        super(driver);
    }

    public WebElement getViewCartLink() {
        return viewCartLink;
    }

    public String getViewCartLinkLabel() {
        return viewCartLinkLabel;
    }

    public WebElement getTitleText() {
        return titleText;
    }

    public String getTitleTextLabel() {
        return titleTextLabel;
    }

}
