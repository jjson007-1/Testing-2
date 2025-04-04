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
                Staff staff = findStaffByUser(user);
                
                if (staff != null) {
                    // Set current user in BaseGUI for session management
                    BaseGUI.setCurrentUser(staff.getStaffId(), "staff", staff.getUserName());
                    
                    // Login successful, navigate to staff dashboard
                    JOptionPane.showMessageDialog(this, 
                        "Login successful. Welcome " + staff.getFullName() + "!", 
                        "Login Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    this.dispose();
                    new StaffDashboard(staff.getStaffId());
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Staff account not found. This user is not authorized as staff.", 
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
     * @param username Username or email
     * @param password Password
     * @return User object if authenticated, null otherwise
     */
    private User authenticateUser(String username, String password) {
        ArrayList<User> users = dataManager.getUsers();
        
        for (User user : users) {
            // Check username match (or email)
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
     * @param user User object to find staff for
     * @return Staff object if found, null otherwise
     */
    private Staff findStaffByUser(User user) {
        // In a real application with a proper database, we would query the staff table
        // with the user's ID. For this implementation, we'll use a simple check.
        
        // Since our Staff objects extend User, we could implement this as a direct check:
        if (user instanceof Staff) {
            return (Staff) user;
        }
        
        // For a more realistic implementation where User and Staff are stored separately:
        // Check if the user has a staff account based on email (or other identifier)
        // This could be improved by having a proper user_id foreign key in the staff table
        ArrayList<Staff> staffMembers = new ArrayList<>(); // This would come from dataManager.getStaff() in a real implementation
        
        // For demo purposes, create a sample staff if username contains "staff"
        if (user.getUserName().toLowerCase().contains("staff") || 
            user.getEmail().toLowerCase().contains("staff")) {
            
            Staff staff = new Staff(user, 1, "Manager", "Active", "Administrator");
            return staff;
        }
        
        return null;
    }
    
    public static void main(String[] args) {
        new StaffLogin();
    }
}