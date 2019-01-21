package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Candidate;
import model.DbConnection;
import model.Exam;
import model.HR;
import model.Question;

@WebServlet(name = "FirstServlet", urlPatterns = {"/FirstServlet"})
public class FirstServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public FirstServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {

            String jspAction = request.getParameter("button");

            if (jspAction.equals("Home")) {
                response.sendRedirect("candidate_home.jsp");
            } else if (jspAction.equals("SignUp")) {
                DbConnection con = new DbConnection();
                try (Connection conn = con.getConnection()) {
                    Statement stmt = conn.createStatement();
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String address = request.getParameter("address");
                    String position = request.getParameter("position");
                    String phone = request.getParameter("phone");
                    int age = Integer.parseInt(request.getParameter("age"));
                    String type = "candidate";
                    String query = "INSERT INTO online_interview.candidate(name,email,type,password,address,position,age,phone) VALUES('"
                            + name + "', '" + email + "', '" + type + "','" + password + "', '" + address + "', '"
                            + position + "', " + age + ", '" + phone + "');";
                    stmt.executeUpdate(query);
                    request.getRequestDispatcher("sign_in.jsp");
                    out.println("Candidate inserted successfully!");
                    //request.getRequestDispatcher("sign_in.jsp").forward(request, response);
                    RequestDispatcher rd = request.getRequestDispatcher("sign_in.jsp");
                    rd.forward(request, response);
                }
            } else if (jspAction.equals("Login")) {
                String userEmail = request.getParameter("email");
                String password = request.getParameter("password");
                DbConnection con = new DbConnection();
                Connection conn = con.getConnection();
                PreparedStatement ps = conn
                        .prepareStatement("SELECT * FROM online_interview.candidate WHERE email=? AND password=?");
                ps.setString(1, userEmail);
                ps.setString(2, password);
                ResultSet RS = ps.executeQuery();

                if (RS.first()) {
                    String userType = RS.getString("type");
                 //   System.out.println("user type: " + userType);

                    Candidate cand = new Candidate();

                    cand.setId("" + RS.getInt("id"));
                    cand.setName(RS.getString("name"));
                    cand.setEmail(RS.getString("email"));
                    cand.setPassword(RS.getString("password"));
                    cand.setType("candidate");
                    cand.setPosition(RS.getString("position"));
                    cand.setAge(RS.getInt("age"));
                    cand.setPhone(RS.getString("phone"));

                    HttpSession candSession = request.getSession(true);
                    candSession.setAttribute("candSession", cand);

                    RequestDispatcher rd = request.getRequestDispatcher("candidate_home.jsp");
                    rd.forward(request, response);
                    conn.close();
                } else {
                    ps = conn.prepareStatement("SELECT * FROM online_interview.hr WHERE email=? AND password=?");
                    ps.setString(1, userEmail);
                    ps.setString(2, password);
                    RS = ps.executeQuery();

                    if (RS.first()) {
                        String userType = RS.getString("type");
                       // System.out.println("user type: " + userType);

                        HR hr = new HR();

                        hr.setId("" + RS.getInt("id"));
                        hr.setName(RS.getString("name"));
                        hr.setEmail(RS.getString("email"));
                        hr.setPassword(RS.getString("password"));
                        hr.setType("hr");
                        // hr.setCompany(RS.getString("company"));

                        HttpSession hrSession = request.getSession(true);
                        hrSession.setAttribute("hrSession", hr);

                        RequestDispatcher rd = request.getRequestDispatcher("hr_home.jsp");
                        rd.forward(request, response);
                        conn.close();
                    } else {
                      //  System.out.print("ERROR: wrong email or password!!"); // For Testing
                        out.print("ERROR: wrong email or password!!");
                        RequestDispatcher rd = request.getRequestDispatcher("sign_in.jsp");
                        rd.forward(request, response);
                    }
                }
                out.close();
            } else if (jspAction.equals("logout")) {
                HttpSession session = request.getSession();
                session.setAttribute("candSession", null);
                session.invalidate();
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);

            } else if (jspAction.equals("JAVA EXAM") || jspAction.equals("DATABASE EXAM")) {
                String examType = "";
                if (jspAction.equals("JAVA EXAM")) {
                    examType = "Java";
                } else if (jspAction.equals("DATABASE EXAM")) {
                    examType = "Database";
                }
                List<Question> questions = new ArrayList<Question>();
                String[] questionTypes = {"java_oop", "java_basics"};
                int limit = 3;

                DbConnection con = new DbConnection();
                Connection conn = con.getConnection();
                HttpSession sessions = request.getSession();
                if(sessions.getAttribute("examSession")==null){
               
                questions = prepareQuestions(conn, questionTypes, limit);
                questions = prepareAnswers(conn, 3, questions);
                questions = prepareAnswers(conn, 1, questions);
                printListOfQuestions(questions);
                }
                HttpSession candSession = request.getSession();
                Candidate cand = (Candidate) candSession.getAttribute("candSession");
                int candId = Integer.parseInt(cand.getId());
                Exam exam = new Exam();
                exam.setQuestions(questions);
                exam.setCandidateId(candId); // tb3n el-mafrood n7ot candidate id 7a2ee2y .. sa7eb el-session
                exam.setType(examType);

                java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());

                saveExam(conn, exam, candId, examType, 0, date);
                HttpSession examSession = request.getSession();
                examSession.setMaxInactiveInterval(2*60); // 1 minutes
                examSession.setAttribute("examSession", exam);

                RequestDispatcher rd = request.getRequestDispatcher("exam.jsp");
                rd.forward(request, response);
                conn.close();
            } else if (jspAction.equals("submit")) {

                DbConnection con = new DbConnection();
                Connection conn = con.getConnection();
                HttpSession solvedExam = request.getSession();
                Exam exam = (Exam) solvedExam.getAttribute("solvedExam");

                String currentName = "";
                String currentAnswer = "";
                int exam_id = exam.getExamId();
                List<Question> qList = exam.getQuestions();
                int numberOfQuestions = qList.size();

                PreparedStatement ps = conn.prepareStatement("UPDATE online_interview.exam_details SET selected_answer = ? "
                        + " WHERE exam_id = ?  AND question_id=? ");

                for (int i = 1; i <= numberOfQuestions; i++) {
                    currentName = "q" + i;
                    //currentName.toString();
                    currentAnswer = request.getParameter(currentName);
                    System.out.println("current name =" + currentName);
                    System.out.println("current answer=" + currentAnswer);
                    qList.get(i - 1).setSelectedAnswer(currentAnswer);
                    if(currentAnswer==null){
                        currentAnswer="skipped";
                    }
                    ps.setString(1, currentAnswer);
                    ps.setInt(2, exam_id);
                    ps.setInt(3, i );
                    ps.executeUpdate();
                }


                int[] details = checkAnswersAndCalculateScore(conn, exam_id, numberOfQuestions);
                request.setAttribute("details", details);
                RequestDispatcher rd = request.getRequestDispatcher("exam_result.jsp");
                rd.forward(request, response);
                conn.close();

            } else if (jspAction.equals("Search_candidate_data")) {
                String searchType = request.getParameter("search_type");
                String searchValue = request.getParameter("search_value");
                List<Candidate> candList = new ArrayList<Candidate>();

                DbConnection con = new DbConnection();
                Connection conn = con.getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT * FROM online_interview.candidate WHERE " + searchType + "=? ");
                ps.setString(1, searchValue);
                ResultSet RS = ps.executeQuery();
                while (RS.next()) {

                    Candidate cand = new Candidate();

                    cand.setId("" + RS.getInt("id"));
                    cand.setName(RS.getString("name"));
                    cand.setEmail(RS.getString("email"));
                    cand.setPassword(RS.getString("password"));
                    cand.setType("candidate");
                    cand.setPosition(RS.getString("position"));
                    cand.setAge(RS.getInt("age"));
                    cand.setPhone(RS.getString("phone"));
                    candList.add(cand);
                }
                if (candList.size() == 0) {

                    RequestDispatcher rd = request.getRequestDispatcher("search_error.jsp");
                    rd.forward(request, response);

                } else {

                    request.setAttribute("candList", candList);
                    RequestDispatcher rd = request.getRequestDispatcher("search_cand_result.jsp");
                    rd.forward(request, response);
                }
            } else if (jspAction.equals("Search_exam_data")) {
                String searchType = request.getParameter("search_type");
                String searchValue = request.getParameter("search_value");
                List<Exam> examList = new ArrayList<Exam>();

                DbConnection con = new DbConnection();
                Connection conn = con.getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT * FROM online_interview.exam WHERE " + searchType + "=? ");
                ps.setString(1, searchValue);
                ResultSet RS = ps.executeQuery();
                while (RS.next()) {

                    Exam exam = new Exam();

                    exam.setExamId(RS.getInt("id"));
                    exam.setCandidateId(RS.getInt("candidate_id"));
                    exam.setType(RS.getString("type"));
                    exam.setResult(RS.getInt("score"));
                    exam.setNumOfRightAnswers(RS.getInt("num_of_right_answers"));
                    exam.setNumOfWrongAnswers(RS.getInt("num_of_wrong_answers"));
                    exam.setNumOfSkippedAnswers(RS.getInt("num_of_skipped_answers"));
                    exam.setExamDate(RS.getDate("date"));

                    examList.add(exam);
                }
                if (examList.size() == 0) {

                    RequestDispatcher rd = request.getRequestDispatcher("search_error.jsp");
                    rd.forward(request, response);

                } else {

                    request.setAttribute("examList", examList);
                    RequestDispatcher rd = request.getRequestDispatcher("search_exam_result.jsp");
                    rd.forward(request, response);
                }
            } else if (jspAction.equals("All_summarized_Results")) {

                List<Exam> examList = new ArrayList<Exam>();

                DbConnection con = new DbConnection();
                Connection conn = con.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM online_interview.exam");

                ResultSet RS = ps.executeQuery();
                while (RS.next()) {

                    Exam exam = new Exam();

                    exam.setExamId(RS.getInt("id"));
                    exam.setCandidateId(RS.getInt("candidate_id"));
                    exam.setType(RS.getString("type"));
                    exam.setResult(RS.getInt("score"));
                    exam.setNumOfRightAnswers(RS.getInt("num_of_right_answers"));
                    exam.setNumOfWrongAnswers(RS.getInt("num_of_wrong_answers"));
                    exam.setNumOfSkippedAnswers(RS.getInt("num_of_skipped_answers"));
                    exam.setExamDate(RS.getDate("date"));

                    examList.add(exam);
                }
                if (examList.size() == 0) {

                    RequestDispatcher rd = request.getRequestDispatcher("search_error.jsp");
                    rd.forward(request, response);

                } else {

                    request.setAttribute("examList", examList);
                    RequestDispatcher rd = request.getRequestDispatcher("search_exam_result.jsp");
                    rd.forward(request, response);
                }
            } else if (jspAction.equals("Search_detailed_Results")) {

                List<Exam> examList = new ArrayList<Exam>();
                String examId = request.getParameter("exam_detailed_id");
                DbConnection con = new DbConnection();
                Connection conn = con.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM online_interview.exam_details WHERE exam_id=?");
                ps.setString(1, examId);
                ResultSet RS = ps.executeQuery();
                while (RS.next()) {

                    Exam exam = new Exam();

                    exam.setExamId(RS.getInt("id"));
                    exam.setExamDetailedId(RS.getInt("exam_id"));
                    exam.setQuestionId(RS.getInt("question_id"));
                    exam.setQuestionDiscription(RS.getString("question"));
                    exam.setRightAnswer(RS.getString("right_answer"));
                    exam.setSelectedAnswer(RS.getString("selected_answer"));
                    exam.setStatus(RS.getBoolean("status"));

                    examList.add(exam);
                }
                if (examList.size() == 0) {

                    RequestDispatcher rd = request.getRequestDispatcher("search_error.jsp");
                    rd.forward(request, response);

                } else {

                    request.setAttribute("examList", examList);
                    RequestDispatcher rd = request.getRequestDispatcher("detailed_results.jsp");
                    rd.forward(request, response);
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FirstServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FirstServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Question> prepareQuestions(Connection conn, String[] questionTypes, int limit) throws SQLException {
        List<Question> questions = new ArrayList<Question>();

        for (int i = 0; i < questionTypes.length; i++) {
            PreparedStatement psQ = conn.prepareStatement("SELECT * FROM online_interview.question WHERE type=? ORDER BY RAND() LIMIT ?");
            psQ.setString(1, questionTypes[i]);
            psQ.setInt(2, limit);
            ResultSet RSQ = psQ.executeQuery();

            while (RSQ.next()) {
                Question q = new Question();

                int id = RSQ.getInt("id");
                String description = RSQ.getString("description");
                String type = RSQ.getString("type");

               // System.out.println(id);
               // System.out.println(type);
               // System.out.println(description);

                q.setDescription(description);
                q.setId(id);
                q.setRightAnswer("");
                q.setSelectedAnswer("");
                q.setStatus(false);
                q.setType(type);
                q.getSelectedAnswer();

                questions.add(q);
            }
        }

        return questions;
    }

    private List<Question> prepareAnswers(Connection conn, int limit, List<Question> questions) throws SQLException {

        int answerType = 0;
        if (limit == 1) {
            answerType = 1;
        }

        for (Question q : questions) {
            List<String> answers = q.getAnswers();
            int question_id = q.getId();

            PreparedStatement psQ = conn.prepareStatement("SELECT * FROM online_interview.answer WHERE question_id = ? AND type = ? ORDER BY RAND() LIMIT ?");
            psQ.setInt(1, question_id);
            psQ.setInt(2, answerType);
            psQ.setInt(3, limit);
            ResultSet RSQ = psQ.executeQuery();

            while (RSQ.next()) {
                int id = RSQ.getInt("id");
                int questionID = RSQ.getInt("question_id");
                String description = RSQ.getString("description");
                String type = RSQ.getString("type");

             // System.out.println(id);
             // System.out.println(questionID);
             // System.out.println(type);
             // System.out.println(description);

                answers.add(description);

                // Correct Answer
                if (limit == 1) {
                    q.setRightAnswer(description);
                    Collections.shuffle(answers); // 3shan myb2osh kollhom wrong - wrong - wrong - right
                }
            }
            q.setAnswers(answers);
        }

        return questions;
    }

    private void printListOfQuestions(List<Question> questions) {
        for (Question q : questions) {
           // System.out.println("ID: " + q.getId());
           // System.out.println("Type: " + q.getType());
           // System.out.println("Description: " + q.getDescription());
           // printListOfStrings(q.getAnswers());
           //System.out.println("--------------------------------------");
        }
    }

    private void printListOfStrings(List<String> answers) {
        //System.out.println("Answers: ");
        for (int i = 0; i < answers.size(); i++) {
          //  System.out.println((i + 1) + " - " + answers.get(i));
        }
    }

    private void saveExam(Connection conn, Exam exam, int candidateID, String type, int score, java.sql.Timestamp date) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO online_interview.exam(candidate_id,type,score,date) VALUES(?,?,?,?)");
        ps.setInt(1, candidateID);
        ps.setString(2, type);
        ps.setInt(3, score);
        ps.setTimestamp(4, date);
        int temp = ps.executeUpdate();
        //System.out.println("temp: " + temp);

        PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM online_interview.exam ORDER BY id DESC LIMIT 1");
        ResultSet RS = ps2.executeQuery();
        RS.next();
        int examId = RS.getInt("id");
        //System.out.println("examID: " + examId);
        exam.setExamId(examId);

        ps = conn.prepareStatement("INSERT INTO online_interview.exam_details(exam_id,question_id,question,right_answer,selected_answer,status) VALUES(?,?,?,?,?,?)");
        int question_id = 1;
        for (Question q : exam.getQuestions()) {
            ps.setInt(1, examId);
            ps.setInt(2, question_id);
            ps.setString(3, q.getDescription());
            ps.setString(4, q.getRightAnswer());
            ps.setString(5, "ahmed galal");
            ps.setInt(6, 0);
            ps.executeUpdate();
            question_id++;
        }

    }

    // Udpate status value in exam_details table
    private int[] checkAnswersAndCalculateScore(Connection conn, int exam_id, int numberOfQuestions) throws SQLException {

        PreparedStatement ps = conn.prepareStatement("SELECT right_answer, selected_answer FROM online_interview.exam_details WHERE exam_id = ? AND question_id = ?");
        String right_answer = "";
        String selected_answer = "";
        int status = 0; // 0 -> skipped, 1 -> correct, 2 -> wrong
        int num_of_right_answers = 0;
        int num_of_wrong_answers = 0;
        int num_of_skipped_answers = 0;

        for (int i = 1; i <= numberOfQuestions; i++) {
            ps.setInt(1, exam_id);
            ps.setInt(2, i);
            ResultSet RS = ps.executeQuery();
            RS.next();

            right_answer = RS.getString("right_answer");
            selected_answer = RS.getString("selected_answer");
            // Skipped
            if (selected_answer.equals("skipped")) {
                status = 0;
                num_of_skipped_answers += 1;
            } // Correct
            else if (right_answer.equals(selected_answer)) {
                status = 1;
                num_of_right_answers += 1;
            } // Wrong
            else {
                status = 2;
                num_of_wrong_answers += 1;
            }

          //  System.out.println("right_answer " + i + " - " + right_answer);
          //  System.out.println("selected_answer " + i + " - " + selected_answer);
          //  System.out.println("status " + i + ": " + status);

            PreparedStatement ps2 = conn.prepareStatement("UPDATE online_interview.exam_details SET status=? WHERE exam_id = ? AND question_id = ?");
            ps2.setInt(1, status);
            ps2.setInt(2, exam_id);
            ps2.setInt(3, i);
            ps2.executeUpdate();
        }

      //  System.out.println("num_of_right_answers: " + num_of_right_answers);
      //  System.out.println("num_of_wrong_answers: " + num_of_wrong_answers);
      //  System.out.println("num_of_skipped_answers: " + num_of_skipped_answers);
        // Add final score to Exam table
        PreparedStatement ps3 = conn.prepareStatement("UPDATE online_interview.exam SET num_of_right_answers = ?, num_of_wrong_answers = ?, num_of_skipped_answers = ?, score = ? WHERE id = ?");
        ps3.setInt(1, num_of_right_answers);
        ps3.setInt(2, num_of_wrong_answers);
        ps3.setInt(3, num_of_skipped_answers);
        ps3.setInt(4, num_of_right_answers);
        ps3.setInt(5, exam_id);
        ps3.executeUpdate();

        int totalScore = num_of_right_answers + num_of_skipped_answers + num_of_wrong_answers;
        int[] details = {num_of_right_answers, num_of_wrong_answers, num_of_skipped_answers, totalScore};
        return details;
    }
}
