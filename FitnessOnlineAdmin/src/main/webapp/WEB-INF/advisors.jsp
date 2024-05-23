<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="etf.ip.model.Advisor" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Advisors</title>
<link rel="stylesheet" href="style.css"></link>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.jsp"></jsp:include>
<% List<Advisor> advisors = (List<Advisor>)request.getAttribute("advisors"); %>
	<table>
		<tr>
			<th>Username</th>
			<th>First name</th>
			<th>Last name</th>
		</tr>
		<% for(Advisor advisor : advisors) {%>
			<tr>
				<td><%= advisor.getUsername() %></td>
				<td><%= advisor.getFirstname() %></td>
				<td><%= advisor.getLastname() %></td>
			</tr>
		<%} %>
	</table>
	<div class="update-form-container notification-container">
		<form method="post" action="?action=addAdvisor">
			<div class="input-container">
				<label for="firstname">First name:</label>
				<input type="text" name="firstname" required="required">
			</div>
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
			<div>
				<button type="submit" class="save-button save-button-under">Save new advisor</button>
			</div>
		</form>
	</div>
	<div class="notification-container"><%=request.getAttribute("notification").toString()%></div>
</body>
</html>