package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;

public class HeaderPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    
    // Page Elements
    @FindBy(css = ".oxd-userdropdown-tab")
    private WebElement userDropdown;
    
    @FindBy(xpath = "//a[text()='Logout']")
    private WebElement logoutLink;
    
    @FindBy(xpath = "//a[text()='About']")
    private WebElement aboutLink;
    
    @FindBy(xpath = "//a[text()='Support']")
    private WebElement supportLink;
    
    @FindBy(xpath = "//a[text()='Change Password']")
    private WebElement changePasswordLink;
    
    @FindBy(css = ".oxd-dropdown-menu")
    private WebElement dropdownMenu;
    
    @FindBy(css = ".oxd-topbar-header-userarea")
    private WebElement userArea;
    
    @FindBy(css = ".oxd-userdropdown-name")
    private WebElement usernameDisplay;
    
    // Constructor
    public HeaderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }
    
    // Page Methods
    public void clickUserDropdown() {
        try {
            // Try multiple selectors for user dropdown
            WebElement dropdown = null;
            String[] selectors = {
                ".oxd-userdropdown-tab",
                ".oxd-userdropdown",
                ".oxd-topbar-header-userarea"
            };
            
            for (String selector : selectors) {
                try {
                    dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (dropdown != null) {
                js.executeScript("arguments[0].click();", dropdown);
                Thread.sleep(1000); // Wait for dropdown to appear
            } else {
                throw new RuntimeException("User dropdown not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to click user dropdown: " + e.getMessage());
        }
    }
    
    public void clickLogout() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
            js.executeScript("arguments[0].click();", logoutLink);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click logout link: " + e.getMessage());
        }
    }
    
    public void logout() {
        try {
            clickUserDropdown();
            clickLogout();
            
            // Wait for logout to complete
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("login"),
                ExpectedConditions.presenceOfElementLocated(By.name("username"))
            ));
        } catch (Exception e) {
            throw new RuntimeException("Logout failed: " + e.getMessage());
        }
    }
    
    public boolean isUserDropdownDisplayed() {
        try {
            return userDropdown.isDisplayed();
        } catch (Exception e) {
            // Try alternative selectors
            try {
                WebElement altDropdown = driver.findElement(By.cssSelector(".oxd-userdropdown"));
                return altDropdown.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public boolean isDropdownMenuDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(dropdownMenu)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isLogoutLinkDisplayed() {
        try {
            return logoutLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getUsernameFromDropdown() {
        try {
            return usernameDisplay.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public void clickAbout() {
        try {
            clickUserDropdown();
            wait.until(ExpectedConditions.elementToBeClickable(aboutLink));
            js.executeScript("arguments[0].click();", aboutLink);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click About link: " + e.getMessage());
        }
    }
    
    public void clickSupport() {
        try {
            clickUserDropdown();
            wait.until(ExpectedConditions.elementToBeClickable(supportLink));
            js.executeScript("arguments[0].click();", supportLink);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Support link: " + e.getMessage());
        }
    }
    
    public void clickChangePassword() {
        try {
            clickUserDropdown();
            wait.until(ExpectedConditions.elementToBeClickable(changePasswordLink));
            js.executeScript("arguments[0].click;", changePasswordLink);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Change Password link: " + e.getMessage());
        }
    }
    
    public boolean isUserAreaDisplayed() {
        try {
            return userArea.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void closeDropdown() {
        try {
            // Click elsewhere to close dropdown
            WebElement body = driver.findElement(By.tagName("body"));
            body.click();
        } catch (Exception e) {
            // Ignore if can't close dropdown
        }
    }
}