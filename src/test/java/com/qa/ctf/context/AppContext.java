package com.qa.ctf.context;

import com.qa.ctf.custom.Cookies;
import com.qa.ctf.objects.BillingDetails;

public class AppContext {

    public BillingDetails billingDetails;
    public Cookies cookies;

    public AppContext() {
        cookies = new Cookies();
        cookies.setCookies(new io.restassured.http.Cookies());
    }
}
