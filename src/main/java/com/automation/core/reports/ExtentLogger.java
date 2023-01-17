package com.automation.core.reports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.testng.ITestResult;

import com.automation.core.configuration.ConfigurationManager;
import com.automation.core.utils.ScreenshotUtils;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public final class ExtentLogger {

	private static ExtentLogger instance = new ExtentLogger();
	
	private static final Log logger = LogFactoryImpl.getLog(ExtentLogger.class);

	/**
	 * Logs a step as pass in the extent report with message passed in param.
	 * 
	 * @param message
	 *
	 */
	public static void pass(String message) {

		ExtentManager.getExtentTest().pass(message);
		logger.info(message);
		
	}
	
	/*public static void pass(String message, Class<?> clazz) {
		Log log = LogFactoryImpl.getLog(clazz);
		ExtentManager.getExtentTest().pass(message);
		log.info(message);
		
	}*/
	
	public static void pass(String message, Log log) {
		
		ExtentManager.getExtentTest().pass(message);
		log.info(message);
		
	}
	
	public static void pass(String message, boolean logToExtent) {
		if(logToExtent)
			ExtentManager.getExtentTest().pass(message);
		logger.info(message);
		
	}
	
	

	/**
	 * Logs a step as pass along with screenshot in the extent report with message
	 * passed in param.
	 * 
	 * @param message
	 *            <p>
	 *            Note: Screenshot is taken only if screenshot.passed is set to true
	 *            in properties file.
	 *            </p>
	 */
	public static void passWithScreenShot(String message) {
		if (ConfigurationManager.getBundle().getString("screenshot.passed").equalsIgnoreCase("yes")) {
			ExtentManager.getExtentTest().pass(message,
					MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64String()).build());
			logger.info(message);
		} else {
			pass(message);
		}

	}

	/**
	 * Logs a step as fail in the extent report with message passed in param.
	 * 
	 * @param message
	 *
	 */
	public static void fail(String message) {

		ExtentManager.getExtentTest().fail(message);
		logger.error(message);
	}
	
	/**
	 * Logs a step as fail in the extent report with message passed in param only when logToExtent is true. Else only los in the console
	 * 
	 * @param message - Fail message
	 *	@param logToExtent - boolean value to determine whether need to log the message in extent report or not.
	 */
	public static void fail(String message, boolean logToExtent) {
		if(logToExtent)
			ExtentManager.getExtentTest().fail(message);
		logger.error(message);
	}
	
	public static void fail(String message,Throwable cause) {

		ExtentManager.getExtentTest().fail(message+" : "+cause.getLocalizedMessage());
		logger.error(message);
	}
	
	/**
	 * Logs a step as fail in the extent report with message passed in param.
	 * 
	 * @param message
	 *
	 */
	public static void fail(Throwable cause) {

		ExtentManager.getExtentTest().fail(cause,MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64String()).build());
		logger.error(cause.getLocalizedMessage());
	}

	/**
	 * Logs a step as fail along with screenshot in the extent report with message
	 * passed in param.
	 * 
	 * @param message
	 *            <p>
	 *            Note: Screenshot is taken only if screenshot.failed is set to true
	 *            in properties file.
	 *            </p>
	 */
	public static void failWithScreenShot(String message) {
		if (ConfigurationManager.getBundle().getString("screenshot.failed").equalsIgnoreCase("yes")) {
			ExtentManager.getExtentTest().fail(message,	MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64String()).build());
			logger.error(message);
		} else {
			fail(message);
		}

	}

	/**
	 * Logs a step as skip in the extent report with message passed in param.
	 * 
	 * @param message
	 *
	 */
	public static void skip(String message) {

		ExtentManager.getExtentTest().skip(message);
		logger.info(message);
	}

	/**
	 * Logs a step as skip along with screenshot in the extent report with message
	 * passed in param.
	 * 
	 * @param message
	 *            <p>
	 *            Note: Screenshot is taken only if screenshot.skipped is set to
	 *            true in properties file.
	 *            </p>
	 */
	public static void skipWithScreenShot(String message) {
		if (ConfigurationManager.getBundle().getString("screenshot.skipped").equalsIgnoreCase("yes")) {
			ExtentManager.getExtentTest().skip(message,
					MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64String()).build());
			logger.info(message);
		} else {
			skip(message);
		}

	}

	public static void logWithScreenshot(ITestResult result) {

		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			passWithScreenShot("Test case :" + result.getMethod().getMethodName() + " passed");
			break;
		case ITestResult.FAILURE:
			failWithScreenShot("Test case :" + result.getMethod().getMethodName() + " failed");
			break;
		case ITestResult.SKIP:
			skipWithScreenShot("Test case :" + result.getMethod().getMethodName() + " skipped");
			break;

		default:
			info("Invalid result status");
			break;
		}

	}
	
	public static void logWithScreenshot(String msg) {
		
		ExtentManager.getExtentTest().log(Status.INFO,msg,MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64String()).build());
		
	}

	/**
	 * Logs a step as info in the extent report with message passed in param.
	 * 
	 * @param message
	 *
	 */
	public static void info(String message) {

		ExtentManager.getExtentTest().info(message);
		logger.info(message);
	}

	public static void warn(String message) {
		ExtentManager.getExtentTest().warning(message);
		logger.warn(message);
	}
	
	public static void info(String message, boolean logToExtent) {
		
		if(logToExtent)
			ExtentManager.getExtentTest().info(message);
		logger.info(message);
	}

	private ExtentLogger() {
	}

	public enum MessageType {
		PASS, FAIL, INFO, WARN
	}

	public static void addMessage(String message, MessageType type, Object... objects) {

		switch (type) {

		case PASS:
				passWithScreenShot(message);
				break;
		case FAIL:
				failWithScreenShot(message);
				break;
		case INFO:
				info(message);
				break;
		case WARN:
				warn(message);
				break;

		default:
				info("Invalid MessageType, message will be logged as Info. Message : "+ message);
				

		}

	}

	public static ExtentLogger getInstance() {
		return instance;
	}

}
