package com.automation.core.exceptions;

import org.testng.SkipException;

/**
 * exception class to indicate special skip
 * 
 * @author AB00789853
 */
public class DataProviderException extends SkipException {

	public DataProviderException(String message) {
		super(message);

	}

	public DataProviderException(String message, Throwable cause) {
		super(message, cause);

	}

	@Override
	public boolean isSkip() {
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}