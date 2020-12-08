package com.cg.exception;

public class NoSuchServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7330391709538510988L;

	public NoSuchServiceException(String message) {
		super(message);
	}

}
