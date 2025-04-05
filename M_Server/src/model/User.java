package model;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;
	private String fullName;
	private String userName;
	private String password;
	private int phone;
	private String email;
	private String address;

	public User() {
		this.fullName = "";
		this.userName = "";
		this.password = "";
		this.phone = 0;
		this.email = "";
		this.address = "";
	}

	public User(String fullName, String userName, String password, int phone, String email, String address) {
		super();
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.address = address;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [fullName=" + fullName + ", userName=" + userName + ", password=" + password + ", phone=" + phone
				+ ", email=" + email + ", address=" + address + "]";
	}

}
