package com.orangehrm.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Properties config;
    
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        try {
            // Load configuration first
            loadConfiguration();
            
            // Setup driver based on browser parameter
            setupDriver(browser);
            
            // Configure driver settings
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            
            // Initialize WebDriverWait
            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Navigate to base URL
            String baseUrl = getConfigProperty("baseUrl", "https://opensource-demo.orangehrmlive.com/");
            driver.get(baseUrl);
            
            System.out.println("Browser setup completed: " + browser);
            System.out.println("Navigated to: " + baseUrl);
            
        } catch (Exception e) {
            System.err.println("Error in setUp: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Setup failed", e);
        }
    }
    
    @AfterMethod
    public void tearDown() {
        try {
            if (driver != null) {
                System.out.println("Closing browser...");
                driver.quit();
                driver = null;
            }
        } catch (Exception e) {
            System.err.println("Error in tearDown: " + e.getMessage());
        }
    }
    
    protected void loadConfiguration() {
        config = new Properties();
        try {
            // Try to load from resources first
            String[] configPaths = {
                "src/test/resources/config.properties",
                "config.properties",
                "src/main/resources/config.properties"
            };
            
            boolean configLoaded = false;
            for (String path : configPaths) {
                try {
                    FileInputStream configFile = new FileInputStream(path);
                    config.load(configFile);
                    configFile.close();
                    System.out.println("Configuration loaded from: " + path);
                    configLoaded = true;
                    break;
                } catch (IOException e) {
                    System.out.println("Config not found at: " + path);
                    continue;
                }
            }
            
            if (!configLoaded) {
                setDefaultConfiguration();
            }
            
        } catch (Exception e) {
            System.out.println("Error loading configuration, using default values: " + e.getMessage());
            setDefaultConfiguration();
        }
    }
    
    private void setDefaultConfiguration() {
        System.out.println("Using default configuration values");
        config.setProperty("baseUrl", "https://opensource-demo.orangehrmlive.com/");
        config.setProperty("username", "Admin");
        config.setProperty("password", "admin123");
        config.setProperty("browser", "chrome");
        config.setProperty("timeout", "30");
        config.setProperty("implicit.wait", "10");
        config.setProperty("explicit.wait", "20");
    }
    
    protected String getConfigProperty(String key, String defaultValue) {
        return config != null ? config.getProperty(key, defaultValue) : defaultValue;
    }
    
    private void setupDriver(String browser) {
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    System.out.println("Chrome driver initialized");
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    System.out.println("Firefox driver initialized");
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    System.out.println("Edge driver initialized");
                    break;
                default:
                    System.err.println("Browser not supported: " + browser + ". Using Chrome as default.");
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error setting up driver for browser: " + browser);
            e.printStackTrace();
            throw new RuntimeException("Driver setup failed", e);
        }
    }
    
    // Utility method to wait for page load
    protected void waitForPageLoad() {
        try {
            wait.until(driver -> 
                ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
            Thread.sleep(1000); // Additional buffer time
        } catch (Exception e) {
            System.out.println("Page load wait completed with exception: " + e.getMessage());
        }
    }
}