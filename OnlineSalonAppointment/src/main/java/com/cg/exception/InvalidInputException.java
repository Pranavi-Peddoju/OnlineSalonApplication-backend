package com.cg.exception;

public class InvalidInputException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4659718956278126007L;

	public InvalidInputException(String message)
	{
		super(message);
	}
}
