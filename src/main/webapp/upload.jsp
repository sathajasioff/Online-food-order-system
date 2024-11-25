<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


 
<div class="container">
        <h1>Upload Food Product</h1>
        <form action="uploadFood" method="POST" enctype="multipart/form-data">
    <label for="name">Food Name:</label>
    <input type="text" id="name" name="name" required>
    
    <label for="category">Category:</label>
    <input type="text" id="category" name="category" required>
    
    <label for="price">Price:</label>
    <input type="number" id="price" name="price" required>
    
    <label for="image">Upload Image:</label>
    <input type="file" id="image" name="image" accept="image/*" required>

    <button type="submit">Upload Food Product</button>
</form>


    </div>

<%@include file="includes/footer.jsp" %>

</body>
</html>