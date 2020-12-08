package com.cg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.model.Appointment;

public interface IAppointmentRepository extends JpaRepository<Appointment, Long>{
	
	
	@Query("select a from Appointment a where a.preferredDate >= CURRENT_DATE")
	public List<Appointment> getOpenAppointments();
	
}