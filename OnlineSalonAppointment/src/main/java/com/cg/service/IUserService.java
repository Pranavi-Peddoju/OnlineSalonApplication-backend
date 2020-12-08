package com.cg.service;

import com.cg.model.User;
import com.cg.model.UserCredentials;
import com.cg.model.UserDetails;

public interface IUserService {
	
	public User signOut(User user);
	public User signInWithUserId(UserCredentials userCredentials);
	public User signInWithUserName(UserCredentials userCredentials);

	public boolean changePassword(UserDetails userDetails);

}
