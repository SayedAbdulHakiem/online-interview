
<%@ page language="java" contentType="text/html; charset=windows-1256"  pageEncoding="windows-1256"%>
<%@page import="java.util.List"%>
<%@page import="model.Question"%>
<%@page import="model.Exam"%>
<!DOCTYPE html>

<%
    
    Exam exam = (Exam) session.getAttribute("examSession");
    HttpSession examSessionWithoutTime = request.getSession();
    examSessionWithoutTime.setAttribute("solvedExam", exam);
    if(exam.getQuestions().size()==0){
        response.sendRedirect("exam_error.jsp");
    }

    if (session.getAttribute("examSession") == null) {
        RequestDispatcher rd = request.getRequestDispatcher("candidate_home.jsp");
        rd.forward(request, response);
    }
    // to prevent user from go to this page by clicking back button in browser
    response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
    response.addHeader("Pragma", "no-cache");
    response.addDateHeader("Expires", 0);
%>
<html>

    <head>

        <script>
            var ajaxRequest;
            var answer="false";
            function saveAnswer() {
            // Create XMLHttpRequest object              
            if (window.XMLHttpRequest) { // Mozilla, Chrome, Safari                     
            ajaxRequest = new XMLHttpRequest();
            } else if (window.ActiveXObject) { // Internet Explorer                     
            ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
            }

            $(document).ready(saveAnswer(){
            $("input[type='button']").on('click', saveAnswer(){
            var radioValue = $("input[name='answer']:checked").val();
            if (radioValue){
                answer=radioValue;
            }
            });
            });
            // Configure XMLHttpRequest object                 
            ajaxRequest.onreadystatechange = processRequest; // callback function                 
            var examId = document.getElementsById("examId");
            var questionId = document.getElementsById("questionId");
            var selectedAnswer = $('input[id=answer]:checked').val();
            var url = "ExamServlet?answer=" + answer.value;
            // &exam_id="+ eID + "&question_id=" + qID

            // Making asynchornous request to the webserver                 
            ajaxRequest.open("GET", url, true);
            ajaxRequest.send(null);
            }

            // The callback function            
            function processRequest() {
            if (ajaxRequest.readyState === 4) {
            // The HTML DOM is updated                     
            var result = ajaxRequest.responseText;
            if (result == "Email already exists") {
            document.getElementById("btn_sign_up").disabled = true;
            }
            if (result == "Email is valid") {
            document.getElementById("btn_sign_up").disabled = false;
            }
            document.getElementById("msg").innerHTML = result;
            }
            }
        </script>
        <meta charset="windows-1256">

        <title><% out.print(exam.getType() + " Exam_" + exam.getExamId());%></title>
    </head>
    <body>
        <form action="FirstServlet" method="get"> 
            <input type="submit" name="button" value="Home">
            <input type="submit" name="button" value="logout">

      
        <h1>------ <% out.print(exam.getType());%> Exam ------</h1>
        <h3>Please solve the following questions before the time passes...</h3>
        
      

        <%

            int qId = 1;
           for (Question q : exam.getQuestions()) {

                List<String> answers =  q.getAnswers();
        %>
        <h5>(<%=qId%>) <% out.print(q.getDescription());%> </h5>
        <input type="hidden" name="questionId" value="<%=qId%>">  
        
            <input type="radio" id="answer" name="q<%=qId%>"  value="<%out.print(answers.get(0));%>" onclick="saveAnswer()"> <%out.print(answers.get(0));%><br>              
            <input type="radio" id="answer" name="q<%=qId%>"  value="<%out.print(answers.get(1));%>" onclick="saveAnswer()"> <%out.print(answers.get(1));%><br>
            <input type="radio" id="answer" name="q<%=qId%>"  value="<%out.print(answers.get(2));%>" onclick="saveAnswer()"> <%out.print(answers.get(2));%><br>
            <input type="radio" id="answer" name="q<%=qId%>"  value="<%out.print(answers.get(3));%>" onclick="saveAnswer()"> <%out.print(answers.get(3));%><br>
            <br><br>
        <% qId++;
           }

        %>
       
            
            <input type="submit" name="button" value="submit">
        </form>
    </body>
</html>