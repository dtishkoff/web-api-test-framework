package com.saucedemo.web.componenets.pageObjects;

import com.saucedemo.web.componenets.BasePage;
import com.saucedemo.web.componenets.ExpectedConditions;
import com.saucedemo.web.componenets.StandardPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;


public class HomePage extends StandardPage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    protected ExpectedCondition<Boolean> isPageLoadedCondition() {
        return ExpectedConditions.presenceOfElementsLocated(By.id("login_credentials"));
    }

    @Override
    protected String getExpectedTitle() {
        return getTitle();
    }

    private WebElement getLoginFromElement() {
        return findElementById("login_button_container");
    }

    private WebElement getSignInField() {
        return getLoginFromElement().findElement(By.id("user-name"));
    }

    private WebElement getPasswordField() {
        return getLoginFromElement().findElement(By.id("password"));
    }

    private WebElement getLoginButton() {
        return findElementByClassName("login-button");
    }

    public void enterUserName(String userName) {
        getSignInField().clear();
        getSignInField().sendKeys(userName);
    }

    public void enterPassword(String password) {
        getPasswordField().clear();
        getPasswordField().sendKeys(password);
    }

    public ProductsPage signIn(String userName, String password) {
        enterUserName(userName);
        enterPassword(password);

        getLoginButton().click();

        return new ProductsPage(getDriver());
    }
}
