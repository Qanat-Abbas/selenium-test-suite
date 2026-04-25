package com.devops.lab;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    @Test
    void test_login_with_incorrect_credentials() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            driver.get("http://103.139.122.250/");

            driver.findElement(By.name("email"))
                    .sendKeys("qasimsb@malik.com");

            driver.findElement(By.name("password"))
                    .sendKeys("abcdefg");

            driver.findElement(By.id("m_login_signin_submit"))
                    .click();

            // IMPORTANT FIX: more stable locator strategy
            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(.,'Incorrect') or contains(.,'invalid')]")
                    )
            );

            String errorText = error.getText();

            assertTrue(
                    errorText.contains("Incorrect") || errorText.contains("invalid"),
                    "Expected login error message not found!"
            );

        } finally {
            driver.quit();
        }
    }
}
