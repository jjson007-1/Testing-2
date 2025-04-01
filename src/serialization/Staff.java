package serialization;

import java.io.Serializable;

public class Staff extends User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int staff_id;
	private String role;
	
	//default constructors
	public Staff() {
		super();
		staff_id = 0;
		role = "";
	}	
	
	//primary constructors
	public Staff(String fullName, String userName, String password, int phone, String email, String address, int staff_id, String role) {
		
		super(fullName, userName, password, phone, email, address);
		this.staff_id = staff_id;
		this.role = role;
	}
	
	//copy Constructor
	public Staff(Staff other) {
		super();
		this.role = other.role;
		this.staff_id = other.staff_id;
		
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	 // Method to assign a new role to the staff
    public void assignStaff_Role(String newRole) {
        this.role = newRole;
        System.out.println("Staff " + getFullName() + " has been assigned the role of " + newRole + ".");
    }
	
 // Method to perform maintenance
    public void performMaintenance() {
        System.out.println("Staff " + getFullName() + " is performing maintenance."); 
    }
}
	


