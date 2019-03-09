package com.saucedemo.web.componenets.pageObjects;

import com.saucedemo.web.componenets.ExpectedConditions;
import com.saucedemo.web.componenets.StandardPage;
import com.saucedemo.web.componenets.pageObjects.helpers.CartItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class CartPage extends StandardPage{

    public CartPage(WebDriver driver) {
        super(driver);
    }

    protected ExpectedCondition<Boolean> isPageLoadedCondition() {
        return ExpectedConditions.presenceOfElementsLocated(By.id("cart_contents_container"));
    }

    @Override
    protected String getExpectedTitle() {
        return getTitle();
    }

    /**
     *
     * @param productName
     *            - name of single product in shopping Cart
     */
    public CartItem getLineItem(String productName) {
        return new CartItem(productName, getDriver());
    }

    /**
     *
     * @param index
     *            - index of product in Cart. starts from 0.
     * @return <br>
     *         CartItem - object of cart product
     *
     *         Example: <br>
     *         If Cart has 5 products and you want to take third one: <br>
     *         getLineItem(2);
     */
    public CartItem getLineItem(int index) {
        waitForPageToLoad(getDriver());
        return new CartItem(index, getDriver());
    }

    private WebElement getContinueShoppingLink() {
        return findElementByClassName("cart_cancel_link");
    }

    private WebElement getCheckoutButton() {
        return findElementByClassName("cart_checkout_link");
    }

    public int getNumberOfItemsInTheCart() {
        return getCartItems().size();
    }

    public List<WebElement> getCartItems() {
        return findElementsByClassName("cart_item");
    }

    public ProductsPage contiuneShopping() {
        getContinueShoppingLink().click();

        return new ProductsPage(getDriver());
    }

    public CartPage emptyCart() {

        while(getCartItems().size() >= 1) {
            CartItem cartItem = getLineItem(0);
            cartItem.removeItem();
        }

        return new CartPage(getDriver());
    }

    //TODO: add locator and navigation to Checkout Page.

}
