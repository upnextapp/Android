package com.stg.inqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;
 
public class JSONParser {
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    static InputStream instream = null;
 
    // constructor
    public JSONParser() {
    	//empty constructor
    }
 
    /*
    public JSONObject getJSONFromUrl(String url) {
    	
    	
    	
    	 DefaultHttpClient httpClient = new DefaultHttpClient();
         HttpGet httpGet = new HttpGet(url);
 
         HttpResponse httpResponse = null;
         HttpEntity httpEntity = null;
        // Making HTTP request
        try {
            httpResponse= httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            //is = httpEntity.getContent();           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("first error", "error 1");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.e("2 error", "error 2");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("3 error", "error 3");
        }
        
        // A Simple JSON Response Read
		try {
			instream = httpEntity.getContent();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result= convertStreamToString(instream);
		
		
        
        /*
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
 
        // return JSON String
        return jObj;
        
 
    }
	*/
    
    public void postRequest(JSONObject jo,String url) throws Exception{
		
    	DefaultHttpClient httpClient = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost(url);
    	
    	StringEntity se = new StringEntity(jo.toString());
    	httpPost.setEntity(se);
    	httpPost.setHeader("Accept", "application/json");
    	httpPost.setHeader("Content-type", "application/json");

    	HttpResponse httpResponse = httpClient.execute(httpPost);
    	ResponseHandler responseHandler = new BasicResponseHandler();
    	httpResponse = httpClient.execute(httpPost, responseHandler);
    	
    }
    
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

}