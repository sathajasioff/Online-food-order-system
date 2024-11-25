package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import food.connection.DbCon;
import food.dao.PaymentDao;
import food.model.Payment;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/payment-servlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String cardNumber = request.getParameter("cardNumber");
        String expiryDateString = request.getParameter("expiryDate");
        LocalDate expiryDate = LocalDate.parse(expiryDateString + "-01"); // Add a day to parse correctly
        String cvv = request.getParameter("cvv");
        String address = request.getParameter("address");

        // Create Payment object
        // Removed LocalDate.now() for paymentDate if not using it in the constructor
        Payment payment = new Payment(0, name, email, cardNumber, expiryDate, cvv, address);

        System.out.println("Payment processing started with: " + payment);

        // Establish a database connection using the DbCon class
        try (Connection con = DbCon.getConnection()) {
            if (con == null) {
                throw new Exception("Database connection is null");
            }
            PaymentDao paymentDao = new PaymentDao(con);
            boolean paymentSuccess = paymentDao.savePayment(payment);

            if (paymentSuccess) {
                System.out.println("Payment successful, redirecting to index.jsp...");
                response.sendRedirect("index.jsp");
            } else {
                request.setAttribute("paymentError", "Payment submission failed. Please try again.");
                request.getRequestDispatcher("payment.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("paymentError", "Database error occurred during submission. Please try again.");
            request.getRequestDispatcher("payment.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("paymentError", "An error occurred during submission. Please try again.");
            request.getRequestDispatcher("payment.jsp").forward(request, response);
        }
    }
}
