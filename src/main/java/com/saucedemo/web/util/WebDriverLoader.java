package com.saucedemo.web.util;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.Properties;




public class WebDriverLoader {

    public static final int DEFAULT_TIMEOUT_IN_SECONDS = 15;
    private static final String DRIVER_CHROME = "chrome";
    private static final String DRIVER_REMOTE_CHROME = "remote-chrome";
    private static final String DRIVER_HEADLESS_REMOTE_CHROME = "remote-headless-chrome";
    private static final String DRIVER_SAFARI = "safari";
    private static final String DRIVER_PHANTOMJS = "phantomjs";
    private static final String DRIVER_HEADLESS_CHROME = "headless-chrome";
    ;
    static final String BROWSER_SAFARI = "safari";
    static final String SAFARI_VERSION = "7.1";
    static final String PLATFORM_WIN = "win";
    static final String PLATFORM_MAC = "mac";


    private String testName = "unnamed test";



    public WebDriverLoader(Properties properties, String testName) {
        this.testName = testName;
    }

    public WebDriverLoader(Properties properties) {
        this(properties, "unnamed test");
    }

    /**
     * Construct a WebDriverLoader that uses the provided selenium grid location and defaults for all other configurations.
     *
     */

    public WebDriver loadWebDriver(String driverType) {
        WebDriver driver;

       if (isChrome(driverType)) {
            driver = loadChromeDriver();
        } else if (isSafari(driverType)) {
            driver = loadSafariDriver();
        } else {
            throw new IllegalArgumentException("Sorry, " + driverType + " is not supported yet");
        }
        setDefaultTimeout(driver, DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);

        return driver;
    }

    private boolean isChrome(String driverType) {
        return DRIVER_CHROME.equals(driverType);
    }

    private WebDriver loadChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-extensions");
        options.addArguments("ignore-certificate-errors");
        options.addArguments("--no-proxy-server");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        try {
            if(isPlatformWindows())//Remove the set property step, as we do not need it , if the path to driver is present in PATH variable.
                System.setProperty("webdriver.chrome.driver", FileUtilities.getResource("bin/x32/win/chromedriver.exe").getPath());
            else if(isPlatformMAC())
                System.setProperty("webdriver.chrome.driver", FileUtilities.getResource("bin/macosx/chromedriver").getPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ChromeDriver(capabilities);
    }

    private boolean isPlatformWindows(){
        if(System.getProperty("os.name").toLowerCase().startsWith(PLATFORM_WIN.toLowerCase()))
            return true;
        return false;
    }

    private boolean isWindows64Bit(){
        if(System.getProperty("os.arch").contentEquals("amd64"))
            return true;
        return false;
    }

    private boolean isPlatformMAC(){
        if(System.getProperty("os.name").toLowerCase().startsWith(PLATFORM_MAC.toLowerCase()))
            return true;
        return false;
    }

    private boolean isSafari(String driverType) {
        return DRIVER_SAFARI.equals(driverType);
    }

    private WebDriver loadSafariDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities = DesiredCapabilities.safari();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        return new SafariDriver(capabilities);
    }

    private void setDefaultTimeout(WebDriver driver, int timeout, TimeUnit timeUnit) {
        driver.manage().timeouts().implicitlyWait(timeout, timeUnit);
    }

}

