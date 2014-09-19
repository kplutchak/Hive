package com.hive.fragments;

import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

import network.ConnectToBackend;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hive.R;
import com.hive.helpers.Constants;
import com.hive.main.MainActivity;

public class SplashFragment extends Fragment implements OnGestureListener {

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
		
		// for config changes
		this.setRetainInstance(true);
	}
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.splash_fragment, container, false);
        if(getActivity() != null)
        {
	        MainActivity ma = (MainActivity) getActivity();
	        if(!ma.wasRestarted)
	        {
	        	ma.startTime = Calendar.getInstance().getTimeInMillis();
	        	ma.handler.postDelayed(ma.runnable, SplashFragment.SPLASH_MIN_WAITTIME - ma.timeDiff);
	        }
        }
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
