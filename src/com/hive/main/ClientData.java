package com.hive.main;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.google.gson.JsonArray;

import objects.Question;

public class ClientData {
	
	//Singleton class to hold all data
	private static ClientData clientdata= new ClientData();
	private static List<Question> questions;
	private static List<Question> answeredQuestions;
	
	
	
	private ClientData(){
		//Initialize
		questions = new ArrayList<Question>();
		answeredQuestions = new ArrayList<Question>();
		Log.d("ClientData", "ClientData() - initializing questions arrays");
	}
	
	public static ClientData getInstance(){
		return clientdata;
	}
	
	public static void addQuestion(Question q){
		questions.add(q);
		Log.d("ClientData", "addQuestion(q) added a question with text: '" + q.getQuestionBody() + "' - question array size is now" + Integer.toString(questions.size()));
		return;
	}
	
	public static void setAllQuestions(){
		return;
	}
	

}
