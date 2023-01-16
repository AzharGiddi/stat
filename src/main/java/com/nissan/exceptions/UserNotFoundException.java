package com.nissan.exceptions;

@SuppressWarnings("serial")
public class UserNotFoundException extends CustomRuntimeException {

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	public UserNotFoundException(String message) {
		super(message);

	}

}
