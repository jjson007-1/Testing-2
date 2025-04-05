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
import javax.swing.table.DefaultTableModel;

import serialization.DataManager;
import serialization.Equipment;

/**
 * GUI for managing equipment inventory
 */
public class EquipmentManagementGUI extends JFrame implements ActionListener {
    // Components
    private JPanel mainPanel;
    private JTable equipmentTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton maintenanceButton;
    private JButton backButton;
    private JFrame previousScreen;
    
    // Data manager
    private DataManager dataManager;
    
    // Colors
    private final Color PRIMARY_COLOR = new Color(0, 112, 116);
    private final Color SECONDARY_COLOR = new Color(255, 100, 50);
    private final Color NEUTRAL_COLOR = new Color(70, 70, 70);
    
    // Table column names
    private final String[] columnNames = {
        "ID", "Name", "Description", "Rental Price", "Availability", "Condition"
    };
    
    /**
     * Constructor
     */
    public EquipmentManagementGUI(JFrame previousScreen) {
        this.setTitle("Equipment Management");
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.previousScreen = previousScreen;

        
        // Get data manager instance
        dataManager = DataManager.getInstance();
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        this.setVisible(true);
    }
    
    public EquipmentManagementGUI() {
        this(new WelcomeGUI());
        // Hide the WelcomeGUI created as the default previous screen
        previousScreen.setVisible(false);
    }
    /**
     * Initialize all UI components
     */
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Get equipment from data manager
        ArrayList<Equipment> equipmentList = dataManager.getEquipment();
        
        // Create the table model
        Object[][] data = new Object[equipmentList.size()][6];
        for (int i = 0; i < equipmentList.size(); i++) {
            Equipment equipment = equipmentList.get(i);
            data[i][0] = equipment.getEquipmentId();
            data[i][1] = equipment.getName();
            data[i][2] = equipment.getDescription();
            data[i][3] = String.format("$%.2f", equipment.getRentalPrice());
            data[i][4] = equipment.getAvailabilityStatus();
            data[i][5] = equipment.getConditionStatus();
        }
        
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        equipmentTable = new JTable(tableModel);
        equipmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        equipmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        equipmentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        equipmentTable.setRowHeight(30);
        equipmentTable.setAutoCreateRowSorter(true);
        
        // Create buttons
        addButton = createStyledButton("Add Equipment", SECONDARY_COLOR);
        editButton = createStyledButton("Edit Equipment", PRIMARY_COLOR);
        deleteButton = createStyledButton("Delete Equipment", NEUTRAL_COLOR);
        maintenanceButton = createStyledButton("Maintenance", PRIMARY_COLOR);
        backButton = createStyledButton("Back", NEUTRAL_COLOR);
    }
    
    /**
     * Set up the layout of all components
     */
    private void setupLayout() {
        // Header panel with title and back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Equipment Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(maintenanceButton);
        
        // Top panel combining header and buttons
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(equipmentTable);
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
        maintenanceButton.addActionListener(this);
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
            showEquipmentDialog(null); // null for new equipment
        } else if (source == editButton) {
            int selectedRow = equipmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                int equipmentId = (int) equipmentTable.getValueAt(selectedRow, 0);
                Equipment selectedEquipment = getEquipmentById(equipmentId);
                if (selectedEquipment != null) {
                    showEquipmentDialog(selectedEquipment);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select an equipment item to edit.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == deleteButton) {
            int selectedRow = equipmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                int equipmentId = (int) equipmentTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this equipment?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
                    
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteEquipment(equipmentId);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select an equipment item to delete.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == maintenanceButton) {
            int selectedRow = equipmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                int equipmentId = (int) equipmentTable.getValueAt(selectedRow, 0);
                Equipment selectedEquipment = getEquipmentById(equipmentId);
                if (selectedEquipment != null) {
                    showMaintenanceDialog(selectedEquipment);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select an equipment item for maintenance.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == backButton) {
            // Go back to previous screen instead of always to WelcomeGUI
            this.dispose();
            
            if (previousScreen != null) {
                previousScreen.setVisible(true);
            } else {
                new WelcomeGUI(); // Fallback if previous screen is null
            }
        }
    }
    
    /**
     * Find equipment by ID
     */
    private Equipment getEquipmentById(int equipmentId) {
        ArrayList<Equipment> equipmentList = dataManager.getEquipment();
        for (Equipment equipment : equipmentList) {
            if (equipment.getEquipmentId() == equipmentId) {
                return equipment;
            }
        }
        return null;
    }
    
    /**
     * Delete equipment
     */
    private void deleteEquipment(int equipmentId) {
        boolean success = dataManager.deleteEquipment(equipmentId);
        
        if (success) {
            // Find the row with this equipment ID
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if ((int)tableModel.getValueAt(i, 0) == equipmentId) {
                    tableModel.removeRow(i);
                    break;
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                "Equipment deleted successfully.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to delete equipment.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Show dialog for adding or editing equipment
     */
    private void showEquipmentDialog(Equipment equipment) {
        final boolean isNewEquipment = (equipment == null);
        final JDialog dialog = new JDialog(this, isNewEquipment ? "Add Equipment" : "Edit Equipment", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 10);
        
        // Equipment ID (for editing only)
        JTextField idField = new JTextField(10);
        if (!isNewEquipment) {
            panel.add(new JLabel("Equipment ID:"), gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            idField.setText(String.valueOf(equipment.getEquipmentId()));
            idField.setEditable(false);
            panel.add(idField, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.anchor = GridBagConstraints.EAST;
        }
        
        // Name field
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField nameField = new JTextField(20);
        if (!isNewEquipment) {
            nameField.setText(equipment.getName());
        }
        panel.add(nameField, gbc);
        
        // Description field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextArea descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        if (!isNewEquipment) {
            descriptionArea.setText(equipment.getDescription());
        }
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        panel.add(descScrollPane, gbc);
        
        // Rental price field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Rental Price:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField priceField = new JTextField(10);
        if (!isNewEquipment) {
            priceField.setText(String.valueOf(equipment.getRentalPrice()));
        }
        panel.add(priceField, gbc);
        
        // Availability status field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Availability:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        String[] availabilityOptions = {
            "Available", "Rented", "Under Maintenance", "Reserved"
        };
        JComboBox<String> availabilityBox = new JComboBox<>(availabilityOptions);
        if (!isNewEquipment) {
            availabilityBox.setSelectedItem(equipment.getAvailabilityStatus());
        }
        panel.add(availabilityBox, gbc);
        
        // Condition status field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Condition:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        String[] conditionOptions = {
            "Excellent", "Good", "Fair", "Poor"
        };
        JComboBox<String> conditionBox = new JComboBox<>(conditionOptions);
        if (!isNewEquipment) {
            conditionBox.setSelectedItem(equipment.getConditionStatus());
        }
        panel.add(conditionBox, gbc);
        
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
                        "Name cannot be empty.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
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
                        "Price must be a valid number.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Save or update equipment
                if (isNewEquipment) {
                    // Create new equipment with next available ID
                    int newId = dataManager.getNextId("equipment");
                    Equipment newEquipment = new Equipment(
                        newId,
                        nameField.getText().trim(),
                        descriptionArea.getText().trim(),
                        price,
                        (String) availabilityBox.getSelectedItem(),
                        (String) conditionBox.getSelectedItem()
                    );
                    
                    // Save to data manager
                    dataManager.saveEquipment(newEquipment);
                    
                    // Add to table
                    Object[] rowData = {
                        newId,
                        newEquipment.getName(),
                        newEquipment.getDescription(),
                        String.format("$%.2f", newEquipment.getRentalPrice()),
                        newEquipment.getAvailabilityStatus(),
                        newEquipment.getConditionStatus()
                    };
                    
                    tableModel.addRow(rowData);
                } else {
                    // Update existing equipment
                    int id = Integer.parseInt(idField.getText());
                    Equipment existingEquipment = getEquipmentById(id);
                    
                    if (existingEquipment != null) {
                        existingEquipment.setName(nameField.getText().trim());
                        existingEquipment.setDescription(descriptionArea.getText().trim());
                        existingEquipment.setRentalPrice(price);
                        existingEquipment.setAvailabilityStatus((String) availabilityBox.getSelectedItem());
                        existingEquipment.setConditionStatus((String) conditionBox.getSelectedItem());
                        
                        // Save to data manager
                        dataManager.saveEquipment(existingEquipment);
                        
                        // Update table
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            if ((int)tableModel.getValueAt(i, 0) == id) {
                                tableModel.setValueAt(existingEquipment.getName(), i, 1);
                                tableModel.setValueAt(existingEquipment.getDescription(), i, 2);
                                tableModel.setValueAt(String.format("$%.2f", existingEquipment.getRentalPrice()), i, 3);
                                tableModel.setValueAt(existingEquipment.getAvailabilityStatus(), i, 4);
                                tableModel.setValueAt(existingEquipment.getConditionStatus(), i, 5);
                                break;
                            }
                        }
                    }
                }
                
                dialog.dispose();
                JOptionPane.showMessageDialog(EquipmentManagementGUI.this, 
                    isNewEquipment ? "Equipment added successfully." : "Equipment updated successfully.",
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
     * Show maintenance dialog for equipment
     */
    private void showMaintenanceDialog(Equipment equipment) {
        final JDialog dialog = new JDialog(this, "Schedule Maintenance", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 10);
        
        // Equipment info
        panel.add(new JLabel("Equipment ID:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField idField = new JTextField(String.valueOf(equipment.getEquipmentId()), 10);
        idField.setEditable(false);
        panel.add(idField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Equipment Name:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField nameField = new JTextField(equipment.getName(), 20);
        nameField.setEditable(false);
        panel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Current Status:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField statusField = new JTextField(equipment.getAvailabilityStatus(), 15);
        statusField.setEditable(false);
        panel.add(statusField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Current Condition:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField conditionField = new JTextField(equipment.getConditionStatus(), 15);
        conditionField.setEditable(false);
        panel.add(conditionField, gbc);
        
        // New status
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("New Status:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JComboBox<String> newStatusBox = new JComboBox<>(new String[]{ 
            "Available", "Under Maintenance" 
        });
        newStatusBox.setSelectedItem("Under Maintenance");
        panel.add(newStatusBox, gbc);
        
        // Maintenance notes
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Maintenance Notes:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextArea notesArea = new JTextArea(4, 20);
        notesArea.setLineWrap(true);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        panel.add(notesScrollPane, gbc);
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton saveButton = createStyledButton("Schedule", SECONDARY_COLOR);
        JButton cancelButton = createStyledButton("Cancel", NEUTRAL_COLOR);
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update equipment status
                equipment.setAvailabilityStatus((String) newStatusBox.getSelectedItem());
                
                // Save to data manager
                dataManager.saveEquipment(equipment);
                
                // Update table
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if ((int)tableModel.getValueAt(i, 0) == equipment.getEquipmentId()) {
                        tableModel.setValueAt(equipment.getAvailabilityStatus(), i, 4);
                        break;
                    }
                }
                
                // In a full implementation, we would create a Maintenance record here
                
                dialog.dispose();
                JOptionPane.showMessageDialog(EquipmentManagementGUI.this, 
                    "Maintenance scheduled successfully.", 
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
        SwingUtilities.invokeLater(() -> new EquipmentManagementGUI());
    }
}