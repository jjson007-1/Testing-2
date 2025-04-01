package GUIs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import serialization.Customer;
import serialization.Date;
import serialization.Equipment;
import serialization.Event;
import serialization.RentalOrder;

/**
 * GUI for managing rental orders
 */
public class RentalManagementGUI extends JFrame implements ActionListener {
    // Components
    private JPanel mainPanel;
    private JTable rentalsTable;
    private JTable equipmentTable;
    private DefaultTableModel rentalsTableModel;
    private DefaultTableModel equipmentTableModel;
    private JButton createRentalButton;
    private JButton editRentalButton;
    private JButton deleteRentalButton;
    private JButton generateInvoiceButton;
    private JButton backButton;
    
    // Data
    private ArrayList<RentalOrder> rentalsList;
    private ArrayList<Equipment> equipmentList;
    private ArrayList<Event> eventsList;
    private ArrayList<Customer> customersList;
    
    // Currently selected rental
    private RentalOrder selectedRental;
    
    // Colors (matching existing GUIs)
    private final Color PRIMARY_COLOR = new Color(0, 112, 116);    // Teal
    private final Color SECONDARY_COLOR = new Color(255, 100, 50); // Orange
    private final Color NEUTRAL_COLOR = new Color(70, 70, 70);     // Dark Gray
    
    /**
     * Constructor
     */
    public RentalManagementGUI() {
        this.setTitle("Rental Management");
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        // Load data
        loadData();
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        this.setVisible(true);
    }
    
    /**
     * Load all necessary data
     */
    private void loadData() {
        loadRentalsData();
        loadEquipmentData();
        loadEventsData();
        loadCustomersData();
    }
    
    /**
     * Load rental orders data
     */
    private void loadRentalsData() {
        // This would normally load from serialized file
        // For now, create sample data
        rentalsList = new ArrayList<>();
        
        // Create sample rental orders with our Date class
        Date rentalDate1 = new Date(8, 3, 2025);
        Date returnDate1 = new Date(11, 3, 2025);
        Date rentalDate2 = new Date(18, 4, 2025);
        Date returnDate2 = new Date(21, 4, 2025);
        
        rentalsList.add(new RentalOrder(1, 1, 1236, rentalDate1, returnDate1, "Confirmed", 200.0));
        rentalsList.add(new RentalOrder(2, 2, 2546, rentalDate2, returnDate2, "Pending", 500.0));
    }
    
    /**
     * Load equipment data
     */
    private void loadEquipmentData() {
        // This would normally load from serialized file
        // For now, create sample data
        equipmentList = new ArrayList<>();
        
        equipmentList.add(new Equipment(1, "Speakers", "High-quality sound system", 50.0, "Available", "Good"));
        equipmentList.add(new Equipment(2, "Projector", "4K HD projector", 80.0, "Available", "Good"));
        equipmentList.add(new Equipment(3, "Tables", "Round tables for seating", 15.0, "Available", "Good"));
        equipmentList.add(new Equipment(4, "Chairs", "Comfortable seating", 5.0, "Available", "Good"));
    }
    
    /**
     * Load events data
     */
    private void loadEventsData() {
        // This would normally load from serialized file
        // For now, create sample data
        eventsList = new ArrayList<>();
        
        eventsList.add(new Event(1, 1, "Birthday Party", "Skating Ranger", new Date(10, 3, 2025)));
        eventsList.add(new Event(2, 2, "Wedding Reception", "Pauls Chapel", new Date(20, 4, 2025)));
        eventsList.add(new Event(3, 3, "Corporate Event", "LHN Headquarters", new Date(15, 5, 2025)));
    }
    
    /**
     * Load customers data
     */
    private void loadCustomersData() {
        // This would normally load from serialized file
        // For now, create sample data
        customersList = new ArrayList<>();
        
        customersList.add(new Customer("John Doe", "123-456-7890", "johndoe@example.com"));
        customersList.add(new Customer("Jane Smith", "234-567-8901", "janesmith@example.com"));
        
        // Set IDs manually for the sample data
        customersList.get(0).setId(1);
        customersList.get(1).setId(2);
    }
    
    /**
     * Initialize all UI components
     */
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create the rentals table model
        Object[][] rentalsData = new Object[rentalsList.size()][7];
        for (int i = 0; i < rentalsList.size(); i++) {
            RentalOrder rental = rentalsList.get(i);
            rentalsData[i][0] = rental.getRentalId();
            rentalsData[i][1] = getEventNameById(rental.getEventId());
            rentalsData[i][2] = getCustomerNameById(rental.getCustomerId());
            
            // Format the dates
            serialization.Date rentalDate = rental.getRentalDate();
            serialization.Date returnDate = rental.getReturnDate();
            
            rentalsData[i][3] = String.format("%02d/%02d/%04d", 
                rentalDate.getDay(), rentalDate.getMonth(), rentalDate.getYear());
            rentalsData[i][4] = String.format("%02d/%02d/%04d", 
                returnDate.getDay(), returnDate.getMonth(), returnDate.getYear());
            
            rentalsData[i][5] = rental.getRentStatus();
            rentalsData[i][6] = String.format("$%.2f", rental.getTotalPrice());
        }
        
        String[] rentalsColumns = {"ID", "Event", "Customer", "Rental Date", "Return Date", "Status", "Total"};
        
        rentalsTableModel = new DefaultTableModel(rentalsData, rentalsColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        rentalsTable = new JTable(rentalsTableModel);
        rentalsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rentalsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        rentalsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        rentalsTable.setRowHeight(30);
        rentalsTable.setAutoCreateRowSorter(true);
        
        // Create the equipment table (initially empty)
        String[] equipmentColumns = {"ID", "Name", "Description", "Daily Rate", "Quantity", "Subtotal"};
        Object[][] emptyData = new Object[0][6];
        equipmentTableModel = new DefaultTableModel(emptyData, equipmentColumns);
        
        equipmentTable = new JTable(equipmentTableModel);
        equipmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        equipmentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        equipmentTable.setRowHeight(30);
        
        // Create buttons
        createRentalButton = createStyledButton("Create Rental", SECONDARY_COLOR);
        editRentalButton = createStyledButton("Edit Rental", PRIMARY_COLOR);
        deleteRentalButton = createStyledButton("Delete Rental", NEUTRAL_COLOR);
        generateInvoiceButton = createStyledButton("Generate Invoice", PRIMARY_COLOR);
        backButton = createStyledButton("Back", NEUTRAL_COLOR);
    }
    
    /**
     * Set up the layout of all components
     */
    private void setupLayout() {
        // Header panel with title and back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Rental Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.add(createRentalButton);
        buttonPanel.add(editRentalButton);
        buttonPanel.add(deleteRentalButton);
        buttonPanel.add(generateInvoiceButton);
        
        // Top panel combining header and buttons
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Rentals panel with table
        JPanel rentalsPanel = new JPanel(new BorderLayout());
        rentalsPanel.setBorder(BorderFactory.createTitledBorder("Rental Orders"));
        JScrollPane rentalsScrollPane = new JScrollPane(rentalsTable);
        rentalsPanel.add(rentalsScrollPane, BorderLayout.CENTER);
        
        // Equipment panel with table (for selected rental's equipment)
        JPanel equipmentPanel = new JPanel(new BorderLayout());
        equipmentPanel.setBorder(BorderFactory.createTitledBorder("Equipment for Selected Rental"));
        JScrollPane equipmentScrollPane = new JScrollPane(equipmentTable);
        equipmentPanel.add(equipmentScrollPane, BorderLayout.CENTER);
        
        // Split pane for rentals and equipment
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            rentalsPanel,
            equipmentPanel
        );
        splitPane.setDividerLocation(350);
        splitPane.setResizeWeight(0.5);
        
        // Add components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        this.setContentPane(mainPanel);
    }
    
    /**
     * Set up all event handlers
     */
    private void setupEventHandlers() {
        createRentalButton.addActionListener(this);
        editRentalButton.addActionListener(this);
        deleteRentalButton.addActionListener(this);
        generateInvoiceButton.addActionListener(this);
        backButton.addActionListener(this);
        
        // Add selection listener to rentals table
        rentalsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = rentalsTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        int rentalId = (int) rentalsTable.getValueAt(selectedRow, 0);
                        selectedRental = getRentalById(rentalId);
                        displayRentalEquipment(selectedRental);
                    } else {
                        clearEquipmentTable();
                    }
                }
            }
        });
    }
    
    /**
     * Creates a styled button with the given text and color
     *  text The button text
     *  color The button background color
     * @return A styled JButton
     */
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(color);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    /**
     * Display equipment for the selected rental
     *  rental The selected rental order
     */
    private void displayRentalEquipment(RentalOrder rental) {
        // Clear the equipment table
        clearEquipmentTable();
        
        if (rental == null) return;
        
        // In a real implementation, this would fetch the actual equipment items
        // associated with the rental from a database or serialized file.
        // For now, we'll show sample data
        
        // Clear the table and add sample data
        for (int i = 0; i < 3 && i < equipmentList.size(); i++) {
            Equipment equip = equipmentList.get(i);
            int quantity = i + 1;
            double subtotal = equip.getRentalPrice() * quantity;
            
            Object[] rowData = {
                equip.getEquipmentId(),
                equip.getName(),
                equip.getDescription(),
                String.format("$%.2f", equip.getRentalPrice()),
                quantity,
                String.format("$%.2f", subtotal)
            };
            
            equipmentTableModel.addRow(rowData);
        }
    }
    
    /**
     * Clear the equipment table
     */
    private void clearEquipmentTable() {
        while (equipmentTableModel.getRowCount() > 0) {
            equipmentTableModel.removeRow(0);
        }
    }
    
    /**
     * Find rental order by ID
     *  rentalId The rental ID to find
     * @return The rental order object or null if not found
     */
    private RentalOrder getRentalById(int rentalId) {
        for (RentalOrder rental : rentalsList) {
            if (rental.getRentalId() == rentalId) {
                return rental;
            }
        }
        return null;
    }
    
    /**
     * Find event by ID
     *  eventId The event ID to find
     * @return The event name or "Unknown" if not found
     */
    private String getEventNameById(int eventId) {
        for (Event event : eventsList) {
            if (event.getEventId() == eventId) {
                return event.getEventName();
            }
        }
        return "Unknown Event";
    }
    
    /**
     * Find customer by ID
     *  customerId The customer ID to find
     * @return The customer name or "Unknown" if not found
     */
    private String getCustomerNameById(int customerId) {
        for (Customer customer : customersList) {
            if (customer.getId() == customerId) {
                return customer.getFirstName() + " " + customer.getLastName();
            }
        }
        return "Unknown Customer";
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == createRentalButton) {
            showRentalDialog(null); // null for new rental
        } else if (source == editRentalButton) {
            int selectedRow = rentalsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int rentalId = (int) rentalsTable.getValueAt(selectedRow, 0);
                RentalOrder selectedRental = getRentalById(rentalId);
                if (selectedRental != null) {
                    showRentalDialog(selectedRental);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select a rental to edit.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == deleteRentalButton) {
            int selectedRow = rentalsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int rentalId = (int) rentalsTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this rental?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
                    
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteRental(rentalId);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select a rental to delete.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == generateInvoiceButton) {
            int selectedRow = rentalsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int rentalId = (int) rentalsTable.getValueAt(selectedRow, 0);
                // TODO: Generate invoice for this rental
                JOptionPane.showMessageDialog(this, 
                    "Invoice generation for Rental ID " + rentalId + " will be implemented.", 
                    "Information", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select a rental to generate an invoice.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == backButton) {
            // Go back to previous screen
            this.dispose();
            // For demo purposes, show welcome screen
            new WelcomeGUI();
        }
    }
    
    /**
     * Delete rental with confirmation
     *  rentalId The rental ID to delete
     */
    private void deleteRental(int rentalId) {
        // Find the rental to delete
        RentalOrder toDelete = null;
        int index = -1;
        
        for (int i = 0; i < rentalsList.size(); i++) {
            if (rentalsList.get(i).getRentalId() == rentalId) {
                toDelete = rentalsList.get(i);
                index = i;
                break;
            }
        }
        
        if (toDelete != null && index >= 0) {
            // Remove from list
            rentalsList.remove(index);
            
            // Update table
            int modelRow = rentalsTable.convertRowIndexToModel(rentalsTable.getSelectedRow());
            rentalsTableModel.removeRow(modelRow);
            
            // Clear equipment table
            clearEquipmentTable();
            
            // Save changes (to be implemented)
            
            JOptionPane.showMessageDialog(this, 
                "Rental deleted successfully.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Show dialog for adding or editing rental
     *  rental Rental to edit, or null for new rental
     */
    private void showRentalDialog(RentalOrder rental) {
        final boolean isNewRental = (rental == null);
        final JDialog dialog = new JDialog(this, isNewRental ? "Create Rental" : "Edit Rental", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 10);
        
        // Rental ID (for editing only)
        JTextField idField = new JTextField(10);
        if (!isNewRental) {
            panel.add(new JLabel("Rental ID:"), gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            idField.setText(String.valueOf(rental.getRentalId()));
            idField.setEditable(false);
            panel.add(idField, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.anchor = GridBagConstraints.EAST;
        }
        
        // Event selector
        panel.add(new JLabel("Event:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Create array of event names for the dropdown
        String[] eventNames = new String[eventsList.size()];
        for (int i = 0; i < eventsList.size(); i++) {
            Event event = eventsList.get(i);
            eventNames[i] = event.getEventId() + " - " + event.getEventName();
        }
        
        JComboBox<String> eventBox = new JComboBox<>(eventNames);
        
        if (!isNewRental) {
            // Select the current event
            for (int i = 0; i < eventsList.size(); i++) {
                if (eventsList.get(i).getEventId() == rental.getEventId()) {
                    eventBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        panel.add(eventBox, gbc);
        
        // Customer selector
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Customer:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Create array of customer names for the dropdown
        String[] customerNames = new String[customersList.size()];
        for (int i = 0; i < customersList.size(); i++) {
            Customer customer = customersList.get(i);
            customerNames[i] = customer.getId() + " - " + customer.getFirstName() + " " + customer.getLastName();
        }
        
        JComboBox<String> customerBox = new JComboBox<>(customerNames);
        
        if (!isNewRental) {
            // Select the current customer
            for (int i = 0; i < customersList.size(); i++) {
                if (customersList.get(i).getId() == rental.getCustomerId()) {
                    customerBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        panel.add(customerBox, gbc);
        
        // Rental date fields
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Rental Date (DD/MM/YYYY):"), gbc);
        
        // Panel for rental date fields
        JPanel rentalDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JTextField rentalDayField = new JTextField(2);
        JTextField rentalMonthField = new JTextField(2);
        JTextField rentalYearField = new JTextField(4);
        
        if (!isNewRental) {
            Date rentalDate = rental.getRentalDate();
            rentalDayField.setText(String.valueOf(rentalDate.getDay()));
            rentalMonthField.setText(String.valueOf(rentalDate.getMonth()));
            rentalYearField.setText(String.valueOf(rentalDate.getYear()));
        }
        
        rentalDatePanel.add(rentalDayField);
        rentalDatePanel.add(new JLabel("/"));
        rentalDatePanel.add(rentalMonthField);
        rentalDatePanel.add(new JLabel("/"));
        rentalDatePanel.add(rentalYearField);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(rentalDatePanel, gbc);
        
        // Return date fields
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Return Date (DD/MM/YYYY):"), gbc);
        
        // Panel for return date fields
        JPanel returnDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JTextField returnDayField = new JTextField(2);
        JTextField returnMonthField = new JTextField(2);
        JTextField returnYearField = new JTextField(4);
        
        if (!isNewRental) {
            Date returnDate = rental.getReturnDate();
            returnDayField.setText(String.valueOf(returnDate.getDay()));
            returnMonthField.setText(String.valueOf(returnDate.getMonth()));
            returnYearField.setText(String.valueOf(returnDate.getYear()));
        }
        
        returnDatePanel.add(returnDayField);
        returnDatePanel.add(new JLabel("/"));
        returnDatePanel.add(returnMonthField);
        returnDatePanel.add(new JLabel("/"));
        returnDatePanel.add(returnYearField);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(returnDatePanel, gbc);
        
        // Status field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        String[] statusOptions = {"Pending", "Confirmed", "Completed", "Cancelled"};
        JComboBox<String> statusBox = new JComboBox<>(statusOptions);
        
        if (!isNewRental) {
            statusBox.setSelectedItem(rental.getRentStatus());
        }
        
        panel.add(statusBox, gbc);
        
        // Total price field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Total Price:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pricePanel.add(new JLabel("$"));
        JTextField priceField = new JTextField(10);
        
        if (!isNewRental) {
            priceField.setText(String.format("%.2f", rental.getTotalPrice()));
        }
        
        pricePanel.add(priceField);
        panel.add(pricePanel, gbc);
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton saveButton = createStyledButton("Save", SECONDARY_COLOR);
        JButton cancelButton = createStyledButton("Cancel", NEUTRAL_COLOR);
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate fields
                if (priceField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Price cannot be empty.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Parse rental date
                int rentalDay, rentalMonth, rentalYear;
                try {
                    rentalDay = Integer.parseInt(rentalDayField.getText().trim());
                    rentalMonth = Integer.parseInt(rentalMonthField.getText().trim());
                    rentalYear = Integer.parseInt(rentalYearField.getText().trim());
                    
                    if (rentalDay < 1 || rentalDay > 31 || rentalMonth < 1 || rentalMonth > 12 || rentalYear < 2000) {
                        throw new NumberFormatException("Invalid rental date values");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Please enter valid rental date values.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Parse return date
                int returnDay, returnMonth, returnYear;
                try {
                    returnDay = Integer.parseInt(returnDayField.getText().trim());
                    returnMonth = Integer.parseInt(returnMonthField.getText().trim());
                    returnYear = Integer.parseInt(returnYearField.getText().trim());
                    
                    if (returnDay < 1 || returnDay > 31 || returnMonth < 1 || returnMonth > 12 || returnYear < 2000) {
                        throw new NumberFormatException("Invalid return date values");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Please enter valid return date values.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Parse price
                double price;
                try {
                    price = Double.parseDouble(priceField.getText().trim());
                    if (price < 0) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Price must be a positive number.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Please enter a valid price.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create Date objects
                Date rentalDate = new Date(rentalDay, rentalMonth, rentalYear);
                Date returnDate = new Date(returnDay, returnMonth, returnYear);
                
                // Get selected event ID
                String selectedEvent = (String) eventBox.getSelectedItem();
                int eventId = Integer.parseInt(selectedEvent.split(" - ")[0]);
                
                // Get selected customer ID
                String selectedCustomer = (String) customerBox.getSelectedItem();
                int customerId = Integer.parseInt(selectedCustomer.split(" - ")[0]);
                
                // Get selected status
                String status = (String) statusBox.getSelectedItem();
                
                // Save or update rental
                if (isNewRental) {
                    // Create new rental
                    int newId = getNextRentalId();
                    RentalOrder newRental = new RentalOrder(
                        newId,
                        eventId,
                        customerId,
                        rentalDate,
                        returnDate,
                        status,
                        price
                    );
                    
                    // Add to list and table
                    rentalsList.add(newRental);
                    
                    Object[] rowData = {
                        newId,
                        getEventNameById(eventId),
                        getCustomerNameById(customerId),
                        String.format("%02d/%02d/%04d", rentalDay, rentalMonth, rentalYear),
                        String.format("%02d/%02d/%04d", returnDay, returnMonth, returnYear),
                        status,
                        String.format("$%.2f", price)
                    };
                    
                    rentalsTableModel.addRow(rowData);
                } else {
                    // Update existing rental
                    int id = Integer.parseInt(idField.getText());
                    RentalOrder existingRental = getRentalById(id);
                    
                    if (existingRental != null) {
                        existingRental.setEventId(eventId);
                        existingRental.setCustomerId(customerId);
                        existingRental.setRentalDate(rentalDate);
                        existingRental.setReturnDate(returnDate);
                        existingRental.setRentStatus(status);
                        existingRental.setTotalPrice(price);
                        
                        // Update table
                        int modelRow = rentalsTable.convertRowIndexToModel(rentalsTable.getSelectedRow());
                        rentalsTableModel.setValueAt(getEventNameById(eventId), modelRow, 1);
                        rentalsTableModel.setValueAt(getCustomerNameById(customerId), modelRow, 2);
                        rentalsTableModel.setValueAt(
                            String.format("%02d/%02d/%04d", rentalDay, rentalMonth, rentalYear), 
                            modelRow, 3
                        );
                        rentalsTableModel.setValueAt(
                            String.format("%02d/%02d/%04d", returnDay, returnMonth, returnYear), 
                            modelRow, 4
                        );
                        rentalsTableModel.setValueAt(status, modelRow, 5);
                        rentalsTableModel.setValueAt(String.format("$%.2f", price), modelRow, 6);
                    }
                }
                
                // Save changes (to be implemented with serialization)
                // RentalOrder.saveRentalOrders(rentalsList);
                
                dialog.dispose();
                JOptionPane.showMessageDialog(RentalManagementGUI.this, 
                    isNewRental ? "Rental created successfully." : "Rental updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
    
    /**
     * Get next available rental ID
     * @return The next available ID
     */
    private int getNextRentalId() {
        int maxId = 0;
        for (RentalOrder rental : rentalsList) {
            if (rental.getRentalId() > maxId) {
                maxId = rental.getRentalId();
            }
        }
        return maxId + 1;
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RentalManagementGUI());
    }
}