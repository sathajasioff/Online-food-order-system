package food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

import food.dao.ProductDao;

@WebServlet("/uploadFood")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class UploadFoodServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Directory to save uploaded files (adjust this path)
    private static final String UPLOAD_DIRECTORY = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String priceStr = request.getParameter("price");

        // Check if priceStr is null or empty before parsing
        if (priceStr == null || priceStr.trim().isEmpty()) {
            response.sendRedirect("errorPage.jsp?error=Price is required");
            return;
        }

        // Price validation
        double price = 0.0;
        try {
            price = Double.parseDouble(priceStr.trim());  // Trim any whitespace
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("errorPage.jsp?error=Invalid price format");
            return;
        }

        // Handle the file upload (image)
        Part imagePart = request.getPart("image"); // Retrieves <input type="file" name="image">
        
        if (imagePart == null || imagePart.getSize() == 0) {
            response.sendRedirect("errorPage.jsp?error=Image is required");
            return;
        }
        
        String fileName = extractFileName(imagePart);  // Extract the file name
        String savePath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(savePath);
        if (!uploadDir.exists()) uploadDir.mkdir();  // Create the directory if it does not exist

        // Save the file to the specified path
        String filePath = savePath + File.separator + fileName;
        imagePart.write(filePath);  // Save the uploaded file to this location

        // Insert product into the database (image path is the filePath)
        try {
            ProductDao productDao = new ProductDao(null);  // Ensure ProductDao is properly initialized
            productDao.insertProduct(name, category, price, filePath);  // Save image path in DB
            response.sendRedirect("upload.jsp");  // Redirect to success page
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("errorPage.jsp?error=Database error");
        }
    }

    // Utility method to extract the file name from the Part header
    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
