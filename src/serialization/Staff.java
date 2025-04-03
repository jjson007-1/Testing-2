package serialization;

import java.io.Serializable;

/**
 * The Staff class represents a staff member in the system.
 * Staff extends User, inheriting all user properties.
 */
public class Staff extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int staffId;
    private String position;
    private String status;
    private String staffRole;  // From database schema

    /**
     * Default Constructor
     */
    public Staff() {
        super(); // Call User's default constructor
        this.staffId = 0;
        this.position = "";
        this.status = "";
        this.staffRole = "";
    }

    /**
     * Constructor with User data
     * 
     * @param fullName The staff member's full name
     * @param userName The staff member's username
     * @param password The staff member's password
     * @param phone The staff member's phone number
     * @param email The staff member's email
     * @param address The staff member's address
     * @param staffId The staff member's ID
     * @param position The staff member's position
     * @param status The staff member's status
     * @param staffRole The staff member's role
     */
    public Staff(String fullName, String userName, String password, int phone, 
                String email, String address, int staffId, String position, 
                String status, String staffRole) {
        super(fullName, userName, password, phone, email, address);
        this.staffId = staffId;
        this.position = position;
        this.status = status;
        this.staffRole = staffRole;
    }
    
    /**
     * Constructor with only staff-specific fields
     * This is useful when creating a staff member from an existing user
     * 
     * @param staffId The staff member's ID
     * @param position The staff member's position
     * @param status The staff member's status
     * @param staffRole The staff member's role
     */
    public Staff(int staffId, String position, String status, String staffRole) {
        super();
        this.staffId = staffId;
        this.position = position;
        this.status = status;
        this.staffRole = staffRole;
    }

    // Getters and Setters for Staff-specific fields
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }

    /**
     * Display staff information
     */
    public String displayInfo() {
        return "Staff ID: " + staffId + ", Name: " + getFullName() + 
               ", Phone: " + getPhone() + ", Email: " + getEmail() + 
               ", Position: " + position + ", Status: " + status;
    }
    
    @Override
    public String toString() {
        return "Staff [staffId=" + staffId + ", fullName=" + getFullName() + 
               ", userName=" + getUserName() + ", email=" + getEmail() + 
               ", position=" + position + ", status=" + status + 
               ", staffRole=" + staffRole + "]";
    }
}