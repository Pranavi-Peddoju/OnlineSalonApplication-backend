package com.cg.test.appointment.servicemockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.exception.AppointmentIdNotFoundException;
import com.cg.exception.InvalidAppointmentException;
import com.cg.model.Address;
import com.cg.model.Appointment;
import com.cg.model.Card;
import com.cg.model.Customer;
import com.cg.model.Payment;
import com.cg.repository.IAppointmentRepository;
import com.cg.service.AppointmentServiceImpl;

@SpringBootTest
public class AppointmentServiceTestWithMock {

	@Autowired
	AppointmentServiceImpl appService;

	@MockBean
	private IAppointmentRepository repo;

	@Test
	void testAddAppointment() {
		Address address = new Address(2317, "123", "AO nagar", "Hyderabad", "Tel", "500028");
		Customer customer = new Customer("pranavi", "pranavipeddoju@gmail.com", "9878763210",
				LocalDate.of(1999, 12, 02), address);
		customer.setUserId(116498);
		customer.setUserName("PranaviPeddoju");
		customer.setPassword("pranavi");

		Card card1 = new Card(701, "SBI", "555657687980", LocalDate.of(2025, 07, 9), 765, "sbi");
		Payment p1 = new Payment(17, "Credit", "status", card1);
		Appointment app = new Appointment(53743, "Hyd", "home", "Hair Treatment", LocalDate.of(2020, 12, 23),
				LocalTime.of(22, 30, 30), customer, p1);
		when(repo.save(app)).thenReturn(app);
		Appointment test = appService.addAppointment(app);
		assertEquals(app, test);

	}

	@Test
	void testRemoveAppointment() {
		Address address = new Address(2317, "123", "AO nagar", "Hyderabad", "Tel", "500028");
		Customer customer = new Customer("pranavi", "pranavipeddoju@gmail.com", "9878763210",
				LocalDate.of(1999, 12, 02), address);
		customer.setUserId(116498);
		customer.setUserName("PranaviPeddoju");
		customer.setPassword("pranavi");

		Card card1 = new Card(701, "SBI", "555657687980", LocalDate.of(2025, 07, 9), 765, "sbi");
		Payment p1 = new Payment(17, "Credit", "status", card1);
		Appointment app = new Appointment(53743, "Hyd", "home", "Hair Treatment", LocalDate.of(2020, 12, 23),
				LocalTime.of(22, 30, 30), customer, p1);
		when(repo.existsById(53743L)).thenReturn(true);
		when(repo.findById(53743L)).thenReturn(Optional.of(app));
		boolean remove = appService.removeAppointment(53743L);
		assertTrue(remove);
	}

	@Test
	void testUpdateAppointment() {
		Address address = new Address(2317, "123", "AO nagar", "Hyderabad", "Tel", "500028");
		Customer customer = new Customer("pranavi", "pranavipeddoju@gmail.com", "9878763210",
				LocalDate.of(1999, 12, 02), address);
		customer.setUserId(116498);
		customer.setUserName("PranaviPeddoju");
		customer.setPassword("pranavi");

		Card card1 = new Card(701, "SBI", "555657687980", LocalDate.of(2025, 07, 9), 765, "sbi");
		Payment p1 = new Payment(17, "Credit", "status", card1);
		Appointment app = new Appointment(53743, "Hyd", "home", "Hair Treatment", LocalDate.of(2020, 12, 23),
				LocalTime.of(22, 30, 30), customer, p1);
		when(repo.existsById(53743L)).thenReturn(true);
		when(repo.findById(53743L)).thenReturn(Optional.of(app));
		when(repo.save(app)).thenReturn(app);
		Appointment test = appService.updateAppointment(53743, app);
		assertEquals(app, test);

	}

	@Test
	void testGetAppointment() {
		Address address = new Address(2317, "123", "AO nagar", "Hyderabad", "Tel", "500028");
		Customer customer = new Customer("pranavi", "pranavipeddoju@gmail.com", "9878763210",
				LocalDate.of(1999, 12, 02), address);
		customer.setUserId(116498);
		customer.setUserName("PranaviPeddoju");
		customer.setPassword("pranavi");

		Card card1 = new Card(701, "SBI", "555657687980", LocalDate.of(2025, 07, 9), 765, "sbi");
		Payment p1 = new Payment(17, "Credit", "status", card1);
		Appointment app = new Appointment(53743, "Hyd", "home", "Hair Treatment", LocalDate.of(2020, 12, 23),
				LocalTime.of(22, 30, 30), customer, p1);

		when(repo.existsById(53743L)).thenReturn(true);
		when(repo.findById(53743L)).thenReturn(Optional.of(app));
		when(repo.save(app)).thenReturn(app);
		Appointment test = appService.getAppointment(53743);
		assertEquals(app, test);

	}

	@Test
	void testGetAllAppointments() {
		List<Appointment> appointments = new ArrayList<>();
		Address address = new Address(2317, "123", "AO nagar", "Hyderabad", "Tel", "500028");
		Customer customer = new Customer("pranavi", "pranavipeddoju@gmail.com", "9878763210",
				LocalDate.of(1999, 12, 02), address);
		customer.setUserId(116498);
		customer.setUserName("PranaviPeddoju");
		customer.setPassword("pranavi");

		Card card1 = new Card(701, "SBI", "555657687980", LocalDate.of(2025, 07, 9), 765, "sbi");
		Payment p1 = new Payment(17, "Credit", "status", card1);

		Appointment app1 = new Appointment(53743, "Hyd", "Salon", "Facial", LocalDate.of(2020, 11, 23),
				LocalTime.of(22, 30, 30), customer, p1);
		Appointment app2 = new Appointment(53744, "Hyd", "Salon", "Hair Cut", LocalDate.of(2020, 11, 23),
				LocalTime.of(22, 30, 30), customer, p1);
		Appointment app3 = new Appointment(53745, "Hyd", "Salon", "bridalMakeUp", LocalDate.of(2020, 11, 23),
				LocalTime.of(22, 30, 30), customer, p1);
		appointments.add(app1);
		appointments.add(app2);
		appointments.add(app3);
		when(repo.findAll()).thenReturn(appointments);
		List<Appointment> listApp = appService.getAllAppointments();
		assertEquals(appointments.size(), listApp.size());
	}

	@Test
	void testRemoveAppointment_ThrowsException() throws AppointmentIdNotFoundException {

		assertThrows(AppointmentIdNotFoundException.class, () -> {
			appService.removeAppointment(8876767);
		});
	}

	@Test
	void testUpdateAppointmentThrowsException() {
		Address address = new Address(2317, "123", "AO nagar", "Hyderabad", "Tel", "500028");
		Customer customer = new Customer("pranavi", "pranavipeddoju@gmail.com", "9878763210",
				LocalDate.of(1999, 12, 02), address);
		customer.setUserId(116498);
		customer.setUserName("PranaviPeddoju");
		customer.setPassword("pranavi");

		Card card1 = new Card(701, "SBI", "555657687980", LocalDate.of(2025, 07, 9), 765, "sbi");
		Payment p1 = new Payment(17, "Credit", "status", card1);
		Appointment app = new Appointment(53743, "Hyd", "home", "Hair Treatment", LocalDate.of(2020, 12, 23),
				LocalTime.of(22, 30, 30), customer, p1);
		assertThrows(InvalidAppointmentException.class, () -> {
			appService.updateAppointment(5656, app);
		});
	}

	@Test
	void testGetOrderShouldThrowOrderNotFoundEXception() {

		assertThrows(AppointmentIdNotFoundException.class, () -> {
			appService.getAppointment(36666);
		});
	}

}
