package com.saucedemo.web.componenets;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Map;
import java.util.Properties;

/**
 * BasePage provides a convenient extension point for implementers of Page.
 */
public abstract class BasePage extends Component {
    private static final int PAGE_LOAD_TIME_OUT_IN_SECONDS_DEFAULT = 5;
    private static final String SFLY_FUNC_TEST_DEFAULT_PAGE_TIME_OUT_IN_SEC = "SFLY_FUNC_TEST_DEFAULT_PAGE_TIME_OUT_IN_SEC";

    private final Properties resources;
    private final ExpectedCondition<Boolean> isPageLoadedCondition;
    private static int currentPageLoadTimeOutInSecondsDefault = -1;

    public BasePage(WebDriver driver) {
        this(driver, (ExpectedCondition)null);
    }


    /**
     * Create a page providing both the driver and an ExpectedCondition that can be used to validate
     * that the correct page loaded.
     *
     * @param driver is the driver for the page
     * @param isPageLoadedCondition validates the proper page has loaded
     */
    public BasePage(WebDriver driver, ExpectedCondition<Boolean> isPageLoadedCondition) {
        super(driver);

        this.isPageLoadedCondition = isPageLoadedCondition;
        this.resources = new Properties();

        waitForPageToLoad(driver);
    }


    /**
     * waitForPageToLoad is used to wait
     * until the page has loaded and is ready for further validation.
     * @param driver is the driver to wait with
     */
    protected void waitForPageToLoad(final WebDriver driver) {
        waitFor(driver, isPageLoadedCondition(), getPageLoadTimeOut());
    }

    /**
     * Get an ExpectedCondition to use in validating that the underlying page has loaded for this Page object.
     * Currently, this implementation verifies that the page's title matches <code>getExpectedTitle()</code>
     *
     * @return an ExpectedCondition<Boolean>
     * @see org.openqa.selenium.support.ui.ExpectedConditions
     */
    protected ExpectedCondition<Boolean> isPageLoadedCondition() {
        return isPageLoadedCondition == null ?
                ExpectedConditions.titleIs(getExpectedTitle()) :
                isPageLoadedCondition;
    }

    /**
     * Get the page's load timeout, in seconds.
     *
     * @return the page's load timeout
     */
    protected int getPageLoadTimeOut() {
        return getDefaultPageLoadTimeOutInSeconds();
    }

    public static int getDefaultPageLoadTimeOutInSeconds() {
        if(currentPageLoadTimeOutInSecondsDefault <= 0)
            currentPageLoadTimeOutInSecondsDefault = loadDefaultPageLoadTimeOutInSeconds();
        return currentPageLoadTimeOutInSecondsDefault;
    }

    private static int loadDefaultPageLoadTimeOutInSeconds(){
        Map<String, String> env = System.getenv();
        int loadedDefaultPageLoadTimeout;

        if(env.containsKey(SFLY_FUNC_TEST_DEFAULT_PAGE_TIME_OUT_IN_SEC))
            loadedDefaultPageLoadTimeout = Integer.parseInt(env.get(SFLY_FUNC_TEST_DEFAULT_PAGE_TIME_OUT_IN_SEC));
        else
            loadedDefaultPageLoadTimeout = PAGE_LOAD_TIME_OUT_IN_SECONDS_DEFAULT;

        Logger.getLogger(BasePage.class).info("Loaded default page load timeout: " +
                Integer.toString(loadedDefaultPageLoadTimeout) + " seconds.");
        return loadedDefaultPageLoadTimeout;
    }

    protected abstract String getExpectedTitle();


    public String getTitle() {
        return getDriver().getTitle();
    }
}

