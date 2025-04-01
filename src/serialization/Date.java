package serialization;

import java.io.Serializable;

/**
 * Simple Date class for the Event Scheduling System
 */
public class Date implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int day;
    private int month;
    private int year;
    
    /**
     * Default constructor - sets date to January 1, 2000
     */
    public Date() {
        this.day = 1;
        this.month = 1;
        this.year = 2000;
    }
    
    /**
     * Constructor with day, month, and year
     * 
     * @param day The day of the month (1-31)
     * @param month The month (1-12)
     * @param year The year (4-digit)
     */
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    /**
     * Copy constructor
     * 
     * @param other The date to copy
     */
    public Date(Date other) {
        this.day = other.day;
        this.month = other.month;
        this.year = other.year;
    }
    
    /**
     * Get the day of the month
     * 
     * @return The day
     */
    public int getDay() {
        return day;
    }
    
    /**
     * Set the day of the month
     * 
     * @param day The day to set
     */
    public void setDay(int day) {
        if (day >= 1 && day <= 31) {
            this.day = day;
        }
    }
    
    /**
     * Get the month
     * 
     * @return The month
     */
    public int getMonth() {
        return month;
    }
    
    /**
     * Set the month
     * 
     * @param month The month to set
     */
    public void setMonth(int month) {
        if (month >= 1 && month <= 12) {
            this.month = month;
        }
    }
    
    /**
     * Get the year
     * 
     * @return The year
     */
    public int getYear() {
        return year;
    }
    
    /**
     * Set the year
     * 
     * @param year The year to set
     */
    public void setYear(int year) {
        this.year = year;
    }
    
    /**
     * Get a formatted string representation (DD/MM/YYYY)
     * 
     * @return The formatted date string
     */
    public String getFormattedDate() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }
    
    /**
     * Check if this date is after another date
     * 
     * @param other The date to compare with
     * @return true if this date is after other, false otherwise
     */
    public boolean isAfter(Date other) {
        if (this.year > other.year) return true;
        if (this.year < other.year) return false;
        
        if (this.month > other.month) return true;
        if (this.month < other.month) return false;
        
        return this.day > other.day;
    }
    
    /**
     * Check if this date is before another date
     * 
     * @param other The date to compare with
     * @return true if this date is before other, false otherwise
     */
    public boolean isBefore(Date other) {
        if (this.year < other.year) return true;
        if (this.year > other.year) return false;
        
        if (this.month < other.month) return true;
        if (this.month > other.month) return false;
        
        return this.day < other.day;
    }
    
    /**
     * Check if this date is equal to another date
     * 
     * @param obj The object to compare with
     * @return true if the dates are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Date other = (Date) obj;
        return day == other.day && month == other.month && year == other.year;
    }
    
    /**
     * Get a string representation of the date
     * 
     * @return The date as a string
     */
    @Override
    public String toString() {
        return getFormattedDate();
    }
}