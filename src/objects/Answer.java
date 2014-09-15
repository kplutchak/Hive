package objects;

public class Answer {
	
	private String answerBody;
	private String answerID;
	private String questionID;
	private int votes;
	
	public Answer(String m_answerBody, String m_questionID, int m_votes){
		answerBody = m_answerBody;
		questionID = m_questionID;
		votes = m_votes;
	}
	

}
