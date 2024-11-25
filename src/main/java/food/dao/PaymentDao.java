package food.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import food.model.Payment;
import java.sql.Date;

public class PaymentDao {
    private Connection con;

    public PaymentDao(Connection con) {
        this.con = con;
    }
        
    public boolean savePayment(Payment payment) {
        String query = "INSERT INTO payment (name, email, card_number, expiry_date, cvv, address) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, payment.getName());
            ps.setString(2, payment.getEmail());
            ps.setString(3, payment.getCardNumber());
            
            // Convert LocalDate to java.sql.Date
            Date sqlExpiryDate = Date.valueOf(payment.getExpiryDate());
            ps.setDate(4, sqlExpiryDate);
            
            ps.setString(5, payment.getCvv());
            ps.setString(6, payment.getAddress());
            
            // Log the values being inserted
            System.out.println("Executing query: " + query);
            System.out.println("Values: " + payment.getName() + ", " + payment.getEmail() + ", " + payment.getCardNumber() + ", " + sqlExpiryDate + ", " + payment.getCvv() + ", " + payment.getAddress());
            
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0; // Return true if payment was saved
        } catch (SQLException e) {
            // Log detailed error information
            System.err.println("Error while saving payment: " + e.getMessage());
            e.printStackTrace();
            return false; // Return false if an error occurred
        }
    }
    
    public Payment selectPaymentById(int id) {
        String query = "SELECT * FROM payment WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Create a new Payment object and populate it with data from the ResultSet
                String name = rs.getString("name");
                String email = rs.getString("email");
                String cardNumber = rs.getString("card_number");
                Date expiryDate = rs.getDate("expiry_date");
                String cvv = rs.getString("cvv");
                String address = rs.getString("address");

                return new Payment(id, name, email, cardNumber, expiryDate.toLocalDate(), cvv, address);
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving payment by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Return null if no payment is found with the specified ID
    }
    
    public List<Payment> selectAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payment"; // Adjust the SELECT query if you want specific columns

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Create a new Payment object and populate it with data from the ResultSet
                int id = rs.getInt("id"); // Assuming there's an 'id' column in your table
                String name = rs.getString("name");
                String email = rs.getString("email");
                String cardNumber = rs.getString("card_number");
                
                // Handle expiryDate to avoid NullPointerException
                LocalDate expiryDate = null;
                Date sqlExpiryDate = rs.getDate("expiry_date");
                if (sqlExpiryDate != null) {
                    expiryDate = sqlExpiryDate.toLocalDate();
                }

                String cvv = rs.getString("cvv");
                String address = rs.getString("address");

                // Add the payment to the list
                Payment payment = new Payment(id, name, email, cardNumber, expiryDate, cvv, address);
                payments.add(payment);
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving payments: " + e.getMessage());
            e.printStackTrace(); // Consider using a logging framework instead
        }
        return payments; // Return the list of payments
    }

    
    public boolean deletePayment(int paymentId) {
        String query = "DELETE FROM payment WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, paymentId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if payment was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurred
        }
    }

    public boolean updatePayment(Payment payment) {
        String query = "UPDATE payment SET name = ?, email = ?, card_number = ?, expiry_date = ?, cvv = ?, address = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, payment.getName());
            ps.setString(2, payment.getEmail());
            ps.setString(3, payment.getCardNumber());
            ps.setDate(4, Date.valueOf(payment.getExpiryDate())); // Convert LocalDate to java.sql.Date
            ps.setString(5, payment.getCvv());
            ps.setString(6, payment.getAddress());
            ps.setInt(7, payment.getId()); // Set the ID for the payment to be updated
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if payment was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurred
        }
    }
}
