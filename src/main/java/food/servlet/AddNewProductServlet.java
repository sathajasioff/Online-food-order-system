package food.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import food.connection.DbCon;
import food.dao.ProductDao;
import food.model.Product;

@WebServlet("/Add-new-product")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class AddNewProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDao;

    @Override
    public void init() {
        try {
            Connection con = DbCon.getConnection();
            productDao = new ProductDao(con); // Pass the connection to the ProductDao
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Handle the connection error
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Add New Product</title>");
        // Inline CSS for styling
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 20px; }");
        out.println("h1 { color: #333; text-align: center; margin-bottom: 30px; }");
        out.println("form { max-width: 600px; margin: auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
        out.println("label { font-weight: bold; margin-bottom: 5px; display: block; color: #555; }");
        out.println("input[type='text'], input[type='number'], input[type='file'] { width: 100%; padding: 10px; margin: 10px 0 20px 0; border: 1px solid #ccc; border-radius: 5px; }");
        out.println("input[type='submit'] { background-color: #28a745; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; }");
        out.println("input[type='submit']:hover { background-color: #218838; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        
        out.println("<h1>Add New Product</h1>");
        out.println("<form action='Add-new-product' method='post' enctype='multipart/form-data'>");

        out.println("<label for='name'>Product Name:</label>");
        out.println("<input type='text' id='name' name='name' required><br>");

        out.println("<label for='category'>Category:</label>");
        out.println("<input type='text' id='category' name='category' required><br>");

        out.println("<label for='price'>Price:</label>");
        out.println("<input type='number' id='price' name='price' step='0.01' required><br>");

        out.println("<label for='image'>Product Image:</label>");
        out.println("<input type='file' id='image' name='image' accept='image/*' required><br>");

        out.println("<input type='submit' value='Add Product'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters using request.getParameter() for non-file fields
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String priceStr = request.getParameter("price");

        // Validate the form fields
        if (name == null || category == null || priceStr == null || name.isEmpty() || category.isEmpty() || priceStr.isEmpty()) {
            request.setAttribute("errorMessage", "Please fill out all fields.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Handle image file upload
        Part filePart = request.getPart("image");
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("errorMessage", "No image file uploaded. Please upload a product image.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        String fileName = extractFileName(filePart);

        // Define the upload path
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        // Save the uploaded file
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        // Convert price from String to double
        double price;
        try {
            price = Double.parseDouble(priceStr.trim());
            if (price < 0) throw new IllegalArgumentException("Price cannot be negative.");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Invalid price format. Please enter a valid number.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Create a new Product object
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setImage("uploads/" + fileName); // Relative path to the image

        // Add the product to the database
        boolean isAdded = productDao.addProduct(product);
        if (isAdded) {
            response.sendRedirect("index.jsp"); // Redirect to success page
        } else {
            request.setAttribute("errorMessage", "Unable to add product to the database. Please check your input.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }


    // Helper method to extract file name from the Part object
    private String extractFileName(Part filePart) {
        String contentDisposition = filePart.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 2, token.length() - 1).replace("\"", "");
            }
        }
        return null;
    }
}
