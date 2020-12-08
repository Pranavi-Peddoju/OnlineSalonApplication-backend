package com.cg.exception;

public class InvalidAppointmentException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidAppointmentException(String message)
	{
		super(message);
	}

}