package com.cg.test.customer.servicemockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import com.cg.model.Address;
import com.cg.model.Customer;
import com.cg.repository.ICustomerRepository;
import com.cg.service.CustomerServiceImpl;

@SpringBootTest
public class CustomerServiceImplTestWithMockito {

	@Autowired
	private CustomerServiceImpl service;
	
	@MockBean
	private ICustomerRepository repoMock;

	@Test
	@Order(1)
	public void addCustomerTest() {
		Address address = new Address(2438, "81/A", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(125432);
		customer.setUserName("Pranavi12");
		customer.setPassword("pranavi123");
		when(repoMock.findByUserName("Pranavi12")).thenReturn(null);
		Customer add = service.addCustomer(customer);
		assertEquals(customer, add);
	}
	
	@Test
	@Order(2)
	public void updateCustomerById_withoutUserNameTest() {
		Address address = new Address(2438, "81/B", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi123@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(125432);
		customer.setPassword("pranavi123");
		when(repoMock.existsById(125432L)).thenReturn(true);
		when(repoMock.findById(125432L)).thenReturn(Optional.of(customer));
		when(repoMock.findByAddress(2438L)).thenReturn(address);
		when(repoMock.save(customer)).thenReturn(customer);
		Customer update = service.updateCustomer(customer);
		assertEquals(customer, update);
	}
	
	@Test
	@Order(3)
	public void updateCustomerByNameTest() {
		Address address = new Address(2438, "81/B", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserName("Pranavi12");
		customer.setPassword("pranavi123");
		when(repoMock.findByUserName("Pranavi12")).thenReturn(customer);
		when(repoMock.findByAddress(2438L)).thenReturn(address);
		when(repoMock.save(customer)).thenReturn(customer);
		Customer update = service.updateCustomer(customer);
		assertEquals(customer, update);
	}
	
	@Test
	@Order(4)
	public void updateCustomerById_withUserNameTest() {
		Address address = new Address(2438, "81/B", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(125432);
		customer.setUserName("Pranavi_12");
		customer.setPassword("pranavi123");
		when(repoMock.existsById(125432L)).thenReturn(true);
		when(repoMock.findById(125432L)).thenReturn(Optional.of(customer));
		when(repoMock.findByAddress(2438L)).thenReturn(address);
		when(repoMock.save(customer)).thenReturn(customer);
		Customer update = service.updateCustomer(customer);
		assertEquals(customer, update);
	}
	
	@Test
	@Order(5)
	public void addCustomer_DuplicateRecordExceptionTest() {
		Address address = new Address(2438, "81/A", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(125432);
		customer.setUserName("Pranavi_12");
		customer.setPassword("pranavi123");
		when(repoMock.save(customer)).thenThrow(DataIntegrityViolationException.class);
		assertThrows(DuplicateRecordException.class, ()->service.addCustomer(customer), "User with this user Name  : " + customer.getUserName() + " Already Exists");
	}
	
	@Test
	@Order(6)
	public void getByIdTest() {
		Address address = new Address(2438, "81/B", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		when(repoMock.existsById(125432L)).thenReturn(true);
		when(repoMock.findById(125432L)).thenReturn(Optional.of(customer));
		Customer get = service.getCustomerById(125432);
		assertEquals(customer, get);
	}
	
	@Test
	@Order(7)
	public void getByUserNameTest() {
		Address address = new Address(2438, "81/B", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(123931);
		customer.setUserName("Pranavi_12");
		customer.setPassword("pranavi123");
		when(repoMock.findByUserName("Pranavi_12")).thenReturn(customer);
		Customer get = service.getByUserName("Pranavi_12");
		assertEquals(customer, get);
	}
	
	@Test
	@Order(8)
	public void deleteCustomerTest() {
		Address address = new Address(2438, "81/B", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(123931);
		customer.setUserName("Pranavi_12");
		customer.setPassword("pranavi123");
		when(repoMock.existsById(125432L)).thenReturn(true);
		when(repoMock.findById(125432L)).thenReturn(Optional.of(customer));
		boolean remove = service.removeCustomer(125432);
		assertTrue(remove);
	}
	
	@Test
	public void updateCustomerById_NoSuchUserExceptionTest() {
		Address address = new Address(2438, "81/A", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserId(125432);
		customer.setPassword("pranavi123");
		when(repoMock.existsById(125444L)).thenReturn(false);
		assertThrows(NoSuchUserException.class, ()->service.updateCustomer(customer), "No such user found");
	}
	
	@Test
	public void updateCustomerByName_NoSuchUserExceptionTest() {
		Address address = new Address(2438, "81/A", "lakshmi sri colony", "Hyderabad", "Telangana", "900121");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07), address);
		customer.setUserName("Pranavi123");
		customer.setPassword("pranavi123");
		when(repoMock.findByUserName("Pranavi123")).thenReturn(null);
		assertThrows(NoSuchUserException.class, ()->service.updateCustomer(customer), "No such user found");
	}
	
	@Test
	public void getById_InvalidInputExceptionTest() {
		assertThrows(InvalidInputException.class, ()->service.getCustomerById(1252), "The user Id must be 6 digits starting with 1");
	}
	
	@Test
	public void getById_NoSuchUserExceptionTest() {
		when(repoMock.existsById(125432L)).thenReturn(false);
		assertThrows(NoSuchUserException.class, ()->service.getCustomerById(125222), "No such user found");
	}
	
	@Test
	public void getAllCustomers_NoUserExceptionTest() {
		when(repoMock.findAll()).thenReturn(Collections.emptyList());
		assertThrows(NoSuchUserException.class, ()->service.getAllCustomers(), "Currently No Customer details");
		
	}
	

}
