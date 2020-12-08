package com.cg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.exception.AppointmentIdNotFoundException;
import com.cg.exception.DuplicateRecordException;
import com.cg.exception.InvalidAppointmentException;
import com.cg.model.Appointment;
import com.cg.repository.IAppointmentRepository;

@Service
public class AppointmentServiceImpl implements IAppointmentService {

	@Autowired
	private IAppointmentRepository appRepo;

	/**
	 *  This addAppointment method is used add appointment to(by) a customer
	 *  @return returns the added appointment, throws duplicate exception if any appointment id
	 *  is repeated (make sure no two appointments have same id)
	 */
	@Override
	public Appointment addAppointment(Appointment appointment) {

		if (!appRepo.existsById(appointment.getAppointmentId())) {
			Appointment addApp = appRepo.save(appointment);
			return addApp;
		} else {
			throw new DuplicateRecordException("This is the duplicate id");
		}
	}
	/**
	 * This method helps in removing the appointments placed
	 * @return if appointment found with provided id removes it and returns a boolean value if id not found throws an exception
	 * 
	 */
	@Override
	public boolean removeAppointment(long id) throws AppointmentIdNotFoundException {

		if (appRepo.existsById(id)) {
			appRepo.deleteById(id);
			return true;

		} else {
			throw new AppointmentIdNotFoundException("Appointment id is not exist");
		}

	}
	 /**
	   * This updateAppointment method updates the appointment by reading id
	   * @return if appointment found and updated returns updated appointment if order not found 
	   * then throws exception
	   */
	@Override
	public Appointment updateAppointment(long id, Appointment appointment) throws InvalidAppointmentException {
		// Appointment updateApp = appRepo.getOne(id);
		// updateApp.setLocation(appointment.getLocation());
		if (appRepo.existsById(id)) {

			return appRepo.save(appointment);

		} else {
			throw new InvalidAppointmentException("Appointment not found");
		}

	}
	/**
	 *  This getAppointmentDetails method is used to find details of appointment by giving appointment id
	 *   @return returns the appointment by reading appointment id, if id not found throws an exception
	 */
	@Override
	public Appointment getAppointment(long id) throws AppointmentIdNotFoundException {
		
		if (appRepo.existsById(id)) {
			Appointment getApp = appRepo.findById(id).get();
			return getApp;
		} else {
			throw new AppointmentIdNotFoundException("Appointment Id not found");
		}

	}
	/**
	 *  This getAllAppointment method gives all the appointments made by customer
	 *  @return if number of appointments greater zero then returns appointments otherwise throws exception
	 */
	@Override
	public List<Appointment> getAllAppointments() {
		List<Appointment> list = appRepo.findAll();
		return list;
	}

	@Override
	public List<Appointment> getOpenAppointments() {
		 List<Appointment> list = appRepo.getOpenAppointments();
		return list;
	}
	

}
