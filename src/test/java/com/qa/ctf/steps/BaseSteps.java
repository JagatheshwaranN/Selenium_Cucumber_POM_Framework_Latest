package com.qa.ctf.steps;

import com.qa.ctf.base.PageFactory;
import com.qa.ctf.context.TestContext;

public class BaseSteps {

    protected PageFactory pageFactory;

    public BaseSteps(TestContext testContext) {
        pageFactory = new PageFactory();
        pageFactory.setTestContext(testContext);
    }

}
