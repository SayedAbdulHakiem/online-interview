package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Exam {

	private int candidateId;
	private int examId;
	private List<Question> questions = new ArrayList<Question>();
	private int result=0;
	private String type="";
        private Date date;
        private int numOfRightAnswers=0;
        private int numOfWrongAnswers=0;
        private int numOfSkippedAnswers=0;
        private int examDetailedId=0;
        private int questionId=0;
         private String questionDesription="";
        private String rightAnswer="";
        private String selectedAnswer="";
        private boolean status=false;
        

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public List<Question> getQuestions() {
		return questions;
	}
	
	public void addQuestion(Question Q) {
		this.questions.add(Q);
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
        
        public void setNumOfRightAnswers( int right) {
		 this.numOfRightAnswers=right;
	}

	public int getNumOfRightAnswers() {
		return this.numOfRightAnswers ;
	}
        
         public void setNumOfWrongAnswers( int wrong) {
		 this.numOfWrongAnswers=wrong;
	}

	public int getNumOfWrongAnswers() {
		return this.numOfWrongAnswers ;
	}
        
         public void setNumOfSkippedAnswers( int skipped) {
		 this.numOfSkippedAnswers=skipped;
	}

	public int getNumOfSkippedAnswers() {
		return this.numOfSkippedAnswers ;
	}
        

         public void setExamDate(Date date) {
            this.date=date;
        }
         public Date getExamDate(){
             return this.date;
         }
         
         
         public int getExamDetailedId() {
		return examDetailedId;
	}

	public void setExamDetailedId(int examId) {
		this.examDetailedId = examId;
	}
        
         public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionID) {
		this.questionId = questionID;
	}
        
        public String getSelectedAnswer() {
		return this.selectedAnswer;
	}

	public void setSelectedAnswer(String selected) {
		this.selectedAnswer = selected;
	}
        
          public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String worng) {
		this.rightAnswer = worng;
	}
        
        public String getQuestionDiscription() {
		return this.questionDesription;
	}

	public void setQuestionDiscription(String question) {
		this.questionDesription = question;
	}
        
        
         public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
        
        
}
