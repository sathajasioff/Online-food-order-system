<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="index.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" >
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/brands.min.css"  />
    <script src="index.js"></script>
</head>
<body>
		<div class="navbar">
        <img src="images/logo.png" alt="Logo" class="logo" />
        <ul class="navbar-menu">
            <li><a href="index.jsp" class="">Home</a></li>
            <li><a href="#explore-menu" class="">Menu</a></li>
            <li><a href="orders.jsp" class="">Orders</a></li>
            <li><a href="contact.jsp" class="">Contact us</a></li>
        </ul>
        <div class="navbar-right">
            <img src="images/search_icon.png" alt="Search Icon" />
            <div class="navbar-search-icon">
                <a href="cart.jsp"><img src="images/basket_icon.png" alt="Basket Icon" /></a>
                <span class="badge bagee-danger">${ cart_list.size() }</span>
            </div>
            <%
            if(auth != null){%>
            	<button onclick="showLoginPopup()" ><a href="log-out">SIGN OUT</a></button>
            <%}else{%>
            	<button onclick="showLoginPopup()" ><a href="login.jsp">SIGN IN</a></button>
            <%}
            
            %>
        </div>
    </div>
	
</body>
</html>