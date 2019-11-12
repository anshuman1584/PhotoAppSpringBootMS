package com.appsdeveloperblog.app.ws.ui.controller;


import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import com.appsdeveloperblog.app.ws.userservice.UserService;

// dependency injection helps to keep classes independent of each other as we are not creating object of another class in a class using new.We use Autowire etc
// dependency injection helps to make classes independent of each other
// keep businees logic in a separate class i.e. service class or a util class.
// we cant keep everything in a createuser method.we need to break it.So that some goes to service class,some to utility class and some to DAo class (to communicate with DB)
// after this we need to create instances of these clases in createUser method which makes direct dependency on these clases which is not god.We will
// not be able to test createuser method independently of those three classes using JUnit,Mockito.We will not be able to mock them to test createUser

// To install and create jar ->  mvn install
// To run jar ->   java -jar mobile-apps-ws-0.0.1-SNAPSHOT.jar
// You can now send request to the applicatio
// to stop app -->  ctrl+C
// to clean target directory --> mvn clean

@RestController
@RequestMapping("users") // http:/localhost:8080/users/
public class UserController {
	
	//Let spring create an instance of the UserServieImpl
	// The spring will create an instance of UserServiceImpl and inject that instance into the UserController object and will make it available to us
	//for the framework to be able to discover the UserServiceImpl and to be able to autowire it we will need to declare the UserServiceImpl as Service using @Service annotation
	//we are not creating direct dependency by using new so our testing framework junit and mockito can mock the userservice and test the createUser method
	@Autowired
	UserService userService;
	
	Map<String,UserRest> users;
	
	@GetMapping
	public String getUsers(@RequestParam(value="page" , defaultValue = "2") int page, 
						   @RequestParam(value="limit", defaultValue = "40") int limit,
						   @RequestParam(value="sort", defaultValue = "desc",required = false) String sort) {
		return "users page=  "+ page + " limit = "+limit;
	}

	@GetMapping(path="/{userId}",produces = {MediaType.APPLICATION_JSON_VALUE,
											 MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
		
//		if(true) {
//			throw new UserServiceException("A user service exception is thrown.");
//		}
		if(users.containsKey(userId)) {
			return new ResponseEntity<UserRest>(users.get(userId),HttpStatus.OK);
		}else {
			return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@PostMapping(consumes = 
				{MediaType.APPLICATION_JSON_VALUE,
				 MediaType.APPLICATION_XML_VALUE},
				produces = 
				 {MediaType.APPLICATION_JSON_VALUE,
				  MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
		
		// This is creating direct dependency on UserServiceImpl.Whiel Testing createUser method I will not be able to mock UserServiceImpl and I will
		// not be able to test createUser method independently.So we want to avoid this dependency and want to use Dependency Injection by Autowiring 
		// the UserService at the top
//		UserRest user = new UserServiceImpl().createUser(userDetails);
		UserRest user = userService.createUser(userDetails);
		return new ResponseEntity<UserRest>(user,HttpStatus.OK);
	}
	
	@PutMapping(path="/{userId}",consumes = 
				{MediaType.APPLICATION_JSON_VALUE,
				 MediaType.APPLICATION_XML_VALUE},
				produces = 
				 {MediaType.APPLICATION_JSON_VALUE,
				  MediaType.APPLICATION_XML_VALUE})
	public UserRest updateUser(@Valid @RequestBody UpdateUserDetailsRequestModel userDetails,@PathVariable String userId) {
		
		UserRest user= users.get(userId);
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		users.put(userId, user);
		return user;
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable String id) {
		users.remove(id);
		
		return ResponseEntity.noContent().build();
	}
}
