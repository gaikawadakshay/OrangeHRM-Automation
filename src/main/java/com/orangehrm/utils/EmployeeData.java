package com.orangehrm.utils;

public class EmployeeData {
    private String firstName;
    private String lastName;
    private String employeeId;
    private String username;
    private String password;
    private String confirmPassword;
    
    public EmployeeData(String firstName, String lastName, String employeeId, 
                       String username, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
    
    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmployeeId() { return employeeId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getConfirmPassword() { return confirmPassword; }
    
    // Builder pattern
    public static class Builder {
        private String firstName;
        private String lastName;
        private String employeeId;
        private String username;
        private String password;
        private String confirmPassword;
        
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public Builder employeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }
        
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        
        public Builder confirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }
        
        public EmployeeData build() {
            return new EmployeeData(firstName, lastName, employeeId, 
                                  username, password, confirmPassword);
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
}
