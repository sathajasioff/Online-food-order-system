<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pay here</title>
<link rel="stylesheet" href="index.css">
<script src="index.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" >
</head>
<body>
	<div class="navbar">
        <img src="images/logo.png" alt="Logo" class="logo" />
        <ul class="navbar-menu">
            <li><a href="index.jsp" class="">Home</a></li>
            <li><a href="#explore-menu" class="">Menu</a></li>
            <li><a href="orders.jsp" class="">Orders</a></li>
            <li><a href="#footer" class="">Contact us</a></li>
        </ul>
        <div class="navbar-right">
            <img src="images/search_icon.png" alt="Search Icon" />
            <div class="navbar-search-icon">
                <a href="cart.jsp"><img src="images/basket_icon.png" alt="Basket Icon" /></a>
                <span class="badge bagee-danger">${ cart_list.size() }</span>
            </div>
            <%
    // Check if the 'auth' attribute exists in the session
    			Object auth = session.getAttribute("auth");
				%>
				<%
				    if(auth != null){ 
				%>
				    <button onclick="showLoginPopup()"><a href="log-out">SIGN OUT</a></button>
				<%
				    }else{
				%>
				    <button onclick="showLoginPopup()"><a href="login.jsp">SIGN IN</a></button>
				<%
				    }
				%>
        </div>
   	 </div>


    <div class="container mt-5">
        <h2 class="text-center mb-4" style="color: tomato;">Payment Information</h2>
        <form action="payment-servlet" method="post">
            <div class="mb-3">
                <label for="name" class="form-label">Name on Card</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="John Doe" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email Address</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com" required>
            </div>
            <div class="mb-3">
                <label for="cardNumber" class="form-label">Card Number</label>
                <input type="text" class="form-control" id="cardNumber" name="cardNumber" placeholder="1234 5678 9012 3456" required>
            </div>
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="expiryDate" class="form-label">Expiration Date</label>
                    <input type="month" class="form-control" id="expiryDate" name="expiryDate" required>
                </div>
                <div class="col-md-6">
                    <label for="cvv" class="form-label">CVV</label>
                    <input type="text" class="form-control" id="cvv" name="cvv" placeholder="123" required>
                </div>
            </div>

            <h3 class="mt-4">Delivery Address</h3>
            <div class="mb-3">
                <label for="address" class="form-label">Street Address</label>
                <input type="text" class="form-control" id="address" name="address" placeholder="123 Main St" required>
            </div>

            <button type="submit" class="btn w-100" style="background-color: tomato; border-color: tomato;">Pay Now</button>
        </form>
    </div>
    
    <%@include file="includes/footer.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>