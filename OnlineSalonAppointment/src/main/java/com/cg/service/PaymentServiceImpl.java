package com.cg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.exception.DuplicatePaymentException;
import com.cg.exception.NoSuchServiceException;
import com.cg.exception.PaymentNotFoundException;
import com.cg.model.Payment;
import com.cg.model.SalonService;
import com.cg.repository.IPaymentRepository;

@Service

public class PaymentServiceImpl implements IPaymentService {

	@Autowired
	private IPaymentRepository payRepo;


	@Override
	public Payment addPayment(Payment payment) throws DuplicatePaymentException {    //method - adding a payment by paymentId and returning payment
       if (!payRepo.existsById(payment.getPaymentId())) {                            //returning payment
			payment = payRepo.save(payment);
			return payment;
		} else {
			throw new DuplicatePaymentException("Payment with this id already exists you cannot add again");
		}
	}

	@Override
	public boolean removePayment(long id) {
		Payment payRemove = getPaymentDetails(id);
		payRepo.delete(payRemove);
		return true;
	}

	@Override
	public Payment updatePayment(long id, Payment payment) throws PaymentNotFoundException {
		
		if (payRepo.existsById(id)) {
			// payUpdate.setStatus(payment.getStatus());
			Payment payUpdate = payRepo.save(payment);
			return payUpdate;
		} else {
			throw new PaymentNotFoundException("paymentId not found to update");
		}
	}

	@Override
	public Payment getPaymentDetails(long id) throws PaymentNotFoundException {
	
		if (payRepo.existsById(id)) {
			return payRepo.findById(id).get();
		} else {
			throw new PaymentNotFoundException("PaymentId Details Not Found Exception");
		}
	}

	@Override
	public List<Payment> getAllPaymentDetails() throws PaymentNotFoundException {
		List<Payment> getAll = payRepo.findAll();
		if (getAll != null) {
			return getAll;
		} else {
			throw new PaymentNotFoundException("Payment is not found");
		}
	}

	//@Override
	//public List<Payment> getPaymentByName(String paymentName) {
	//	List<Payment> paymentByName = payRepo.findByPaymentNameIgnoreCase(paymentName);
	//	if (!paymentByName.isEmpty()) {
		//	return paymentByName;
		//} else {
			//throw new PaymentNotFoundException("Sorry!! The payment you are trying to find does not exists");
		//}
	//}
}
