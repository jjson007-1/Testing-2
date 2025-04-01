package serialization;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Equipment class represents equipment items available for rental.
 * This corresponds to the "item" table in the database.
 */
public class Equipment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int equipmentId;
    private String name;
    private String description;
    private double rentalPrice;
    private String availabilityStatus;
    private String conditionStatus;
    
    /**
     * Default constructor
     */
    public Equipment() {
        this.equipmentId = 0;
        this.name = "";
        this.description = "";
        this.rentalPrice = 0.0;
        this.availabilityStatus = "Available";
        this.conditionStatus = "Good";
    }
    
    /**
     * Parameterized constructor
     */
    public Equipment(int equipmentId, String name, String description, double rentalPrice, 
                   String availabilityStatus, String conditionStatus) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.description = description;
        this.rentalPrice = rentalPrice;
        this.availabilityStatus = availabilityStatus;
        this.conditionStatus = conditionStatus;
    }
    
    // Getters and Setters
    public int getEquipmentId() {
        return equipmentId;
    }
    
    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getRentalPrice() {
        return rentalPrice;
    }
    
    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }
    
    public String getAvailabilityStatus() {
        return availabilityStatus;
    }
    
    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
    
    public String getConditionStatus() {
        return conditionStatus;
    }
    
    public void setConditionStatus(String conditionStatus) {
        this.conditionStatus = conditionStatus;
    }
    
    /**
     * Saves equipment list to file
     */
    public static void saveEquipment(ArrayList<Equipment> equipmentList) {
        FileOutputStream fileOut = null;
        ObjectOutputStream objOut = null;
        
        try {
            fileOut = new FileOutputStream("./src/EquipmentDB.ser");
            objOut = new ObjectOutputStream(fileOut);
            
            for(Equipment equipment : equipmentList) {
                objOut.writeObject(equipment);
            }
            objOut.flush();
            objOut.close();
            System.out.printf("Serialized equipment data is saved \n");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    /**
     * Reads equipment data from file
     */
    public static ArrayList<Equipment> readFromEquipmentFile() {
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        FileInputStream fileIn = null;
        ObjectInputStream objIn = null;
        Equipment equipment;
        
        try {
            fileIn = new FileInputStream("./src/EquipmentDB.ser");
            objIn = new ObjectInputStream(fileIn);
            while(true) {
                try {
                    equipment = (Equipment) objIn.readObject();
                    if(equipment != null) {
                        equipmentList.add(equipment);
                    } else if(equipment == null) {
                        break; // Leave the while loop
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Object could not be converted to Equipment");
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
        return equipmentList;
    }
    
    @Override
    public String toString() {
        return "Equipment [equipmentId=" + equipmentId + ", name=" + name + ", description=" + description + 
               ", rentalPrice=" + rentalPrice + ", availabilityStatus=" + availabilityStatus + 
               ", conditionStatus=" + conditionStatus + "]";
    }
}