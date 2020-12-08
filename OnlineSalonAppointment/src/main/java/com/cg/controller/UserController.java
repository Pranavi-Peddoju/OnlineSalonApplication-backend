package com.cg.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.model.User;
import com.cg.model.UserCredentials;
import com.cg.model.UserDetails;
import com.cg.service.UserServiceImpl;

/**
 * The UserController class Do the Authentication and Change Password of A User
 *
 * @author :Peddoju Teja Pranavi
 * @version :1.0
 * @since :2020-12-01
 **/

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	// Tells the application context to inject an instance of UserServiceImpl
	@Autowired
	private UserServiceImpl service;

	/**
	 * signIn
	 * <p>
	 * This method is used to login with The given Credentials.
	 * </p>
	 * 
	 * @param : userCredentials
	 * @return ResponseEntity<String> : This returns success message upon successful
	 *         login.
	 **/
	@PostMapping("/login")
	public ResponseEntity<String> signIn(@RequestBody @Valid UserCredentials userCredentials) {
		User userr;
		if (userCredentials.getPassword() == null || userCredentials.getUsername() == null) {
			return new ResponseEntity<>("Provide valid details to login", HttpStatus.BAD_REQUEST);
		} else if (userCredentials.getUsername().matches("[0-9]+")) {
			log.info("sending details to sign in with user Id ");
			userr = service.signInWithUserId(userCredentials);
		} else {
			log.info("sending details to sign in with user Name ");
			userr = service.signInWithUserName(userCredentials);
		}
		log.info("Sign in Success ");
		return new ResponseEntity<>("Hello " + userr.getUserName() + " Welcome! You are Successfully logged In",
				HttpStatus.OK);
	}

	/**
	 * changePassword
	 * <p>
	 * This method is used to change the password with The given Credentials
	 * Details.
	 * </p>
	 * 
	 * @param : userDetails
	 * @return ResponseEntity<String> : This returns success message upon successful
	 *         attempt to change password.
	 **/
	@PostMapping("/changepassword")
	public ResponseEntity<String> changePassword(@Valid @RequestBody UserDetails details) {
		service.changePassword(details);
		log.info("Change password success");
		return new ResponseEntity<>("You are Password is changed Successfully", HttpStatus.OK);
	}

	/**
	 * signout
	 * <p>
	 * This method is used to logout.
	 * </p>
	 * 
	 * @return ResponseEntity<String> : This returns success message upon successful
	 *         logout.
	 **/
	@PostMapping("/logout")
	public ResponseEntity<String> signOut() {
		return new ResponseEntity<>("You are Successfully logged Out", HttpStatus.OK);
	}

}
