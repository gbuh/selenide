package com.epam.il.selenide;

import com.codeborne.selenide.testng.ScreenShooter;
import com.opencsv.exceptions.CsvValidationException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

@Listeners({ScreenShooter.class})
public class GoogleMainPageTest extends BaseTest {
    @Parameters({"browser", "url"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, @Optional("https://google.com") String url) {
        long id = Thread.currentThread().getId();
        System.out.println("Before test-method. Thread id is: " + id);
        open(url);
        //        ScreenShooter.captureSuccessfulTests = true;
    }

    @Test
    public void testBrowsersGetChromeName() {
        Assert.assertTrue("chrome".equals(Browsers.CHROME.getName()));
    }

    @Test(expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testCsvDataProviderCannotReadUncorrectedDataFile(Method method) throws IOException,
                                                                                       CsvValidationException {
            CsvDataProvider.provideData(method);

    }

    @Test(expectedExceptions = CsvValidationException.class)
    public void testCsvDataProviderCannotReadEmptyDataFile(Method method) throws IOException,
                                                                                       CsvValidationException {
            CsvDataProvider.provideData(method);

    }

    @Test(expectedExceptions = IOException.class)
    public void testCstDataProviderCannotGetDataFromNonexistentFile(Method method) throws IOException,
                                                                                          CsvValidationException {
            CsvDataProvider.provideData(method);
    }

    @Test(groups = "windows.function")
    public void testGoogleSearch_Gangnamstyle_PsyYoutubeChanelOnTop(Method method) {
        GoogleMainPage mainPage = new GoogleMainPage();
        mainPage.setText("gangnam style");
        //        screenshot(method.getName());
        //        $(By.cssSelector("h3.LC20lb")).shouldHave(Condition.text("PSY - GANGNAM STYLE"));
        Assert.assertTrue((mainPage.getFirstSearchResult().toUpperCase().contains("PSY - GANGNAM STYLE")),
                "PSY - YouTube is on the first search position");
    }

    @Test(groups = "ios.function")
    public void testGoogleSearch_NoDoubte_NDYoutubeChanelOnTop() {
        GoogleMainPage mainPage = new GoogleMainPage();
        mainPage.setText("no doubt don't speak");
        Assert.assertTrue((mainPage.getFirstSearchResult().toUpperCase().contains("NO DOUBT - DON'T SPEAK")),
                "No Doubt Don't Speak - YouTube is on the first search position");
    }

    @Test(groups = "not_start")
    public void testGoogleSearch_CanTouchThis_CTTYoutubeChanelOnTop() {
        GoogleMainPage mainPage = new GoogleMainPage();
        mainPage.setText("can't touch this");
        Assert.assertTrue((mainPage.getFirstSearchResult().toUpperCase().contains("MC HAMMER - U CAN'T TOUCH THIS")),
                "MC Hammer - U Can't Touch This - YouTube is on the first search position");
    }

    @Test(dataProvider = "csvdataset", dataProviderClass = CsvDataProvider.class)
    public void testFindFirstResult(Map<String, String> testData) {
        String shouldBe = " - YouTube should be on the first search position";
        String question = testData.get("findWord");
        String answer = testData.get("expectedPhrase");
        GoogleMainPage mainPage = new GoogleMainPage();
        mainPage.setText(question);
        Assert.assertTrue((mainPage.getFirstSearchResult().toUpperCase().contains(answer)), answer + shouldBe);
    }
}
