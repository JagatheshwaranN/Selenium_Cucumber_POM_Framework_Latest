package com.qa.ctf.constants;

public enum BrowserType {

    BROWSER("Browser"),
    CHROME("Chrome"),
    FIREFOX("Firefox"),
    EDGE("Edge");

    private final String browserType;

    BrowserType(String browserType){
        this.browserType = browserType;
    }

    public String getBrowserType() {
        return browserType;
    }

}
