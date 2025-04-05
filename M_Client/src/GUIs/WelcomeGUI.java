package GUIs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class WelcomeGUI extends JFrame implements ActionListener{
	private JLabel welcomeLabel;
	private JTextArea description;
	private JComboBox<String> userDropdown;
	
	
	public WelcomeGUI() {		
		this.setTitle("Welcome");
		this.setSize(600, 400);
		this.setLayout(null);
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(255, 100, 50));
		
		welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setBounds(120, 30, 200, 30);
        this.add(welcomeLabel);
        
        // DropDown Menu for User Selection
        String[] userOptions = {"Select User", "Customer", "Staff"};
        
        userDropdown = new JComboBox<>(userOptions);
        userDropdown.setBackground(new Color (0, 112, 116));
        userDropdown.setBounds(450, 20, 120, 25);
        userDropdown.addActionListener(this);
        this.add(userDropdown);
        
        // Description
        description = new JTextArea(
                "We provide top-quality event equipment rental services, including:\n" +
                "- Staging\n" +
                "- Lighting\n" +
                "- Power Equipment\n" +
                "- Sound Systems\n\n" +
                "Operating Hours:\n" +
                "Monday - Saturday: 9:00 AM - 8:00 PM\n" +
                "Sunday: Closed\n\n" +
                "Book your equipment today and make your event unforgettable!"
        );
        
        description.setOpaque(false);
        description.setEditable(false);
        description.setBounds(50, 60, 500, 200);
        this.add(description);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new WelcomeGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== userDropdown) {
//			userDropdown.removeItem("Staff");
			
			String selectedUser = (String) userDropdown.getSelectedItem();
            if ("Staff".equals(selectedUser)) {
                new StaffLogin();
                this.dispose();
            } else if ("Customer".equals(selectedUser)) {
                new CustomerLogin();
                this.dispose();
            }
		}
		
	}	
}