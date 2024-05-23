<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="categoriesButton" class="java.lang.String" scope="application"/>
<jsp:useBean id="usersButton" class="java.lang.String" scope="application"/>
<jsp:useBean id="logsButton" class="java.lang.String" scope="application"/>
<jsp:useBean id="advisorsButton" class="java.lang.String" scope="application"/>
<div class="navbar">
	<a href="?action=categories"><button class="navbutton <%=categoriesButton%>">Categories</button></a>
	<a href="?action=users"><button class="navbutton <%=usersButton%>">Users</button></a>
	<a href="?action=statistics"><button class="navbutton <%=logsButton%>">Logs</button></a>
	<a href="?action=advisors"><button class="navbutton <%=advisorsButton%>">Advisors</button></a>
</div>