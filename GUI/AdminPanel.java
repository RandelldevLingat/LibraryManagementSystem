package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import db.LibraryDb;

public class AdminPanel extends Structure {

    private JTable table;
    private DefaultTableModel tableModel;
    public AdminPanel() {
        super("Admin Panel", new BorderLayout());
        initialize();
        loadData();
    }

    private void initialize() {
        // TOP PANEL
        JPanel topPanel = new JPanel();
        topPanel.setBackground(CommonConstants.SECONDARY_COLOR);

        JLabel title = new JLabel("Admin Panel");
        title.setFont(new Font("Dialog", Font.BOLD, 28));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setForeground(CommonConstants.TEXT_COLOR);

        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        // CENTER PANEL
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        Dimension buttonSize = new Dimension(200, 50);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton addBooksButton = new JButton("Add Book");
        styleButton(addBooksButton, buttonSize);
        addBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input fields
                JTextField titleField = new JTextField();
                JTextField authorField = new JTextField();
                String[] statuses = { "Available", "Borrowed", "Unavailable" };
                JComboBox<String> statusComboBox = new JComboBox<>(statuses);
                
                // Show input dialog
                // basically this one is the jlabel and the input field in the right side
                Object[] message = {
                    "Title:", titleField,
                    "Author:", authorField,
                    "Status:", statusComboBox
                };
                int option = JOptionPane.showConfirmDialog(null, message, "Add Book", 
                		JOptionPane.OK_CANCEL_OPTION);
                
                if (option == JOptionPane.OK_OPTION) {
                    String title = titleField.getText();
                    String author = authorField.getText();
                    String status = (String) statusComboBox.getSelectedItem();
                    int addedBy = 1; // Replace with actual user ID logic
                    String dateAdded = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    
                    // Add book to the database
                    // if the .addBook in the db class is true then the book will be added
                    boolean added = LibraryDb.addBook(title, author, status, addedBy, dateAdded);
                    if (added) {
                        loadData(); // Refresh table
                        JOptionPane.showMessageDialog(AdminPanel.this, "Book added successfully!");
                    } else {
                        JOptionPane.showMessageDialog(AdminPanel.this, "Failed to add the book.");
                    }
                }
            }
        });

        JButton removeBooksButton = new JButton("Remove Book");
        styleButton(removeBooksButton, buttonSize);
        removeBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int idToRemove = (int) table.getValueAt(selectedRow, 0); // Assuming id is in the first column
                    boolean removed = LibraryDb.removeBook(idToRemove);
                    // if the removeBook method has resulted in a true then it will remove the table row as well
                    if (removed) {
                        tableModel.removeRow(selectedRow);
                    }
                }
            }
        });


        JButton backButton = new JButton("Back");
        styleButton(backButton, buttonSize);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminPanel.this.dispose(); // Assuming AdminPanel extends JFrame or JDialog
                new MainFrame().setVisible(true); // Open the main frame or previous window
            }
        });

        buttonPanel.add(addBooksButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(removeBooksButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(buttonPanel, BorderLayout.WEST);

        // RIGHT PANEL (for table)
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Create table model with columns
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Title");
        tableModel.addColumn("Author");
        tableModel.addColumn("Status");
        tableModel.addColumn("Added By");
        tableModel.addColumn("Date Added");

        // Create JTable with the table model
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add table to a scroll pane for scrolling if necessary
        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(rightPanel, BorderLayout.CENTER);

        // Add back button to the SOUTH of centerPanel
        centerPanel.add(backButton, BorderLayout.SOUTH);

        // Add center panel to the AdminPanel
        add(centerPanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button, Dimension buttonSize) {
        button.setBackground(CommonConstants.PRIMARY_COLOR);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
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
}
