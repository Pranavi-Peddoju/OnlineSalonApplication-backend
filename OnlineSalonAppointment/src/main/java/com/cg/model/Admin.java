package com.cg.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ADMIN")
@Component
@Scope("prototype")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Admin extends User
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1037935198579562125L;
	@Size(min = 10,max = 10,message =  "Contact Number(${validatedValue}) must be 10 digits")
	private String contactNo;
	@Size(max = 50, message = "The email id(${validatedValue}) you have provided is very long. Please choose another")
	@Email
	private String emailId;
	
}
