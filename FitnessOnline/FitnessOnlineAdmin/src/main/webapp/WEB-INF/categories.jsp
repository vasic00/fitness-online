<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="etf.ip.model.CategoryDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="etf.ip.model.SpecificAttributeDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Categories</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.jsp"></jsp:include>
	<% 
		List<CategoryDTO> categories = (List<CategoryDTO>)request.getAttribute("categories");
	%>
	<table>
		<tr>
			<th>Category name</th>
			<th>Specific attributes</th>
			<th></th>
		</tr>
		<% for (CategoryDTO category : categories) { %>
		<tr>
			<td><a href="?action=category&category=<%=category.getName()%>"><%=category.getName()%></a></td>
			<td>
				<ul>
					<% for (SpecificAttributeDTO sa : category.getSpecificAttributes()){ %>
						<li><%=sa.getName() %></li>
					<%}%>
				</ul>
			</td>
			<td>
				<a href="?action=deleteCategory&category=<%=category.getId()%>">Delete</a>
			</td>
		</tr>
		<%}%>
	</table>
	<form action="?action=addCategory" method="post" id="new-category-form">
			
			New category: <input type="text" name="category" required="required">
			<input type="submit" value="Save new category" class="save-button">
	</form>
	<div class="notification-container"><%=request.getAttribute("notification").toString()%></div>
</body>
</html>