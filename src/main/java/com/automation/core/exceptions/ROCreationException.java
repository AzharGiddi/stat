package com.automation.core.exceptions;

import org.apache.log4j.Logger;




public class ROCreationException extends CustomRuntimeException{
	
	public ROCreationException(String message, Throwable err) {
		
		super(message,err);
		
	}
	
public ROCreationException(String message) {
		
		super(message);
		
	}

}
