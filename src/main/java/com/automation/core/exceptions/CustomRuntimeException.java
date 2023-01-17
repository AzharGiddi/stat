package com.automation.core.exceptions;

import com.automation.core.reports.ExtentLogger;

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
