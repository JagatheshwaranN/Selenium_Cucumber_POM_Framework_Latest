package com.jtaf.qa.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

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

	private By homePageHeader = By.xpath("//h1[text()='Domestic and International Flights']");
	private By oneWayTrip = By
			.xpath("//div[contains(@class,'fltSwitchOpt')]//span[@id='oneway' and @class='curPointFlt switchAct']");
	private By fromLocation = By.xpath("//input[@id='gosuggest_inputSrc']");
	// private By fromLocationSuggestion = By.xpath(
	// "//ul[@id='react-autosuggest-1']//li//div[@class='mainTxt
	// clearfix']//span[text()='Chennai, India']");
	private By fromLocationSuggestion1 = By
			.xpath("//ul[@id='react-autosuggest-1']//li//div[@class='mainTxt clearfix']//span[text()='@1@']");
	private By toLocation = By.xpath("//input[@id='gosuggest_inputDest']");
	// private By toLocationSuggestion = By.xpath(
	// "//ul[@id='react-autosuggest-1']//li//div[@class='mainTxt
	// clearfix']//span[text()='Bengaluru, India']");
	private By toLocationSuggestion1 = By
			.xpath("//ul[@id='react-autosuggest-1']//li//div[@class='mainTxt clearfix']//span[text()='@1@']");
	private By depatureDate = By.xpath("//input[@id='departureCalendar']");
	private By monthTextInDatePicker = By.xpath("//div[@class='DayPicker-Caption' and @role='heading']");
	private By monthNavigatorInDatePicker = By
			.xpath("//span[@role='button' and @class='DayPicker-NavButton DayPicker-NavButton--next']");
	private By dateInCalendar = By
			.xpath("//div[@class='DayPicker-Week']/div[@class='DayPicker-Day']/div[text()='@1@']");
	private By travelSelection = By.xpath("//div[@id='pax_link_common']");
	private By travelSelectionPassenger = By.xpath("//button[@id='adultPaxPlus']");
	private By travelSelectionClass = By.xpath("//select[@class='custSelect width100 whiteBg padTB5 padLR10']");
	private By travelclass = By.xpath(
			"(//span[@class='dF alignItemsCenter ico14 textOverflow blueGrey trvlr-box']//span[@class='blueGrey textOverflow'])[2]");
	private By searchButton = By.xpath("//button[contains(@class,'orange') and @value='Search']");

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
		return getElement(oneWayTrip);
	}

	public WebElement getFromLocation() {
		return getElement(fromLocation);
	}

	/*
	 * public WebElement getFromLocationSuggestion() { return
	 * getElement(fromLocationSuggestion); }
	 */

	public WebElement getToLocation() {
		return getElement(toLocation);
	}

	/*
	 * public WebElement getToLocationSuggestion() { return
	 * getElement(toLocationSuggestion); }
	 */

	public WebElement getDepatureDate() {
		return getElement(depatureDate);
	}

	public WebElement getMonthTextInDatePicker() {
		return getElement(monthTextInDatePicker);
	}

	public WebElement getMonthNavigatorInDatePicker() {
		return getElement(monthNavigatorInDatePicker);
	}

	public By getDateInCalendar() {
		return dateInCalendar;
	}

	public WebElement getTravelSelection() {
		return getElement(travelSelection);
	}

	public WebElement getTravelSelectionPassenger() {
		return getElement(travelSelectionPassenger);
	}

	public WebElement getTravelSelectionClass() {
		return getElement(travelSelectionClass);
	}

	public WebElement getTravelClass() {
		return getElement(travelclass);
	}

	public WebElement getSearchButton() {
		return getElement(searchButton);
	}

	public By getFromLocationSuggestion1() {
		return fromLocationSuggestion1;
	}

	public By getToLocationSuggestion1() {
		return toLocationSuggestion1;
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
					verificationHelper.readTextValueFromElement(getTravelClass(), "travelClass"));
			reusableHelper.elementClick(getSearchButton(), "searchButton");
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
			reusableHelper.elementClick(getDateInCalendar(), day, "dateInCalendar");
		} catch (Exception ex) {
			log.info("Error occured while select date from calendar" + "\n" + ex);
			Assert.fail();
		}
	}

}
