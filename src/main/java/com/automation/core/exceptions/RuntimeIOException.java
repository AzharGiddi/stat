package com.automation.core.exceptions;

@SuppressWarnings("serial")
public class RuntimeIOException extends CustomRuntimeException {

	public RuntimeIOException(String message) {
		super(message);
	}

	public RuntimeIOException(String message, Throwable cause) {
		super(message, cause);
	}

}
