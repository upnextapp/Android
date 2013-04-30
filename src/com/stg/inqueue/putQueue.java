package com.stg.inqueue;

import org.json.JSONObject;

import com.stg.inqueue.MainActivity.Callback;

import android.os.AsyncTask;
import android.util.Log;

public class putQueue extends AsyncTask<JSONObject, Void, Boolean> {
	
	private Callback mCallbacks;

	public putQueue(Callback callback) {
		mCallbacks = callback;
	}

	protected Boolean doInBackground(JSONObject... params) {
		boolean result = false;
		String url_enterQueue = "http://ec2-54-244-184-198.us-west-2.compute.amazonaws.com/api/" +
				"queue";
		
		JSONObject jObject = params[0];
		RestClient rC = new RestClient();
		result = rC.postWithGet(jObject, url_enterQueue);
		Log.i("front_end", "async success post");
		return result;
	}

	protected void onPostExecute(Void result) {
		mCallbacks.onComplete();
	}

}
