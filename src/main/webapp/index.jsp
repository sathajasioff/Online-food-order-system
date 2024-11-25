<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="food.model.*" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="food.connection.DbCon" %>
<%@page import="food.dao.ProductDao" %>
<%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
        request.setAttribute("auth", auth);
    }

    // Initialize the products list
    List<Product> products = null;

    try {
        ProductDao pd = new ProductDao(DbCon.getConnection());
        products = pd.getAllProducts();
    } catch (Exception e) {
        out.println("Error fetching products: " + e.getMessage());
    }

    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    if (cart_list != null) {
        request.setAttribute("cart_list", cart_list);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tomato</title>
    <link rel="stylesheet" href="index.css">
    <script src="index.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%@include file="includes/header.jsp" %>
    
    <div class="header">
        <img src="images/header_img1.png" alt="headerimg" style="height: 450px; width: 90%; display: block; margin: 0 auto; border-radius: 15px;" />
        <div class="header-contents">
            <h2 class="text-center font-weight-bold display-4" style="color: white;">Order your favourite food here</h2>	
            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Sequi amet, quisquam, voluptatem iste, labore assumenda reiciendis obcaecati commodi fugiat dignissimos placeat vitae rerum quis ab dicta atque fuga. Impedit, vero.</p>
            <button ><a href="#">View Menu</a></button>
        </div>
    </div>
   
    <div class="explore-menu" id="explore-menu">
        <h1>Explore Our Menu</h1>
        <p class="explore-menu-text">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Omnis quis ut fugit consequatur deleniti dolorem perferendis iste quibusdam ea pariatur beatae.
        </p>
        <div class="explore-menu-list">
            <div class="explore-menu-list-item">
                <img class="active" src="images/menu_1.png" alt="Menu Item 1" />
                <p>Salad</p>
            </div>
            <div class="explore-menu-list-item">
                <img src="images/menu_2.png" alt="Menu Item 2" />
                <p>Rolls</p>
            </div>
            <div class="explore-menu-list-item">
                <img src="images/menu_3.png" alt="Menu Item 3" />
                <p>Deserts</p>
            </div>
            <div class="explore-menu-list-item">
                <img src="images/menu_4.png" alt="">
                <p>Sandwich</p>
            </div>
            <div class="explore-menu-list-item">
                <img src="images/menu_5.png" alt="">
                <p>Cake</p>
            </div>
            <div class="explore-menu-list-item">
                <img src="images/menu_6.png" alt="">
                <p>Pure Veg</p>
            </div>
            <div class="explore-menu-list-item">
                <img src="images/menu_7.png" alt="">
                <p>Pasta</p>
            </div>
            <div class="explore-menu-list-item">
                <img src="images/menu_8.png" alt="">
                <p>Noodles</p>
            </div>
        </div>
        <hr />
    </div>
    
   <div class="food-display-lists" id="food-display-lists">
    <%
        if (products != null && !products.isEmpty()) {
            for (Product p : products) {
    %>
                <div class="food-item" id="food-item-<%= p.getId() %>"> 
                    <h3><%= p.getName() %></h3>
                    <p>Food provides essential nutrients for overall health and well-being.</p>
                    <p>Price: <%= p.getPrice() %></p>
                    
                    <img src="images/food_4.png" alt="image">
                    <button class="btn btn-dark">
                        <a href="add-to-cart?id=<%= p.getId() %>" style="text-decoration: none; color: white;">Add to Cart</a>
                    </button>
                    <button class="btn btn-success">
                        <a href="payment.jsp?quantity=1&id=<%= p.getId() %>" style="text-decoration: none; color: white;">Buy Now</a>
                    </button>
                </div>
    <%
            }
        } else {
            out.println("No products available.");
        }
    %>    
    </div>

    <%@include file="includes/footer.jsp" %>
</body>
</html>
