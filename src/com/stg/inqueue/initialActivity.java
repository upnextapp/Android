package com.stg.inqueue;

import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.stackmob.android.sdk.common.StackMobAndroid;
import com.stackmob.sdk.api.StackMob;
import com.stackmob.sdk.api.StackMobOptions;
import com.stackmob.sdk.api.StackMobSession;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;
import com.stackmob.sdk.model.StackMobUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class initialActivity extends Activity {
	
	//initial setup
	private EditText email; 
	private EditText password;
	private Button loginButton;
	private Button cancelButton;
	private Button createUserButton;
	private JSONObject jObject;
	private String phoneNumber = "";
	private String deviceID = "";
	private User registration;
	private String enteredEmail;
	private String enteredPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intialpage);
		
		email = (EditText) findViewById(R.id.userEMail);
		password = (EditText) findViewById(R.id.userPassword);
		loginButton = (Button) findViewById(R.id.login);
		cancelButton= (Button) findViewById(R.id.cancel);
		createUserButton = (Button) findViewById(R.id.createUser);
		
		loginButton.setOnClickListener(loginButtonListener);
		cancelButton.setOnClickListener(cancelButtonListener);
		createUserButton.setOnClickListener(createUserButtonListener);
		
		email.setHint("Please enter your email.");
		password.setHint("Please enter a password.");
		
		Bundle bundle = this.getIntent().getExtras();
		deviceID = bundle.get("deviceID").toString();
		phoneNumber = bundle.getString("phoneNumber").toString();
		
		//StackMobAndroid.init(getApplicationContext(), 0, "f66ba52f-9d96-47a6-97ad-ec4bc95e9687");
		StackMobAndroid.init(getApplicationContext(), StackMob.OAuthVersion.Two, 0, "f66ba52f-9d96-47a6-97ad-ec4bc95e9687", "f66ba52f-9d96-47a6-97ad-ec4bc95e9687");
		StackMob.getStackMob().getSession().getLogger().setLogging(true);
		
		if(StackMob.getStackMob().isLoggedIn()) {
			User.getLoggedInUser(User.class, StackMobOptions.depthOf(2), new StackMobQueryCallback<User>() {
				@Override
				public void success(List<User> u) {
					registration = u.get(0);
					//initAdapter();
				}

				@Override
				public void failure(StackMobException e) {
					//doLogin();
				}
			});
		} else {
			//doLogin();
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		email.setText("");
		password.setText("");
		
	}
	
	//make JSON objects
	public OnClickListener loginButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v){
			
			getUserInput();
			login(getUser());
			/*
			Registration newUser = new Registration();
			newUser.createWithFacebook(facebookToken, new StackMobModelCallback() {
			    @Override
			    public void success() {
			    }
			 
			    @Override
			    public void failure(StackMobException e) {
			    }
			});
			
			
			/*r.login(new StackMobCallback() {
				
				@Override
				public void success(String arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "welcome back!", Toast.LENGTH_SHORT).show();
					Intent goToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(goToMainActivity);
				}
				
				@Override
				public void failure(StackMobException arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "unidentified user", Toast.LENGTH_SHORT).show();
				}
			});
			
			/*
				//Log.d("successfully saved stackmob", "okay");
				//Toast.makeText(getApplicationContext(), "saving stackmob", Toast.LENGTH_SHORT).show();
				
			}
			
			/*
			try {
				parseJSON(enteredEmail, enteredPassword);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(jObject != null){
				sendHTTPPost(jObject);
			}else{
				Log.d("JSON object is not parsed", "error JSON");
			}
			*/
			
			//go to next activity
			Intent goToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
			Toast.makeText(getApplicationContext(), "welcome returning user", Toast.LENGTH_LONG).show();
			startActivity(goToMainActivity);
		}
	};
	
	public OnClickListener cancelButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			clearInputs();
		}
	};
	
	public OnClickListener createUserButtonListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			
			getUserInput();
			
			if(enteredEmail != "" && enteredPassword != ""){
				User registration = new User(enteredEmail, enteredPassword
						,phoneNumber, deviceID);
				registration.save();
				Intent goToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
				Toast.makeText(getApplicationContext(), "welcome newcomer", Toast.LENGTH_LONG).show();
				startActivity(goToMainActivity);
			}else{
				Toast.makeText(getApplicationContext(), "invalid inputs", Toast.LENGTH_LONG).show();
			}
		}
	};
	
	public void parseJSON(String em, String pw) throws Exception{
		jObject.put("Email", em);
		jObject.put("Password", pw);
	}
	
	public void sendHTTPPost(JSONObject jObject){
		String url = ""; //need our server page
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpPost request = new HttpPost(url);
			StringEntity params =new StringEntity(jObject.toString());
			request.addHeader("content-type", "application/x-www-form-urlencoded");
	        request.setEntity(params);
	        HttpResponse response = httpClient.execute(request);
		}
		catch(Exception e){
			
		}
		finally{
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	public void getUserInput(){
		enteredEmail = email.getText().toString();
		enteredPassword = password.getText().toString();
	}
	
	public void clearInputs(){
		email.setText(R.string.empty);
		password.setText(R.string.empty);
	}
	
	public User getUser(){
		return new User(enteredEmail, enteredPassword, phoneNumber, deviceID);
	}
	
	public void login(final StackMobUser user){
		user.login(StackMobOptions.depthOf(2), new StackMobCallback() {
			
			@Override
			public void success(String arg0) {
				StackMobSession session = StackMob.getStackMob().getSession();
				Intent i = getIntent();
				//i.putExtra(inqueue.LOGGED_IN_USER, user.toJson());
				//setResult(RESULT_OK, i);
				finish();
				
			}
			
			@Override
			public void failure(StackMobException arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), arg0.getMessage(), 10).show();
			}
		});
	}
}
