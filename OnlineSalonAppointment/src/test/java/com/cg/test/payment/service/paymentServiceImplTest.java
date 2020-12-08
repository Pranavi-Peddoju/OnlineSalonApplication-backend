package com.cg.test.payment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.exception.DuplicatePaymentException;
import com.cg.exception.NoSuchServiceException;
import com.cg.exception.PaymentNotFoundException;
import com.cg.model.Card;
import com.cg.model.Payment;
import com.cg.service.PaymentServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class paymentServiceImplTest {

	@Autowired
	PaymentServiceImpl service;

	@Test
	@Order(1)
	public void addPaymentTest() throws DuplicatePaymentException  {
		Card card = new  Card(7456,"swetha","789654",LocalDate.of(2022,7,23),898,"hdfc");
		Payment payment = new Payment(665434, "Credit", "success",card);
	    Payment paymentTest = service.addPayment(payment);
		assertEquals(payment, paymentTest);
	}

	
	@Test
	@Order(2)
	public void updatePaymentByIdTest() throws PaymentNotFoundException {
		Card card = new  Card(7456,"swetha","789654",LocalDate.of(2022,7,23),898,"hdfc");
		Payment p = new Payment(665434, "Credit", "success",card);
		Payment update = service.updatePayment(665434, p);
		assertEquals(p.toString(), update.toString());
	}

	@Test
	@Order(3)
	public void getPaymentDetailsTest() {
		Card card = new  Card(7456,"swetha","789654",LocalDate.of(2022,7,23),898,"hdfc");
		Payment p = new Payment(665434, "Credit", "success",card);
        assertEquals(p, service.getPaymentDetails(665434));

	}

	@Test
	@Order(4)
	public void getAllPaymentDetailsList() {
		List<Payment> list = service.getAllPaymentDetails();
		assertEquals(11, list.size());    

	}
	
	
	@Test
	@Order(5)
	public void duplicate_TestOnAdd() throws DuplicatePaymentException{
		Card card = new  Card(7456,"priya","789654",LocalDate.of(2022,7,23),898,"hdfc");
		Payment p = new Payment(665434, "Credit", "success",card);
		assertThrows(DuplicatePaymentException.class, () -> service.addPayment(p),
				"Payment with this id already exists you cannot add again");
	}
	
	@Test
	@Order(6)
	public void removePaymentTest() {
	   boolean remove= service.removePayment(665434);
	   assertTrue(remove);
	}
	
	@Test
	@Order(7)
	public void noSuchPayment_TestOnId() throws PaymentNotFoundException{
		assertThrows(PaymentNotFoundException.class, () -> service.getPaymentDetails(67854),
				"Sorry!! No Such Payment Found By This Id");
	}
	
	@Test
	@Order(8)
	public void noSuchPayment_TestOnRemove() throws NoSuchServiceException{
		assertThrows(PaymentNotFoundException.class, () -> service.removePayment(678954),
				"Sorry!! The service you are trying to delete does not exists.");
	}
	
	
}
