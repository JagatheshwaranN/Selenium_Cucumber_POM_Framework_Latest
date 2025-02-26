package com.qa.ctf.base;

import io.cucumber.java.After;

public class AppHook {

    @After(order = 2)
    public void resetThreadLocal() {
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ RESET");
        PageFactory.resetThreadLocal();
    }

}
