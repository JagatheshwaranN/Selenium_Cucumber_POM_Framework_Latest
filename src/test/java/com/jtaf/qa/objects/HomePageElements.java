package com.jtaf.qa.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePageElements {

	@FindBy(how = How.CSS, using = ".ic_circularclose_grey")
	public WebElement AppLaunchPopupClose;
	@FindBy(how = How.XPATH, using = "(//span[text()='Flights'])[1]")
	public WebElement HomePageHeader;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'sc-eGRUor jFqzBD')]//..//span[@class='sc-ctqQKy jgGUHT']")
	public WebElement OneWayTrip;
	@FindBy(how = How.XPATH, using = "//label[@for='fromCity']")
	public WebElement FromLocationSection;
	@FindBy(how = How.XPATH, using = "//input[@id='fromCity']")
	public WebElement FromLocation;
	@FindBy(how = How.XPATH, using = "//ul[@role='listbox']//li[@role='option']//p[text()='@1@']")
	public WebElement FromLocationSuggestion;
	@FindBy(how = How.XPATH, using = "//label[@for='toCity']")
	public WebElement ToLocationSection;
	@FindBy(how = How.XPATH, using = "//input[@id='toCity']")
	public WebElement ToLocation;
	@FindBy(how = How.XPATH, using = "//ul[@role='listbox']//li[@role='option']//p[text()='@1@']")
	public WebElement ToLocationSuggestion;
	@FindBy(how = How.XPATH, using = "//label[@for='departure']")
	public WebElement DepatureSection;
	@FindBy(how = How.XPATH, using = "(//div[@class='DayPicker-Caption' and @role='heading'])[2]")
	public WebElement MonthTextInDatePicker;
	@FindBy(how = How.XPATH, using = "//span[@role='button' and @class='DayPicker-NavButton DayPicker-NavButton--next']")
	public WebElement MonthNavigatorInDatePicker;
	@FindBy(how = How.XPATH, using = "//div[@class='DayPicker-Week']/div[@class='DayPicker-Day']//p[text()='@1@']")
	public WebElement DateInCalendar;
	@FindBy(how = How.XPATH, using = "//label[@for='travellers']")
	public WebElement TravelSelection;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'guestCounter')]//li[@data-cy='adults-2']")
	public WebElement TravelSelectionPassenger;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'guestCounter')]//li[@data-cy='travelClass-1']")
	public WebElement TravelSelectionClass;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'primaryBtn btnApply pushRight')]")
	public WebElement TravelSelectionApply;
	@FindBy(how = How.XPATH, using = "//label[@for='travellers']//p[2]")
	public WebElement TravelClassDisplay;
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'primaryBtn font24 latoBold widgetSearchBtn')]")
	public WebElement SearchFlights;

	public By FromLocationSuggestion1 = By.xpath("//ul[@role='listbox']//li[@role='option']//p[text()='@1@']");
	public By ToLocationSuggestion1 = By.xpath("//ul[@role='listbox']//li[@role='option']//p[text()='@1@']");
	public By DateInCalendar1 = By.xpath("//div[@class='DayPicker-Week']/div[@class='DayPicker-Day']//p[text()='@1@']");
	public By TravelSelectionPassenger1 = By.xpath("//ul[contains(@class,'guestCounter')]//li[@data-cy='adults-@1@']");
	public By TravelSelectionClass1 = By.xpath("//ul[contains(@class,'guestCounter')]//li[@data-cy='travelClass-@1@']");
	
}
