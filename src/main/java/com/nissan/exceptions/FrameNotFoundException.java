package com.nissan.exceptions;




public class FrameNotFoundException extends RuntimeException{
	
	public FrameNotFoundException(String message, Throwable err) {
		
		super(message,err);
		
	}
	
public FrameNotFoundException(String message) {
		
		super(message);
		
	}

}
