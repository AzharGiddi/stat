package com.nissan.exceptions;

import com.nissan.reports.ExtentLogger;

@SuppressWarnings("serial")
public class CustomRuntimeException extends RuntimeException {

	public CustomRuntimeException(String message) {
		super(message);
		ExtentLogger.failWithScreenShot(message);

	}

	public CustomRuntimeException(String message, Throwable cause) {
		super(message, cause);
		ExtentLogger.failWithScreenShot(cause.getLocalizedMessage());
	}

}
