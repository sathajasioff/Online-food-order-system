package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import food.connection.DbCon;
import food.dao.OrderDao;
import food.model.User;
import food.model.Cart;
import food.model.Order; // Make sure this import is correct

@WebServlet("/order-now")
public class OrderNowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis()); // Use current date

            User auth = (User) request.getSession().getAttribute("auth");
            if (auth != null) {
                String productID = request.getParameter("id");
                int productQuantity = Integer.parseInt(request.getParameter("quantity"));
                if (productQuantity <= 0) {
                    productQuantity = 1;
                }

                Order orderModel = new Order();
                orderModel.setId(Integer.parseInt(productID));
                orderModel.setUid(auth.getId());
                orderModel.setQuantity(productQuantity);
                orderModel.setDate(formatter.format(date));

                OrderDao orderDao = new OrderDao(DbCon.getConnection());
                boolean result = orderDao.insertOrder(orderModel);

                if (result) {
                	
                	ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
                    if (cart_list != null) {
                        // Iterate through the cart list to find and remove the item
                        for (Cart c : cart_list) {
                            if (c.getId() == Integer.parseInt(productID)) {
                                cart_list.remove(c);
                                break;  // Exit the loop after removing the item
                            }
                        }
                    }
                    response.sendRedirect("orders.jsp");
                } else {
                    out.println("Order failed. Please try again."); // Improved error message
                }
            } else {
                response.sendRedirect("login.jsp");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle the error, e.g., redirect or inform the user
        } catch (Exception e) {
            e.printStackTrace(); // Handle database-related errors
        } 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
