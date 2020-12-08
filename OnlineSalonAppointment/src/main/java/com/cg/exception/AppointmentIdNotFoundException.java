package com.cg.exception;


public class AppointmentIdNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public AppointmentIdNotFoundException(String message) {
		super(message);
	}
}