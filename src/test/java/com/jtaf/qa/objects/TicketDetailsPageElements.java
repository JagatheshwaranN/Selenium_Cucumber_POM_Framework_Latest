package com.jtaf.qa.objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class TicketDetailsPageElements {

	@FindBy(how = How.XPATH, using = "//h2[contains(@class,'headerTitle')]")
	public WebElement ticketDetailsHeader;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'flightItenaryHdr')]//p//span[1]")
	public WebElement ticketDetailsFlightName;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'flightItenaryHdr')]//div[@class='makeFlex']//span[1]")
	public WebElement ticketDetailsTravelClass;
	@FindBy(how = How.XPATH, using = "(//div[contains(@class,'itenaryLeft')]//div[contains(@class,'makeFlex')]//div//span)[3]")
	public WebElement ticketDetailsFromPlace;
	@FindBy(how = How.XPATH, using = "(//div[contains(@class,'itenaryLeft')]//div[contains(@class,'makeFlex')]//div//span)[7]")
	public WebElement ticketDetailsToPlace;
	@FindBy(how = How.ID, using = "FARE_SUMMARY")
	public WebElement ticketFareSummary;
	@FindBy(how = How.XPATH, using = "//p[@class='fareRow']//span[2]")
	public WebElement ticketTotalAmount;
}
