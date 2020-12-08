package com.cg.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cg.exception.DuplicateRecordException;
import com.cg.exception.InvalidInputException;
import com.cg.exception.NoSuchUserException;
import com.cg.model.Address;
import com.cg.model.Customer;
import com.cg.repository.ICustomerRepository;

@Service
public class CustomerServiceImpl implements ICustomerService {

	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	// Tells the application context to inject an instance of ICustomerRepository
	@Autowired
	private ICustomerRepository repository;

	/**
	 * addCustomer
	 * <p>
	 * This method adds the customer details into database if userName is unique.
	 * Else Integrity Violation Exception is caught and duplicate record exception
	 * is thrown
	 * </p>
	 * 
	 * @param : Customer
	 * @throws : DuplicateRecordException if a record with same id exists
	 * @return Customer - This returns the customer details
	 */
	@Override
	public Customer addCustomer(Customer customer) {
		try {
			log.info("Storing the customer record into database");
			repository.save(customer);
			log.info("stored successfully");
			return customer;
		} catch (DataIntegrityViolationException ex) {
			log.error("Exception Stack Trace" + ex.getMessage());
			throw new DuplicateRecordException(
					"User with this user Name  : " + customer.getUserName() + " Already Exists");
		}
	}

	/**
	 * updateCustomer
	 * <p>
	 * If userId is provided and is an existing record, Then updates the details
	 * based on ID. Else if userId doesn't exist or if it is not Provided but if
	 * user Name is provided and is an existing record then the details are updated
	 * based on User Name.
	 * </p>
	 * 
	 * @param : Customer
	 * @return Customer - This returns the customer details
	 */

	@Override
	public Customer updateCustomer(Customer customer) {
		Customer toUpdate;
		if (repository.existsById(customer.getUserId())) {
			log.info("Valid User Id is provided updating based on user Id  : " + customer.getUserId());
			toUpdate = repository.findById(customer.getUserId()).get();
		} else {
			log.info("User Id might not be provide or Invalid.");
			toUpdate = getByUserName(customer.getUserName());
			log.info("Updating the record to the same userID based on existing user Name");
			customer.setUserId(toUpdate.getUserId());
		}
		log.info("Updating the customer's address to the existing customers address Id");
		Address toUpdateAddress = repository.findByAddress(toUpdate.getAddress().getAddressId());
		log.info("Updating the Record of user with userId  : " + toUpdate.getUserId());
		customer.getAddress().setAddressId(toUpdateAddress.getAddressId());
		return repository.save(customer);
	}

	/**
	 * getCustomerById
	 * <p>
	 * This method will get the customer details based on customer's user Id
	 * </p>
	 * 
	 * @param : userId
	 * @throws : NoSuchUserException if user with that id not found
	 *           InvalidInputException if id is not valid
	 * @return Customer - This returns customer details
	 **/
	@Override
	public Customer getCustomerById(long userId) {
		if (userId == 0 || userId < 99999 || userId > 200000) {
			log.error("Invalid user Id provided. Throwing exception");
			throw new InvalidInputException("The user Id must be 6 digits starting with 1");
		} else if (repository.existsById(userId)) {
			log.info("Details exist. Getting the details By Id : " + userId);
			Customer get = repository.findById(userId).get();
			return get;
		} else {
			log.error("No user found throwing exception");
			throw new NoSuchUserException("No such user found");
		}
	}

	/**
	 * getAllCustomers
	 * <p>
	 * This method will get the all the customer details
	 * </p>
	 * 
	 * @return List<Customer> - This returns List of All customer details
	 **/
	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = repository.findAll();
		if (!customers.isEmpty()) {
			log.info("Returning all customer details");
			return customers;
		} else {
			log.error("List is empty. Throwing Exception");
			throw new NoSuchUserException("Currently No Customer details");
		}
	}

	/**
	 * removeCustomer
	 * <p>
	 * This method deletes the customer details based on customer's user Id
	 * </p>
	 * 
	 * @param : userId
	 * @return boolean - This returns true if successfully deleted
	 **/
	@Override
	public boolean removeCustomer(long userId) {
		Customer user = getCustomerById(userId);
		log.info("Deleted customer details with user Id : " + userId);
		repository.delete(user);
		return true;
	}

	/**
	 * getByUserName
	 * <p>
	 * This method will get the customer details based on customer's user Name
	 * </p>
	 * 
	 * @param : userName
	 * @throws : NoSuchUserException if user with that name not found
	 * @return Customer - This returns customer details
	 **/
	public Customer getByUserName(String userName) {
		Customer get = repository.findByUserName(userName);
		if (get != null) {
			log.info("Getting customer details based on admins user name : " + userName);
			return get;
		} else {
			log.error("No customer details found with user name : " + userName + ". Throwing Exception");
			throw new NoSuchUserException("No such user found");
		}
	}
}
