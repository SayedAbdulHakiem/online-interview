<%-- 
    Document   : search_result
    Created on : Jan 1, 2019, 11:11:17 PM
    Author     : SAY_ED
--%>

<%@page import="java.util.List"%>
<%@page import="model.Candidate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
List<Candidate> candList = (List<Candidate>)request.getAttribute("candList");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>search result</title>
    </head>
    <body>
         <form action="FirstServlet">
            <input type="submit" name="button" value="logout"> 
            
        </form>
        <form action="hr_home.jsp">
           
            <input type="submit" name="button" value="Home">
        </form>
       
        <h1>Search Result about candidate :</h1>
        <table cellspacing ="20" border="1">
            <tr>
                <td>ID</td>
                <td>Name</td>
                <td>Email</td>
                <td>Position</td>
                <td>Age</td> 
                <td>Phone</td>
            </tr>        
       
        <%
        for(Candidate cand : candList){  %>
        <tr>
            <td><% out.print(cand.getId());%></td> 
            <td><% out.print(cand.getName());%></td> 
            <td><% out.print(cand.getEmail());%></td> 
            <td><% out.print(cand.getPosition());%></td> 
            <td><% out.print(cand.getAge());%></td> 
            <td><% out.print(cand.getPhone());%></td>
        </tr>
        <%
        }
        %>
         </table>
    </body>
</html>
