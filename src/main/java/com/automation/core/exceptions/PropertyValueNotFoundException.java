package com.automation.core.exceptions;

@SuppressWarnings("serial")
public class PropertyValueNotFoundException extends CustomRuntimeException {

	public PropertyValueNotFoundException(String message) {
		super(message);
		
		
	}

	public PropertyValueNotFoundException(String message, Throwable cause) {
		super(message, cause);
		cause.printStackTrace();
		System.exit(0);
		
	}
	
	

}
