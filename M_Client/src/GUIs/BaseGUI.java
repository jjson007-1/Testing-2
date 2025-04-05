package GUIs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Base GUI class that provides common functionality and styling
 * for all GUI screens in the Event Scheduling System.
 */
public abstract class BaseGUI extends JFrame {
    // Common color scheme
    protected static final Color PRIMARY_COLOR = new Color(0, 112, 116);    // Teal
    protected static final Color SECONDARY_COLOR = new Color(255, 100, 50); // Orange
    protected static final Color NEUTRAL_COLOR = new Color(70, 70, 70);     // Dark Gray
    protected static final Color LIGHT_COLOR = new Color(245, 245, 245);    // Light Gray
    
    // Common fonts
    protected static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 24);
    protected static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    protected static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    protected static final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 12);
    
    // Session data (could be expanded to a proper session class)
    protected static int currentUserId = 0;
    protected static String currentUserType = null;
    protected static String currentUsername = null;
    
    /**
     * Constructor for the base GUI
     *  title The title of the window
     *  width The width of the window
     *  height The height of the window
     */
    public BaseGUI(String title, int width, int height) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }
        
        this.setTitle(title);
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center on screen
    }
    
    /**
     * Creates a styled button with the given text and color
     *  text The button text
     *  color The button background color
     *  listener The action listener for the button
     * @return A styled JButton
     */
    protected JButton createStyledButton(String text, Color color, ActionListener listener) {
        JButton button = new JButton(text);
        styleButton(button, color);
        if (listener != null) {
            button.addActionListener(listener);
        }
        return button;
    }
    
    /**
     * Applies standard styling to a button
     *  button The button to style
     *  bgColor The background color
     */
    protected void styleButton(JButton button, Color bgColor) {
        button.setForeground(Color.WHITE);
        button.setFont(NORMAL_FONT);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * Creates a styled text field with standard properties
     * @return A styled JTextField
     */
    protected JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(NEUTRAL_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        field.setFont(NORMAL_FONT);
        return field;
    }
    
    /**
     * Creates a styled panel with the given color
     *  color The background color
     * @return A styled JPanel
     */
    protected JPanel createStyledPanel(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        return panel;
    }
    
    /**
     * Displays an error message dialog
     *  message The error message
     */
    protected void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Displays an information message dialog
     *  message The information message
     */
    protected void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Information",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Displays a confirmation dialog
     *  message The confirmation message
     * @return true if the user confirmed, false otherwise
     */
    protected boolean showConfirmDialog(String message) {
        int result = JOptionPane.showConfirmDialog(
            this,
            message,
            "Confirm",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Navigates to another screen
     *  screen The screen to navigate to
     */
    protected void navigateTo(final JFrame screen) {
        SwingUtilities.invokeLater(() -> {
            screen.setVisible(true);
            dispose(); // Close current window
        });
    }
    
    /**
     * Validates if a component's text is not empty
     *  component The component to check
     *  fieldName The name of the field (for error message)
     * @return true if valid, false otherwise
     */
    protected boolean validateNotEmpty(JTextField component, String fieldName) {
        if (component.getText().trim().isEmpty()) {
            showErrorMessage(fieldName + " cannot be empty.");
            component.requestFocus();
            return false;
        }
        return true;
    }
    
    /**
     * Sets the current user session data
     *  userId The user ID
     *  userType The user type (customer, staff)
     *  username The username
     */
    protected static void setCurrentUser(int userId, String userType, String username) {
        currentUserId = userId;
        currentUserType = userType;
        currentUsername = username;
    }
    
    /**
     * Clears the current user session data (logout)
     */
    protected static void clearCurrentUser() {
        currentUserId = 0;
        currentUserType = null;
        currentUsername = null;
    }
    
    /**
     * Initializes the UI components
     * This method must be implemented by subclasses
     */
    protected abstract void initComponents();
    
    /**
     * Sets up the layout of components
     * This method must be implemented by subclasses
     */
    protected abstract void setupLayout();
    
    /**
     * Sets up event handlers
     * This method must be implemented by subclasses
     */
}
