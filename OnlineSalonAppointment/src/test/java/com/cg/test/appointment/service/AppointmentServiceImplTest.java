package com.cg.test.appointment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.exception.AppointmentIdNotFoundException;
import com.cg.exception.DuplicateOrderException;
import com.cg.exception.DuplicateRecordException;
import com.cg.exception.InvalidAppointmentException;
import com.cg.exception.OrderNotFoundException;
import com.cg.model.Address;
import com.cg.model.Appointment;
import com.cg.model.Card;
import com.cg.model.Customer;
import com.cg.model.Payment;
import com.cg.service.AppointmentServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class AppointmentServiceImplTest {

	@Autowired
	AppointmentServiceImpl appService;

	@Test
	@Order(1)
	public void testAddAppointment() {
		Address address = new Address(2837, "12/B-1", "hanuman nagar", "Hyderabad", "tel", "654321");
		Customer customer = new Customer("Sana", "sana123@gmail.com", "9876543210",
				LocalDate.of(1997, 06, 13), address);
		customer.setUserId(101761);
		customer.setUserName("SanaFatima");
		customer.setPassword("sana");

		Card card1 = new Card(701, "SBI", "555657687980", LocalDate.of(2025, 07, 9), 765, "sbi");
		Payment p1 = new Payment(17, "Credit", "status", card1);
		Appointment app = new Appointment(53743, "Hyd", "home", "Hair Treatment", LocalDate.of(2020, 12, 23),
				LocalTime.of(22, 30, 30), customer, p1);

		Appointment added = appService.addAppointment(app);
		assertEquals(added.getAppointmentId(), 53743);
	}

	@Test
	@Order(2)
	public void testUpdateAppointment() {
		Address address = new Address(2837, "12/B-1", "hanuman nagar", "Hyderabad", "tel", "654321");
		Customer customer = new Customer("Sana", "sana123@gmail.com", "9876543210",
				LocalDate.of(1997, 06, 13), address);
		customer.setUserId(101761);
		customer.setUserName("SanaFatima");
		customer.setPassword("sana");
		Card card1 = new Card(701, "SBI", "555657687980", LocalDate.of(2025, 07, 9), 765, "sbi");
		Payment p1 = new Payment(17, "Credit", "status", card1);
		Appointment app = new Appointment(53743, "Hyd", "home", "Hair Treatment", LocalDate.of(2020, 12, 23),
				LocalTime.of(22, 30, 30), customer, p1);

		Appointment test = appService.updateAppointment(53743, app);
		assertEquals(test.getVisitType(), "home");

	}

	@Test
	@Order(3)
	public void testGetAppointment() {
		Address address = new Address(2837, "12/B-1", "hanuman nagar", "Hyderabad", "tel", "654321");
		Customer customer = new Customer("Sana", "sana123@gmail.com", "9876543210",
				LocalDate.of(1997, 06, 13), address);
		customer.setUserId(101761);
		customer.setUserName("SanaFatima");
		customer.setPassword("sana");

		Card card1 = new Card(701, "SBI", "555657687980", LocalDate.of(2025, 07, 9), 765, "sbi");
		Payment p1 = new Payment(17, "Credit", "status", card1);
		Appointment app = new Appointment(53743, "Hyd", "home", "Hair Treatment", LocalDate.of(2020, 12, 23),
				LocalTime.of(22, 30, 30), customer, p1);
		Appointment added = appService.getAppointment(app.getAppointmentId());
		assertEquals(added.getAppointmentId(), 53743);
	}

	@Test
	@Order(4)
	public void testAddAppointmentException() {
		Address address = new Address(2837, "12/B-1", "hanuman nagar", "Hyderabad", "tel", "654321");
		Customer customer = new Customer("Sana", "sana123@gmail.com", "9876543210",
				LocalDate.of(1997, 06, 13), address);
		customer.setUserId(101761);
		customer.setUserName("SanaFatima");
		customer.setPassword("sana");

		Card card1 = new Card(701, "SBI", "555657687980", LocalDate.of(2025, 07, 9), 765, "sbi");
		Payment p1 = new Payment(17, "Credit", "status", card1);
		Appointment app = new Appointment(53743, "Hyd", "home", "Hair Treatment", LocalDate.of(2020, 12, 23),
				LocalTime.of(22, 30, 30), customer, p1);
		assertThrows(DuplicateRecordException.class, () -> appService.addAppointment(app),
				"Appointment cannot be added");

	}
	
	@Test
	@Order(5)
	public void testGetOpen() {
		List<Appointment> list = appService.getOpenAppointments();
		assertEquals(list.size(), 1);
	}

	@Test
	@Order(6)
	public void testRemoveAppointment() {
		boolean appointment = appService.removeAppointment(53743);
		assertTrue(appointment);
	}

	@Test
	@Order(7)
	public void testGetAll() {
		List<Appointment> list = appService.getAllAppointments();
		assertEquals(list.size(), 2);
	}
	
	
	@Test
	public void testRemoveAppointmentException() {

		assertThrows(AppointmentIdNotFoundException.class, () -> appService.removeAppointment(98763),
				"Appointment cannot be added");
	}

	@Test
	public void testUpdateException() {
		Address address = new Address(2837, "12/B-1", "hanuman nagar", "Hyderabad", "tel", "654321");
		Customer customer = new Customer("Sana", "sana123@gmail.com", "9876543210",
				LocalDate.of(1997, 06, 13), address);
		customer.setUserId(101761);
		customer.setUserName("SanaFatima");
		customer.setPassword("sana");
		Card card1 = new Card(701, "SBI", "555657687980", LocalDate.of(2025, 07, 9), 765, "sbi");
		Payment p1 = new Payment(17, "Credit", "status", card1);
		Appointment app = new Appointment(53743, "Hyd", "home", "Hair Treatment", LocalDate.of(2020, 12, 23),
				LocalTime.of(22, 30, 30), customer, p1);
		assertThrows(InvalidAppointmentException.class, () -> appService.updateAppointment(33333444, app),
				"Appointment cannot be updated");
	}

	@Test
	public void testGetAppointmentException() {
		assertThrows(AppointmentIdNotFoundException.class, () -> appService.getAppointment(36678899),
				"Sorry!! No Order Found By This Id");
	}
}
