package com.cg.test.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.exception.InvalidPasswordException;
import com.cg.exception.InvalidUserException;
import com.cg.model.User;
import com.cg.model.UserCredentials;
import com.cg.model.UserDetails;
import com.cg.service.UserServiceImpl;

@SpringBootTest
public class UserServiceImplTest {

	@Autowired
	private UserServiceImpl service;

	@Test
	public void signInWithUserNameTest() {
		UserCredentials userCredentials = new UserCredentials("HimaBindu", "himabindu");
		User user = service.signInWithUserName(userCredentials);
		assertEquals(user.getUserId(), 1721);
	}

	@Test
	public void changePasswordByIdTest() {
		UserDetails userDetails = new UserDetails("1426", "pranavi","pranavi123");
		boolean changed = service.changePassword(userDetails);
		assertTrue(changed);
	}
	
	@Test
	public void changePasswordByUserNameTest() {
		UserDetails userDetails = new UserDetails("Pranavi", "pranavi123","pranavi");
		boolean changed = service.changePassword(userDetails);
		assertTrue(changed);
	}

	@Test
	public void signInWithUserIdTest() {
		UserCredentials userCredentials = new UserCredentials("1426", "pranavi");
		User user = service.signInWithUserId(userCredentials);
		assertEquals(user.getUserName(), "Pranavi");
	}

	@Test
	public void invalidPasswordException_signInWithUserNameTest() {
		UserCredentials userCredentials = new UserCredentials("HimaBindu", "himabindu123");
		assertThrows(InvalidPasswordException.class, () -> service.signInWithUserName(userCredentials),
				"Invalid Password. Enter correct Paassword");
	}

	@Test
	public void invalidPasswordException_signInWithUserIdTest() {
		UserCredentials userCredentials = new UserCredentials("1426", "pranavi1234");
		assertThrows(InvalidPasswordException.class, () -> service.signInWithUserId(userCredentials),
				"Invalid Password. Enter correct Paassword");
	}

	@Test
	public void invalidUserException_changePasswordTest() {
		UserDetails userDetails = new UserDetails("1222", "pranavi","pranavi123");
		assertThrows(InvalidUserException.class, () -> service.changePassword(userDetails),
				"No such user Found with this user Id : " + 1222 + "Found. Please enter correct password");
	}
	

	@Test
	public void invalidUserException_signInWithUserNameTest() {
		UserCredentials userCredentials = new UserCredentials("Himabindu1", "himabindu");
		assertThrows(InvalidUserException.class, () -> service.signInWithUserName(userCredentials),
				"No such user with this username : " + userCredentials.getUsername()
						+ " Found. Register or use correct username");
	}

	@Test
	public void invalidUserException_signInWithUserIdTest() {
		UserCredentials userCredentials = new UserCredentials("1234", "himabindu");
		assertThrows(InvalidUserException.class, () -> service.signInWithUserId(userCredentials),
				"No such user with this username : " + userCredentials.getUsername()
						+ " Found. Register or use correct username");
	}

}
