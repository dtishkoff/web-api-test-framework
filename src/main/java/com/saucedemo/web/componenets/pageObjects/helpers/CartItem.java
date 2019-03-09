package com.saucedemo.web.componenets.pageObjects.helpers;

import com.saucedemo.web.componenets.pageObjects.CartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartItem extends CartPage{

    private int index;
    private String productName;
    private WebElement parent;

    public CartItem(WebDriver driver) {
        super(driver);
    }

    public CartItem(int index, WebDriver driver) {
        super(driver);
        this.index = index;
        this.parent = getCartItemByIndex(index);
    }

    public CartItem(String productName, WebDriver driver) {
        super(driver);
        this.productName = productName;
        this.parent = getCartItemByProductName(productName);
    }

    private WebElement getCartItemByProductName(String name) {
        for(WebElement item : getCartItems()) {
            if(item.findElement(By.className("inventory_item_name")).getText().equals(name)) {
                return item;
            }
        }

        return null;
    }

    private WebElement getCartItemByIndex(int index) {
        return getCartItems().get(index);
    }

    public int getItemQty() {
        String qty = parent.findElement(By.className("cart_quantity")).getText();

        return Integer.valueOf(qty);
    }

    public String getItemPrice() {
        return parent.findElement(By.className("inventory_item_price")).getText();
    }

    private WebElement getRemoveItemButton() {
        return parent.findElement(By.className("remove-from-cart-button"));
    }

    public void removeItem() {
       getRemoveItemButton().click();
    }

    private WebElement findItemNameElement() {
        return parent.findElement(By.className("inventory_item_name"));
    }

    public String getItemName() {
        return findItemNameElement().getText();
    }

}
