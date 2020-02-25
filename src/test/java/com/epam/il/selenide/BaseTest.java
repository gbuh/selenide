package com.epam.il.selenide;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.time.LocalDateTime;

public abstract class BaseTest {
    protected long startTime;
    protected long endTime;
    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);

    @BeforeSuite(alwaysRun = true)
    public void onStartSuite() {
        startTime = System.currentTimeMillis();
        LOGGER.info("Test suite start time: " + LocalDateTime.now().toString());
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestContext context) {
        DriverFactory.setupDriverInstance(context);
        //        context.setAttribute("WebDriver", this.driver);
    }

    @AfterSuite
    public void timeSlotEnd() {
        endTime = System.currentTimeMillis();
        LOGGER.info("Test suite end time: " + LocalDateTime.now().toString());
        LOGGER.info("Test suite duration (ms): " + (endTime - startTime));
        System.out.println("\n++++++++++Test suite duration (ms): " + (endTime - startTime) + "++++++++++++\n");
    }
}
