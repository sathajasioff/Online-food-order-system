package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import food.connection.DbCon;
import food.dao.PaymentDao;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/delete-payment")
public class DeletePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PaymentDao paymentDao;

    @Override
    public void init() throws ServletException {
        try {
            // Establish a connection to the database using DbCon
            Connection connection = DbCon.getConnection();
            // Initialize the paymentDao object by passing the connection
            paymentDao = new PaymentDao(connection);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Database connection error: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the payment ID from the request
        String paymentIdStr = request.getParameter("id");
        
        if (paymentIdStr != null) {
            try {
                int paymentId = Integer.parseInt(paymentIdStr); // Parse the payment ID
                boolean deleteSuccess = paymentDao.deletePayment(paymentId); // Call delete method

                if (deleteSuccess) {
                    response.sendRedirect("payment-details"); // Redirect to the payment details page after successful deletion
                } else {
                    request.setAttribute("deleteError", "Failed to delete payment. Please try again.");
                    request.getRequestDispatcher("payment-details").forward(request, response); // Forward to details page on error
                }
            } catch (NumberFormatException e) {
                request.setAttribute("deleteError", "Invalid payment ID.");
                request.getRequestDispatcher("payment-details").forward(request, response);
            }
        } else {
            request.setAttribute("deleteError", "Payment ID is missing.");
            request.getRequestDispatcher("payment-details").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        // Optionally, you can close the database connection here if needed
    }
}
