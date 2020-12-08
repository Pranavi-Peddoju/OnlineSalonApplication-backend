package com.cg.test.admin.servicemockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import com.cg.exception.DuplicateRecordException;
import com.cg.exception.InvalidInputException;
import com.cg.exception.NoSuchUserException;
import com.cg.model.Admin;
import com.cg.repository.AdminRepo;
import com.cg.service.AdminserviceImpl;

@SpringBootTest
public class AdminServiceImplTestWithMockito {

	@Autowired
	private AdminserviceImpl service;
	
	@MockBean
	private AdminRepo repoMock;

	@Test
	@Order(1)
	public void addCustomerTest() {
		Admin admin = new Admin("9800043210","jeevika@gmail.com");
		admin.setUserId(1527);
		admin.setUserName("Jeevika123");
		admin.setPassword("jeevika");	
		when(repoMock.findByUserName("Jeevika123")).thenReturn(null);
		Admin add = service.addAdmin(admin);
		assertEquals(admin, add);
	}
	
	
	
	@Test
	@Order(2)
	public void updateCustomerByNameTest() {
		Admin admin = new Admin("9876543210","jeevika@gmail.com");
		admin.setUserName("Jeevika123");
		admin.setPassword("jeevika");	
		when(repoMock.findByUserName("Jeevika123")).thenReturn(admin);
		when(repoMock.save(admin)).thenReturn(admin);
		Admin update = service.updateAdmin(admin);
		assertEquals(admin, update);
	}
	
	@Test
	@Order(3)
	public void updateCustomerById_withUserNameTest() {
		Admin admin= new Admin("9876543210","jeevika@gmail.com");
		admin.setUserId(1527);
		admin.setUserName("Jeevika");
		admin.setPassword("jeevika");	
		when(repoMock.existsById(1527L)).thenReturn(true);
		when(repoMock.findById(1527L)).thenReturn(Optional.of(admin));
		when(repoMock.save(admin)).thenReturn(admin);
		Admin update = service.updateAdmin(admin);
		assertEquals(admin, update);
	}
	
	@Test
	@Order(4)
	public void addCustomer_DuplicateRecordExceptionTest() {
		Admin admin = new Admin("9876543210","jeevika@gmail.com");
		admin.setUserId(1528);
		admin.setUserName("Jeevika");
		admin.setPassword("jeevika");
		when(repoMock.save(admin)).thenThrow(DataIntegrityViolationException.class);
		assertThrows(DuplicateRecordException.class, ()->service.addAdmin(admin), "User with this user Name  : " + admin.getUserName() + " Already Exists");
	}
	
	@Test
	@Order(5)
	public void getByIdTest() {
		Admin admin = new Admin("9876543210","jeevika@gmail.com");
		admin.setUserId(1527);
		admin.setUserName("Jeevika");
		admin.setPassword("jeevika");	
		when(repoMock.existsById(1527L)).thenReturn(true);
		when(repoMock.findById(1527L)).thenReturn(Optional.of(admin));
		Admin get = service.getAdminById(1527);
		assertEquals(admin, get);
	}
	
	@Test
	@Order(6)
	public void getByUserNameTest() {
		Admin admin = new Admin("9876543210","jeevika@gmail.com");
		admin.setUserId(1527);
		admin.setUserName("Jeevika");
		admin.setPassword("jeevika");
		
		when(repoMock.findByUserName("Jeevika")).thenReturn(admin);
		Admin get = service.getByUserName("Jeevika");
		assertEquals(admin, get);
	}
	
	@Test
	@Order(7)
	public void deleteCustomerTest() {
		Admin admin= new Admin("9876543210","jeevika@gmail.com");
		admin.setUserId(1527);
		admin.setUserName("Jeevika");
		admin.setPassword("jeevika");	
		when(repoMock.existsById(1527L)).thenReturn(true);
		when(repoMock.findById(1527L)).thenReturn(Optional.of(admin));
		boolean remove = service.removeAdmin(1527);
		assertTrue(remove);
	}
	
	@Test
	public void updateCustomerById_NoSuchUserExceptionTest() {
		Admin admin = new Admin("9876543210","jeevika@gmail.com");
		admin.setUserId(1597);
		admin.setPassword("jeevika");
		when(repoMock.existsById(1597L)).thenReturn(false);
		assertThrows(NoSuchUserException.class, ()->service.updateAdmin(admin), "No such user found");
	}
	
	@Test
	public void updateCustomerByName_NoSuchUserExceptionTest() {
		Admin admin = new Admin("9876543210","jeevika@gmail.com");
		admin.setUserName("Jeevika123");
		admin.setPassword("jeevika");
		when(repoMock.findByUserName("Jeevika123")).thenReturn(null);
		assertThrows(NoSuchUserException.class, ()->service.updateAdmin(admin), "No such user found");
	}
	
	@Test
	public void getById_InvalidInputExceptionTest() {
		assertThrows(InvalidInputException.class, ()->service.getAdminById(15555), "The user Id must be 4 digits starting with 1");
	}

	@Test
	public void getById_NoSuchUserExceptionTest() {
		when(repoMock.existsById(1254L)).thenReturn(false);
		assertThrows(NoSuchUserException.class, ()->service.getAdminById(1254), "No such user found");
	}
	
	@Test
	public void getAllCustomers_NoUserExceptionTest() {
		when(repoMock.findAll()).thenReturn(Collections.emptyList());
		assertThrows(NoSuchUserException.class, ()->service.getAllAdmins(), "Currently No Admin details");
		
	}
	

}
