package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import food.connection.DbCon;
import food.dao.UserDao;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Declare userDao as a class-level variable
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        try {
            // Establish a connection to the database using DbCon
            Connection connection = DbCon.getConnection();
            // Initialize the userDao object by passing the connection
            userDao = new UserDao(connection); // Use class-level variable
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user ID from request parameter
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            // Attempt to delete the user from the database
            boolean deleted = userDao.deleteUser(id); // Now accessible
            if (deleted) {
                // Redirect to the user list if the deletion was successful
                response.sendRedirect("users");
            } else {
                // Send an error response if the deletion failed
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User deletion failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to delete user.");
        }
    }
}
