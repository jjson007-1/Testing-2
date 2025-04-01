package serialization;

public class Event {
	private int eventId;
	private int customerId;
	private String eventName;
	private String eventLocation;
	private Date eventDate;
	
	public Event() {
		this.eventId = 0 ;
		this.customerId = 0000;
		this.eventName = "";
		this.eventLocation = "";
		this.eventDate = new Date(1, 1, 2000);
	}
	
	public Event(int eventId, int customerId, String eventName, String eventLocation, Date eventDate) {
		this.eventId = eventId;
		this.customerId = customerId;
		this.eventName = eventName;
		this.eventLocation = eventLocation;
		this.eventDate = eventDate;
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

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	private void scheduleEquipment() {
		
	}
	
	private void requestRental() {
		
	}
	
	private void cancelRental() {
		
	}
	
	private void viewRental() {
		
	}
	
	private void updateEventDetails() {
		
	}
}
