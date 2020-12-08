package com.cg.test.order.servicemockio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.exception.DuplicateOrderException;
import com.cg.exception.OrderNotFoundException;
import com.cg.model.Address;
import com.cg.model.Customer;
import com.cg.model.Order;
import com.cg.model.PaymentMethod;
import com.cg.repository.IOrderRepository;
import com.cg.service.OrderServiceImpl;

@SpringBootTest
public class OrderMockTest {

	@Autowired
	OrderServiceImpl orderImpl;

	@MockBean
	private IOrderRepository orderRepo;

	@Test
	void testAddOrder() throws DuplicateOrderException {
		Address address = new Address(808, "764", "Sai giri", "Vizag", "Andhra Pradesh", "589099");
		Customer customer = new Customer("Neelima", "neelu@gmail.com", "9876543210", LocalDate.of(1999, 11, 07),
				address);
		customer.setUserId(125432);
		customer.setUserName("Neelima12");
		customer.setPassword("neelima123");
		Order addTest = new Order(351, 990, LocalDate.of(1999, 9, 8), customer, PaymentMethod.CREDIT_CARD.name());
		when(orderRepo.save(addTest)).thenReturn(addTest);
		Order result = orderImpl.addOrder(addTest);
		assertEquals(result, addTest);
	}

	@Test
	void testAddOrderShouldThrowDuplicateEXception() throws DuplicateOrderException {
		Address address = new Address(808, "764", "Sai giri", "Vizag", "Andhra Pradesh", "589099");
		Customer customer = new Customer("Neelima", "neelu@gmail.com", "9876543210", LocalDate.of(1999, 11, 07),
				address);
		customer.setUserId(125432);
		customer.setUserName("Neelima12");
		customer.setPassword("neelima123");
		Order addTest = new Order(351, 990, LocalDate.of(2020, 12, 13), customer, PaymentMethod.CREDIT_CARD.name());
		when(orderImpl.addOrder(addTest)).thenThrow(DuplicateOrderException.class);
		assertThrows(DuplicateOrderException.class, () -> {
			orderImpl.addOrder(addTest);
		});
	}

	@Test
	void testUpdateOrder() throws OrderNotFoundException {
		Address address = new Address(808, "764", "Sai giri", "Vizag", "Andhra Pradesh", "589099");
		Customer customer = new Customer("Neelima", "neelu@gmail.com", "9876543210", LocalDate.of(1999, 11, 07),
				address);
		customer.setUserId(125432);
		customer.setUserName("Neelima12");
		customer.setPassword("neelima123");
		Order updateTest = new Order(351, 990, LocalDate.of(2020, 12, 13), customer, PaymentMethod.CREDIT_CARD.name());
		when(orderRepo.existsById(351L)).thenReturn(true);
		when(orderRepo.findById(351L)).thenReturn(Optional.of(updateTest));

		when(orderRepo.save(updateTest)).thenReturn(updateTest);
		Order result = orderImpl.updateOrder(updateTest);
		assertEquals(result, updateTest);
	}

	@Test
	void testUpdateOrderShouldThrowOrderNotFoundEXception() {
		Address address = new Address(808, "764", "Sai giri", "Vizag", "Andhra Pradesh", "589099");
		Customer customer = new Customer("Neelima", "neelu@gmail.com", "9876543210", LocalDate.of(1999, 11, 07),
				address);
		customer.setUserId(125432);
		customer.setUserName("Neelima12");
		customer.setPassword("neelima123");
		Order addTest = new Order(351, 990, LocalDate.of(2020, 12, 13), customer, PaymentMethod.CREDIT_CARD.name());
		// when(orderImpl.addOrder(addTest)).thenThrow(OrderNotFoundException.class);
		assertThrows(OrderNotFoundException.class, () -> {
			orderImpl.updateOrder(addTest);
		});
	}

	@Test
	void testGetOrderDetails() throws OrderNotFoundException {
		Address address = new Address(808, "764", "Sai giri", "Vizag", "Andhra Pradesh", "589099");
		Customer customer = new Customer("Neelima", "neelu@gmail.com", "9876543210", LocalDate.of(1999, 11, 07),
				address);
		customer.setUserId(125432);
		customer.setUserName("Neelima12");
		customer.setPassword("neelima123");
		Order order = new Order(351, 990, LocalDate.of(2020, 12, 13), customer, PaymentMethod.CREDIT_CARD.name());
		when(orderRepo.existsById(351L)).thenReturn(true);
		when(orderRepo.findById(351L)).thenReturn(Optional.of(order));
		Order get = orderImpl.getOrderDetails(351L);
		assertEquals(order, get);
	}

	@Test
	void testGetOrderShouldThrowOrderNotFoundEXception() {

		assertThrows(OrderNotFoundException.class, () -> {
			orderImpl.getOrderDetails(36666);
		});
	}

	@Test
	void testRemoveOrder() throws OrderNotFoundException {
		Address address = new Address(808, "764", "Sai giri", "Vizag", "Andhra Pradesh", "589099");
		Customer customer = new Customer("Neelima", "neelu@gmail.com", "9876543210", LocalDate.of(1999, 11, 07),
				address);
		customer.setUserId(125432);
		customer.setUserName("Pranavi12");
		customer.setPassword("pranavi123");
		Order order = new Order(351, 990, LocalDate.of(2020, 12, 13), customer, PaymentMethod.CREDIT_CARD.name());

		when(orderRepo.existsById(351L)).thenReturn(true);
		when(orderRepo.findById(351L)).thenReturn(Optional.of(order));
		boolean remove = orderImpl.removeOrder(351L);
		assertTrue(remove);

	}

	@Test
	void testRemoveOrderShouldThrowOrderNotFoundEXception() {

		assertThrows(OrderNotFoundException.class, () -> {
			orderImpl.removeOrder(399999);
		});
	}

	@Test
	void testGetAllDetails() throws OrderNotFoundException {
		List<Order> list = new ArrayList<>();
		Address address = new Address(808, "764", "Sai giri", "Vizag", "Andhra Pradesh", "589099");
		Customer customer = new Customer("Neelima", "neelu@gmail.com", "9876543210", LocalDate.of(1999, 11, 07),
				address);
		customer.setUserId(125432);
		customer.setUserName("Neelima12");
		customer.setPassword("neelima123");
		Order order1 = new Order(351, 990, LocalDate.of(2020, 12, 13), customer, PaymentMethod.CREDIT_CARD.name());
		Order order2 = new Order(352, 990, LocalDate.of(2020, 12, 13), customer, PaymentMethod.CREDIT_CARD.name());
		Order order3 = new Order(351, 990, LocalDate.of(2020, 12, 13), customer, PaymentMethod.CREDIT_CARD.name());
		list.add(order1);
		list.add(order2);
		list.add(order3);
		when(orderRepo.findAll()).thenReturn(list);
		List<Order> Orders = orderImpl.getAllOrders();
		assertEquals(list, Orders);
	}

	@Test
	void testGetAll_OrderNotFoundException() {
		when(orderRepo.findAll()).thenReturn(Collections.emptyList());
		assertThrows(OrderNotFoundException.class, () -> orderImpl.getAllOrders(), "No Orders");
	}
}
