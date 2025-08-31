# OrangeHRM Automation Testing Project

📌 Overview

This project is a Selenium + TestNG Automation Framework built for the OrangeHRM demo application
It demonstrates end-to-end test automation for modules like:

🔑 Login & Logout

👤 Add Employee

📝 Edit Employee Details

🔍 Search Employee

❌ Delete Employee


⚙️ Tech Stack

  Programming Language: Java (JDK 11 or later)

  Automation Tool: Selenium WebDriver

  Testing Framework: TestNG

  Build Tool: Maven

  Reporting: Extent Reports

  Design Pattern: Page Object Model (POM)

📂 Project Structure

  OrangeHRM-Automation/
  │── src/main/java/com/orangehrm/pages/        # Page classes (POM)
  │── src/main/java/com/orangehrm/base/         # Base classes (driver setup)
  │── src/main/java/com/orangehrm/utils/        # Utility classes
  │── src/test/java/com/orangehrm/tests/        # Test cases
  │── testng.xml                                # TestNG Suite
  │── pom.xml                                   # Maven dependencies
  │── README.md                                 # Project documentation
  │── reports/                                  # Extent Reports
  │── screenshots/                              # Screenshots on failure


🚀 How to Run Tests
1️⃣ Clone the repository
    git clone https://github.com/your-username/OrangeHRM-Automation.git
    cd OrangeHRM-Automation

2️⃣ Install dependencies
    mvn clean install

3️⃣ Run tests using Maven
    mvn test

4️⃣ Run tests using TestNG XML
    mvn clean test -DsuiteXmlFile=testng.xml

📊 Reports & Screenshots

Extent Reports are generated after every test run inside:
/reports/ExtentReport.html

Screenshots on failure are saved inside:
/screenshots/

🛠️ Future Enhancements

  ✅ Integrate with Jenkins for CI/CD

  ✅ Add Data-Driven Testing (Excel/CSV/DB)

  ✅ Implement Parallel Testing with Selenium Grid

  ✅ Add Cross-Browser Testing

