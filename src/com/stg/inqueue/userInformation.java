package com.stg.inqueue;

import java.util.UUID;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.os.Bundle;

public class userInformation {

	private String userPhoneNumber;
	private String userDeviceID;
	
	public userInformation(String phoneNumber, String DeviceID){
		this.userPhoneNumber = phoneNumber;
		this.userDeviceID = DeviceID;
	}
	
	public String getPhoneNumber(){
		return this.userPhoneNumber;
	}
	
	public String getDeviceID(){
		return this.userDeviceID;
	}
	
	
}
