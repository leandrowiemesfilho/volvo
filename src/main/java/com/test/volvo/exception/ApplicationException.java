package com.test.volvo.exception;

public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 3702602902172114352L;

	public ApplicationException(String msg) {
		super(msg);
	}
	
	public ApplicationException(Throwable t) {
		super(t);
	}

}
