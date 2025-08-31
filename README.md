# OrangeHRM Automation Testing Project

## Project Summary
This is an end-to-end automation test suite for OrangeHRM using Selenium WebDriver, TestNG, and Maven. It demonstrates login, scrolling, adding/editing employees, and logout with Page Object Model (POM).

## Prerequisites
- JDK 17+
- Maven 3.8+
- Chrome browser

## Setup
1. Clone or unzip the project.
2. Run  to download dependencies.

## Test Organization
- Page Objects: 
- Tests: 
- Utilities: 
- Uses POM for encapsulation, TestNG for structure.

## How to Run
- Default suite: 
- Specific suite: 
- In IDE: Import as Maven project, right-click testng.xml and run as TestNG suite.
- Reports: Check  for TestNG reports; screenshots in  on failure.

## Adding More Tests
Extend BaseTest and create new @Test methods using page objects.

## CI Suggestions
Use GitHub Actions with this workflow (.github/workflows/ci.yml):

