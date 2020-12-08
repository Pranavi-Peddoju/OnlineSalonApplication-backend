package com.cg.model;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
@Component
public class UserCredentials {
	
	@NotEmpty(message = "User Name cannot be null, please provide user id or user name")
	private String username;
	@NotEmpty(message = "Provide Password to Login")
	private String password;
	

}
