package com.cg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.exception.DuplicateOrderException;
import com.cg.exception.OrderNotFoundException;
import com.cg.model.Order;
import com.cg.repository.IOrderRepository;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderRepository repo;

	/**
	 * This addOrder method is used add orders to(by) a customer
	 * 
	 * @return returns the added order, throws duplicate exception if any order id
	 *         is repeated (make sure no two orders have same id)
	 */
	@Override
	public Order addOrder(Order order) {
		if (!repo.existsById(order.getOrderId())) {
			Order ordAdd = repo.save(order);
			return ordAdd;
		} else {
			throw new DuplicateOrderException("Dupicate order found");
		}
	}

	/**
	 * This updateOrder method updates the order by reading id
	 * 
	 * @return if order found and updated returns updated order if order not found
	 *         then throws exception
	 */
	@Override
	public Order updateOrder(Order order) {

		if (repo.existsById(order.getOrderId())) {
			// order.setAmount(order.getAmount());
			return repo.save(order);
		} else {
			throw new OrderNotFoundException("Order is not found with this id");
		}

	}

	/**
	 * This getOrderDetails method is used to find details of order by giving order
	 * id
	 * 
	 * @return returns the order by reading order id, if id not found throws an
	 *         exception
	 */
	@Override
	public Order getOrderDetails(long id) {

		if (repo.existsById(id)) {
			Order getDetails = repo.findById(id).get();
			return getDetails;
		} else {
			throw new OrderNotFoundException("No order found with this id");
		}
	}

	/**
	 * This getAllOrders method gives all the orders made by customer
	 * 
	 * @return if number of orders greater zero then returns orders otherwise throws
	 *         exception
	 */
	@Override
	public List<Order> getAllOrders() {
		List<Order> getAll = repo.findAll();
		if (!getAll.isEmpty()) {
			return getAll;
		} else {
			throw new OrderNotFoundException("Order is not found with this id");
		}
	}

	/**
	 * This method helps in removing the orders placed
	 * 
	 * @return if order found with provided id removes it and returns a boolean
	 *         value if id not found throws an exception
	 */
	@Override
	public boolean removeOrder(long id) {

		if (repo.existsById(id)) {
			repo.deleteById(id);
			return true;
		} else {
			throw new OrderNotFoundException("Order Id not found to remove");
		}

	}

}
