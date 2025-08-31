package com.orangehrm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.List;
import com.orangehrm.base.BasePage;

public class AddEmployeePage extends BasePage {
    
    // Multiple success message locators
    @FindBy(xpath = "//div[contains(@class, 'oxd-toast-content--success')]")
    private WebElement successToast;
    
    @FindBy(xpath = "//div[contains(@class, 'toast') and contains(@class, 'success')]")
    private WebElement successToastAlt;
    
    @FindBy(xpath = "//*[contains(text(), 'Successfully Saved') or contains(text(), 'Success')]")
    private WebElement anySuccessMessage;
    
    @FindBy(xpath = "//div[@class='message success fadable']")
    private WebElement messageSuccess;
    
    @FindBy(xpath = "//p[contains(@class, 'oxd-text--toast-message')]")
    private WebElement toastMessage;
    
    // Add Employee page identifier
    @FindBy(xpath = "//h6[text()='Add Employee']")
    private WebElement addEmployeeHeader;
    
    @FindBy(xpath = "//div[contains(@class, 'oxd-form-row')]//span[text()='Create Login Details']")
    private WebElement createLoginToggleLabel;
    
    // Form elements
    @FindBy(name = "firstName")
    private WebElement firstNameField;
    
    @FindBy(name = "lastName")
    private WebElement lastNameField;
    
    @FindBy(xpath = "//input[@class='oxd-input oxd-input--active' and contains(@class, 'orangehrm-lastname')]")
    private WebElement employeeIdField;
    
    @FindBy(xpath = "//span[@class='oxd-switch-input oxd-switch-input--active --label-right']")
    private WebElement createLoginDetailsToggle;
    
    @FindBy(xpath = "//input[@autocomplete='off']")
    private WebElement usernameField;
    
    @FindBy(xpath = "//input[@type='password'][1]")
    private WebElement passwordField;
    
    @FindBy(xpath = "//input[@type='password'][2]")
    private WebElement confirmPasswordField;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;
    
    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }
    
    public boolean isAddEmployeePageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOf(addEmployeeHeader),
                ExpectedConditions.visibilityOf(createLoginToggleLabel)
            ));
            return true;
        } catch (Exception e) {
            System.out.println("Add Employee page not displayed: " + e.getMessage());
            return false;
        }
    }
    
    public boolean isSuccessMessageDisplayed() {
        try {
            System.out.println("🔍 Checking for success messages...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Method 1: Check for success toast
            try {
                wait.until(ExpectedConditions.visibilityOf(successToast));
                System.out.println("✅ Found success toast");
                return true;
            } catch (Exception e1) {
                System.out.println("❌ Success toast not found");
            }
            
            // Method 2: Check alternative success toast
            try {
                wait.until(ExpectedConditions.visibilityOf(successToastAlt));
                System.out.println("✅ Found alternative success toast");
                return true;
            } catch (Exception e2) {
                System.out.println("❌ Alternative success toast not found");
            }
            
            // Method 3: Check for any success message text
            try {
                wait.until(ExpectedConditions.visibilityOf(anySuccessMessage));
                System.out.println("✅ Found general success message: " + anySuccessMessage.getText());
                return true;
            } catch (Exception e3) {
                System.out.println("❌ General success message not found");
            }
            
            // Method 4: Check message success class
            try {
                wait.until(ExpectedConditions.visibilityOf(messageSuccess));
                System.out.println("✅ Found message success element");
                return true;
            } catch (Exception e4) {
                System.out.println("❌ Message success element not found");
            }
            
            // Method 5: Check toast message content
            try {
                wait.until(ExpectedConditions.visibilityOf(toastMessage));
                String messageText = toastMessage.getText().toLowerCase();
                boolean isSuccess = messageText.contains("success") || 
                                  messageText.contains("saved") || 
                                  messageText.contains("created");
                if (isSuccess) {
                    System.out.println("✅ Found success in toast message: " + messageText);
                    return true;
                } else {
                    System.out.println("❌ Toast message doesn't indicate success: " + messageText);
                }
            } catch (Exception e5) {
                System.out.println("❌ Toast message not found");
            }
            
            // Method 6: JavaScript search for success indicators
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                Boolean hasSuccess = (Boolean) js.executeScript(
                    "var elements = document.querySelectorAll('*');" +
                    "for (var i = 0; i < elements.length; i++) {" +
                    "  var text = elements[i].textContent || elements[i].innerText || '';" +
                    "  var className = elements[i].className || '';" +
                    "  if ((text.toLowerCase().includes('success') || " +
                    "       text.toLowerCase().includes('saved') || " +
                    "       text.toLowerCase().includes('created') || " +
                    "       className.toLowerCase().includes('success')) && " +
                    "      elements[i].offsetWidth > 0 && elements[i].offsetHeight > 0) {" +
                    "    console.log('Found success indicator:', text, className);" +
                    "    return true;" +
                    "  }" +
                    "}" +
                    "return false;"
                );
                
                if (hasSuccess) {
                    System.out.println("✅ JavaScript found success indicator");
                    return true;
                } else {
                    System.out.println("❌ JavaScript didn't find success indicators");
                }
            } catch (Exception e6) {
                System.out.println("❌ JavaScript search failed: " + e6.getMessage());
            }
            
            System.out.println("❌ No success message found using any method");
            return false;
            
        } catch (Exception e) {
            System.out.println("❌ Error checking success message: " + e.getMessage());
            return false;
        }
    }
    
    // Other methods remain the same...
    public void enterFirstName(String firstName) {
        waitAndSendKeys(firstNameField, firstName);
    }
    
    public void enterLastName(String lastName) {
        waitAndSendKeys(lastNameField, lastName);
    }
    
    public void enterEmployeeId(String employeeId) {
        waitAndClear(employeeIdField);
        waitAndSendKeys(employeeIdField, employeeId);
    }
    
    public void enableCreateLoginDetails() {
        if (!createLoginDetailsToggle.isSelected()) {
            waitAndClick(createLoginDetailsToggle);
        }
    }
    
    public void enterUsername(String username) {
        waitAndSendKeys(usernameField, username);
    }
    
    public void enterPassword(String password) {
        waitAndSendKeys(passwordField, password);
    }
    
    public void confirmPassword(String confirmPassword) {
        waitAndSendKeys(confirmPasswordField, confirmPassword);
    }
    
    public void clickSaveButton() {
        waitAndClick(saveButton);
    }
}
