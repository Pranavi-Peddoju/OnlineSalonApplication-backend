package com.cg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ADDRESS")
@Scope("prototype")
@AllArgsConstructor
@RequiredArgsConstructor
public @Data class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -844825059552444066L;
	@Id
	@Column(name="ADDRESS_ID")
	private long addressId;
	@Column(name="DOOR_NO")
	private String doorNo;
	@Column(name="STREET")
	private String street;
	@Column(name="CITY")
	private String city;
	@Column(name="STATE")
	private String state;
	@Column(name="PIN_CODE")
	private String pinCode;
	
	
	
}
