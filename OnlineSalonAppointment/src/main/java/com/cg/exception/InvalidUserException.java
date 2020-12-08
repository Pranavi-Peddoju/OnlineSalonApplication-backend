package com.cg.exception;

public class InvalidUserException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8619829236406476183L;

	public InvalidUserException(String exception) {
		super(exception);
	}
}
