package serialization;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

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
    
    // Methods for serialization
    public static void createCustomer(ArrayList<Customer> customersList) {
        try (FileOutputStream fileOut = new FileOutputStream("./src/CustomersDB.ser");
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
             
            for (Customer customer : customersList) {
                objOut.writeObject(customer);
            }
            System.out.printf("Serialized customers data is saved \n");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    public static ArrayList<Customer> readFromCustomersFile() {
        ArrayList<Customer> customersList = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream("./src/CustomersDB.ser");
             ObjectInputStream objIn = new ObjectInputStream(fileIn)) {
             
            while (true) {
                try {
                    Customer customer = (Customer) objIn.readObject();
                    customersList.add(customer);
                } catch (EOFException e) {
                    break;  // End of file
                } catch (ClassNotFoundException e) {
                    System.out.println("Object could not be converted to a Customer");
                }
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
        return customersList;
    }
}