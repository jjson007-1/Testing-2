package serialization;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Centralized manager for all data operations.
 * Provides methods to load and save all types of data.
 */
public class DataManager {
    // File paths
    private static final String DATA_DIRECTORY = "./src/";
    private static final String EVENTS_FILE = DATA_DIRECTORY + "EventsDB.ser";
    private static final String EQUIPMENT_FILE = DATA_DIRECTORY + "EquipmentDB.ser";
    private static final String RENTALS_FILE = DATA_DIRECTORY + "RentalsDB.ser";
    private static final String INVOICES_FILE = DATA_DIRECTORY + "InvoicesDB.ser";
    private static final String USERS_FILE = DATA_DIRECTORY + "UsersDB.ser";
    private static final String STAFF_FILE = DATA_DIRECTORY + "StaffDB.ser";  // New file for Staff
    private static final String CUSTOMERS_FILE = DATA_DIRECTORY + "CustomersDB.ser";
    
    private static DataManager instance;
    
    // Data collections
    private ArrayList<Event> events;
    private ArrayList<Equipment> equipment;
    private ArrayList<RentalOrder> rentals;
    private ArrayList<Invoice> invoices;
    private ArrayList<User> users;
    private ArrayList<Staff> staff;  // New collection for Staff
    private ArrayList<Customer> customers;
    
   
    private DataManager() {
        // Initialize data collections
        events = new ArrayList<>();
        equipment = new ArrayList<>();
        rentals = new ArrayList<>();
        invoices = new ArrayList<>();
        users = new ArrayList<>();
        staff = new ArrayList<>();  // Initialize staff list
        customers = new ArrayList<>();
        
        // Load all data on startup
        loadAllData();
    }
    
    /**
     * Get singleton instance
     * @return The DataManager instance
     */
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    
    /**
     * Load all data from files
     */
    private void loadAllData() {
        events = loadEvents();
        equipment = loadEquipment();
        rentals = loadRentals();
        invoices = loadInvoices();
        users = loadUsers();
        staff = loadStaff();  // Load staff data
        customers = loadCustomers();
    }
    
    /**
     * Save all data to files
     */
    public void saveAllData() {
        saveEvents(events);
        saveEquipment(equipment);
        saveRentals(rentals);
        saveInvoices(invoices);
        saveUsers(users);
        saveStaff(staff);  // Save staff data
        saveCustomers(customers);
    }
    
    /**
     * Ensure data directory exists
     */
    private static void ensureDirectoryExists() {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    // ---------- EVENT METHODS ----------
    
    /**
     * Get all events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }
    
    /**
     * Add or update an event
     */
    public void saveEvent(Event event) {
        // Check if event already exists
        boolean found = false;
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventId() == event.getEventId()) {
                events.set(i, event); // Update existing
                found = true;
                break;
            }
        }
        
        if (!found) {
            events.add(event); // Add new
        }
        
        saveEvents(events);
    }
    
    /**
     * Delete an event
     */
    public boolean deleteEvent(int eventId) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventId() == eventId) {
                events.remove(i);
                saveEvents(events);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Load events from file
     */
    private ArrayList<Event> loadEvents() {
        ArrayList<Event> result = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(EVENTS_FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            
            while (true) {
                try {
                    Event event = (Event) in.readObject();
                    if (event != null) {
                        result.add(event);
                    }
                } catch (EOFException e) {
                    break; // End of file
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // File might not exist yet or other issue
            System.out.println("Info: Could not load events file. " + e.getMessage());
        }
        return result;
    }
    
    /**
     * Save events to file
     */
    private void saveEvents(ArrayList<Event> eventsList) {
        ensureDirectoryExists();
        try (FileOutputStream fileOut = new FileOutputStream(EVENTS_FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            for (Event event : eventsList) {
                out.writeObject(event);
            }
        } catch (IOException e) {
            System.out.println("Error saving events: " + e.getMessage());
        }
    }
    
    // ---------- EQUIPMENT METHODS ----------
    
    /**
     * Get all equipment
     */
    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }
    
    /**
     * Add or update equipment
     */
    public void saveEquipment(Equipment item) {
        boolean found = false;
        for (int i = 0; i < equipment.size(); i++) {
            if (equipment.get(i).getEquipmentId() == item.getEquipmentId()) {
                equipment.set(i, item);
                found = true;
                break;
            }
        }
        
        if (!found) {
            equipment.add(item);
        }
        
        saveEquipment(equipment);
    }
    
    /**
     * Delete equipment
     */
    public boolean deleteEquipment(int equipmentId) {
        for (int i = 0; i < equipment.size(); i++) {
            if (equipment.get(i).getEquipmentId() == equipmentId) {
                equipment.remove(i);
                saveEquipment(equipment);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Load equipment from file
     */
    private ArrayList<Equipment> loadEquipment() {
        ArrayList<Equipment> result = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(EQUIPMENT_FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            
            while (true) {
                try {
                    Equipment item = (Equipment) in.readObject();
                    if (item != null) {
                        result.add(item);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Info: Could not load equipment file. " + e.getMessage());
        }
        return result;
    }
    
    /**
     * Save equipment to file
     */
    private void saveEquipment(ArrayList<Equipment> equipmentList) {
        ensureDirectoryExists();
        try (FileOutputStream fileOut = new FileOutputStream(EQUIPMENT_FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            for (Equipment item : equipmentList) {
                out.writeObject(item);
            }
        } catch (IOException e) {
            System.out.println("Error saving equipment: " + e.getMessage());
        }
    }
    
    // ---------- RENTAL METHODS ----------
    
    /**
     * Get all rental orders
     */
    public ArrayList<RentalOrder> getRentals() {
        return rentals;
    }
    
    /**
     * Add or update a rental order
     */
    public void saveRental(RentalOrder rental) {
        boolean found = false;
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).getRentalId() == rental.getRentalId()) {
                rentals.set(i, rental);
                found = true;
                break;
            }
        }
        
        if (!found) {
            rentals.add(rental);
        }
        
        saveRentals(rentals);
    }
    
    /**
     * Delete a rental order
     */
    public boolean deleteRental(int rentalId) {
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).getRentalId() == rentalId) {
                rentals.remove(i);
                saveRentals(rentals);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Load rental orders from file
     */
    private ArrayList<RentalOrder> loadRentals() {
        ArrayList<RentalOrder> result = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(RENTALS_FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            
            while (true) {
                try {
                    RentalOrder rental = (RentalOrder) in.readObject();
                    if (rental != null) {
                        result.add(rental);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Info: Could not load rentals file. " + e.getMessage());
        }
        return result;
    }
    
    /**
     * Save rental orders to file
     */
    private void saveRentals(ArrayList<RentalOrder> rentalsList) {
        ensureDirectoryExists();
        try (FileOutputStream fileOut = new FileOutputStream(RENTALS_FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            for (RentalOrder rental : rentalsList) {
                out.writeObject(rental);
            }
        } catch (IOException e) {
            System.out.println("Error saving rentals: " + e.getMessage());
        }
    }
    
    // ---------- INVOICE METHODS ----------
    
    /**
     * Get all invoices
     */
    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }
    
    /**
     * Add or update an invoice
     */
    public void saveInvoice(Invoice invoice) {
        boolean found = false;
        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).getInvoiceId() == invoice.getInvoiceId()) {
                invoices.set(i, invoice);
                found = true;
                break;
            }
        }
        
        if (!found) {
            invoices.add(invoice);
        }
        
        saveInvoices(invoices);
    }
    
    /**
     * Delete an invoice
     */
    public boolean deleteInvoice(int invoiceId) {
        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).getInvoiceId() == invoiceId) {
                invoices.remove(i);
                saveInvoices(invoices);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Load invoices from file
     */
    private ArrayList<Invoice> loadInvoices() {
        ArrayList<Invoice> result = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(INVOICES_FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            
            while (true) {
                try {
                    Invoice invoice = (Invoice) in.readObject();
                    if (invoice != null) {
                        result.add(invoice);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Info: Could not load invoices file. " + e.getMessage());
        }
        return result;
    }
    
    /**
     * Save invoices to file
     */
    private void saveInvoices(ArrayList<Invoice> invoicesList) {
        ensureDirectoryExists();
        try (FileOutputStream fileOut = new FileOutputStream(INVOICES_FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            for (Invoice invoice : invoicesList) {
                out.writeObject(invoice);
            }
        } catch (IOException e) {
            System.out.println("Error saving invoices: " + e.getMessage());
        }
    }
    
    // ---------- USER METHODS ----------
    
    /**
     * Get all users
     */
    public ArrayList<User> getUsers() {
        return users;
    }
    
    /**
     * Add or update a user
     */
    public void saveUser(User user) {
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(user.getUserName())) {
                users.set(i, user);
                found = true;
                break;
            }
        }
        
        if (!found) {
            users.add(user);
        }
        
        saveUsers(users);
        
        // If the user is a Staff or Customer, also save to the appropriate collection
        if (user instanceof Staff) {
            saveStaffMember((Staff) user);
        } else if (user instanceof Customer) {
            saveCustomer((Customer) user);
        }
    }
    
    /**
     * Load users from file
     */
    private ArrayList<User> loadUsers() {
        ArrayList<User> result = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(USERS_FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            
            while (true) {
                try {
                    User user = (User) in.readObject();
                    if (user != null) {
                        result.add(user);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Info: Could not load users file. " + e.getMessage());
        }
        return result;
    }
    
    /**
     * Save users to file
     */
    public void saveUsers(ArrayList<User> usersList) {
        ensureDirectoryExists();
        try (FileOutputStream fileOut = new FileOutputStream(USERS_FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            for (User user : usersList) {
                out.writeObject(user);
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
    
    
    /**
     * Get all staff members
     */
    public ArrayList<Staff> getStaff() {
        return staff;
    }
    
    /**
     * Add or update a staff member
     */
    public void saveStaffMember(Staff staffMember) {
        boolean found = false;
        for (int i = 0; i < staff.size(); i++) {
            if (staff.get(i).getStaffId() == staffMember.getStaffId()) {
                staff.set(i, staffMember);
                found = true;
                break;
            }
        }
        
        if (!found) {
            staff.add(staffMember);
        }
        
        saveStaff(staff);
        
        // Also update in the users list (if exists)
        boolean userFound = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(staffMember.getUserName())) {
                users.set(i, staffMember);
                userFound = true;
                break;
            }
        }
        
        if (!userFound) {
            users.add(staffMember);
        }
        
        saveUsers(users);
    }
    
    /**
     * Delete a staff member
     */
    public boolean deleteStaffMember(int staffId) {
        Staff toRemove = null;
        
        // Find the staff member
        for (Staff s : staff) {
            if (s.getStaffId() == staffId) {
                toRemove = s;
                break;
            }
        }
        
        if (toRemove != null) {
            // Remove from staff list
            staff.remove(toRemove);
            saveStaff(staff);
            
            // Also remove from users list
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i) instanceof Staff && 
                    ((Staff)users.get(i)).getStaffId() == staffId) {
                    users.remove(i);
                    break;
                }
            }
            saveUsers(users);
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Load staff from file
     */
    private ArrayList<Staff> loadStaff() {
        ArrayList<Staff> result = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(STAFF_FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            
            while (true) {
                try {
                    Staff staffMember = (Staff) in.readObject();
                    if (staffMember != null) {
                        result.add(staffMember);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Info: Could not load staff file. " + e.getMessage());
        }
        
        // Also check the users list for Staff objects to ensure we have all staff
        for (User user : users) {
            if (user instanceof Staff) {
                Staff staffUser = (Staff) user;
                boolean found = false;
                
                for (Staff existingStaff : result) {
                    if (existingStaff.getStaffId() == staffUser.getStaffId()) {
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    result.add(staffUser);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Save staff to file
     */
    private void saveStaff(ArrayList<Staff> staffList) {
        ensureDirectoryExists();
        try (FileOutputStream fileOut = new FileOutputStream(STAFF_FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            for (Staff staffMember : staffList) {
                out.writeObject(staffMember);
            }
        } catch (IOException e) {
            System.out.println("Error saving staff: " + e.getMessage());
        }
    }
    
    // ---------- CUSTOMER METHODS ----------
    
    /**
     * Get all customers
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    
    /**
     * Add or update a customer
     */
    public void saveCustomer(Customer customer) {
        boolean found = false;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == customer.getId()) {
                customers.set(i, customer);
                found = true;
                break;
            }
        }
        
        if (!found) {
            customers.add(customer);
        }
        
        saveCustomers(customers);
        
        // Also update in the users list (if exists)
        boolean userFound = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(customer.getUserName())) {
                users.set(i, customer);
                userFound = true;
                break;
            }
        }
        
        if (!userFound) {
            users.add(customer);
        }
        
        saveUsers(users);
    }
    
    /**
     * Delete a customer
     */
    public boolean deleteCustomer(int customerId) {
        Customer toRemove = null;
        
        // Find the customer
        for (Customer c : customers) {
            if (c.getId() == customerId) {
                toRemove = c;
                break;
            }
        }
        
        if (toRemove != null) {
            // Remove from customers list
            customers.remove(toRemove);
            saveCustomers(customers);
            
            // Also remove from users list
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i) instanceof Customer && 
                    ((Customer)users.get(i)).getId() == customerId) {
                    users.remove(i);
                    break;
                }
            }
            saveUsers(users);
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Load customers from file
     */
    private ArrayList<Customer> loadCustomers() {
        ArrayList<Customer> result = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(CUSTOMERS_FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            
            while (true) {
                try {
                    Customer customer = (Customer) in.readObject();
                    if (customer != null) {
                        result.add(customer);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Info: Could not load customers file. " + e.getMessage());
        }
        
        // Also check the users list for Customer objects to ensure we have all customers
        for (User user : users) {
            if (user instanceof Customer) {
                Customer customerUser = (Customer) user;
                boolean found = false;
                
                for (Customer existingCustomer : result) {
                    if (existingCustomer.getId() == customerUser.getId()) {
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    result.add(customerUser);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Save customers to file
     */
    public static  void saveCustomers(ArrayList<Customer> customersList) {
        ensureDirectoryExists();
        try (FileOutputStream fileOut = new FileOutputStream(CUSTOMERS_FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            for (Customer customer : customersList) {
                out.writeObject(customer);
            }
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }
    
    // ---------- UTILITY METHODS ----------
    
    /**
     * Get next available ID for a specific entity type
     */
    public int getNextId(String entityType) {
        int maxId = 0;
        
        switch (entityType.toLowerCase()) {
            case "event":
                for (Event event : events) {
                    if (event.getEventId() > maxId) {
                        maxId = event.getEventId();
                    }
                }
                break;
            case "equipment":
                for (Equipment item : equipment) {
                    if (item.getEquipmentId() > maxId) {
                        maxId = item.getEquipmentId();
                    }
                }
                break;
            case "rental":
                for (RentalOrder rental : rentals) {
                    if (rental.getRentalId() > maxId) {
                        maxId = rental.getRentalId();
                    }
                }
                break;
            case "invoice":
                for (Invoice invoice : invoices) {
                    if (invoice.getInvoiceId() > maxId) {
                        maxId = invoice.getInvoiceId();
                    }
                }
                break;
            case "staff":
                for (Staff staffMember : staff) {
                    if (staffMember.getStaffId() > maxId) {
                        maxId = staffMember.getStaffId();
                    }
                }
                break;
            case "customer":
                for (Customer customer : customers) {
                    if (customer.getId() > maxId) {
                        maxId = customer.getId();
                    }
                }
                break;
        }
        
        return maxId + 1;
    }
    
    /**
     * Find a user by username
     */
    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Find a staff member by User
     */
    public Staff findStaffByUser(User user) {
        // If the user is a Staff, we can just cast
        if (user instanceof Staff) {
            return (Staff) user;
        }
        
        // Otherwise, search by username or email
        for (Staff s : staff) {
            if (s.getUserName().equals(user.getUserName()) || 
                s.getEmail().equals(user.getEmail())) {
                return s;
            }
        }
        
        return null;
    }
    
    /**
     * Find a customer by User
     */
    public Customer findCustomerByUser(User user) {
        // If the user is a Customer, we can just cast
        if (user instanceof Customer) {
            return (Customer) user;
        }
        
        // Otherwise, search by username or email
        for (Customer c : customers) {
            if (c.getUserName().equals(user.getUserName()) || 
                c.getEmail().equals(user.getEmail())) {
                return c;
            }
        }
        
        return null;
    }
}