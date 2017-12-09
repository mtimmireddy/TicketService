package com.myapp.errorhandling;

public class CustomerValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1007785632749164765L;

	public CustomerValidationException() {
		super();
	}

	public CustomerValidationException(String s) {
		super(s);

	}

}
