package com.cg.test.user.servicemockito;

import static org.junit.jupiter.api.Assertions.assertEquals
;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.exception.InvalidPasswordException;
import com.cg.exception.InvalidUserException;
import com.cg.model.User;
import com.cg.model.UserCredentials;
import com.cg.model.UserDetails;
import com.cg.repository.IUserRepository;
import com.cg.service.UserServiceImpl;

@SpringBootTest
@SuppressWarnings("serial")
public class UserServiceImplTestWithMockito {
	
	@Autowired
	private UserServiceImpl service;
	
	@MockBean
	private IUserRepository repoMock;
	
	@Test
	public void signInWithUserNameTest() {
		UserCredentials userCredentials = new UserCredentials("HimaBindu", "himabindu");
		User userInDb = new User(1721,"HimaBindu","himabindu") {};
		when(repoMock.findByUserName("HimaBindu")).thenReturn(userInDb);
		User user = service.signInWithUserName(userCredentials);
		assertEquals(user.getUserId(), 1721);
	}
	
	@Test
	public void changePasswordByIdTest() {	
		User userr = new User(1426,"Pranavi","pranavi") {};
		UserDetails userDetails = new UserDetails("1426", "pranavi","pranavi123");
		when(repoMock.existsById(1426L)).thenReturn(true);
		when(repoMock.findById(1426L)).thenReturn(Optional.of(userr));
		when(repoMock.save(userr)).thenReturn(userr);
		boolean changed = service.changePassword(userDetails);
		assertTrue(changed);
	}
	
	@Test
	public void changePasswordByUserNameTest() {
		User userr = new User(1426,"Pranavi","pranavi") {};
		UserDetails userDetails = new UserDetails("Pranavi", "pranavi","pranavi123");
		when(repoMock.existsById(1426L)).thenReturn(true);
		when(repoMock.findByUserName("Pranavi")).thenReturn(userr);
		when(repoMock.save(userr)).thenReturn(userr);
		boolean changed = service.changePassword(userDetails);
		assertTrue(changed);
	}
	
	@Test
	public void signInWithUserIdTest() {
		UserCredentials userCredentials = new UserCredentials("1426", "pranavi");
		User userInDb = new User(1426,"Pranavi","pranavi") {
		};
		when(repoMock.existsById(1426L)).thenReturn(true);
		when(repoMock.findById(1426L)).thenReturn(Optional.of(userInDb));
		User user = service.signInWithUserId(userCredentials);
		assertEquals(user.getUserName(), "Pranavi");
	}
	
	@Test
	public void signInWithUserName_invalidPasswordExceptionTest() {
		UserCredentials userCredentials = new UserCredentials("HimaBindu", "himabindu123");
		User userInDb = new User(1721,"HimaBindu","himabindu") {};
		when(repoMock.findByUserName("HimaBindu")).thenReturn(userInDb);
		assertThrows(InvalidPasswordException.class,() -> service.signInWithUserName(userCredentials), 
				"Invalid Password. Enter correct Paassword");
	}
	
	@Test
	public void signInWithUserId_InvalidPasswordExceptionTest() {
		UserCredentials userCredentials = new UserCredentials("1426", "pranavi1234");
		User userInDb = new User(1426,"Pranavi","pranavi") {};
		when(repoMock.existsById(1426L)).thenReturn(true);
		when(repoMock.findById(1426L)).thenReturn(Optional.of(userInDb));
		assertThrows(InvalidPasswordException.class,() -> service.signInWithUserId(userCredentials), 
				"Invalid Password. Enter correct Paassword");
	}
	
	@Test
	public void changePassword_invalidUserExceptionTest() {
		UserDetails userDetails = new UserDetails("Praaaanavi", "pranavi","pranavi123");
		when(repoMock.existsById(1222L)).thenReturn(false);
		assertThrows(InvalidUserException.class,() -> service.changePassword(userDetails),
				"No such user Found with this user Id : "+ userDetails.getUsername() + "Found. Please enter correct password");
	}
	
	@Test
	public void signInWithUserName_invalidUserExceptionTest() {
		UserCredentials userCredentials = new UserCredentials("Himabindu1", "himabindu");
		when(repoMock.findByUserName("HimaBindu1")).thenReturn(null);
		assertThrows(InvalidUserException.class,() -> service.signInWithUserName(userCredentials), 
				"No such user with this username : " + userCredentials.getUsername()
				+ " Found. Register or use correct username");
	}
	
	@Test
	public void signInWithUserId_invalidUserExceptionTest() {
		UserCredentials userCredentials = new UserCredentials("1234", "himabindu");
		when(repoMock.existsById(1234L)).thenReturn(false);
		assertThrows(InvalidUserException.class,() -> service.signInWithUserId(userCredentials), 
				"No such user with this username : " + userCredentials.getUsername()
				+ " Found. Register or use correct username");
	}
	
}
