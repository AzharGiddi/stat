package com.automation.core.exceptions;

import org.apache.log4j.Logger;




public class NoSuchOpenTabException extends RuntimeException{
	
	public NoSuchOpenTabException(String message, Throwable err) {
		
		super(message,err);
		
	}
	
public NoSuchOpenTabException(String message) {
		
		super(message);
		
	}

}
