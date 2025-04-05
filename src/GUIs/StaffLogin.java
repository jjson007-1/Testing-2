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
            
            // Authenticate
            Staff staff = authenticateStaff(username, password);
            
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
                    "Invalid username or password, or you don't have staff privileges.", 
                    "Login Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Authenticate staff with data manager
     */
    private Staff authenticateStaff(String username, String password) {
        ArrayList<User> users = dataManager.getUsers();
        
        for (User user : users) {
            // Check if it's a Staff instance
            if (user instanceof Staff) {
                Staff staff = (Staff) user;
                
                // Check username and password
                if ((staff.getUserName().equals(username) || staff.getEmail().equals(username)) 
                        && staff.getPassword().equals(password)) {
                    return staff;
                }
            }
        }
        
        return null; // No matching staff found
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        new StaffLogin();
    }
}