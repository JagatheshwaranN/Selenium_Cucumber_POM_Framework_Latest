package com.jtaf.qa.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.jtaf.qa.base.BasePage;
import com.jtaf.qa.helpers.AlertHelper;
import com.jtaf.qa.helpers.BrowserHelper;
import com.jtaf.qa.helpers.DropDownHelper;
import com.jtaf.qa.helpers.JavaScriptHelper;
import com.jtaf.qa.helpers.MouseActionHelper;
import com.jtaf.qa.helpers.ReusableHelper;
import com.jtaf.qa.helpers.VerificationHelper;
import com.jtaf.qa.utilities.FileReaderUtility;
import com.jtaf.qa.utilities.LoggerUtility;

/**
 * 
 * @author Jaga
 *
 */
public class HomePage extends BasePage {

	private static Logger log = LoggerUtility.getLog(HomePage.class);

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
//	@FindBy(how = How.XPATH, using = "//span[@class='fswTrvl__done']")
//	public WebElement calendarDone;
	@FindBy(how = How.XPATH, using = "//label[@for='travellers']")
	public WebElement travelSelection;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'guestCounter')]//li[@data-cy='adults-2']")
	public WebElement travelSelectionPassenger;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'guestCounter')]//li[@data-cy='travelClass-1']")
	public WebElement travelSelectionClass;
	@FindBy(how = How.XPATH, using = "//label[@for='travellers']//p[2]")
	public WebElement travelClassDisplay;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'primaryBtn btnApply pushRight')]")
	public WebElement travelSelectionApply;
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'primaryBtn font24 latoBold widgetSearchBtn')]")
	public WebElement searchFlights;

	private By fromLocationSuggestion1 = By.xpath("//ul[@role='listbox']//li[@role='option']//p[text()='@1@']");
	private By toLocationSuggestion1 = By.xpath("//ul[@role='listbox']//li[@role='option']//p[text()='@1@']");
	private By dateInCalendar1 = By
			.xpath("//div[@class='DayPicker-Week']/div[@class='DayPicker-Day']//p[text()='@1@']");

	AlertHelper alertHelper = new AlertHelper(getDriver());
	BrowserHelper browserHelper = new BrowserHelper(getDriver());
	DropDownHelper dropDownHelper = new DropDownHelper();
	JavaScriptHelper javaScriptHelper = new JavaScriptHelper(getDriver());
	MouseActionHelper mouseActionHelper = new MouseActionHelper(getDriver());
	VerificationHelper verificationHelper = new VerificationHelper();
	ReusableHelper reusableHelper = new ReusableHelper(getDriver());

	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public String getHomePageTitle() {
		return getPageTitle();
	}

	public String getHomePageHeader() {
		return getPageHeader(homePageHeader);
	}

	public WebElement getAppLaunchPopupClose() {
		return appLaunchPopupClose;
	}

	public WebElement getOneWayTrip() {
		return oneWayTrip;
	}

	public WebElement getFromLocationSection() {
		return fromLocationSection;
	}

	public WebElement getFromLocation() {
		return fromLocation;
	}

	public WebElement getToLocationSection() {
		return toLocationSection;
	}

	public WebElement getToLocation() {
		return toLocation;
	}

	public WebElement getDepatureDate() {
		return depatureSection;
	}

	public WebElement getMonthTextInDatePicker() {
		return monthTextInDatePicker;
	}

	public WebElement getMonthNavigatorInDatePicker() {
		return monthNavigatorInDatePicker;
	}

	public WebElement getTravelSelection() {
		return travelSelection;
	}

	public WebElement getTravelSelectionPassenger() {
		return travelSelectionPassenger;
	}

	public WebElement getTravelSelectionClass() {
		return travelSelectionClass;
	}

	public WebElement getTravelClassDisplay() {
		return travelClassDisplay;
	}

	public WebElement getSearchFlights() {
		return searchFlights;
	}

	public By getFromLocationSuggestion1() {
		return fromLocationSuggestion1;
	}

	public By getToLocationSuggestion1() {
		return toLocationSuggestion1;
	}

	public By getDateInCalendar1() {
		return dateInCalendar1;
	}

//	public WebElement getCalendarDone() {
//		return calendarDone;
//	}

	public WebElement getTravelSelectionApply() {
		return travelSelectionApply;
	}

	public void verifyHomePageTitle() {
		try {
			Thread.sleep(2000);
			reusableHelper.elementClick(getAppLaunchPopupClose(), "appLaunchPopupClose");
			browserHelper.getCurrentPageUrl();
			Assert.assertEquals(getHomePageTitle(), FileReaderUtility.getTestData("home.page.title"));
		} catch (Exception ex) {
			log.info("Error occured while check home page title" + "\n" + ex);
			Assert.fail();
		}
	}

	public TicketBookingPage enterTravelDetails(String fromLocation, String toLocation, String travelClass,
			String month, String day) {
		try {
			reusableHelper.elementClick(getFromLocationSection(), "fromLocationSection");
			reusableHelper.enterText(getFromLocation(), fromLocation, "fromLocation");
			reusableHelper.elementClick(getFromLocationSuggestion1(),
					FileReaderUtility.getTestData("from.location.suggestion"), "fromLocationSuggestion1");
			reusableHelper.elementClick(getToLocationSection(), "toLocationSection");
			reusableHelper.enterText(getToLocation(), toLocation, "toLocation");
			reusableHelper.elementClick(getToLocationSuggestion1(),
					FileReaderUtility.getTestData("to.location.suggestion"), "toLocationSuggestion");
			// reusableHelper.elementClick(getDepatureDate(), "depatureDate");
			selectDate(month, day);
			// reusableHelper.elementClick(getCalendarDone(), "CalendarDone");
			reusableHelper.elementClick(getTravelSelection(), "travelSelection");
			// dropDownHelper.selectByValue(getTravelSelectionClass(), travelClass,
			// "travelSelectionClass");
			reusableHelper.elementClick(getTravelSelectionPassenger(), "travelSelectionPassenger");
			reusableHelper.elementClick(getTravelSelectionClass(), "travelSelectionClass");
			reusableHelper.elementClick(getTravelSelectionApply(), "TravelSelectionDone");
			ReusableHelper.setAnyElement("fromLocation",
					verificationHelper.readValueFromInput(getFromLocation(), "fromLocation"));
			ReusableHelper.setAnyElement("toLocation",
					verificationHelper.readValueFromInput(getToLocation(), "toLocation"));
			ReusableHelper.setAnyElement("travelClass",
					verificationHelper.readTextValueFromElement(getTravelSelection(), "travelClass"));
			reusableHelper.elementClick(getSearchFlights(), "searchButton");
		} catch (Exception ex) {
			log.info("Error occured while enter travel details" + "\n" + ex);
			Assert.fail();
		}
		return getInstance(TicketBookingPage.class);
	}

	public void selectDate(String month, String day) {
		try {
			while (true) {
				String monthInDatePicker = getMonthTextInDatePicker().getText();
				if (monthInDatePicker.equals(month)) {
					break;
				} else {
					getMonthNavigatorInDatePicker().click();
				}
			}
			reusableHelper.elementClick(getDateInCalendar1(), day, "dateInCalendar");
		} catch (Exception ex) {
			log.info("Error occured while select date from calendar" + "\n" + ex);
			Assert.fail();
		}
	}

}
