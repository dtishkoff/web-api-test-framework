package com.sausedemo.web.tests;

import com.saucedemo.web.componenets.pageObjects.CartPage;
import com.saucedemo.web.componenets.pageObjects.HomePage;
import com.saucedemo.web.componenets.pageObjects.ProductsPage;
import com.saucedemo.web.componenets.pageObjects.helpers.CartItem;
import org.junit.Test;
import static org.junit.Assert.*;

public class AddItemsToCartTest extends BaseTest {

    @Test
    public void addItemsToCart() {
        String productOne = "Sauce Labs Onesie";
        String productTwo = "Sauce Labs Bike Light";

        HomePage homePage = goToHomePage();
        ProductsPage productsPage = homePage.signIn(getUserName(), getPassword());

        productsPage.addItemToCartByName(productOne);
        assertTrue(productsPage.getNumberOfItemsInTheCart() == 1);

        productsPage.addItemToCartByName(productTwo);
        assertTrue(productsPage.getNumberOfItemsInTheCart() == 2);

        CartPage cartPage = productsPage.goToCart();
        assertTrue(cartPage.getNumberOfItemsInTheCart() == 2);

        CartItem itemOne = cartPage.getLineItem(0);
        itemOne.getItemPrice();
        //TODO: compare with price in the products page
        itemOne.getItemQty();
        //TODO: compare the quantity is 1

        CartItem itemTwo = cartPage.getLineItem("Sauce Labs Onesie");
        //TODO: verify name.
        assertEquals("The name of the product in the cart should match same product from Product page",
                productOne, itemTwo.getItemName());

        cartPage.emptyCart();
    }
}
