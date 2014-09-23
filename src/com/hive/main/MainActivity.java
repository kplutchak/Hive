package com.hive.main;

import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

import objects.Self;

import network.ConnectToBackend;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.hive.R;
import com.hive.fragments.CreateQuestionFragment;
import com.hive.fragments.MyDialogFragment;
import com.hive.fragments.QuestionAnswerFragment;
import com.hive.fragments.SplashFragment;
import com.hive.helpers.Constants;

public class MainActivity extends FragmentActivity {

	// fragments
	private SplashFragment splashFragment;
	private QuestionAnswerFragment qaFragment;
	private CreateQuestionFragment cqFragment;
	
	public boolean wasRestarted = false;

	public long startTime;
	public long endTime;
	
	public long timeDiff;
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.d("Pause", "App stopped!");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.d("Pause", "App destroyed!");
		super.onDestroy();
	}

	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		this.wasRestarted = true;
		handler.post(runnable);
		Log.d("Pause", "App restarted!");
		super.onRestart();
	}

	//public CountDownLatch latch = new CountDownLatch(1);
	public Handler handler = new Handler();
	public Runnable runnable;
	private FragmentManager fm;
	
	public boolean paused;
	
	private String showingFragmentID;
	
	@Override
	protected void onPause() {
		this.endTime = Calendar.getInstance().getTimeInMillis();
		handler.removeCallbacks(runnable);
		super.onPause();
		Log.d("Pause", "App paused!");
		this.paused = true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("Pause", "App resumed!");
		this.paused = false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        //ConnectToBackend.getAllQuestionsByBlocking(this);
		
		Self.updateSelf();
		this.timeDiff = 0;
		
		 runnable = new Runnable() {
	       	   @Override
	       	   public void run() {
	    			  // replace fragment
	       		   	  if(MainActivity.this.qaFragment != null)
	       		   	  {
	       		   		  handler.removeCallbacks(this);
	       		   		  return;
	       		   	  }
	 			MainActivity ma = MainActivity.this;
	 			if(ma.getShowingFragmentID().equals(Constants.SPLASH_FRAGMENT_ID))
	 			{
	 			
	 				 DialogFragment newFragment = new MyDialogFragment();
	  				// transaction.add(newFragment, null);
	 				 //while()
	 				ma.switchToFragment(Constants.QUESTION_ANSWER_FRAGMENT_ID);
	 			}
	 		
	       	   }
	       	};

	     //  handler.postDelayed(runnable, SplashFragment.SPLASH_MIN_WAITTIME);	
		
		// retain fragments
		this.fm = this.getSupportFragmentManager();
		splashFragment = (SplashFragment) fm.findFragmentByTag(Constants.SPLASH_FRAGMENT_ID);
		qaFragment = (QuestionAnswerFragment) fm.findFragmentByTag(Constants.QUESTION_ANSWER_FRAGMENT_ID);
		cqFragment = (CreateQuestionFragment) fm.findFragmentByTag(Constants.CREATE_QUESTION_FRAGMENT_ID);
		
		// by default, we should be showing the splash fragment
		this.showingFragmentID = Constants.SPLASH_FRAGMENT_ID;
		
	   if(savedInstanceState != null)
	    {
		   if(this.qaFragment == null)
			   this.qaFragment = (QuestionAnswerFragment) this.getSupportFragmentManager().getFragment(savedInstanceState, Constants.QUESTION_ANSWER_FRAGMENT_ID);
	        this.showingFragmentID = savedInstanceState.getString("fragID");
	        this.timeDiff = savedInstanceState.getLong("timeDiff");
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
		super.onSaveInstanceState(outState);
        outState.putString("fragID", this.showingFragmentID);
        long timeDiff = this.endTime - this.startTime;
        outState.putLong("timeDiff", timeDiff);
        if(this.qaFragment != null)
        	this.getSupportFragmentManager().putFragment(outState, Constants.QUESTION_ANSWER_FRAGMENT_ID, this.qaFragment);
    }
	
	public String getShowingFragmentID() {
		return this.showingFragmentID;
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
 				  
 				  if(this.showingFragmentID.equals(Constants.CREATE_QUESTION_FRAGMENT_ID))
 				  {
 					  transaction.hide(this.cqFragment);
 					  transaction.show(this.qaFragment);
 				  }
 				  else
 				  {
 					  transaction.replace(R.id.fragment_frame, this.qaFragment, Constants.QUESTION_ANSWER_FRAGMENT_ID);
 					  
 				  }

 				  
 				 this.showingFragmentID = Constants.QUESTION_ANSWER_FRAGMENT_ID;

 				  // Commit the transaction
 				 
 				 // TODO: perhaps find a better method than this to prevent the savestateinstance bug
 				  transaction.commit();
 	
			}
		}
		else if(fragment_id.equals(Constants.CREATE_QUESTION_FRAGMENT_ID))
		{
			FragmentTransaction transaction = fm.beginTransaction();
			if(this.cqFragment == null)
			{
				this.cqFragment = new CreateQuestionFragment();
				transaction.add(R.id.fragment_frame, this.cqFragment, Constants.CREATE_QUESTION_FRAGMENT_ID);
			}
			if(this.fm != null)
			{
				 this.showingFragmentID = Constants.CREATE_QUESTION_FRAGMENT_ID;
				 
 				  transaction.setCustomAnimations(0, android.R.anim.fade_out);
 				 transaction.hide(this.qaFragment);
 				  
					  //transaction.hide(this.cqFragment);
 				  
 				
 				  transaction.show(this.cqFragment);
 				 // transaction.addToBackStack(Constants.QUESTION_ANSWER_FRAGMENT_ID);

 				  // Commit the transaction
 				  transaction.commit();
 	
			}
			
		}
	}
	

}
