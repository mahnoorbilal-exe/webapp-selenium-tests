package com.testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

/**
 * Hospital Management System - Automated Test Suite
 * 15 Selenium Test Cases using TestNG Framework
 * Tests cover: Authentication, Navigation, UI Elements, and Core Functionality
 * 
 * @author MediCore Team
 * @version 1.0
 */
public class HospitalManagementTest extends BaseTest {

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 1: Home Page Load Verification
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 1, 
          description = "TC01: Verify home page loads with correct title",
          groups = {"smoke", "regression"})
    public void test01_HomePageLoads() {
        System.out.println("\n[TEST 1] Testing: Home Page Load");
        
        // Step 1: Navigate to base URL
        driver.get(baseUrl);
        System.out.println("→ Navigated to: " + baseUrl);
        
        // Step 2: Get page title
        String title = driver.getTitle();
        System.out.println("→ Page title: " + title);
        
        // Step 3: Verify title contains "MediCore"
        Assert.assertTrue(title.contains("MediCore"), 
            "Page title should contain 'MediCore'");
        
        System.out.println("✓ TEST 1 PASSED: Home page loaded successfully");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 2: Login Page Elements Present
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 2, 
          description = "TC02: Verify all login page elements are present",
          groups = {"smoke", "ui"})
    public void test02_LoginPageElements() {
        System.out.println("\n[TEST 2] Testing: Login Page Elements");
        
        // Step 1: Navigate to login page
        driver.get(baseUrl);
        
        // Step 2: Check email input field
        WebElement emailInput = driver.findElement(By.id("loginEmail"));
        Assert.assertTrue(emailInput.isDisplayed(), 
            "Email input field should be visible");
        System.out.println("→ Email input field found");
        
        // Step 3: Check password input field
        WebElement passwordInput = driver.findElement(By.id("loginPassword"));
        Assert.assertTrue(passwordInput.isDisplayed(), 
            "Password input field should be visible");
        System.out.println("→ Password input field found");
        
        // Step 4: Check login button
        WebElement loginButton = driver.findElement(
            By.xpath("//button[contains(text(), 'Sign In')]"));
        Assert.assertTrue(loginButton.isDisplayed(), 
            "Login button should be visible");
        System.out.println("→ Login button found");
        
        System.out.println("✓ TEST 2 PASSED: All login elements present");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 3: Successful Login with Valid Credentials
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 3, 
          description = "TC03: Verify user can login with valid credentials",
          groups = {"smoke", "regression", "authentication"})
    public void test03_SuccessfulLogin() {
        System.out.println("\n[TEST 3] Testing: Successful Login");
        
        // Step 1: Navigate to login page
        driver.get(baseUrl);
        
        // Step 2: Enter email
        driver.findElement(By.id("loginEmail")).sendKeys(testEmail);
        System.out.println("→ Entered email: " + testEmail);
        
        // Step 3: Enter password
        driver.findElement(By.id("loginPassword")).sendKeys(testPassword);
        System.out.println("→ Entered password");
        
        // Step 4: Click login button
        driver.findElement(By.xpath("//button[contains(text(), 'Sign In')]")).click();
        System.out.println("→ Clicked login button");
        
        // Step 5: Wait for dashboard to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mainApp")));
        
        // Step 6: Verify dashboard is visible
        WebElement mainApp = driver.findElement(By.id("mainApp"));
        Assert.assertTrue(mainApp.isDisplayed(), 
            "Dashboard should be visible after login");
        
        System.out.println("✓ TEST 3 PASSED: Login successful, dashboard loaded");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 4: Invalid Login Shows Error
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 4, 
          description = "TC04: Verify error message appears with invalid credentials",
          groups = {"regression", "authentication", "negative"})
    public void test04_InvalidLogin() {
        System.out.println("\n[TEST 4] Testing: Invalid Login");
        
        // Step 1: Navigate to login page
        driver.get(baseUrl);
        
        // Step 2: Enter invalid email
        String wrongEmail = "wrong@email.com";
        driver.findElement(By.id("loginEmail")).sendKeys(wrongEmail);
        System.out.println("→ Entered invalid email: " + wrongEmail);
        
        // Step 3: Enter invalid password
        String wrongPassword = "wrongpassword";
        driver.findElement(By.id("loginPassword")).sendKeys(wrongPassword);
        System.out.println("→ Entered invalid password");
        
        // Step 4: Click login button
        driver.findElement(By.xpath("//button[contains(text(), 'Sign In')]")).click();
        System.out.println("→ Clicked login button");
        
        // Step 5: Wait for error message
        waitFor(1000);
        
        // Step 6: Verify error message is displayed
        WebElement errorDiv = driver.findElement(By.id("loginError"));
        String className = errorDiv.getAttribute("class");
        Assert.assertFalse(className.contains("hidden"), 
            "Error message should be visible for invalid credentials");
        
        System.out.println("✓ TEST 4 PASSED: Error message displayed for invalid login");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 5: Dashboard Loads with Statistics
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 5, 
          description = "TC05: Verify dashboard displays statistics cards",
          groups = {"smoke", "regression", "dashboard"})
    public void test05_DashboardLoads() {
        System.out.println("\n[TEST 5] Testing: Dashboard Statistics");
        
        // Step 1: Login
        login();
        System.out.println("→ Logged in successfully");
        
        // Step 2: Verify stats grid exists
        WebElement statsGrid = driver.findElement(By.className("stats-grid"));
        Assert.assertTrue(statsGrid.isDisplayed(), 
            "Statistics grid should be visible");
        System.out.println("→ Stats grid found");
        
        // Step 3: Count stat cards
        List<WebElement> statCards = driver.findElements(By.className("stat-card"));
        int cardCount = statCards.size();
        System.out.println("→ Found " + cardCount + " stat cards");
        
        // Step 4: Verify at least 4 cards exist
        Assert.assertTrue(cardCount >= 4, 
            "Should have at least 4 statistics cards (Patients, Doctors, Appointments, Bills)");
        
        System.out.println("✓ TEST 5 PASSED: Dashboard loaded with " + cardCount + " statistics");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 6: Navigate to Patients Page
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 6, 
          description = "TC06: Verify navigation to patients page works",
          groups = {"regression", "navigation"})
    public void test06_NavigateToPatients() {
        System.out.println("\n[TEST 6] Testing: Navigate to Patients");
        
        // Step 1: Login
        login();
        
        // Step 2: Click patients navigation link
        driver.findElement(By.xpath("//a[contains(text(), 'Patients')]")).click();
        System.out.println("→ Clicked Patients menu item");
        
        // Step 3: Wait for patients view
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view-patients")));
        
        // Step 4: Verify patients view is active
        WebElement patientsView = driver.findElement(By.id("view-patients"));
        String className = patientsView.getAttribute("class");
        Assert.assertTrue(className.contains("active"), 
            "Patients view should be marked as active");
        
        System.out.println("✓ TEST 6 PASSED: Successfully navigated to Patients page");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 7: Patients Table Loads
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 7, 
          description = "TC07: Verify patients table displays with proper structure",
          groups = {"regression", "patients"})
    public void test07_PatientsTableLoads() {
        System.out.println("\n[TEST 7] Testing: Patients Table");
        
        // Step 1: Login and navigate
        login();
        driver.findElement(By.xpath("//a[contains(text(), 'Patients')]")).click();
        waitFor(1000);
        
        // Step 2: Find patients table
        WebElement table = driver.findElement(By.cssSelector("#view-patients table"));
        Assert.assertTrue(table.isDisplayed(), 
            "Patients table should be visible");
        System.out.println("→ Patients table found");
        
        // Step 3: Check table headers
        List<WebElement> headers = driver.findElements(
            By.cssSelector("#view-patients table th"));
        int headerCount = headers.size();
        System.out.println("→ Found " + headerCount + " table columns");
        
        // Step 4: Verify minimum columns
        Assert.assertTrue(headerCount >= 5, 
            "Table should have at least 5 columns (ID, Name, Age, Gender, Phone, etc.)");
        
        System.out.println("✓ TEST 7 PASSED: Patients table loaded with " + headerCount + " columns");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 8: Patient Search Box Works
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 8, 
          description = "TC08: Verify patient search functionality",
          groups = {"regression", "patients"})
    public void test08_PatientSearch() {
        System.out.println("\n[TEST 8] Testing: Patient Search");
        
        // Step 1: Login and navigate
        login();
        driver.findElement(By.xpath("//a[contains(text(), 'Patients')]")).click();
        waitFor(1000);
        
        // Step 2: Find search input
        WebElement searchInput = driver.findElement(By.id("patientSearch"));
        Assert.assertTrue(searchInput.isDisplayed(), 
            "Search input should be visible");
        System.out.println("→ Search input found");
        
        // Step 3: Enter search text
        String searchTerm = "test";
        searchInput.sendKeys(searchTerm);
        System.out.println("→ Entered search term: " + searchTerm);
        waitFor(500);
        
        // Step 4: Verify text was entered
        String enteredValue = searchInput.getAttribute("value");
        Assert.assertEquals(enteredValue, searchTerm, 
            "Search input should contain the entered text");
        
        System.out.println("✓ TEST 8 PASSED: Patient search box works correctly");
    }
    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 9: Add Patient Button Opens Modal
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 9, 
          description = "TC09: Verify add patient button opens modal dialog",
          groups = {"regression", "patients", "ui"})
    public void test09_AddPatientModal() {
        System.out.println("\n[TEST 9] Testing: Add Patient Modal");
        
        // Step 1: Login and navigate
        login();
        driver.findElement(By.xpath("//a[contains(text(), 'Patients')]")).click();
        waitFor(1000);
        
        // Step 2: Click add patient button
        driver.findElement(By.xpath("//button[contains(text(), 'Add Patient')]")).click();
        System.out.println("→ Clicked Add Patient button");
        waitFor(500);
        
        // Step 3: Verify modal is visible
        WebElement modal = driver.findElement(By.id("patientModal"));
        String className = modal.getAttribute("class");
        Assert.assertFalse(className.contains("hidden"), 
            "Patient modal should be visible after clicking Add Patient");
        
        System.out.println("✓ TEST 9 PASSED: Add patient modal opened successfully");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 10: Navigate to Doctors Page
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 10, 
          description = "TC10: Verify navigation to doctors page",
          groups = {"regression", "navigation"})
    public void test10_NavigateToDoctors() {
        System.out.println("\n[TEST 10] Testing: Navigate to Doctors");
        
        // Step 1: Login
        login();
        
        // Step 2: Click doctors navigation
        driver.findElement(By.xpath("//a[contains(text(), 'Doctors')]")).click();
        System.out.println("→ Clicked Doctors menu item");
        waitFor(1000);
        
        // Step 3: Verify doctors view is active
        WebElement doctorsView = driver.findElement(By.id("view-doctors"));
        String className = doctorsView.getAttribute("class");
        Assert.assertTrue(className.contains("active"), 
            "Doctors view should be marked as active");
        
        System.out.println("✓ TEST 10 PASSED: Successfully navigated to Doctors page");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 11: Navigate to Appointments Page
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 11, 
          description = "TC11: Verify navigation to appointments page",
          groups = {"regression", "navigation"})
    public void test11_NavigateToAppointments() {
        System.out.println("\n[TEST 11] Testing: Navigate to Appointments");
        
        // Step 1: Login
        login();
        
        // Step 2: Click appointments navigation
        driver.findElement(By.xpath("//a[contains(text(), 'Appointments')]")).click();
        System.out.println("→ Clicked Appointments menu item");
        waitFor(1000);
        
        // Step 3: Verify appointments view is active
        WebElement appointmentsView = driver.findElement(By.id("view-appointments"));
        String className = appointmentsView.getAttribute("class");
        Assert.assertTrue(className.contains("active"), 
            "Appointments view should be marked as active");
        
        System.out.println("✓ TEST 11 PASSED: Successfully navigated to Appointments page");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 12: Navigate to Wards Page with Grid
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 12, 
          description = "TC12: Verify navigation to wards page and grid display",
          groups = {"regression", "navigation", "wards"})
    public void test12_NavigateToWards() {
        System.out.println("\n[TEST 12] Testing: Navigate to Wards");
        
        // Step 1: Login
        login();
        
        // Step 2: Click wards navigation
        driver.findElement(By.xpath("//a[contains(text(), 'Wards')]")).click();
        System.out.println("→ Clicked Wards menu item");
        waitFor(1000);
        
        // Step 3: Verify wards view is active
        WebElement wardsView = driver.findElement(By.id("view-wards"));
        String className = wardsView.getAttribute("class");
        Assert.assertTrue(className.contains("active"), 
            "Wards view should be marked as active");
        
        // Step 4: Verify wards grid is displayed
        WebElement wardsGrid = driver.findElement(By.id("wardsGrid"));
        Assert.assertTrue(wardsGrid.isDisplayed(), 
            "Wards grid should be visible");
        System.out.println("→ Wards grid displayed");
        
        System.out.println("✓ TEST 12 PASSED: Successfully navigated to Wards page with grid");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 13: Navigate to Billing Page
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 13, 
          description = "TC13: Verify navigation to billing page",
          groups = {"regression", "navigation"})
    public void test13_NavigateToBilling() {
        System.out.println("\n[TEST 13] Testing: Navigate to Billing");
        
        // Step 1: Login
        login();
        
        // Step 2: Click billing navigation
        driver.findElement(By.xpath("//a[contains(text(), 'Billing')]")).click();
        System.out.println("→ Clicked Billing menu item");
        waitFor(1000);
        
        // Step 3: Verify billing view is active
        WebElement billingView = driver.findElement(By.id("view-billing"));
        String className = billingView.getAttribute("class");
        Assert.assertTrue(className.contains("active"), 
            "Billing view should be marked as active");
        
        System.out.println("✓ TEST 13 PASSED: Successfully navigated to Billing page");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 14: Logout Functionality
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 14, 
          description = "TC14: Verify logout returns user to login page",
          groups = {"smoke", "regression", "authentication"})
    public void test14_Logout() {
        System.out.println("\n[TEST 14] Testing: Logout Functionality");
        
        // Step 1: Login
        login();
        System.out.println("→ User logged in");
        
        // Step 2: Click logout button
        WebElement logoutButton = waitClickable(By.xpath("//button[contains(text(), 'Sign Out')]"));
        clickJS(logoutButton);
        System.out.println("→ Clicked Sign Out button");
        waitFor(1000);
        
        // Step 3: Verify back on login page
        WebElement loginPage = driver.findElement(By.id("loginPage"));
        String className = loginPage.getAttribute("class");
        Assert.assertTrue(className.contains("active"), 
            "Should be redirected to login page after logout");
        
        System.out.println("✓ TEST 14 PASSED: Logout successful, returned to login page");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST CASE 15: Registration Page Form Elements
    // ═══════════════════════════════════════════════════════════════
    @Test(priority = 15, 
          description = "TC15: Verify registration page displays all form fields",
          groups = {"regression", "authentication", "ui"})
    public void test15_RegisterPageElements() {
        System.out.println("\n[TEST 15] Testing: Registration Page Elements");
        
        // Step 1: Navigate to login page c
        driver.get(baseUrl);
        
        // Step 2: Click create account link
        driver.findElement(By.xpath("//a[contains(text(), 'Create account')]")).click();
        System.out.println("→ Clicked Create account link");
        waitFor(500);
        
        // Step 3: Verify register page is active
        WebElement registerPage = driver.findElement(By.id("registerPage"));
        String className = registerPage.getAttribute("class");
        Assert.assertTrue(className.contains("active"), 
            "Register page should be active");
        System.out.println("→ Registration page displayed");
        
        // Step 4: Verify all form fields exist
        WebElement nameField = driver.findElement(By.id("regName"));
        Assert.assertTrue(nameField.isDisplayed(), "Name field should be visible");
        System.out.println("→ Name field found");
        
        WebElement emailField = driver.findElement(By.id("regEmail"));
        Assert.assertTrue(emailField.isDisplayed(), "Email field should be visible");
        System.out.println("→ Email field found");
        
        WebElement passwordField = driver.findElement(By.id("regPassword"));
        Assert.assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        System.out.println("→ Password field found");
        
        WebElement phoneField = driver.findElement(By.id("regPhone"));
        Assert.assertTrue(phoneField.isDisplayed(), "Phone field should be visible");
        System.out.println("→ Phone field found");
        
        WebElement roleField = driver.findElement(By.id("regRole"));
        Assert.assertTrue(roleField.isDisplayed(), "Role dropdown should be visible");
        System.out.println("→ Role dropdown found");
        
        System.out.println("✓ TEST 15 PASSED: All registration form fields present");
    }
}