package db;

// Import necessary Java SQL and other classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import GUI.Books;
import GUI.CommonConstants;

public class LibraryDb {

    // Static connection object to interact with the database
    private static Connection conn;

    // Static block to initialize the connection when the class is first loaded
    static {
        try {
            // Establish a connection to the database using credentials from CommonConstants
            conn = DriverManager.getConnection(
                CommonConstants.DB_URL,       // Database URL
                CommonConstants.DB_USERNAME,  // Database username
                CommonConstants.DB_PASSWORD   // Database password
            );
        } catch (SQLException e) {
            // Print stack trace if connection fails
            e.printStackTrace();
        }
    }

    // Method to add a new book to the database
    public static boolean addBook(String title, String author, String status, int addedBy, String dateAdded) {
        try {
            // Prepare SQL statement for inserting a new book record
            PreparedStatement insertBook = conn.prepareStatement(
                "INSERT INTO books (title, author, status, added_by, date_added) VALUES (?, ?, ?, ?, ?)"
            );
            // Set values for each parameter in the SQL statement
            insertBook.setString(1, title);     // Set the title
            insertBook.setString(2, author);    // Set the author
            insertBook.setString(3, status);    // Set the status
            insertBook.setInt(4, addedBy);      // Set the ID of the user who added the book
            insertBook.setString(5, dateAdded); // Set the date when the book was added

            // Execute the insert statement and get the number of affected rows
            int rowsAffected = insertBook.executeUpdate();
            // Return true if one or more rows were affected (i.e., book was added successfully)
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Print stack trace if an SQL exception occurs
            e.printStackTrace();
        }
        // Return false if an exception occurred or no rows were affected
        return false;
    }

    // Method to remove a book from the database based on its ID
    public static boolean removeBook(int id) {
        try {
            // Prepare SQL statement for deleting a book record by ID
            PreparedStatement deleteBook = conn.prepareStatement(
                "DELETE FROM books WHERE id = ?"
            );
            // Set the ID parameter for the delete statement
            deleteBook.setInt(1, id);

            // Execute the delete statement and get the number of affected rows
            int rowsAffected = deleteBook.executeUpdate();
            // Return true if one or more rows were affected (i.e., book was removed successfully)
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Print stack trace if an SQL exception occurs
            e.printStackTrace();
        }
        // Return false if an exception occurred or no rows were affected
        return false;
    }

    // Method to retrieve all books from the database
    public static List<Books> getAllBooks() {
        // List to hold all books retrieved from the database
        List<Books> books = new ArrayList<>();

        try {
            // Prepare SQL statement for selecting all book records
            PreparedStatement getAllBooks = conn.prepareStatement(
                "SELECT * FROM books"
            );
            // Execute the query and get the result set
            ResultSet resultSet = getAllBooks.executeQuery();

            // Iterate through the result set and create Book objects
            while (resultSet.next()) {
                // Retrieve book details from the result set
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String status = resultSet.getString("status");
                int addedBy = resultSet.getInt("added_by");
                String dateAdded = resultSet.getString("date_added");

                // Create a Book object with the retrieved data
                Books book = new Books(id, title, author, status, addedBy, dateAdded);
                // Add the Book object to the list
                books.add(book);
            }
            // Close the result set
            resultSet.close();
        } catch (SQLException e) {
            // Print stack trace if an SQL exception occurs
            e.printStackTrace();
        }

        // Return the list of books
        return books;
    }
    
    
    public static boolean borrowBook(int bookId, String borrowedDate) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE books SET status = 'Borrowed', borrowed_Date = ? WHERE id = ?"
            );

            stmt.setString(1, borrowedDate);
            stmt.setInt(2, bookId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    
    public static boolean returnBook(int bookId, String returnedDate) {
        try {
            // Corrected SQL query: removed the extra comma before the WHERE clause
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE books SET status = 'Available', return_date = ? WHERE id = ?"
            );
            
            stmt.setString(1, returnedDate);
            stmt.setInt(2, bookId);
            
            int rowsUpdated = stmt.executeUpdate();
            // Return true if the table has been updated
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
