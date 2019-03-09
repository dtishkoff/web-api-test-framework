package com.saucedemo.web.componenets;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * Abstraction for all Page classes.
 */
public abstract class StandardPage extends BasePage {


    public StandardPage(WebDriver driver) {
        this(driver, null);
    }

    public StandardPage(WebDriver driver, ExpectedCondition<Boolean> isPageLoadedCondition) {
        super(driver, isPageLoadedCondition);
    }

}

