<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="etf.ip.model.MessageDTO" %>
    <%@ page import="etf.ip.services.MessageService"%>
    <%@ page import="java.util.List" %>
    <jsp:useBean id="messageService" class="etf.ip.services.MessageService" scope="application"/>
    <jsp:useBean id="notification" class="java.lang.String" scope="session"/>
<!DOCTYPE html>
<%
if (session.getAttribute("advisor") == null)
	response.sendRedirect("login.jsp");
	String content = "-";
	String unreadOnly = "true";
	if (request.getParameter("unread") != null && request.getParameter("unread").equals("false")) {
		unreadOnly = "false";
		application.setAttribute("unreadButton", "");
		application.setAttribute("readButton", "active");
	}
	else {
		application.setAttribute("unreadButton", "active");
		application.setAttribute("readButton", "");
	}
	if (request.getParameter("delete") != null) {
		messageService.delete(Long.parseLong(request.getParameter("msgId")));
	}
	if (request.getParameter("filter") != null && !request.getParameter("filter").equals(""))
		content = request.getParameter("filter");
	List<MessageDTO> unreadMessages = messageService.getAll(unreadOnly, content);
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Unread messages</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="WEB-INF/header.jsp"></jsp:include>
<jsp:include page="WEB-INF/navbar.jsp"></jsp:include>
<div>
	<form action="messages.jsp?unread=<%=unreadOnly%>" method="post" class="filter-form">
			
			Content contains: <input type="text" name="filter" value="<%=!content.equals("-")?content:""%>">
			<input type="submit" value="Filter" class="save-button">
	</form>
</div>
<% for (MessageDTO message : unreadMessages) {%>
	<div class="message-container"><a class="message" href="content_and_response.jsp?id=<%=message.getId()%>">
	Message from <%=message.getSender()%> at <%=message.getTimeOfSending().replace("T"," ")%></a>
	<a href="messages.jsp?msgId=<%=message.getId()%>&delete=true&unread=<%=unreadOnly%>"><button class="save-button">Delete</button></a>
	</div>
<%}%>
<div class="notification-container">
		<%=notification!=null?notification:""%>
</div>
</body>
</html>