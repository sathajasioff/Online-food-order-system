package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import food.connection.DbCon;
import food.dao.ProductDao;
import food.model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Initialize database connection
        Connection con = null;
		try {
			con = DbCon.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (con == null) {
            response.getWriter().write("Database connection is not available.");
            return;
        }

        // Fetch all products using the DAO
        ProductDao productDao = new ProductDao(con);
        List<Product> productList = productDao.getAllProducts();
        
        // Set response content type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Write HTML to display the products
        out.println("<html><head><title>Product List</title></head><body>");
        out.println("<h1>Available Products</h1>");
        
        if (productList.isEmpty()) {
            out.println("<p>No products available.</p>");
        } else {
            out.println("<table border='1' cellpadding='10'>");
            out.println("<thead><tr>");
            out.println("<th>Product ID</th>");
            out.println("<th>Product Name</th>");
            out.println("<th>Category</th>");
            out.println("<th>Price</th>");
            out.println("<th>Image</th>");
            out.println("</tr></thead>");
            out.println("<tbody>");
            
            for (Product product : productList) {
                out.println("<tr>");
                out.println("<td>" + product.getId() + "</td>");
                out.println("<td>" + product.getName() + "</td>");
                out.println("<td>" + product.getCategory() + "</td>");
                out.println("<td>" + product.getPrice() + "</td>");
                out.println("<td><img src='" + product.getImage() + "' alt='" + product.getName() + "' width='100'/></td>");
                out.println("</tr>");
            }
            
            out.println("</tbody></table>");
        }
        
        out.println("</body></html>");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
