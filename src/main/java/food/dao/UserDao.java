package food.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import food.model.User;

public class UserDao {
	
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public UserDao(Connection con) {
		super();
		this.con = con;
	}
	
	


	public User userLogin(String email, String password) {
        User user = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // Fetch user by email
            String query = "SELECT * FROM users WHERE email=?";
            pst = this.con.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();
            
            // Check if user exists and verify password
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(password)) { // Check if the stored password matches the input password
                    user = new User(); // Assuming User class is your model
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                }
            } else {
                System.out.println("No user found with email: " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during login: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return user;
    }



	
	public boolean registerUser(String name, String email, String password) {
	    boolean result = false;

	    try {
	        // Check if the user already exists by email (optional, but recommended)
	        String checkQuery = "SELECT * FROM users WHERE email = ?";
	        PreparedStatement checkPst = this.con.prepareStatement(checkQuery);
	        checkPst.setString(1, email);
	        ResultSet rs = checkPst.executeQuery();
	        if (rs.next()) {
	            // Email already exists
	            return false; // Registration failed due to duplicate email
	        }

	        // If email does not exist, proceed with the insertion
	        String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
	        PreparedStatement pst = this.con.prepareStatement(query);
	        pst.setString(1, name);
	        pst.setString(2, email);
	        pst.setString(3, password); // Store plain text password, but hash in production
	        int rowsAffected = pst.executeUpdate();
	        result = rowsAffected > 0; // If rows are affected, registration is successful
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result; // Return the result of the operation
	}

	
	public List<User> getAllUsers() throws SQLException {
	    List<User> users = new ArrayList<>();
	    String query = "SELECT id, name, email FROM users"; // Be explicit about the columns

	    // Ensure the connection is valid
	    if (con == null || con.isClosed()) {
	        throw new SQLException("Database connection is not available.");
	    }

	    try (PreparedStatement stmt = con.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            User user = new User();
	            user.setId(rs.getInt("id"));
	            user.setName(rs.getString("name"));
	            user.setEmail(rs.getString("email"));
	            users.add(user);
	        }
	    } catch (SQLException e) {
	        throw new SQLException("Error fetching users: " + e.getMessage(), e);
	    }

	    return users;
	}


    // Method to get user by ID
	 public User getUserById(int id) {
	        User user = null;
	        String query = "SELECT * FROM users WHERE id = ?";

	        try (PreparedStatement statement = con.prepareStatement(query)) {
	            statement.setInt(1, id);
	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                user = new User();
	                user.setId(resultSet.getInt("id"));
	                user.setName(resultSet.getString("name"));
	                user.setEmail(resultSet.getString("email"));
	                user.setPassword(resultSet.getString("password"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return user;
	    }

	    public boolean updateUser(User user) throws SQLException {
	        String query = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
	        try (PreparedStatement stmt = con.prepareStatement(query)) {
	            stmt.setString(1, user.getName());
	            stmt.setString(2, user.getEmail());
	            stmt.setString(3, user.getPassword());
	            stmt.setInt(4, user.getId());
	            int rowsUpdated = stmt.executeUpdate();
	            return rowsUpdated > 0; // Returns true if at least one row was updated
	        }
	    }
	


    // Method to delete a user by ID
    public boolean deleteUser(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?"; // Adjust your query as needed
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0; // Returns true if at least one row was deleted
        }
    }
}