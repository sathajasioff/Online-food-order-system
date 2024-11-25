<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .sidebar {
            height: 100vh;
            background: #343a40;
        }

        .sidebar a {
            color: #ffffff;
        }

        .sidebar a:hover {
            background: #495057;
        }

        .content {
            padding: 20px;
        }

        .add-product-btn {
            margin-bottom: 20px;
            background-color: #28a745;
            color: #fff;
            border: none;
        }

        .add-product-btn:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="d-flex">
        <div class="sidebar p-3">
            <h4 class="text-white">Admin Panel</h4>
            <ul class="nav flex-column">
                <li class="nav-item">
                     <a class="nav-link" href="users" onclick="loadContent('PaymentManagementServlet')">Users Management</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="payment-details" onclick="loadContent('PaymentManagementServlet')">Payment Management</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="contact-details" onclick="loadContent('ContactManagementServlet')">Contact Management</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="orders.jsp" onclick="loadContent('ContactManagementServlet')">Order Management</a>
                </li>
            </ul>
        </div>
        <div class="content flex-grow-1">
            <div class="container">
                <h1 class="mt-4">Admin Dashboard</h1>

                <!-- New Product Button -->
                <a href="Add-new-product" class="btn add-product-btn">Add New Product</a>
                <a href="signup.jsp" class="btn add-product-btn">Add New User</a>
                <a href="contact.jsp" class="btn add-product-btn">Add New Message</a>
                <a href="payment.jsp" class="btn add-product-btn">Add New Payment</a>

                <!-- User Management Section -->
                <div id="userManagement" class="mt-4">
                    <h2>User Management</h2>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>User ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>John Doe</td>
                                <td>john@example.com</td>
                                <td>
                                    <button class="btn btn-primary btn-sm">Edit</button>
                                    <button class="btn btn-danger btn-sm">Delete</button>
                                </td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Jane Smith</td>
                                <td>jane@example.com</td>
                                <td>
                                    <button class="btn btn-primary btn-sm">Edit</button>
                                    <button class="btn btn-danger btn-sm">Delete</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <!-- Payment Management Section -->
                <div id="paymentManagement" class="mt-4">
                    <h2>Payment Management</h2>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Transaction ID</th>
                                <th>User ID</th>
                                <th>Amount</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>TXN12345</td>
                                <td>1</td>
                                <td>$100</td>
                                <td>Completed</td>
                                <td>
                                    <button class="btn btn-info btn-sm">View</button>
                                </td>
                            </tr>
                            <tr>
                                <td>TXN12346</td>
                                <td>2</td>
                                <td>$150</td>
                                <td>Pending</td>
                                <td>
                                    <button class="btn btn-info btn-sm">View</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <!-- Contact Management Section -->
                <div id="contactManagement" class="mt-4">
                    <h2>Contact Management</h2>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Contact ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Message</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>Sam Wilson</td>
                                <td>sam@example.com</td>
                                <td>Hello, I have a question.</td>
                                <td>
                                    <button class="btn btn-info btn-sm">Reply</button>
                                    <button class="btn btn-danger btn-sm">Delete</button>
                                </td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Lucy Liu</td>
                                <td>lucy@example.com</td>
                                <td>Can you provide more info?</td>
                                <td>
                                    <button class="btn btn-info btn-sm">Reply</button>
                                    <button class="btn btn-danger btn-sm">Delete</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
