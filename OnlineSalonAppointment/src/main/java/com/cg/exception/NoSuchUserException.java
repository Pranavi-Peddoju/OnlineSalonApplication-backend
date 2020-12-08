package com.cg.exception;

public class NoSuchUserException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2016936902700842302L;

	public NoSuchUserException(String exception) {
		super(exception);
	}
}
