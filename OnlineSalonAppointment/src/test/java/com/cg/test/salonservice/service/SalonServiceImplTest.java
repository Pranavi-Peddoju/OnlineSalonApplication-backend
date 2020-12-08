package com.cg.test.salonservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals
;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.exception.DuplicateRecordException;
import com.cg.exception.InvalidInputException;
import com.cg.exception.NoSuchServiceException;
import com.cg.model.SalonService;
import com.cg.service.SalonServiceImpl;

@SpringBootTest
public class SalonServiceImplTest {
	
	@Autowired
	SalonServiceImpl serviceImpl;
	
	@Test
	public void getServiceByIdTest() {
		SalonService salonServiceModel = new SalonService(4388, "Manicure and Pedicure", 1600.50, 15, "1hr");
		assertEquals(serviceImpl.getService(4388), salonServiceModel);
	}

	@Test
	public void addServiceTest() {
		SalonService serviceToAdd = new SalonService(4108, "Hair Color", 5000.50, 40, "2hrs");
		SalonService added = serviceImpl.addService(serviceToAdd);
		assertEquals(added, serviceToAdd);
	}

	@Test
	public void updateServiceTest() {
		SalonService updateService = new SalonService(4917, "Body Massage", 4000, 22, "1hr 30mins");
		SalonService salonServiceUpdate = serviceImpl.updateService(updateService);
		assertEquals(updateService.toString(), salonServiceUpdate.toString());
	} 

	@Test
	public void removeServiceTest() {
		boolean remove = serviceImpl.removeService(4108);
		assertTrue(remove);
	}

	@Test
	public void getServiceByPriceTest() {
		SalonService salonServiceModel = new SalonService(4388, "Manicure and Pedicure", 1600.50, 15, "1hr");
		List<SalonService> gotByPrice = serviceImpl.getServiceByPrice(3500, 1500);
		assertEquals(true, gotByPrice.contains(salonServiceModel));
	}

	@Test
	public void getServieByDurationTest() {
		List<SalonService> actual = Arrays.asList(new SalonService(4273, "Manicure", 700.50, 5, "30mins"),
				new SalonService(4449, "Pedicure", 1000, 8, "30mins"));
		List<SalonService> expected = serviceImpl.getServiceByDuration("30mins");
		assertEquals(expected, actual);
	}

	@Test
	public void getServiceByNameTest() {
		SalonService salonServiceExpectedModel = new SalonService(4273, "Manicure", 700.50, 5, "30mins");
		List<SalonService> expected = serviceImpl.getServicebyName("Manicure");
		assertEquals(true, expected.contains(salonServiceExpectedModel));
	}

	@Test
	public void getAllServicesTest() {
		List<SalonService> expected = serviceImpl.getAllServices();
		assertEquals(expected.size(),12);

	}
	
	@Test
	public void duplicateRecord_TestOnAdd() throws DuplicateRecordException{
		SalonService salonServiceModel = new SalonService(4018, "Manicure and Pedicure", 1600.50, 15, "1hr");
		assertThrows(DuplicateRecordException.class, () -> serviceImpl.addService(salonServiceModel),
				"Service with this id already exists you cannot add again");
	}

	@Test
	public void noSuchService_TestOnId() throws NoSuchServiceException{
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.getService(4001),
				"Sorry!! No Such Service Found By This Id");
	}
	
	@Test
	public void noSuchService_TestOnRemove() throws NoSuchServiceException{
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.removeService(4001),
				"Sorry!! The service you are trying to delete does not exists.");
	}
	
	
	@Test
	public void invalidInput_TestOnId() throws InvalidInputException{
		assertThrows(InvalidInputException.class, () -> serviceImpl.getService(401),
				"The service id must be minimum 4 digits starting with 4");
	}

	@Test
	public void noSuchService_TestOnName() throws NoSuchServiceException{
		String serviceName = "Nail Art";
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.getServicebyName(serviceName),
				"Sorry!! No Such Service");
	}

	@Test
	public void noSuchService_TestOnPrice() throws NoSuchServiceException{
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.getServiceByPrice(15000, 20000),
				"Sorry! No Service in given Price Range");
	}

	@Test
	public void noSuchService_TestOnDuration() throws NoSuchServiceException{
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.getServiceByDuration("15hrs"),
				"Sorry! No service provided for this duration");
	}
	@Test
	public void noSuchService_TestOnGetUpdate() throws NoSuchServiceException {
		SalonService salonServiceModel = new SalonService(4333, "Manicure and Pedicure", 1600.50, 15, "1hr");
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.updateService(salonServiceModel),
				"No such service found");
	}


}
