package com.stg.inqueue;

//import com.stackmob.sdk.model.StackMobModel;
import com.stackmob.sdk.model.StackMobUser;

public class Registration extends StackMobUser{
	
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
