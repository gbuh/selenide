package com.epam.il.selenide;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class GoogleMainPage {
    private final String RESULT = "h3.LC20lb";
    private final String SET_TEXT = "q";//input.gLFyf.gsfi

    public void setText(String text) {
        $(By.name(SET_TEXT)).setValue(text).pressEnter();
    }

    public String getFirstSearchResult() {
        return $(RESULT).getText();
    }
}
