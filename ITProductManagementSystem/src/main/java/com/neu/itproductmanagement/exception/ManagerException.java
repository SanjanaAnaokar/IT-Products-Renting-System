package com.neu.itproductmanagement.exception;

public class ManagerException extends Exception {


	public ManagerException(String message) {
		super(message);
	}

	public ManagerException(String message,Throwable cause) {
		super(cause);
	}

}
