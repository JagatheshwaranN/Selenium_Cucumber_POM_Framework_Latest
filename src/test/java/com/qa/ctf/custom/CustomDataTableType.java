package com.qa.ctf.custom;

import com.qa.ctf.objects.BillingDetails;
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
