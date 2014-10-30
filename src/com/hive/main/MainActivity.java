package com.hive.main;

import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import objects.Self;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.hive.helpers.*;
import network.ConnectToBackend;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.hive.R;
import com.hive.fragments.CreateQuestionFragment;
import com.hive.fragments.LoginFragment;
import com.hive.fragments.MyDialogFragment;
import com.hive.fragments.QuestionAnswerFragment;
import com.hive.fragments.SplashFragment;
import com.hive.helpers.Constants;


public class MainActivity extends FragmentActivity {

	// fragments
	private SplashFragment splashFragment;
	private QuestionAnswerFragment qaFragment;
	private CreateQuestionFragment cqFragment;
	private LoginFragment loginFragment;
	
	public boolean wasRestarted = false;
	public long startTime;
	public long endTime;
	public long timeDiff;

	public Handler handler = new Handler();
	public Runnable runnable;
	private FragmentManager fm;
	
	public boolean paused;
	
	private String showingFragmentID;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*Initialize self*/
		initializeUserID(this);		
		/*Create layout*/
		
	
		/*Check for network*/
		if (!isOnline()){
			setContentView(R.layout.splash_fragment);
			Log.d("MainActivity", "onCreate detected no network; stopping application");
			DisplayExceptionAlertDialog error = new DisplayExceptionAlertDialog();
			error.showAlert(this, "No Network Detected", true);	
		}
		
		setContentView(R.layout.activity_main);
			
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
	 				//ma.switchToFragment(Constants.QUESTION_ANSWER_FRAGMENT_ID);
	 				ma.switchToFragment(Constants.LOGIN_FRAGMENT_ID);
	 			}
	 		
	       	   }
	       	};

	     //  handler.postDelayed(runnable, SplashFragment.SPLASH_MIN_WAITTIME);	
		
		// retain fragments
		this.fm = this.getSupportFragmentManager();
		splashFragment = (SplashFragment) fm.findFragmentByTag(Constants.SPLASH_FRAGMENT_ID);
		qaFragment = (QuestionAnswerFragment) fm.findFragmentByTag(Constants.QUESTION_ANSWER_FRAGMENT_ID);
		cqFragment = (CreateQuestionFragment) fm.findFragmentByTag(Constants.CREATE_QUESTION_FRAGMENT_ID);
		loginFragment = (LoginFragment) fm.findFragmentByTag(Constants.LOGIN_FRAGMENT_ID);
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
        else if(this.showingFragmentID.equals(Constants.LOGIN_FRAGMENT_ID))
        {
        	
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
		else if(fragment_id.equals(Constants.LOGIN_FRAGMENT_ID)){
			if(this.loginFragment == null)
				this.loginFragment = new LoginFragment();
			if(this.fm != null)
			{
				 FragmentTransaction transaction = fm.beginTransaction();
 				  
 				  transaction.setCustomAnimations(0, android.R.anim.fade_out);
 				  
 				  if(this.showingFragmentID.equals(Constants.CREATE_QUESTION_FRAGMENT_ID))
 				  {
 					  transaction.hide(this.cqFragment);
 					  transaction.show(this.loginFragment);
 				  }
 				  else
 				  {
 					  transaction.replace(R.id.fragment_frame, this.loginFragment, Constants.LOGIN_FRAGMENT_ID);  
 				  }
 				 this.showingFragmentID = Constants.LOGIN_FRAGMENT_ID;

 				  transaction.commit();
			}
		}
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.d("Pause", "App stopped!");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		DisplayExceptionAlertDialog.killDialog();
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
		/*Check for network*/
		if (!isOnline()){
			Log.d("MainActivity", "onCreate detected no network; stopping application");
			DisplayExceptionAlertDialog error = new DisplayExceptionAlertDialog();
			error.showAlert(this, "No Network Detected", true);	
		}
		
		super.onResume();
		Log.d("Pause", "App resumed!");
		this.paused = false;
	}

	
	/*  Handle retrieval of sharedPreferences */
	private SharedPreferences getGCMPreferences() {
	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
	
	public String retrieveSavedGuestName()
	{
		Log.d("retrieveSavedGuestName()", "Attempting to retrieve saved guest name");
		String guestName = null;
		final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String registrationGuest = prefs.getString("PROPERTY_GUESTNAME", "");
		if (registrationGuest.isEmpty()) {
		    	Log.i("LOUD AND CLEAR", "No previous guest name registered.");
		    	guestName = "Name";
		    	Log.d("retrieveSavedGuestName()", "guest name registratedGuest is empty guestname is now" + guestName);
			}
		else{ 
			guestName = registrationGuest;
		Log.d("retrieveSavedGuestName()", "guestname is found, guestname is " + guestName);
		}

		return guestName;
	}
	
	private void initializeUserID(Context context){
		final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String registrationId = prefs.getString("PROPERTY_UID", "");
		if (registrationId.isEmpty()) {
		    	Log.d("initializeUserID", "Registration not found.");
		    	registrationId = UUID.randomUUID().toString();
			}
    	Self.getUser().setuID(registrationId);
    	storeUID(context, Self.getUser().getuID());
    	  Log.d("initializeUserID", "UID is " +Self.getUser().getuID());
	}
	
	private void storeUID(Context context, String regID){
		final SharedPreferences prefs = getGCMPreferences();
		    SharedPreferences.Editor editor = prefs.edit();
		    editor.putString("PROPERTY_UID", Self.getUser().getuID());
		    editor.commit();  
	}
	
	public void handleNewGuest(String username)
	{
		final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String registrationId = prefs.getString("PROPERTY_UID", "");
		if (registrationId.isEmpty()) {
		    	Log.i("LOUD AND CLEAR", "Registration not found.");
		    	registrationId = UUID.randomUUID().toString();
			}
    	Self.getUser().setName(registrationId);

    	if (username.equals("Name") || (username.trim().length() == 0) ){
    		username = "";
    	}
    
    	String processedUsername = "Guest-" + registrationId.substring(registrationId.length()-6, registrationId.length()-1) + " (" + username + ")";
    	
    	Self.getUser().setName(processedUsername);
		Log.d("LOUD AND CLEAR", "Accepting guest login with username: " + processedUsername);
     
		Log.d("LOUD AND CLEAR", "Guest Login accepted and storing name " + Self.getUser().getName());
		storeGuestName(username);
		//Store UID info
    	
	}
	private void storeGuestName(String guestName){
		final SharedPreferences prefs = getGCMPreferences();
		    Log.i("storeGuestName", "Saving guestName on app version " + guestName);
		    SharedPreferences.Editor editor = prefs.edit();
		    editor.putString("PROPERTY_GUESTNAME", guestName);
		    editor.commit();
	}
	
	/*Check for network*/
    public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	

}
