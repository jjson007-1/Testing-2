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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import serialization.Customer;
import serialization.DataManager;
import serialization.Date;
import serialization.Event;
import serialization.Invoice;
import serialization.RentalOrder;

/**
 * GUI for managing invoices, updated to use DataManager
 */
public class InvoiceManagementGUI extends JFrame implements ActionListener {
    // Components
    private JPanel mainPanel;
    private JTable invoicesTable;
    private JTextArea invoiceDetailsArea;
    private DefaultTableModel tableModel;
    private JButton createInvoiceButton;
    private JButton markAsPaidButton;
    private JButton printInvoiceButton;
    private JButton backButton;
    
    // Data manager
    private DataManager dataManager;
    
    // Colors
    private final Color PRIMARY_COLOR = new Color(0, 112, 116);
    private final Color SECONDARY_COLOR = new Color(255, 100, 50);
    private final Color NEUTRAL_COLOR = new Color(70, 70, 70);
    
    /**
     * Constructor
     */
    public InvoiceManagementGUI() {
        this.setTitle("Invoice Management");
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
        
        // Get invoices from data manager
        ArrayList<Invoice> invoicesList = dataManager.getInvoices();
        
        // Create the invoices table model
        Object[][] invoicesData = new Object[invoicesList.size()][6];
        for (int i = 0; i < invoicesList.size(); i++) {
            Invoice invoice = invoicesList.get(i);
            invoicesData[i][0] = invoice.getInvoiceId();
            invoicesData[i][1] = "Rental #" + invoice.getRentalId();
            invoicesData[i][2] = getCustomerNameById(invoice.getCustomerId());
            invoicesData[i][3] = String.format("$%.2f", invoice.getAmountDue());
            
            // Format the payment date
            Date paymentDate = invoice.getPaymentDate();
            invoicesData[i][4] = paymentDate != null ? 
                String.format("%02d/%02d/%04d", paymentDate.getDay(), paymentDate.getMonth(), paymentDate.getYear()) : 
                "Not Paid";
            
            invoicesData[i][5] = invoice.isPaid() ? "Paid" : "Pending";
        }
        
        String[] invoicesColumns = {"ID", "Rental", "Customer", "Amount Due", "Payment Date", "Status"};
        
        tableModel = new DefaultTableModel(invoicesData, invoicesColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        invoicesTable = new JTable(tableModel);
        invoicesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        invoicesTable.setFont(new Font("Arial", Font.PLAIN, 14));
        invoicesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        invoicesTable.setRowHeight(30);
        invoicesTable.setAutoCreateRowSorter(true);
        
        // Create invoice details area
        invoiceDetailsArea = new JTextArea();
        invoiceDetailsArea.setEditable(false);
        invoiceDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        invoiceDetailsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create buttons
        createInvoiceButton = createStyledButton("Create Invoice", SECONDARY_COLOR);
        markAsPaidButton = createStyledButton("Mark as Paid", PRIMARY_COLOR);
        printInvoiceButton = createStyledButton("Print Invoice", PRIMARY_COLOR);
        backButton = createStyledButton("Back", NEUTRAL_COLOR);
    }
    
    /**
     * Set up the layout of all components
     */
    private void setupLayout() {
        // Header panel with title and back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Invoice Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.add(createInvoiceButton);
        buttonPanel.add(markAsPaidButton);
        buttonPanel.add(printInvoiceButton);
        
        // Top panel combining header and buttons
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Invoices table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Invoices"));
        JScrollPane tableScrollPane = new JScrollPane(invoicesTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        
        // Invoice details panel
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Invoice Details"));
        JScrollPane detailsScrollPane = new JScrollPane(invoiceDetailsArea);
        detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);
        
        // Configure layout
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.add(detailsPanel, BorderLayout.SOUTH);
        
        // Add all components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        this.setContentPane(mainPanel);
    }
    
    /**
     * Set up all event handlers
     */
    private void setupEventHandlers() {
        createInvoiceButton.addActionListener(this);
        markAsPaidButton.addActionListener(this);
        printInvoiceButton.addActionListener(this);
        backButton.addActionListener(this);
        
        // Add selection listener to invoices table
        invoicesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = invoicesTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        int invoiceId = (int) invoicesTable.getValueAt(selectedRow, 0);
                        Invoice selectedInvoice = getInvoiceById(invoiceId);
                        displayInvoiceDetails(selectedInvoice);
                    } else {
                        clearInvoiceDetails();
                    }
                }
            }
        });
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
    
    /**
     * Display details for the selected invoice
     */
    private void displayInvoiceDetails(Invoice invoice) {
        if (invoice == null) {
            clearInvoiceDetails();
            return;
        }
        
        // Get related rental and customer from data manager
        RentalOrder rental = getRentalById(invoice.getRentalId());
        Customer customer = getCustomerById(invoice.getCustomerId());
        
        StringBuilder receipt = new StringBuilder();
        receipt.append("=============== INVOICE RECEIPT ===============\n\n");
        receipt.append("Invoice ID: ").append(invoice.getInvoiceId()).append("\n");
        receipt.append("Rental ID: ").append(invoice.getRentalId()).append("\n");
        receipt.append("Customer: ").append(customer != null ? 
            customer.getFullName() + " " : "Unknown Customer").append("\n\n");
        
        if (rental != null) {
            receipt.append("Event: ").append(getEventNameById(rental.getEventId())).append("\n");
            receipt.append("Rental Period: ").append(formatDate(rental.getRentalDate()))
                   .append(" to ").append(formatDate(rental.getReturnDate())).append("\n");
            receipt.append("Status: ").append(rental.getRentStatus()).append("\n\n");
        }
        
        receipt.append("Amount Due: $").append(String.format("%.2f", invoice.getAmountDue())).append("\n");
        receipt.append("Payment Status: ").append(invoice.isPaid() ? "PAID" : "UNPAID").append("\n");
        
        if (invoice.isPaid()) {
            receipt.append("Payment Date: ").append(formatDate(invoice.getPaymentDate())).append("\n");
            receipt.append("Payment Method: ").append(invoice.getPaymentMethod()).append("\n");
        }
        
        receipt.append("\n==============================================\n");
        
        // Display in text area
        invoiceDetailsArea.setText(receipt.toString());
        invoiceDetailsArea.setCaretPosition(0); // Scroll to top
    }
    
    /**
     * Clear the invoice details area
     */
    private void clearInvoiceDetails() {
        invoiceDetailsArea.setText("");
    }
    
    /**
     * Format date for display
     */
    private String formatDate(Date date) {
        if (date == null) return "N/A";
        return String.format("%02d/%02d/%04d", date.getDay(), date.getMonth(), date.getYear());
    }
    
    /**
     * Find invoice by ID
     */
    private Invoice getInvoiceById(int invoiceId) {
        ArrayList<Invoice> invoices = dataManager.getInvoices();
        for (Invoice invoice : invoices) {
            if (invoice.getInvoiceId() == invoiceId) {
                return invoice;
            }
        }
        return null;
    }
    
    /**
     * Find rental by ID
     */
    private RentalOrder getRentalById(int rentalId) {
        ArrayList<RentalOrder> rentals = dataManager.getRentals();
        for (RentalOrder rental : rentals) {
            if (rental.getRentalId() == rentalId) {
                return rental;
            }
        }
        return null;
    }
    
    /**
     * Find customer by ID
     */
    private Customer getCustomerById(int customerId) {
        ArrayList<Customer> customers = dataManager.getCustomers();
        for (Customer customer : customers) {
            if (customer.getId() == customerId) {
                return customer;
            }
        }
        return null;
    }
    
    /**
     * Find event name by ID
     */
    private String getEventNameById(int eventId) {
        ArrayList<Event> events = dataManager.getEvents();
        for (Event event : events) {
            if (event.getEventId() == eventId) {
                return event.getEventName();
            }
        }
        return "Unknown Event";
    }
    
    /**
     * Find customer name by ID
     */
    private String getCustomerNameById(int customerId) {
        Customer customer = getCustomerById(customerId);
        if (customer != null) {
            return customer.getFullName();
        }
        return "Unknown Customer";
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == createInvoiceButton) {
            showCreateInvoiceDialog();
        } else if (source == markAsPaidButton) {
            int selectedRow = invoicesTable.getSelectedRow();
            if (selectedRow >= 0) {
                int invoiceId = (int) invoicesTable.getValueAt(selectedRow, 0);
                Invoice invoice = getInvoiceById(invoiceId);
                if (invoice != null && !invoice.isPaid()) {
                    showPaymentDialog(invoice);
                } else if (invoice != null && invoice.isPaid()) {
                    JOptionPane.showMessageDialog(this, 
                        "This invoice is already paid.", 
                        "Information", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select an invoice to mark as paid.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == printInvoiceButton) {
            int selectedRow = invoicesTable.getSelectedRow();
            if (selectedRow >= 0) {
                // In a real app, this would generate a PDF
                JOptionPane.showMessageDialog(this, 
                    "Printing functionality will be implemented.", 
                    "Information", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select an invoice to print.", 
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
     * Show dialog for creating a new invoice
     */
    private void showCreateInvoiceDialog() {
        final JDialog dialog = new JDialog(this, "Create Invoice", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 10);
        
        // Rental selector
        panel.add(new JLabel("Rental:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Get rentals from data manager
        ArrayList<RentalOrder> rentalsList = dataManager.getRentals();
        
        // Create array of rental options
        String[] rentalOptions = new String[rentalsList.size()];
        for (int i = 0; i < rentalsList.size(); i++) {
            RentalOrder rental = rentalsList.get(i);
            rentalOptions[i] = rental.getRentalId() + " - Rental #" + rental.getRentalId();
        }
        
        JComboBox<String> rentalBox = new JComboBox<>(rentalOptions);
        panel.add(rentalBox, gbc);
        
        // Amount field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Amount Due:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        amountPanel.add(new JLabel("$"));
        JTextField amountField = new JTextField(10);
        amountPanel.add(amountField);
        panel.add(amountPanel, gbc);
        
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
                // Validate amount
                if (amountField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Amount cannot be empty.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                double amount;
                try {
                    amount = Double.parseDouble(amountField.getText().trim());
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Amount must be greater than zero.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Please enter a valid amount.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Get selected rental ID
                String selectedRental = (String) rentalBox.getSelectedItem();
                int rentalId = Integer.parseInt(selectedRental.split(" - ")[0]);
                
                // Find rental
                RentalOrder rental = getRentalById(rentalId);
                
                if (rental == null) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Selected rental not found.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create invoice with next available ID
                int newId = dataManager.getNextId("invoice");
                Invoice newInvoice = new Invoice(
                    newId,
                    rentalId,
                    rental.getCustomerId(),
                    amount,
                    null,  // Not paid yet
                    null   // No payment method yet
                );
                
                // Save to data manager
                dataManager.saveInvoice(newInvoice);
                
                // Add to table
                Object[] rowData = {
                    newId,
                    "Rental #" + rentalId,
                    getCustomerNameById(rental.getCustomerId()),
                    String.format("$%.2f", amount),
                    "Not Paid",
                    "Pending"
                };
                
                tableModel.addRow(rowData);
                
                dialog.dispose();
                JOptionPane.showMessageDialog(InvoiceManagementGUI.this, 
                    "Invoice created successfully.", 
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
     * Show dialog for marking an invoice as paid
     */
    private void showPaymentDialog(Invoice invoice) {
        final JDialog dialog = new JDialog(this, "Mark Invoice as Paid", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 10);
        
        // Invoice info
        panel.add(new JLabel("Invoice ID:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel idLabel = new JLabel(String.valueOf(invoice.getInvoiceId()));
        panel.add(idLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Amount Due:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel amountLabel = new JLabel("$" + String.format("%.2f", invoice.getAmountDue()));
        panel.add(amountLabel, gbc);
        
        // Payment date
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Payment Date (DD/MM/YYYY):"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Panel for payment date fields
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JTextField dayField = new JTextField(2);
        JTextField monthField = new JTextField(2);
        JTextField yearField = new JTextField(4);
        
        // Use current date as default
        dayField.setText("1");
        monthField.setText("4");
        yearField.setText("2025");
        
        datePanel.add(dayField);
        datePanel.add(new JLabel("/"));
        datePanel.add(monthField);
        datePanel.add(new JLabel("/"));
        datePanel.add(yearField);
        
        panel.add(datePanel, gbc);
        
        // Payment method
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Payment Method:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        String[] paymentMethods = {"Credit Card", "Cash", "Check", "Bank Transfer"};
        JComboBox<String> methodBox = new JComboBox<>(paymentMethods);
        panel.add(methodBox, gbc);
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton confirmButton = createStyledButton("Confirm Payment", SECONDARY_COLOR);
        JButton cancelButton = createStyledButton("Cancel", NEUTRAL_COLOR);
        
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate date
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
                        "Please enter a valid date.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create payment date
                Date paymentDate = new Date(day, month, year);
                
                // Update invoice
                invoice.setPaymentDate(paymentDate);
                invoice.setPaymentMethod((String) methodBox.getSelectedItem());
                
                // Save to data manager
                dataManager.saveInvoice(invoice);
                
                // Update table
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if ((int)tableModel.getValueAt(i, 0) == invoice.getInvoiceId()) {
                        tableModel.setValueAt(formatDate(paymentDate), i, 4);
                        tableModel.setValueAt("Paid", i, 5);
                        break;
                    }
                }
                
                // Update display
                displayInvoiceDetails(invoice);
                
                dialog.dispose();
                JOptionPane.showMessageDialog(InvoiceManagementGUI.this, 
                    "Invoice marked as paid.", 
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
        
        buttonPanel.add(confirmButton);
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
        SwingUtilities.invokeLater(() -> new InvoiceManagementGUI());
    }
}