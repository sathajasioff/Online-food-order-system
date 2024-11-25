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
 * Servlet implementation class ContactDelete
 */
@WebServlet("/contact-delete")
public class ContactDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ContactDao contactDao;

    @Override
    public void init() throws ServletException {
        try {
            // Establish a connection to the database using DbCon
            Connection connection = DbCon.getConnection();
            // Initialize the contactDao object by passing the connection
            contactDao = new ContactDao(connection);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing contact ID.");
            return; // Exit the method early on error
        }

        try {
            int id = Integer.parseInt(idParam);
            contactDao.deleteContact(id); // Call deleteContact from contactDao
            response.sendRedirect("contact-details"); // Redirect after successful deletion
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid contact ID.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to delete contact.");
        }
    }
}
