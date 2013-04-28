package com.stg.inqueue;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import com.stg.inqueue.MainActivity.Callback;

import android.os.AsyncTask;

public class GetBusiness extends AsyncTask<Void, Void, JSONObject>{
	
	private Callback mCallbacks;
	
	public GetBusiness(Callback callback){
		mCallbacks = callback;
	}

	@Override
	protected JSONObject doInBackground(Void... params) {
		
		String url_getRestaurants = "http://ec2-54-244-184-198.us-west-2.compute.amazonaws.com/" +
				"api/list";
		//JSON request to grab businesses
		RestClient rc = new RestClient();
		//JSONObject jsonObject = rc.connect(url_getRestaurants);
		JSONObject restaurants = rc.connect(url_getRestaurants);
		return restaurants;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		mCallbacks.onComplete();
	}
	
}
