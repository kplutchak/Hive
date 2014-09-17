package com.hive.fragments;

import network.ConnectToBackend;

import com.hive.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class SplashFragment extends Fragment implements OnGestureListener {

	private Handler handler = new Handler();
	
	//Constants
	public static final long SPLASH_MIN_WAITTIME = 3000;
	
	public SplashFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.splash_fragment, container, false);
        //
        
        Runnable runnable = new Runnable() {
       	   @Override
       	   public void run() {
    			  // replace fragment
       		   	   if(getActivity() == null)
       		   	   {
       		   		   handler.removeCallbacks(this);
       		   		   return;
       		   	   }
 				  QuestionAnswerFragment qa_frag = new QuestionAnswerFragment();
 				  FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
 				  
 				  transaction.setCustomAnimations(0, android.R.anim.fade_out);
 				  
 				  transaction.replace(R.id.fragment_frame, qa_frag);
 				  transaction.addToBackStack(null);
 				 
 				 
 				// CheckinSuccessDialog dialog = new CheckinSuccessDialog();
 				//dialog.show(getSupportFragmentManager(), null);
 			//	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
 				 DialogFragment newFragment = new MyDialogFragment();
 				// transaction.add(newFragment, null);
 				 transaction.commit();
 				ConnectToBackend.getAllQuestions(getActivity());
 				
 				
 				  // Commit the transaction
 				 // transaction.commit();
 	
       	   }
       	};
       	
       if(getActivity() == null)
    	   handler.removeCallbacks(runnable);
       handler.postDelayed(runnable, this.SPLASH_MIN_WAITTIME);	
             

        return v;
    }

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		Log.d("Fling", "Flung!");
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void endSplashScreen(long delay){
		Log.d("SplashFragment", "endSplashScreen(), with delay:" + Long.toString(delay));
		 
      return;
	}
	
	

}
