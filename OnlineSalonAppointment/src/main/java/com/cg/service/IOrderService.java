package com.cg.service;

import java.util.List;

import com.cg.model.Order;

public interface IOrderService {
	public Order addOrder(Order order);
	public boolean removeOrder(long id);
	public Order  updateOrder(Order order);
	public List<Order> getAllOrders();
	public Order getOrderDetails(long id); 

}
