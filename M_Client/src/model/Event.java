package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Event implements Serializable {

	private static final long serialVersionUID = 1L;

	private int eventId;
	private int customerId;
	private String eventName;
	private String eventLocation;
	private LocalDate eventDate;

	public Event() {
		this.eventId = 0;
		this.customerId = 0000;
		this.eventName = "";
		this.eventLocation = "";
		this.eventDate = LocalDate.of(2000, 1, 1);
	}

	public Event(int eventId, int customerId, String eventName, String eventLocation, LocalDate eventDate) {
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

	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

}
