package com.cg.test.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.exception.DuplicateRecordException;
import com.cg.exception.InvalidInputException;
import com.cg.exception.NoSuchUserException;
import com.cg.model.Admin;
import com.cg.service.AdminserviceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class AdminServiceImplTest {
	
	@Autowired
	private AdminserviceImpl service;

	@Test
	@Order(1)
	public void addAdminTest() {
		Admin admin = new Admin("9800043210","jeevika@gmail.com");
		admin.setUserId(1527);
		admin.setUserName("Jeevika123");
		admin.setPassword("jeevika");
		Admin add = service.addAdmin(admin);
		assertEquals(admin, add);
	}
	
	
	@Test
	@Order(2)
	public void updateAdminByNameTest() {
		Admin admin = new Admin("9876543210","jeevika@gmail.com");
		admin.setUserName("Jeevika123");
		admin.setPassword("jeevika");
		Admin update = service.updateAdmin(admin);
		assertEquals(admin, update);
	}
	
	@Test
	@Order(3)
	public void updateAdminByIdAndUserNameTest() {
		Admin admin = new Admin("9876543210","jeevika@gmail.com");
		admin.setUserId(1527);
		admin.setUserName("Jeevika1");
		admin.setPassword("jeevika");
		Admin update = service.updateAdmin(admin);
		assertEquals(admin, update);
	}
	
	@Test
	@Order(4)
	public void addAdmin_DuplicateRecordExceptionTest() {
		Admin admin = new Admin("9876543210","jeevika@gmail.com");
		admin.setUserId(1528);
		admin.setUserName("Jeevika");
		admin.setPassword("jeevika");
		assertThrows(DuplicateRecordException.class, ()->service.addAdmin(admin), "User with this user Name  : " + admin.getUserName() + " Already Exists");
	}
	
	@Test
	@Order(5)
	public void getByIdTest() {
		Admin admin = new Admin("9876543210","jeevika@gmail.com");
		admin.setUserId(1527);
		admin.setUserName("Jeevika");
		admin.setPassword("jeevika");
		Admin get = service.getAdminById(1527);
		assertEquals(admin, get);
	}
	
	@Test
	@Order(6)
	public void getByUserNameTest() {
		Admin admin = new Admin("8067909422","jeevika@gmail.com");
		admin.setUserId(1527);
		admin.setUserName("Jeevika");
		admin.setPassword("jeevika");
		Admin get = service.getByUserName("Jeevika");
		assertEquals(admin, get);
	}
	
	@Test
	@Order(7)
	public void deleteAdminTest() {
		boolean remove = service.removeAdmin(1527);
		assertTrue(remove);
	}
	
	@Test
	public void getAllAdminsTest() {
		List<Admin> admins = service.getAllAdmins();
		assertEquals(admins.size(),4);
	}

	
	@Test
	public void updateAdminById_NoSuchUserExceptionTest() {
		Admin admin = new Admin("9876543210","jeevika@gmail.com");
		admin.setUserId(15999);
		admin.setUserName("Jeevika1233");
		admin.setPassword("jeevika");
		assertThrows(NoSuchUserException.class, ()->service.updateAdmin(admin), "No such user found");
	}
	
	@Test
	public void updateAdminByName_NoSuchUserExceptionTest() {
		Admin admin = new Admin("9876543210","jeevika@gmail.com");
		admin.setUserName("Jeevika123");
		admin.setPassword("jeevika");
		assertThrows(NoSuchUserException.class, ()->service.updateAdmin(admin), "No such user found");
	}
	
	@Test
	public void getById_InvalidInputExceptionTest() {
		assertThrows(InvalidInputException.class, ()->service.getAdminById(125222), "The user Id must be 4 digits starting with 1");
	}
	
	@Test
	public void getById_NoSuchUserExceptionTest() {
		assertThrows(NoSuchUserException.class, ()->service.getAdminById(1252), "No such user found");
	}
	
	

}
