package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import food.connection.DbCon;
import food.dao.UserDao;
import food.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Use try-with-resources to automatically close the PrintWriter
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("email");
            String password = request.getParameter("pwd");

            UserDao udao = new UserDao(DbCon.getConnection());
            User user = udao.userLogin(email, password);

            // Check if user is not null (successful login)
            if (user != null) {
                request.getSession().setAttribute("auth", user);
                response.sendRedirect("index.jsp"); // Redirect to the main page after successful login
            } else {
                // Redirect to login page with an error message
                request.setAttribute("errorMessage", "Invalid email or password.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Log exceptions for debugging
            // Optionally, you can redirect to an error page
            response.sendRedirect("error.jsp");
        }
    }
}
