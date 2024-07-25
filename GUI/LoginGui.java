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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import db.JDBC;
public class LoginGui extends Structure {
	// ELEMENTS
	private JTextField emailField;
	private JPasswordField passwordField;
	private JButton loginButton;
	
	// LABELS
	private JLabel emailLabel;
	private JLabel passwordLabel;
	private JLabel registerLabel;
	private JLabel mainPanelLabel;
	
	public LoginGui() {
		super("Login Form", new BorderLayout());
		initializeGui();
	}
	
	private void initializeGui() {
		// TOP PANEL
		JPanel topPanel = new JPanel();
		topPanel.setBackground(CommonConstants.SECONDARY_COLOR);
		JLabel title = new JLabel("Login");
		title.setFont(new Font("Dialog", Font.BOLD, 28));
		title.setBorder(new EmptyBorder(20,0,20,0));
		title.setForeground(CommonConstants.TEXT_COLOR);
		topPanel.add(title);
		add(topPanel, BorderLayout.NORTH);
		
		// CENTER PANEL
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		// INITIALIZE FIELDS
		emailField = new JTextField();
		emailField.setFont(new Font("Arial", Font.PLAIN, 15));
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 15));

		// FIELDS LABEL
	    emailLabel = new JLabel("Email:");
	    emailLabel.setFont(new Font("Dialog", Font.BOLD, 14));
	    
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Dialog", Font.BOLD,14));
        
        mainPanelLabel = new JLabel("Main Panel");
        mainPanelLabel.setForeground(Color.BLUE);
        mainPanelLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanelLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanelLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				LoginGui.this.dispose();
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
		registerLabel = new JLabel("Don't have an account? Register here");
		registerLabel.setForeground(Color.BLUE);
		registerLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		registerLabel.addMouseListener(new MouseListener() {
	
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginGui.this.dispose();
				new RegisterGui().setVisible(true);
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
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Dialog", Font.BOLD, 15));
        loginButton.setBackground(CommonConstants.SECONDARY_COLOR);
        loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String email = emailField.getText();
				String password = new String(passwordField.getPassword());
				
                // check database if the username and password combo is valid
				if(JDBC.login(email, password)) {
					UserSession.setCurrentUserEmail(email);
					String userRole = JDBC.checkRole(email);
					UserSession.setCurrentUserRole(userRole);
					 JOptionPane.showMessageDialog(LoginGui.this,
	                            "Login Successful!");
					 
					 LoginGui.this.dispose();
					 new MainFrame().setVisible(true);
	            	}else{
	                    // login failed
	                    JOptionPane.showMessageDialog(LoginGui.this,
	                    		"Login Failed...");
	                }

			}
        	
        });
        

		// FIELDS SIZE
		Dimension textFieldSize = new Dimension(400,40);
		emailField.setPreferredSize(textFieldSize);
		emailField.setMaximumSize(textFieldSize);
		passwordField.setPreferredSize(textFieldSize);
		passwordField.setMaximumSize(textFieldSize);	

		//BUTTON SIZE
        Dimension buttonSize = new Dimension(200, 30);
        loginButton.setPreferredSize(buttonSize);
        loginButton.setMaximumSize(buttonSize);
        
		// FIELDS OWN PANELS
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passwordPanel.setMaximumSize(new Dimension(400, 30));
        passwordPanel.add(passwordLabel);
        
        
		JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		emailPanel.setMaximumSize(new Dimension(400,30));
		emailPanel.add(emailLabel);

		emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(centerPanel, BorderLayout.CENTER);
		
		
		centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(emailPanel);
        centerPanel.add(emailField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(passwordPanel);
        centerPanel.add(passwordField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(registerLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(mainPanelLabel);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(loginButton);
        centerPanel.add(Box.createVerticalStrut(10));

	}
	
		
	private boolean validateUserInput 	(String email, String password) {
	    // all fields must have a value
        if(email.length() == 0  || password.length() == 0) return false;
        // passes validation
        return true;

	}
}
