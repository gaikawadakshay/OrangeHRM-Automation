package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.AddEmployeePage;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.PIMPage;
import com.orangehrm.pages.PersonalDetailsPage;
import com.orangehrm.utils.EmployeeData;
import com.orangehrm.utils.TestDataGenerator;

public class AddEmployeeTest extends BaseTest {
    
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private PIMPage pimPage;
    private AddEmployeePage addEmployeePage;
    private PersonalDetailsPage personalDetailsPage;
    
    @BeforeMethod
    public void setUp() {
        initializePages();
        loginAsAdmin();
    }
    
    @Test(description = "Navigate to Add Employee page")
    public void testNavigateToAddEmployee() {
        try {
            navigateToAddEmployeePage();
            System.out.println("✓ Successfully navigated to Add Employee page");
        } catch (Exception e) {
            System.out.println("❌ Navigation test failed: " + e.getMessage());
            throw e;
        }
    }
    
    @Test(description = "Add employee with login details successfully")
    public void testAddEmployeeWithLoginDetails() {
        try {
            // Arrange - Create test data
            EmployeeData employeeData = TestDataGenerator.generateEmployeeData();
            System.out.println("Testing with Employee: " + employeeData.getFirstName() + " " + employeeData.getLastName());
            
            // Act - Navigate and create employee
            navigateToAddEmployeePage();
            createEmployeeWithLoginDetails(employeeData);
            
            // Assert - Verify employee creation success with multiple checks
            boolean isCreated = verifyEmployeeCreationSuccess(employeeData);
            Assert.assertTrue(isCreated, "Employee with login details not created successfully");
            
        } catch (Exception e) {
            System.out.println("Test failed with error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    // Helper methods
    private void initializePages() {
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        pimPage = new PIMPage(driver);
        addEmployeePage = new AddEmployeePage(driver);
        personalDetailsPage = new PersonalDetailsPage(driver);
    }
    
    private void loginAsAdmin() {
        try {
            System.out.println("Attempting admin login...");
            loginPage.login("Admin", "admin123");
            
            // Wait longer for dashboard to fully load
            Thread.sleep(5000);
            
            boolean dashboardLoaded = dashboardPage.isDashboardDisplayed();
            System.out.println("Dashboard loaded: " + dashboardLoaded);
            
            Assert.assertTrue(dashboardLoaded, "Admin login failed - dashboard not displayed");
            System.out.println("✓ Admin login successful");
                
        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());
            throw new RuntimeException("Cannot proceed without successful login", e);
        }
    }
    
    private void navigateToAddEmployeePage() {
        try {
            System.out.println("Starting navigation to Add Employee page...");
            
            // Ensure we're on dashboard
            if (!dashboardPage.isDashboardDisplayed()) {
                Thread.sleep(3000);
                if (!dashboardPage.isDashboardDisplayed()) {
                    throw new RuntimeException("Dashboard not displayed before navigation");
                }
            }
            
            // Navigate to PIM menu with retry logic
            boolean pimClicked = false;
            int retryCount = 0;
            int maxRetries = 3;
            
            while (!pimClicked && retryCount < maxRetries) {
                try {
                    System.out.println("Attempt " + (retryCount + 1) + " to click PIM menu");
                    dashboardPage.clickPIMMenu();
                    pimClicked = true;
                    System.out.println("✓ PIM menu clicked successfully");
                } catch (Exception e) {
                    retryCount++;
                    if (retryCount >= maxRetries) {
                        throw e;
                    }
                    System.out.println("PIM click failed, retrying in 2 seconds...");
                    Thread.sleep(2000);
                }
            }
            
            // Wait for PIM page to load
            Thread.sleep(3000);
            
            // Click Add Employee button
            System.out.println("Clicking Add Employee button...");
            pimPage.clickAddEmployeeButton();
            Thread.sleep(3000);
            
            // Verify Add Employee page loaded
            boolean pageLoaded = addEmployeePage.isAddEmployeePageDisplayed();
            System.out.println("Add Employee page loaded: " + pageLoaded);
            
            Assert.assertTrue(pageLoaded, "Add Employee page not loaded properly");
            System.out.println("✓ Successfully navigated to Add Employee page");
                
        } catch (Exception e) {
            System.out.println("❌ Navigation to Add Employee page failed: " + e.getMessage());
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());
            throw e;
        }
    }
    
    private void createEmployeeWithLoginDetails(EmployeeData employeeData) {
        try {
            // Fill basic employee information
            addEmployeePage.enterFirstName(employeeData.getFirstName());
            Thread.sleep(500);
            
            addEmployeePage.enterLastName(employeeData.getLastName());
            Thread.sleep(500);
            
            addEmployeePage.enterEmployeeId(employeeData.getEmployeeId());
            Thread.sleep(500);
            
            // Enable create login details
            addEmployeePage.enableCreateLoginDetails();
            Thread.sleep(2000); // Wait for login fields to appear
            
            // Fill login credentials
            addEmployeePage.enterUsername(employeeData.getUsername());
            Thread.sleep(500);
            
            addEmployeePage.enterPassword(employeeData.getPassword());
            Thread.sleep(500);
            
            addEmployeePage.confirmPassword(employeeData.getConfirmPassword());
            Thread.sleep(500);
            
            // Submit the form
            addEmployeePage.clickSaveButton();
            
            // Wait for form submission
            Thread.sleep(5000);
            
        } catch (Exception e) {
            System.out.println("Error creating employee with login details: " + e.getMessage());
            throw e;
        }
    }
    
    private boolean verifyEmployeeCreationSuccess(EmployeeData employeeData) {
        try {
            System.out.println("Verifying employee creation success...");
            
            // Method 1: Check for success message
            if (addEmployeePage.isSuccessMessageDisplayed()) {
                System.out.println("✓ Success message displayed");
                return true;
            }
            
            // Method 2: Check URL change
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);
            
            if (currentUrl.contains("viewPersonalDetails") || 
                currentUrl.contains("personalDetails") || 
                currentUrl.contains("employee/view")) {
                System.out.println("✓ Redirected to personal details page");
                return true;
            }
            
            // Method 3: Check if personal details page is displayed
            if (personalDetailsPage.isPersonalDetailsPageDisplayed()) {
                System.out.println("✓ Personal details page is displayed");
                return true;
            }
            
            System.out.println("✗ No success indicators found");
            return false;
            
        } catch (Exception e) {
            System.out.println("Error during verification: " + e.getMessage());
            return false;
        }
    }
}
