<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" >

    <!-- Custom styles for input fields -->
    <style>
        body {
            background-color: #f8f9fa; /* Light background color */
        }
        .form-control {
            padding: 15px; /* More padding */
            font-size: 16px; /* Bigger font size */
            border-radius: 5px; /* Rounded corners */
            border: 2px solid #ced4da; /* Thicker border */
        }
        .form-control:focus {
            border-color: #007bff; /* Blue border on focus */
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5); /* Blue glow */
        }
        .card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Subtle shadow for card */
        }
        .btn {
            font-size: 18px; /* Larger font size for button */
            padding: 10px 15px; /* Padding for buttons */
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="row justify-content-center align-items-center" style="height: 100vh;">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header text-center">
                        <h2>Admin Login</h2>
                    </div>
                    <div class="card-body">
                        <!-- Login form -->
                        <form action="admin-login" method="post">
                            <div class="form-group mb-3">
                                <label for="email">Email:</label>
                                <input type="email" name="email" class="form-control" id="email" placeholder="Enter email" required>
                            </div>
                            <div class="form-group mb-3">
                                <label for="password">Password:</label>
                                <input type="password" name="password" class="form-control" id="password" placeholder="Enter password" required>
                            </div>
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary btn-block">Login</button>
                            </div>
                        </form>
                    </div>

                    <!-- Display error message if login fails -->
                    <div class="card-footer text-center">
                        <% if(request.getParameter("error") != null) { %>
                            <p class="text-danger"><%= request.getParameter("error") %></p>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>

</body>
</html>
