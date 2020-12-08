package com.cg.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.cg.exception.AppointmentIdNotFoundException;
import com.cg.exception.InvalidAppointmentException;
import com.cg.model.Appointment;
import com.cg.service.IAppointmentService;
/**
 * @author Jeevika K
 */
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private IAppointmentService appService;
	
	@Autowired
    private GenerateID auto;
	
	/**
	 * 
	 * @param appointment calls service method for adding appointment
	 * @return posted if no appointment is already placed with that id, otherwise
	 * @throws DuplicateAppointmentException
	 */

	@PostMapping
	public Appointment addAppointment(@RequestBody Appointment appointment) {
		appointment.setAppointmentId(auto.generateID(appointment));
		Appointment addApp = appService.addAppointment(appointment);	
		return addApp;
	}
	
	/**
	 * 
	 * @param id calls service method to get appointment details by id
	 * @return appointment of that id if found, otherwise
	 * @throws AppointmentIdNotFoundException
	 */
	@GetMapping("/{id}")
	public Appointment getAppointmentById(@PathVariable("id") long id) {
		Appointment getApp = appService.getAppointment(id);
		return getApp;
	}
	
	/**
	 * 
	 * @param id 
	 * @param appointment 
	 * @return appointment of that id if found, otherwise
	 * @throws InvalidAppointmentException
	 */
	@PutMapping("/{id}")
	public Appointment updateAppointment(@PathVariable("id") long id,@RequestBody Appointment appointment){
		Appointment updApp= appService.updateAppointment(id, appointment);
		return updApp;
	}
	
	/**
	 * <p>
	 * calls service to get all appointment
	 * </p>
	 * 
	 * @return all appointment if number of appointment not zero, otherwise
	 * @throws InvalidAppointmentException
	 */
	@GetMapping
	public List<Appointment> getAllAppointmentDetails() throws InvalidAppointmentException{
		List<Appointment> getAll = appService.getAllAppointments();
		return getAll;
	}
	
	/**
	 * <p>
	 * calls service to get open appointment
	 * </p>
	 * 
	 * @return appointment if preferred date is on or before local date
	 */
	@GetMapping("/open")
	public List<Appointment> getOpenAppointments() {
		List<Appointment> getOpen = appService.getOpenAppointments();
		return getOpen;
	}
	
	
	/**
	 * <p>
	 * calls delete method in service
	 * </p>
	 * 
	 * @param id
	 * @return a string message if deleted,if appointment not found then returns Http
	 *         status message
	 * @throws AppointmentIdNotFoundException
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAppointment(@PathVariable("id") long id) throws AppointmentIdNotFoundException
	{
		boolean deleteApp =appService.removeAppointment(id);
		ResponseEntity<String> response;
		if(deleteApp) {
			response = new ResponseEntity<String>("Appointment is deleted",HttpStatus.CREATED );
		}
		else {
			response = new ResponseEntity<String>("Appointment is  not deleted",HttpStatus.INTERNAL_SERVER_ERROR );
		}
		return response;
	}

}
