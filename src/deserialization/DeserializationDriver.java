package deserialization;

import java.util.ArrayList;
import java.util.List;

import serialization.Guest;
import serialization.User;

public class DeserializationDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<User> usersList = new ArrayList<User>();
		usersList = User.readFromUsersFile();
		for(User user : usersList) {
			System.out.println(user);
		}
	}

}
