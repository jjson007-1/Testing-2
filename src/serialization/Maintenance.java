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
 * The Maintenance class represents maintenance records for equipment.
 * This corresponds to the "maintenance" table in the database.
 */
public class Maintenance implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int maintenanceId;
    private int equipmentId;
    private String performedBy;
    private Date datePerformed;
    private String status;
    
    /**
     * Default constructor
     */
    public Maintenance() {
        this.maintenanceId = 0;
        this.equipmentId = 0;
        this.performedBy = "";
        this.datePerformed = new Date();
        this.status = "";
    }
    
    /**
     * Parameterized constructor
     */
    public Maintenance(int maintenanceId, int equipmentId, String performedBy, 
                     Date datePerformed, String status) {
        this.maintenanceId = maintenanceId;
        this.equipmentId = equipmentId;
        this.performedBy = performedBy;
        this.datePerformed = datePerformed;
        this.status = status;
    }
    
    /**
     * Constructor without maintenanceId (for new records)
     */
    public Maintenance(int equipmentId, String performedBy, Date datePerformed, String status) {
        this.equipmentId = equipmentId;
        this.performedBy = performedBy;
        this.datePerformed = datePerformed;
        this.status = status;
    }
    
    // Getters and Setters
    public int getMaintenanceId() {
        return maintenanceId;
    }
    
    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }
    
    public int getEquipmentId() {
        return equipmentId;
    }
    
    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }
    
    public String getPerformedBy() {
        return performedBy;
    }
    
    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }
    
    public Date getDatePerformed() {
        return datePerformed;
    }
    
    public void setDatePerformed(Date datePerformed) {
        this.datePerformed = datePerformed;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Saves maintenance records to file
     */
    public static void saveMaintenance(ArrayList<Maintenance> maintenanceList) {
        FileOutputStream fileOut = null;
        ObjectOutputStream objOut = null;
        
        try {
            fileOut = new FileOutputStream("./src/MaintenanceDB.ser");
            objOut = new ObjectOutputStream(fileOut);
            
            for(Maintenance maintenance : maintenanceList) {
                objOut.writeObject(maintenance);
            }
            objOut.flush();
            objOut.close();
            System.out.printf("Serialized maintenance data is saved \n");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    /**
     * Reads maintenance records from file
     */
    public static ArrayList<Maintenance> readFromMaintenanceFile() {
        ArrayList<Maintenance> maintenanceList = new ArrayList<>();
        FileInputStream fileIn = null;
        ObjectInputStream objIn = null;
        Maintenance maintenance;
        
        try {
            fileIn = new FileInputStream("./src/MaintenanceDB.ser");
            objIn = new ObjectInputStream(fileIn);
            while(true) {
                try {
                    maintenance = (Maintenance) objIn.readObject();
                    if(maintenance != null) {
                        maintenanceList.add(maintenance);
                    } else if(maintenance == null) {
                        break; // Leave the while loop
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Object could not be converted to Maintenance");
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
        return maintenanceList;
    }
    
    @Override
    public String toString() {
        return "Maintenance [maintenanceId=" + maintenanceId + ", equipmentId=" + equipmentId + 
               ", performedBy=" + performedBy + ", datePerformed=" + datePerformed + 
               ", status=" + status + "]";
    }
}