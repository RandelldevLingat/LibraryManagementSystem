package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import GUI.*;
public class JDBC {

	public static boolean register(String username,String email ,String password, String role) {
		try{
			if(!checkUser(username)) {
				Connection connection = DriverManager.getConnection(CommonConstants.DB_URL, 
						CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
				
				PreparedStatement insertUser = connection.prepareStatement(
						"INSERT INTO " + CommonConstants.DB_USER_TABLE_NAME + "(username,email ,password,role)" + 
						"VALUES(?,?,?,?)"
						);
				insertUser.setString(1, username);
				insertUser.setString(2, password);
				insertUser.setString(3, email);
				insertUser.setString(4, role);
				
				insertUser.executeUpdate();
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public static boolean checkUser(String username) {
		try {
			Connection connection = DriverManager.getConnection(CommonConstants.DB_URL,CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
			PreparedStatement checkUserExist = connection.prepareStatement("SELECT * FROM " + CommonConstants.DB_USER_TABLE_NAME + 
					" WHERE USERNAME = ?");
			checkUserExist.setString(1, username);
			ResultSet resultSet = checkUserExist.executeQuery();
			// this checks if a user is found with the same name if not it will return false
			if(!resultSet.isBeforeFirst()) {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	public static boolean login(String email,String password) {
		try {
			Connection connection = DriverManager.getConnection(CommonConstants.DB_URL, 
					CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
			PreparedStatement validateUser = connection.prepareStatement("SELECT * FROM " + 
					CommonConstants.DB_USER_TABLE_NAME + " WHERE EMAIL = ? AND PASSWORD = ?");
			validateUser.setString(1,email);
			validateUser.setString(2, password);
			
			ResultSet resultSet = validateUser.executeQuery();
			
            // if no matching user is found then it will return false
			if(!resultSet.isBeforeFirst()) {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	// LIBRARY SYSTEM
	
	public static String checkRole(String email) {
		String role = null;
		
		try {
			Connection connection = DriverManager.getConnection(CommonConstants.DB_URL, 
					CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
			PreparedStatement getRole = connection.prepareStatement("SELECT role FROM " + 
					CommonConstants.DB_USER_TABLE_NAME + " WHERE EMAIL = ?");
			getRole.setString(1, email);
			
			ResultSet resultSet = getRole.executeQuery();
			
			if(resultSet.next()) {
				role = resultSet.getString("role");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return role;
	}
	
}
