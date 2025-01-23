//package com.qa.ctf.pages;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.PageFactory;
//import org.testng.Assert;
//
//import com.qa.ctf.base.BasePage;
//import com.qa.ctf.helpers.AlertHelper;
//import com.qa.ctf.helpers.BrowserHelper;
//import com.qa.ctf.helpers.DropDownHelper;
//import com.qa.ctf.helpers.JavaScriptHelper;
//import com.qa.ctf.helpers.MouseActionHelper;
//import com.qa.ctf.helpers.ReusableHelper;
//import com.qa.ctf.helpers.VerificationHelper;
//import com.qa.ctf.objects.Old_HomePageElements;
//import com.qa.ctf.utilities.FileReaderUtility;
//
///**
// *
// * @author Jaga
// *
// */
//public class Old_HomePage extends BasePage {
//
//	private static final Logger log = LogManager.getLogger(Old_HomePage.class.getName());
//
//	AlertHelper alertHelper = new AlertHelper(getDriver());
//	BrowserHelper browserHelper = new BrowserHelper(getDriver());
//	DropDownHelper dropDownHelper = new DropDownHelper(getDriver());
//	JavaScriptHelper javaScriptHelper = new JavaScriptHelper(getDriver());
//	MouseActionHelper mouseActionHelper = new MouseActionHelper(getDriver());
//	VerificationHelper verificationHelper = new VerificationHelper(getDriver());
//	ReusableHelper reusableHelper = new ReusableHelper(getDriver());
//	Old_HomePageElements homePageElements;
//
//	public Old_HomePage(WebDriver driver) {
//		super(driver);
//		this.homePageElements = new Old_HomePageElements();
//		PageFactory.initElements(driver, this.homePageElements);
//	}
//
//	public String getHomePageTitle() {
//		return getPageTitle();
//	}
//
//	public String getHomePageHeader() {
//		return getPageHeader(homePageElements.HomePageHeader);
//	}
//
//	public WebElement getAppLaunchPopupClose() {
//		return homePageElements.AppLaunchPopupClose;
//	}
//
//	public WebElement getOneWayTrip() {
//		return homePageElements.OneWayTrip;
//	}
//
//	public WebElement getFromLocationSection() {
//		return homePageElements.FromLocationSection;
//	}
//
//	public WebElement getFromLocation() {
//		return homePageElements.FromLocation;
//	}
//
//	public WebElement getToLocationSection() {
//		return homePageElements.ToLocationSection;
//	}
//
//	public WebElement getToLocation() {
//		return homePageElements.ToLocation;
//	}
//
//	public WebElement getDepatureDate() {
//		return homePageElements.DepatureSection;
//	}
//
//	public WebElement getMonthTextInDatePicker() {
//		return homePageElements.MonthTextInDatePicker;
//	}
//
//	public WebElement getMonthNavigatorInDatePicker() {
//		return homePageElements.MonthNavigatorInDatePicker;
//	}
//
//	public WebElement getTravelSelection() {
//		return homePageElements.TravelSelection;
//	}
//
//	public WebElement getTravelSelectionPassenger() {
//		return homePageElements.TravelSelectionPassenger;
//	}
//
//	public WebElement getTravelSelectionClass() {
//		return homePageElements.TravelSelectionClass;
//	}
//
//	public WebElement getTravelSelectionApply() {
//		return homePageElements.TravelSelectionApply;
//	}
//
//	public WebElement getTravelClassDisplay() {
//		return homePageElements.TravelClassDisplay;
//	}
//
//	public WebElement getSearchFlights() {
//		return homePageElements.SearchFlights;
//	}
//
//	public By getFromLocationSuggestion1() {
//		return homePageElements.FromLocationSuggestion1;
//	}
//
//	public By getToLocationSuggestion1() {
//		return homePageElements.ToLocationSuggestion1;
//	}
//
//	public By getDateInCalendar1() {
//		return homePageElements.DateInCalendar1;
//	}
//
//	public By getTravelSelectionPassenger1() {
//		return homePageElements.TravelSelectionPassenger1;
//	}
//
//	public By getTravelSelectionClass1() {
//		return homePageElements.TravelSelectionClass1;
//	}
//
//	public void verifyHomePageTitle() {
//		try {
//			reusableHelper.waitForElementVisible(getAppLaunchPopupClose());
//			reusableHelper.elementClick(getAppLaunchPopupClose(), "appLaunchPopupClose");
//			browserHelper.getCurrentPageUrl();
//			Assert.assertEquals(getHomePageTitle(), FileReaderUtility.getTestData("home.page.title"));
//		} catch (Exception ex) {
//			log.info("Error occured while check home page title" + "\n" + ex);
//			Assert.fail();
//		}
//	}
//
//	public Old_TicketBookingPage enterTravelDetails(String fromLocation, String toLocation, String month, String day,
//													String passengers, String travelClass) {
//		try {
//			reusableHelper.elementClick(getFromLocationSection(), "fromLocationSection");
//			reusableHelper.enterText(getFromLocation(), fromLocation, "fromLocation");
//			reusableHelper.elementClick(getFromLocationSuggestion1(),
//					FileReaderUtility.getTestData("from.location.suggestion"), "fromLocationSuggestion1");
//			reusableHelper.elementClick(getToLocationSection(), "toLocationSection");
//			reusableHelper.enterText(getToLocation(), toLocation, "toLocation");
//			reusableHelper.elementClick(getToLocationSuggestion1(),
//					FileReaderUtility.getTestData("to.location.suggestion"), "toLocationSuggestion");
//			selectDepartureDate(month, day);
//			reusableHelper.elementClick(getTravelSelection(), "travelSelection");
//			reusableHelper.elementClick(getTravelSelectionPassenger1(), passengers, "getTravelSelectionPassenger1");
//			reusableHelper.elementClick(getTravelSelectionClass1(), travelClass, "getTravelSelectionClass1");
//			reusableHelper.elementClick(getTravelSelectionApply(), "TravelSelectionDone");
//			ReusableHelper.setAnyElement("fromLocation",
//					verificationHelper.readValueFromInput(getFromLocation(), "fromLocation"));
//			ReusableHelper.setAnyElement("toLocation",
//					verificationHelper.readValueFromInput(getToLocation(), "toLocation"));
//			ReusableHelper.setAnyElement("travelClass",
//					verificationHelper.readTextValueFromElement(getTravelSelection(), "travelClass"));
//			reusableHelper.elementClick(getSearchFlights(), "searchButton");
//		} catch (Exception ex) {
//			log.info("Error occured while enter travel details" + "\n" + ex);
//			Assert.fail();
//		}
//		return getInstance(Old_TicketBookingPage.class);
//	}
//
//	public void selectDepartureDate(String month, String day) {
//		try {
//			while (true) {
//				var monthInDatePicker = getMonthTextInDatePicker().getText();
//				if (monthInDatePicker.equals(month)) {
//					break;
//				} else {
//					getMonthNavigatorInDatePicker().click();
//				}
//			}
//			reusableHelper.elementClick(getDateInCalendar1(), day, "dateInCalendar");
//		} catch (Exception ex) {
//			log.info("Error occured while select departure date from calendar" + "\n" + ex);
//			Assert.fail();
//		}
//	}
//}
