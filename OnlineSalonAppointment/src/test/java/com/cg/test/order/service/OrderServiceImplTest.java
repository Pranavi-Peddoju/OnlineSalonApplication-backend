package com.cg.test.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.exception.DuplicateOrderException;
import com.cg.exception.OrderNotFoundException;
import com.cg.model.Address;
import com.cg.model.Customer;
import com.cg.model.Order;
import com.cg.model.PaymentMethod;
import com.cg.service.OrderServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class OrderServiceImplTest {

	@Autowired
	OrderServiceImpl orderImpl;

	@Test

	public void addOrderTest() throws DuplicateOrderException {
		Address address = new Address(2074, "12/348", "Rajiv nagar", "TPT", "AP", "634321");
		Customer customer = new Customer("MNeelima", "neelu@gmail.com", "9002232499", LocalDate.of(1999, 06, 22), address);
		customer.setUserId(150003);
		customer.setUserName("Neelima");
		customer.setPassword("neelu123");
		Order addTest = new Order(3789, 990, LocalDate.of(2020, 12, 23), customer, PaymentMethod.CREDIT_CARD.name());
		Order addTest1 = orderImpl.addOrder(addTest);
		assertEquals(addTest1, addTest);
	}

	@Test
	public void testUpdateOrder() throws OrderNotFoundException {
		Address address = new Address(2074, "12/348", "Rajiv nagar", "TPT", "AP", "634321");
		Customer customer = new Customer("MNeelima", "neelu@gmail.com", "9002232499", LocalDate.of(1999, 06, 22), address);
		customer.setUserId(150003);
		customer.setUserName("Neelima");
		customer.setPassword("neelu123");
		Order order1 = new Order(3789, 1996, LocalDate.of(2020, 12, 23), customer, PaymentMethod.CREDIT_CARD.name());
		Order upTest1 = orderImpl.updateOrder(order1);
		assertEquals(upTest1, order1);
	}

	@Test
	public void testGetOrderDetails() {
		Address address = new Address(2074, "12/348", "Rajiv nagar", "TPT", "AP", "634321");
		Customer customer = new Customer("MNeelima", "neelu@gmail.com", "9002232499", LocalDate.of(1999, 06, 22), address);
		customer.setUserId(150003);
		customer.setUserName("Neelima");
		customer.setPassword("neelu123");
		Order order1 = new Order(3789, 1966, LocalDate.of(2020, 12, 23), customer, PaymentMethod.CREDIT_CARD.name());

		Order ord1 = orderImpl.getOrderDetails(order1.getOrderId());
		assertEquals(ord1.getOrderId(), 3789);
	}

	@Test
	public void testGetAllOrders() {
		List<Order> list = orderImpl.getAllOrders();
		assertEquals(list.size(), 3);
	}

	@Test
	public void testRemoveOrder() throws OrderNotFoundException {
		Address address = new Address(2074, "12/348", "Rajiv nagar", "TPT", "AP", "634321");
		Customer customer = new Customer("MNeelima", "neelu@gmail.com", "9002232499", LocalDate.of(1999, 06, 22), address);
		customer.setUserId(150003);
		customer.setUserName("Neelima");
		customer.setPassword("neelu123");
		Order order1 = new Order(3789, 1966, LocalDate.of(2020, 12, 23), customer, PaymentMethod.CREDIT_CARD.name());
		boolean ord = orderImpl.removeOrder(order1.getOrderId());
		assertTrue(ord);
	}

	@Test
	public void testAddOrderException() throws DuplicateOrderException {
		Address address = new Address(808, "764", "Sai giri", "Vizag", "Andhra Pradesh", "589099");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07),
				address);
		customer.setUserId(135775);
		customer.setUserName("Pranavi12");
		customer.setPassword("pranavi123");
		Order addTest = new Order(3789, 990, LocalDate.of(2020, 12, 12), customer, PaymentMethod.CREDIT_CARD.name());
		assertThrows(DuplicateOrderException.class, () -> orderImpl.addOrder(addTest), "Order cannot be added");
	}

	@Test
	public void orderDetailsException() throws OrderNotFoundException {
		assertThrows(OrderNotFoundException.class, () -> orderImpl.getOrderDetails(365),
				"Sorry!! No Order Found By This Id");
	}

	@Test
	public void removeOrderException() throws OrderNotFoundException {
		assertThrows(OrderNotFoundException.class, () -> orderImpl.removeOrder(922), "No Order with this id to remove");
	}

	@Test
	public void updateOrderException() throws OrderNotFoundException {
		Address address = new Address(808, "764", "Sai giri", "Vizag", "Andhra Pradesh", "589099");
		Customer customer = new Customer("Pranavi", "pranavi@gmail.com", "9876543210", LocalDate.of(1999, 11, 07),
				address);
		customer.setUserId(125432);
		customer.setUserName("Pranavi12");
		customer.setPassword("pranavi123");
		Order addTest = new Order(3333, 990, LocalDate.of(1999, 9, 8), customer, PaymentMethod.CREDIT_CARD.name());
		assertThrows(OrderNotFoundException.class, () -> orderImpl.updateOrder(addTest), "No such order to update");
	}

}
