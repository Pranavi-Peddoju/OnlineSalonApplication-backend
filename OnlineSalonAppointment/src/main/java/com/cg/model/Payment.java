package com.cg.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Component
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "PAYMENT")
public class Payment implements Serializable{

	private static final long SerialVersionUID=1L;
	@Id
	@Column(name = "PAYMENT_ID")
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private long paymentId;

	@Column(name = "TYPE")
	private String type;
    
	@Column(name = "STATUS")
	private String status;

	
	 @ManyToOne(cascade = CascadeType.ALL)
	  
	 @JoinColumn(name = "CARD_ID")
	
	private Card card;
	
	

	

}
