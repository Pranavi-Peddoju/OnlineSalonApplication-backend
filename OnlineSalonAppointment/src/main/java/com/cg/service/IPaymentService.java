package com.cg.service;

import java.util.List;

import com.cg.exception.DuplicatePaymentException;
import com.cg.model.Payment;

public interface IPaymentService {
	public Payment addPayment(Payment payment) throws DuplicatePaymentException, DuplicatePaymentException;

	public boolean removePayment(long id);

	public Payment updatePayment(long id, Payment payment);

	public Payment getPaymentDetails(long id);

	public List<Payment> getAllPaymentDetails();
}
