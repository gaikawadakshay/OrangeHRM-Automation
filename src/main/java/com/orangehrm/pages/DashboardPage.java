package com.orangehrm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import com.orangehrm.base.BasePage;

public class DashboardPage extends BasePage {
    
    // Multiple locator strategies for PIM menu
    @FindBy(xpath = "//span[text()='PIM']")
    private WebElement pimMenuSpan;
    
    @FindBy(xpath = "//a[contains(@href, 'pim')]")
    private WebElement pimMenuLink;
    
    @FindBy(xpath = "//li[contains(@class, 'oxd-main-menu-item')]//span[text()='PIM']")
    private WebElement pimMenuItem;
    
    @FindBy(linkText = "PIM")
    private WebElement pimMenuLinkText;
    
    // Alternative locators
    @FindBy(xpath = "//*[contains(text(), 'PIM')]")
    private WebElement anyPIMText;
    
    @FindBy(xpath = "//nav[@aria-label='Sidebar']//span[text()='PIM']")
    private WebElement sidebarPIM;
    
    // Dashboard verification elements
    @FindBy(xpath = "//h6[text()='Dashboard']")
    private WebElement dashboardTitle;
    
    @FindBy(xpath = "//div[contains(@class, 'oxd-layout-navigation')]")
    private WebElement navigationMenu;
    
    @FindBy(xpath = "//img[@alt='profile picture']")
    private WebElement profilePicture;
    
    @FindBy(xpath = "//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']")
    private WebElement userDropdown;
    
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    
    public boolean isDashboardDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOf(dashboardTitle),
                ExpectedConditions.visibilityOf(navigationMenu),
                ExpectedConditions.visibilityOf(profilePicture)
            ));
            return true;
        } catch (Exception e) {
            System.out.println("Dashboard not displayed: " + e.getMessage());
            return false;
        }
    }
    
    public void clickPIMMenu() {
        try {
            System.out.println("Attempting to click PIM menu...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Wait for navigation menu to be fully loaded
            wait.until(ExpectedConditions.visibilityOf(navigationMenu));
            System.out.println("Navigation menu is visible");
            
            // Try multiple locator strategies
            WebElement pimElement = null;
            
            // Strategy 1: Try span with text PIM
            try {
                pimElement = wait.until(ExpectedConditions.elementToBeClickable(pimMenuSpan));
                System.out.println("Found PIM using span locator");
            } catch (Exception e1) {
                System.out.println("Span locator failed, trying link locator");
                
                // Strategy 2: Try link with href containing pim
                try {
                    pimElement = wait.until(ExpectedConditions.elementToBeClickable(pimMenuLink));
                    System.out.println("Found PIM using link locator");
                } catch (Exception e2) {
                    System.out.println("Link locator failed, trying menu item locator");
                    
                    // Strategy 3: Try menu item locator
                    try {
                        pimElement = wait.until(ExpectedConditions.elementToBeClickable(pimMenuItem));
                        System.out.println("Found PIM using menu item locator");
                    } catch (Exception e3) {
                        System.out.println("Menu item locator failed, trying linkText");
                        
                        // Strategy 4: Try linkText
                        try {
                            pimElement = wait.until(ExpectedConditions.elementToBeClickable(pimMenuLinkText));
                            System.out.println("Found PIM using linkText locator");
                        } catch (Exception e4) {
                            System.out.println("LinkText failed, trying any PIM text");
                            
                            // Strategy 5: Try any element with PIM text
                            pimElement = wait.until(ExpectedConditions.elementToBeClickable(anyPIMText));
                            System.out.println("Found PIM using any text locator");
                        }
                    }
                }
            }
            
            if (pimElement != null) {
                // Scroll to element
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", pimElement);
                Thread.sleep(1000);
                
                // Try regular click first
                try {
                    pimElement.click();
                    System.out.println("✓ PIM menu clicked successfully");
                } catch (Exception e) {
                    // If regular click fails, try JavaScript click
                    System.out.println("Regular click failed, trying JavaScript click");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pimElement);
                    System.out.println("✓ PIM menu clicked using JavaScript");
                }
            } else {
                throw new RuntimeException("Could not locate PIM menu element");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Failed to click PIM menu: " + e.getMessage());
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());
            
            // Debug: Print all available menu items
            printAvailableMenuItems();
            
            throw new RuntimeException("Failed to click PIM menu", e);
        }
    }
    
    private void printAvailableMenuItems() {
        try {
            System.out.println("=== Available Menu Items ===");
            var menuItems = driver.findElements(org.openqa.selenium.By.xpath("//nav//span | //nav//a"));
            for (var item : menuItems) {
                if (item.isDisplayed()) {
                    System.out.println("Menu item: " + item.getText());
                }
            }
            System.out.println("===========================");
        } catch (Exception e) {
            System.out.println("Could not print menu items: " + e.getMessage());
        }
    }
    
    public boolean isEmployeeDashboardDisplayed() {
        return isDashboardDisplayed(); // Same logic for now
    }
    
    public void logout() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Click user dropdown
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(userDropdown));
            dropdown.click();
            
            // Click logout
            WebElement logoutOption = wait.until(ExpectedConditions.elementToBeClickable(
                org.openqa.selenium.By.xpath("//a[text()='Logout']")));
            logoutOption.click();
            
            System.out.println("✓ Logout successful");
            
        } catch (Exception e) {
            System.out.println("❌ Logout failed: " + e.getMessage());
            throw new RuntimeException("Logout failed", e);
        }
    }
}
