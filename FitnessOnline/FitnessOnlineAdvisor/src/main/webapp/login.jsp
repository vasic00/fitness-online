<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="etf.ip.services.AdvisorService" %>
    <%@ page import="etf.ip.model.Advisor" %>
    <jsp:useBean id="notification" class="java.lang.String" scope="page"/>
    <jsp:useBean id="advisorService" class="etf.ip.services.AdvisorService" scope="page"/>
    
<!DOCTYPE html>
<% notification = ""; %>
<%
	if (request.getParameter("username") != null) {
		Advisor advisor = advisorService.login(request.getParameter("username"), request.getParameter("password"));
		if (advisor != null) {
			session.setAttribute("advisor", advisor);
			response.sendRedirect("messages.jsp");
		}
		else
			notification = "Invalid credentials.";
	}
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<link rel="stylesheet" href="style.css">
<body>
<jsp:include page="WEB-INF/header.jsp"></jsp:include>
<div class="login-container">
	<form method="post" action="login.jsp">
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
		<%=notification%>
	</div>
</form>
</div>
</body>
</html>