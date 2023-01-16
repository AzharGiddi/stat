package com.nissan.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import com.nissan.driver.DriverFactory;
import com.nissan.driver.DriverManager;
import com.nissan.reports.ExtentLogger;
import com.nissan.reports.ExtentReport;
import com.nissan.utils.ROUtil;

public class BaseTest {
	
	

	@BeforeMethod
	public void setUp(ITestResult result) {
		String scenarioName=result.getMethod().getMethodName();
		ExtentReport.createTest(scenarioName);
		ExtentLogger.info(scenarioName,false);
		
		//softassert = new SoftAssert();
	}

		//TODO convert both DriverManager and ROUtil to Test-Scoped instead of Static. 
	@AfterMethod
	public void teardown() {
		DriverManager.tearDown();
		ROUtil.setrONumber(null);
	//DriverFactory.quitDriver();

	}
	
	public WebDriver getDriver() {
		
		return DriverManager.getDriver();
		
		
	}
	
public Actions getActionDriver() {
		
		return DriverManager.getActionDriver();
		
		
	}

public JavascriptExecutor getJavaScriptExecutor() {
	
	return DriverManager.getJavaScriptExecutor();
	
	
}
	


	protected BaseTest() {

	}

}
