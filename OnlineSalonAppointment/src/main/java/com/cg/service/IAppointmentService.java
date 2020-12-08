package com.cg.service;

import java.util.List;

import com.cg.model.Appointment;

public interface IAppointmentService {

	public Appointment addAppointment(Appointment appointment);
	public boolean removeAppointment(long id); 
	public Appointment updateAppointment(long id, Appointment appointment);
	public Appointment getAppointment(long id); 
	public List<Appointment> getAllAppointments(); 
	public List<Appointment> getOpenAppointments();
	
}
