package GUIs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import serialization.Customer;
import serialization.Event;
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
        
        // Load customer data (to be implemented with actual data)
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
        // This should load the actual customer data from the serialized file
        // For now, we'll create a placeholder customer
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
    }
    
    /**
     * Creates a styled button for the sidebar
     *  text The button text
     *  A styled JButton
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
    private void createProfilePanel() {
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
        detailsPanel.add(createBoldLabel("First Name:"), gbc);
        gbc.gridy++;
        detailsPanel.add(createBoldLabel("Last Name:"), gbc);
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
        
        JButton editProfileButton = createStyledButton("Edit Profile", PRIMARY_COLOR, this);
        detailsPanel.add(editProfileButton, gbc);
        
        profilePanel.add(detailsPanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates a bold label
     *  text The label text
     *  A styled JLabel
     */
    private JLabel createBoldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(NORMAL_FONT.getName(), Font.BOLD, NORMAL_FONT.getSize()));
        return label;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        // Handle sidebar button navigation
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
        
        // Handle action buttons
        else if (source == createEventButton) {
            // TODO: Open create event form
            showInfoMessage("Create Event functionality will be implemented.");
        } else if (source == editEventButton) {
            if (eventsTable.getSelectedRow() == -1) {
                showErrorMessage("Please select an event to edit.");
            } else {
                // TODO: Open edit event form with selected event
                int eventId = (int) eventsTable.getValueAt(eventsTable.getSelectedRow(), 0);
                showInfoMessage("Edit Event functionality will be implemented for event ID: " + eventId);
            }
        } else if (source == deleteEventButton) {
            if (eventsTable.getSelectedRow() == -1) {
                showErrorMessage("Please select an event to delete.");
            } else {
                int eventId = (int) eventsTable.getValueAt(eventsTable.getSelectedRow(), 0);
                if (showConfirmDialog("Are you sure you want to delete this event?")) {
                    // TODO: Delete selected event
                    showInfoMessage("Delete Event functionality will be implemented for event ID: " + eventId);
                }
            }
        } else if (source == createRentalButton) {
            // TODO: Open create rental form
            showInfoMessage("Create Rental functionality will be implemented.");
        }
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        // Test with customer ID 1
        new CustomerDashboard(1);
    }
}