package com.cg.test.salonservice.servicemockito;

import static org.junit.jupiter.api.Assertions.assertEquals

;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.exception.DuplicateRecordException;
import com.cg.exception.InvalidInputException;
import com.cg.exception.NoSuchServiceException;
import com.cg.model.SalonService;
import com.cg.repository.ISalonRepository;
import com.cg.service.SalonServiceImpl;

@SpringBootTest
public class SalonServiceImplTestMockito {

	@Autowired
	SalonServiceImpl serviceImpl;

	@MockBean
	ISalonRepository salonRepoMock;

	@Test
	public void getServiceByIdTest() {
		SalonService salonServiceModel = new SalonService(4388, "Manicure and Pedicure", 1600.50, 15, "1hr");
		when(salonRepoMock.existsById(4388L)).thenReturn(true);
		when(salonRepoMock.findById(4388L)).thenReturn(Optional.of(salonServiceModel));
		assertEquals(serviceImpl.getService(4388), salonServiceModel);
	}

	@Test
	public void addServiceTest() {
		SalonService serviceToAdd = new SalonService(4108, "Hair Color", 5000.50, 40, "2hrs");
		when(salonRepoMock.save(serviceToAdd)).thenReturn(serviceToAdd);
		SalonService added = serviceImpl.addService(serviceToAdd);
		assertEquals(added, serviceToAdd);
	}

	@Test
	public void updateServiceTest() {
		SalonService updateService = new SalonService(4917, "Body Massage", 4000, 22, "1hr 30mins");
		when(salonRepoMock.existsById(4917L)).thenReturn(true);
		when(salonRepoMock.findById(4917L)).thenReturn(Optional.of(updateService));
		when(salonRepoMock.save(updateService)).thenReturn(updateService);
		SalonService salonServiceUpdate = serviceImpl.updateService(updateService);
		assertEquals(updateService.toString(), salonServiceUpdate.toString());
	}

	@Test
	public void removeServiceTest() {
		when(salonRepoMock.existsById(4917L)).thenReturn(true);
		doNothing().when(salonRepoMock).deleteById(125432L);
		boolean remove = serviceImpl.removeService(4917);
		assertTrue(remove);
	}

	@Test
	public void getServiceByPriceTest() {
		SalonService salonServiceModel = new SalonService(4388, "Manicure and Pedicure", 1600.50, 15, "1hr");
		when(salonRepoMock.findByPriceBetweenOrderByPrice(1500, 3500)).thenReturn(Arrays.asList(salonServiceModel));
		List<SalonService> gotByPrice = serviceImpl.getServiceByPrice(1500, 3500);
		assertEquals(true, gotByPrice.contains(salonServiceModel));
	}

	@Test
	public void getServieByDurationTest() {
		List<SalonService> actual = Arrays.asList(new SalonService(4273, "Manicure", 700.50, 5, "30mins"),
				new SalonService(4449, "Pedicure", 1000, 8, "30mins"));
		when(salonRepoMock.findByDurationOrderByServiceId("30mins")).thenReturn(actual);
		List<SalonService> expected = serviceImpl.getServiceByDuration("30mins");
		assertEquals(expected, actual);
	}

	@Test
	public void getServiceByNameTest() {
		SalonService salonServiceExpectedModel = new SalonService(4273, "Manicure", 700.50, 5, "30mins");
		when(salonRepoMock.findByServiceNameIgnoreCase("Manicure"))
				.thenReturn(Arrays.asList(salonServiceExpectedModel));
		List<SalonService> expected = serviceImpl.getServicebyName("Manicure");
		assertTrue(expected.contains(salonServiceExpectedModel));
	}

	@Test
	public void duplicateRecord_TestOnAdd() throws DuplicateRecordException {
		SalonService salonServiceModel = new SalonService(4388, "Manicure and Pedicure", 1600.50, 15, "1hr");
		when(salonRepoMock.existsById(4388L)).thenReturn(true);
		assertThrows(DuplicateRecordException.class, () -> serviceImpl.addService(salonServiceModel),
				"Service with this id already exists you cannot add again");
	}

	@Test
	public void noSuchService_TestOnId() throws NoSuchServiceException {
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.getService(4001),
				"Sorry!! No Such Service Found By This Id");
	}

	@Test
	public void noSuchService_TestOnRemove() throws NoSuchServiceException {
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.removeService(4001),
				"Sorry!! The service you are trying to delete does not exists.");
	}

	@Test
	public void invalidInput_TestOnId() throws InvalidInputException {
		assertThrows(InvalidInputException.class, () -> serviceImpl.getService(0),
				"The service id must be minimum 4 digits starting with 4");
	}

	@Test
	public void noSuchService_TestOnName() throws NoSuchServiceException {
		String serviceName = "Nail Art";
		when(salonRepoMock.findByServiceNameIgnoreCase(serviceName))
				.thenReturn(Collections.emptyList());
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.getServicebyName(serviceName),
				"Sorry!! No Such Service");
	}

	@Test
	public void noSuchService_TestOnPrice() throws NoSuchServiceException {
		when(salonRepoMock.findByPriceBetweenOrderByPrice(15000, 20000))
				.thenReturn(Collections.emptyList());
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.getServiceByPrice(15000, 20000),
				"Sorry! No Service in given Price Range");
	}

	@Test
	public void noSuchService_TestOnDuration() throws NoSuchServiceException {
		when(salonRepoMock.findByDurationOrderByServiceId("15hrs"))
				.thenReturn(Collections.emptyList());
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.getServiceByDuration("15hrs"),
				"Sorry! No service provided for this duration");
	}
	
	@Test
	public void noSuchService_TestOnGetAll() throws NoSuchServiceException {
		when(salonRepoMock.findAll())
				.thenReturn(Collections.emptyList());
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.getAllServices(),
				"Sorry!! No services are provided right now.");
	}
	
	@Test
	public void noSuchService_TestOnGetUpdate() throws NoSuchServiceException {
		SalonService salonServiceModel = new SalonService(4333, "Manicure and Pedicure", 1600.50, 15, "1hr");
		when(salonRepoMock.existsById(4333L))
				.thenReturn(false);
		assertThrows(NoSuchServiceException.class, () -> serviceImpl.updateService(salonServiceModel),
				"No such service found");
	}
	
	@Test
	public void getAllServices() throws NoSuchServiceException {
		List<SalonService> arrays =  Arrays.asList(new SalonService[12]);
		when(salonRepoMock.findAll())
				.thenReturn(arrays);
		assertEquals(12, serviceImpl.getAllServices().size());
	}
	
	

}
