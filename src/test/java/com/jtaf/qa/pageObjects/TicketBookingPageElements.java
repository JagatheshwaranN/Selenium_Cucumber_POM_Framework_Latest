package com.jtaf.qa.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class TicketBookingPageElements {

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button buttonSecondry buttonBig')]")
	public WebElement ticketBookPopupClose;
	@FindBy(how = How.XPATH, using = "//p[contains(@class,'filtersHeading') and text()='One Way Price']")
	public WebElement ticketBookingSection;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'price_sorter')]//span[@class='pointer']")
	public WebElement priceSort;
	@FindBy(how = How.XPATH, using = "(//button[contains(@class,'ViewFareBtn')]//span[@class='appendRight8'])[1]")
	public WebElement viewPrices;
	@FindBy(how = How.XPATH, using = "(//div[@class='priceSection']//p)[1]")
	public WebElement priceList;
	@FindBy(how = How.XPATH, using = "(//div[contains(@class,'viewFareBtnCol')]//button[contains(@id,'bookbutton')])[1]")
	public WebElement book;
	@FindBy(how = How.XPATH, using = "(//p[contains(@class,'airlineName')])[1]")
	public WebElement flightName;
}
