package com.nissan.automation.core.utils.ui;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

import com.nissan.WebElement.ExtWebElementImpl;

public class WebElementWait extends DynamicWait<ExtWebElementImpl> {

	/**
	 * Wait will ignore instances of NotFoundException that are encountered (thrown)
	 * by default in the 'until' condition, and immediately propagate all others.
	 * You can add more to the ignore list by calling ignoring(exceptions to add).
	 * 
	 * @param element
	 *            The WebElement instance to pass to the expected conditions
	 * @param timeOutInMiliSeconds
	 *            The timeout in seconds when an expectation is called
	 * @see QAFWebElementWait#ignoring(Class[]) equals
	 */
	public WebElementWait(ExtWebElementImpl element, long timeOutInMiliSeconds) {
		super(element);
		withTimeout(timeOutInMiliSeconds, TimeUnit.MILLISECONDS);
		ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
	}

	/**
	 * Wait will ignore instances of NotFoundException that are encountered (thrown)
	 * by default in the 'until' condition, and immediately propagate all others.
	 * You can add more to the ignore list by calling ignoring(exceptions to add).
	 * 
	 * @param element
	 *            The WebElement instance to pass to the expected conditions
	 * @param timeOutInMiliSeconds
	 *            The timeout in seconds when an expectation is called
	 * @param sleepInMillis
	 *            The duration in milliseconds to sleep between polls.
	 * @see QAFWebElementWait#ignoring(Class[]) equals
	 */
	public WebElementWait(ExtWebElementImpl element, long timeOutInMiliSeconds, long sleepInMillis) {
		this(element, timeOutInMiliSeconds);
		pollingEvery(sleepInMillis, TimeUnit.MILLISECONDS);
	}

	public WebElementWait(ExtWebElementImpl element, long... timeout) {
		this(element, getTimeout(timeout), getInterval(timeout));
	}

	private static long getTimeout(long... timeout) {
		if ((null == timeout) || (timeout.length < 1) || (timeout[0] <= 0)) {
			return getDefaultTimeout();
		}
		return timeout[0];
	}

	private static long getInterval(long... timeout) {
		try {
			if ((null == timeout) || (timeout.length < 2) || (timeout[1] <= 0)) {
				return getDefaultInterval();
			}
			return timeout[1];
		} catch (Exception e) {
			return 5000;
		}

	}
}
