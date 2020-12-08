package com.cg.exception;

public class InvalidPasswordException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1649376480759199088L;

	public InvalidPasswordException(String exception) {
		super(exception);
	}
}
