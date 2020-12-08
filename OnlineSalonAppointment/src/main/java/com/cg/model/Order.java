package com.cg.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ORDERS")
@Component
//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Order implements  Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private long orderId;
	
	  @NotNull
	 private double amount;
	
	@Column(name = "BILLING_DATE")
	@FutureOrPresent
	private LocalDate billingDate;
	
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	@NotNull
	private Customer customer;

	//@Enumerated(EnumType.STRING)
	@NotNull
	@Size(min=3,message="Payment method should be atleast in 3 characters")
	private String paymentMethod;
	
}
