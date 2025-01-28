package com.qa.ctf.factories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory1 {

//    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver initializeDriver(String browser) {
        WebDriver driver = switch (browser) {
            case "chrome" -> new ChromeDriver();
            case "firefox" -> new FirefoxDriver();
            default -> throw new IllegalArgumentException("Invalid browser: " + browser);
        };
        driver.manage().window().maximize();
  //      DriverFactory.driver.set(driver);
        return driver;
    }


//    /*public static WebDriver getDriver() {
//        return driver.get();
//    }*/

}
