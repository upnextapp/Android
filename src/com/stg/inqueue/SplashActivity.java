package com.stg.inqueue;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.stackmob.sdk.api.*;
import com.stackmob.sdk.callback.StackMobModelCallback;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends Activity {
	
	userInformation userInfo;
	boolean goToInitial = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        userInfo = new userInformation(getUserPhoneNumber(), getUserDeviceID());
        
		/*Registration.query(Registration.class, new StackMobQuery().isInRange(0, 2), new StackMobQueryCallback<Registration>(){

			@Override
			public void failure(StackMobException arg0) {
				// TODO Auto-generated method stub
				Log.d("error 2", "failed to connect to stackmob");
			}

			@Override
			public void success(List<Registration> users) {
				// TODO Auto-generated method stub
				
				for(Registration u: users){
					if(u.getID().contains(userInfo.getDeviceID())){
						goToInitial = false;
					};
				}
			}
		});*/
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		// Wait 2 seconds and switch to main page.
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run(){
				
				/*registration.fetch(new StackMobModelCallback() {
				     public void success() {
				         // The blogPostTask object is now filled in with data.
				     }

				     public void failure(StackMobException e) {
				         // handle failure case
				     }
				 });
				
				//StackMobModelQuery<Task> highPriorityQuery = new StackMobModelQuery<Task>(Task.class).field(new StackMobField("priority").isGreaterThanOrEqualTo( 3).isLessThan(6)).fieldIsEqualTo("done", false);
				
				//StackMobModelQuery<Registration> q = new StackMobModelQuery<Registration>(Registration.class).field(new StackMobField("usermail").fieldIsNotNull(null));
				
				/*
				Registration.query(Registration.class, new StackMobQuery().isInRange(0), new StackMobQueryCallback<Registration>() {

					@Override
					public void failure(StackMobException arg0) {
						// TODO Auto-generated method stub
						Log.d("error 2", "failed to connect to stackmob");
					}

					@Override
					public void success(List<Registration> users) {
						// TODO Auto-generated method stub
						
						for(Registration u: users){
							if(u.getID().contains(userInfo.getDeviceID())){
								goToInitial = false;
							};
						}
					}
				});*/
				
				//goToInitial = false;
				startAnotherActivity();
			}
		}, 3000);
	}

	public void startAnotherActivity() {
		//switch back to main later
		if(!goToInitial){
			Intent goToMainActivity = new Intent(getApplicationContext(),MainActivity.class);
			//Toast.makeText(getApplicationContext(), "Main", Toast.LENGTH_SHORT).show();
			startActivity(goToMainActivity);
		}else{
			Intent goToInitialActivity = new Intent(getApplicationContext(), initialActivity.class);
			Bundle b = new Bundle();
			b.putString("deviceID", getUserDeviceID());
			b.putString("phoneNumber", getUserPhoneNumber());
			goToInitialActivity.putExtras(b);
			//Toast.makeText(getApplicationContext(), "Init", Toast.LENGTH_SHORT).show();
			startActivity(goToInitialActivity);
		}
	}
	
	public String getUserDeviceID() {
		/*
		 * //String Return_DeviceID =
		 * USERNAME_and_PASSWORD.getString(DeviceID_key,"Guest"); //return
		 * Return_DeviceID;
		 * 
		 * TelephonyManager TelephonyMgr = (TelephonyManager)
		 * getApplicationContext
		 * ().getApplicationContext().getSystemService(Context
		 * .TELEPHONY_SERVICE); String m_szImei = TelephonyMgr.getDeviceId(); //
		 * Requires // READ_PHONE_STATE
		 * 
		 * // 2 compute DEVICE ID String m_szDevIDShort = "35" + // we make this
		 * look like a valid IMEI Build.BOARD.length() % 10 +
		 * Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 +
		 * Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 +
		 * Build.HOST.length() % 10 + Build.ID.length() % 10 +
		 * Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 +
		 * Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 +
		 * Build.TYPE.length() % 10 + Build.USER.length() % 10; // 13 digits //
		 * 3 android ID - unreliable String m_szAndroidID =
		 * Secure.getString(getContentResolver(),Secure.ANDROID_ID); // 4 wifi
		 * manager, read MAC address - requires //
		 * android.permission.ACCESS_WIFI_STATE or comes as null WifiManager wm
		 * = (WifiManager)
		 * getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		 * String m_szWLANMAC = wm.getConnectionInfo().getMacAddress(); // 5
		 * Bluetooth MAC address android.permission.BLUETOOTH required
		 * BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth
		 * adapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		 * String m_szBTMAC = m_BluetoothAdapter.getAddress();
		 * System.out.println("m_szBTMAC "+m_szBTMAC);
		 * 
		 * // 6 SUM THE IDs String m_szLongID = m_szImei + m_szDevIDShort +
		 * m_szAndroidID+ m_szWLANMAC + m_szBTMAC;
		 * System.out.println("m_szLongID "+m_szLongID); MessageDigest m = null;
		 * try { m = MessageDigest.getInstance("MD5"); } catch
		 * (NoSuchAlgorithmException e) { e.printStackTrace(); }
		 * m.update(m_szLongID.getBytes(), 0, m_szLongID.length()); byte
		 * p_md5Data[] = m.digest();
		 * 
		 * String m_szUniqueID = new String(); for (int i = 0; i <
		 * p_md5Data.length; i++) { int b = (0xFF & p_md5Data[i]); // if it is a
		 * single digit, make sure it have 0 in front (proper // padding) if (b
		 * <= 0xF) m_szUniqueID += "0"; // add number to string m_szUniqueID +=
		 * Integer.toHexString(b); } m_szUniqueID = m_szUniqueID.toUpperCase();
		 * 
		 * Log.i("-------------DeviceID------------", m_szUniqueID);
		 * Log.d("DeviceIdCheck",
		 * "DeviceId that generated MPreferenceActivity:"+m_szUniqueID);
		 * 
		 * 
		 * //System.out.println(m_szUniqueID); return m_szUniqueID;
		 * 
		 * }
		 */

		// for prototype purposes, this is fine
		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String deviceId = deviceUuid.toString();

		return deviceId;
	}
	
	public String getUserPhoneNumber() {

		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmNumber;

		tmNumber = "" + tm.getLine1Number();
		return tmNumber.toString();

	}
}
