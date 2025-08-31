package com.orangehrm.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    public static String takeScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path destinationPath = Paths.get("screenshots", testName + "_" + timestamp + ".png");

        try {
            Files.createDirectories(destinationPath.getParent());
            Files.copy(screenshotFile.toPath(), destinationPath);
            return destinationPath.toString();
        } catch (Exception e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }
}
