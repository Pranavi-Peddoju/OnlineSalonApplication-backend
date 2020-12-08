package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.model.UserAccountConfirmation;

public interface AccountConfirmationRepository extends JpaRepository<UserAccountConfirmation, Long>{

	UserAccountConfirmation findByConfirmationToken(long otp);

	UserAccountConfirmation findByUser(long userId);


}
