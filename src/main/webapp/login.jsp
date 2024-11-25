<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="food.model.*" %>
    <%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
    <%
User auth = (User) request.getSession().getAttribute("auth");
if(auth != null){
	response.sendRedirect("index.jsp");
}

ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");

if(cart_list != null){
    
    request.setAttribute("cart_list", cart_list);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="index.css">
    <script src="index.js"></script>
</head>
<body>
	<%@include file="includes/header.jsp" %>
	
	    <div class="login-popup" id="loginPopup">
    <form class="login-popup-container" method="POST" action="user-login">
        <div class="login-popup-title">
            <h2>Login</h2>
            <img onclick="closeLoginPopup()" src="images/cross_icon.png" alt="Close" />
        </div>
        <div class="login-popup-inputs">
            <input type="email" placeholder="Your Email" name="email" required />
            <input type="password" placeholder="Password" name="pwd" required />
        </div>
        <button type="submit" id="submitButton">Login</button>
        <div class="login-popup-condition">
            <input type="checkbox" required />
            <p>I agree to the <a href="terms-and-conditions.html" target="_blank">terms & conditions</a></p>
        </div>
        <p>Don't have an account? <span onclick="location.href='signup.jsp'">Click Here</span></p>
        
        <!-- Placeholder for error message -->
        <div class="error-message" style="color: red;">
            <p id="loginError"></p>
        </div>
    </form>
</div>

	
	<%@include file="includes/footer.jsp" %>
</body>
</html>