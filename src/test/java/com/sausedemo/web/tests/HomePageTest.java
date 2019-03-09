package com.sausedemo.web.tests;

import com.saucedemo.web.componenets.pageObjects.HomePage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HomePageTest extends BaseTest {

    @Test
    public void test_homepage_content() {
        HomePage homePage = goToHomePage();

        assertEquals("",homePage.getTitle());
    }
}
