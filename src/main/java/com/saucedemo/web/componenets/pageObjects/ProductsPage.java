package com.saucedemo.web.componenets.pageObjects;

import com.saucedemo.web.componenets.BasePage;
import com.saucedemo.web.componenets.ExpectedConditions;
import com.saucedemo.web.componenets.StandardPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class ProductsPage extends StandardPage {


    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    protected ExpectedCondition<Boolean> isPageLoadedCondition() {
        return ExpectedConditions.presenceOfElementsLocated(By.className("product_label"));
    }

    @Override
    protected String getExpectedTitle() {
        return getTitle();
    }

    private List<WebElement> getAllProductsElements() {
        return findElementsByCssSelector("div[class=inventory_list] div[class=inventory_item]");
    }

    private WebElement getProductByName(String name) {
        List<WebElement> products = getAllProductsElements();
        WebElement found = null;
        for(WebElement product : products) {
            if(product.getText().contains(name)) {
                found = product;
            }
        }

        return  found;
    }

    private WebElement getAddToCartButtonByProductName(String name) {
        WebElement product = getProductByName(name);
        return product.findElement(By.className("add-to-cart-button"));
    }

    private WebElement getRemoveButtonByProductName(String name) {
        WebElement product = getProductByName(name);
        return product.findElement(By.className("add-to-cart-button"));
    }

    public void addItemToCartByName(String name) {
        getAddToCartButtonByProductName(name).click();
    }

    public void removeItemFromCartByName(String name) {
        getRemoveButtonByProductName(name).click();
    }

    private WebElement getCartIconElement() {
        return findElementById("shopping_cart_container");
    }

    private WebElement getShoppingCartBadge() {
        return getCartIconElement().findElement(By.cssSelector("span[class^=fa-layers-counter]"));
    }

    public int getNumberOfItemsInTheCart() {
        String itemsInTheCart = getShoppingCartBadge().getText();

        return Integer.valueOf(itemsInTheCart);
    }

    public CartPage goToCart() {
        getCartIconElement().click();

        return new CartPage(getDriver());
    }
}
