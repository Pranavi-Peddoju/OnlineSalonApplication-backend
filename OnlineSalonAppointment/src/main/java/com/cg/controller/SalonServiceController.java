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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cg.model.SalonService;
import com.cg.service.SalonServiceImpl;

/**
 * The SalonServiceController class Has all the CRUD operations and specific get
 * Methods By Name, Price, Duration
 * 
 * @author :Peddoju Teja Pranavi
 * @version :1.0
 * @since :2020-12-01
 **/

@RestController
@RequestMapping(path = "/salonservice")
public class SalonServiceController {

	private SalonServiceImpl salonServiceImpl;
	private GenerateID auto;

	// Tells the application context to inject an instance of SalonServiceImpl and
	// GenerateId here
	@Autowired
	public SalonServiceController(SalonServiceImpl salonServiceImpl, GenerateID auto) {
		super();
		this.salonServiceImpl = salonServiceImpl;
		this.auto = auto;
	}

	/**
	 * addService
	 * <p>
	 * This method is used to set a 4 digit code for the salon service id and sends
	 * to salon service add method
	 * </p>
	 * 
	 * @param : SalonService
	 * @return SalonService : This returns the service details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping
	public SalonService addService(@Valid @RequestBody SalonService salonService) {
		salonService.setServiceId(auto.generateID(salonService));
		return salonServiceImpl.addService(salonService);
	}

	/**
	 * getServiceById
	 * <p>
	 * This method is used to get the salon service details based on id.
	 * </p>
	 * 
	 * @param : serviceId
	 * @return SalonService : This returns the service details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/{id}")
	public SalonService getServiceById(@Valid @PathVariable("id") long id) {
		return salonServiceImpl.getService(id);
	}

	/**
	 * getServiceByName
	 * <p>
	 * This method is used to get the salon service details based on service name
	 * </p>
	 * 
	 * 
	 * @param : serviceName
	 * @return List<SalonService> : This returns List of service details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/name/{name}")
	public List<SalonService> getServiceByName(@Valid @PathVariable("name") String serviceName) {
		return salonServiceImpl.getServicebyName(serviceName);
	}

	/**
	 * getServiceByDuration
	 * <p>
	 * This method is used to get the salon service details based on service
	 * duration
	 * </p>
	 * 
	 * @param : duration
	 * @return List<SalonService> : This returns List of service details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/duration/{duration}")
	public List<SalonService> getServiceByDuration(@Valid @PathVariable("duration") String duration) {
		return salonServiceImpl.getServiceByDuration(duration);
	}

	/**
	 * getServiceByPrice
	 * <p>
	 * This method is used to get the salon service details based on service price
	 * range
	 * </p>
	 * 
	 * @param price1
	 * @param price2
	 * @return List<SalonService> : This returns List of service details.
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/pricerange")
	public List<SalonService> getServiceByPrice(@Valid @RequestParam("price1") double price1,
			@RequestParam("price2") double price2) {
		return salonServiceImpl.getServiceByPrice(price1, price2);
	}

	/**
	 * updateService
	 * <p>
	 * This method is used to update the salon service details and sends the details
	 * to service layer update method
	 * </p>
	 * 
	 * @param : SalonService
	 * @return SalonService : This returns the updated service details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping
	public SalonService updateService(@Valid @RequestBody SalonService salonService) {
		return salonServiceImpl.updateService(salonService);
	}

	/**
	 * deleteById
	 * <p>
	 * This method is used to delete the salon service details based on service id
	 * </p>
	 * 
	 * @param : serviceId
	 * @return ResponseEntity<String> : This return success message upon successful
	 *         deletion.
	 **/
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@Valid @PathVariable("id") long id) {
		salonServiceImpl.removeService(id);
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}

	/**
	 * getAllServices
	 * <p>
	 * This method is used to get all salon service details
	 * </p>
	 * 
	 * @return List<SalonService> : This returns List of service details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping
	public List<SalonService> getAllServices() {
		return salonServiceImpl.getAllServices();
	}

}
