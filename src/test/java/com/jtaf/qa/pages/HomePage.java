package com.jtaf.qa.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import com.jtaf.qa.objects.HomePageElements;
import com.jtaf.qa.utilities.FileReaderUtility;
import com.jtaf.qa.utilities.LoggerUtility;

/**
 * 
 * @author Jaga
 *
 */
public class HomePage extends BasePage {

	private static Logger log = LoggerUtility.getLog(HomePage.class);

	AlertHelper alertHelper = new AlertHelper(getDriver());
	BrowserHelper browserHelper = new BrowserHelper(getDriver());
	DropDownHelper dropDownHelper = new DropDownHelper(getDriver());
	JavaScriptHelper javaScriptHelper = new JavaScriptHelper(getDriver());
	MouseActionHelper mouseActionHelper = new MouseActionHelper(getDriver());
	VerificationHelper verificationHelper = new VerificationHelper(getDriver());
	ReusableHelper reusableHelper = new ReusableHelper(getDriver());
	HomePageElements homePageElements;

	public HomePage(WebDriver driver) {
		super(driver);
		this.homePageElements = new HomePageElements();
		PageFactory.initElements(driver, this.homePageElements);
	}

	public String getHomePageTitle() {
		return getPageTitle();
	}

	public String getHomePageHeader() {
		return getPageHeader(homePageElements.homePageHeader);
	}

	public WebElement getAppLaunchPopupClose() {
		return homePageElements.appLaunchPopupClose;
	}

	public WebElement getOneWayTrip() {
		return homePageElements.oneWayTrip;
	}

	public WebElement getFromLocationSection() {
		return homePageElements.fromLocationSection;
	}

	public WebElement getFromLocation() {
		return homePageElements.fromLocation;
	}

	public WebElement getToLocationSection() {
		return homePageElements.toLocationSection;
	}

	public WebElement getToLocation() {
		return homePageElements.toLocation;
	}

	public WebElement getDepatureDate() {
		return homePageElements.depatureSection;
	}

	public WebElement getMonthTextInDatePicker() {
		return homePageElements.monthTextInDatePicker;
	}

	public WebElement getMonthNavigatorInDatePicker() {
		return homePageElements.monthNavigatorInDatePicker;
	}

	public WebElement getTravelSelection() {
		return homePageElements.travelSelection;
	}

	public WebElement getTravelSelectionPassenger() {
		return homePageElements.travelSelectionPassenger;
	}

	public WebElement getTravelSelectionClass() {
		return homePageElements.travelSelectionClass;
	}

	public WebElement getTravelSelectionApply() {
		return homePageElements.travelSelectionApply;
	}

	public WebElement getTravelClassDisplay() {
		return homePageElements.travelClassDisplay;
	}

	public WebElement getSearchFlights() {
		return homePageElements.searchFlights;
	}

	public By getFromLocationSuggestion1() {
		return homePageElements.fromLocationSuggestion1;
	}

	public By getToLocationSuggestion1() {
		return homePageElements.toLocationSuggestion1;
	}

	public By getDateInCalendar1() {
		return homePageElements.dateInCalendar1;
	}

	public By getTravelSelectionPassenger1() {
		return homePageElements.travelSelectionPassenger1;
	}

	public By getTravelSelectionClass1() {
		return homePageElements.travelSelectionClass1;
	}

	public void verifyHomePageTitle() {
		try {
			// Temporary Fix
			Thread.sleep(10000);
			reusableHelper.waitForElementVisible(getAppLaunchPopupClose());
			reusableHelper.elementClick(getAppLaunchPopupClose(), "appLaunchPopupClose");
			browserHelper.getCurrentPageUrl();
			Assert.assertEquals(getHomePageTitle(), FileReaderUtility.getTestData("home.page.title"));
		} catch (Exception ex) {
			log.info("Error occured while check home page title" + "\n" + ex);
			Assert.fail();
		}
	}

	public TicketBookingPage enterTravelDetails(String fromLocation, String toLocation, String month, String day,
			String passengers, String travelClass) {
		try {
			reusableHelper.elementClick(getFromLocationSection(), "fromLocationSection");
			reusableHelper.enterText(getFromLocation(), fromLocation, "fromLocation");
			reusableHelper.elementClick(getFromLocationSuggestion1(),
					FileReaderUtility.getTestData("from.location.suggestion"), "fromLocationSuggestion1");
			reusableHelper.elementClick(getToLocationSection(), "toLocationSection");
			reusableHelper.enterText(getToLocation(), toLocation, "toLocation");
			reusableHelper.elementClick(getToLocationSuggestion1(),
					FileReaderUtility.getTestData("to.location.suggestion"), "toLocationSuggestion");
			selectDepartureDate(month, day);
			reusableHelper.elementClick(getTravelSelection(), "travelSelection");
			reusableHelper.elementClick(getTravelSelectionPassenger1(), passengers, "getTravelSelectionPassenger1");
			reusableHelper.elementClick(getTravelSelectionClass1(), travelClass, "getTravelSelectionClass1");
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

	public void selectDepartureDate(String month, String day) {
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
			log.info("Error occured while select departure date from calendar" + "\n" + ex);
			Assert.fail();
		}
	}
}
