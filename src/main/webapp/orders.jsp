<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="food.model.*" %>
<%@page import="java.util.*" %>
<%@page import="food.connection.DbCon" %>
<%@page import="food.dao.OrderDao" %>
<%@page import="java.text.DecimalFormat" %>
<%
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
    
    User auth = (User) request.getSession().getAttribute("auth");
    List<Order> orders = null;
    
    if (auth != null) {
        request.setAttribute("auth", auth);
        orders = new OrderDao(DbCon.getConnection()).userOrders(auth.getId());
    } else {
        response.sendRedirect("login.jsp");
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
    <title>Orders</title>
    <link rel="stylesheet" href="index.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/brands.min.css" />
</head>
<body>
    <%@include file="includes/header.jsp" %>

    <div class="container">
        <div class="card-header my-3">All Orders</div>
        <table class="table table-light">
            <thead>
                <tr>
                    <th scope="col">Date</th>
                    <th scope="col">Name</th>
                    <th scope="col">Category</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Price</th>
                    <th scope="col">Cancel</th>
                </tr>
            </thead>
            <tbody>
            <%
                if (orders != null) {
                    for (Order o : orders) {
            %>
                <tr>
                    <td><%= o.getDate() %></td>
                    <td><%= o.getName() %></td>
                    <td><%= o.getCategory() %></td>
                    <td><%= o.getQuantity() %></td>
                    <td><%= dcf.format(o.getPrice()) %></td>
                    <td><a class="btn btn-sm btn-danger" href="cancel-order?id=<%= o.getOrderId() %>">Cancel</a></td>
                </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>

    <%@include file="includes/footer.jsp" %>
</body>
</html>
