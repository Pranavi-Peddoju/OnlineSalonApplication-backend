package com.cg.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javassist.SerialVersionUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Card implements Serializable {

	private static final long SerialVersionUID = 1L;
	@Id
	@Column(name = "CARD_ID")

	private long cardId;

	@Column(name = "CARD_NAME")
	private String cardName;

	@Column(name = "CARD_NUMBER")
	private String cardNumber;

	@Column(name = "CARD_EXPIRY")
	private LocalDate cardExpiry;

	@Column(name = "CVV")
	private int cvv;

	@Column(name = "BANK_NAME")
	private String bankName;

	/*
	 * @OneToMany(mappedBy = "card", fetch = FetchType.EAGER, cascade =
	 * CascadeType.ALL) private Set<Payment> payments = new HashSet<>();
	 */

	}

	

