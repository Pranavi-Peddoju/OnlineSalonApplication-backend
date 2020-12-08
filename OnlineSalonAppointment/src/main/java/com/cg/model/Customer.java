package com.cg.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "CUSTOMER")
@Component
@Scope("prototype")
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class Customer extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4846601128410278529L;

	@NotNull(message = "Name cannot be null")
	@Column(name = "NAME")
	private String name;
	@Column(name = "EMAIL")
	@Size(max = 30, message = "The email id you have provided(${validatedValue}) is very long. Please choose another")
	@Email
	private String email;
	@Column
	@Size(min = 10, max = 10, message = "Contact Number you provided(${validatedValue}) must be 10 digits")
	private String contactNo;
	@Column(name = "DATE_OF_BIRTH", columnDefinition = "DATE")
	@Past(message = "Date must be less than today's date")
	private LocalDate dob;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

}
