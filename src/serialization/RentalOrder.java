package serialization;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * The RentalOrder class represents rental transactions between customers and equipment.
 */
public class RentalOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int rentalId;
    private int eventId;
    private int customerId;
    private serialization.Date rentalDate;
    private serialization.Date returnDate;
    private String rentStatus;
    private double totalPrice;
    
    /**
     * Default constructor
     */
    public RentalOrder() {
        this.rentalId = 0;
        this.eventId = 0;
        this.customerId = 0;
        this.rentalDate = new serialization.Date();
        this.returnDate = new serialization.Date();
        this.rentStatus = "";
        this.totalPrice = 0.0;
    }
    
    /**
     * Parameterized constructor
     */
    public RentalOrder(int rentalId, int eventId, int customerId, serialization.Date rentalDate1, 
                     serialization.Date returnDate1, String rentStatus, double totalPrice) {
        this.rentalId = rentalId;
        this.eventId = eventId;
        this.customerId = customerId;
        this.rentalDate = rentalDate1;
        this.returnDate = returnDate1;
        this.rentStatus = rentStatus;
        this.totalPrice = totalPrice;
    }
    
    /**
     * Constructor without rentalId (for new records)
     */
    public RentalOrder(int eventId, int customerId, serialization.Date rentalDate, 
                     serialization.Date returnDate, String rentStatus, double totalPrice) {
        this.eventId = eventId;
        this.customerId = customerId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.rentStatus = rentStatus;
        this.totalPrice = totalPrice;
    }
    
    // Getters and Setters
    public int getRentalId() {
        return rentalId;
    }
    
    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }
    
    public int getEventId() {
        return eventId;
    }
    
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public serialization.Date getRentalDate() {
        return rentalDate;
    }
    
    public void setRentalDate(serialization.Date rentalDate) {
        this.rentalDate = rentalDate;
    }
    
    public serialization.Date getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(serialization.Date returnDate) {
        this.returnDate = returnDate;
    }
    
    public String getRentStatus() {
        return rentStatus;
    }
    
    public void setRentStatus(String rentStatus) {
        this.rentStatus = rentStatus;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    /**
     * Calculates the rental duration in days
     */
    /*public int calculateDuration() {
        // 86400000 = 1000 * 60 * 60 * 24 (milliseconds in a day)
        return (int)((returnDate.getTime() - rentalDate.getTime()) / 86400000) + 1;
    }
    
    /**
     * Saves rental order records to file
     */
    public static void saveRentalOrders(ArrayList<RentalOrder> rentalOrderList) {
        FileOutputStream fileOut = null;
        ObjectOutputStream objOut = null;
        
        try {
            fileOut = new FileOutputStream("./src/RentalOrdersDB.ser");
            objOut = new ObjectOutputStream(fileOut);
            
            for(RentalOrder rentalOrder : rentalOrderList) {
                objOut.writeObject(rentalOrder);
            }
            objOut.flush();
            objOut.close();
            System.out.printf("Serialized rental order data is saved \n");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    /**
     * Reads rental order records from file
     */
    public static ArrayList<RentalOrder> readFromRentalOrdersFile() {
        ArrayList<RentalOrder> rentalOrderList = new ArrayList<>();
        FileInputStream fileIn = null;
        ObjectInputStream objIn = null;
        RentalOrder rentalOrder;
        
        try {
            fileIn = new FileInputStream("./src/RentalOrdersDB.ser");
            objIn = new ObjectInputStream(fileIn);
            while(true) {
                try {
                    rentalOrder = (RentalOrder) objIn.readObject();
                    if(rentalOrder != null) {
                        rentalOrderList.add(rentalOrder);
                    } else if(rentalOrder == null) {
                        break; // Leave the while loop
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Object could not be converted to RentalOrder");
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
        return rentalOrderList;
    }
    
    /**
     * Find rental orders for a specific customer
     */
    public static ArrayList<RentalOrder> findByCustomerId(int customerId) {
        ArrayList<RentalOrder> allRentalOrders = readFromRentalOrdersFile();
        ArrayList<RentalOrder> customerRentalOrders = new ArrayList<>();
        
        for (RentalOrder order : allRentalOrders) {
            if (order.getCustomerId() == customerId) {
                customerRentalOrders.add(order);
            }
        }
        
        return customerRentalOrders;
    }
    
    /**
     * Find rental orders for a specific event
     */
    public static ArrayList<RentalOrder> findByEventId(int eventId) {
        ArrayList<RentalOrder> allRentalOrders = readFromRentalOrdersFile();
        ArrayList<RentalOrder> eventRentalOrders = new ArrayList<>();
        
        for (RentalOrder order : allRentalOrders) {
            if (order.getEventId() == eventId) {
                eventRentalOrders.add(order);
            }
        }
        
        return eventRentalOrders;
    }
    
    @Override
    public String toString() {
        return "RentalOrder [rentalId=" + rentalId + ", eventId=" + eventId + 
               ", customerId=" + customerId + ", rentalDate=" + rentalDate + 
               ", returnDate=" + returnDate + ", rentStatus=" + rentStatus + 
               ", totalPrice=" + totalPrice + "]";
    }
}