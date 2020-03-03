package com.epam.il.selenide;

/**
 * List of browsers available to run the tests.
 */
public enum Browsers {
    CHROME("chrome"),
    FIREFOX("firefox"),
    REMOTEC("grid_chrome"),
    REMOTEF("grid_firefox"),
    DOCKERC("selenoid_chrome"),
    DOCKERF("selenoid_firefox"),
    DEFAULT("default");

    /**
     * Enum constructor.
     * @param browserName browser string representation
     */
    Browsers(final String browserName) {
        this.name = browserName;
    }

    private String name;

    public String getName() {
        return name;
    }
}
