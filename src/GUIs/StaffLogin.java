package GUIs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import serialization.DataManager;
import serialization.Staff;
import serialization.User;

public class StaffLogin extends JFrame implements ActionListener {
    private JLabel username, passwordLabel, forgotPasswordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton enterButton;
    
    // Data manager
    private DataManager dataManager;
    
    public StaffLogin() {
        this.setTitle("Staff Login");
        this.setSize(600, 350);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(0, 112, 116));
        
        // Get data manager instance
        dataManager = DataManager.getInstance();
        
        username = new JLabel("Username");
        username.setBounds(170, 70, 140, 25);
        username.setForeground(Color.WHITE);
        this.add(username);
        
        usernameField = new JTextField();
        usernameField.setBounds(250, 70, 170, 25);
        this.add(usernameField);
        
        
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(170, 120, 100, 25);
        passwordLabel.setForeground(Color.WHITE);
        this.add(passwordLabel);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(250, 120, 170, 25);
        this.add(passwordField);
        
        // Forgot Password Label
        forgotPasswordLabel = new JLabel("<html><u>Forgot Password?</u></html>");
        forgotPasswordLabel.setForeground(new Color (163, 29, 29));
        forgotPasswordLabel.setBounds(250, 148, 120, 20);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(forgotPasswordLabel);
        
        enterButton = new JButton("Enter");
        enterButton.setBounds(190, 210, 250, 30);
        styleButton(enterButton, new Color(255, 100, 50));
        enterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        enterButton.addActionListener(this);
        this.add(enterButton);
        
        
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // Method to Style Buttons
    private void styleButton(JButton button, Color bgColor) {
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setOpaque(true);
        button.setBorderPainted(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == enterButton) {
            // Handle login
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter both username and password.", 
                    "Login Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Authenticate user
            User user = authenticateUser(username, password);
            if (user != null) {
                // Find associated staff
                Staff staff = findStaffByUserId(user);
                
                if (staff != null) {
                    // Login successful, navigate to staff dashboard
                    JOptionPane.showMessageDialog(this, 
                        "Login successful. Welcome " + staff.getFullName() + "!", 
                        "Login Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    this.dispose();
                    new StaffDashboard(staff.getStaffId());
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Staff account not found.", 
                        "Login Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Invalid username or password.", 
                    "Login Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Authenticate user with data manager
     */
    private User authenticateUser(String username, String password) {
        ArrayList<User> users = dataManager.getUsers();
        
        for (User user : users) {
            // Check username match
            if (user.getUserName().equals(username) || user.getEmail().equals(username)) {
                // Check password match
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        
        return null; // No matching user found
    }
    
    /**
     * Find staff associated with user
     */
    private Staff findStaffByUserId(User user) {
        // In a real application, this would query from a staff table that links to users
        // For this demo, we'll create a sample staff member
        
        // Check if the username contains "staff" as a simple way to identify staff accounts
        if (user.getUserName().toLowerCase().contains("staff") || 
            user.getEmail().toLowerCase().contains("staff")) {
            
            Staff staff = new Staff();
            staff.setStaffId(1);
            staff.setFullName(user.getFullName());
            staff.setEmail(user.getEmail());
            staff.setPhone(user.getPhone());
            staff.setPosition("Manager");
            staff.setStatus("Active");
            
            return staff;
        }
        
        return null;
    }
    
    public static void main(String[] args) {
        new StaffLogin();
    }
}