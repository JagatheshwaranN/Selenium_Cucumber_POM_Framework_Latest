package com.qa.ctf.objects;

import com.qa.ctf.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StorePageObject extends BasePage {

    @FindBy(xpath = "//h1[text()='Store']")
    protected WebElement titleText;
    protected String titleTextLabel = "Store Page Title";

    @FindBy(css = "a[title='View cart']")
    protected WebElement viewCartLink;
    protected String viewCartLinkLabel = "View Cart Link";

    protected By addToCartBtn = By.xpath("//a[contains(@aria-label,'Blue Shoes')]");
    protected String addToCartBtnLabel = "Add To Cart Button";

    public StorePageObject(WebDriver driver) {
        super(driver);
    }

    public WebElement getTitleText() {
        return titleText;
    }

    public String getTitleTextLabel() {
        return titleTextLabel;
    }

    public WebElement getViewCartLink() {
        return viewCartLink;
    }

    public String getViewCartLinkLabel() {
        return viewCartLinkLabel;
    }

    public By getAddToCartBtn() {
        return addToCartBtn;
    }

    public String getAddToCartBtnLabel() {
        return addToCartBtnLabel;
    }

}
