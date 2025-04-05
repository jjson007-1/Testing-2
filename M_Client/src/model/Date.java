package model;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Date implements Serializable{
	
	private static Logger logger = LogManager.getLogger(Date.class);

	
	 private static final long serialVersionUID = 1L;
	    
	 private int day;
	 private int month;
	 private int year;
	 
	 public Date() {
		 this.setDefaultDate();
	 }
	 
	 private void setDefaultDate() {
		 this.day = 1;
	     this.month = 1;
	     this.year = 2000;
	 }
	 
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
		
		
		public void toDate(String date) {
	        if (date != null) {
	            String[] strDate = date.split("/");
	            if (strDate.length == 3) {
	                try {
	                    this.setDay(Integer.parseInt(strDate[0]));
	                    this.setMonth(Integer.parseInt(strDate[1]));
	                    this.setYear(Integer.parseInt(strDate[2]));
	                } catch (NumberFormatException e) {
	                	logger.error("Error: Date parts must be valid integers. Setting to default.");
	                    setDefaultDate();
	                }
	            } else {
	            	logger.trace("Warning: Date string format is incorrect (expected dd/mm/yyyy). Setting to default.");
	                setDefaultDate();
	            }
	        } else {
	        	logger.trace("Warning: Date string is null. Setting to default.");
	            setDefaultDate();
	        }
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
		 
		 @Override
		 public String toString() {
			 return getFormattedDate();
		 }
	 
	 
	    

}
