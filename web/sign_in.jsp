<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>


   
<!DOCTYPE html>
<html>
    <%
       
        if(session.getAttribute("candSession")!=null ){
            RequestDispatcher rd= request.getRequestDispatcher("candidate_home.jsp");
            rd.forward(request, response);
        }  
        
    %>
<head>
<meta charset="windows-1256">
<title>Sign In</title>

</head>
<body>

	<h1>Sign In</h1>

        <form class="form" action="FirstServlet" method="post">
            Email*: <input class="email" type="email" id="email" name="email"/> <p> 
		Password*: <input class="pass" type="password" name="password" /> <p> 
		<input type="submit" name="button" value="Login" />
	</form>
        
        <script src="js/jquery-1.12.4.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/custom.js"></script>

</body>
</html>