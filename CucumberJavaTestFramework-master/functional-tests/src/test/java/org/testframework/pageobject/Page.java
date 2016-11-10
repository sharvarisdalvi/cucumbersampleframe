package org.testframework.pageobject;

import org.testframework.Utils.TestWebDriver;
import org.openqa.selenium.WebElement;

public abstract class Page {

    public TestWebDriver testWebDriver;

    protected Page(TestWebDriver driver) {
        this.testWebDriver = driver;
    }
    public void sendKeys(WebElement locator, String value) {
        int length = testWebDriver.getAttribute(locator, "value").length();
        for (int i = 0; i < length; i++)
            locator.sendKeys("\u0008");
        locator.sendKeys(value);
    }

}