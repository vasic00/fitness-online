<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" href="style.css"></link>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div class="login-container">
	<form method="post" action="?action=login">
	<div class="input-container">
		<label for="username">Username:</label>
		<input name="username" required="required">
	</div>
	<div class="input-container">
		<label for="password">Password:</label>
		<input name="password" required="required" type="password">
	</div>
	<div class="centered-flex">
		<button class="login-button" type="submit" name="submit">Login</button>
	</div>
	<div class="centered-flex notification-container">
		<%=request.getAttribute("notification").toString()%>
	</div>
</form>
</div>
</body>
</html>