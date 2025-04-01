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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import serialization.Customer;
import serialization.DataManager;
import serialization.Date;
import serialization.Event;

/**
 * GUI for managing events
 */
public class EventManagementGUI extends JFrame implements ActionListener {
    // Components
    private JPanel mainPanel;
    private JTable eventsTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton viewRentalsButton;
    private JButton backButton;
    
    // Data manager
    private DataManager dataManager;
    
    // Colors
    private final Color PRIMARY_COLOR = new Color(0, 112, 116);
    private final Color SECONDARY_COLOR = new Color(255, 100, 50);
    private final Color NEUTRAL_COLOR = new Color(70, 70, 70);
    
    // Table column names
    private final String[] columnNames = {
        "ID", "Customer ID", "Event Name", "Date", "Location"
    };
    
    /**
     * Constructor
     */
    public EventManagementGUI() {
        this.setTitle("Event Management");
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        // Get data manager instance
        dataManager = DataManager.getInstance();
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        this.setVisible(true);
    }
    
    /**
     * Initialize all UI components
     */
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Get events from data manager
        ArrayList<Event> eventsList = dataManager.getEvents();
        
        // Create the table model
        Object[][] data = new Object[eventsList.size()][5];
        for (int i = 0; i < eventsList.size(); i++) {
            Event event = eventsList.get(i);
            data[i][0] = event.getEventId();
            data[i][1] = event.getCustomerId();
            data[i][2] = event.getEventName();
            // Format the date
            Date eventDate = event.getEventDate();
            data[i][3] = eventDate.getDay() + "/" + eventDate.getMonth() + "/" + eventDate.getYear();
            data[i][4] = event.getEventLocation();
        }
        
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        eventsTable = new JTable(tableModel);
        eventsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        eventsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        eventsTable.setRowHeight(30);
        eventsTable.setAutoCreateRowSorter(true);
        
        // Create buttons
        addButton = createStyledButton("Add Event", SECONDARY_COLOR);
        editButton = createStyledButton("Edit Event", PRIMARY_COLOR);
        deleteButton = createStyledButton("Delete Event", NEUTRAL_COLOR);
        viewRentalsButton = createStyledButton("View Rentals", PRIMARY_COLOR);
        backButton = createStyledButton("Back", NEUTRAL_COLOR);
    }
    
    /**
     * Set up the layout of all components
     */
    private void setupLayout() {
        // Header panel with title and back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Event Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewRentalsButton);
        
        // Top panel combining header and buttons
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(eventsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        // Add components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        this.setContentPane(mainPanel);
    }
    
    /**
     * Set up all event handlers
     */
    private void setupEventHandlers() {
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        viewRentalsButton.addActionListener(this);
        backButton.addActionListener(this);
    }
    
    /**
     * Creates a styled button with the given text and color
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == addButton) {
            showEventDialog(null); // null for new event
        } else if (source == editButton) {
            int selectedRow = eventsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int eventId = (int) eventsTable.getValueAt(selectedRow, 0);
                Event selectedEvent = getEventById(eventId);
                if (selectedEvent != null) {
                    showEventDialog(selectedEvent);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select an event to edit.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == deleteButton) {
            int selectedRow = eventsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int eventId = (int) eventsTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this event?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
                    
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteEvent(eventId);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select an event to delete.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == viewRentalsButton) {
            int selectedRow = eventsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int eventId = (int) eventsTable.getValueAt(selectedRow, 0);
                // Show rentals for the selected event
                // This would typically open a filtered view of the RentalManagementGUI
                JOptionPane.showMessageDialog(this, 
                    "View rentals for Event ID " + eventId + " (to be implemented)", 
                    "Information", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select an event to view rentals.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == backButton) {
            // Go back to previous screen
            this.dispose();
            new WelcomeGUI();
        }
    }
    
    /**
     * Find event by ID
     *  eventId The event ID to find
     *  The event object or null if not found
     */
    private Event getEventById(int eventId) {
        ArrayList<Event> events = dataManager.getEvents();
        for (Event event : events) {
            if (event.getEventId() == eventId) {
                return event;
            }
        }
        return null;
    }
    
    /**
     * Delete event
     *  eventId The event ID to delete
     */
    private void deleteEvent(int eventId) {
        boolean success = dataManager.deleteEvent(eventId);
        
        if (success) {
            // Find the row with this event ID
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if ((int)tableModel.getValueAt(i, 0) == eventId) {
                    tableModel.removeRow(i);
                    break;
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                "Event deleted successfully.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to delete event.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Show dialog for adding or editing event
     *  event Event to edit, or null for new event
     */
    private void showEventDialog(Event event) {
        final boolean isNewEvent = (event == null);
        final JDialog dialog = new JDialog(this, isNewEvent ? "Add Event" : "Edit Event", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 10);
        
        // Event ID (for editing only)
        JTextField idField = new JTextField(10);
        if (!isNewEvent) {
            panel.add(new JLabel("Event ID:"), gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            idField.setText(String.valueOf(event.getEventId()));
            idField.setEditable(false);
            panel.add(idField, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.anchor = GridBagConstraints.EAST;
        }
        
        // Customer selector
        panel.add(new JLabel("Customer:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Get customers from data manager
        ArrayList<Customer> customersList = dataManager.getCustomers();
        
        // Create array of customer names for the dropdown
        String[] customerNames = new String[customersList.size()];
        for (int i = 0; i < customersList.size(); i++) {
            Customer customer = customersList.get(i);
            customerNames[i] = customer.getId() + " - " + customer.getFirstName() + " " + customer.getLastName();
        }
        
        JComboBox<String> customerBox = new JComboBox<>(customerNames);
        
        if (!isNewEvent) {
            // Select the current customer
            for (int i = 0; i < customersList.size(); i++) {
                if (customersList.get(i).getId() == event.getCustomerId()) {
                    customerBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        panel.add(customerBox, gbc);
        
        // Event name field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Event Name:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField nameField = new JTextField(20);
        if (!isNewEvent) {
            nameField.setText(event.getEventName());
        }
        panel.add(nameField, gbc);
        
        // Event date fields
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Date (DD/MM/YYYY):"), gbc);
        
        // Panel for date fields
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JTextField dayField = new JTextField(2);
        JTextField monthField = new JTextField(2);
        JTextField yearField = new JTextField(4);
        
        if (!isNewEvent) {
            Date eventDate = event.getEventDate();
            dayField.setText(String.valueOf(eventDate.getDay()));
            monthField.setText(String.valueOf(eventDate.getMonth()));
            yearField.setText(String.valueOf(eventDate.getYear()));
        }
        
        datePanel.add(dayField);
        datePanel.add(new JLabel("/"));
        datePanel.add(monthField);
        datePanel.add(new JLabel("/"));
        datePanel.add(yearField);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(datePanel, gbc);
        
        // Event location field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField locationField = new JTextField(20);
        if (!isNewEvent) {
            locationField.setText(event.getEventLocation());
        }
        panel.add(locationField, gbc);
        
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
                if (nameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Event name cannot be empty.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (locationField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Location cannot be empty.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Parse date
                int day, month, year;
                try {
                    day = Integer.parseInt(dayField.getText().trim());
                    month = Integer.parseInt(monthField.getText().trim());
                    year = Integer.parseInt(yearField.getText().trim());
                    
                    if (day < 1 || day > 31 || month < 1 || month > 12 || year < 2000) {
                        throw new NumberFormatException("Invalid date values");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Please enter valid date values.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create Date object
                Date eventDate = new Date(day, month, year);
                
                // Get selected customer ID
                String selectedCustomer = (String) customerBox.getSelectedItem();
                int customerId = Integer.parseInt(selectedCustomer.split(" - ")[0]);
                
                // Save or update event
                if (isNewEvent) {
                    // Create new event with next available ID
                    int newId = dataManager.getNextId("event");
                    Event newEvent = new Event(
                        newId,
                        customerId,
                        nameField.getText().trim(),
                        locationField.getText().trim(),
                        eventDate
                    );
                    
                    // Save to data manager
                    dataManager.saveEvent(newEvent);
                    
                    // Add to table
                    Object[] rowData = {
                        newId,
                        customerId,
                        newEvent.getEventName(),
                        day + "/" + month + "/" + year,
                        newEvent.getEventLocation()
                    };
                    
                    tableModel.addRow(rowData);
                } else {
                    // Update existing event
                    int id = Integer.parseInt(idField.getText());
                    Event existingEvent = getEventById(id);
                    
                    if (existingEvent != null) {
                        existingEvent.setCustomerId(customerId);
                        existingEvent.setEventName(nameField.getText().trim());
                        existingEvent.setEventLocation(locationField.getText().trim());
                        existingEvent.setEventDate(eventDate);
                        
                        // Save to data manager
                        dataManager.saveEvent(existingEvent);
                        
                        // Update table
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            if ((int)tableModel.getValueAt(i, 0) == id) {
                                tableModel.setValueAt(customerId, i, 1);
                                tableModel.setValueAt(existingEvent.getEventName(), i, 2);
                                tableModel.setValueAt(day + "/" + month + "/" + year, i, 3);
                                tableModel.setValueAt(existingEvent.getEventLocation(), i, 4);
                                break;
                            }
                        }
                    }
                }
                
                dialog.dispose();
                JOptionPane.showMessageDialog(EventManagementGUI.this, 
                    isNewEvent ? "Event added successfully." : "Event updated successfully.",
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
     * Main method for testing
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventManagementGUI());
    }
}