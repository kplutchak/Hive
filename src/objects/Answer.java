package objects;

public class Answer {
	
	private String answerBody;
	private String answerID;
	private String questionID;
	private int votes;
	
	public Answer(String m_answerBody){
		setAnswerBody(m_answerBody);
		//setQuestionID(m_questionID);
		setVotes(0);
	}

	public String getQuestionID() {
		return questionID;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	public String getAnswerID() {
		return answerID;
	}

	public void setAnswerID(String answerID) {
		this.answerID = answerID;
	}

	public String getAnswerBody() {
		return answerBody;
	}

	public void setAnswerBody(String answerBody) {
		this.answerBody = answerBody;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
	

}
