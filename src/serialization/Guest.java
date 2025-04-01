package serialization;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Guest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName = "";
	private String lastName = "";
	private int age = 0;
	private String bandColour = "";
	
	public Guest() {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.bandColour = bandColour;
	}
	
	public Guest(String firstName, String lastName, int age, String bandColour) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.bandColour = bandColour;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBandColour() {
		return bandColour;
	}

	public void setBandColour(String bandColour) {
		this.bandColour = bandColour;
	}

	@Override
	public String toString() {
		return "Guest [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", bandColour="
				+ bandColour + "]";
	}
	
	public static void writeToGuestFile(ArrayList<Guest> guestsList) {
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut = null;
		
		try {
			fileOut = new FileOutputStream("./src/GuestsDB.ser");
			objOut = new ObjectOutputStream(fileOut);
			
			for(Guest guest : guestsList) {
				objOut.writeObject(guest);
			}
			objOut.flush();
			objOut.close();
			System.out.printf("Serialized users data is saved \n");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	public static ArrayList<Guest> readFromGuestsFile(){
		ArrayList<Guest> guestsList = new ArrayList<>();
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		Guest guest;
		
		try {
			fileIn = new FileInputStream("./src/GuestsDB.ser");
			objIn = new ObjectInputStream(fileIn);
			while(true) {
				try {
					guest = (Guest) objIn.readObject();
					if(guest != null) {
						//Add employee to the list of users
						guestsList.add(guest);
					}else if(guest == null) {
						break; //Leave the while loop
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
		return guestsList;
	}
}


