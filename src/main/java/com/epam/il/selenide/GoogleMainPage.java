package com.epam.il.selenide;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Google search home page view.
 */
public class GoogleMainPage {
    private static final String RESULT = "h3.LC20lb";
    private static final String SET_TEXT = "q";

    /**
     * Type the provided text message into search field and press Enter button.
     *
     * @param text for searching
     */
    public void setText(final String text) {
        $(By.name(SET_TEXT)).setValue(text).pressEnter();
    }

    /**
     * Get text from the first searched result.
     *
     * @return text from the first searched result
     */
    public String getFirstSearchResult() {
        return $(RESULT).getText();
    }
}
