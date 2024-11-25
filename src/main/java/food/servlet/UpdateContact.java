package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import food.connection.DbCon;
import food.dao.ContactDao;
import food.model.Contact;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/update-contact")
public class UpdateContact extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ContactDao contactDao; // Declare contactDao as a class-level variable

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbCon.getConnection(); // Obtain a valid connection
            contactDao = new ContactDao(connection); // Initialize ContactDao with the connection
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error initializing ContactDao", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Contact existingContact = null;

        try {
            existingContact = contactDao.getContactById(id); // Fetch contact by ID
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging this instead
            response.sendRedirect("error.jsp"); // Redirect to an error page if there's an issue
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Generate the update form with the current contact data pre-filled
        out.println("<html><head><title>Update Contact</title>");
        
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
        out.println("<h2>Update Contact</h2>");
        out.println("<form action='update-contact' method='POST'>"); // This is correct // Match the action URL
        out.println("<input type='hidden' name='id' value='" + existingContact.getId() + "'>");
        out.println("<label for='name'>Name:</label>");
        out.println("<input type='text' id='name' name='name' value='" + existingContact.getName() + "' required>");
        out.println("<label for='email'>Email:</label>");
        out.println("<input type='text' id='email' name='email' value='" + existingContact.getEmail() + "' required>");
        out.println("<label for='subject'>Subject:</label>");
        out.println("<input type='text' id='subject' name='subject' value='" + existingContact.getSubject() + "' required>");
        out.println("<label for='message'>Message:</label>");
        out.println("<input type='text' id='message' name='message' value='" + existingContact.getMessage() + "' required>");
        out.println("<input type='submit' value='Update Contact'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle the form submission for updating the contact
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        Contact contact = new Contact(id, name, email, subject, message); // Ensure your Contact constructor matches

        try {
            contactDao.updateContact(contact); // Update the contact details in the database
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging this instead
            response.sendRedirect("error.jsp"); // Redirect to an error page if an issue occurs
            return;
        }

        // Redirect back to the contact list after updating
        response.sendRedirect("contact-details"); // Adjust this to your actual contact list URL
    }

    @Override
    public void destroy() {
        // Optionally close the database connection here if needed
    }
}
