package com.jtaf.qa.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jDemo {

    public static void main(String[] args) {

        Logger logger = LogManager.getLogger(Log4jDemo.class.getName());
        logger.error("Hi....");
        logger.info("Hello....");
        logger.info("How are you....");
    }
}
