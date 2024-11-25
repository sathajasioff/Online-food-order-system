package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import food.model.User;
import food.connection.DbCon;
import food.dao.UserDao;

@WebServlet("/users")
public class Users extends HttpServlet {
    private static final long serialVersionUID = 1L;  
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        try {
            // Establish a connection to the database using DbCon
            Connection connection = DbCon.getConnection();
            // Initialize the userDao object by passing the connection
            userDao = new UserDao(connection);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch all users from the database
        List<User> userList = null; // Initialize userList to null
        try {
            userList = userDao.getAllUsers(); // Call getAllUsers() from userDao
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch users.");
            return; // Exit the method early on error
        }

        // Set the content type for the response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Start generating HTML
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>User Management</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }");
        out.println("h1 { text-align: center; color: tomato; }"); // Changed to tomato
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
        out.println("th { background-color: black; color: white; }"); // Changed to ash
        out.println("tr:hover { background-color: #f5f5f5; }");
        out.println(".button { background-color: #4CAF50; border: none; color: white; padding: 10px 15px; text-align: center; text-decoration: none; display: inline-block; margin: 5px; cursor: pointer; }");
        out.println(".delete-button { background-color: #f44336; }");
        out.println("</style>");


        // Adding JavaScript for confirmation dialogs
        out.println("<script type='text/javascript'>");
        out.println("function confirmUpdate() { return confirm('Are you sure you want to update this user?'); }");
        out.println("function confirmDelete() { return confirm('Are you sure you want to delete this user?'); }");
        out.println("</script>");

        out.println("</head>");
        out.println("<body>");
        out.println("<h1>User Management</h1>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Actions</th></tr>");

        // Iterate over the user list and create table rows
        if (userList != null) { // Check if userList is not null
            for (User user : userList) {
                out.println("<tr>");
                out.println("<td>" + user.getId() + "</td>");
                out.println("<td>" + user.getName() + "</td>");
                out.println("<td>" + user.getEmail() + "</td>");
                out.println("<td>");
                // Add confirmation for Update
                out.println("<a href='updateUser?id=" + user.getId() + "' class='button' onclick='return confirmUpdate()'>Update</a>");
                // Add confirmation for Delete
                out.println("<a href='delete-user?id=" + user.getId() + "' class='button delete-button' onclick='return confirmDelete()'>Delete</a>");
                out.println("</td>");
                out.println("</tr>");
            }
        }

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}
