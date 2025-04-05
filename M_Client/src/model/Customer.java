package model;

import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private int customer_id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    // Default constructor
    public Customer() {
        this.customer_id = 0;
        this.firstName = "";
        this.lastName = "";
        this.phone = "";
        this.email = "";
    }

    public Customer(String fullName, String phone, String email) {
        String[] names = fullName.split(" ");
        this.firstName = names[0];
        this.lastName = names.length > 1 ? names[1] : "";
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return customer_id;
    }

    public void setId(int customer_id) {
        this.customer_id = customer_id;
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

    @Override
    public String toString() {
        return "Customer [customer_id=" + customer_id + ", firstName=" + firstName + ", lastName=" + lastName +
                ", phone=" + phone + ", email=" + email + "]";
    }
}
