package com.orangehrm.tests;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.HeaderPage;
import com.orangehrm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Verify successful login and logout")
    public void testSuccessfulLoginAndLogout() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.login(config.getProperty("username"), config.getProperty("password"));

        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard header is not visible after login.");
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"), "URL did not navigate to the dashboard.");

        HeaderPage headerPage = new HeaderPage(driver);
        headerPage.logout();

        Assert.assertTrue(driver.getCurrentUrl().contains("/auth/login"), "URL did not return to the login page after logout.");
    }
}
