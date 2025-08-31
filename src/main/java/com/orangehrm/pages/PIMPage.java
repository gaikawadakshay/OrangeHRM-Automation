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
import java.util.List;

public class PIMPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    
    // Page Elements
    @FindBy(xpath = "//h5[text()='Employee Information']")
    private WebElement employeeInformationHeader;
    
    @FindBy(xpath = "//a[contains(text(),'Add Employee')]")
    private WebElement addEmployeeButton;
    
    @FindBy(css = ".oxd-table-body")
    private WebElement employeeTable;
    
    @FindBy(css = ".oxd-table-row")
    private List<WebElement> employeeRows;
    
    @FindBy(css = "input[placeholder='Type for hints...']")
    private WebElement employeeSearchField;
    
    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;
    
    @FindBy(css = ".bi-pencil-fill")
    private List<WebElement> editButtons;
    
    @FindBy(css = ".bi-trash-fill")
    private List<WebElement> deleteButtons;
    
    // Constructor
    public PIMPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }
    
    // Page Methods
    public boolean isEmployeeInformationHeaderDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(employeeInformationHeader)).isDisplayed();
        } catch (Exception e) {
            // Alternative verification
            return driver.getCurrentUrl().contains("pim");
        }
    }
    
    public AddEmployeePage goToAddEmployeePage() {
        try {
            // Wait for add employee button and click
            wait.until(ExpectedConditions.elementToBeClickable(addEmployeeButton));
            js.executeScript("arguments[0].click();", addEmployeeButton);
            
            // Wait for Add Employee page to load
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("addEmployee"),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//h6[contains(text(),'Add Employee')]"))
            ));
            
            return new AddEmployeePage(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to Add Employee page: " + e.getMessage());
        }
    }
    
    public boolean isAddEmployeeButtonDisplayed() {
        try {
            return addEmployeeButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isEmployeeTableDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(employeeTable)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public int getEmployeeCount() {
        try {
            wait.until(ExpectedConditions.visibilityOf(employeeTable));
            return employeeRows.size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    public void searchEmployee(String searchTerm) {
        try {
            wait.until(ExpectedConditions.visibilityOf(employeeSearchField));
            employeeSearchField.clear();
            employeeSearchField.sendKeys(searchTerm);
            
            wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            searchButton.click();
            
            // Wait for search results
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search for employee: " + e.getMessage());
        }
    }
    
    public void clickFirstEmployeeEditButton() {
        try {
            wait.until(ExpectedConditions.visibilityOf(employeeTable));
            if (editButtons.size() > 0) {
                WebElement firstEditButton = editButtons.get(0);
                wait.until(ExpectedConditions.elementToBeClickable(firstEditButton));
                js.executeScript("arguments[0].click();", firstEditButton);
            } else {
                throw new RuntimeException("No edit buttons found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to click edit button: " + e.getMessage());
        }
    }
    
    public boolean areEditButtonsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(employeeTable));
            return editButtons.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean areDeleteButtonsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(employeeTable));
            return deleteButtons.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getPageTitle() {
        try {
            return employeeInformationHeader.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean isSearchFieldDisplayed() {
        try {
            return employeeSearchField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clickAddEmployeeButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addEmployeeButton));
            js.executeScript("arguments[0].click();", addEmployeeButton);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Add Employee button: " + e.getMessage());
        }
    }

	public void clickAddEmployeeButton1() {
		// TODO Auto-generated method stub
		
	}

	public boolean isEmployeeTableDisplayed1() {
		// TODO Auto-generated method stub
		return false;
	}
}