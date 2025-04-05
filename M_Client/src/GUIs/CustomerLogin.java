package GUIs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class CustomerLogin extends JFrame implements ActionListener {
	private JPanel leftPanel, rightPanel;
	private JButton loginBtn, registerBtn, submitButton, enterButton, nextButton;
	private JLabel userName, userPassword, forgotPasswordLabel, firstName, lastName, addressLabel, emailLabel,
			phoneLabel;
	private JTextField userNameField, firstNameField, lastNameField, addressField, emailField, phoneField;
	private JPasswordField userPasswordField, confirmPasswordField;
	private JRadioButton female, male;
	private ButtonGroup gender;

	public CustomerLogin() {
		this.setTitle("Login");
		this.setSize(600, 350);
		this.setResizable(false);
		this.setLayout(null);

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
		if (e.getSource() == registerBtn) {
			styleButton(loginBtn, new Color(70, 70, 70));
			styleButton(registerBtn, new Color(255, 100, 50));
			rightPanel.removeAll();
			rightPanel.revalidate();
			rightPanel.repaint();
			showRegisterForm();
			this.setTitle("Register");
		}
		// 50, 100, 200 blue
		if (e.getSource() == loginBtn) {
			styleButton(loginBtn, new Color(255, 100, 50));
			styleButton(registerBtn, new Color(70, 70, 70));
			rightPanel.removeAll();
			rightPanel.revalidate();
			rightPanel.repaint();
			showLoginForm();
			this.setTitle("Login");
		}

		if (e.getSource() == enterButton) {

		}

		if (e.getSource() == submitButton) {

		}

		if (e.getSource() == nextButton) {
			rightPanel.removeAll();
			rightPanel.revalidate();
			rightPanel.repaint();
			showNextRegister();
		}
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

	private void showLoginForm() {

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
		forgotPasswordLabel.setForeground(new Color(163, 29, 29));
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

	private void showNextRegister() {
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
