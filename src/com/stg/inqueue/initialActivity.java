package com.stg.inqueue;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class initialActivity extends Activity {
	
	//initial setup
	private EditText email; 
	private EditText password;
	private Button submitButton;
	private Button cancelButton;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intialpage);
		
		email = (EditText) findViewById(R.id.userEMail);
		password = (EditText) findViewById(R.id.userPassword);
		submitButton = (Button) findViewById(R.id.submit);
		cancelButton= (Button) findViewById(R.id.cancel);
		
		submitButton.setOnClickListener((android.view.View.OnClickListener) submitButton);
		cancelButton.setOnClickListener((android.view.View.OnClickListener) cancelButton);
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	//make JSON objects
	public OnClickListener sumbitButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public OnClickListener cancelButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			email.setText(R.string.empty);
			password.setText(R.string.empty);
		}
	};

}
