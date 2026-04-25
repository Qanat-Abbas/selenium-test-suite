package com.devops.lab;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    @Test
    void test_login_with_incorrect_credentials() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        try {
            // NEW URL (correct one)
            driver.navigate().to("http://103.139.122.250:4000/login");

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            // Stable selectors (matching your working version style)
            driver.findElement(By.id("email")).sendKeys("qasim@malik.com");
            driver.findElement(By.id("password")).sendKeys("abcdefg");

            driver.findElement(By.id("m_login_signin_submit")).click();

            Thread.sleep(2000); // small buffer for UI update (keeps stability in CI)

            // SAME LOGIC AS YOUR ORIGINAL TEST (MOST IMPORTANT FIX)
            String errorText = driver.findElement(
                    By.xpath("/html/body/div/div/div[1]/div/div/div/div[2]/form/div[1]")
            ).getText();

            assertTrue(errorText.contains("Incorrect email or password"));

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            driver.quit();
        }
    }
}
