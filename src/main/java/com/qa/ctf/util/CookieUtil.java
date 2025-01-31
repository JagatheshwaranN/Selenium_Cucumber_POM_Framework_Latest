package com.qa.ctf.util;

import io.restassured.http.Cookies;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

public class CookieUtil {

    public List<Cookie> convertRestAssuredCookieToSelenium(Cookies cookies) {

        List<io.restassured.http.Cookie> restAssuredCookies;
        restAssuredCookies = cookies.asList();
        List<Cookie> seleniumCookieList = new ArrayList<>();
        for(io.restassured.http.Cookie cookie : restAssuredCookies) {
            seleniumCookieList.add(new Cookie(cookie.getName(),
                    cookie.getValue(),
                    cookie.getDomain(),
                    cookie.getPath(),
                    cookie.getExpiryDate(),
                    cookie.isSecured(),
                    cookie.isHttpOnly(),
                    cookie.getSameSite()
            ));
        }
        return seleniumCookieList;
     }

}
