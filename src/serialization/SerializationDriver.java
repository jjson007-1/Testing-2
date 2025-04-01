package serialization;

import java.util.ArrayList;

public class SerializationDriver {

	public static void main(String[] args) {
		ArrayList<User> usersList = new ArrayList<>();
		
		usersList.add(new User("John Brown", "JBrown22", "123456", 9087624, "jbrown22@gmail.com", "11 Limmer Lane"));
		usersList.add(new User("Jane Doe", "JDoe54", "4586", 2658749, "doejane@gmail.com", "85 Limmer Lane"));

		User.createUser(usersList);

		ArrayList<Customer> customersList = new ArrayList<>();

		customersList.add(new Customer("JOHNSON KING", "1234212", "jking@gmail.com"));
		Customer.createCustomer(customersList);

	}

}