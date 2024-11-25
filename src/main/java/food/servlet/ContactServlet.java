package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import food.connection.DbCon;
import food.dao.ContactDao;

/**
 * Servlet implementation class ContactServlet
 */
@WebServlet("/contact")
public class ContactServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        // Establish a database connection using the DbCon class
        try (Connection con = DbCon.getConnection()) {
            if (con == null) {
                throw new Exception("Database connection is null");
            }
            ContactDao contactDao = new ContactDao(con);
            boolean contactSuccess = contactDao.saveContactDetails(name, email, subject, message);
            
            if (contactSuccess) {
                response.sendRedirect("index.jsp"); // Redirect to a success page
            } else {
                request.setAttribute("contactError", "Submission failed. Please try again.");
                request.getRequestDispatcher("contact.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("contactError", "An error occurred during submission. Please try again.");
            request.getRequestDispatcher("contact.jsp").forward(request, response);
        }
    }

}
