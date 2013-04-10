package com.stg.inqueue;

import com.stackmob.sdk.model.StackMobModel;

public class Registration extends StackMobModel{
	
	private String userEmail;
	private String userPW;
	private String userPhoneNumber;
	private String userDeviceID;
	

	public Registration(String name, String password, String phoneNumber, String DeviceID){
		super(Registration.class);
		this.userEmail = name;
		this.userPW = password;
		this.userPhoneNumber = phoneNumber;
		this.userDeviceID = DeviceID;
	}

}
