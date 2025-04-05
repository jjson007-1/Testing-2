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
    
   
    public Date() {
        this.day = 1;
        this.month = 1;
        this.year = 2000;
    }
    
   
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    
    public Date(Date other) {
        this.day = other.day;
        this.month = other.month;
        this.year = other.year;
    }
    
    
    public int getDay() {
        return day;
    }
    
    
    public void setDay(int day) {
        if (day >= 1 && day <= 31) {
            this.day = day;
        }
    }
    
    
    public int getMonth() {
        return month;
    }
    
    
    public void setMonth(int month) {
        if (month >= 1 && month <= 12) {
            this.month = month;
        }
    }
    
    
    public int getYear() {
        return year;
    }
    
    
    public void setYear(int year) {
        this.year = year;
    }
    
    
    public String getFormattedDate() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }
    
    
    public boolean isAfter(Date other) {
        if (this.year > other.year) return true;
        if (this.year < other.year) return false;
        
        if (this.month > other.month) return true;
        if (this.month < other.month) return false;
        
        return this.day > other.day;
    }
    
    
    public boolean isBefore(Date other) {
        if (this.year < other.year) return true;
        if (this.year > other.year) return false;
        
        if (this.month < other.month) return true;
        if (this.month > other.month) return false;
        
        return this.day < other.day;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Date other = (Date) obj;
        return day == other.day && month == other.month && year == other.year;
    }
    
    
    @Override
    public String toString() {
        return getFormattedDate();
    }
}