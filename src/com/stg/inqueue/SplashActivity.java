package com.stg.inqueue;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		ActionBar actionBar = getActionBar();
        actionBar.hide();
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		// Wait 2 seconds and switch to main page.
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				startAnotherActivity();
			}
		}, 2000);
	}

	public void startAnotherActivity() {
		Intent goToNextActivity = new Intent(getApplicationContext(),
				MainActivity.class);
		startActivity(goToNextActivity);

	}
}
