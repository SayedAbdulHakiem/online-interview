<%@ page language="java" contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"%>
<%@page import="model.HR"%>
<!DOCTYPE html>
<html>
    <%
        HR hr = (HR) session.getAttribute("hrSession");

        if (session.getAttribute("hrSession") == null) {
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
        // to prevent user from go to this page by clicking back button in browser
        response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
        response.addHeader("Pragma", "no-cache");
        response.addDateHeader("Expires", 0);
    %>
    <head>
        <meta charset="windows-1256">
        <title><%=hr.getName()%></title>
    </head>
    <body>
        <h1>HR Home</h1>
        <h4>Hello, <%= hr.getName()%></h4>
        <form action="FirstServlet">
            <h4>search for Candidates Data :
                <select name="search_type">
                    <option value="email">Email</option>
                    <option value="id">ID</option>
                    <option value="name">Name</option>
                    <option value="address">Address</option>
                    <option value="position">Position</option>
                    <option value="age">Age</option>
                    <option value="phone">Phone</option>
                    
                </select>

            </h4>
            <h6> 
                <input type="text" name="search_value">
                <input type="submit" name="button" value="Search_candidate_data">
            </h6>
        </form>
        <br>
        <form action="FirstServlet">
            <h4>search for Exam summarized Data :
                <select name="search_type">
                    <option value="id">Exam ID</option>
                    <option value="candidate_id">Candidate ID</option>
                    <option value="type">Exam Type</option>
                </select>

            </h4>
            <h6> 
                <input type="text" name="search_value">
                <input type="submit" name="button" value="Search_exam_data">
            </h6>
        </form>

        <form action="FirstServlet">
           
            <br>
            <h4>Enter Exam Id :</h4>
            <input type="text" name ="exam_detailed_id" >
            <input type="submit" name="button" value="Search_detailed_Results">
            <br>
             <input type="submit" name="button" value="All_summarized_Results">
             <br>
            <input type="submit" name="button" value="hr_logout">
        </form>

    </body>
</html>