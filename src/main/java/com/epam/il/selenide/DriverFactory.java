package com.epam.il.selenide;

import com.codeborne.selenide.Configuration;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import static com.codeborne.selenide.Selenide.open;

public final class DriverFactory {
    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);
    private static final String HUB_URL = "hub_url";
    private static final String CHROME = "chrome";
    private static final String FIREFOX = "firefox";
    private static final int IMPLICIT_WAIT_TIMEOUT = 7000;
    private static final int VIDEO_FRAME_RATE = 24;

    private DriverFactory() {
    }

    public static synchronized void setupDriverInstance(final ITestContext context) {
        DesiredCapabilities capabilities;
        ITestContext testContext;
        int randomDigit = 0;
        try {
            randomDigit = (SecureRandom.getInstanceStrong().nextInt());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.info(Arrays.toString(e.getStackTrace()));
        }
        testContext = context;
        if (context.getCurrentXmlTest().getAllParameters().containsKey("browser")) {
            Browsers browser =
                    Browsers.valueOf(context.getCurrentXmlTest().getAllParameters().get("browser").toUpperCase());
            switch (browser) {
                case CHROME:
                    setupChromeDriver(context);
                    LOGGER.info("Chrome browser was created.");
                    break;
                case FIREFOX:
                    Configuration.browser = FIREFOX;
                    manageDriver(context);
                    LOGGER.info("Firefox browser was set up.");
                    break;
                case DOCKERC:
                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability("enableVNC", true);
                    capabilities.setCapability("enableVideo", true);
                    capabilities.setCapability("videoName", "Chrome-" + randomDigit + ".mp4");
                    capabilities.setCapability("videoFrameRate", VIDEO_FRAME_RATE);
                    capabilities.setCapability("videoScreenSize", "1920x1080");
                    capabilities.setCapability("enableLog", true);
                    capabilities.setCapability("logName", "Chrome-" + randomDigit + ".log");
                    Configuration.browserCapabilities = capabilities;
                    Configuration.remote = testContext.getCurrentXmlTest().getAllParameters().get(HUB_URL);
                    setupChromeDriver(context);
                    LOGGER.info("'Selenoid' chrome 'GRID HUB' was set up.");
                    break;
                case DOCKERF:
                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability("enableVNC", true);
                    capabilities.setCapability("enableVideo", true);
                    capabilities.setCapability("videoName", "Firefox-" + randomDigit + ".mp4");
                    capabilities.setCapability("videoFrameRate", VIDEO_FRAME_RATE);
                    capabilities.setCapability("videoScreenSize", "1920x1080");
                    capabilities.setCapability("enableLog", true);
                    capabilities.setCapability("logName", "Firefox-" + randomDigit + ".log");
                    Configuration.browserCapabilities = capabilities;
                    Configuration.browser = FIREFOX;
                    Configuration.remote = testContext.getCurrentXmlTest().getAllParameters().get(HUB_URL);
                    manageDriver(context);
                    LOGGER.info("'Selenoid' firefox 'GRID HUB' was set up.");
                    break;
                case REMOTEC:
                    Configuration.remote = testContext.getCurrentXmlTest().getAllParameters().get(HUB_URL);
                    setupChromeDriver(context);
                    LOGGER.info("Remote chrome in the Docker was created.");
                    break;
                case REMOTEF:
                    Configuration.browser = FIREFOX;
                    Configuration.remote = testContext.getCurrentXmlTest().getAllParameters().get(HUB_URL);
                    manageDriver(context);
                    LOGGER.info("Remote firefox in the Docker was created.");
                    break;
                default:
                    setupChromeDriver(context);
                    LOGGER.info("Chrome browser was created by default.");
            }
        } else {
            setupChromeDriver(context);
            LOGGER.info("Chrome browser was created by default.");
        }
    }

    private static void setupChromeDriver(final ITestContext context) {
        Configuration.browser = CHROME;
        manageDriver(context);
    }

    private static void manageDriver(final ITestContext context) {
        Configuration.baseUrl = context.getCurrentXmlTest().getAllParameters().get(HUB_URL);
        Configuration.startMaximized = true;
        Configuration.timeout = IMPLICIT_WAIT_TIMEOUT;
        Configuration.reportsFolder = "reports";
        if (context.getCurrentXmlTest().getAllParameters().containsKey("url")) {
            open(context.getCurrentXmlTest().getAllParameters().get("url"));
        }
    }
}
