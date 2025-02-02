package com.qa.ctf.handler;

import com.qa.ctf.context.TestContext;
import com.qa.ctf.util.ExceptionHub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.*;
import java.time.format.DateTimeFormatter;

import java.util.List;

/**
 * The DatePicker class provides utility methods for interacting with date picker elements
 * in web applications using Selenium WebDriver. It encapsulates the logic required to
 * handle various types of date picker widgets, enabling seamless date selection and validation.
 *
 * <p>Features:
 * <ul>
 *   <li>Date Selection: Methods to select specific dates by interacting with date picker
 *   components, including calendar pop-ups or text input fields.</li>
 *   <li>Format Handling: Support for multiple date formats, ensuring compatibility with
 *   various date picker implementations.</li>
 *   <li>Dynamic Date Selection: Utility methods to select dates relative to the current
 *   date (e.g., today, tomorrow, or custom offsets).</li>
 *   <li>Error Handling: Robust exception handling to manage scenarios where the desired
 *   date is unavailable or the date picker fails to render properly.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions from the {@link ExceptionHub} class are thrown to provide
 *   descriptive error messages during failures.</li>
 *   <li>Comprehensive logging for troubleshooting issues related to date picker interactions.</li>
 * </ul>
 *
 * <p>Note:
 * Ensure that the WebDriver instance is properly initialized and points to a web page
 * containing the target date picker element. The class assumes that date picker widgets
 * on the web page follow standard HTML structures or are compatible with JavaScript interactions.
 *
 * <p>Example:
 * <pre>
 * {@code
 * DatePicker datePicker = new DatePicker(driver);
 * WebElement dateInput = driver.findElement(By.id("dateField"));
 * datePicker.selectDate(dateInput, "2025-01-20");
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.1
 */
public class DateTimeHandler {

    // Logger instance for the DateTimeHandler class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(DateTimeHandler.class);

    // Constant for the label identifying the "Day" field in a date picker.
    private static final String DAY_LABEL = "Day";

    // Instance of VerificationHandler to perform verification actions on dropdown elements
    private final VerificationHandler verificationHandler;

    // Instance of InteractionHandler to perform mouse actions on elements
    private final InteractionHandler interactionHandler;

    /**
     * Constructs a DatePickerHandler instance and initializes it with the provided
     * TestContext and VerificationHandler.
     * <p>
     * This constructor sets up the necessary dependencies, including the TestContext
     * for managing WebDriver instances and the VerificationHandler for performing
     * verification tasks. Additionally, it initializes an InteractionHandler to handle
     * user interactions with web elements.
     * </p>
     *
     * @param testContext  The TestContext instance to be used for interacting with
     *                     the WebDriver.
     * @param verificationHandler The VerificationHandler instance for handling verification
     *                            tasks.
     * @throws IllegalArgumentException If the provided TestContext is null.
     */
    public DateTimeHandler(TestContext testContext, VerificationHandler verificationHandler) {
        if (testContext == null) {
            throw new IllegalArgumentException("TestContext cannot be null.");
        }
        this.verificationHandler = verificationHandler;
        this.interactionHandler = new InteractionHandler(testContext, this.verificationHandler);
    }

    /**
     * Selects a date from a single date picker component.
     * <p>
     * This method validates the provided date, opens the date picker, navigates to the
     * specified month and year, and selects the desired day. It ensures the date picker
     * is properly displayed and verifies element visibility before interacting with it.
     * </p>
     *
     * @param datePicker             The WebElement representing the date picker.
     * @param dateDetailSection      The WebElement representing the section displaying date details.
     * @param monthDetail            The WebElement displaying the current month.
     * @param yearDetail             The WebElement displaying the current year.
     * @param monthNavigator         The WebElement used to navigate through months in the picker.
     * @param dayLocator             The By locator for locating day elements.
     * @param day                    The day to select.
     * @param month                  The month to navigate to.
     * @param year                   The year to navigate to.
     * @param dateDetailSectionLabel The label describing the date detail section for logging and
     *                               validation.
     * @throws ExceptionHub.DatePickerException If the provided date is invalid, the desired month/year
     *                                          cannot be found, or other issues occur during selection.
     */
    public void selectDateFromSingleDatePicker(
            WebElement datePicker,
            WebElement dateDetailSection,
            WebElement monthDetail,
            WebElement yearDetail,
            WebElement monthNavigator,
            By dayLocator,
            String day,
            String month,
            String year,
            String dateDetailSectionLabel) {
        int maxAttempts = 12;
        if (day == null || month == null || year == null) {
            log.error("Day, Month, and Year must not be null.");
            throw new ExceptionHub.DatePickerException("Day, Month, and Year must not be null.");
        }
        try {
            // Validate the date by attempting to create a LocalDate object.
            // The result is not used because we are only interested in ensuring the inputs form a valid date.
            // noinspection ResultOfMethodCallIgnored
            LocalDate.of(Integer.parseInt(year), Month.valueOf(month.toUpperCase()), Integer.parseInt(day));
        } catch (DateTimeException | IllegalArgumentException ex) {
            log.error("Invalid date: {}/{}/{}", day, month, year);
            throw new ExceptionHub.DatePickerException(String.format("Invalid date: %s/%s/%s", day, month, year), ex);
        }
        log.info("Selecting date: {}/{}/{}", day, month, year);
        datePicker.click();
        verificationHandler.isElementDisplayed(dateDetailSection, dateDetailSectionLabel);
        String monthAfterTrim = month.trim();
        String yearAfterTrim = year.trim();
        String monthText = monthDetail.getText();
        String yearText = yearDetail.getText();
        while (!(monthText.trim().equalsIgnoreCase(monthAfterTrim) && yearText.trim().equalsIgnoreCase(yearAfterTrim))) {
            maxAttempts--;
            log.info("Navigating to Month: {}, Year: {}", monthText, yearText);
            monthNavigator.click();
            monthText = monthDetail.getText();
            yearText = yearDetail.getText();
            if (maxAttempts <= 0) {
                log.error("Could not find the desired month and year: {} & {}", month, year);
                throw new ExceptionHub.DatePickerException(String.format("Could not find the desired month and year: %s & %s", month, year));
            }
        }
        log.info("Selecting day: {}", day);
        interactionHandler.clickElement(dayLocator, day, DAY_LABEL);
    }

    /**
     * Selects a date from a dual date picker component.
     * <p>
     * This method validates the provided date inputs, opens the date picker, navigates to the
     * specified month and year in a dual-pane calendar view, and selects the desired day.
     * It ensures the date picker is properly displayed and verifies element visibility before
     * interacting with it.
     * </p>
     *
     * @param datePicker             The WebElement representing the date picker.
     * @param dateDetailSection      The WebElement representing the section displaying date details.
     * @param monthYearDetailList    A list of WebElements representing the displayed month and year
     *                               in the left and right panes of the dual date picker.
     * @param monthNavigator         The WebElement used to navigate through months and years in the picker.
     * @param dayLocator             The By locator for locating day elements.
     * @param day                    The day to select.
     * @param monthYear              The month and year to navigate to, formatted as "Month Year" (e.g.,
     *                               "January 2025").
     * @param dateDetailSectionLabel The label describing the date detail section for logging and validation.
     * @throws ExceptionHub.DatePickerException If the provided day, month, or year is invalid,
     *                                          the desired month/year cannot be found, or other issues occur
     *                                          during selection.
     */
    public void selectDateFromDualDatePicker(
            WebElement datePicker,
            WebElement dateDetailSection,
            List<WebElement> monthYearDetailList,
            WebElement monthNavigator,
            By dayLocator,
            String day,
            String monthYear,
            String dateDetailSectionLabel) {
        int maxAttempts = 12;
        if (day == null || monthYear == null) {
            log.error("Day, Month, and Year must not be null.");
            throw new ExceptionHub.DatePickerException("Day, Month, and Year must not be null.");
        }
        log.info("Selecting date: {} / {}", day, monthYear);
        datePicker.click();
        verificationHandler.isElementDisplayed(dateDetailSection, dateDetailSectionLabel);
        String monthYearAfterTrim = monthYear.trim();
        String leftMonthYearDetail = monthYearDetailList.get(0).getText();
        String rightMonthYearDetail = monthYearDetailList.get(1).getText();
        while (!(leftMonthYearDetail.trim().equalsIgnoreCase(monthYearAfterTrim) &&
                rightMonthYearDetail.trim().equalsIgnoreCase(monthYearAfterTrim))) {
            maxAttempts--;
            log.info("Navigating to Month & Year: {}", monthYear);
            monthNavigator.click();
            leftMonthYearDetail = monthYearDetailList.get(0).getText();
            rightMonthYearDetail = monthYearDetailList.get(1).getText();
            if (maxAttempts <= 0) {
                log.error("Could not find the desired month and year: {}", monthYear);
                throw new ExceptionHub.DatePickerException("Could not find the desired month and year: " + monthYear);
            }
        }
        log.info("Selecting day: {}", day);
        interactionHandler.clickElement(dayLocator, day, DAY_LABEL);
    }

    /**
     * Retrieves the current date in the default format "YYYY-MM-dd".
     * <p>
     * This method returns the current system date in ISO-8601 format.
     * </p>
     *
     * @return The current date as a string in "YYYY-MM-dd" format.
     */
    public String getDate() {
        return LocalDate.now().toString();
    }

    /**
     * Retrieves the current date in a customized format.
     * <p>
     * If the provided format is null or empty, the default format "dd-MM-yyyy" is used.
     * </p>
     *
     * @param format The desired date format (e.g., "dd-MM-yyyy", "MM/dd/yyyy").
     * @return The current date as a string formatted according to the provided format.
     */
    public String getCustomizedDate(String format) {
        if (format == null || format.isEmpty()) {
            format = "dd-MM-yyyy";
        }
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return currentDate.format(dateTimeFormatter);
    }

    /**
     * Retrieves the current day of the month.
     * <p>
     * This method returns the numerical value of the current day (1-31).
     * </p>
     *
     * @return The current day of the month as a string.
     */
    public String getDay() {
        return String.valueOf(LocalDate.now().getDayOfMonth());
    }

    /**
     * Retrieves the current month in a customized format.
     * <p>
     * If the provided format is null or empty, the default format "MMMM" is used,
     * which returns the full month name (e.g., "January"). Other formats like "MMM"
     * (e.g., "Jan") can also be used.
     * </p>
     *
     * @param format The desired month format (e.g., "MMM", "MMMM").
     * @return The current month as a string formatted according to the provided format.
     */
    public String getMonth(String format) {
        if (format == null || format.isEmpty()) {
            format = "MMMM";
        }
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return currentDate.format(dateTimeFormatter);
    }

    /**
     * Retrieves the current year.
     * <p>
     * This method returns the current year as a four-digit number (e.g., "2025").
     * </p>
     *
     * @return The current year as a string.
     */
    public String getYear() {
        return String.valueOf(LocalDate.now().getYear());
    }

    /**
     * Retrieves the current time in the default format "HH:mm:ss.nnnnnnnnn".
     * <p>
     * This method returns the current system time with nanosecond precision.
     * </p>
     *
     * @return The current time as a string in "HH:mm:ss.nnnnnnnnn" format.
     */
    public String getTime() {
        return LocalTime.now().toString();
    }

    /**
     * Retrieves the current time in a customized format.
     * <p>
     * If the provided format is null or empty, the default format "hh:mm:ss a"
     * (e.g., "09:10:40 AM") is used.
     * </p>
     *
     * @param format The desired time format (e.g., "HH:mm:ss", "hh:mm:ss a").
     * @return The current time as a string formatted according to the provided format.
     */
    public String getCustomizedTime(String format) {
        if (format == null || format.isEmpty()) {
            format = "hh:mm:ss a";
        }
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return currentTime.format(dateTimeFormatter);
    }

    /**
     * Retrieves the current date and time in the default format
     * "YYYY-MM-ddTHH:mm:ss.nnnnnnnnn".
     * <p>
     * This method returns the current system date and time in ISO-8601 format.
     * </p>
     *
     * @return The current date and time as a string.
     */
    public String getDateTime() {
        return LocalDateTime.now().toString();
    }

    /**
     * Retrieves the current date and time with time zone information.
     * <p>
     * This method returns the current date and time for the specified zone ID.
     * </p>
     *
     * @param zoneId The ZoneId for which the date and time should be retrieved.
     * @return The current date and time as a string with time zone information.
     */
    public String getZonedDateTime(ZoneId zoneId) {
        return ZonedDateTime.now(zoneId).toString();
    }

    /**
     * Retrieves the current timestamp in ISO-8601 format.
     * <p>
     * This method returns the current instant in time with nanosecond precision.
     * </p>
     *
     * @return The current timestamp as a string.
     */
    public String getTimeStamp() {
        return Instant.now().toString();
    }

}
