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
     * Constructor with User data
     */
    public Customer(String fullName, String userName, String password, int phone, 
                   String email, String address, int customerId) {
        super(fullName, userName, password, phone, email, address);
        this.customerId = customerId;
    }
    
    /**
     * Simplified constructor (used in current code)
     */
    public Customer(String fullName, String phone, String email) {
        super();
        setFullName(fullName);
        try {
            setPhone(Integer.parseInt(phone.replaceAll("[^0-9]", "")));
        } catch (NumberFormatException e) {
            setPhone(0); // Default value if phone cannot be parsed
        }
        setEmail(email);
        this.customerId = 0; // Will be set later
    }
    
    // Getters and Setters
    public int getId() {
        return customerId;
    }
    
    public void setId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Customer [customerId=" + customerId + ", fullName=" + getFullName() +
               ", email=" + getEmail() + ", phone=" + getPhone() + "]";
    }
}