package com.qa.ctf.constants;

public enum Endpoint {

    STORE("/store"),
    ACCOUNT("/account");

    public final String url;

    Endpoint(String url) {
        this.url = url;
    }
}
