package com.cg.exception;

public class DuplicateRecordException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4334945028623160469L;

	public DuplicateRecordException(String message)
	{
		super(message);
	}
}
