package com.webium.tests;

import org.jsoup.Connection;
import org.testng.annotations.Test;

public class TestHome extends BaseTest {

    @Test
    public void testHome() {
        System.out.println("Test Home");

        // Get the current thread object
        Thread currentThread = Thread.currentThread();

        // Print the name and ID of the current thread
        System.out.println("Current thread name: " + currentThread.getName());
        System.out.println("Current thread ID: " + currentThread.getId());


        driver.get("https://see.stanford.edu/Course/CS106A");
        System.out.println(driver.getTitle());
        //captureScreenshot();
    }

}
