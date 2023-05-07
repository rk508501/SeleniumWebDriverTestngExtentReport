package com.webium.tests;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.webium.utils.BrowserFactory;
import com.webium.utils.ExtentManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;

/**
 * This is to initialize the webdriver using ThreadLocals
 * Close the browser at the end of the tests
 * Initialize the reporter
 */
public class BaseTest {
    private ExtentReports extent = ExtentManager.getExtentReports();
    private ExtentTest parentTest;
    private ExtentTest test;

    public WebDriver driver;

    @BeforeClass
    public void setup() {
        parentTest = extent.createTest(getClass().getSimpleName());
        ExtentManager.setParentTest(parentTest);
    }

    @BeforeMethod
    public void setupDriver(Method method) {
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.OFF);

        test = parentTest.createNode(method.getName());
        ExtentManager.setTest(test);

        driver = new ChromeDriver();
        BrowserFactory.setDriver(driver);
    }

    @AfterMethod
    public void cleanup(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot();

            ExtentManager.getTest().fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentManager.getTest().skip(result.getThrowable());
        } else {
            captureScreenshot();

            ExtentManager.getTest().pass("Test passed");
        }
        ExtentManager.endTest();
        BrowserFactory.quitDriver();
    }

    public void captureScreenshot() {
        try {
            WebDriver driver = BrowserFactory.getDriver();
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            ExtentManager.getTest().addScreenCaptureFromBase64String(new String(Base64.getEncoder().encode(screenshot)), "Screenshot");
        } catch (Exception e) {
            System.out.println("Error capturing screenshot: " + e.getMessage());
        }
    }

    public void captureFullPageScreenshot() {
        try {
            WebDriver driver = BrowserFactory.getDriver();
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
            ExtentManager.getTest().addScreenCaptureFromBase64String(screenshot.toString());
        } catch (Exception e) {
            System.out.println("Error capturing full-page screenshot: " + e.getMessage());
        }
    }


}



