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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cg.exception.DuplicatePaymentException;
import com.cg.exception.PaymentNotFoundException;
import com.cg.model.Payment;
import com.cg.service.PaymentServiceImpl;

@RestController
@RequestMapping("/Payment")
public class PaymentController {

	/*
	 * @Autowired private GenerateID auto;
	 */
	@Autowired
	private PaymentServiceImpl service;

	@Autowired
	private GenerateID auto;

	// http://localhost:7999/Payment/addPayment - Create method
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/addPayment")
	public ResponseEntity<String> addPayment(@Valid @RequestBody Payment payment) throws DuplicatePaymentException {
		payment.setPaymentId(auto.generateID(payment));
		payment.getCard().setCardId(auto.generateID(payment.getCard()));
		Payment addPay = service.addPayment(payment);
		ResponseEntity<String> response;
		if (addPay != null) {
			response = new ResponseEntity<String>("Payment is added", HttpStatus.CREATED);
		} else {
			response = new ResponseEntity<String>("Payment is not added", HttpStatus.NOT_ACCEPTABLE);
		}
		return response;
	}

	// http://localhost:7999/Payment/deletePayment/{id}
	@ResponseStatus(value = HttpStatus.OK)
	@DeleteMapping("/deletePayment/{id}")
	public ResponseEntity<String> removePayment(@Valid @PathVariable(value = "id") long id) {
		boolean delete = service.removePayment(id);
		ResponseEntity<String> response;
		if (delete) {
			response = new ResponseEntity<String>("Payment Deleted Successfully", HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>("error try again", HttpStatus.EXPECTATION_FAILED);
		}
		return response;
	}

	// http://localhost:7999/Payment/{id}
	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping("/{id}")
	public ResponseEntity<Payment> updatePayment(@Valid @PathVariable("id") long id, @RequestBody Payment payment)
			throws PaymentNotFoundException {
		if (service.updatePayment(id, payment) != null) {
			return new ResponseEntity<Payment>(payment, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity("Payment is not found", HttpStatus.NOT_FOUND);
	}

	// http://localhost:7999/Payment/getPaymentDetails/{id}
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/getPaymentDetails/{id}")
	public ResponseEntity<Payment> getPaymentDetails(@Valid @PathVariable("id") long id)
			throws PaymentNotFoundException {
		Payment getPayment = service.getPaymentDetails(id);
		if (getPayment == null) {
			return new ResponseEntity("payment not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Payment>(getPayment, HttpStatus.OK);

	}

	// http://localhost:7999/Payment/getAllPaymentDetails/{id}
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/getAllPaymentDetails")
	public List<Payment> getAllPaymentDetails() {
		List<Payment> payAll = service.getAllPaymentDetails();
		return payAll;
	}

}
