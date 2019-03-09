package com.saucedemo.web.componenets;

import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class Component extends TestWatcher{

    private final WebDriver driver;

    public Component(WebDriver driver) {
        this.driver = driver;
    }

    protected void waitFor(WebDriver driver, ExpectedCondition<Boolean> expectedCondition, int timeOutInSeconds) {
        this.getLogger().info("waiting up to " + timeOutInSeconds * 1000 + "ms for expectedCondition: " + expectedCondition);
        WebDriverWait wait = new WebDriverWait(driver, (long)timeOutInSeconds, 250L);
        long tStart = System.currentTimeMillis();
        wait.until(expectedCondition);
        long tElapsed = System.currentTimeMillis() - tStart;
        this.getLogger().info("waited for page load " + tElapsed + "ms");
    }

    protected Logger getLogger() {
        return Logger.getLogger(this.getClass());
    }

    /**
     * Return true if an element is present
     * @param locatorKey	Locator key of the element
     * @return 				true if element is found
     */
    protected boolean isElementPresent(By locatorKey) {
        try {
            getDriver().findElement(locatorKey);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Find an element by id -- shorthand for
     * <code>getDriver().findElement(By.id(id))</code>.
     *
     * @param id
     *            the id of the element to find
     * @return the element, if present
     *
     * @see WebDriver#findElement(org.openqa.selenium.By)
     */
    protected WebElement findElementById(String id) {
        return getDriver().findElement(By.id(id));
    }

    /**
     * Find the first element with the specified className -- shorthand for
     * <code>getDriver().findElement(By.className(className))</code>.
     *
     * @param className
     *            the CSS class name identifying the element
     * @return the first element with the specified class name
     *
     * @see WebDriver#findElement(org.openqa.selenium.By)
     */
    protected WebElement findElementByClassName(String className) {
        return getDriver().findElement(By.className(className));
    }

    /**
     * Find the elements matching the specified selector -- shorthand for
     * <code>getDriver().findElements(By.cssSelector(selector))</code>.
     *
     * @param selector
     *            the CSS selector identifying the element
     * @return the elements matching the selector
     *
     * @see WebDriver#findElements(org.openqa.selenium.By)
     */
    protected List<WebElement> findElementsByCssSelector(String selector) {
        return getDriver().findElements(By.cssSelector(selector));
    }

    /**
     * Find a list of elements by id -- shorthand for
     * <code>getDriver().findElements(By.id(id))</code>. This method is
     * primarily useful for testing that an element with the specified id does
     * <em>not</em> exist by finding that list of the matching elements is
     * empty.
     *
     * @param id
     *            the id of the element(s) to find
     * @return the elements, if present
     *
     * @see WebDriver#findElements(org.openqa.selenium.By)
     */
    protected List<WebElement> findElementsById(String id) {
        return getDriver().findElements(By.id(id));
    }

    /**
     * Find the elements with the specified className -- shorthand for
     * <code>getDriver().findElements(By.className(className))</code>.
     *
     * @param className
     *            the CSS class name identifying the elements
     * @return a list of element with the specified class name
     *
     * @see WebDriver#findElements(org.openqa.selenium.By)
     */
    protected List<WebElement> findElementsByClassName(String className) {
        return getDriver().findElements(By.className(className));
    }

}
