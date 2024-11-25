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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate; // Import for LocalDate parsing
import java.time.format.DateTimeFormatter; // Import for date formatting

@WebServlet("/update-payment")
public class UpdatePayment extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PaymentDao paymentDao; // Declare paymentDao as a class-level variable

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbCon.getConnection(); // Obtain a valid connection
            paymentDao = new PaymentDao(connection); // Initialize PaymentDao with the connection
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error initializing PaymentDao", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Payment existingPayment = null;

        try {
            existingPayment = paymentDao.selectPaymentById(id); // Fetch payment by ID
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging this instead
            response.sendRedirect("error.jsp"); // Redirect to an error page if there's an issue
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Generate the update form with the current payment data pre-filled
        out.println("<html><head><title>Update Payment</title>");
        
        // Inline CSS for styling
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }");
        out.println("form { background-color: white; padding: 20px; margin: 0 auto; border: 1px solid #ccc; width: 400px; border-radius: 10px; box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1); }");
        out.println("h2 { text-align: center; color: #333; }");
        out.println("label { display: block; margin-bottom: 10px; color: #333; }");
        out.println("input[type='text'] { width: 100%; padding: 10px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 5px; }");
        out.println("input[type='submit'] { background-color: #4CAF50; color: white; border: none; padding: 10px 20px; cursor: pointer; border-radius: 5px; width: 100%; }");
        out.println("input[type='submit']:hover { background-color: #45a049; }");
        out.println("</style>");
        
        out.println("</head><body>");
        out.println("<h2>Update Payment</h2>");
        out.println("<form action='update-payment' method='POST'>");
        out.println("<input type='hidden' name='id' value='" + existingPayment.getId() + "'>");
        out.println("<label for='name'>Name:</label>");
        out.println("<input type='text' id='name' name='name' value='" + existingPayment.getName() + "' required>");
        out.println("<label for='email'>Email:</label>");
        out.println("<input type='text' id='email' name='email' value='" + existingPayment.getEmail() + "' required>");
        out.println("<label for='cardNumber'>Card Number:</label>");
        out.println("<input type='text' id='cardNumber' name='cardNumber' value='" + existingPayment.getCardNumber() + "' required>");
        out.println("<label for='expiryDate'>Expiry Date (yyyy-mm-dd):</label>");
        out.println("<input type='text' id='expiryDate' name='expiryDate' value='" + existingPayment.getExpiryDate() + "' required>");
        out.println("<label for='cvv'>CVV:</label>");
        out.println("<input type='text' id='cvv' name='cvv' value='" + existingPayment.getCvv() + "' required>");
        out.println("<label for='address'>Address:</label>");
        out.println("<input type='text' id='address' name='address' value='" + existingPayment.getAddress() + "' required>");
        out.println("<input type='submit' value='Update Payment'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle the form submission for updating the payment
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String cardNumber = request.getParameter("cardNumber");
        String expiryDateStr = request.getParameter("expiryDate"); // Keep it in yyyy-mm-dd format
        String cvv = request.getParameter("cvv");
        String address = request.getParameter("address");

        // Parse the expiryDate from String to LocalDate
        LocalDate expiryDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define the format
            expiryDate = LocalDate.parse(expiryDateStr, formatter); // Convert String to LocalDate
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging this instead
            response.sendRedirect("error.jsp"); // Redirect to an error page if date parsing fails
            return;
        }

        // Create a new Payment object with the updated details
        Payment payment = new Payment(id, name, email, cardNumber, expiryDate, cvv, address); // Ensure your Payment constructor matches

        try {
            boolean isUpdated = paymentDao.updatePayment(payment); // Update the payment details in the database
            if (!isUpdated) {
                throw new Exception("Payment update failed");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging this instead
            response.sendRedirect("error.jsp"); // Redirect to an error page if an issue occurs
            return;
        }

        // Redirect back to the payment list after updating
        response.sendRedirect("payment-details"); // Adjust this to your actual payment list URL
    }

    @Override
    public void destroy() {
        // Optionally close the database connection here if needed
    }
}
