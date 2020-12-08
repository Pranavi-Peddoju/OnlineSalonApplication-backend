package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.exception.InvalidAccessException;
import com.cg.model.UserAccountConfirmation;
import com.cg.repository.AccountConfirmationRepository;

@Service
public class AccountConfirmationService {

	@Autowired
	private AccountConfirmationRepository confirmationRepository;

	@Autowired
	private EmailSenderService mailService;

	/**
	 * confirmUserAccount
	 * <p>
	 * This method is confirms the activation of user account by setting status to true
	 * </p>
	 * 
	 * @param otp
	 * @return UserAccountConfirmation
	 */
	public UserAccountConfirmation confirmUserAccount(long otp) {
		UserAccountConfirmation token = confirmationRepository.findByConfirmationToken(otp);
		if (token != null) {
			token.setActive(true);
			mailService.activated(token.getUser().getUserId());
			return confirmationRepository.save(token);
		} else {
			throw new InvalidAccessException("Token has expired");
		}
	}

}
