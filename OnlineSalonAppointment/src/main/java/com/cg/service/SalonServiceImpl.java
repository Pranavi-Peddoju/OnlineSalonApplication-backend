package com.cg.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.exception.DuplicateRecordException;
import com.cg.exception.InvalidInputException;
import com.cg.exception.NoSuchServiceException;
import com.cg.model.SalonService;
import com.cg.repository.ISalonRepository;

@Service
public class SalonServiceImpl implements ISalonService {

	private static final Logger log = LoggerFactory.getLogger(SalonServiceImpl.class);

	// Tells the application context to inject an instance of ISalonRepository
	@Autowired
	private ISalonRepository salonRepository;

	/**
	 * This method adds the salon service details into database
	 * 
	 * @param : SalonService
	 * @throws : DuplicateRecordException if a record with same id exists
	 * @return SalonService - This returns the salon service details
	 **/
	@Override
	public SalonService addService(SalonService salonService) {
		long serviceId = salonService.getServiceId();
		if (salonRepository.existsById(serviceId)) {
			log.error("Record with this id already exists. Throwing Exception");
			throw new DuplicateRecordException("Service with this id already exists you cannot add again");
		} else {
			log.info("Storing the service details into database");
			SalonService addService = salonRepository.save(salonService);
			log.info("Stored successfully");
			return addService;
		}
	}

	/**
	 * This method updates the salon service details into database
	 * 
	 * @param : SalonService
	 * @throws : NoSuchServiceException if service with Id not found
	 * @return SalonService - This returns the salon service details
	 **/
	@Override
	public SalonService updateService(SalonService salonService) {
		SalonService toUpdate = getService(salonService.getServiceId());
		log.info("Updating the service details of service Id  : " + salonService.getServiceId());
		return salonRepository.save(toUpdate);
	}

	/**
	 * This method deletes the salon service details based on service Id
	 * 
	 * @param : serviceId
	 * @throws : NoSuchServiceException if service with Id not found
	 * @return boolean - This returns true if successfully deleted
	 **/
	@Override
	public boolean removeService(long id) {
		if (salonRepository.existsById(id)) {
			log.info("Deleted service details with service Id : " + id);
			salonRepository.deleteById(id);
			return true;
		} else {
			throw new NoSuchServiceException("Sorry!! The service you are trying to delete does not exists.");
		}
	}

	/**
	 * This method will get the salon service details based on service Id
	 * 
	 * @param : serviceId
	 * @throws : NoSuchServiceException if service with Id not found
	 * @return SalonService - This returns salon service details
	 **/
	@Override
	public SalonService getService(long id) {
		if (id <= 3999 || id >= 5000) {
			log.error("Invalid service Id provided. Throwing exception");
			throw new InvalidInputException("The service id must be minimum 4 digits starting with 4");
		} else if (salonRepository.existsById(id)) {
			log.info("Details exist. Getting the details By service Id : " + id);
			SalonService findService = salonRepository.findById(id).get();
			return findService;
		} else {
			log.error("No service found throwing exception");
			throw new NoSuchServiceException("Sorry no such service with service id :" + id + " found");
		}
	}

	/**
	 * This method will get the all the service details
	 * 
	 * @throws : NoSuchServiceException if services are not found
	 * @return List<SalonService> - This returns List of All service details
	 **/
	@Override
	public List<SalonService> getAllServices() {
		List<SalonService> allServices = salonRepository.findAll();
		if (!allServices.isEmpty()) {
			log.info("Returning all service details");
			return allServices;
		} else {
			log.error("List is empty. Throwing Exception");
			throw new NoSuchServiceException("Sorry!! No services are provided right now.");
		}
	}

	/**
	 * This method will all the Salon Service details based on price range
	 * 
	 * @param : serivce price range min, max
	 * @throws : NoSuchServiceException if service in that range not found
	 * @return List<SalonService> - This returns service details in the price range
	 **/
	@Override
	public List<SalonService> getServiceByPrice(double price1, double price2) {
		double min = price1 < price2 ? price1 : price2;
		double max = price1 > price2 ? price1 : price2;
		List<SalonService> serviceByPrice = salonRepository.findByPriceBetweenOrderByPrice(min, max);
		if (!serviceByPrice.isEmpty()) {
			log.info("Getting the List of service details in price range : (" + min + ", " + max + ")");
			return serviceByPrice;
		} else {
			log.error("No service details found in the price range : (" + min + ", " + max + "). Throwing Exception");
			throw new NoSuchServiceException("Sorry!! No service is provided in this price range");
		}
	}

	/**
	 * This method will all the Salon Service details based on service Name
	 * 
	 * @param : serviceName
	 * @throws : NoSuchServiceException if service with Name not found
	 * @return List<SalonService> - This returns service details
	 **/
	@Override
	public List<SalonService> getServicebyName(String serviceName) {
		List<SalonService> serviceByName = salonRepository.findByServiceNameIgnoreCase(serviceName);
		if (!serviceByName.isEmpty()) {
			log.info("Getting the List of service details based on service name : " + serviceName);
			return serviceByName;
		} else {
			log.error("No service details found with service name : " + serviceName + ". Throwing Exception");
			throw new NoSuchServiceException("Sorry!! The service you are trying to find does not exists");
		}
	}

	/**
	 * This method will all the Salon Service details based on service duration
	 * 
	 * @param : serviceDuration
	 * @throws : NoSuchServiceException if service with duration not found
	 * @return List<SalonService> - This returns service details in the duration
	 **/
	@Override
	public List<SalonService> getServiceByDuration(String duration) {
		List<SalonService> serviceByDuration = salonRepository.findByDurationOrderByServiceId(duration);
		if (!serviceByDuration.isEmpty()) {
			log.info("Getting the List of service details based on service duration : " + duration);
			return serviceByDuration;
		} else {
			log.error("No service details found with service duration : " + duration + ". Throwing Exception");
			throw new NoSuchServiceException("Sorry!! No service is provided in this duration");
		}
	}
}
