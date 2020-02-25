package com.epam.il.selenide;

public enum Browsers {
    CHROME("chrome"),
    FIREFOX("firefox"),
    REMOTEC("grid_chrome"),
    REMOTEF("grid_firefox"),
    DOCKERC("selenoid_chrome"),
    DOCKERF("selenoid_firefox");

    private Browsers(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
