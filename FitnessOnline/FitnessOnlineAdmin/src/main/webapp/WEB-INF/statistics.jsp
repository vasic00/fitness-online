<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Logs</title>
<link rel="stylesheet" href="style.css"></link>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.jsp"></jsp:include>
	<% List<String> statistics = (List<String>)request.getAttribute("statistics"); %>
	<table>
		<tr>
			<th>Number</th>
			<th>Request/Response</th>
		</tr>
		<%
		int count = 1;
		for (String line : statistics) {
			if (line.contains("log Response")==false) {
		%>
		<tr>
			<td><%=count%></td>
			<td><%=line %></td>
		</tr>
		<%
		count++;
		}
		}
		%>
	</table>
</body>
</html>