<%-- 
    Document   : exam_error
    Created on : Jan 3, 2019, 9:30:25 AM
    Author     : SAY_ED
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Sorry, you have only one chance to solve a specified type exam per session </h1>
        <a href="candidate_home.jsp"> get Another exam?</a>
        <form action="FirstServlet" >
            <input type="submit" name="button" value="logout">
        </form>
    </body>
</html>
