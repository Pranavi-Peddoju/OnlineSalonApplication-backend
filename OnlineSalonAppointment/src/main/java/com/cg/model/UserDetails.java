package com.cg.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class UserDetails {
	
	@NotEmpty(message = "User Name cannot be null, please provide user id or user name")
	private String username;
	@NotEmpty(message = "Enter your Old Password")
	private String oldPassword;
	@NotEmpty(message = "Enter New Password")
	private String newPassword;
	
}
