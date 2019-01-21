<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1256">
<title>Exam Result</title>
</head>
<%
	int [] details = (int [])request.getAttribute("details");
	int right = details[0];
	int wrong = details[1];
	int skipped = details[2];
	int total = details[3];
%>
<body>
	<form action="FirstServlet">
		<input type="submit" name="button" value="logout">
	</form>
	<form action="FirstServlet">
		<input type="submit" name="button" value="Home">
	</form>
	<h1>Exam Result</h1>
	<p> Number of right answers: <% out.print(right);%>
	<p> Number of wrong answers: <% out.print(wrong);%>
	<p> Number of skipped answers: <% out.print(skipped);%>
	<p> Your score is <% out.print(right);%> / <% out.print(total); %>
</body>
</html>