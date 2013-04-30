package com.stg.inqueue;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		ActionBar ab = getActionBar();
		ab.hide();
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		// Wait 2 seconds and switch to main page.
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run(){
				//Make phoneNumber.txt file every time app launches
				SDCard write = new SDCard();
				try {
					
					write.outputTextFile(getUserPhoneNumber());
					Bundle b = new Bundle();
					b.putString("number", getUserPhoneNumber());
					Intent i = new Intent(getApplicationContext(), MainActivity.class);
					i.putExtras(b);
			    	startActivityForResult(i, 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 2000);
	}
	
	public String getUserPhoneNumber() {

		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmNumber;
		tmNumber = "" + tm.getLine1Number();
		return tmNumber.toString();
	}

}
