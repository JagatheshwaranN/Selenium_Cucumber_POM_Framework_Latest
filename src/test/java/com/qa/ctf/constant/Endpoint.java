package com.qa.ctf.constant;

public enum Endpoint {

    STORE("/store"),
    ACCOUNT("/account"),
    ADD_TO_CART("/?wc-ajax=add_to_cart"),
    CHECKOUT("/checkout");

    public final String url;

    Endpoint(String url) {
        this.url = url;
    }
}
