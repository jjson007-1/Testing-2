package GUIs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import serialization.Equipment;

/**
 * GUI for managing equipment inventory
 */
public class EquipmentManagementGUI extends BaseGUI implements ActionListener {
    // Components
    private JPanel mainPanel;
    private JTable equipmentTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton maintenanceButton;
    private JButton backButton;
    
    // Equipment data
    private ArrayList<Equipment> equipmentList;
    
    // Table column names
    private final String[] columnNames = {
        "ID", "Name", "Description", "Rental Price", "Availability", "Condition"
    };
    
    /**
     * Constructor
     */
    public EquipmentManagementGUI() {
        super("Equipment Management", 900, 600);
        
        // Load equipment data
        loadEquipmentData();
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        this.setVisible(true);
    }
    
    /**
     * Load equipment data from storage
     */
    private void loadEquipmentData() {
        // This should load equipment data from serialized file
        // For now, create some sample data
        equipmentList = new ArrayList<>();
        
        equipmentList.add(new Equipment(1, "Speakers", "High-quality sound system", 50.0, "Available", "Good"));
        equipmentList.add(new Equipment(2, "Projector", "4K HD projector", 80.0, "Available", "Good"));
        equipmentList.add(new Equipment(3, "Tables", "Round tables for seating", 15.0, "Rented", "Good"));
        equipmentList.add(new Equipment(4, "Chairs", "Comfortable seating", 5.0, "Under Maintenance", "Fair"));
    }
    
    @Override
    protected void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
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
        equipmentTable.setFont(NORMAL_FONT);
        equipmentTable.getTableHeader().setFont(TITLE_FONT);
        equipmentTable.setRowHeight(30);
        equipmentTable.setAutoCreateRowSorter(true);
        
        // Create buttons
        addButton = createStyledButton("Add Equipment", SECONDARY_COLOR, this);
        editButton = createStyledButton("Edit Equipment", PRIMARY_COLOR, this);
        deleteButton = createStyledButton("Delete Equipment", NEUTRAL_COLOR, this);
        maintenanceButton = createStyledButton("Maintenance", PRIMARY_COLOR, this);
        backButton = createStyledButton("Back to Dashboard", NEUTRAL_COLOR, this);
    }
    
    @Override
    protected void setupLayout() {
        // Header panel with title and back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Equipment Management");
        titleLabel.setFont(HEADER_FONT);
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
    
    @Override
    protected void setupEventHandlers() {
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        maintenanceButton.addActionListener(this);
        backButton.addActionListener(this);
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
                showErrorMessage("Please select an equipment item to edit.");
            }
        } else if (source == deleteButton) {
            int selectedRow = equipmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                int equipmentId = (int) equipmentTable.getValueAt(selectedRow, 0);
                if (showConfirmDialog("Are you sure you want to delete this equipment?")) {
                    deleteEquipment(equipmentId);
                }
            } else {
                showErrorMessage("Please select an equipment item to delete.");
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
                showErrorMessage("Please select an equipment item for maintenance.");
            }
        } else if (source == backButton) {
            // Go back to staff dashboard (to be implemented)
            this.dispose();
        }
    }
    
    /**
     * Find equipment by ID
     *  equipmentId The equipment ID to find
     * @return The equipment object or null if not found
     */
    private Equipment getEquipmentById(int equipmentId) {
        for (Equipment equipment : equipmentList) {
            if (equipment.getEquipmentId() == equipmentId) {
                return equipment;
            }
        }
        return null;
    }
    
    /**
     * Delete equipment with confirmation
     *  equipmentId The equipment ID to delete
     */
    private void deleteEquipment(int equipmentId) {
        // Find the equipment to delete
        Equipment toDelete = null;
        int index = -1;
        
        for (int i = 0; i < equipmentList.size(); i++) {
            if (equipmentList.get(i).getEquipmentId() == equipmentId) {
                toDelete = equipmentList.get(i);
                index = i;
                break;
            }
        }
        
        if (toDelete != null && index >= 0) {
            // Remove from list
            equipmentList.remove(index);
            
            // Update table
            tableModel.removeRow(equipmentTable.convertRowIndexToModel(equipmentTable.getSelectedRow()));
            
            // Save changes (to be implemented with serialization)
            // Equipment.saveEquipment(equipmentList);
            
            showInfoMessage("Equipment deleted successfully.");
        }
    }
    
    /**
     * Show dialog for adding or editing equipment
     *  equipment Equipment to edit, or null for new equipment
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
        JButton saveButton = createStyledButton("Save", SECONDARY_COLOR, null);
        JButton cancelButton = createStyledButton("Cancel", NEUTRAL_COLOR, null);
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate fields
                if (nameField.getText().trim().isEmpty()) {
                    showErrorMessage("Name cannot be empty.");
                    return;
                }
                
                double price;
                try {
                    price = Double.parseDouble(priceField.getText().trim());
                    if (price < 0) {
                        showErrorMessage("Price must be a positive number.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showErrorMessage("Price must be a valid number.");
                    return;
                }
                
                // Save or update equipment
                if (isNewEquipment) {
                    // Create new equipment
                    int newId = getNextEquipmentId();
                    Equipment newEquipment = new Equipment(
                        newId,
                        nameField.getText().trim(),
                        descriptionArea.getText().trim(),
                        price,
                        (String) availabilityBox.getSelectedItem(),
                        (String) conditionBox.getSelectedItem()
                    );
                    
                    // Add to list and table
                    equipmentList.add(newEquipment);
                    
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
                        
                        // Update table
                        int row = equipmentTable.getSelectedRow();
                        if (row >= 0) {
                            equipmentTable.setValueAt(existingEquipment.getName(), row, 1);
                            equipmentTable.setValueAt(existingEquipment.getDescription(), row, 2);
                            equipmentTable.setValueAt(String.format("$%.2f", existingEquipment.getRentalPrice()), row, 3);
                            equipmentTable.setValueAt(existingEquipment.getAvailabilityStatus(), row, 4);
                            equipmentTable.setValueAt(existingEquipment.getConditionStatus(), row, 5);
                        }
                    }
                }
                
                // Save changes (to be implemented with serialization)
                // Equipment.saveEquipment(equipmentList);
                
                dialog.dispose();
                showInfoMessage(isNewEquipment ? "Equipment added successfully." : "Equipment updated successfully.");
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
     *  equipment The equipment to maintain
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
        JButton saveButton = createStyledButton("Schedule", SECONDARY_COLOR, null);
        JButton cancelButton = createStyledButton("Cancel", NEUTRAL_COLOR, null);
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update equipment status
                equipment.setAvailabilityStatus((String) newStatusBox.getSelectedItem());
                
                // Update table
                int row = equipmentTable.getSelectedRow();
                if (row >= 0) {
                    equipmentTable.setValueAt(equipment.getAvailabilityStatus(), row, 4);
                }
                
                // Save maintenance record (to be implemented)
                // Create and save a Maintenance object
                
                // Save equipment changes (to be implemented)
                // Equipment.saveEquipment(equipmentList);
                
                dialog.dispose();
                showInfoMessage("Maintenance scheduled successfully.");
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
     * Get next available equipment ID
     * @return The next available ID
     */
    private int getNextEquipmentId() {
        int maxId = 0;
        for (Equipment equipment : equipmentList) {
            if (equipment.getEquipmentId() > maxId) {
                maxId = equipment.getEquipmentId();
            }
        }
        return maxId + 1;
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EquipmentManagementGUI());
    }
}