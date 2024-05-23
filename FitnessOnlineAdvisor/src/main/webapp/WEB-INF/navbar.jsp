<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="unreadButton" class="java.lang.String" scope="application"/>
<jsp:useBean id="readButton" class="java.lang.String" scope="application"/>
<div class="navbar">
	<a href="messages.jsp?unread=true"><button class="navbutton <%=unreadButton%>">Unread messages</button></a>
	<a href="messages.jsp?unread=false"><button class="navbutton <%=readButton%>">Read messages</button></a>
</div>