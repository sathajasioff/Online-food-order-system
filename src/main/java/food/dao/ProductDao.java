package food.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import food.connection.DbCon;
import food.model.Cart;
import food.model.Product;

public class ProductDao {

    private Connection con;
    private String query;
    private PreparedStatement pst;
    private ResultSet rs;

    public ProductDao(Connection con) {
        this.con = con;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try {
            // Check if the connection is valid
            if (this.con == null) {
                System.out.println("Database connection is null");
                return products; // Return empty list if connection is null
            }

            query = "SELECT * FROM products";
            pst = this.con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Product row = new Product();
                row.setId(rs.getInt("id"));
                row.setName(rs.getString("name"));
                row.setCategory(rs.getString("category"));
                row.setPrice(rs.getDouble("price")); // Assuming price is a valid double
                row.setImage(rs.getString("image"));
                
                products.add(row);
            }
        } catch (Exception e) {
            // Output exception to the console for debugging
            System.out.println("Error retrieving products: " + e.getMessage());
            e.printStackTrace();
        }

        return products;
    }
    
    public Product getSingleProduct(int id) {
    	Product row = null;
    	
    	try {
    		
    		query = "select * from products where id=?";
    		pst = this.con.prepareStatement(query);
    		pst.setInt(1, id);
    		rs = pst.executeQuery();
    		
    		
    		while(rs.next()) {
    			row = new Product();
    			row.setId(rs.getInt("id"));
    			row.setName(rs.getString("name"));
    			row.setCategory(rs.getString("category"));
    			row.setPrice(rs.getDouble("price"));
    			row.setImage(rs.getString("image"));
    		}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	return row;
    }

    public List<Cart> getCartProducts(ArrayList<Cart> cartList) {
        List<Cart> cartProducts = new ArrayList<>();

        // Check if the cartList is null or empty to avoid unnecessary DB calls
        if (cartList == null || cartList.isEmpty()) {
            return cartProducts;  // Return empty list if cartList is null or empty
        }

        // SQL query to fetch the product details based on the product ID
        String query = "SELECT * FROM products WHERE id=?";
        
        try {
            for (Cart item : cartList) {
                // Using try-with-resources to automatically close the PreparedStatement and ResultSet
                try (PreparedStatement pst = this.con.prepareStatement(query)) {
                    pst.setInt(1, item.getId());  // Set the product ID in the query
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            // Create and populate a Cart object
                            Cart cartItem = new Cart();
                            cartItem.setId(rs.getInt("id"));
                            cartItem.setName(rs.getString("name"));
                            cartItem.setCategory(rs.getString("category"));
                            cartItem.setPrice(rs.getDouble("price"));  // Price per unit
                            cartItem.setQuantity(item.getQuantity());  // Quantity from the cart

                            // Add the Cart object to the cartProducts list
                            cartProducts.add(cartItem);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving cart products: " + e.getMessage());
            e.printStackTrace();
        }

        return cartProducts;  // Return the list of cart products
    }


    public double getTotalPrice(ArrayList<Cart> cartList) {
        double sum = 0;

        try {
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    query = "SELECT price FROM products WHERE id=?";
                    pst = this.con.prepareStatement(query);
                    pst.setInt(1, item.getId());
                    rs = pst.executeQuery();

                    if (rs.next()) {
                        sum += rs.getDouble("price") * item.getQuantity(); // Calculate total price
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sum;
    }
    
    public void insertProduct(String name, String category, double price, String imagePath) {
        String query = "INSERT INTO products (name, category, price, image) VALUES (?, ?, ?, ?)";
        try {
            if (this.con == null || this.con.isClosed()) {
                throw new Exception("Database connection is not available");
            }

            pst = this.con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, category);
            pst.setDouble(3, price);
            pst.setString(4, imagePath); // Save the image path as a string (VARCHAR or TEXT)
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product inserted successfully.");
            } else {
                System.out.println("Failed to insert the product.");
            }
        } catch (Exception e) {
            System.out.println("SQL error inserting product: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();  // Always close the statement
            } catch (Exception e) {
                System.out.println("Error closing prepared statement: " + e.getMessage());
            }
        }
    }
    
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, category, price, image) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, product.getName());
            pst.setString(2, product.getCategory());
            pst.setDouble(3, product.getPrice());
            pst.setString(4, product.getImage());

            int rowsAffected = pst.executeUpdate(); // Execute the update statement
            return rowsAffected > 0; // Return true if a product was added successfully
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception for debugging
            return false; // Return false in case of any SQL errors
        }
    }

}
