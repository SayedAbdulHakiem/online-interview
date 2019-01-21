<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1256">
<title>Home</title>
</head>
<%
    HttpSession candSession = request.getSession();
   
if(candSession.getAttribute("candSession")!=null){
    response.sendRedirect("candidate_home.jsp");
}
%>
<body>
	<h1>Welcome in our COMPANY</h1>
		
	<form action="sign_up.jsp">
    	<input type="submit" value="SignUp">
    </form>
    
    <form action="sign_in.jsp">
    	<input type="submit" value="SignIn">
    </form>
    
    
        <form> 
            <input type="submit" name="button" value="Home">
        </form>
    
</body>
</html>