package serialization;

import java.io.Serializable;

/**
 * The Customer class represents a customer in the system.
 * Customer extends User, inheriting all user properties.
 */
public class Customer extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int customerId;
    
    /**
     * Default constructor
     */
    public Customer() {
        super(); // Call User's default constructor
        this.customerId = 0;
    }
    
    /**
     * Full constructor with all User fields and Customer fields
     */
    public Customer(String fullName, String userName, String password, int phone, 
                   String email, String address, int customerId) {
        super(fullName, userName, password, phone, email, address);
        this.customerId = customerId;
    }
    
    /**
     * Simplified constructor for creating a Customer from basic information
     * Note that this sets User properties directly via setters
     */
    public Customer(String fullName, String phone, String email) {
        super(); // Call default constructor
        setFullName(fullName);
        
        // Parse phone to integer if possible, otherwise use 0
        try {
            setPhone(Integer.parseInt(phone.replaceAll("[^0-9]", "")));
        } catch (NumberFormatException e) {
            setPhone(0);
        }
        
        setEmail(email);
        this.customerId = 0; // Will be set later
    }
    
    /**
     * Constructor that creates a Customer from an existing User
     * Useful for converting a regular User to a Customer
     */
    public Customer(User user, int customerId) {
        super(user.getFullName(), user.getUserName(), user.getPassword(), 
              user.getPhone(), user.getEmail(), user.getAddress());
        this.customerId = customerId;
    }
    
    // Getters and Setters
    public int getId() {
        return customerId;
    }
    
    public void setId(int customerId) {
        this.customerId = customerId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Customer [customerId=" + customerId + ", fullName=" + getFullName() +
               ", userName=" + getUserName() + ", email=" + getEmail() + 
               ", phone=" + getPhone() + "]";
    }
}