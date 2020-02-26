package com.epam.il.selenide;

import com.codeborne.selenide.testng.ScreenShooter;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

@Listeners({ScreenShooter.class})
public class GoogleMainPageTest extends BaseTest {
    @Parameters({"browser", "url"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, @Optional("https://google.com") String url) {
        open(url);
        //        ScreenShooter.captureSuccessfulTests = true;
    }

    @Test(groups = "windows.function")
    public void testGoogleSearch_Gangnamstyle_PsyYoutubeChanelOnTop(Method method) {
        long id = Thread.currentThread().getId();
        System.out.println("Before test-method. Thread id is: " + id);
        GoogleMainPage mainPage = new GoogleMainPage();
        mainPage.setText("gangnam style");
        //        screenshot(method.getName());
        //        $(By.cssSelector("h3.LC20lb")).shouldHave(Condition.text("PSY - GANGNAM STYLE"));
        Assert.assertTrue((mainPage.getFirstSearchResult().toUpperCase().contains("PSY - GANGNAM STYLE")),
                "PSY - YouTube is on the first search position");
    }

    @Test(groups = "ios.function")
    public void testGoogleSearch_NoDoubte_NDYoutubeChanelOnTop() {
        long id = Thread.currentThread().getId();
        System.out.println("Before test-method. Thread id is: " + id);
        GoogleMainPage mainPage = new GoogleMainPage();
        mainPage.setText("no doubt don't speak");
        Assert.assertTrue((mainPage.getFirstSearchResult().toUpperCase().contains("NO DOUBT - DON'T SPEAK")),
                "No Doubt Don't Speak - YouTube is on the first search position");
    }

    @Test(groups = "not_start")
    public void testGoogleSearch_CanTouchThis_CTTYoutubeChanelOnTop() {
        long id = Thread.currentThread().getId();
        System.out.println("Before test-method. Thread id is: " + id);
        GoogleMainPage mainPage = new GoogleMainPage();
        mainPage.setText("can't touch this");
        Assert.assertTrue((mainPage.getFirstSearchResult().toUpperCase().contains("MC HAMMER - U CAN'T TOUCH THIS")),
                "MC Hammer - U Can't Touch This - YouTube is on the first search position");
    }

    @Test(dataProvider = "csvdataset", dataProviderClass = CsvDataProvider.class)
    public void testFindFirstResult(Map<String, String> testData) {
        long id = Thread.currentThread().getId();
        System.out.println("Before test-method. Thread id is: " + id);
        String shouldBe = " - YouTube should be on the first search position";
        String number = testData.get("number");
        String question = testData.get("findWord");
        String answer = testData.get("expectedPhrase");
        GoogleMainPage mainPage = new GoogleMainPage();
        mainPage.setText(question);
        Assert.assertTrue((mainPage.getFirstSearchResult().toUpperCase().contains(answer)), answer + shouldBe);
    }
}
