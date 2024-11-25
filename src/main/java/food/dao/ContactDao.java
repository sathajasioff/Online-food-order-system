package food.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import food.model.Contact;

public class ContactDao {
    private Connection con;

    public ContactDao(Connection con) {
        this.con = con;
    }

    public boolean saveContactDetails(String name, String email, String subject, String message) {
        // Check for null or empty fields
        if (name == null || email == null || subject == null || message == null ||
            name.isEmpty() || email.isEmpty() || subject.isEmpty() || message.isEmpty()) {
            System.out.println("All fields are required.");
            return false; // Prevent null or empty submissions
        }

        // Proceed with the insert
        String query = "INSERT INTO contact (name, email, subject, message) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, subject);
            pstmt.setString(4, message);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0; // Return true if at least one row was affected
        } catch (SQLException e) {
            System.err.println("SQL Error during insertion: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public Contact getContactById(int id) throws SQLException {
        Contact contact = null;
        String query = "SELECT * FROM contact WHERE id = ?"; // Adjust table name as needed

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    contact = new Contact();
                    contact.setId(rs.getInt("id"));
                    contact.setName(rs.getString("name"));
                    contact.setEmail(rs.getString("email"));
                    contact.setSubject(rs.getString("subject"));
                    contact.setMessage(rs.getString("message"));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching contact by ID: " + e.getMessage(), e);
        }

        return contact; // Returns null if no contact is found
    }

    // Fetch all contacts from the database
    public List<Contact> getAllContacts() throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contact"; // Adjust your table name as needed

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Contact contact = new Contact(); // Make sure to have a Contact model class
                contact.setId(rs.getInt("id")); // Assuming there's an 'id' field
                contact.setName(rs.getString("name"));
                contact.setEmail(rs.getString("email"));
                contact.setSubject(rs.getString("subject"));
                contact.setMessage(rs.getString("message"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching all contacts: " + e.getMessage(), e);
        }

        return contacts; // Returns empty list if no contacts found
    }
    
    public void updateContact(Contact contact) throws SQLException {
        String query = "UPDATE contact SET name = ?, email = ?, subject = ?, message = ? WHERE id = ?"; // Adjust table name as needed

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getEmail());
            pstmt.setString(3, contact.getSubject());
            pstmt.setString(4, contact.getMessage());
            pstmt.setInt(5, contact.getId());
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("No contact found with ID: " + contact.getId());
            }
        } catch (SQLException e) {
            throw new SQLException("Error updating contact: " + e.getMessage(), e);
        }
    }

    // Delete a contact by ID
    public void deleteContact(int id) throws SQLException {
        String query = "DELETE FROM contact WHERE id = ?"; // Adjust table name as needed

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new SQLException("No contact found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new SQLException("Error deleting contact: " + e.getMessage(), e);
        }
    }
}
