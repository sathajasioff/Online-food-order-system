package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import food.dao.UserDao;
import food.model.User;
import food.connection.DbCon; // Ensure you have this import

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/updateUser")
public class UpdateUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbCon.getConnection(); // Obtain a valid connection
            userDao = new UserDao(connection); // Initialize UserDao with the connection
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error initializing UserDao", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = null;

        try {
            existingUser = userDao.getUserById(id); // Fetch user by ID
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirect to an error page if there's an issue
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Generate the update form with the current user data pre-filled and styled
        out.println("<html><head><title>Update User</title>");
        
        // Inline CSS for styling
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }");
        out.println("form { background-color: white; padding: 20px; margin: 0 auto; border: 1px solid #ccc; width: 400px; border-radius: 10px; box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1); }");
        out.println("h2 { text-align: center; color: #333; }");
        out.println("label { display: block; margin-bottom: 10px; color: #333; }");
        out.println("input[type='text'], input[type='password'] { width: 100%; padding: 10px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 5px; }");
        out.println("input[type='submit'] { background-color: #4CAF50; color: white; border: none; padding: 10px 20px; cursor: pointer; border-radius: 5px; width: 100%; }");
        out.println("input[type='submit']:hover { background-color: #45a049; }");
        out.println("</style>");
        
        out.println("</head><body>");
        out.println("<h2>Update User</h2>");
        out.println("<form action='updateUser' method='POST'>");
        out.println("<input type='hidden' name='id' value='" + existingUser.getId() + "'>");
        out.println("<label for='name'>Name:</label>");
        out.println("<input type='text' id='name' name='name' value='" + existingUser.getName() + "' required>");
        out.println("<label for='email'>Email:</label>");
        out.println("<input type='text' id='email' name='email' value='" + existingUser.getEmail() + "' required>");
        out.println("<label for='password'>Password:</label>");
        out.println("<input type='password' id='password' name='pwd' value='" + existingUser.getPassword() + "' required>");
        out.println("<input type='submit' value='Update User'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle the form submission for updating the user
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("pwd");

        User user = new User(id, name, email, password);

        try {
            userDao.updateUser(user); // Update the user details in the database
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception (e.g., set an error message in the request and forward to an error page)
            response.sendRedirect("error.jsp");
            return;
        }

        // Redirect back to the user list after updating
        response.sendRedirect("users"); // Adjust this to your actual user list URL
    }

    @Override
    public void destroy() {
        // Optionally close the database connection here if needed
    }
}
