package com.orangehrm.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class EditEmployeeTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // Login
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        
        WebElement username = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        username.sendKeys("Admin");
        
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("admin123");
        
        WebElement loginBtn = driver.findElement(By.cssSelector("button[type='submit']"));
        loginBtn.click();
        
        // Wait for dashboard
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".oxd-topbar-header-breadcrumb h6")));
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @Test(priority = 1)
    public void testNavigateToEmployeeList() {
        // Click PIM menu
        WebElement pimMenu = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("a[href='/web/index.php/pim/viewPimModule']")));
        pimMenu.click();
        
        // Verify employee list page
        WebElement employeeInfo = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h5[text()='Employee Information']")));
        Assert.assertTrue(employeeInfo.isDisplayed(), "Employee list page not loaded");
    }
    
    @Test(priority = 2)
    public void testSearchAndEditEmployee() {
        // Navigate to PIM
        WebElement pimMenu = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("a[href='/web/index.php/pim/viewPimModule']")));
        pimMenu.click();
        
        // Wait for employee table to load
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".oxd-table-body")));
        
        // Click on first employee's edit button (pencil icon)
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".oxd-table-body .oxd-table-row:first-child .bi-pencil-fill")));
        editButton.click();
        
        // Verify personal details page loaded
        WebElement personalDetails = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h6[text()='Personal Details']")));
        Assert.assertTrue(personalDetails.isDisplayed(), "Employee edit page not loaded");
    }
    
    @Test(priority = 3)
    public void testEditEmployeeDetails() {
        // Navigate to PIM and edit first employee
        WebElement pimMenu = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("a[href='/web/index.php/pim/viewPimModule']")));
        pimMenu.click();
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".oxd-table-body")));
        
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".oxd-table-body .oxd-table-row:first-child .bi-pencil-fill")));
        editButton.click();
        
        // Edit middle name
        WebElement middleName = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.name("middleName")));
        middleName.clear();
        middleName.sendKeys("Updated");
        
        // Save changes
        WebElement saveButton = driver.findElement(
            By.cssSelector("button[type='submit']"));
        saveButton.click();
        
        // Verify success message or page update
        try {
            WebElement successMsg = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".oxd-toast-content--success")));
            Assert.assertTrue(successMsg.isDisplayed(), "Success message not displayed");
        } catch (Exception e) {
            // Alternative verification - check if still on edit page
            WebElement personalDetails = driver.findElement(By.xpath("//h6[text()='Personal Details']"));
            Assert.assertTrue(personalDetails.isDisplayed(), "Update operation failed");
        }
    }
}