package com.stg.inqueue;

import java.io.IOException;

import org.json.JSONObject;

import com.stackmob.sdk.api.StackMob;
import com.stackmob.sdk.api.StackMobOptions;
import com.stackmob.sdk.api.StackMobSession;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.exception.StackMobException;
import com.stackmob.sdk.model.StackMobUser;
import com.stg.inqueue.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	//TextView username;
	Button login;
	Button createUser;
	
	private EditText email; 
	private EditText password;
	private EditText userPhoneNumber;
	private Button loginButton;
	private Button cancelButton;
	private Button createUserButton;
	private Button okayButton;
	private JSONObject jObject;
	private String phoneNumber = "";
	private String deviceID = "";
	private User registration;
	private String enteredEmail;
	private String enteredPassword;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initialpage);
		
		email = (EditText) findViewById(R.id.userEMail);
		password = (EditText) findViewById(R.id.userPassword);
		userPhoneNumber = (EditText)findViewById(R.id.userPhoneNumber);
		loginButton = (Button) findViewById(R.id.login);
		cancelButton= (Button) findViewById(R.id.cancel);
		createUserButton = (Button) findViewById(R.id.createUser);
		
 		//username = (TextView) findViewById(R.id.email);

		login = (Button) findViewById(R.id.login);
		login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	login(getUser());
            }
        });
		createUser = (Button) findViewById(R.id.createUser);
		createUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	createUser(getUser());
            }
        });
		
		okayButton = (Button)findViewById(R.id.okayy);
		okayButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				writeToSDCard write = new writeToSDCard();
				try {
					write.outputTextFile(getUserPhoneNumber());
					Intent i = new Intent(getApplicationContext(), MainActivity.class);
			    	startActivityForResult(i, 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		//do nothing. won't go back to splash screen
		
	}
	
	public String getUserPhoneNumber() {

		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmNumber;

		tmNumber = "" + tm.getLine1Number();
		return tmNumber.toString();

	}

	public User getUser() {
		return new User(email.getText().toString(), password.getText().toString());
	}
	
	public void login(final StackMobUser user) {
		user.login(StackMobOptions.depthOf(2), new StackMobCallback() {
			
			@Override
			public void success(String arg0) {
				StackMobSession session = StackMob.getStackMob().getSession();
				Intent i = getIntent();
				i.putExtra(MainActivity.LOGGED_IN_USER, user.toJson());
				setResult(RESULT_OK, i);
				finish();	
			}
			
			@Override
			public void failure(StackMobException arg0) {
				Toast.makeText(getApplicationContext(), arg0.getMessage(), 10).show();
				
			}
		});
	}
	
	public void createUser(final User user) {
		user.save(new StackMobCallback() {
			
			@Override
			public void success(String arg0) {
				login(user);	
			}
			
			@Override
			public void failure(StackMobException arg0) {
				Toast.makeText(getApplicationContext(), arg0.getMessage(), 10).show();
			}
		});
	}
}
