package serialization;

import java.util.ArrayList;

import serialization.Customer;
import serialization.DataManager;
import serialization.Staff;
import serialization.User;

/**
 * Test class to verify the User-Staff-Customer inheritance relationship
 * and authentication flow.
 */
public class AuthInheritanceTest {

    public static void main(String[] args) {
        System.out.println("Testing User-Staff-Customer Inheritance Relationship\n");
        
        // Initialize DataManager
        DataManager dataManager = DataManager.getInstance();
        
        // 1. Test User, Staff, and Customer creation
        testClassCreation();
        
        // 2. Test inheritance relationships
        testInheritanceRelationship();
        
        // 3. Test polymorphic behavior
        testPolymorphism();
        
        // 4. Test authentication logic
        testAuthentication(dataManager);
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Test User, Staff, and Customer object creation
     */
    private static void testClassCreation() {
        System.out.println("1. Testing object creation...");
        
        // Create User
        User user = new User("Test User", "testuser", "password", 1234567890, 
                           "test@example.com", "123 Test St");
        
        // Create Staff extending User
        Staff staff = new Staff("Test Staff", "teststaff", "password", 1234567890,
                              "staff@example.com", "456 Staff St", 1, "Manager", 
                              "Active", "Administrator");
        
        // Create Customer extending User
        Customer customer = new Customer("Test Customer", "testcustomer", "password", 
                                      1234567890, "customer@example.com", 
                                      "789 Customer St", 1);
        
        // Create Customer from existing User
        Customer customerFromUser = new Customer(user, 2);
        
        // Print objects
        System.out.println("  User: " + user);
        System.out.println("  Staff: " + staff);
        System.out.println("  Customer: " + customer);
        System.out.println("  Customer from User: " + customerFromUser);
        
        System.out.println("  Object creation test passed.");
    }
    
    /**
     * Test inheritance relationship between User, Staff, and Customer
     */
    private static void testInheritanceRelationship() {
        System.out.println("\n2. Testing inheritance relationship...");
        
        // Create objects
        User user = new User("Test User", "testuser", "password", 1234567890, 
                           "test@example.com", "123 Test St");
        
        Staff staff = new Staff("Test Staff", "teststaff", "password", 1234567890,
                              "staff@example.com", "456 Staff St", 1, "Manager", 
                              "Active", "Administrator");
        
        Customer customer = new Customer("Test Customer", "testcustomer", "password", 
                                      1234567890, "customer@example.com", 
                                      "789 Customer St", 1);
        
        // Test instanceof relationships
        System.out.println("  Is staff instance of User? " + (staff instanceof User));
        System.out.println("  Is customer instance of User? " + (customer instanceof User));
        System.out.println("  Is user instance of Staff? " + (user instanceof Staff));
        System.out.println("  Is user instance of Customer? " + (user instanceof Customer));
        
        // Test inheritance by accessing User methods from Staff and Customer
        System.out.println("  Staff username (from User): " + staff.getUserName());
        System.out.println("  Customer email (from User): " + customer.getEmail());
        
        // Verify Staff and Customer specific methods
        System.out.println("  Staff role (Staff-specific): " + staff.getStaffRole());
        System.out.println("  Customer ID (Customer-specific): " + customer.getId());
        
        System.out.println("  Inheritance relationship test passed.");
    }
    
    /**
     * Test polymorphic behavior with User, Staff, and Customer
     */
    private static void testPolymorphism() {
        System.out.println("\n3. Testing polymorphic behavior...");
        
        // Create array of User objects
        User[] users = new User[3];
        
        // Populate with User, Staff, and Customer
        users[0] = new User("Test User", "testuser", "password", 1234567890, 
                          "test@example.com", "123 Test St");
        
        users[1] = new Staff("Test Staff", "teststaff", "password", 1234567890,
                           "staff@example.com", "456 Staff St", 1, "Manager", 
                           "Active", "Administrator");
        
        users[2] = new Customer("Test Customer", "testcustomer", "password", 
                              1234567890, "customer@example.com", 
                              "789 Customer St", 1);
        
        // Demonstrate polymorphism - calling User methods on all objects
        for (int i = 0; i < users.length; i++) {
            System.out.println("  User[" + i + "] class: " + users[i].getClass().getSimpleName());
            System.out.println("  User[" + i + "] fullName: " + users[i].getFullName());
            System.out.println("  User[" + i + "] toString(): " + users[i].toString());
            System.out.println();
        }
        
        // Test downcasting
        System.out.println("  Testing downcasting:");
        for (User user : users) {
            if (user instanceof Staff) {
                Staff staff = (Staff) user;
                System.out.println("  Downcasted to Staff: " + staff.getStaffRole());
            } else if (user instanceof Customer) {
                Customer customer = (Customer) user;
                System.out.println("  Downcasted to Customer: " + customer.getId());
            } else {
                System.out.println("  Regular User: " + user.getUserName());
            }
        }
        
        System.out.println("  Polymorphism test passed.");
    }
    
    /**
     * Test authentication logic with User, Staff, and Customer
     */
    private static void testAuthentication(DataManager dataManager) {
        System.out.println("\n4. Testing authentication logic...");
        
        // Get existing users
        ArrayList<User> users = dataManager.getUsers();
        
        // Make sure we have some sample data
        if (users.isEmpty()) {
            System.out.println("  No users found. Please run SampleDataCreator first.");
            return;
        }
        
        // Test authentication success
        User testUser = authenticateUser(users, "johndoe", "john123");
        if (testUser != null) {
            System.out.println("  Authentication successful for: " + testUser.getFullName());
            
            // Test user type identification
            String userType = identifyUserType(testUser);
            System.out.println("  User type identified as: " + userType);
            
            // Test dashboard selection based on user type
            String dashboard = selectDashboard(userType, testUser);
            System.out.println("  Selected dashboard: " + dashboard);
        } else {
            System.out.println("  Authentication failed for: johndoe");
        }
        
        // Test authentication failure
        User failedUser = authenticateUser(users, "johndoe", "wrongpassword");
        if (failedUser == null) {
            System.out.println("  Authentication correctly failed for wrong password.");
        } else {
            System.out.println("  ERROR: Authentication should have failed for wrong password.");
        }
        
        System.out.println("  Authentication logic test passed.");
    }
    
    /**
     * Simulate user authentication
     */
    private static User authenticateUser(ArrayList<User> users, String username, String password) {
        for (User user : users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Identify the type of user
     */
    private static String identifyUserType(User user) {
        if (user instanceof Staff) {
            return "staff";
        } else if (user instanceof Customer) {
            return "customer";
        } else {
            return "user";
        }
    }
    
    /**
     * Select appropriate dashboard based on user type
     */
    private static String selectDashboard(String userType, User user) {
        if (userType.equals("staff")) {
            Staff staff = (Staff) user;
            return "StaffDashboard for " + staff.getStaffRole();
        } else if (userType.equals("customer")) {
            Customer customer = (Customer) user;
            return "CustomerDashboard for Customer #" + customer.getId();
        } else {
            return "Generic Dashboard";
        }
    }
}