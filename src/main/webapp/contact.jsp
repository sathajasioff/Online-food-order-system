<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="food.model.User"%>
<%@page import="food.model.Cart"%> <!-- Import the Cart class -->
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>



<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contact</title>
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
   	 <div class="con-map" style="display: flex; flex-direction: column;">
   	 <div class="container mt-5">
        <h2 class="text-center mb-4" style="color: tomato;">Contact Us</h2>

        <form action="contact" method="post">
            <div class="mb-3">
                <label for="name" class="form-label style="border: 1px solid tomato; padding: 5px;">Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Your Name" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label style="border: 1px solid tomato; padding: 5px;">Email address</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Your Email" required>
            </div>
            <div class="mb-3">
                <label for="subject" class="form-label style="border: 1px solid tomato; padding: 5px;">Subject</label>
                <input type="text" class="form-control" id="subject" name="subject" placeholder="Subject" required>
            </div>
            <div class="mb-3">
                <label for="message" class="form-labelstyle=" border: 1px solid tomato; padding: 5px;">Message</label>
                <textarea class="form-control" id="message" name="message" rows="4" placeholder="Your Message" required></textarea>
            </div>
            <button type="submit" class="btn w-100" style="background-color: tomato; border-color: tomato;">Submit</button>
        </form>
    </div>
     <div class="mt-5" style="text-align: center;">
    <h4 class="text-center">Find Us Here</h4>
    <iframe 
        src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15303.444373529981!2d79.9626739025421!3d6.916876719570271!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3ae256db1a6771c5%3A0x2c63e344ab9a7536!2sSri%20Lanka%20Institute%20of%20Information%20Technology!5e0!3m2!1sen!2slk!4v1728240199466!5m2!1sen!2slk"
        width="60%" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
	</div>
	</div>
    

    <%@include file="includes/footer.jsp" %>
</body>
</html>
