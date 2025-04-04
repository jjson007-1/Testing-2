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
    private String staffRole;

    /**
     * Default Constructor
     */
    public Staff() {
        super(); // Call User's default constructor
        this.staffId = 0;
        this.position = "";
        this.status = "Active";
        this.staffRole = "";
    }

    /**
     * Full constructor with all User fields and Staff fields
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
     * Constructor that creates a Staff from an existing User
     * Useful for converting a regular User to a Staff member
     */
    public Staff(User user, int staffId, String position, String status, String staffRole) {
        super(user.getFullName(), user.getUserName(), user.getPassword(), 
              user.getPhone(), user.getEmail(), user.getAddress());
        this.staffId = staffId;
        this.position = position;
        this.status = status;
        this.staffRole = staffRole;
    }
    
    /**
     * Simplified constructor for creating a Staff member with basic information
     */
    public Staff(String fullName, String userName, String password, int phone, 
                String email, String staffRole) {
        super(fullName, userName, password, phone, email, "");
        this.staffId = 0; // Will be set later
        this.position = "Staff";
        this.status = "Active";
        this.staffRole = staffRole;
    }

    // Getters and Setters
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