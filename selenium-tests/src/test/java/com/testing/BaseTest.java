package com.testing;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Base Test Class
 * Handles WebDriver setup, teardown, and reusable utilities
 */
public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected String baseUrl = "http://localhost:5000";

    protected String testEmail = "admin@hospital.com";
    protected String testPassword = "admin123";

    @BeforeMethod
    public void setUp() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // FIXED HEADLESS MODE
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        System.out.println("✔ WebDriver started");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        System.out.println("✔ WebDriver closed");
    }

    // ===================== FIXED LOGIN =====================
    protected void login() {

        driver.get(baseUrl);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginEmail")))
                .sendKeys(testEmail);

        driver.findElement(By.id("loginPassword")).sendKeys(testPassword);

        driver.findElement(By.xpath("//button[contains(text(),'Sign In')]")).click();

        // IMPORTANT: wait for dashboard
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mainApp")));
    }

    // ===================== SAFE HELPERS =====================
    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void clickJS(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}