package serialization;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Event class represents an event in the scheduling system.
 */
public class Event implements Serializable {
    // Add serialVersionUID for proper serialization
    private static final long serialVersionUID = 1L;
    
    private int eventId;
    private int customerId;
    private String eventName;
    private String eventLocation;
    private Date eventDate;
    
    /**
     * Default constructor
     */
    public Event() {
        this.eventId = 0;
        this.customerId = 0;
        this.eventName = "";
        this.eventLocation = "";
        this.eventDate = new Date(1, 1, 2000);
    }
    
    /**
     * Full constructor
     * 
     * @param eventId The ID of the event
     * @param customerId The ID of the customer
     * @param eventName The name of the event
     * @param eventLocation The location of the event
     * @param eventDate The date of the event
     */
    public Event(int eventId, int customerId, String eventName, String eventLocation, Date eventDate) {
        this.eventId = eventId;
        this.customerId = customerId;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
    }
    
    /**
     * Constructor without event ID (for new events)
     * 
     * @param customerId The ID of the customer
     * @param eventName The name of the event
     * @param eventLocation The location of the event
     * @param eventDate The date of the event
     */
    public Event(int customerId, String eventName, String eventLocation, Date eventDate) {
        this.eventId = 0; // Will be set when saved
        this.customerId = customerId;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
    }

    // Getters and Setters
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
    
    /**
     * Check if this event conflicts with another event
     * 
     * @param other The other event to check against
     * @return true if there's a conflict, false otherwise
     */
    public boolean conflictsWith(Event other) {
        // If they're on different days, no conflict
        if (!this.eventDate.equals(other.eventDate)) {
            return false;
        }
        
        // If they're at the same location on the same day, it's a conflict
        if (this.eventLocation.equals(other.eventLocation)) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Format the event details as a string
     * 
     * @return Formatted event details
     */
    public String getFormattedDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Event ID: ").append(eventId).append("\n");
        sb.append("Name: ").append(eventName).append("\n");
        sb.append("Date: ").append(eventDate.getFormattedDate()).append("\n");
        sb.append("Location: ").append(eventLocation).append("\n");
        sb.append("Customer ID: ").append(customerId);
        return sb.toString();
    }
    
    /**
     * Get a short summary of the event
     * 
     * @return Short event summary
     */
    public String getSummary() {
        return eventName + " on " + eventDate.getFormattedDate() + " at " + eventLocation;
    }
    
    /**
     * Check if the event is in the future
     * 
     * @return true if the event is in the future, false otherwise
     */
    public boolean isFutureEvent() {
        // In a real implementation, we would compare with the current date
        // For simplicity, we'll assume events in 2025 are in the future
        return eventDate.getYear() >= 2025;
    }
    
    /**
     * Check if the event is in the past
     * 
     * @return true if the event is in the past, false otherwise
     */
    public boolean isPastEvent() {
        // In a real implementation, we would compare with the current date
        // For simplicity, we'll assume events before 2025 are in the past
        return eventDate.getYear() < 2025;
    }
    
    /**
     * String representation of the event
     */
    @Override
    public String toString() {
        return "Event [eventId=" + eventId + ", customerId=" + customerId + ", eventName=" + eventName + 
               ", eventLocation=" + eventLocation + ", eventDate=" + eventDate + "]";
    }
    
    /**
     * Equals method for comparing events
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Event other = (Event) obj;
        return eventId == other.eventId;
    }
}