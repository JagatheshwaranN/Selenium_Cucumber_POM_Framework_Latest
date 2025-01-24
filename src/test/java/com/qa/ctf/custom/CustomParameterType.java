package com.qa.ctf.custom;

import com.qa.ctf.objects.Product;
import io.cucumber.java.ParameterType;

public class CustomParameterType {

    @ParameterType(".*")
    public Product product(String productName) {
        return new Product(productName.replace("\"",""));
    }
}
