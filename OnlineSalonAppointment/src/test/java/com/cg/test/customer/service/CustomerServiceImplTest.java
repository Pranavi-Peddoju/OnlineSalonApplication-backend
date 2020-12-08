package com.cg.test.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
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
import com.cg.model.Address;
import com.cg.model.Customer;
import com.cg.service.CustomerServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CustomerServiceImplTest {
	
	@Autowired
	private CustomerServiceImpl service;

	@Test
	@Order(1)
	public void addCustomerTest() {
		Address address = new Address(2438, "81/A", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(125432);
		customer.setUserName("Pranavi12");
		customer.setPassword("pranavi123");
		Customer add = service.addCustomer(customer);
		assertEquals(customer, add);
	}
	
	@Test
	@Order(2)
	public void updateCustomerByNameTest() {
		Address address = new Address(2438, "81/B", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserName("Pranavi12");
		customer.setPassword("pranavi123");
		Customer update = service.updateCustomer(customer);
		assertEquals(customer, update);
	}
	
	@Test
	@Order(3)
	public void updateCustomerById_withUserNameTest() {
		Address address = new Address(2438, "81/B", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(125432);
		customer.setUserName("Pranavi_12");
		customer.setPassword("pranavi123");
		Customer update = service.updateCustomer(customer);
		assertEquals(customer, update);
	}
	
	@Test
	@Order(4)
	public void addCustomer_DuplicateRecordExceptionTest() {
		Address address = new Address(2438, "81/A", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(1239322);
		customer.setUserName("Pranavi_12");
		customer.setPassword("pranavi123");
		assertThrows(DuplicateRecordException.class, ()->service.addCustomer(customer), "User with this user Name  : " + customer.getUserName() + " Already Exists");
	}
	
	@Test
	@Order(5)
	public void getByIdTest() {
		Address address = new Address(2438, "81/B", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		Customer get = service.getCustomerById(125432);
		assertEquals(customer, get);
	}
	
	@Test
	@Order(6)
	public void getByUserNameTest() {
		Address address = new Address(2438, "81/B", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(123931);
		customer.setUserName("Pranavi_12");
		customer.setPassword("pranavi123");
		Customer get = service.getByUserName("Pranavi_12");
		assertEquals(customer, get);
	}
	
	@Test
	@Order(7)
	public void deleteCustomerTest() {
		boolean remove = service.removeCustomer(125432);
		assertTrue(remove);
	}
	
	@Test
	public void getAllCustomersTest() {
		List<Customer> customers = service.getAllCustomers();
		assertEquals(customers.size(),5);
	}
	
	@Test
	public void updateCustomerById_NoSuchUserExceptionTest() {
		Address address = new Address(2438, "81/A", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(125444);
		customer.setPassword("pranavi123");
		assertThrows(NoSuchUserException.class, ()->service.updateCustomer(customer), "No such user found");
	}
	
	@Test
	public void updateCustomerByName_NoSuchUserExceptionTest() {
		Address address = new Address(2438, "81/A", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(1254442);
		customer.setUserName("Pranavi_12");
		customer.setPassword("pranavi123");
		assertThrows(NoSuchUserException.class, ()->service.updateCustomer(customer), "No such user found");
	}
	
	@Test
	public void getById_InvalidInputExceptionTest() {
		assertThrows(InvalidInputException.class, ()->service.getCustomerById(1252), "The user Id must be 6 digits starting with 1");
	}
	
	@Test
	public void getById_NoSuchUserExceptionTest() {
		assertThrows(NoSuchUserException.class, ()->service.getCustomerById(125222), "No such user found");
	}
	
	

}
