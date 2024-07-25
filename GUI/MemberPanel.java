package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import db.LibraryDb;

public class MemberPanel extends Structure{

	private JTable table;
	private DefaultTableModel tableModel;
	
	private JButton borrowedButton;
	private JButton returnButton;
	public MemberPanel() {
		super("Member Panel", new BorderLayout());
		
		initializeGui();
		loadData();
	}
	
	private void initializeGui() {
		
		// TOP PANEL
		JPanel topPanel = new JPanel();
		topPanel.setBackground(CommonConstants.SECONDARY_COLOR);

        JLabel title = new JLabel("Mmeber Panel");
        title.setFont(new Font("Dialog", Font.BOLD, 28));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setForeground(CommonConstants.TEXT_COLOR);

        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);
        
        // CENTER PANEL
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        
        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        tablePanel.setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Title");
        tableModel.addColumn("Author");
        tableModel.addColumn("Status");
        tableModel.addColumn("Added By");
        tableModel.addColumn("Date Added");
       
        Dimension buttonSize = new Dimension(150,45);
        
        borrowedButton = new JButton("Borrow book");
        styleButton(borrowedButton, buttonSize);
        borrowedButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(UserSession.getCurrentUserEmail() == null) {
				    JOptionPane.showMessageDialog(MemberPanel.this, "You must be logged in to return a book.");
                    return;
				}
				// gets the selected row in the table
				int selectedRow = table.getSelectedRow();
				// if the selected row is not null then continue the borrow system
				if(selectedRow != -1) {
					int bookId = (int) table.getValueAt(selectedRow, 0);
					String borrowedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					boolean borrowed = LibraryDb.borrowBook(bookId, borrowedDate);
					// if the borrowBook db code is returns true or the borrowed happen then it will be true
					if(borrowed) {
						loadData();
					  JOptionPane.showMessageDialog(MemberPanel.this, "Book borrowed successfully!");
					}else {
	                    JOptionPane.showMessageDialog(MemberPanel.this, "Please select a book to borrow.");

					}
				}else {
                    JOptionPane.showMessageDialog(MemberPanel.this, "Please select a book to borrow.");

				}
			}
        	
        });
        returnButton = new JButton("Return book");
        styleButton(returnButton, buttonSize);
        returnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(UserSession.getCurrentUserEmail() == null) {
				    JOptionPane.showMessageDialog(MemberPanel.this, "You must be logged in to return a book.");
                    return;
				}
				int selectedRow = table.getSelectedRow();
				if(selectedRow != -1) {
					int bookId = (int) table.getValueAt(selectedRow, 0);
					String returnedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					boolean returned = LibraryDb.returnBook(bookId, returnedDate);
					// if the returnBook method in the db returs true the it will be true
					if(returned) {
						loadData(); // reload the table
						JOptionPane.showMessageDialog(MemberPanel.this, "Book returned successfully!");

					}else {
	                    JOptionPane.showMessageDialog(MemberPanel.this, "Please select a book to return.");
					}
				}else {
                    JOptionPane.showMessageDialog(MemberPanel.this, "Please select a book to return.");
				}
			}
        	
        });
        JButton backButton = new JButton("Back");
        styleButton(backButton, buttonSize);
        backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MemberPanel.this.dispose();
				
				new MainFrame().setVisible(true);
			}
        	
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scroll = new JScrollPane(table);
        tablePanel.add(scroll, BorderLayout.EAST);
        
        buttonPanel.add(borrowedButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        
        buttonPanel.add(returnButton);
        centerPanel.add(buttonPanel, BorderLayout.WEST);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.add(backButton, BorderLayout.SOUTH);
        add(centerPanel,BorderLayout.CENTER);
        }
	
	private void loadData() {
		 // Clear existing table data or refreshes the table
        tableModel.setRowCount(0);

        // Load data from database 
        // this retrieves all of the books and store in a list of books
        List<Books> books = LibraryDb.getAllBooks();
        // this iterates the list of books and retrieves the data of a book 
        for (Books book : books) {
            Object[] rowData = {book.getId(),
            					book.getTitle(),
            					book.getAuthor(),
            					book.getStatus(),
            					book.getAddedBy(),
            					book.getDateAdded() };
            // and then added them into the table as a new row
            tableModel.addRow(rowData);
        }
	}
    private void styleButton(JButton button, Dimension buttonSize) {
        button.setBackground(CommonConstants.PRIMARY_COLOR);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    }
}
