<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="food.model.User"%>
<%@page import="food.model.Cart"%> <!-- Import the Cart class -->
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%
User auth = (User) request.getSession().getAttribute("auth");
if (auth != null) {
    response.sendRedirect("index.jsp");
    return; // Prevent further processing
}

// Capture the cart list from the session
ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
if (cart_list != null) {
    request.setAttribute("cart_list", cart_list);
}

// Capture error message if present
String signupError = (String) request.getAttribute("signupError");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" href="index.css">
    <script>
        // Ensuring the script runs after the DOM is loaded
        window.onload = function() {
            // Function to validate form input
            function validateForm() {
                let name = document.forms["signupForm"]["name"].value;
                let email = document.forms["signupForm"]["email"].value;
                let password = document.forms["signupForm"]["pwd"].value;
                let confirmPassword = document.forms["signupForm"]["confirmPwd"].value;
                let errorMessages = document.getElementById("signupError");

                errorMessages.innerHTML = ""; // Clear previous error messages

                // Check if all fields are filled
                if (!name || !email || !password || !confirmPassword) {
                    errorMessages.innerHTML = "All fields must be filled.";
                    return false;
                }

                // Check if password is at least 6 characters long
                if (password.length < 6) {
                    errorMessages.innerHTML = "Password must be at least 6 characters long.";
                    return false;
                }

                // Check if password and confirm password match
                if (password !== confirmPassword) {
                    errorMessages.innerHTML = "Passwords do not match.";
                    return false;
                }

                return true; // If all checks pass
            }

            // Attach the validation function to the form submission
            document.forms["signupForm"].onsubmit = validateForm;
        }
    </script>
</head>
<body>
    <%@include file="includes/header.jsp" %>

    <div class="login-popup" id="loginPopup">
        <!-- Attach the validateForm function to onsubmit -->
        <form class="login-popup-container" name="signupForm" method="POST" action="register">
            <div class="login-popup-title">
                <h2>Sign Up</h2>
                <img onclick="closeLoginPopup()" src="images/cross_icon.png" alt="Close" />
            </div>
            <div class="login-popup-inputs">
                <input type="text" placeholder="Your Name" name="name" required />
                <input type="email" placeholder="Your Email" name="email" required />
                <input type="password" placeholder="Password" name="pwd" required />
                <input type="password" placeholder="Confirm Password" name="confirmPwd" required />
            </div>
            <button type="submit" id="submitButton">Register</button>
            <div class="login-popup-condition">
                <input type="checkbox" required />
                <p>I agree to the <a href="terms-and-conditions.html" target="_blank">terms & conditions</a></p>
            </div>
            <p>Already have an account? <span onclick="location.href='login.jsp'">Click Here</span></p>
            
            <!-- Display error message if signup fails -->
            <div class="error-message" style="color: red;">
                <p id="signupError">
                    <%= signupError != null ? signupError : "" %>
                </p>
            </div>
        </form>
    </div>

    <%@include file="includes/footer.jsp" %>
</body>
</html>
