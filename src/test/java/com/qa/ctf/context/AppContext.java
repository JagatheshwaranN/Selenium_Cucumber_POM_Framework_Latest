package com.qa.ctf.context;

import com.qa.ctf.app.Cookies;
import com.qa.ctf.data.BillingDetails;

public class AppContext {

    public BillingDetails billingDetails;
    public Cookies cookies;

    public AppContext() {
        cookies = new Cookies();
        cookies.setCookies(new io.restassured.http.Cookies());
    }
}
