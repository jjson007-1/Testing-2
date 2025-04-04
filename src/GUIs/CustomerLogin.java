package GUIs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import serialization.Customer;
import serialization.DataManager;
import serialization.User;

public class CustomerLogin extends JFrame implements ActionListener{
    private JPanel leftPanel, rightPanel;
    private JButton loginBtn, registerBtn, submitButton, enterButton, nextButton;
    private JLabel userName, userPassword, forgotPasswordLabel, firstName, lastName, addressLabel, emailLabel, phoneLabel;
    private JTextField userNameField, firstNameField, lastNameField, addressField, emailField, phoneField;
    private JPasswordField userPasswordField, confirmPasswordField;
    private JRadioButton female, male;
    private ButtonGroup gender;
    
    // Data manager
    private DataManager dataManager;
    
    public CustomerLogin(){
        this.setTitle("Login");
        this.setSize(600, 350);
        this.setLayout(null);
        
        // Get data manager instance
        dataManager = DataManager.getInstance();
        
        // Left Panel
        leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 112, 116));
        leftPanel.setBounds(0, 0, 150, 350);
        leftPanel.setLayout(null);
        this.add(leftPanel);
        
        // Login Button (left panel)
        loginBtn = new JButton("Login");
        styleButton(loginBtn, new Color(255, 100, 50));
        loginBtn.setBounds(20, 100, 110, 30);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(this);
        leftPanel.add(loginBtn);
        
        // Register Button (left panel)
        registerBtn = new JButton("Register");
        styleButton(registerBtn, new Color(70, 70, 70));
        registerBtn.setBounds(20, 150, 110, 30);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.addActionListener(this);
        leftPanel.add(registerBtn);
        
        
        // Right Panel
        rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(150, 0, 450, 350);
        rightPanel.setLayout(null);
        this.add(rightPanel);
        
        showLoginForm();
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
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
        if(e.getSource() == registerBtn) {
            styleButton(loginBtn, new Color(70, 70, 70));
            styleButton(registerBtn, new Color(255, 100, 50));
            rightPanel.removeAll();
            rightPanel.revalidate();
            rightPanel.repaint();
            showRegisterForm();
            this.setTitle("Register");
        }
        
        if(e.getSource() == loginBtn) {
            styleButton(loginBtn, new Color(255, 100, 50));
            styleButton(registerBtn, new Color(70, 70, 70));
            rightPanel.removeAll();
            rightPanel.revalidate();
            rightPanel.repaint();
            showLoginForm();
            this.setTitle("Login");
        }
        
        if(e.getSource() == enterButton) {
            // Handle login
            String username = userNameField.getText().trim();
            String password = new String(userPasswordField.getPassword());
            
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
                // Find associated customer
                Customer customer = findCustomerByUser(user);
                
                if (customer != null) {
                    // Set current user in BaseGUI for session management
                    BaseGUI.setCurrentUser(customer.getId(), "customer", customer.getUserName());
                    
                    // Login successful, navigate to customer dashboard
                    JOptionPane.showMessageDialog(this, 
                        "Login successful. Welcome " + customer.getFullName() + "!", 
                        "Login Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    this.dispose();
                    new CustomerDashboard(customer.getId());
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Customer account not found.", 
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
        
        if(e.getSource() == submitButton) {
            registerUser();
        }
        
        if(e.getSource() == nextButton) {
            if (validateFirstRegistrationStep()) {
                rightPanel.removeAll();
                rightPanel.revalidate();
                rightPanel.repaint();
                showNextRegster();
            }
        }
    }
    
    /**
     * Authenticate user with data manager
     * @param username Username or email to authenticate
     * @param password Password to check
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
     * Find customer associated with user
     * @param user User object to find customer for
     * @return Customer object if found, null otherwise
     */
    private Customer findCustomerByUser(User user) {
        // In a real database implementation, we would use JOIN queries
        // But for this implementation, we'll use a direct check first
        
        // Since our Customer objects extend User, we could implement this as a direct check:
        if (user instanceof Customer) {
            return (Customer) user;
        }
        
        // For a more realistic implementation where User and Customer are stored separately:
        ArrayList<Customer> customers = dataManager.getCustomers();
        
        for (Customer customer : customers) {
            // Match by email (ideally would match by user_id)
            if (customer.getEmail().equals(user.getEmail()) ||
                customer.getUserName().equals(user.getUserName())) {
                return customer;
            }
        }
        
        return null;
    }
    
    /**
     * Validate first registration step fields
     */
    private boolean validateFirstRegistrationStep() {
        String username = userNameField.getText().trim();
        String password = new String(userPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a username.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a password.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, 
                "Passwords do not match.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Check if username already exists
        ArrayList<User> users = dataManager.getUsers();
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                JOptionPane.showMessageDialog(this, 
                    "Username already exists. Please choose another.", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Register new user
     */
    private void registerUser() {
        // Get user input
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String gender = female.isSelected() ? "Female" : "Male";
        
        // Validation
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || 
            phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create new user
        String fullName = firstName + " " + lastName;
        String username = userNameField.getText().trim();
        String password = new String(userPasswordField.getPassword());
        
        // Create base User
        User newUser = new User(fullName, username, password, 
                               Integer.parseInt(phone.replaceAll("[^0-9]", "")), 
                               email, address);
        
        // Create Customer that extends User
        Customer newCustomer = new Customer(newUser, dataManager.getNextId("customer"));
        
        // Save to data manager
        ArrayList<User> users = dataManager.getUsers();
        users.add(newUser);
        dataManager.saveUsers(users);
        
        ArrayList<Customer> customers = dataManager.getCustomers();
        customers.add(newCustomer);
        dataManager.saveCustomers(customers);
        
        JOptionPane.showMessageDialog(this, 
            "Registration successful! You can now login.", 
            "Registration Success", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // Switch to login form
        styleButton(loginBtn, new Color(255, 100, 50));
        styleButton(registerBtn, new Color(70, 70, 70));
        rightPanel.removeAll();
        rightPanel.revalidate();
        rightPanel.repaint();
        showLoginForm();
        this.setTitle("Login");
    }
    
    private void showRegisterForm() {
        userName = new JLabel("Username");
        userName.setBounds(70, 80, 130, 25);
        rightPanel.add(userName);
        
        userNameField = new JTextField();
        userNameField.setBounds(180, 80, 150, 25);
        userNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        rightPanel.add(userNameField);
        
        userPassword = new JLabel("Password");
        userPassword.setBounds(70, 120, 100, 25);
        rightPanel.add(userPassword);
        
        userPasswordField = new JPasswordField();
        userPasswordField.setBounds(180, 120, 150, 25);
        userPasswordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        rightPanel.add(userPasswordField);
        
        userPassword = new JLabel("Confirm Password");
        userPassword.setBounds(50, 160, 110, 25);
        rightPanel.add(userPassword);
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(180, 160, 150, 25);
        confirmPasswordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        rightPanel.add(confirmPasswordField);
        
        nextButton = new JButton("Next");
        nextButton.setBounds(100, 220, 250, 30);
        styleButton(nextButton, new Color(255, 100, 50));
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextButton.addActionListener(this);
        rightPanel.add(nextButton);
    }
    
    private void showLoginForm(){
        
        // userName Label & Field
        userName = new JLabel("Username or Email");
        userName.setBounds(50, 80, 130, 25);
        rightPanel.add(userName);
        
        userNameField = new JTextField();
        userNameField.setBounds(180, 80, 150, 25);
        userNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        rightPanel.add(userNameField);
        
        // Password Label & Field
        userPassword = new JLabel("Password");
        userPassword.setBounds(80, 120, 100, 25);
        rightPanel.add(userPassword);
          
        userPasswordField = new JPasswordField();
        userPasswordField.setBounds(180, 120, 150, 25);
        userPasswordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        rightPanel.add(userPasswordField);
        
        // Forgot Password Label
        forgotPasswordLabel = new JLabel("<html><u>Forgot Password?</u></html>");
        forgotPasswordLabel.setForeground(new Color (163, 29, 29));
        forgotPasswordLabel.setBounds(180, 148, 120, 20);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightPanel.add(forgotPasswordLabel);
        
        enterButton = new JButton("Enter");
        enterButton.setBounds(100, 190, 250, 30);
        styleButton(enterButton, new Color(255, 100, 50));
        enterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        enterButton.addActionListener(this);
        rightPanel.add(enterButton);
    }
    
    private void showNextRegster() {
        firstName = new JLabel("First Name");
        firstName.setBounds(50, 20, 110, 25);
        rightPanel.add(firstName);
        
        firstNameField = new JTextField();
        firstNameField.setBounds(150, 20, 150, 25);
        firstNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        rightPanel.add(firstNameField);
        
        lastName = new JLabel("Last Name");
        lastName.setBounds(50, 60, 110, 25);
        rightPanel.add(lastName);
        
        lastNameField = new JTextField();
        lastNameField.setBounds(150, 60, 150, 25);
        lastNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        rightPanel.add(lastNameField);
        
        emailLabel = new JLabel("Email");
        emailLabel.setBounds(50, 100, 110, 25);
        rightPanel.add(emailLabel);
        
        emailField = new JTextField();
        emailField.setBounds(150, 100, 150, 25);
        emailField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        rightPanel.add(emailField);
        
        
        female = new JRadioButton("Female");
        female.setBounds(50, 130, 80, 25);
        female.setBackground(Color.WHITE);
        rightPanel.add(female);
        
        male = new JRadioButton("Male");
        male.setBounds(160, 130, 80, 25);
        male.setBackground(Color.WHITE);
        rightPanel.add(male);
        
        gender = new ButtonGroup();
        gender.add(female);
        gender.add(male);
        
        phoneLabel = new JLabel("Phone");
        phoneLabel.setBounds(50, 170, 110, 25);
        rightPanel.add(phoneLabel);
        
        phoneField = new JTextField(10);
        phoneField.setBounds(150, 170, 150, 25);
        phoneField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        rightPanel.add(phoneField);
        
        addressLabel = new JLabel("Address");
        addressLabel.setBounds(50, 210, 110, 25);
        rightPanel.add(addressLabel);
        
        addressField = new JTextField();
        addressField.setBounds(150, 210, 150, 25);
        addressField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        rightPanel.add(addressField);
        
        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 260, 250, 30);
        styleButton(submitButton, new Color(255, 100, 50));
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.addActionListener(this);
        rightPanel.add(submitButton);
    }
    
    public static void main(String[] args) {
        new CustomerLogin();
    }
}