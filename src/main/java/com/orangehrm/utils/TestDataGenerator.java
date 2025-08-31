package com.orangehrm.utils;

import java.util.Random;

public class TestDataGenerator {
    private static final String[] FIRST_NAMES = {
        "John", "Jane", "Michael", "Sarah", "David", "Emma", 
        "Robert", "Lisa", "William", "Jennifer", "James", "Mary"
    };
    
    private static final String[] LAST_NAMES = {
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", 
        "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez"
    };
    
    public static EmployeeData generateEmployeeData() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String firstName = getRandomElement(FIRST_NAMES);
        String lastName = getRandomElement(LAST_NAMES);
        String employeeId = "EMP" + timestamp.substring(timestamp.length() - 6);
        String username = firstName.toLowerCase() + "." + lastName.toLowerCase() + 
                         timestamp.substring(timestamp.length() - 4);
        String password = "SecurePass123!";
        
        return EmployeeData.builder()
                .firstName(firstName)
                .lastName(lastName)
                .employeeId(employeeId)
                .username(username)
                .password(password)
                .confirmPassword(password)
                .build();
    }
    
    public static EmployeeData generateBasicEmployeeData() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String firstName = getRandomElement(FIRST_NAMES);
        String lastName = getRandomElement(LAST_NAMES);
        String employeeId = "EMP" + timestamp.substring(timestamp.length() - 6);
        
        return EmployeeData.builder()
                .firstName(firstName)
                .lastName(lastName)
                .employeeId(employeeId)
                .build();
    }
    
    public static EmployeeData generateCustomEmployeeData(String firstName, String lastName) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String employeeId = "EMP" + timestamp.substring(timestamp.length() - 6);
        String username = firstName.toLowerCase() + "." + lastName.toLowerCase() + 
                         timestamp.substring(timestamp.length() - 4);
        String password = "TestPass123!";
        
        return EmployeeData.builder()
                .firstName(firstName)
                .lastName(lastName)
                .employeeId(employeeId)
                .username(username)
                .password(password)
                .confirmPassword(password)
                .build();
    }
    
    private static String getRandomElement(String[] array) {
        Random random = new Random();
        return array[random.nextInt(array.length)];
    }
    
    public static String generateUniqueEmployeeId() {
        return "EMP" + System.currentTimeMillis();
    }
    
    public static String generateRandomUsername() {
        return "user" + System.currentTimeMillis();
    }
}
