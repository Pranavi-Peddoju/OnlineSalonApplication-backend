package com.cg.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "APPOINTMENT")
@Scope("prototype")
@AllArgsConstructor
@RequiredArgsConstructor
public @Data class Appointment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "APPOINTMENT_ID")
	private long appointmentId;
	private String location;
	private String visitType;
	@Column(name = "PREFERRED_SERVICE")
	private String preferredService;
	
	@Column(name ="PREFERRED_DATE")
	private LocalDate preferredDate;
	@Column(name ="PREFERRED_TIME")
	private LocalTime preferredTime;
		
	@ManyToOne
	@JoinColumn(name ="CUSTOMER_ID")
	private Customer customer;	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PAYMENT_ID")
	private Payment payment;

}