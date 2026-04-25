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
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        driver.get("http://103.139.122.250:4000/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for elements before interacting
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")))
                .sendKeys("qasim@malik.com");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")))
                .sendKeys("wrongpassword");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("m_login_signin_submit")))
                .click();

        // Wait for error message
        WebElement errorElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//form//div[contains(@class,'alert') or contains(text(),'Incorrect')]")
                )
        );

        String errorText = errorElement.getText();

        assertTrue(errorText.contains("Incorrect email or password"));

        driver.quit();
    }
}
