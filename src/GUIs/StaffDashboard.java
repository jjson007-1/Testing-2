package GUIs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import serialization.Staff;

/**
 * Dashboard for staff members to access system functionality
 */
public class StaffDashboard extends BaseGUI implements ActionListener {
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    // Header components
    private JLabel dateTimeLabel;
    private JLabel staffNameLabel;
    private JLabel staffRoleLabel;
    private Timer dateTimeTimer;
    
    // Sidebar buttons
    private JButton dashboardButton;
    private JButton equipmentButton;
    private JButton eventsButton;
    private JButton rentalsButton;
    private JButton customersButton;
    private JButton invoicesButton;
    private JButton reportsButton;
    private JButton logoutButton;
    
    // Dashboard panels
    private JPanel overviewPanel;
    private JPanel upcomingEventsPanel;
    private JPanel rentalsStatusPanel;
    
    // Tables
    private JTable eventsTable;
    private JTable rentalsTable;
    
    // Current staff
    private Staff currentStaff;
    
    /**
     * Constructor
     *  staffId The ID of the logged-in staff member
     */
    public StaffDashboard(int staffId) {
        super("Staff Dashboard", 1000, 700);
        
        // Load staff data
        loadStaffData(staffId);
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        // Start date/time timer
        startDateTimeTimer();
        
        this.setVisible(true);
    }
    
    /**
     * Load staff data from storage
     *  staffId The staff ID to load
     */
    private void loadStaffData(int staffId) {
        // This should load staff data from storage
        // For now, create a placeholder staff
        currentStaff = new Staff();
        currentStaff.setStaffId(staffId);
        currentStaff.setFullName("Jane Doe");
        currentStaff.setStaffRole("Manager");
    }
    
    @Override
    protected void initComponents() {
        // Main panel with border layout
        mainPanel = new JPanel(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        
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
        dashboardButton = createSidebarButton("Dashboard");
        equipmentButton = createSidebarButton("Equipment");
        eventsButton = createSidebarButton("Events");
        rentalsButton = createSidebarButton("Rentals");
        customersButton = createSidebarButton("Customers");
        invoicesButton = createSidebarButton("Invoices");
        reportsButton = createSidebarButton("Reports");
        logoutButton = createSidebarButton("Logout");
        
        // Create content panels
        createDashboardContent();
        
        // Default dummy panels for other sections
        JPanel equipmentPanel = new JPanel();
        equipmentPanel.add(new JLabel("Equipment Management"));
        
        JPanel eventsPanel = new JPanel();
        eventsPanel.add(new JLabel("Events Management"));
        
        JPanel rentalsPanel = new JPanel();
        rentalsPanel.add(new JLabel("Rentals Management"));
        
        JPanel customersPanel = new JPanel();
        customersPanel.add(new JLabel("Customers Management"));
        
        JPanel invoicesPanel = new JPanel();
        invoicesPanel.add(new JLabel("Invoices Management"));
        
        JPanel reportsPanel = new JPanel();
        reportsPanel.add(new JLabel("Reports"));
        
        // Add content panels to card layout
        contentPanel.add(overviewPanel, "dashboard");
        contentPanel.add(equipmentPanel, "equipment");
        contentPanel.add(eventsPanel, "events");
        contentPanel.add(rentalsPanel, "rentals");
        contentPanel.add(customersPanel, "customers");
        contentPanel.add(invoicesPanel, "invoices");
        contentPanel.add(reportsPanel, "reports");
        
        // Add header to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }
    
    /**
     * Creates the header panel with date/time and user info
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.setBackground(LIGHT_COLOR);
        
        // Date and time
        dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(NORMAL_FONT);
        updateDateTime(); // Set initial value
        
        // Staff info
        JPanel staffInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        staffInfoPanel.setOpaque(false);
        
        staffNameLabel = new JLabel(currentStaff.getFullName());
        staffNameLabel.setFont(NORMAL_FONT);
        staffRoleLabel = new JLabel(currentStaff.getStaffRole());
        staffRoleLabel.setFont(SMALL_FONT);
        
        staffInfoPanel.add(staffNameLabel);
        staffInfoPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        staffInfoPanel.add(staffRoleLabel);
        
        headerPanel.add(dateTimeLabel, BorderLayout.WEST);
        headerPanel.add(staffInfoPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    /**
     * Creates the dashboard content with overview panels
     */
    private void createDashboardContent() {
        overviewPanel = new JPanel(new BorderLayout(0, 20));
        
        // Create title
        JLabel titleLabel = new JLabel("Dashboard Overview");
        titleLabel.setFont(HEADER_FONT);
        
        // Create summary panels - equipment, events, rentals
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        
        JPanel equipmentSummaryPanel = createSummaryPanel("Equipment", "42", "Total Items");
        JPanel eventsSummaryPanel = createSummaryPanel("Events", "8", "Upcoming");
        JPanel rentalsSummaryPanel = createSummaryPanel("Rentals", "12", "Active");
        
        summaryPanel.add(equipmentSummaryPanel);
        summaryPanel.add(eventsSummaryPanel);
        summaryPanel.add(rentalsSummaryPanel);
        
        // Create upcoming events panel
        upcomingEventsPanel = new JPanel(new BorderLayout(0, 10));
        upcomingEventsPanel.setBorder(BorderFactory.createTitledBorder("Upcoming Events"));
        
        String[] eventColumns = {"Event ID", "Event Name", "Date", "Location", "Customer", "Status"};
        Object[][] eventData = {
            {1, "Birthday Party", "2025-03-10", "Skating Ranger", "John Doe", "Confirmed"},
            {2, "Wedding Reception", "2025-04-20", "Pauls Chapel", "Jane Smith", "Pending"},
            {3, "Corporate Event", "2025-05-15", "LHN Headquarters", "Company Inc.", "Confirmed"}
        };
        
        DefaultTableModel eventModel = new DefaultTableModel(eventData, eventColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        eventsTable = new JTable(eventModel);
        eventsTable.setFont(NORMAL_FONT);
        eventsTable.getTableHeader().setFont(NORMAL_FONT);
        eventsTable.setRowHeight(25);
        
        JScrollPane eventsScrollPane = new JScrollPane(eventsTable);
        upcomingEventsPanel.add(eventsScrollPane, BorderLayout.CENTER);
        
        // Create rentals status panel
        rentalsStatusPanel = new JPanel(new BorderLayout(0, 10));
        rentalsStatusPanel.setBorder(BorderFactory.createTitledBorder("Recent Rentals"));
        
        String[] rentalColumns = {"Rental ID", "Event", "Rental Date", "Return Date", "Status", "Total"};
        Object[][] rentalData = {
            {1, "Birthday Party", "2025-03-08", "2025-03-11", "Confirmed", "$200.00"},
            {2, "Wedding Reception", "2025-04-18", "2025-04-21", "Pending", "$500.00"}
        };
        
        DefaultTableModel rentalModel = new DefaultTableModel(rentalData, rentalColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        rentalsTable = new JTable(rentalModel);
        rentalsTable.setFont(NORMAL_FONT);
        rentalsTable.getTableHeader().setFont(NORMAL_FONT);
        rentalsTable.setRowHeight(25);
        
        JScrollPane rentalsScrollPane = new JScrollPane(rentalsTable);
        rentalsStatusPanel.add(rentalsScrollPane, BorderLayout.CENTER);
        
        // Add all panels to the overview panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.WEST);
        
        overviewPanel.add(titlePanel, BorderLayout.NORTH);
        overviewPanel.add(summaryPanel, BorderLayout.CENTER);
        
        JPanel tablesPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        tablesPanel.add(upcomingEventsPanel);
        tablesPanel.add(rentalsStatusPanel);
        
        overviewPanel.add(tablesPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Creates a summary panel with title, value, and subtitle
     *  title Panel title
     *  value Main value to display
     *  subtitle Subtitle description
     * Formatted summary panel
     */
    private JPanel createSummaryPanel(String title, String value, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(NEUTRAL_COLOR),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new java.awt.Font(HEADER_FONT.getName(), java.awt.Font.BOLD, 36));
        valueLabel.setForeground(PRIMARY_COLOR);
        valueLabel.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(NORMAL_FONT);
        subtitleLabel.setForeground(NEUTRAL_COLOR);
        subtitleLabel.setAlignmentX(LEFT_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(valueLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(subtitleLabel);
        
        return panel;
    }
    
    
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(LIGHT_COLOR);
        button.setBackground(PRIMARY_COLOR);
        button.setFont(NORMAL_FONT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(180, 40));
        return button;
    }
    
    /**
     * Updates the date/time label with current time
     */
    private void updateDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
        dateTimeLabel.setText(sdf.format(new Date()));
    }
    
    /**
     * Starts the timer that updates the date/time label
     */
    private void startDateTimeTimer() {
        dateTimeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateTime();
            }
        });
        dateTimeTimer.start();
    }
    
    @Override
    protected void setupLayout() {
        // Add buttons to sidebar
        sidebarPanel.add(dashboardButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(equipmentButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(eventsButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(rentalsButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(customersButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(invoicesButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(reportsButton);
        
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
        dashboardButton.addActionListener(this);
        equipmentButton.addActionListener(this);
        eventsButton.addActionListener(this);
        rentalsButton.addActionListener(this);
        customersButton.addActionListener(this);
        invoicesButton.addActionListener(this);
        reportsButton.addActionListener(this);
        logoutButton.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == dashboardButton) {
            cardLayout.show(contentPanel, "dashboard");
        } else if (source == equipmentButton) {
            // Either show equipment panel or open equipment management window
            // For a more complete solution, load the EquipmentManagementGUI
            navigateTo(new EquipmentManagementGUI());
        } else if (source == eventsButton) {
            cardLayout.show(contentPanel, "events");
        } else if (source == rentalsButton) {
            cardLayout.show(contentPanel, "rentals");
        } else if (source == customersButton) {
            cardLayout.show(contentPanel, "customers");
        } else if (source == invoicesButton) {
            cardLayout.show(contentPanel, "invoices");
        } else if (source == reportsButton) {
            cardLayout.show(contentPanel, "reports");
        } else if (source == logoutButton) {
            if (showConfirmDialog("Are you sure you want to logout?")) {
                // Clean up resources
                if (dateTimeTimer != null) {
                    dateTimeTimer.stop();
                }
                
                clearCurrentUser();
                navigateTo(new WelcomeGUI());
            }
        }
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StaffDashboard(1));
    }
}