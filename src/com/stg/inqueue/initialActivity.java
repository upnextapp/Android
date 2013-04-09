package com.stg.inqueue;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
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
		public void onClick(View v){
			
			String enteredEmail = email.getText().toString();
			String enteredPassword = password.getText().toString();
			
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
