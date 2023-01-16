package com.nissan.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class AutomationError extends SkipException {

	public AutomationError(String skipMessage) {
		super(skipMessage);

	}

	public AutomationError(String skipMessage, Throwable cause) {
		super(skipMessage, cause);

	}

	public AutomationError(Throwable cause) {
		this(cause.getMessage(), cause);
	}

	@Override
	public boolean isSkip() {
		return true;
	}

}
