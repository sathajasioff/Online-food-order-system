package food.servlet;

import food.dao.UserDao;
import food.model.User;
import food.connection.DbCon;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/user-details")
public class UserDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = null;

        try {
            // Get connection and create UserDao
            Connection con = DbCon.getConnection();
            UserDao udao = new UserDao(con);
            // Retrieve all users
            users = udao.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving user details.");
        }

        // Set users list in request attribute to forward to JSP
        request.setAttribute("users", users);

        // Forward request to userDetails.jsp
        request.getRequestDispatcher("userDetails.jsp").forward(request, response);
    }
}
