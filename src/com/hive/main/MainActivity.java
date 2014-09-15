package com.hive.main;

import network.ConnectToBackend;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.hive.R;
import com.hive.fragments.SplashFragment;

public class MainActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        //ConnectToBackend.getAllQuestionsByBlocking(this);
		
	  if (findViewById(R.id.fragment_frame) != null) {

            if (savedInstanceState != null) {
                return;
            }
		
            
		SplashFragment splash_frag = new SplashFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_frame, splash_frag).commit();
		
		
	  }

	}
	
	public void unlockSplashFragment(){
		/**
		 * called by connectToBackend.getAllMessages() on startup. This function allows 
		 */
	}

}
