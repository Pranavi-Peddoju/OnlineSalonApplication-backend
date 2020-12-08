package com.cg.model;

import java.time.LocalDate;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Entity
@Component
@Table(name = "ACCOUNT_CONFIRMATION")
@Getter
@Setter
public class UserAccountConfirmation {

	@Id
	@Column(name = "token_id")
	private long tokenid;

	@Column(name = "confirmation_token")
	private long confirmationToken;

	@Column(name = "CREATED_DATE")
	private LocalDate createdDate;

	@Column(name = "Activated")
	private boolean isActive;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	private User user;

	public UserAccountConfirmation() {
	}

	public UserAccountConfirmation(User user) {
		this.user = user;
		createdDate = LocalDate.now();
		Random random = new Random();
		confirmationToken = random.nextInt(10000);
	}
}
