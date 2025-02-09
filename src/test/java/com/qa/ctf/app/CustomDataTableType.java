package com.qa.ctf.app;

import com.qa.ctf.data.BillingDetails;
import io.cucumber.java.DataTableType;

import java.util.Map;

public class CustomDataTableType {

    @DataTableType
    public BillingDetails billingDetails(Map<String, String> entry) {
        return new BillingDetails(
                entry.get("firstname"),
                entry.get("lastname"),
                entry.get("address_line"),
                entry.get("city"),
                entry.get("state"),
                entry.get("zipcode"),
                entry.get("email")
        );
    }
}
