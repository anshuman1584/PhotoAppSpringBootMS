package com.appsdeveloperblog.app.ws.userservice.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import com.appsdeveloperblog.app.ws.userservice.UserService;

//The framework will scan our packages and if it sees the class that is annotated with @Service annotation it will create an instanc of it
// and it will make it available to those classes that requst those objects to be autowired
@Service	
public class UserServiceImpl implements UserService{
	
	Map<String,UserRest> users;
	Utils utils;
	
	// Util is made @Service to be Autowired here. Similarly UserServiceImpl is made @Service to be Autowired in UserController
	@Autowired
	public UserServiceImpl(Utils utils) {
		this.utils=utils;
	}
	
	public UserServiceImpl () {
		
	}

	@Override
	public UserRest createUser(UserDetailsRequestModel userDetails) {
		UserRest user = new UserRest();
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setEmail(userDetails.getEmail());
		String userId=this.utils.generateUserId();
		user.setUserId(userId);
		if(users ==null) users=new HashMap<String, UserRest>();
		users.put(userId, user);
		return user;
	}

}
