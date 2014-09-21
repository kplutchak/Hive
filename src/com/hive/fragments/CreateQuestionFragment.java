package com.hive.fragments;


import network.ConnectToBackend;
import objects.Answer;
import objects.Question;
import objects.Self;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.hive.R;
import com.hive.helpers.Constants;
import com.hive.main.MainActivity;

public class CreateQuestionFragment extends Fragment {

	// views
	private RelativeLayout back_tab;
	private EditText choiceA;
	private EditText choiceB;
	private EditText questionInput;
	private Button submit_button;
	
	public CreateQuestionFragment() {
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d("CreateQuestionFragment", "Creating question fragment, setting views");
		// inflate the layout
    	View v = inflater.inflate(R.layout.createquestion_fragment, container, false);
    	questionInput = (EditText) v.findViewById(R.id.question_input);
		choiceA = (EditText) v.findViewById(R.id.inputChoiceA);
		choiceB = (EditText) v.findViewById(R.id.inputChoiceB);
    	
    	// "back" tab
    	back_tab = (RelativeLayout) v.findViewById(R.id.back_tab);
    	back_tab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickOnBackTab();
			}
    	});
    	
    	//Set functionality for entering a new question
    	submit_button = (Button) v.findViewById(R.id.submitQuestionButton);
    	submit_button.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(final View v) {
	        		Log.d("CreateQuestionFragment", "New Question Submit clicked");
	        		
	        		String questionBody = questionInput.getText().toString();
	        		String choiceAtext = choiceA.getText().toString();
	        		String choiceBtext = choiceB.getText().toString();
	        		Question newq = new Question(questionBody, Self.getUser());
	        		Answer ansA = new Answer(choiceAtext);
	        		Answer ansB = new Answer(choiceBtext);
	        		newq.addAnswer(ansA);
	        		newq.addAnswer(ansB);
	        		
	        		ConnectToBackend.postQuestion(getActivity(), newq);
	        		clickOnBackTab();
	        	}
    	});
    	
		return v;
	}
	

	private void clickOnBackTab(){
		
		MainActivity ma = (MainActivity) getActivity();
			if(ma.getShowingFragmentID().equals(Constants.CREATE_QUESTION_FRAGMENT_ID))
				ma.switchToFragment(Constants.QUESTION_ANSWER_FRAGMENT_ID);
	}

}
