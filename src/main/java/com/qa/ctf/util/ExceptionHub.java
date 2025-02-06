package com.qa.ctf.util;

/**
 * The {@code ExceptionHub} class provides custom exception handling for various errors
 * that occur during Selenium-based automation and interaction with web elements.
 * <p>
 * This class extends {@link RuntimeException} and defines several static inner exception
 * classes that provide more specific error messages for different failure scenarios.
 * </p>
 *
 * <p>Features:</p>
 * <ul>
 *     <li>Custom exception handling for element interactions, configuration errors,
 *      invalid data, and alert handling.</li>
 *     <li>Detailed error messages for each specific type of failure.</li>
 *     <li>Support for wrapping underlying exceptions with more descriptive custom
 *      exceptions.</li>
 * </ul>
 *
 * <p>Exception Classes:</p>
 * <ul>
 *     <li>{@link OptionNotFoundException} - Thrown when an option is not found in a
 *      dropdown.</li>
 *     <li>{@link DropDownException} - Thrown for errors related to dropdown elements.</li>
 *     <li>{@link ElementNotFoundException} - Thrown when an element is not found or
 *      modified in the DOM.</li>
 *     <li>{@link ConfigTypeException} - Thrown when an invalid configuration type is
 *      encountered.</li>
 *     <li>{@link InvalidDataException} - Thrown when invalid data is provided.</li>
 *     <li>{@link AlertNotFoundException} - Thrown when an alert is not found on the page.</li>
 *     <li>{@link WindowException} - Thrown for issues related to window handling.</li>
 *     <li>{@link FrameException} - Thrown for issues related to frame handling.</li>
 *     <li>{@link JavascriptExecutorException} - Thrown for errors occurring during JavaScript
 *      execution.</li>
 *     <li>{@link InteractionException} - Thrown for issues with interacting with web elements.</li>
 *     <li>{@link ScreenshotException} - Thrown for errors related to taking or processing
 *      screenshots.</li>
 *     <li>{@link ExtentException} - Thrown for issues related to Extent Reports generation.</li>
 * </ul>
 *
 * <p>Note:</p>
 * <ul>
 *     <li>Each exception includes a detailed error message and, where applicable, an
 *      underlying cause.</li>
 *     <li>All exception classes extend the {@link ExceptionHub} base class to inherit
 *      common functionality.</li>
 * </ul>
 *
 * <p>Example:</p>
 * <pre>
 * {@code
 * try {
 *     // Some code that may throw an exception
 * } catch (OptionNotFoundException ex) {
 *     // Handle the specific exception
 * }
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.1
 */
public class ExceptionHub extends RuntimeException {

    /**
     * Constructs a new ExceptionHub with the specified detail message.
     *
     * @param message the detail message.
     */
    public ExceptionHub(String message) {
        super(message);
    }

    /**
     * Constructs a new ExceptionHub with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public ExceptionHub(String message, Throwable cause) {
        super(message, cause);
    }

    public static class OptionNotFoundException extends ExceptionHub {

        /**
         * Constructs the exception with a message indicating that the specified
         * option could not be found in the specified dropdown element.
         *
         * @param value        The option value that could not be found.
         * @param elementLabel The label of the dropdown where the option was not found.
         */
        public OptionNotFoundException(String value, String elementLabel) {
            super(String.format("Option '%s' not found in the '%s' dropdown.", value, elementLabel));
        }

        /**
         * Constructs the exception with a message indicating that the specified option
         * could not be found in the specified dropdown element, and wraps the provided
         * cause (Throwable) for additional context.
         *
         * @param value        The option value that could not be found.
         * @param elementLabel The label of the dropdown where the option was not found.
         * @param cause        The cause of the exception.
         */
        public OptionNotFoundException(String value, String elementLabel, Throwable cause) {
            super(String.format("Option '%s' not found in the '%s' dropdown.", value, elementLabel), cause);
        }
    }

    public static class DropDownException extends ExceptionHub {

        /**
         * Constructs the exception with a message indicating an error related to a dropdown
         * element, and wraps the provided cause (Throwable) for additional context.
         *
         * @param message The value associated with the dropdown operation.
         * @param cause   The cause of the exception.
         */
        public DropDownException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ElementNotFoundException extends ExceptionHub {

        /**
         * Constructs the exception with a message indicating that the specified element
         * could not be found or modified in the DOM.
         *
         * @param elementLabel The label of the element that was not found or modified.
         */
        public ElementNotFoundException(String elementLabel) {
            super(String.format("Element '%s' is not found / modified on the DOM.", elementLabel));
        }

        /**
         * Constructs the exception with a message indicating the issue with the specified
         * element, and wraps the provided cause (Throwable) for additional context.
         *
         * @param elementLabel The label of the element that was not found or modified.
         * @param cause        The cause of the exception.
         */
        public ElementNotFoundException(String elementLabel, Throwable cause) {
            super(String.format("Element '%s' is not found / modified on the DOM.", elementLabel), cause);
        }
    }

    public static class ConfigTypeException extends ExceptionHub {

        /**
         * Constructs the exception with a message indicating that the specified configuration type
         * is not valid and needs to be checked.
         *
         * @param config The name of the invalid configuration type.
         */
        public ConfigTypeException(String config) {
            super(String.format("The '%s' config type is not valid. Please check the configuration.", config));
        }

        /**
         * Constructs the exception with a message indicating that the specified configuration file
         * is not valid and needs to be checked.
         *
         * @param elementLabel The label of the element that was not found or modified.
         * @param cause        The cause of the exception.
         */
        public ConfigTypeException(String elementLabel, Throwable cause) {
            super(String.format("The '%s' config file is not found. Please check the configuration.", elementLabel), cause);
        }
    }

    public static class InvalidDataException extends ExceptionHub {

        /**
         * Constructs the exception with a message indicating that the specified data value
         * is invalid and needs to be checked.
         *
         * @param data The invalid data value that caused the exception.
         */
        public InvalidDataException(String data) {
            super(String.format("The '%s' value is not valid. Please check the data.", data));
        }

        /**
         * Constructs the exception with a message indicating that the specified data value
         * is invalid and needs to be checked.
         *
         * @param data  The invalid data value that caused the exception.
         * @param cause The cause of the exception.
         */
        public InvalidDataException(String data, Throwable cause) {
            super(String.format("The '%s' value is not found in the configuration file.", data), cause);
        }
    }

    public static class AlertNotFoundException extends ExceptionHub {

        /**
         * Constructs the exception with a message indicating that the alert was not present on the page,
         * and includes the underlying cause of the failure.
         *
         * @param cause The underlying cause of the exception.
         */
        public AlertNotFoundException(Throwable cause) {
            super("Alert was not present on the page.", cause);
        }
    }

    public static class WindowException extends ExceptionHub {

        /**
         * Constructs the exception with a message describing the window handling issue,
         * and includes the underlying cause of the failure.
         *
         * @param message The error message detailing the window issue.
         * @param cause   The underlying cause of the exception.
         */
        public WindowException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class FrameException extends ExceptionHub {

        /**
         * Constructs the exception with a message describing the frame handling issue,
         * and includes the underlying cause of the failure.
         *
         * @param message The error message detailing the frame issue.
         * @param cause   The underlying cause of the exception.
         */
        public FrameException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class JavascriptExecutorException extends ExceptionHub {

        /**
         * Constructs the exception with a message detailing the JavaScript execution issue,
         * and includes the underlying cause of the failure.
         *
         * @param message The error message describing the JavaScript issue.
         * @param cause   The underlying cause of the exception.
         */
        public JavascriptExecutorException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InteractionException extends ExceptionHub {

        /**
         * Constructs the exception with a message describing the interaction error,
         * and includes the underlying cause of the failure.
         *
         * @param message The error message detailing the issue during interaction with the web element.
         * @param cause   The underlying cause of the exception.
         */
        public InteractionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ScreenshotException extends ExceptionHub {

        /**
         * Constructs the exception with a message explaining the screenshot error
         * and includes the underlying cause of the failure.
         *
         * @param message The error message detailing the issue during screenshot capture.
         * @param cause   The underlying cause of the exception.
         */
        public ScreenshotException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ExtentException extends ExceptionHub {

        /**
         * Constructs the exception with a message explaining the report generation error
         * and includes the underlying cause of the failure.
         *
         * @param message The error message detailing the issue during Extent Report generation.
         * @param cause   The underlying cause of the exception.
         */
        public ExtentException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ExcelException extends ExceptionHub {

        /**
         * Constructs the exception with a message indicating that the specified Excel
         * operation is not valid and needs to be checked.
         *
         * @param message The error message detailing the issue during Excel operation.
         */
        public ExcelException(String message) {
            super(message);
        }

        /**
         * Constructs the exception with a message explaining the Excel operation error
         * and includes the underlying cause of the failure.
         *
         * @param message The error message detailing the issue during Excel operation.
         * @param cause   The underlying cause of the exception.
         */
        public ExcelException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class DatePickerException extends ExceptionHub {

        /**
         * Constructs the exception with a message indicating that the specified date picker
         * operation is not valid and needs to be checked.
         *
         * @param message The error message detailing the issue during Date Picker operation.
         */

        public DatePickerException(String message) {
            super(message);
        }

        /**
         * Constructs the exception with a message explaining the date picker operation error
         * and includes the underlying cause of the failure.
         *
         * @param message The error message detailing the issue during Date Picker operation.
         * @param cause   The underlying cause of the exception.
         */
        public DatePickerException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class TimeoutException extends ExceptionHub {

        /**
         * Constructs the exception with a message indicating that the specified date picker
         * operation is not valid and needs to be checked.
         *
         * @param message The error message detailing the issue during Date Picker operation.
         */

        public TimeoutException(String message) {
            super(message);
        }

        /**
         * Constructs the exception with a message explaining the date picker operation error
         * and includes the underlying cause of the failure.
         *
         * @param message The error message detailing the issue during Date Picker operation.
         * @param cause   The underlying cause of the exception.
         */
        public TimeoutException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class EncryptionException extends ExceptionHub {

        /**
         * Constructs the exception with a message explaining the Encryption operation error
         * and includes the underlying cause of the failure.
         *
         * @param message The error message detailing the issue during Encryption.
         * @param cause The underlying cause of the exception.
         */
        public EncryptionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
