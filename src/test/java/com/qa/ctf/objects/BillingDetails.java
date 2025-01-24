package com.qa.ctf.objects;

public class BillingDetails {

    private String billingFirstName;
    private String billingLastName;
    private String billingAddressLine;
    private String billingCity;
    private String billingState;
    private String billingZipcode;
    private String billingEmail;


    public BillingDetails(String billingFirstName, String billingLastName, String billingAddressLine, String billingCity, String billingState, String billingZipcode, String billingEmail) {
        this.billingFirstName = billingFirstName;
        this.billingLastName = billingLastName;
        this.billingAddressLine = billingAddressLine;
        this.billingCity = billingCity;
        this.billingState = billingState;
        this.billingZipcode = billingZipcode;
        this.billingEmail = billingEmail;
    }

    public String getBillingFirstName() {
        return billingFirstName;
    }

    public void setBillingFirstName(String billingFirstName) {
        this.billingFirstName = billingFirstName;
    }

    public String getBillingLastName() {
        return billingLastName;
    }

    public void setBillingLastName(String billingLastName) {
        this.billingLastName = billingLastName;
    }

    public String getBillingAddressLine() {
        return billingAddressLine;
    }

    public void setBillingAddressLine(String billingAddressLine) {
        this.billingAddressLine = billingAddressLine;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingZipcode() {
        return billingZipcode;
    }

    public void setBillingZipcode(String billingZipcode) {
        this.billingZipcode = billingZipcode;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }
}
