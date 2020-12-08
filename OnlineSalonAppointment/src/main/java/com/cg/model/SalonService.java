package com.cg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "salon_service")
@Component
@Scope("prototype")
@RequiredArgsConstructor
@AllArgsConstructor
public @Data class SalonService  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4138844593865846481L;
	
	@Id
	@Column(name = "SERVICE_ID")
	private long serviceId;
	@NotEmpty(message = " Service Name is a mandatory field. Please provide service Name")
	@Column(name = "SERVICE_NAME")
	private String serviceName;
	@Column(name = "PRICE")
	private double price;
	@PositiveOrZero(message = "Discount(${validatedValue}) must be either zero or more")
	@Column(name = "DISCOUNT")
	private int discount;
	@Column(name = "DURATION")
	private String duration;
	
}
