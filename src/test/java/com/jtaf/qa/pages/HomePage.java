package com.jtaf.qa.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
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

	@FindBy(how = How.CSS, using = ".logSprite.icClose")
	public WebElement appLaunchPopupClose;
	@FindBy(how = How.XPATH, using = "//h2[text()='Domestic and International Flights']")
	public WebElement homePageHeader;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'sc-eGRUor jFqzBD')]//..//span[@class='sc-ctqQKy jgGUHT']")
	public WebElement oneWayTrip;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'fswWidgetLabel') and text()='From']")
	public WebElement fromLocationSection;
	@FindBy(how = How.XPATH, using = "//div[@class='sc-iUKqMP kjxDYN']//input")
	public WebElement fromLocation;
	@FindBy(how = How.XPATH, using = "//ul[@id='autoSuggest-list']//li//div[@class='sc-jObWnj bRMwkb']//span[text()='@1@']")
	public WebElement fromLocationSuggestion;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'fswWidgetLabel') and text()='To']")
	public WebElement toLocationSection;
	@FindBy(how = How.XPATH, using = "//div[@class='sc-iUKqMP kjxDYN']//input")
	public WebElement toLocation;
	@FindBy(how = How.XPATH, using = "//ul[@id='autoSuggest-list']//li//div[@class='sc-jObWnj bRMwkb']//span[text()='@1@']")
	public WebElement toLocationSuggestion;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'fswWidgetLabel') and text()='Departure']")
	public WebElement depatureSection;
	@FindBy(how = How.XPATH, using = "//div[@class='DayPicker-Caption' and @role='heading']")
	public WebElement monthTextInDatePicker;
	@FindBy(how = How.XPATH, using = "//span[@role='button' and @class='DayPicker-NavButton DayPicker-NavButton--next']")
	public WebElement monthNavigatorInDatePicker;
	@FindBy(how = How.XPATH, using = "//div[@class='DayPicker-Week']/div[@class='DayPicker-Day']//p[text()='@1@']")
	public WebElement dateInCalendar;
	@FindBy(how = How.XPATH, using = "//span[@class='fswTrvl__done']")
	public WebElement calendarDone;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'fswWidgetLabel') and text()='Travellers & Class']")
	public WebElement travelSelection;
	@FindBy(how = How.XPATH, using = "(//div[@class='sc-fWCJzd eRvjBC']//span[@class='sc-dvQaRk ghZzRT'])[2]")
	public WebElement travelSelectionPassenger;
	@FindBy(how = How.XPATH, using = "//li[text()='premium economy']")
	public WebElement travelSelectionClass;
	@FindBy(how = How.XPATH, using = "//a[@class='sc-jQrDum gMuQGX']")
	public WebElement travelSelectionDone;
	@FindBy(how = How.XPATH, using = "//span[@class='sc-XxNYO hiLeUc']")
	public WebElement searchFlights;

	private By fromLocationSuggestion1 = By
			.xpath("//ul[@id='autoSuggest-list']//li//div[@class='sc-jObWnj bRMwkb']//span[text()='@1@']");
	private By toLocationSuggestion1 = By
			.xpath("//ul[@id='autoSuggest-list']//li//div[@class='sc-jObWnj bRMwkb']//span[text()='@1@']");
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
	}

	public String getHomePageTitle() {
		return getPageTitle();
	}

	public String getHomePageHeader() {
		return getPageHeader(homePageHeader);
	}

	public WebElement getOneWayTrip() {
		return oneWayTrip;
	}

	public WebElement getFromLocation() {
		return fromLocation;
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

	public void verifyHomePageTitle() {
		try {
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
			reusableHelper.enterText(getFromLocation(), fromLocation, "fromLocation");
			reusableHelper.elementClick(getFromLocationSuggestion1(),
					FileReaderUtility.getTestData("from.location.suggestion"), "fromLocationSuggestion1");
			reusableHelper.enterText(getToLocation(), toLocation, "toLocation");
			reusableHelper.elementClick(getToLocationSuggestion1(),
					FileReaderUtility.getTestData("to.location.suggestion"), "toLocationSuggestion");
			reusableHelper.elementClick(getDepatureDate(), "depatureDate");
			selectDate(month, day);
			reusableHelper.elementClick(getTravelSelection(), "travelSelection");
			dropDownHelper.selectByValue(getTravelSelectionClass(), travelClass, "travelSelectionClass");
			ReusableHelper.setAnyElement("fromLocation",
					verificationHelper.readValueFromInput(getFromLocation(), "fromLocation"));
			ReusableHelper.setAnyElement("toLocation",
					verificationHelper.readValueFromInput(getToLocation(), "toLocation"));
			ReusableHelper.setAnyElement("travelClass",
					verificationHelper.readTextValueFromElement(getTravelSelectionClass(), "travelClass"));
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
