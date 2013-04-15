package com.stg.inqueue;

import java.util.ArrayList;
import java.util.List;

import com.stackmob.sdk.model.StackMobUser;

public class User extends StackMobUser {

	protected User(String email, String password) {
		super(User.class, email, password);
	}
	
	//add phone number later
}
