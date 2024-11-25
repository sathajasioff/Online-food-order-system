package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import food.model.Cart;

/**
 * Servlet implementation class QuantityIncDecServlet
 */
@WebServlet("/quantity-inc-dec")
public class QuantityIncDecServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Corrected: action should be retrieved from the request, not response
            String action = request.getParameter("action");
            int id = Integer.parseInt(request.getParameter("id"));

            ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");

            if (action != null && id >= 1) {
                if (action.equals("inc")) {
                    for (Cart c : cart_list) {
                        if (c.getId() == id) {
                            int quantity = c.getQuantity();
                            quantity++; // Increment quantity
                            c.setQuantity(quantity);
                            break; // Exit the loop after updating the item
                        }
                    }
                } else if (action.equals("dec")) {
                    for (Cart c : cart_list) {
                        if (c.getId() == id && c.getQuantity() > 1) {
                            int quantity = c.getQuantity();
                            quantity--; // Decrement quantity
                            c.setQuantity(quantity);
                            break; 
                        }
                    }
                }
            }

            // Redirect to cart page after updating the quantity
            response.sendRedirect("cart.jsp");
        }
    }
}
