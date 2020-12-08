package com.cg.exception;

public class InvalidOrderException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5999377008778431809L;

	public InvalidOrderException(String message)
	{
		super(message);
	}

}
