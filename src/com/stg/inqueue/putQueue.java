package com.stg.inqueue;

import org.json.JSONObject;

import com.stg.inqueue.MainActivity.Callback;

import android.os.AsyncTask;
import android.util.Log;

public class PutQueue extends AsyncTask<JSONObject, Void, JSONObject> {
	
	private Callback mCallbacks;

	public PutQueue(Callback callback) {
		mCallbacks = callback;
	}

	protected JSONObject doInBackground(JSONObject... params) {
		JSONObject result = new JSONObject();
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
