package com.devops.lab;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    @Test
    void test_login_with_incorrect_credentials() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            driver.get("http://103.139.122.250:4000/login");

            // Email
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")))
                    .sendKeys("qasim@malik.com");

            // Password
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")))
                    .sendKeys("wrongpassword");

            // Login button (kept flexible for UI variations)
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Login') or contains(.,'Sign')]")
            )).click();

            // Error message
            WebElement errorElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Incorrect') or contains(text(),'invalid')]")
                    )
            );

            String errorText = errorElement.getText().toLowerCase();

            assertTrue(
                    errorText.contains("incorrect") || errorText.contains("invalid")
            );

        } finally {
            driver.quit();
        }
    }
}
