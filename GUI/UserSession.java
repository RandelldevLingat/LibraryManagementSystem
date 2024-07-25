package GUI;

public class UserSession {

    // Store the current user's email
    private static String currentUserEmail = null;
    
    // Store the current user's role (optional)
    private static String currentUserRole = null;

    // Set the current user's email
    public static void setCurrentUserEmail(String email) {
        currentUserEmail = email;
    }
    
    // Get the current user's email
    public static String getCurrentUserEmail() {
        return currentUserEmail;
    }
    
    // Set the current user's role (optional)
    public static void setCurrentUserRole(String role) {
        currentUserRole = role;
    }
    
    // Get the current user's role (optional)
    public static String getCurrentUserRole() {
        return currentUserRole;
    }
    
    // Clear the session (logout)
    public static void clearSession() {
        currentUserEmail = null;
        currentUserRole = null;
    }
    
    // Check if a user is logged in
    public static boolean isLoggedIn() {
        return currentUserEmail != null;
    }
}
