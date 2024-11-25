package food.servlet;

import jakarta.servlet.ServletException;
import food.model.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try(PrintWriter out = response.getWriter()){
            // Create a new cart list
            ArrayList<Cart> cartList = new ArrayList<>();
            
            // Get product ID from request
            int id = Integer.parseInt(request.getParameter("id"));
            Cart cm = new Cart();
            cm.setId(id);
            cm.setQuantity(1);
            
            // Get the session
            HttpSession session = request.getSession();
            ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
            
            if(cart_list == null) {
                // If cart-list doesn't exist, create a new one and add the item
                cartList.add(cm);
                session.setAttribute("cart-list", cartList);  // Use the correct cartList
                response.sendRedirect("index.jsp");
            } else {
                cartList = cart_list;
                boolean exist = false;
                
                // Check if the item already exists in the cart
                for(Cart c : cart_list) {
                    if(c.getId() == id) {
                        exist = true;
                        out.println("<h3 style='color:crimson; text-align:center'>Item already exists in cart. <a href='cart.jsp'>Go to cart page</a></h3>");
                        break;  // Exit loop if item is found
                    }
                }
                
                if(!exist) {
                    // If item does not exist, add it to the cart
                    cartList.add(cm);
                    session.setAttribute("cart-list", cartList);  // Update session with modified cart list
                    response.sendRedirect("index.jsp");
                }
            }
        }
    }
}
