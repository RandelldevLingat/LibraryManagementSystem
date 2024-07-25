package GUI;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import db.JDBC;

public class MainFrame extends Structure{
	private JTextField usernameField;
	private JButton registerButton;
	private JButton loginButton;
	private JButton managementButton;
	private JButton exitButton;
	
	public MainFrame() {
		super("Library Management", new BorderLayout());
		initializeGui();
	}
	
	//TODO make the database tommorow 
	private void initializeGui() {
		// TOP PANEL
		JPanel topPanel = new JPanel();
		
		topPanel.setBackground(CommonConstants.PRIMARY_COLOR);
		
		JLabel title = new JLabel("Library System");
		title.setFont(new Font("Dialog", Font.BOLD, 28));
		title.setBorder(new EmptyBorder(20,0,20,0));
		title.setForeground(CommonConstants.TEXT_COLOR);
		
		topPanel.add(title);
		add(topPanel, BorderLayout.NORTH);
		
		// CENTER PANEL
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(4,1,10,10));
		centerPanel.setBackground(CommonConstants.SECONDARY_COLOR);
	   	centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
	   	
		registerButton = new JButton("Register");
		registerButton.setBackground(CommonConstants.PRIMARY_COLOR);
		registerButton.setFont(new Font("Arial", Font.BOLD, 18));
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.dispose();
				
				new RegisterGui().setVisible(true);
			}
			
		});
		
		loginButton = new JButton("Login");
		loginButton.setBackground(CommonConstants.PRIMARY_COLOR);
		loginButton.setFont(new Font("Arial", Font.BOLD, 18));
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.dispose();
				new LoginGui().setVisible(true);
			}
		
		});
		managementButton = new JButton("Management Role");
		managementButton.setBackground(CommonConstants.PRIMARY_COLOR);
		managementButton.setFont(new Font("Arial", Font.BOLD, 18));
		managementButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String email = UserSession.getCurrentUserEmail();
				String role = JDBC.checkRole(email);
			
				if("admin".equalsIgnoreCase(role)) {
					new AdminPanel().setVisible(true);
				}else if("member".equalsIgnoreCase(role)) {
					new MemberPanel().setVisible(true);
				} else {
		            JOptionPane.showMessageDialog(MainFrame.this, "Role: " + role + ". Login first!");
		        }
			}
			
		});
		
		exitButton = new JButton("Exit");		
		exitButton.setBackground(CommonConstants.PRIMARY_COLOR);
		exitButton.setFont(new Font("Arial", Font.BOLD, 18));

		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		centerPanel.add(registerButton);
		centerPanel.add(loginButton);
		centerPanel.add(managementButton);
		centerPanel.add(exitButton);
		
		add(centerPanel, BorderLayout.CENTER);
	}
}
