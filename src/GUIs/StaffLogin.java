package GUIs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class StaffLogin extends JFrame implements ActionListener {
	private JLabel username, passwordLabel, forgotPasswordLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton enterButton;
	
	public StaffLogin() {
		this.setTitle("Login");
		this.setSize(600, 350);
		this.setLayout(null);
		this.getContentPane().setBackground(new Color(0, 112, 116));
		
		username = new JLabel("Username");
		username.setBounds(170, 70, 140, 25);
		this.add(username);
		
		usernameField = new JTextField();
		usernameField.setBounds(250, 70, 170, 25);
		this.add(usernameField);
		
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(170, 120, 100, 25);
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
		if(e.getSource()== enterButton) {
			
		}
	}
	public static void main(String[] args) {
		new StaffLogin();
	}
}
