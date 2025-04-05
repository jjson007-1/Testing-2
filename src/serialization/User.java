package serialization;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import serialization.User;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fullName;
	private String userName;
	private String password;
	private int phone;
	private String email;
	private String address;
	
	public User () {
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
	
	public static void createUser(ArrayList<User> usersList) {
			FileOutputStream fileOut = null;
			ObjectOutputStream objOut = null;
			
			try {
				fileOut = new FileOutputStream("./src/UsersDB.ser");
				objOut = new ObjectOutputStream(fileOut);
				
				for(User user : usersList) {
					objOut.writeObject(user);
				}
				objOut.flush();
				objOut.close();
				System.out.printf("Serialized users data is saved \n");
			} catch (IOException i) {
				i.printStackTrace();
			}
		}
	
	public static ArrayList<User> readFromUsersFile(){
		ArrayList<User> usersList = new ArrayList<>();
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		User user;
		
		try {
			fileIn = new FileInputStream("./src/UsersDB.ser");
			objIn = new ObjectInputStream(fileIn);
			while(true) {
				try {
					user = (User) objIn.readObject();
					if(user != null) {
						//Add employee to the list of users
						usersList.add(user);
					}else if(user == null) {
						break;
					}
				} catch (ClassNotFoundException e) {
					System.out.println("Object could not be converted to a User");
				} catch (EOFException e) {
					break;
				}
			}
		} catch (IOException i) {
			i.printStackTrace();
		}
		return usersList;
	}
	
	}

	
