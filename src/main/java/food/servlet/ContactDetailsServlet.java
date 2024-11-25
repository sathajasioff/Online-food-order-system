package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import food.connection.DbCon;
import food.dao.ContactDao;
import food.model.Contact;

/**
 * Servlet implementation class ContactDetailsServlet
 */
@WebServlet("/contact-details")
public class ContactDetailsServlet extends HttpServlet {
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
            throw new ServletException("Database connection error: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Contact> contactList;
        try {
            contactList = contactDao.getAllContacts(); // Call getAllContacts() from contactDao
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch contacts: " + e.getMessage());
            return; // Exit the method early on error
        }

        // Set the content type for the response
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            // Start generating HTML
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Contact Management</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }");
            out.println("h1 { text-align: center; color: tomato; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
            out.println("th { background-color: black; color: white; }");
            out.println("tr:hover { background-color: #f5f5f5; }");
            out.println(".button { background-color: #4CAF50; border: none; color: white; padding: 10px 15px; text-align: center; text-decoration: none; display: inline-block; margin: 5px; cursor: pointer; }");
            out.println(".delete-button { background-color: #f44336; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Contact Management</h1>");
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Subject</th><th>Message</th><th>Actions</th></tr>");

            // Iterate over the contact list and create table rows
            if (contactList != null && !contactList.isEmpty()) { // Check if contactList is not null or empty
                for (Contact contact : contactList) {
                    out.println("<tr>");
                    out.println("<td>" + contact.getId() + "</td>");
                    out.println("<td>" + contact.getName() + "</td>");
                    out.println("<td>" + contact.getEmail() + "</td>");
                    out.println("<td>" + contact.getSubject() + "</td>");
                    out.println("<td>" + contact.getMessage() + "</td>");
                    out.println("<td>");
                    // Add action links
                    out.println("<a href='update-contact?id=" + contact.getId() + "' class='button'>Update</a>");
                    out.println("<a href='contact-delete?id=" + contact.getId() + "' class='button delete-button'>Delete</a>");
                    out.println("</td>");
                    out.println("</tr>");
                }
            } else {
                out.println("<tr><td colspan='6'>No contacts available.</td></tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
