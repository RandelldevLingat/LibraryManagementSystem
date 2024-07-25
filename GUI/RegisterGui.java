package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import db.JDBC;

public class RegisterGui extends Structure{

	private JTextField usernameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JButton registerButton;
	
	private JLabel emailLabel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel loginLabel;
	private JLabel mainPanelLabel;
	private JLabel roleLabel;
	private JLabel memberLabel;
	private JLabel adminLabel;
	
	private JRadioButton memberRadio;
	private JRadioButton adminRadio;
	
	public RegisterGui() {
		super("Register Form", new BorderLayout());
		initializeGui();
	}
	
	private void initializeGui() {
		// TOP PANEL
		JPanel topPanel = new JPanel();
		topPanel.setBackground(CommonConstants.SECONDARY_COLOR);
		
		JLabel title = new JLabel("Register");
		title.setFont(new Font("Dialog", Font.BOLD, 28));
		title.setBorder(new EmptyBorder(20,0,20,0));
		title.setForeground(CommonConstants.TEXT_COLOR);
		topPanel.add(title);
		add(topPanel, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		// INITIALIZE FIELDS
		usernameField = new JTextField();
		usernameField.setFont(new Font("Arial", Font.PLAIN, 15));

		emailField = new JTextField();
		emailField.setFont(new Font("Arial", Font.PLAIN, 15));
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 15));

		// RADIOS 
		memberRadio = new JRadioButton("Member");	
		memberRadio.setFont(new Font("Arial", Font.PLAIN, 12));
		adminRadio = new JRadioButton("Admin");
		adminRadio.setFont(new Font("Arial", Font.PLAIN, 12));
		
		ButtonGroup roleGroup = new ButtonGroup();	
		roleGroup.add(memberRadio);
		roleGroup.add(adminRadio);
		
		// SETS THE DEFAULT SELECTED RADIO
		memberRadio.setSelected(true);
		
		// FIELDS LABEL
		usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		
	    emailLabel = new JLabel("Email:");
	    emailLabel.setFont(new Font("Dialog", Font.BOLD, 14));
	    
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Dialog", Font.BOLD,14));
        
        roleLabel = new JLabel("Role:  ");
        roleLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        
        mainPanelLabel = new JLabel("Main Panel");
        mainPanelLabel.setForeground(Color.BLUE);
        mainPanelLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanelLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanelLabel.addMouseListener(new MouseListener() {
        	
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterGui.this.dispose();
				new MainFrame().setVisible(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
		loginLabel = new JLabel("Have an account already? Login here");
		loginLabel.setForeground(Color.BLUE);
		loginLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginLabel.addMouseListener(new MouseListener() {
	
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterGui.this.dispose();
				new LoginGui().setVisible(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
        // BUTTONS
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Dialog", Font.BOLD, 15));
        registerButton.setBackground(CommonConstants.SECONDARY_COLOR);
        
        registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String email = emailField.getText();
				String password = new String(passwordField.getPassword());
		        String role = memberRadio.isSelected() ? "member" : "admin";  // Get selected role
				// if the validation is successfull then it will go through inside the if statement
				if(validateUserInput(username,email,password, roleGroup)) {
					if(JDBC.register(username, password,email,role)) {
						RegisterGui.this.dispose();
						
						new LoginGui().setVisible(true);
						// create a result dialog
                        JOptionPane.showMessageDialog(RegisterGui.this,
                                "Registered Account Successfully!");
					}else {
	                        // register failed (likely due to the user already existing in the database)
	                        JOptionPane.showMessageDialog(RegisterGui.this,
	                                "Error: Username already taken");
					}
				}else {
					 // invalid user input
                    JOptionPane.showMessageDialog(RegisterGui.this,
                            "Error: Username must be at least 6 characters \n" +
                                    "and/or Passwords must match");

				}
			}
        	
        });
		// FIELDS SIZE
		Dimension textFieldSize = new Dimension(400,40);
		usernameField.setPreferredSize(textFieldSize);
		usernameField.setMaximumSize(textFieldSize);
		emailField.setPreferredSize(textFieldSize);
		emailField.setMaximumSize(textFieldSize);
		passwordField.setPreferredSize(textFieldSize);
		passwordField.setMaximumSize(textFieldSize);	

		//BUTTON SIZE
        Dimension buttonSize = new Dimension(200, 30);
        registerButton.setPreferredSize(buttonSize);
        registerButton.setMaximumSize(buttonSize);
        
		// FIELDS OWN PANELS
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        usernamePanel.setMaximumSize(new Dimension(400,30));
        usernamePanel.add(usernameLabel);
        
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passwordPanel.setMaximumSize(new Dimension(400, 30));
        passwordPanel.add(passwordLabel);
        
        
		JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		emailPanel.setMaximumSize(new Dimension(400,30));
		emailPanel.add(emailLabel);

		JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
		rolePanel.setMaximumSize(new Dimension(400,30));
		rolePanel.add(roleLabel);
		rolePanel.add(memberRadio);
		rolePanel.add(adminRadio);
		
		usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        memberRadio.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminRadio.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(centerPanel, BorderLayout.CENTER);
		
		
		centerPanel.add(Box.createVerticalStrut(50));
		centerPanel.add(usernamePanel);
		centerPanel.add(usernameField);
		centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(emailPanel);
        centerPanel.add(emailField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(passwordPanel);
        centerPanel.add(passwordField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(rolePanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(loginLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(mainPanelLabel);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(registerButton);
        centerPanel.add(Box.createVerticalStrut(10));

	}
	
	
	private boolean validateUserInput(String username,String email, String password, ButtonGroup roleGroup) {
		if(username.length() == 0 || email.length() == 0 || password.length() == 0 ) return false;
		
		if(username.length() < 6) return false;
		
		if(roleGroup.getSelection() == null) return false;
		
		return true;
	}
}
