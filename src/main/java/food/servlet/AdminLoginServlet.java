package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class AdminLoginServlet
 */
@WebServlet("/admin-login")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 // Admin email and password to restrict access
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "admin123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate email and password
        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password)) {
            // If valid, create session and redirect to admin.jsp
            HttpSession session = request.getSession();
            session.setAttribute("isAdmin", true);
            response.sendRedirect("admin.jsp");
        } else {
            // If invalid, redirect back to login page with error message
            response.sendRedirect("login.jsp?error=Invalid email or password");
        }
    }
}