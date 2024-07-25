package GUI;
import java.awt.Color;

public class CommonConstants {

	public static final Color PRIMARY_COLOR = Color.decode("#E6E0B0");
	public static final Color SECONDARY_COLOR = Color.decode("#D2B48C");
	public static final Color TEXT_COLOR = Color.decode("#000000");
	
	
    // MY DATABASE CONNECTION INFORMATION
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/librarydb";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "12345randell";
    public static final String DB_USER_TABLE_NAME = "users";
    public static final String DB_BOOKS_TABLE_NAME = "books";
    public static final String DB_BORROWED_BOOKS_TABLE_NAME = "borrowed_books";
	
}
