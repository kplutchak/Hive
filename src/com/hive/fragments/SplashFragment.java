package com.hive.fragments;

import com.hive.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SplashFragment extends Fragment {

	private Handler handler = new Handler();
	
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
        
        Runnable runnable = new Runnable() {
        	   @Override
        	   public void run() {
     			  // replace fragment
 				  
 				  QuestionAnswerFragment qa_frag = new QuestionAnswerFragment();
 				  FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
 				  
 				  transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
 				  
 				  transaction.replace(R.id.fragment_frame, qa_frag);
 				  transaction.addToBackStack(null);

 				  // Commit the transaction
 				  transaction.commit();
 	
        	   }
        	};
        	
        
        handler.postDelayed(runnable, 3000);	

        return v;
    }
	
	
	

}
