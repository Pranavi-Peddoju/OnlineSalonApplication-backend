package com.cg.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cg.exception.DuplicateRecordException;
import com.cg.exception.InvalidInputException;
import com.cg.exception.NoSuchUserException;
import com.cg.model.Admin;
import com.cg.repository.AdminRepo;

@Service
public class AdminserviceImpl implements IAdminService {

	private static final Logger log = LoggerFactory.getLogger(AdminserviceImpl.class);

	// Tells the application context to inject an instance of IAdminRepository
	@Autowired
	private AdminRepo repository;

	/**
	 * addAdmin
	 * <p>
	 * This method adds the admin details into database if userName is unique. Else
	 * Integrity Violation Exception is caught and duplicate record exception is
	 * thrown
	 * </p>
	 * 
	 * @param : admin
	 * @throws : DuplicateRecordException if a record with same id exists
	 * @return Admin - This returns the admin details
	 **/
	@Override
	public Admin addAdmin(Admin admin) {
		try {
			log.info("Storing the admin record into database");
			repository.save(admin);
			log.info("stored successfully");
			return admin;
		} catch (DataIntegrityViolationException ex) {
			log.error("Exception Stack Trace" + ex.getMessage());
			throw new DuplicateRecordException(
					"User with this user name  : " + admin.getUserName() + " Already Exists");
		}
	}

	/**
	 * updateAdmin
	 * <p>
	 * In this method If userId is provided and is an existing record, Then updates
	 * the details based on ID. Else if userId doesn't exist or if it is not
	 * Provided but if user Name is provided and is an existing record then the
	 * details are updated based on User Name.
	 * </p>
	 * 
	 * @param : admin
	 * @return Admin - This returns Admin details
	 **/
	@Override
	public Admin updateAdmin(Admin admin) {
		Admin toUpdate;
		if (repository.existsById(admin.getUserId())) {
			log.info("Valid User Id is provided updating based on user Id  : " + admin.getUserId());
			toUpdate = repository.findById(admin.getUserId()).get();
		} else {
			log.info("User Id might not be provide or Invalid.");
			toUpdate = getByUserName(admin.getUserName());
			log.info("Updating the record to the same userID based on existing user Name");
			admin.setUserId(toUpdate.getUserId());
		}
		log.info("Updating the Record of user with userId  : " + toUpdate.getUserId());
		return repository.save(admin);
	}

	/**
	 * removeAdmin
	 * <p>
	 * This method deletes the admin details based on admin's user Id
	 * </p>
	 * 
	 * @param : userId
	 * @return boolean - This returns true if successfully deleted
	 **/
	@Override
	public boolean removeAdmin(long userId) {
		Admin toRemove = getAdminById(userId);
		repository.delete(toRemove);
		log.info("Deleted admin details with user Id : " + userId);
		return true;
	}

	/**
	 * getAdminById
	 * <p>
	 * This method will get the admin details based on admin's user Id
	 * </p>
	 * 
	 * @param : userId
	 * @throws : NoSuchUserException if user with that id not found
	 *           InvalidInputException if id is not valid
	 * @return Admin - This returns Admin details
	 **/
	@Override
	public Admin getAdminById(long userId) {
		if (userId == 0 || userId < 999 || userId > 2000) {
			log.error("Invalid user Id provided. Throwing exception");
			throw new InvalidInputException("The user Id must be 4 digits starting with 1");
		} else if (repository.existsById(userId)) {
			log.info("Details exist. Getting the details By Id : " + userId);
			Admin get = repository.findById(userId).get();
			return get;
		} else {
			log.error("No user found throwing exception");
			throw new NoSuchUserException("Sorry! No such user found");
		}
	}

	/**
	 * getAllAdmins
	 * <p>
	 * This method will get the all the admin details
	 * </p>
	 * 
	 * @return List<Admin> - This returns List of All Admin details
	 **/
	@Override
	public List<Admin> getAllAdmins() {
		List<Admin> admins = repository.findAll();
		if (!admins.isEmpty()) {
			log.info("Returning all admin details");
			return admins;
		} else {
			log.error("List is empty. Throwing Exception");
			throw new NoSuchUserException("Currently No Admin details");
		}
	}

	/**
	 * getByUserName
	 * <p>
	 * This method will get the admin details based on admin's user Name
	 * </p>
	 * 
	 * @param : userName
	 * @throws : NoSuchUserException if user with that name not found
	 * @return Admin - This returns Admin details
	 **/
	@Override
	public Admin getByUserName(String userName) {

		Admin get = repository.findByUserName(userName);
		if (get != null) {
			log.info("Getting admin details based on admins user name : " + userName);
			return get;
		} else {
			log.error("No Admin details found with user name : " + userName + ". Throwing Exception");
			throw new NoSuchUserException("No such user found");
		}
	}

}
