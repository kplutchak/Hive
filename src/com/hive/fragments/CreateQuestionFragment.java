package com.hive.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hive.R;
import com.hive.helpers.Constants;
import com.hive.main.MainActivity;

public class CreateQuestionFragment extends Fragment {

	// views
	private RelativeLayout back_tab;
	
	public CreateQuestionFragment() {
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// inflate the layout
    	View v = inflater.inflate(R.layout.createquestion_fragment, container, false);
    	
    	// "back" tab
    	back_tab = (RelativeLayout) v.findViewById(R.id.back_tab);
    	back_tab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity ma = (MainActivity) getActivity();
	 			if(ma.getShowingFragmentID().equals(Constants.CREATE_QUESTION_FRAGMENT_ID))
	 				ma.switchToFragment(Constants.QUESTION_ANSWER_FRAGMENT_ID);
			}
    		
    	});
    	
		return v;
	}

}
