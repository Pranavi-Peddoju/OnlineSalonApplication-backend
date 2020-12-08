package com.cg.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.exception.DuplicateOrderException;
import com.cg.exception.OrderNotFoundException;
import com.cg.model.Order;
import com.cg.service.OrderServiceImpl;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderServiceImpl service;

	@Autowired
	private GenerateID auto;

	/**
	 * 
	 * @param order calls service method for adding order
	 * @return order posted if no order is already placed with that id, otherwise
	 * @throws DuplicateOrderException
	 */
	@PostMapping
	public Order addOrder(@Valid @RequestBody Order order) {
		order.setOrderId(auto.generateID(order));
		Order addOrd = service.addOrder(order);

		return addOrd;
	}

	/**
	 * 
	 * @param order calls service method to update order by finding its id
	 * @return updated order if order found, otherwise
	 * @throws OrderNotFoundException
	 */
	@PutMapping
	public Order updateOrder(@Valid @RequestBody Order order) {
		Order updOrd = service.updateOrder(order);
		return updOrd;
	}

	/**
	 * 
	 * @param id calls service method to get order details by id
	 * @return order of that id if found, otherwise
	 * @throws OrderNotFoundException
	 */
	@GetMapping("/{id}")
	public Order getOrderDetailsById(@Valid @PathVariable("id") long id) {
		Order getOrd = service.getOrderDetails(id);
		return getOrd;
	}

	/**
	 * calls service to get all orders
	 * 
	 * @return all orders if number of orders not zero, otherwise
	 * @throws OrderNotFoundException
	 */
	@GetMapping
	public List<Order> getAllOrderDetails() {
		List<Order> getAll = service.getAllOrders();
		return getAll;
	}

	/**
	 * calls delete method in service
	 * 
	 * @param id
	 * @return a string message if deleted,if order not found then returns http
	 *         status message
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOrder(@Valid @PathVariable("id") long id) {
		boolean deleteOrder = service.removeOrder(id);
		ResponseEntity<String> response;
		if (deleteOrder) {
			response = new ResponseEntity<String>("Order is deleted", HttpStatus.GONE);
		} else {
			response = new ResponseEntity<String>("Order is  not deleted", HttpStatus.NOT_FOUND);
		}
		return response;
	}

}
