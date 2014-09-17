package com.hive.main;

import network.ConnectToBackend;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hive.R;
import com.hive.fragments.CreateQuestionFragment;
import com.hive.fragments.QuestionAnswerFragment;
import com.hive.fragments.SplashFragment;
import com.hive.helpers.Constants;

public class MainActivity extends FragmentActivity {

	// fragments
	private SplashFragment splashFragment;
	private QuestionAnswerFragment qaFragment;
	private CreateQuestionFragment cqFragment;
	
	private FragmentManager fm;
	
	
	private String showingFragmentID;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        //ConnectToBackend.getAllQuestionsByBlocking(this);
		
		// retain fragments
		this.fm = this.getSupportFragmentManager();
		splashFragment = (SplashFragment) fm.findFragmentByTag(Constants.SPLASH_FRAGMENT_ID);
		qaFragment = (QuestionAnswerFragment) fm.findFragmentByTag(Constants.QUESTION_ANSWER_FRAGMENT_ID);
		cqFragment = (CreateQuestionFragment) fm.findFragmentByTag(Constants.CREATE_QUESTION_FRAGMENT_ID);
		
		// by default, we should be showing the splash fragment
		this.showingFragmentID = Constants.SPLASH_FRAGMENT_ID;
		
	   if(savedInstanceState != null)
	    {
	        this.showingFragmentID = savedInstanceState.getString("fragID");
	    }
		
	  if (findViewById(R.id.fragment_frame) != null) {

        if(this.splashFragment == null && this.showingFragmentID.equals(Constants.SPLASH_FRAGMENT_ID)){    
        	this.splashFragment = new SplashFragment();
        	this.showingFragmentID = Constants.SPLASH_FRAGMENT_ID;
        	fm.beginTransaction().add(R.id.fragment_frame, splashFragment, Constants.SPLASH_FRAGMENT_ID).commit();
        }
        else if(this.showingFragmentID.equals(Constants.QUESTION_ANSWER_FRAGMENT_ID))
        {
        	
        }
        else if(this.showingFragmentID.equals(Constants.CREATE_QUESTION_FRAGMENT_ID))
        {
        	this.switchToFragment(Constants.CREATE_QUESTION_FRAGMENT_ID);
        }
		
	  }

	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("fragID", this.showingFragmentID);
    }
	
	public String getShowingFragmentID() {
		return this.showingFragmentID;
	}
	
	public void unlockSplashFragment(){
		/**
		 * called by connectToBackend.getAllMessages() on startup. This function allows 
		 */
	}
	
	@Override
	public void onBackPressed() {
		
		QuestionAnswerFragment frag = (QuestionAnswerFragment) getSupportFragmentManager().findFragmentByTag(Constants.QUESTION_ANSWER_FRAGMENT_ID);
		if (frag != null && this.showingFragmentID.equals(Constants.QUESTION_ANSWER_FRAGMENT_ID)) 
		{
			finish();
		}
		else
		{
			super.onBackPressed();
		}
		
		if(this.showingFragmentID.equals(Constants.CREATE_QUESTION_FRAGMENT_ID))
			this.showingFragmentID = Constants.QUESTION_ANSWER_FRAGMENT_ID;
	}
	
	public void switchToFragment(String fragment_id) {
		if(fragment_id.equals(Constants.QUESTION_ANSWER_FRAGMENT_ID))
		{
			if(this.qaFragment == null)
				this.qaFragment = new QuestionAnswerFragment();
			if(this.fm != null)
			{
				 
				 FragmentTransaction transaction = fm.beginTransaction();
 				  
 				  transaction.setCustomAnimations(0, android.R.anim.fade_out);
 				  
 				  transaction.replace(R.id.fragment_frame, this.qaFragment, Constants.QUESTION_ANSWER_FRAGMENT_ID);

 				  
 				 this.showingFragmentID = Constants.QUESTION_ANSWER_FRAGMENT_ID;

 				  // Commit the transaction
 				 
 				 // TODO: perhaps find a better method than this to prevent the savestateinstance bug
 				  transaction.commitAllowingStateLoss();
 	
			}
		}
		else if(fragment_id.equals(Constants.CREATE_QUESTION_FRAGMENT_ID))
		{
			if(this.cqFragment == null)
				this.cqFragment = new CreateQuestionFragment();
			if(this.fm != null)
			{
				 this.showingFragmentID = Constants.CREATE_QUESTION_FRAGMENT_ID;
				 FragmentTransaction transaction = fm.beginTransaction();
 				  
 				  transaction.setCustomAnimations(0, android.R.anim.fade_out);
 				  
 				  transaction.replace(R.id.fragment_frame, this.cqFragment, Constants.CREATE_QUESTION_FRAGMENT_ID);
 				  transaction.addToBackStack(Constants.QUESTION_ANSWER_FRAGMENT_ID);

 				  // Commit the transaction
 				  transaction.commit();
 	
			}
			
		}
	}
	

}
