package com.myapp.errorhandling;

public class TicketServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7008412877644461179L;

	public TicketServiceException() {
		super();
	}

	public TicketServiceException(String s) {
		super(s);
	}

	public TicketServiceException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public TicketServiceException(Throwable throwable) {
		super(throwable);

	}

}
