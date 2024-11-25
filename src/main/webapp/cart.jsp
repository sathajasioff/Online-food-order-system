<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="food.model.*" %>
<%@page import="java.util.*" %>
<%@page import="java.text.DecimalFormat" %>
<%@page import="food.connection.DbCon" %>
<%@page import="food.dao.ProductDao" %>
<%
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);

    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
        request.setAttribute("auth", auth);
    }

    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    List<Cart> cartProduct = null;
    if (cart_list != null) {
        ProductDao pDao = new ProductDao(DbCon.getConnection());
        cartProduct = pDao.getCartProducts(cart_list);
        double total = pDao.getTotalPrice(cart_list);  // Ensure this method exists in ProductDao
        request.setAttribute("cart_list", cart_list);
        request.setAttribute("total", total);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add to cart</title>
    <link rel="stylesheet" href="cart.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>

<%@include file="includes/header.jsp" %>

<div class="container">
    <div class="d-flex py-3">
        <h3>Total Price: ${ (total > 0) ? dcf.format(total) : 0 }</h3>
        <a class="mx-3 btn btn-primary" href="check-out">Check Out</a>
    </div>
    <table class="table table-light">
        <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Category</th>
                <th scope="col">Price</th>
                <th scope="col">Buy Now</th>
                <th scope="col">Cancel</th>
            </tr>
        </thead>
        <tbody>
            <% if (cart_list != null) {
                for (Cart c : cartProduct) { %>
                    <tr>
                        <td><%= c.getName() %></td>
                        <td><%= c.getCategory() %></td>
                        <td><%= dcf.format(c.getPrice()) %></td> <!-- Formatting the price -->
                        <td>
                            <form action="order-now" method="post" class="form-inline">
                                <input type="hidden" name="id" value="<%= c.getId() %>" class="form-input">
                                <div class="form-group d-flex align-items-center w-100">
                                    <a class="btn btn-sm btn-decre" href="quantity-inc-dec?action=dec&id=<%= c.getId() %>" style="color: white; background-color: tomato;">
                                        <i class="fas fa-minus-square"></i>
                                    </a>
                                    <input type="text" name="quantity" class="form-control mx-2" value="<%= c.getQuantity() %>" readonly>
                                    <a class="btn btn-sm btn-incre" href="quantity-inc-dec?action=inc&id=<%= c.getId() %>" style="color: white; background-color: tomato;">
                                        <i class="fas fa-plus-square"></i>
                                    </a>
                                    <a href="payment.jsp">
                                        <button type="button" class="btn btn-primary btn-sm ms-2">Buy</button>
                                    </a>
                                </div>
                            </form>
                        </td>
                        <td>
                            <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/remove-from-cart?id=<%= c.getId() %>">Remove</a>
                        </td>
                    </tr>
                <% }
            } %>
        </tbody>
    </table>
</div>

<%@include file="includes/footer.jsp" %>

</body>
</html>
