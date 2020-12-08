package com.cg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.exception.InvalidPasswordException;
import com.cg.exception.InvalidUserException;
import com.cg.model.User;
import com.cg.model.UserCredentials;
import com.cg.model.UserDetails;
import com.cg.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private IUserRepository repository;
	private UserCredentials userCredentials;
	private EmailSenderService emailSenderService;
	private CustomerServiceImpl customerService;

	@Autowired
	public UserServiceImpl(IUserRepository repository, UserCredentials userCredentials,
			EmailSenderService emailSenderService, CustomerServiceImpl customerService) {
		super();
		this.repository = repository;
		this.userCredentials = userCredentials;
		this.emailSenderService = emailSenderService;
		this.customerService = customerService;
	}

	/**
	 * This method is used to Verify Whether The User with The given Credentials is
	 * Exists or not. And allows Sign In with User ID
	 * 
	 * @return User : This returns the user.
	 **/
	@Override
	public User signInWithUserId(UserCredentials userCredentials) {
		long id = Integer.parseInt(userCredentials.getUsername());
		if (repository.existsById(id)) {
			log.info("Details exits. Getting the details with user Id : " +id);
			User userInDb = repository.findById(id).get();
			if (userCredentials.getPassword().equals(userInDb.getPassword())) {
				log.info("Entered password is correct");
				return userInDb;
			} else {
				log.error("Entered password is incorrect. Throwing exception");
				throw new InvalidPasswordException("Invalid Password. Enter correct Paassword");
			}
		} else {
			log.error("No user found with user Id  : " + id + " Thrrowing Exception");
			throw new InvalidUserException("No such user with this username : " + userCredentials.getUsername()
					+ " Found. Register or use correct username");
		}
	}

	/**
	 * This method is used to Verify Whether The User with The given Credentials is
	 * Exists or not. And allows Sign In with User Name
	 * 
	 * @return UserCredentials : This returns of the user details.
	 **/
	@Override
	public User signInWithUserName(UserCredentials userCredentials) {
		User userInDb = repository.findByUserName(userCredentials.getUsername());
		if (userInDb != null) {
			log.info("Details exits. Getting the details with user Name : " +userCredentials.getUsername());
			if (userCredentials.getPassword().equals(userInDb.getPassword())) {
				log.info("Entered password is correct");
				return userInDb;
			} else {
				log.error("Entered password is incorrect. Throwing exception");
				throw new InvalidPasswordException("Invalid Password. Enter correct Paassword");
			}
		} else {
			log.error("No user found with user Name  : " + userCredentials.getUsername() + " Thrrowing Exception");
			throw new InvalidUserException("No such user with this username : " + userCredentials.getUsername()
					+ " Found. Register or use correct username");
		}

	}

	@Override
	public User signOut(User user) {
		return null;
	}

	/**
	 * This method is verifies Whether The User with given user name exits and old
	 * password is correct And then allows to change password with the new password
	 * 
	 * @return boolean : This returns boolean value true on successful updation
	 **/
	@Override
	public boolean changePassword(UserDetails userDetails) {
		String userId = userDetails.getUsername();
		String password = userDetails.getOldPassword();
		userCredentials.setPassword(password);
		userCredentials.setUsername(userId);
		User toChangePassword;
		if (userId.matches("[0-9]+")) {
			log.info("Getting user details with user Id");
			toChangePassword = signInWithUserId(userCredentials);
		} else {
			log.info("Getting user details with user Name");
			toChangePassword = signInWithUserName(userCredentials);
		}
		toChangePassword.setPassword(userDetails.getNewPassword());
		log.info("Password changed for user with user Id  : "+ toChangePassword.getUserId());
		repository.save(toChangePassword);
		log.info("Sending mail to the user");
		//emailSenderService.changePasswordSuccess(customerService.getCustomerById(toChangePassword.getUserId()));
		return true;

	}
}
