package com.webium.utils;

import org.openqa.selenium.WebDriver;

public class BrowserFactory
{
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return webDriver.get();
    }

    public static void setDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    public static void quitDriver() {
        if (webDriver.get() != null) {
            webDriver.get().quit();
        }
        webDriver.remove();
    }
}
