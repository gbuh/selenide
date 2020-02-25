package com.epam.il.selenide;

import com.codeborne.selenide.Configuration;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;

import static com.codeborne.selenide.Selenide.open;

public class DriverFactory {
    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);
    private static ITestContext testContext;

    private DriverFactory() {
    }

    public static synchronized void setupDriverInstance(ITestContext context) {
        DesiredCapabilities capabilities;
        int randomDigit = (int) (Math.random() * 100000);
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
                    Configuration.browser = "firefox";
                    manageDriver(context);
                    LOGGER.info("Firefox browser was set up.");
                    break;
                case DOCKERC:
                    capabilities = new DesiredCapabilities();
                    //                    capabilities.setBrowserName("chrome");
                    capabilities.setCapability("enableVNC", true);
                    capabilities.setCapability("enableVideo", true);
                    capabilities.setCapability("videoName", "Chrome-" + randomDigit + ".mp4");
                    capabilities.setCapability("videoFrameRate", 24);
                    capabilities.setCapability("videoScreenSize", "1920x1080");
                    capabilities.setCapability("enableLog", true);
                    capabilities.setCapability("logName", "Chrome-" + randomDigit + ".log");
                    Configuration.browserCapabilities = capabilities;
                    Configuration.remote = testContext.getCurrentXmlTest().getAllParameters().get("hub_url");
                    setupChromeDriver(context);
                    LOGGER.info("'Selenoid' chrome 'GRID HUB' was set up.");
                    break;
                case DOCKERF:
                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability("enableVNC", true);
                    capabilities.setCapability("enableVideo", true);
                    capabilities.setCapability("videoName", "Firefox-" + randomDigit + ".mp4");
                    capabilities.setCapability("videoFrameRate", 24);
                    capabilities.setCapability("videoScreenSize", "1920x1080");
                    capabilities.setCapability("enableLog", true);
                    capabilities.setCapability("logName", "Firefox-" + randomDigit + ".log");
                    Configuration.browserCapabilities = capabilities;
                    Configuration.browser = "firefox";
                    Configuration.remote = testContext.getCurrentXmlTest().getAllParameters().get("hub_url");
                    manageDriver(context);
                    LOGGER.info("'Selenoid' firefox 'GRID HUB' was set up.");
                    break;
                case REMOTEC:
                    Configuration.remote = testContext.getCurrentXmlTest().getAllParameters().get("hub_url");
                    setupChromeDriver(context);
                    LOGGER.info("Remote chrome in the Docker was created.");
                    break;
                case REMOTEF:
                    Configuration.browser = "firefox";
                    Configuration.remote = testContext.getCurrentXmlTest().getAllParameters().get("hub_url");
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

    private static void setupChromeDriver(ITestContext context) {
        Configuration.browser = "chrome";
        manageDriver(context);
    }

    private static void manageDriver(ITestContext context) {
        Configuration.baseUrl = context.getCurrentXmlTest().getAllParameters().get("hub_url");
        Configuration.startMaximized = true;
        Configuration.timeout = 7000;
        Configuration.reportsFolder = "reports";
        //        getWebDriver().manage().window().maximize();
        //        getWebDriver().manage().deleteAllCookies();
        if (context.getCurrentXmlTest().getAllParameters().containsKey("url")) {
            open(context.getCurrentXmlTest().getAllParameters().get("url"));
        }
    }
}
