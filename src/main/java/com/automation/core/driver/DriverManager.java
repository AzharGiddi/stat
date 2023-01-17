package com.automation.core.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.automation.core.databeans.TestDataBean;
import com.automation.core.databeans.TestDataBeanManager;

public final class DriverManager {

	private static ThreadLocal<WebDriver> dr = new ThreadLocal<>();
	private static ThreadLocal<JavascriptExecutor> js = new ThreadLocal<>();
	private static ThreadLocal<Actions> action = new ThreadLocal<>();
	private static DriverManager instance = new DriverManager();
	
	

	public static void setJs(JavascriptExecutor javascriptExecutor) {
		js.set(javascriptExecutor);
	}

	public static void setAction(Actions actionDriver) {
		action.set(actionDriver);
	}

	public static WebDriver getDriver() {
		return dr.get();
	}

	public static Actions getActionDriver() {
		/*
		 * if (Objects.isNull(action)) { action = new Actions(getDriver()); }
		 */
		return action.get();
	}

	public static JavascriptExecutor getJavaScriptExecutor() {

		return js.get();
	}

	public static void setDriver(WebDriver driverRef) {
		dr.set(driverRef);
	}

	public static void setDriver(String browserName) {
		DriverFactory.initDriver(browserName,false);
		TestDataBeanManager.setTestData(new TestDataBean());
		setAction(new Actions(getDriver()));
		setJs((JavascriptExecutor) getDriver());

	}
	
	public static void setDriver(String browserName,boolean headless) {
		TestDataBeanManager.setTestData(new TestDataBean());
		DriverFactory.initDriver(browserName,headless);
		setAction(new Actions(getDriver()));
		setJs((JavascriptExecutor) getDriver());

	}
	
	public static void setDriver(String browserName,boolean headless, boolean gridExecution) {
		TestDataBeanManager.setTestData(new TestDataBean());
		DriverFactory.initDriver(browserName,headless,gridExecution);
		setAction(new Actions(getDriver()));
		setJs((JavascriptExecutor) getDriver());

	}
	
	
	public static void unload() {
		TestDataBeanManager.unload();
		dr.remove();
		js.remove();
		action.remove();
	}

	public static void tearDown() {

		//DriverFactory.quitDriver();
		getDriver().quit();
		unload();
	}

	public static void tearDown(WebDriver driver) {

		DriverFactory.quitDriver(driver);
	}

	public static WebDriver getNewDriver() {

		return DriverFactory.getNewDriver();
	}

	private DriverManager() {

	}

	public static DriverManager getInstance() {

		return instance;
	}
	
	
}
