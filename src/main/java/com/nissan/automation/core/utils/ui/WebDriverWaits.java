package com.nissan.automation.core.utils.ui;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import com.nissan.driver.DriverManager;

public class WebDriverWaits extends DynamicWait<WebDriver> {

	/**
	 * Wait will ignore instances of NotFoundException that are encountered
	 * (thrown) by default in the 'until' condition, and immediately propagate
	 * all others. You can add more to the ignore list by calling
	 * ignoring(exceptions to add).
	 * 
	 * @param driver
	 *            The WebDriver instance to pass to the expected conditions
	 * @param timeOutInMiliSeconds
	 *            The timeout in seconds when an expectation is called
	 * @see QAFWebDriverWait#ignoring(Class[]) equals
	 */
	public WebDriverWaits(WebDriver driver, long timeOutInMiliSeconds) {
		super(driver);
		withTimeout(timeOutInMiliSeconds, TimeUnit.MILLISECONDS);
		ignoring(Exception.class);
	}

	/**
	 * Wait will ignore instances of NotFoundException that are encountered
	 * (thrown) by default in the 'until' condition, and immediately propagate
	 * all others. You can add more to the ignore list by calling
	 * ignoring(exceptions to add).
	 * 
	 * @param driver
	 *            The WebDriver instance to pass to the expected conditions
	 * @param timeOutInMiliSeconds
	 *            The timeout in seconds when an expectation is called
	 * @param sleepInMillis
	 *            The duration in milliseconds to sleep between polls.
	 * @see QAFWebDriverWait#ignoring(Class[]) equals
	 */
	public WebDriverWaits(WebDriver driver, long timeOutInMiliSeconds,
			long sleepInMillis) {
		this(driver,timeOutInMiliSeconds);
		pollingEvery(sleepInMillis, TimeUnit.MILLISECONDS);
	}

	/**
	 * Wait will ignore instances of NotFoundException that are encountered
	 * (thrown) by default in the 'until' condition, and immediately propagate
	 * all others. You can add more to the ignore list by calling
	 * ignoring(exceptions to add).
	 * 
	 * @param driver
	 *            The WebDriver instance to pass to the expected conditions
	 */
	public WebDriverWaits(long... timeout) {
		this(DriverManager.getDriver(), timeout);
	}

	public WebDriverWaits(WebDriver driver, long... timeout) {
		this(driver, getTimeout(timeout), getInterval(timeout));
	}


	private static long getTimeout(long... timeout) {
		if ((null == timeout) || (timeout.length < 1) || (timeout[0] <= 0)) {
			return getDefaultTimeout();
		}
		return timeout[0];
	}

	private static long getInterval(long... timeout) {
		if ((null == timeout) || (timeout.length < 2) || (timeout[1] <= 0)) {
			return getDefaultInterval();
		}
		return timeout[1];
	}
}
