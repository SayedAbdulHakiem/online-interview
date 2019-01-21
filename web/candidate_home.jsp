<%@ page language="java" contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"%>
<%@page import="model.Candidate"%>
<!DOCTYPE html>
<html>
    <%
        Candidate cand = (Candidate) session.getAttribute("candSession");
        if(session.getAttribute("candSession")==null){
            RequestDispatcher rd= request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
         // to prevent user from go to this page by clicking back button in browser
   response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
   response.addHeader("Pragma", "no-cache"); 
   response.addDateHeader ("Expires", 0);
 
        
    %>
    <head>
        <meta charset="windows-1256">
        <title><%=cand.getName()%></title>
    </head>
    <body>
        <form action="FirstServlet">
            <input type="submit" name="button" value="logout"> 
            
        </form>
        <form action="FirstServlet">
           
            <input type="submit" name="button" value="Home">
        </form>
        <h4>Hello, <%= cand.getName()%></h4>
        <br>
        <h1>Here are the available exams for you...</h1>
        <p>
            As your position is:"<% out.print(cand.getPosition());%>" you have to finish all of these
            exams.
        </p>
        <p>
            NOTE: once you click on the EXAM button the time available to finish
            this exam is start counting<br> so, if you have a free 5 minutes
            for a one of any exam type click on that exam type button to start the
            exam immediately.
        </p>

        <form action="FirstServlet">
            <input type="submit" name="button" value="JAVA EXAM"> 
            <input type="submit" name="button" value="DATABASE EXAM">
        </form>
       
    </body>
</html>