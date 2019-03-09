package com.sausedemo.web.tests;

import com.saucedemo.web.componenets.PropertyLoader;
import com.saucedemo.web.componenets.pageObjects.HomePage;
import com.saucedemo.web.util.WebDriverLoader;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static javax.print.DocFlavor.URL.PNG;

/**
 * BaseFunctionalTest is responsible for providing a consistent property-loading and logging
 * for functional tests.
 */
public abstract class BaseTest extends TestWatcher{

    private static final String DEFAULTS_PROPERTIES_LOCATION = "/defaultTest.properties";
    private static final String SCREENSHOT = "screenshot-";
    private static final String DATE_FORMAT_SCREENSHOT = "yyyyMMdd-HHmmss";

    protected WebDriver driver = null;
    private String webdriver;
    private String protocol;
    private String secureSiteURL;
    private String siteURL;

    private String userName;
    private String password;

    private Properties properties;

    @Before
    public void before() throws Exception {
        setProperties(loadProperties());

        Properties properties = getProperties();
        Properties overrideProperties = loadOverrideProperties();
        properties.putAll(overrideProperties);

        this.siteURL = "http://" + properties.getProperty("server.hostname");
        this.secureSiteURL = "https://" + properties.getProperty("server.hostname");

        webdriver = properties.getProperty("webdriver");
        protocol = properties.getProperty("server.protocol");
        this.userName = properties.getProperty("userName");
        this.password = properties.getProperty("password");

        setDriver(createDriver(getClass().getName()));
    }

    @After
    public void after() throws Exception {
        driver.close();
    }

    protected Properties loadOverrideProperties() {
        final Properties overrideProperties = new Properties();
        try {
            InputStream stream = BaseTest.class.getResourceAsStream(DEFAULTS_PROPERTIES_LOCATION);
            if (stream != null) {
                overrideProperties.load(stream);
                getLogger().info("Loaded some properties from " + DEFAULTS_PROPERTIES_LOCATION);
            }
        } catch (IOException e) {
            getLogger().error("IOEXception while loading " + DEFAULTS_PROPERTIES_LOCATION + " file: " + e.toString(), e);
            throw new RuntimeException("Can't load file " + DEFAULTS_PROPERTIES_LOCATION +
                    " from classpath. Error is: " + e.toString(), e);
        }
        return overrideProperties;
    }

    protected void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * Get the list of property files to be loaded for this test. File paths
     * should be relative to the classloader root.
     */
    protected ArrayList<String> getPropertyFileList() {
        ArrayList<String> propFileList = new ArrayList<String>();
        propFileList.add("/defaultTest.properties");
        return propFileList;
    }


    protected Properties loadProperties() {
        ArrayList<String> propFileList = getPropertyFileList();
        try {
            return PropertyLoader.loadDefaultAndCustomProps(propFileList);
        } catch (IOException e) {
            throw new RuntimeException("Could not load properties from " + propFileList, e);
        }
    }

    /**
     * @return Returns the base site url based on environment
     * */
    public String getSiteURL() {
        URL newURL;
        try {
            if (protocol.equals("https")) {
                newURL = new URL(secureSiteURL);
            } else {
                newURL = new URL(siteURL);
            }
            return newURL.toString();
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Failed to build site url", exception);
        }
    }

    protected void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    protected WebDriver createDriver(String testName) {
        return (new WebDriverLoader(getProperties(), testName)).loadWebDriver(webdriver);
    }

    public HomePage goToHomePage() {
        String url = getSiteURL();
        if (url.contains("beta")) {
            long timeToWaitForPageLoad = 15L;
            this.driver.manage().timeouts().pageLoadTimeout(timeToWaitForPageLoad, TimeUnit.SECONDS);

            try {
                this.driver.get(url);
                getLogger().info(url);
            } catch (TimeoutException e) {
                getLogger().info("Page Load time exceeded: " + timeToWaitForPageLoad + "sec. Attempting to continue the test.");
            }
        } else {
            this.driver.get(url);
            getLogger().info(url);
        }

        return new HomePage(this.driver);
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    private void captureScreenshot(String screenshotQualifier) {
        getLogger().debug("capturing screenshot for " + screenshotQualifier);
        try {
            WebDriver driver = this.driver;

            if (driver != null && driver instanceof TakesScreenshot) {
                getLogger().debug("driver takes screenshots: " + driver);
                TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

                if (ensureScreenshotDirectoryExists()) {
                    //capture screenshot to a (temporary) file and then copy to someplace more permanent
                    File tempScreenshotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
                    File destScreenshotFile = new File(createScreenshotFilename(screenshotQualifier));
                    FileUtils.copyFile(tempScreenshotFile, destScreenshotFile, true);

                    getLogger().info("screenshot is available at: " + destScreenshotFile.getAbsolutePath());
                } else {
                    getLogger().warn("Skipping screenshot because path to screenshot directory" +
                            " is not writable or cannot be created: " + getScreenshotDirParent());
                }

            } else {
                getLogger().warn("was asked to capture screenshot, but driver was null: " + (driver == null)
                        + " or does not implement TakesScreenshot");
            }
        } catch (Exception e) {
            getLogger().warn("could not capture screenshot", e);
        }
    }

    protected static String createScreenshotFilename(String screenshotQualifier) {
        return getScreenshotDirParent() + File.separator + SCREENSHOT + screenshotQualifier + PNG;
    }

    private boolean ensureScreenshotDirectoryExists() {
        File parentDir = new File(getScreenshotDirParent());
        return parentDir.canWrite() || parentDir.mkdirs();
    }

    protected static String getScreenshotDirParent() {
        return System.getProperty("functional-test.screenshots.dir", "target/screenshots");
    }

    protected Logger getLogger() {
        return Logger.getLogger(getClass());
    }


    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SCREENSHOT);
            captureScreenshot(description.getTestClass().getSimpleName() + "-"
                    + description.getMethodName() + " "+ sdf.format(new Date()));
        }
    };
}

