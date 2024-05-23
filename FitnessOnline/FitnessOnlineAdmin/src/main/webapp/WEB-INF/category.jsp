<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="etf.ip.model.CategoryDTO" %>
    <%@ page import="etf.ip.model.SpecificAttributeDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Category</title>
<link rel="stylesheet" href="style.css"></link>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.jsp"></jsp:include>
	<% CategoryDTO category = (CategoryDTO)request.getAttribute("category"); %>
	<div class="update-form-container">
		<form action="?action=updateCategory&category=<%=category.getName()%>" method="post">
			<div>
				Category name: <input type="text" value="<%=category.getName()%>" name="categoryName" required="required">
			</div>
			<div>Attributes:</div>
			<% for (SpecificAttributeDTO sa : category.getSpecificAttributes()){ %>
				<div>
					<input type="text" value="<%=sa.getName()%>" name="<%=sa.getId()%>" required="required">
					| <a href="?action=deleteAttribute&attribute=<%=sa.getId()%>&category=<%=category.getName()%>">Delete</a>
				</div>
			<%}%>
			<div>
				<button type="submit" class="save-button save-button-under">Save category details</button>
			</div>
			<div><%=request.getAttribute("notification").toString()%></div>
		</form>
		<div>
			<form action="?action=addAttribute&categoryId=<%=category.getId()%>&categoryName=<%=category.getName()%>" method="post">
				<div>
					New attribute name: <input type="text" name="newAttribute" required="required">
					<button type="submit" class="save-button">Save new attribute</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>