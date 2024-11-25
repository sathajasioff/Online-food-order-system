package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import food.connection.DbCon;
import food.dao.OrderDao;
import food.model.Cart;
import food.model.Order;
import food.model.User;

@WebServlet("/check-out")
public class CheckOutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            
            // Date formatter
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date();  // Current date

            // Retrieve all cart products from session
            ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");

            // User authentication
            User auth = (User) request.getSession().getAttribute("auth");

            // Check if user is logged in and if the cart is not empty
            if (auth == null) {
                response.sendRedirect("login.jsp");
                return;  // Stop further execution if not logged in
            } else if (cart_list == null || cart_list.isEmpty()) {
                response.sendRedirect("cart.jsp");
                return;  // Stop further execution if cart is empty
            } else {
                // Initialize OrderDao to handle database operations
                OrderDao oDao;
                try {
                    oDao = new OrderDao(DbCon.getConnection());
                } catch (SQLException e) {
                    throw new ServletException("Database connection error: " + e.getMessage());
                }

                // Process each item in the cart and create corresponding orders
                boolean allOrdersProcessed = true;
                for (Cart cartItem : cart_list) {
                    Order order = new Order();
                    order.setId(cartItem.getId());  // Product ID
                    order.setUid(auth.getId());     // User ID
                    order.setQuantity(cartItem.getQuantity());  // Quantity of the product
                    order.setDate(formatter.format(date));      // Order date

                    // Insert order into database and check if successful
                    boolean result = oDao.insertOrder(order);
                    if (!result) {
                        out.println("Error processing order for product ID: " + cartItem.getId());
                        allOrdersProcessed = false;
                        break;  // Stop processing if an error occurs
                    }
                }

                // If all orders are processed successfully, clear the cart
                if (allOrdersProcessed) {
                    cart_list.clear();  // Clear cart after successful order placement
                    request.getSession().setAttribute("cart-list", cart_list);  // Update session with cleared cart
                    response.sendRedirect("orders.jsp");  // Redirect to the orders page
                } else {
                    response.sendRedirect("error.jsp");  // Optional: Redirect to an error page if processing fails
                }
            }
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            throw new ServletException("An error occurred while processing the request: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST request by calling the GET method
        doGet(request, response);
    }
}
