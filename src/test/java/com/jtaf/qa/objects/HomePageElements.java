package com.jtaf.qa.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePageElements {

	@FindBy(how = How.CSS, using = ".ic_circularclose_grey")
	public WebElement appLaunchPopupClose;
	@FindBy(how = How.XPATH, using = "(//span[text()='Flights'])[1]")
	public WebElement homePageHeader;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'sc-eGRUor jFqzBD')]//..//span[@class='sc-ctqQKy jgGUHT']")
	public WebElement oneWayTrip;
	@FindBy(how = How.XPATH, using = "//label[@for='fromCity']")
	public WebElement fromLocationSection;
	@FindBy(how = How.XPATH, using = "//input[@id='fromCity']")
	public WebElement fromLocation;
	@FindBy(how = How.XPATH, using = "//ul[@role='listbox']//li[@role='option']//p[text()='@1@']")
	public WebElement fromLocationSuggestion;
	@FindBy(how = How.XPATH, using = "//label[@for='toCity']")
	public WebElement toLocationSection;
	@FindBy(how = How.XPATH, using = "//input[@id='toCity']")
	public WebElement toLocation;
	@FindBy(how = How.XPATH, using = "//ul[@role='listbox']//li[@role='option']//p[text()='@1@']")
	public WebElement toLocationSuggestion;
	@FindBy(how = How.XPATH, using = "//label[@for='departure']")
	public WebElement depatureSection;
	@FindBy(how = How.XPATH, using = "(//div[@class='DayPicker-Caption' and @role='heading'])[2]")
	public WebElement monthTextInDatePicker;
	@FindBy(how = How.XPATH, using = "//span[@role='button' and @class='DayPicker-NavButton DayPicker-NavButton--next']")
	public WebElement monthNavigatorInDatePicker;
	@FindBy(how = How.XPATH, using = "//div[@class='DayPicker-Week']/div[@class='DayPicker-Day']//p[text()='@1@']")
	public WebElement dateInCalendar;
	@FindBy(how = How.XPATH, using = "//label[@for='travellers']")
	public WebElement travelSelection;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'guestCounter')]//li[@data-cy='adults-2']")
	public WebElement travelSelectionPassenger;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'guestCounter')]//li[@data-cy='travelClass-1']")
	public WebElement travelSelectionClass;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'primaryBtn btnApply pushRight')]")
	public WebElement travelSelectionApply;
	@FindBy(how = How.XPATH, using = "//label[@for='travellers']//p[2]")
	public WebElement travelClassDisplay;
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'primaryBtn font24 latoBold widgetSearchBtn')]")
	public WebElement searchFlights;

	public By fromLocationSuggestion1 = By.xpath("//ul[@role='listbox']//li[@role='option']//p[text()='@1@']");
	public By toLocationSuggestion1 = By.xpath("//ul[@role='listbox']//li[@role='option']//p[text()='@1@']");
	public By dateInCalendar1 = By.xpath("//div[@class='DayPicker-Week']/div[@class='DayPicker-Day']//p[text()='@1@']");
	public By travelSelectionPassenger1 = By.xpath("//ul[contains(@class,'guestCounter')]//li[@data-cy='adults-@1@']");
	public By travelSelectionClass1 = By.xpath("//ul[contains(@class,'guestCounter')]//li[@data-cy='travelClass-@1@']");
	
}
