<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="etf.ip.model.UserDTO" %>
    <%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Users</title>
<link rel="stylesheet" href="style.css"></link>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.jsp"></jsp:include>
<% List<UserDTO> users = (List<UserDTO>)request.getAttribute("users"); %>
	<table>
		<tr>
			<th>Username</th>
			<th>First name</th>
			<th>Last name</th>
			<th>Status</th>
			<th></th>
			<th></th>
		</tr>
		<% for(UserDTO user : users) {%>
			<tr>
				<td><a href="?action=user&user=<%=user.getId()%>"><%=user.getUsername()%></a></td>
				<td><%= user.getFirstname() %></td>
				<td><%= user.getLastname() %></td>
				<td><%= user.getStatus().toString() %></td>
				<td><a href="?action=changeStatus&user=<%=user.getId()%>"><%=(user.getStatus().toString().equals("BLOCKED") ||
				user.getStatus().toString().equals("NOT_ACTIVATED"))?"Activate":"Block"%></a></td>
				<td><a href="?action=deleteUser&user=<%=user.getId()%>">Delete</a></td>
			</tr>
		<%} %>
	</table>
	<div class="update-form-container notification-container">
		<form method="post" action="?action=addUser">
			<div class="input-container">
				<label for="firstname">First name:</label>
				<input type="text" name="firstname" required="required">
			</div >
			<div class="input-container">
				<label for="lastname">Last name:</label>
				<input type="text" name="lastname" required="required"> 
			</div>
			<div class="input-container">
				<label for="username">Username:</label>
				<input type="text" name="username" minlength="5" required="required">
			</div>
			<div class="input-container">
				<label for="password">Password:</label>
				<input type="text" name="password" minlength="8" required="required">
			</div>
			<div class="input-container">
				<label for="email">Email:</label>
				<input type="email" name="email" required="required">
			</div>
			<div class="input-container">
				<label for="city">City:</label>
				<input type="text" name="city" required="required">
			</div>
			<div>
				<button type="submit" class="save-button save-button-under">Save new user</button>
			</div>
		</form>
	</div>
	<div class="notification-container"><%=request.getAttribute("notification").toString()%></div>
</body>
</html>