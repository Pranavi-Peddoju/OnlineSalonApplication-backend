package com.cg.test.payment.servicemockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.exception.DuplicatePaymentException;
import com.cg.exception.DuplicateRecordException;
import com.cg.exception.InvalidInputException;
import com.cg.exception.NoSuchServiceException;
import com.cg.exception.PaymentNotFoundException;
import com.cg.model.Card;
import com.cg.model.Payment;
import com.cg.repository.IPaymentRepository;
import com.cg.service.PaymentServiceImpl;

@SpringBootTest
class PaymentImplTestMock {

	@Autowired
	private PaymentServiceImpl service;

	@MockBean
	private IPaymentRepository payRepoMock;

	@Test
	public void addPaymentShouldAddPaymentToDatabaseTest() throws DuplicatePaymentException {
		Card card = new  Card(7456,"priya","789654",LocalDate.of(2022,7,23),898,"hdfc");
		Payment payment = new Payment(665, "Credit", "success",card);
		when(payRepoMock.existsById(324L)).thenReturn(false);
		when(payRepoMock.save(payment)).thenReturn(payment);
		Payment add = service.addPayment(payment);
		assertEquals(payment, add);
	}
	
	@Test
	public void removePaymentTest() {
		Card card4 = new Card(7685, "Pranavi", "789673", LocalDate.of(2024, 6, 22), 566, "union");
		Payment payment = new Payment(678 ,"Credit", "success", card4);
		when(payRepoMock.existsById(234L)).thenReturn(true);
	    when(payRepoMock.findById(234L)).thenReturn(Optional.of(payment));
		doNothing().when(payRepoMock).deleteById(234L);
		boolean remove = service.removePayment(234L);
		assertTrue(remove);
	}


	@Test
	public void updatePaymentByIdTest() {
		Card card3 = new Card(7654, "hima", "789671", LocalDate.of(2024, 6, 22), 566, "union");
		Payment updatePayment = new Payment(675, "Debit", "failure", card3);
		 when(payRepoMock.existsById(675L)).thenReturn(true);
		long paymentId = updatePayment.getPaymentId();
		when(payRepoMock.save(updatePayment)).thenReturn(updatePayment);
		Payment payment= service.updatePayment(paymentId, updatePayment);
		assertEquals(updatePayment, payment);
	}
	
	@Test
	public void getPaymentDetailsBasedOnIdTest() {
		Card card4 = new Card(7896, "Pranavi", "789673", LocalDate.of(2024, 6, 22), 566, "union");
		Payment payment = new Payment(673 ,"Credit", "success", card4);
	    when(payRepoMock.existsById(234L)).thenReturn(true);
		when(payRepoMock.findById(234L)).thenReturn(Optional.of(payment));
	    Payment payment1 = service.getPaymentDetails(234L);
	    assertEquals(payment1,payment);
	   
	}
	
	@Test
	public void getAllPaymentDetailsShouldGiveAllDetailsTest() {
		Card card5 = new Card(7564, "rani", "789674", LocalDate.of(2024, 6, 22), 566, "union");
		Payment payment1 = new Payment(672 ,"Credit", "success", card5);
		Payment payment2 = new Payment(236,"Debit", "failure", card5);
		List<Payment> payments= new ArrayList<Payment>();
		payments.add(payment2);
		payments.add(payment1);
		when(payRepoMock.findAll()).thenReturn(payments);
		assertEquals(service.getAllPaymentDetails(),payments);
	}
	
	@Test
	public void duplicateRecord_TestOnAdd() throws DuplicateRecordException {
		Card card = new  Card(7456,"priya","789654",LocalDate.of(2022,7,23),898,"hdfc");
		Payment p = new Payment(665, "Credit", "success",card);
		when(payRepoMock.existsById(665L)).thenReturn(true);
		assertThrows(DuplicatePaymentException.class, () -> service.addPayment(p),
				"Payment with this id already exists you cannot add again");
	}

	@Test
	public void noSuchPayment_TestOnId() throws PaymentNotFoundException{
		assertThrows(PaymentNotFoundException.class, () -> service.getPaymentDetails(665),
				"Sorry!! No Such Payment Found By This Id");
	}

	@Test
	public void noSuchPayment_TestOnRemove() throws PaymentNotFoundException{
		assertThrows(PaymentNotFoundException.class, () -> service.removePayment(665),
				"PaymentId Details Not Found Exception");
	}

}
