package model;

import java.io.Serializable;

public class Staff implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4L;
    private int staffId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String position;
    private String status;

    // Default Constructor
    public Staff() {
        this.staffId = 0;
        this.firstName = "";
        this.lastName = "";
        this.phone = "";
        this.email = "";
        this.position = "";
        this.status = "";
    }

    // Primary Constructor
    public Staff(String firstName, String lastName, String phone, String email, String position, String status) {
        this.staffId = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.status = status;
    }

    // Copy Constructor
    public Staff(Staff other) {
        this.staffId = other.staffId;
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.phone = other.phone;
        this.email = other.email;
        this.position = other.position;
        this.status = other.status;
    }

    // Getters and Setters
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String displayInfo() {
        return "Staff ID: " + staffId + ", Name: " + firstName + " " + lastName + ", Phone: " + phone +
                ", Email: " + email + ", Position: " + position + ", Status: " + status;
    }
}
