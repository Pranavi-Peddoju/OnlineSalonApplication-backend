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

import com.cg.model.Admin;
import com.cg.service.AdminserviceImpl;

/**
 * The AdminController class Has all The CRUD Operations
 *
 * @author :Peddoju Teja Pranavi
 * @version :1.0
 * @since :2020-12-01
 **/
@RestController
@RequestMapping("/auth/admin")
public class AdminController {

	private AdminserviceImpl service;

	private GenerateID auto;

	// Tells the application context to inject an instance of AdminServiceImpl and
	// GenerateId here
	@Autowired
	public AdminController(AdminserviceImpl service, GenerateID auto) {
		super();
		this.service = service;
		this.auto = auto;
	}

	/**
	 * addAdmin
	 * <p>
	 * This method is used to set a 4 digit code for the user id and sends to admin
	 * service add method
	 * </p>
	 * 
	 * @param : Admin
	 * @return Admin : This returns the user details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping
	public Admin addAdmin(@Valid @RequestBody Admin admin) {
		admin.setUserId(auto.generateID(admin));
		return service.addAdmin(admin);
	}

	/**
	 * updateAdmin
	 * <p>
	 * This method is used to update the user-admin details and sends the details to
	 * service layer for updation
	 * </p>
	 * 
	 * @param : Admin
	 * @return Admin : This returns the user details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping
	public Admin updateAdmin(@Valid @RequestBody Admin admin) {
		return service.updateAdmin(admin);
	}

	/**
	 * This method is used to get all the user - admin details
	 * 
	 * @return List<Admin> : This return all the admin details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/all")
	public List<Admin> getAdmin() {
		return service.getAllAdmins();
	}

	/**
	 * getAdminById
	 * <p>
	 * This method is used to get the user - admin details based on user id
	 * </p>
	 * 
	 * @param : id
	 * @return Admin : This return the admin details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/{id}")
	public Admin getAdminById(@Valid @PathVariable("id") long userId) {
		return service.getAdminById(userId);
	}

	/**
	 * getAdminByUserName
	 * <p>
	 * This method is used to get the user - admin details based on unique user name
	 * </p>
	 * 
	 * @param : userName
	 * @return Admin : This return the admin details.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping
	public Admin getAdminByUserName(@Valid @RequestParam("name") String userName) {
		return service.getByUserName(userName);
	}

	/**
	 * deleteAdmin
	 * <p>
	 * This method is used to delete the user details based on user id
	 * </p>
	 * 
	 * @param : id
	 * @return ResponseEntity<String> : This return success message upon successful
	 *         deletion.
	 **/
	@ResponseStatus(value = HttpStatus.OK)
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAdmin(@PathVariable("id") long userId) {
		boolean check = service.removeAdmin(userId);
		if (check) {
			return new ResponseEntity<>("Deleted Succesfully", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Sorry come back again", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
