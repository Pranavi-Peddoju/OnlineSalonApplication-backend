package com.cg.controller;

import java.util.List;

import javax.mail.MessagingException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cg.model.Customer;
import com.cg.model.UserAccountConfirmation;
import com.cg.service.AccountConfirmationService;
import com.cg.service.CustomerServiceImpl;
import com.cg.service.EmailSenderService;

/**
 * The CustomerController class Has all The CRUD Operations
 *
 * @author :Peddoju Teja Pranavi
 * @version :1.0
 * @since :2020-12-01
 **/
@RestController
@RequestMapping("/auth/user")
public class CustomerController {

	private CustomerServiceImpl service;
	private GenerateID auto;
	private EmailSenderService emailSenderService;
	private AccountConfirmationService accountService;

	@Autowired
	public CustomerController(CustomerServiceImpl service, GenerateID auto, EmailSenderService emailSenderService,
			AccountConfirmationService accountService) {
		super();
		this.service = service;
		this.auto = auto;
		this.emailSenderService = emailSenderService;
		this.accountService = accountService;
	}

	/**
	 * addCustomer
	 * <p>
	 * This method is used to set a 6 digit code for the user id and sends to
	 * customer service add method
	 * </p>
	 * 
	 * @param : Customer
	 * @return Customer : This returns the customer details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/register")
	public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) throws MessagingException {
		customer.setUserId(auto.generateID(customer));
		customer.getAddress().setAddressId(auto.generateID(customer.getAddress()));
		service.addCustomer(customer);
		emailSenderService.addCustomerSuccess(customer);
		return new ResponseEntity<>(customer, HttpStatus.OK);

	}

	/**
	 * getAllCustomer
	 * <p>
	 * This method is used to get all the user - customer details
	 * </p>
	 * 
	 * @return List<Customer> : This return all the customer details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/all")
	public List<Customer> getAllCustomer() {
		return service.getAllCustomers();
	}

	/**
	 * getCustomer
	 * <p>
	 * This method is used to get the user - customer details based on user id
	 * </p>
	 * 
	 * @param : id
	 * @return Customer : This return the customer details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/{id}")
	public Customer getCustomer(@Valid @PathVariable("id") long userId) {
		return service.getCustomerById(userId);
	}

	/**
	 * getCustomerByUserName
	 * <p>
	 * This method is used to get the user - customer details based on unique user
	 * name
	 * </p>
	 * 
	 * @param : userName
	 * @return Customer : This return the customer details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping
	public Customer getCustomerByUserName(@Valid @RequestParam("name") String userName) {
		return service.getByUserName(userName);
	}

	/**
	 * updateCustomer
	 * <p>
	 * This method is used to update the user-customer details and sends the details
	 * to service layer for updation
	 * </p>
	 * 
	 * @param : Customer
	 * @return Customer : This returns the customer details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping
	public Customer updateCustomer(@Valid @RequestBody Customer customer) {
		return service.updateCustomer(customer);
	}

	/**
	 * deleteCustomer
	 * <p>
	 * This method is used to delete the user details based on user id
	 * </p>
	 * 
	 * @param : id
	 * @return ResponseEntity<String> : This return success message upon successful
	 *         deletion.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") long userId) {
		boolean check = service.removeCustomer(userId);
		if (check) {
			return new ResponseEntity<>("Deleted Succesfully", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Sorry come back again", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * confirmAccount
	 * <p>
	 * This method is confirms the activation of user account by setting status to
	 * true
	 * </p>
	 * 
	 * @param : otp
	 * @return ResponseEntity<String> : This return success message upon successful
	 *         activation.
	 */
	@PostMapping("/confirm")
	public ResponseEntity<String> confirmAccount(@RequestParam("otp") long otp) {
		UserAccountConfirmation accountStatus = accountService.confirmUserAccount(otp);
		if (accountStatus != null) {
			return new ResponseEntity<>("Your account has been Succesfully Activated", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Sorry could not activate.", HttpStatus.BAD_REQUEST);
		}
	}

}