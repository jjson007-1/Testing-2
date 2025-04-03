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
    private static final String CUSTOMERS_FILE = DATA_DIRECTORY + "CustomersDB.ser";
    
    // Singleton instance
    private static DataManager instance;
    
    // Data collections
    private ArrayList<Event> events;
    private ArrayList<Equipment> equipment;
    private ArrayList<RentalOrder> rentals;
    private ArrayList<Invoice> invoices;
    private ArrayList<User> users;
    private ArrayList<Customer> customers;
    
    /**
     * Private constructor for singleton pattern
     */
    private DataManager() {
        // Initialize data collections
        events = new ArrayList<>();
        equipment = new ArrayList<>();
        rentals = new ArrayList<>();
        invoices = new ArrayList<>();
        users = new ArrayList<>();
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
     * @return List of events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }
    
    /**
     * Add or update an event
     * @param event The event to add or update
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
     * @param eventId The ID of the event to delete
     * @return true if deleted, false if not found
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
     * @return List of events
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
     * @param eventsList List of events to save
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
     * @return List of equipment
     */
    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }
    
    /**
     * Add or update equipment
     * @param item The equipment to add or update
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
     * @param equipmentId The ID of the equipment to delete
     * @return true if deleted, false if not found
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
     * @return List of equipment
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
     * @param equipmentList List of equipment to save
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
     * @return List of rental orders
     */
    public ArrayList<RentalOrder> getRentals() {
        return rentals;
    }
    
    /**
     * Add or update a rental order
     * @param rental The rental order to add or update
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
     * @param rentalId The ID of the rental order to delete
     * @return true if deleted, false if not found
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
     * @return List of rental orders
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
     * @param rentalsList List of rental orders to save
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
     * @return List of invoices
     */
    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }
    
    /**
     * Add or update an invoice
     * @param invoice The invoice to add or update
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
     * @param invoiceId The ID of the invoice to delete
     * @return true if deleted, false if not found
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
     * @return List of invoices
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
     * @param invoicesList List of invoices to save
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
     * @return List of users
     */
    public ArrayList<User> getUsers() {
        return users;
    }
    
    /**
     * Load users from file
     * @return List of users
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
     * @param usersList List of users to save
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
    
    // ---------- CUSTOMER METHODS ----------
    
    /**
     * Get all customers
     * @return List of customers
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    
    /**
     * Load customers from file
     * @return List of customers
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
        return result;
    }
    
    /**
     * Save customers to file
     * @param customersList List of customers to save
     */
    public static void saveCustomers(ArrayList<Customer> customersList) {
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
     * @param entityType The type of entity ("event", "equipment", "rental", "invoice")
     * @return The next available ID
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
}