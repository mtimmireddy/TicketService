package com.myapp.errorhandling;

public class SeatHoldNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2688339151231865397L;

	public SeatHoldNotFoundException() {
		super();
	}

	public SeatHoldNotFoundException(String s) {
		super(s);

	}

}
