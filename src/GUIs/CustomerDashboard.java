package GUIs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
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
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


import serialization.Customer;
import serialization.DataManager;
import serialization.Date;
import serialization.Event;
import serialization.RentalOrder;
import GUIs.EventManagementGUI;

/**
 * Dashboard for customers to manage their events and rentals.
 */
public class CustomerDashboard extends BaseGUI implements ActionListener {
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    // Sidebar buttons
    private JButton eventsButton;
    private JButton rentalsButton;
    private JButton invoicesButton;
    private JButton profileButton;
    private JButton logoutButton;
    private JButton editProfileButton;
    
    // Content panels
    private JPanel eventsPanel;
    private JPanel rentalsPanel;
    private JPanel invoicesPanel;
    private JPanel profilePanel;
    
    // Tables
    private JTable eventsTable;
    private JTable rentalsTable;
    private JTable invoicesTable;
    
    // Action buttons
    private JButton createEventButton;
    private JButton editEventButton;
    private JButton deleteEventButton;
    private JButton createRentalButton;
    
    // Current customer
    private Customer currentCustomer;
    
    /**
     * Constructor
     *  customerId The ID of the logged-in customer
     */
    public CustomerDashboard(int customerId) {
        super("Customer Dashboard", 900, 600);
        
        loadCustomerData(customerId);
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        this.setVisible(true);
    }
    
    /**
     * Load customer data from storage
     *  customerId The customer ID to load
     */
    private void loadCustomerData(int customerId) {
        currentCustomer = new Customer();
        currentCustomer.setId(customerId);
        currentCustomer.setFullName("John");
        currentCustomer.setEmail("john.doe@example.com");
        currentCustomer.setPhone(1234567890);
    }
    
    @Override
    protected void initComponents() {
        // Main panel with border layout
        mainPanel = new JPanel(new BorderLayout());
        
        // Sidebar panel
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(PRIMARY_COLOR);
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sidebarPanel.setPreferredSize(new Dimension(200, 0));
        
        // Content panel with card layout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create sidebar buttons
        eventsButton = createSidebarButton("My Events");
        rentalsButton = createSidebarButton("My Rentals");
        invoicesButton = createSidebarButton("My Invoices");
        profileButton = createSidebarButton("My Profile");
        logoutButton = createSidebarButton("Logout");
        
        // Create content panels
        createEventsPanel();
        createRentalsPanel();
        createInvoicesPanel();
        createProfilePanel();
        
        // Add content panels to card layout
        contentPanel.add(eventsPanel, "events");
        contentPanel.add(rentalsPanel, "rentals");
        contentPanel.add(invoicesPanel, "invoices");
        contentPanel.add(profilePanel, "profile");
    }
    
    @Override
    protected void setupLayout() {
        // Add welcome label to sidebar
        JLabel welcomeLabel = new JLabel("Welcome, " + currentCustomer.getFullName());
        welcomeLabel.setForeground(LIGHT_COLOR);
        welcomeLabel.setFont(TITLE_FONT);
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
        sidebarPanel.add(welcomeLabel);
        
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // Add buttons to sidebar
        sidebarPanel.add(eventsButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(rentalsButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(invoicesButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(profileButton);
        
        // Add spacer and logout at bottom
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(logoutButton);
        
        // Add panels to main layout
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        this.setContentPane(mainPanel);
    }
    
    @Override
    protected void setupEventHandlers() {
        // Add action listeners to sidebar buttons
        eventsButton.addActionListener(this);
        rentalsButton.addActionListener(this);
        invoicesButton.addActionListener(this);
        profileButton.addActionListener(this);
        logoutButton.addActionListener(this);
        
        
        // Add action listeners to action buttons
        createEventButton.addActionListener(this);
        editEventButton.addActionListener(this);
        deleteEventButton.addActionListener(this);
        createRentalButton.addActionListener(this);
        editProfileButton.addActionListener(this);

    }
    
    /**
     * Creates a styled button for the sidebar
     *  text The button text
     */
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(LIGHT_COLOR);
        button.setBackground(PRIMARY_COLOR);
        button.setFont(NORMAL_FONT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(180, 40));
        return button;
    }
    
    /**
     * Creates the events panel
     */
    private void createEventsPanel() {
        eventsPanel = new JPanel(new BorderLayout(10, 10));
        
        // Header panel with title and buttons
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("My Events");
        titleLabel.setFont(HEADER_FONT);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        createEventButton = createStyledButton("Create Event", SECONDARY_COLOR, this);
        editEventButton = createStyledButton("Edit Event", PRIMARY_COLOR, this);
        deleteEventButton = createStyledButton("Delete Event", NEUTRAL_COLOR, this);
        
        buttonsPanel.add(createEventButton);
        buttonsPanel.add(editEventButton);
        buttonsPanel.add(deleteEventButton);
        headerPanel.add(buttonsPanel, BorderLayout.EAST);
        
        eventsPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Table with sample data
        String[] columnNames = {"Event ID", "Event Name", "Date", "Location", "Status"};
        Object[][] data = {
            {1, "Birthday Party", "2025-03-10", "Skating Ranger", "Confirmed"},
            {2, "Corporate Meeting", "2025-04-15", "Office Building", "Pending"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        eventsTable = new JTable(model);
        eventsTable.setFont(NORMAL_FONT);
        eventsTable.getTableHeader().setFont(TITLE_FONT);
        eventsTable.setRowHeight(30);
        eventsTable.setAutoCreateRowSorter(true);
        
        JScrollPane scrollPane = new JScrollPane(eventsTable);
        eventsPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Creates the rentals panel
     */
    private void createRentalsPanel() {
        rentalsPanel = new JPanel(new BorderLayout(10, 10));
        
        // Header panel with title and buttons
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("My Rentals");
        titleLabel.setFont(HEADER_FONT);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        createRentalButton = createStyledButton("New Rental", SECONDARY_COLOR, this);
        
        buttonsPanel.add(createRentalButton);
        headerPanel.add(buttonsPanel, BorderLayout.EAST);
        
        rentalsPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Table with sample data
        String[] columnNames = {"Rental ID", "Event", "Rental Date", "Return Date", "Status", "Total"};
        Object[][] data = {
            {1, "Birthday Party", "2025-03-08", "2025-03-11", "Confirmed", "$200.00"},
            {2, "Corporate Meeting", "2025-04-14", "2025-04-16", "Pending", "$350.00"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        rentalsTable = new JTable(model);
        rentalsTable.setFont(NORMAL_FONT);
        rentalsTable.getTableHeader().setFont(TITLE_FONT);
        rentalsTable.setRowHeight(30);
        rentalsTable.setAutoCreateRowSorter(true);
        
        JScrollPane scrollPane = new JScrollPane(rentalsTable);
        rentalsPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Creates the invoices panel
     */
    private void createInvoicesPanel() {
        invoicesPanel = new JPanel(new BorderLayout(10, 10));
        
        // Header panel with title
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("My Invoices");
        titleLabel.setFont(HEADER_FONT);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        invoicesPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Table with sample data
        String[] columnNames = {"Invoice ID", "Rental", "Amount Due", "Payment Date", "Payment Method", "Status"};
        Object[][] data = {
            {1, "Birthday Party Rental", "$200.00", "2025-03-07", "Credit Card", "Paid"},
            {2, "Corporate Meeting Rental", "$350.00", "Not Paid", "Not Paid", "Pending"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        invoicesTable = new JTable(model);
        invoicesTable.setFont(NORMAL_FONT);
        invoicesTable.getTableHeader().setFont(TITLE_FONT);
        invoicesTable.setRowHeight(30);
        invoicesTable.setAutoCreateRowSorter(true);
        
        JScrollPane scrollPane = new JScrollPane(invoicesTable);
        invoicesPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Creates the profile panel
     */
    public void createProfilePanel() {
        profilePanel = new JPanel(new BorderLayout(10, 10));
        
        // Header panel with title
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("My Profile");
        titleLabel.setFont(HEADER_FONT);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        profilePanel.add(headerPanel, BorderLayout.NORTH);
        
        // Profile details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 15);
        
        // Labels for customer details
        detailsPanel.add(createBoldLabel("Customer ID:"), gbc);
        gbc.gridy++;
        detailsPanel.add(createBoldLabel("Full Name:"), gbc);
        gbc.gridy++;
        detailsPanel.add(createBoldLabel("Email:"), gbc);
        gbc.gridy++;
        detailsPanel.add(createBoldLabel("Phone:"), gbc);
        
        // Values for customer details
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        
        detailsPanel.add(new JLabel(String.valueOf(currentCustomer.getId())), gbc);
        gbc.gridy++;
        detailsPanel.add(new JLabel(currentCustomer.getFullName()), gbc);
        gbc.gridy++;
        detailsPanel.add(new JLabel(currentCustomer.getEmail()), gbc);
        gbc.gridy++;
        detailsPanel.add(new JLabel(String.valueOf(currentCustomer.getPhone())), gbc);
        
        // Edit profile button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 5, 5, 5);
        
        editProfileButton = createStyledButton("Edit Profile", PRIMARY_COLOR, this);
        detailsPanel.add(editProfileButton, gbc);
        editProfileButton.addActionListener(this);

        
        profilePanel.add(detailsPanel, BorderLayout.CENTER);
    }
    
    
    private JLabel createBoldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(NORMAL_FONT.getName(), Font.BOLD, NORMAL_FONT.getSize()));
        return label;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == eventsButton) {
            cardLayout.show(contentPanel, "events");
        } else if (source == rentalsButton) {
            cardLayout.show(contentPanel, "rentals");
        } else if (source == invoicesButton) {
            cardLayout.show(contentPanel, "invoices");
        } else if (source == profileButton) {
            cardLayout.show(contentPanel, "profile");
        } else if (source == logoutButton) {
            if (showConfirmDialog("Are you sure you want to logout?")) {
                clearCurrentUser();
                navigateTo(new WelcomeGUI());
            }
        }
        
        else if (source == createEventButton) {
            
            showEventDialog(null); // null indicates a new event
        } 

		else if (source == editEventButton) {
		    if (eventsTable.getSelectedRow() == -1) {
		        showErrorMessage("Please select an event to edit.");
		    } else {
		        // Get the selected event ID from the table
		        int selectedRow = eventsTable.getSelectedRow();
		        int eventId = (int) eventsTable.getValueAt(selectedRow, 0);
		        
		        // Get the event from the data manager
		        DataManager dataManager = DataManager.getInstance();
		        ArrayList<Event> events = dataManager.getEvents();
		        Event selectedEvent = null;
		        
		        for (Event event : events) {
		            if (event.getEventId() == eventId) {
		                selectedEvent = event;
		                break;
		            }
		        }
		        
		        if (selectedEvent != null) {
		            // Open the event dialog with the selected event for editing
		            showEventDialog(selectedEvent);
		        } else {
		            showErrorMessage("Event not found in database.");
		        }
		    }
        } else if (source == deleteEventButton) {
            if (eventsTable.getSelectedRow() == -1) {
                showErrorMessage("Please select an event to delete.");
            } else {
                int eventId = (int) eventsTable.getValueAt(eventsTable.getSelectedRow(), 0);
                if (showConfirmDialog("Are you sure you want to delete this event?")) {
                    // Delete the event from the data manager
                    DataManager dataManager = DataManager.getInstance();
                    boolean success = dataManager.deleteEvent(eventId);
                    
                    if (success) {
                        // Remove from table
                        DefaultTableModel model = (DefaultTableModel) eventsTable.getModel();
                        model.removeRow(eventsTable.getSelectedRow());
                        showInfoMessage("Event deleted successfully.");
                    } else {
                        showErrorMessage("Failed to delete event. It may be associated with rentals.");
                    }
                }
            }
        } else if (source == createRentalButton) {
            showRentalDialog(null); // null for new rental
        } else if (source == editProfileButton) {
            showEditProfileDialog();
        }
        
    }
    
    
    private void showEventDialog(Event event) {
        final boolean isNewEvent = (event == null);
        final JDialog dialog = new JDialog(this, isNewEvent ? "Create Event" : "Edit Event", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 10);
        
        // Event name field
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
        } else {
            // Default to current month and year
            Calendar cal = Calendar.getInstance();
            dayField.setText("");
            monthField.setText(String.valueOf(cal.get(Calendar.MONTH) + 1)); // Month is 0-based
            yearField.setText(String.valueOf(cal.get(Calendar.YEAR)));
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
        JButton saveButton = createStyledButton("Save", SECONDARY_COLOR, null);
        JButton cancelButton = createStyledButton("Cancel", NEUTRAL_COLOR, null);
        
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
                    
                    if (day < 1 || day > 31 || month < 1 || month > 12 || year < 2023) {
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
                
                // Get DataManager instance
                DataManager dataManager = DataManager.getInstance();
                
                // Save or update event
                if (isNewEvent) {
                    // Create new event with next available ID
                    int newId = dataManager.getNextId("event");
                    Event newEvent = new Event(
                        newId,
                        currentUserId,
                        nameField.getText().trim(),
                        locationField.getText().trim(),
                        eventDate
                    );
                    
                    // Save to data manager
                    dataManager.saveEvent(newEvent);
                    
                    // Add to table
                    DefaultTableModel model = (DefaultTableModel) eventsTable.getModel();
                    Object[] rowData = {
                        newId,
                        newEvent.getEventName(),
                        day + "/" + month + "/" + year,
                        newEvent.getEventLocation(),
                        "Pending"
                    };
                    
                    model.addRow(rowData);
                } else {
                    // Update existing event
                    event.setEventName(nameField.getText().trim());
                    event.setEventLocation(locationField.getText().trim());
                    event.setEventDate(eventDate);
                    
                    // Save to data manager
                    dataManager.saveEvent(event);
                    
                    // Update table
                    for (int i = 0; i < eventsTable.getRowCount(); i++) {
                        if ((int)eventsTable.getValueAt(i, 0) == event.getEventId()) {
                            eventsTable.setValueAt(event.getEventName(), i, 1);
                            eventsTable.setValueAt(day + "/" + month + "/" + year, i, 2);
                            eventsTable.setValueAt(event.getEventLocation(), i, 3);
                            break;
                        }
                    }
                }
                
                dialog.dispose();
                JOptionPane.showMessageDialog(CustomerDashboard.this, 
                    isNewEvent ? "Event created successfully." : "Event updated successfully.",
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
        
        // Event selector
        panel.add(new JLabel("Event:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        DataManager dataManager = DataManager.getInstance();
        ArrayList<Event> customerEvents = new ArrayList<>();
        for (Event event : dataManager.getEvents()) {
            if (event.getCustomerId() == currentUserId) {
                customerEvents.add(event);
            }
        }
        
        if (customerEvents.isEmpty()) {
            showErrorMessage("You need to create an event before creating a rental.");
            dialog.dispose();
            return;
        }
        
        String[] eventNames = new String[customerEvents.size()];
        for (int i = 0; i < customerEvents.size(); i++) {
            Event event = customerEvents.get(i);
            eventNames[i] = event.getEventId() + " - " + event.getEventName();
        }
        
        JComboBox<String> eventBox = new JComboBox<>(eventNames);
        
        if (!isNewRental) {
            // Select the current event
            for (int i = 0; i < customerEvents.size(); i++) {
                if (customerEvents.get(i).getEventId() == rental.getEventId()) {
                    eventBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        panel.add(eventBox, gbc);
        
    
        gbc.gridx = 0;
        gbc.gridy += 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton saveButton = createStyledButton("Save", SECONDARY_COLOR, null);
        JButton cancelButton = createStyledButton("Cancel", NEUTRAL_COLOR, null);
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save logic
                dialog.dispose();
                showInfoMessage("Rental created successfully.");
                
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
    
    private void showEditProfileDialog() {
        final JDialog dialog = new JDialog(this, "Edit Profile", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 10);
        
        // Full name field
        panel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField fullNameField = new JTextField(20);
        fullNameField.setText(currentCustomer.getFullName());
        panel.add(fullNameField, gbc);
        
        // Email field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField emailField = new JTextField(20);
        emailField.setText(currentCustomer.getEmail());
        panel.add(emailField, gbc);
        
        // Phone field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField phoneField = new JTextField(20);
        phoneField.setText(String.valueOf(currentCustomer.getPhone()));
        panel.add(phoneField, gbc);
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton saveButton = createStyledButton("Save", SECONDARY_COLOR, null);
        JButton cancelButton = createStyledButton("Cancel", NEUTRAL_COLOR, null);
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate and save
                if (!fullNameField.getText().trim().isEmpty() && 
                    !emailField.getText().trim().isEmpty() && 
                    !phoneField.getText().trim().isEmpty()) {
                    
                    // Update customer object
                    currentCustomer.setFullName(fullNameField.getText().trim());
                    currentCustomer.setEmail(emailField.getText().trim());
                    try {
                        currentCustomer.setPhone(Integer.parseInt(phoneField.getText().trim()));
                    } catch (NumberFormatException ex) {
                        showErrorMessage("Phone must be a valid number.");
                        return;
                    }
                    
                    // Save to data manager
                    DataManager dataManager = DataManager.getInstance();
                    dataManager.saveCustomer(currentCustomer);
                    
                    contentPanel.remove(profilePanel);
                    createProfilePanel();
                    contentPanel.add(profilePanel, "profile");
                    cardLayout.show(contentPanel, "profile");
                    
                    dialog.dispose();
                    showInfoMessage("Profile updated successfully.");
                } else {
                    showErrorMessage("All fields are required.");
                }
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
        dialog.setVisible(true);
    }
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        // Test with customer ID 1
        new CustomerDashboard(1);
    }
}