package com.stg.inqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.JsonReader;
import android.util.Log;

public class RestClient {
	static JSONObject json;

	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}


	/* This is a test function which will connects to a given
	 * rest service and prints it's response to Android Log with
	 * labels "Praeda".
	 */
	public static JSONObject connect(String url)
	{
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpGet httpget = new HttpGet(url); 

		// Execute the request
		HttpResponse response;
		try {
			
			response = httpclient.execute(httpget);
			// Examine the response status
			Log.i("front_end",response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result= convertStreamToString(instream);
				Log.i("front_end",result);

				// A Simple JSONObject Creation
				json = new JSONObject(result);
				Log.i("front_end","<jsonobject>\n"+json.toString()+"\n</jsonobject>");
				
				/*
				// A Simple JSONObject Parsing
				JSONArray nameArray=json.names();
				JSONArray valArray=json.toJSONArray(nameArray);
				for(int i=0;i<valArray.length();i++)
				{
					Log.i("front_end","<jsonname"+i+">\n"+nameArray.getString(i)+"\n</jsonname"+i+">\n"
							+"<jsonvalue"+i+">\n"+valArray.getString(i)+"\n</jsonvalue"+i+">");
				}

				// A Simple JSONObject Value Pushing
				json.put("sample key", "sample value");
				Log.i("front_end","<jsonobject>\n"+json.toString()+"\n</jsonobject>");

				// Closing the input stream will trigger connection release 
				instream.close();
				return json;
				*/
				instream.close();
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("front_end","returning json object");
		return json;
	}
	
	public boolean post(JSONObject jObject, String url){
			HttpClient httpclient = new DefaultHttpClient();
			
			HttpPost httpPost = new HttpPost();
			
			HttpResponse httpResponse;
			
			try{
				StringEntity sE = new StringEntity(jObject.toString());
				Log.i("front_end", "json to string" + jObject.toString());
				
				//sE.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				httpPost.setEntity(sE);
				httpPost.setHeader("Accept", "application/json");
			    httpPost.setHeader("Content-type", "application/json");
				httpResponse = httpclient.execute(httpPost);
				
				Log.i("front_end", "executing! httpPost");
				
				if(httpResponse != null){
					Log.i("front_end", httpResponse.toString());
					return true;
				}else{
					Log.i("front_end", "null!!!!!");
				}
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.i("front_end", "client protocol error");
			} catch (IOException e) {
				e.printStackTrace();
				Log.i("front_end", "IOexception error");
			}
			finally{
				//what can I do here?
			}
		
		return false;
	
	}
	
	public boolean postWithGet(JSONObject jObject, String url){
		
	
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpGet httpget = new HttpGet(url);
		
		try {
			StringEntity sE = new StringEntity(jObject.toString());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Execute the request
		HttpResponse response;
		try{
			response = httpclient.execute(httpget);
			Log.i("front_end", "success with post request");
			Log.i("front_end", response.getEntity().toString());
			return true;
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			//TODO: do something
		}
		
		return false;
		
	}
}