package model;

import java.io.Serializable;

public class Equipment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7863719212774095596L;
	 
    private int equipmentId;
    private String name;
    private String description;
    private double rentalPrice;
    private Date date_added;
    private String availabilityStatus;
    private String conditionStatus;
    
    /**
     * Default constructor
     */
    public Equipment() {
        this.equipmentId = 0;
        this.name = "";
        this.description = "";
        this.date_added = new Date();
        this.rentalPrice = 0.0;
        this.availabilityStatus = "Available";
        this.conditionStatus = "Good";
    }
    
    /**
     * Parameterized constructor
     */
    public Equipment(int equipmentId, String name, String description,double rentalPrice, Date date_added, 
                   String availabilityStatus, String conditionStatus) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.description = description;
        this.date_added = date_added;
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

	public Date getDate_added() {
		return date_added;
	}

	public void setDate_added(Date date_added) {
		this.date_added = date_added;
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
	
	

	@Override
	public String toString() {
		return "Equipment [equipmentId=" + equipmentId + ", name=" + name + ", description=" + description
				+ ", rentalPrice=" + rentalPrice + ", date_added=" + date_added + ", availabilityStatus="
				+ availabilityStatus + ", conditionStatus=" + conditionStatus + "]";
	}


    
    

}
