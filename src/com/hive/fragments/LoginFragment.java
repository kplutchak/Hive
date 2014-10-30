package com.hive.fragments;

import com.hive.R;
import com.hive.helpers.Constants;
import com.hive.main.MainActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends Fragment {

	public static boolean dialogOpened = false;

	EditText mEdit;
	Button positive;
	Button submitButtom;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login_splash, container, false);
		final MainActivity mactivity = (MainActivity) getActivity();

		Button submitButton = (Button) view.findViewById(R.id.guestButton);
		mEdit = (EditText) view.findViewById(R.id.namebar);
		mEdit.setText(mactivity.retrieveSavedGuestName(),
				TextView.BufferType.EDITABLE);

		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String username = mEdit.getText().toString().trim();
				Log.d("username handling in edittext",
						"EditText in logindialog accepted the username "
								+ username);

				mactivity.handleNewGuest(username);
				mactivity.switchToFragment(Constants.QUESTION_ANSWER_FRAGMENT_ID);

			}
		});
		return view;
	}
}
