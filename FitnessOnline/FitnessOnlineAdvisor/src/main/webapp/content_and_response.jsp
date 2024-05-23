<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="etf.ip.model.MessageDTO" %>
    <%@ page import="etf.ip.services.MessageService" %>
    <jsp:useBean id="messageService" class="etf.ip.services.MessageService" scope="application"/>
<!DOCTYPE html>
<%
	if (session.getAttribute("advisor") == null)
		response.sendRedirect("login.jsp");
	MessageDTO message = messageService.read(Long.parseLong(request.getParameter("id")));
	if (message == null) {
		response.sendRedirect("messages.jsp");
	}
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Message</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="WEB-INF/header.jsp"></jsp:include>
<jsp:include page="WEB-INF/navbar.jsp"></jsp:include>
<div>Sender: <%=message.getSender() %></div>
<br>
Content: <div class="message-content"><%=message.getContent() %></div>
<br>
<h3>Send Email</h3>
    <form id="emailForm">
    	<input type="text" name="recipient" value="<%=message.getSenderId()%>" class="input-delete">
        <label for="content">Text:</label>
        <textarea name="content" rows="6" cols="60" required="required"></textarea><br><br>
        <label for="file">Choose file:</label>
        <input type="file" name="file" class="save-button-under"><br><br>
        <button type="button" class="save-button save-button-under" onclick="sendEmail()">Send Email</button>
    </form>
    <script>
        function sendEmail() {
            var form = document.getElementById("emailForm");
            var formData = new FormData(form);

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "http://localhost:8082/FitnessOnline/email", true);
            xhr.onload = function () {
                if (xhr.status === 200) {
                    alert("Email sent successfully!");
                } else {
                    alert("Failed to send email. Error: " + xhr.responseText);
                }
            };
            xhr.send(formData);
        }
    </script>
</body>
</html>