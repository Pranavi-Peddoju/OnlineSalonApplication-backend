package com.cg.service;

import java.util.List;

import com.cg.model.Customer;

public interface ICustomerService {
	public Customer addCustomer(Customer customer);
	public boolean removeCustomer(long custId);
	public Customer updateCustomer(Customer customer);
	public Customer getCustomerById(long custId);
	public List<Customer> getAllCustomers(); 
}
