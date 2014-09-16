package com.hive.fragments;

import com.hive.R;
import com.hive.helpers.Constants;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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
				String backStateName = Constants.QUESTION_ANSWER_FRAGMENT_ID;
				boolean fragmentPopped = false;
				  FragmentManager manager = getActivity().getSupportFragmentManager();
				  if(getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0)
					  fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

				  if(!fragmentPopped) // fragment does not exist in backstack; create the fragment
				  {
				    FragmentTransaction ft = manager.beginTransaction();
				    ft.replace(R.id.fragment_frame, new QuestionAnswerFragment());
				    ft.addToBackStack(null);
				    ft.commit();
				  }
			}
    		
    	});
    	
		return v;
	}

}
