package com.test.volvo.exception;

public class InvalidEntityException extends ApplicationException {
	private static final long serialVersionUID = 8113745541125719585L;

	public InvalidEntityException(String msg) {
		super(msg);
	}
	
	public InvalidEntityException(Throwable t) {
		super(t);
	}

}

