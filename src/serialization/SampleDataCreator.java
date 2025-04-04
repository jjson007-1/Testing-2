package serialization;

import java.util.ArrayList;

/**
 * Utility class to create sample data for testing the system
 */
public class SampleDataCreator {
    
    /**
     * Main method to create sample data and save it
     */
    public static void main(String[] args) {
        // Get DataManager instance
        DataManager dataManager = DataManager.getInstance();
        
        // Create sample users, staff, and customers
        createSampleUsers(dataManager);
        createSampleStaff(dataManager);
        createSampleCustomers(dataManager);
        createSampleEvents(dataManager);
        createSampleEquipment(dataManager);
        createSampleRentals(dataManager);
        createSampleInvoices(dataManager);
        
        System.out.println("Sample data created successfully!");
    }
    
    /**
     * Create sample users
     */
    private static void createSampleUsers(DataManager dataManager) {
        ArrayList<User> users = dataManager.getUsers();
        
        // Clear existing users if needed
        // users.clear();
        
        // Check if we already have sample data
        if (users.size() > 0) {
            System.out.println("Users already exist, skipping sample user creation.");
            return;
        }
        
        // Create sample users
        users.add(new User("Admin User", "admin", "admin123", 5551234, "admin@example.com", "123 Admin St"));
        users.add(new User("Staff User", "staff", "staff123", 5552345, "staff@example.com", "456 Staff St"));
        users.add(new User("Manager User", "manager", "manager123", 5553456, "manager@example.com", "789 Manager St"));
        users.add(new User("John Doe", "johndoe", "john123", 5554567, "john@example.com", "101 Customer St"));
        users.add(new User("Jane Smith", "janesmith", "jane123", 5555678, "jane@example.com", "202 Customer St"));
        
        // Save users
        dataManager.saveUsers(users);
        System.out.println("Created " + users.size() + " sample users.");
    }
    
    /**
     * Create sample staff members that extend User
     */
    private static void createSampleStaff(DataManager dataManager) {
        ArrayList<User> users = dataManager.getUsers();
        ArrayList<Staff> staffList = new ArrayList<>();
        
        // Find users we can convert to staff
        for (User user : users) {
            // Look for users that might be staff based on username or email
            if (user.getUserName().contains("staff") || 
                user.getUserName().contains("admin") || 
                user.getEmail().contains("staff") || 
                user.getEmail().contains("admin")) {
                
                // Skip if already a Staff object
                if (user instanceof Staff) {
                    staffList.add((Staff)user);
                    continue;
                }
                
                // Create a new Staff from this User
                int staffId = dataManager.getNextId("staff");
                Staff staff = new Staff(
                    user.getFullName(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getPhone(),
                    user.getEmail(),
                    user.getAddress(),
                    staffId,
                    "Manager",
                    "Active",
                    "Administrator"
                );
                
                staffList.add(staff);
                
                // Replace the User with Staff in users list
                users.remove(user);
                users.add(staff);
            }
        }
        
        // If no staff members were found, create some default ones
        if (staffList.isEmpty()) {
            // Create default admin
            int staffId = dataManager.getNextId("staff");
            Staff adminStaff = new Staff(
                "Admin User",
                "admin",
                "admin123",
                5551234,
                "admin@example.com",
                "123 Admin St",
                staffId,
                "Administrator",
                "Active",
                "System Admin"
            );
            staffList.add(adminStaff);
            users.add(adminStaff);
            
            // Create default staff
            staffId = dataManager.getNextId("staff");
            Staff defaultStaff = new Staff(
                "Staff User",
                "staff",
                "staff123",
                5552345,
                "staff@example.com",
                "456 Staff St",
                staffId,
                "Employee",
                "Active",
                "Assistant"
            );
            staffList.add(defaultStaff);
            users.add(defaultStaff);
        }
        
        // Save the updated users and staff lists
        dataManager.saveUsers(users);
        for (Staff staff : staffList) {
            dataManager.saveStaffMember(staff);
        }
        
        System.out.println("Created " + staffList.size() + " sample staff members.");
    }
    
    /**
     * Create sample customers that extend User
     */
    private static void createSampleCustomers(DataManager dataManager) {
        ArrayList<User> users = dataManager.getUsers();
        ArrayList<Customer> customers = dataManager.getCustomers();
        
        // Clear existing customers if needed
        // customers.clear();
        
        // Check if we already have sample data
        if (customers.size() > 0) {
            System.out.println("Customers already exist, skipping sample customer creation.");
            return;
        }
        
        // Find users we want to convert to customers
        User johnUser = null;
        User janeUser = null;
        
        for (User user : users) {
            if (user.getUserName().equals("johndoe")) {
                johnUser = user;
            } else if (user.getUserName().equals("janesmith")) {
                janeUser = user;
            }
        }
        
        // Create customers from users
        if (johnUser != null) {
            Customer john = new Customer(johnUser, 1);
            customers.add(john);
            
            // Also replace the User with a Customer in the users list
            users.remove(johnUser);
            users.add(john);
        }
        
        if (janeUser != null) {
            Customer jane = new Customer(janeUser, 2);
            customers.add(jane);
            
            // Also replace the User with a Customer in the users list
            users.remove(janeUser);
            users.add(jane);
        }
        
        // Create a few more customers
        Customer newCustomer1 = new Customer("Bob Johnson", "bobjohnson", "bob123", 5556789, "bob@example.com", "303 Customer St", 3);
        customers.add(newCustomer1);
        users.add(newCustomer1);
        
        Customer newCustomer2 = new Customer("Sarah Williams", "sarahw", "sarah123", 5557890, "sarah@example.com", "404 Customer St", 4);
        customers.add(newCustomer2);
        users.add(newCustomer2);
        
        // Save updated customers and users
        dataManager.saveCustomers(customers);
        dataManager.saveUsers(users);
        
        System.out.println("Created " + customers.size() + " sample customers.");
    }
    
    /**
     * Create sample events
     */
    private static void createSampleEvents(DataManager dataManager) {
        ArrayList<Event> events = dataManager.getEvents();
        
        // Check if we already have sample data
        if (events.size() > 0) {
            System.out.println("Events already exist, skipping sample event creation.");
            return;
        }
        
        // Create sample events
        events.add(new Event(1, 1, "Birthday Party", "Blue Mountain Hall", new Date(15, 3, 2025)));
        events.add(new Event(2, 2, "Wedding Reception", "Grand Plaza Hotel", new Date(20, 4, 2025)));
        events.add(new Event(3, 3, "Corporate Meeting", "Business Center", new Date(10, 5, 2025)));
        events.add(new Event(4, 4, "Music Festival", "Central Park", new Date(25, 6, 2025)));
        
        // Save events
        for (Event event : events) {
            dataManager.saveEvent(event);
        }
        
        System.out.println("Created " + events.size() + " sample events.");
    }
    
    /**
     * Create sample equipment
     */
    private static void createSampleEquipment(DataManager dataManager) {
        ArrayList<Equipment> equipmentList = dataManager.getEquipment();
        
        // Check if we already have sample data
        if (equipmentList.size() > 0) {
            System.out.println("Equipment already exists, skipping sample equipment creation.");
            return;
        }
        
        // Create sample equipment
        equipmentList.add(new Equipment(1, "Sound System", "Complete PA system with speakers and mixer", 200.0, "Available", "Excellent"));
        equipmentList.add(new Equipment(2, "Stage Lights", "Professional stage lighting set", 150.0, "Available", "Good"));
        equipmentList.add(new Equipment(3, "Microphone Set", "Set of 4 wireless microphones", 75.0, "Available", "Excellent"));
        equipmentList.add(new Equipment(4, "Projector", "High-definition projector with screen", 100.0, "Available", "Good"));
        equipmentList.add(new Equipment(5, "DJ Equipment", "Turntables and mixer for DJ performance", 250.0, "Available", "Excellent"));
        
        // Save equipment
        for (Equipment equipment : equipmentList) {
            dataManager.saveEquipment(equipment);
        }
        
        System.out.println("Created " + equipmentList.size() + " sample equipment items.");
    }
    
    /**
     * Create sample rental orders
     */
    private static void createSampleRentals(DataManager dataManager) {
        ArrayList<RentalOrder> rentals = dataManager.getRentals();
        
        // Check if we already have sample data
        if (rentals.size() > 0) {
            System.out.println("Rentals already exist, skipping sample rental creation.");
            return;
        }
        
        // Create sample rentals
        rentals.add(new RentalOrder(1, 1, 1, new Date(14, 3, 2025), new Date(16, 3, 2025), "Confirmed", 350.0));
        rentals.add(new RentalOrder(2, 2, 2, new Date(19, 4, 2025), new Date(21, 4, 2025), "Confirmed", 500.0));
        rentals.add(new RentalOrder(3, 3, 3, new Date(9, 5, 2025), new Date(11, 5, 2025), "Pending", 200.0));
        
        // Save rentals
        for (RentalOrder rental : rentals) {
            dataManager.saveRental(rental);
        }
        
        System.out.println("Created " + rentals.size() + " sample rentals.");
    }
    
    /**
     * Create sample invoices
     */
    private static void createSampleInvoices(DataManager dataManager) {
        ArrayList<Invoice> invoices = dataManager.getInvoices();
        
        // Check if we already have sample data
        if (invoices.size() > 0) {
            System.out.println("Invoices already exist, skipping sample invoice creation.");
            return;
        }
        
        // Create sample invoices
        // First invoice is paid
        invoices.add(new Invoice(1, 1, 1, 350.0, new Date(10, 3, 2025), "Credit Card"));
        
        // Second invoice is paid
        invoices.add(new Invoice(2, 2, 2, 500.0, new Date(15, 4, 2025), "Bank Transfer"));
        
        // Third invoice is not paid yet
        invoices.add(new Invoice(3, 3, 3, 200.0, null, null));
        
        // Save invoices
        for (Invoice invoice : invoices) {
            dataManager.saveInvoice(invoice);
        }
        
        System.out.println("Created " + invoices.size() + " sample invoices.");
    }
}