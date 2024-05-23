<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="etf.ip.model.UserDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User</title>
<link rel="stylesheet" href="style.css"></link>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.jsp"></jsp:include>
	<% UserDTO user = (UserDTO)request.getAttribute("user"); %>
	<div class="update-form-container">
		<form action="?action=updateUser&user=<%=user.getId()%>" method="post">
		<div class="input-container">
			<label for="firstname">First name:</label>
			<input type="text" value="<%=user.getFirstname()%>" required="required" name="firstname">
		</div>
		<div class="input-container">
			<label for="lastname">Last name:</label>
			<input type="text" value="<%=user.getLastname()%>" required="required" name="lastname">
		</div>
		<div class="input-container">
			<label for="username">Username:</label>
			<input type="text" value="<%=user.getUsername()%>" required="required" name="username" minlength="5">
		</div>
		<div class="input-container">
			<label for="password">Password:</label>
			<input type="text" name="password" minlength="8">
		</div>
		<div class="input-container">
			<label for="email">Email:</label>
			<input type="email" name="email" value="<%=user.getEmail()%>" required="required">
		</div>
		<div class="input-container">
			<label for="city">City:</label>
			<input type="text" name="city" value="<%=user.getCity()%>" required="required">
		</div>
		<div>
			<button type="submit" class="save-button save-button-under">Save user details</button>
		</div>
		<div class="notification-container"><%=request.getAttribute("notification").toString()%></div>
	</form>
	</div>
</body>
</html>