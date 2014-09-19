package objects;

import java.util.ArrayList;
import java.util.List;

public class Question {
	
	private String questionBody;
	private List<Answer> choices;
	private String questionID;
	private User asker;
	
	public Question(String qbody, User asker){
		setQuestionBody(qbody);
		choices = new ArrayList<Answer>();
	    setAsker(asker);
	}
	
	public void addAnswer(Answer ans){
		ans.setQuestionID(questionID);
		this.choices.add(ans);
	}

	public void deleteAnswer(Answer ans){
		this.choices.remove(ans);
	}
	/**
	 * @return the questionBody
	 */
	public String getQuestionBody() {
		return questionBody;
	}

	/**
	 * @param questionBody the questionBody to set
	 */
	public void setQuestionBody(String questionBody) {
		this.questionBody = questionBody;
	}

	/**
	 * @return the choiceA
	 */


	/**
	 * @return the asker
	 */
	public User getAsker() {
		return asker;
	}

	/**
	 * @param asker the asker to set
	 */
	public void setAsker(User asker) {
		this.asker = asker;
	}

	public List<Answer> getChoices() {
		return choices;
	}

	public void setChoices(List<Answer> choices) {
		this.choices = choices;
	}

}
