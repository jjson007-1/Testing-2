package model;

import java.io.Serializable;

public class RentalOrder implements Serializable{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5549873273467712550L;
	
	private int rentalId;
	private int eventId;
	private int customerId;
	private Date rentalDate;
	private Date returnDate;
	private String rentStatus;
	private double totalPrice;
	
	/**
	 * Default constructor
	 */
	public RentalOrder() {
	    this.rentalId = 0;
	    this.eventId = 0;
	    this.customerId = 0;
	    this.rentalDate = new Date();
	    this.returnDate = new Date();
	    this.rentStatus = "";
	    this.totalPrice = 0.0;
	}
	
	/**
	 * Parameterized constructor
	 */
	public RentalOrder(int rentalId, int eventId, int customerId, Date rentalDate1, 
	                 Date returnDate1, String rentStatus, double totalPrice) {
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
	public RentalOrder(int eventId, int customerId, Date rentalDate, 
	                 Date returnDate, String rentStatus, double totalPrice) {
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
	
	public Date getRentalDate() {
	    return rentalDate;
	}
	
	public void setRentalDate(Date rentalDate) {
	    this.rentalDate = rentalDate;
	}
	
	public Date getReturnDate() {
	    return returnDate;
	}
	
	public void setReturnDate(Date returnDate) {
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
	
	
	
	@Override
	public String toString() {
	    return "RentalOrder [rentalId=" + rentalId + ", eventId=" + eventId + 
	           ", customerId=" + customerId + ", rentalDate=" + rentalDate + 
	           ", returnDate=" + returnDate + ", rentStatus=" + rentStatus + 
	           ", totalPrice=" + totalPrice + "]";
	}
}