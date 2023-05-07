package com.webium.tests;

import org.testng.annotations.Test;

public class TestLanding extends BaseTest{

    @Test
    public void testLanding(){
        System.out.println("Test landing");

        // Get the current thread object
        Thread currentThread = Thread.currentThread();

        // Print the name and ID of the current thread
        System.out.println("Current thread name: " + currentThread.getName());
        System.out.println("Current thread ID: " + currentThread.getId());

        driver.get("https://www.lib.berkeley.edu/");
        //captureScreenshot();
    }
}
