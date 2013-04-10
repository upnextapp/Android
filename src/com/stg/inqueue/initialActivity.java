package com.stg.inqueue;

import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.stackmob.android.sdk.common.StackMobAndroid;

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

public class initialActivity extends Activity {
	
	//initial setup
	private EditText email; 
	private EditText password;
	private Button submitButton;
	private Button cancelButton;
	private JSONObject jObject;
	private String phoneNumber = "";
	private String deviceID = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intialpage);
		
		email = (EditText) findViewById(R.id.userEMail);
		password = (EditText) findViewById(R.id.userPassword);
		submitButton = (Button) findViewById(R.id.submit);
		cancelButton= (Button) findViewById(R.id.cancel);
		
		submitButton.setOnClickListener(sumbitButtonListener);
		cancelButton.setOnClickListener(cancelButtonListener);
		
		//Bundle bundle = this.getIntent().getExtras();
		//deviceID = bundle.get("deviceID").toString();
		//phoneNumber = bundle.getString("phoneNumber").toString();
		
		StackMobAndroid.init(getApplicationContext(), 0, "f66ba52f-9d96-47a6-97ad-ec4bc95e9687");
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	//make JSON objects
	public OnClickListener sumbitButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v){
			
			String enteredEmail = email.getText().toString();
			String enteredPassword = password.getText().toString();
			
			
			/*if(enteredEmail != "" && enteredPassword != ""){
				Registration registration = new Registration(enteredEmail, enteredPassword
						,phoneNumber, deviceID);
				registration.save();
				
			
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
		}
	};
	
	public OnClickListener cancelButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			email.setText(R.string.empty);
			password.setText(R.string.empty);
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
	
}
