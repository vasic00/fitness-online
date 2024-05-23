<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%
	if (session.getAttribute("advisor") == null)
		response.sendRedirect("login.jsp");
	application.setAttribute("readButton", "active");
	application.setAttribute("unreadButton", "");
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Read messages</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="WEB-INF/header.jsp"></jsp:include>
<jsp:include page="WEB-INF/navbar.jsp"></jsp:include>
<div>
Read
</div>
</body>
</html>