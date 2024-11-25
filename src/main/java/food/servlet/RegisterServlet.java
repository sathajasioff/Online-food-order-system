package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import food.dao.UserDao;
import food.connection.DbCon;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("pwd"); // Ensure "pwd" matches your form

        // Check if any required fields are missing
        if (name == null || email == null || password == null || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("signupError", "All fields are required.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return; // Stop further processing
        }

        // Establish a database connection
        try (Connection con = DbCon.getConnection()) {
            // Create a new UserDao object with the connection
            UserDao userDao = new UserDao(con);

            // Register the user
            boolean registrationSuccess = userDao.registerUser(name, email, password);

            if (registrationSuccess) {
                response.sendRedirect("index.jsp"); // Success, redirect to the home page
            } else {
                request.setAttribute("signupError", "Registration failed. Email may already exist.");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("signupError", "An error occurred during registration. Please try again.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }
}
