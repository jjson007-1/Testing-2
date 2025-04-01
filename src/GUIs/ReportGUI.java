package GUIs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import serialization.Date;
import serialization.Event;
import serialization.Invoice;
import serialization.RentalOrder;

/**
 * Simple GUI for generating reports
 */
public class ReportGUI extends JFrame implements ActionListener {
    // Components
    private JPanel mainPanel;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> reportTypeBox;
    private JTextField startDateField, endDateField;
    private JButton generateButton;
    private JButton exportButton;
    private JButton backButton;
    
    // Data
    private ArrayList<Event> eventsList;
    private ArrayList<RentalOrder> rentalsList;
    private ArrayList<Invoice> invoicesList;
    
    // Colors
    private final Color PRIMARY_COLOR = new Color(0, 112, 116);
    private final Color SECONDARY_COLOR = new Color(255, 100, 50);
    private final Color NEUTRAL_COLOR = new Color(70, 70, 70);
    
    /**
     * Constructor
     */
    public ReportGUI() {
        this.setTitle("Reports");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        // Load sample data
        loadSampleData();
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        this.setVisible(true);
    }
    
    /**
     * Load sample data
     */
    private void loadSampleData() {
        // Events
        eventsList = new ArrayList<>();
        eventsList.add(new Event(1, 1, "Birthday Party", "Skating Ranger", new Date(10, 3, 2025)));
        eventsList.add(new Event(2, 2, "Wedding Reception", "Pauls Chapel", new Date(20, 4, 2025)));
        
        // Rentals
        rentalsList = new ArrayList<>();
        rentalsList.add(new RentalOrder(1, 1, 1, new Date(8, 3, 2025), new Date(11, 3, 2025), "Confirmed", 200.0));
        rentalsList.add(new RentalOrder(2, 2, 2, new Date(18, 4, 2025), new Date(21, 4, 2025), "Pending", 500.0));
        
        // Invoices
        invoicesList = new ArrayList<>();
        invoicesList.add(new Invoice(1, 1, 1, 200.0, new Date(7, 3, 2025), "Credit Card"));
        invoicesList.add(new Invoice(2, 2, 2, 500.0, null, null));
    }
    
    /**
     * Initialize all UI components
     */
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Report type selector
        String[] reportTypes = {
            "Events by Date",
            "Rental Orders by Date",
            "Revenue Report"
        };
        reportTypeBox = new JComboBox<>(reportTypes);
        reportTypeBox.setPreferredSize(new Dimension(200, 25));
        
        // Date fields
        startDateField = new JTextField(10);
        startDateField.setText("01/01/2025"); // Default start date
        endDateField = new JTextField(10);
        endDateField.setText("31/12/2025"); // Default end date
        
        // Buttons
        generateButton = createStyledButton("Generate Report", SECONDARY_COLOR);
        exportButton = createStyledButton("Export to PDF", PRIMARY_COLOR);
        backButton = createStyledButton("Back", NEUTRAL_COLOR);
        
        // Initial empty table
        tableModel = new DefaultTableModel();
        reportTable = new JTable(tableModel);
        reportTable.setFont(new Font("Arial", Font.PLAIN, 14));
        reportTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        reportTable.setRowHeight(25);
    }
    
    /**
     * Set up the layout of all components
     */
    private void setupLayout() {
        // Header panel with title and back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Report Generation");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Controls panel
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        controlsPanel.add(new JLabel("Report Type:"));
        controlsPanel.add(reportTypeBox);
        controlsPanel.add(new JLabel("Start Date:"));
        controlsPanel.add(startDateField);
        controlsPanel.add(new JLabel("End Date:"));
        controlsPanel.add(endDateField);
        controlsPanel.add(generateButton);
        controlsPanel.add(exportButton);
        
        // Top panel combining header and controls
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(controlsPanel, BorderLayout.CENTER);
        
        // Report table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Report Results"));
        JScrollPane scrollPane = new JScrollPane(reportTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        
        this.setContentPane(mainPanel);
    }
    
    /**
     * Set up all event handlers
     */
    private void setupEventHandlers() {
        generateButton.addActionListener(this);
        exportButton.addActionListener(this);
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
        
        if (source == generateButton) {
            generateReport();
        } else if (source == exportButton) {
            JOptionPane.showMessageDialog(this, 
                "PDF export functionality will be implemented in the future.", 
                "Information", 
                JOptionPane.INFORMATION_MESSAGE);
        } else if (source == backButton) {
            // Go back to previous screen
            this.dispose();
            new WelcomeGUI();
        }
    }
    
    /**
     * Generate the selected report
     */
    private void generateReport() {
        try {
            // Parse date range
            String startDateStr = startDateField.getText().trim();
            String endDateStr = endDateField.getText().trim();
            
            // Simple validation
            if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter both start and end dates.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // In a full implementation, we would parse the dates properly
            // For simplicity, we'll just proceed with the report generation
            
            String reportType = (String) reportTypeBox.getSelectedItem();
            
            if ("Events by Date".equals(reportType)) {
                generateEventsReport();
            } else if ("Rental Orders by Date".equals(reportType)) {
                generateRentalsReport();
            } else if ("Revenue Report".equals(reportType)) {
                generateRevenueReport();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error generating report: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Generate events report
     */
    private void generateEventsReport() {
        // Clear existing data
        tableModel = new DefaultTableModel(
            new String[]{"Event ID", "Name", "Date", "Location", "Customer ID"}, 
            0
        );
        
        // Add data rows
        for (Event event : eventsList) {
            Date eventDate = event.getEventDate();
            Object[] row = {
                event.getEventId(),
                event.getEventName(),
                formatDate(eventDate),
                event.getEventLocation(),
                event.getCustomerId()
            };
            tableModel.addRow(row);
        }
        
        reportTable.setModel(tableModel);
    }
    
    /**
     * Generate rentals report
     */
    private void generateRentalsReport() {
        // Clear existing data
        tableModel = new DefaultTableModel(
            new String[]{"Rental ID", "Event ID", "Customer ID", "Rental Date", "Return Date", "Status", "Total Price"}, 
            0
        );
        
        // Add data rows
        for (RentalOrder rental : rentalsList) {
            Object[] row = {
                rental.getRentalId(),
                rental.getEventId(),
                rental.getCustomerId(),
                formatDate(rental.getRentalDate()),
                formatDate(rental.getReturnDate()),
                rental.getRentStatus(),
                String.format("$%.2f", rental.getTotalPrice())
            };
            tableModel.addRow(row);
        }
        
        reportTable.setModel(tableModel);
    }
    
    /**
     * Generate revenue report
     */
    private void generateRevenueReport() {
        // Clear existing data
        tableModel = new DefaultTableModel(
            new String[]{"Invoice ID", "Rental ID", "Customer ID", "Amount", "Payment Date", "Status"}, 
            0
        );
        
        // Add data rows
        double totalRevenue = 0;
        double paidRevenue = 0;
        double pendingRevenue = 0;
        
        for (Invoice invoice : invoicesList) {
            String status = invoice.isPaid() ? "Paid" : "Pending";
            Object[] row = {
                invoice.getInvoiceId(),
                invoice.getRentalId(),
                invoice.getCustomerId(),
                String.format("$%.2f", invoice.getAmountDue()),
                invoice.isPaid() ? formatDate(invoice.getPaymentDate()) : "Not Paid",
                status
            };
            tableModel.addRow(row);
            
            totalRevenue += invoice.getAmountDue();
            if (invoice.isPaid()) {
                paidRevenue += invoice.getAmountDue();
            } else {
                pendingRevenue += invoice.getAmountDue();
            }
        }
        
        // Add summary row
        Object[] summaryRow = {
            "TOTAL", "", "", String.format("$%.2f", totalRevenue), "", ""
        };
        tableModel.addRow(summaryRow);
        
        // Add paid/pending summary
        Object[] paidRow = {
            "PAID", "", "", String.format("$%.2f", paidRevenue), "", ""
        };
        tableModel.addRow(paidRow);
        
        Object[] pendingRow = {
            "PENDING", "", "", String.format("$%.2f", pendingRevenue), "", ""
        };
        tableModel.addRow(pendingRow);
        
        reportTable.setModel(tableModel);
    }
    
    /**
     * Format date for display
     */
    private String formatDate(Date date) {
        if (date == null) return "N/A";
        return String.format("%02d/%02d/%04d", date.getDay(), date.getMonth(), date.getYear());
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReportGUI());
    }
}