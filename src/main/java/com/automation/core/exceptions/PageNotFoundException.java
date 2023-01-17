package com.automation.core.exceptions;

import org.apache.log4j.Logger;




public class PageNotFoundException extends CustomRuntimeException{
	
	public PageNotFoundException(String message, Throwable err) {
		
		super(message,err);
		
	}
	
public PageNotFoundException(String message) {
		
		super(message);
		
	}

}
