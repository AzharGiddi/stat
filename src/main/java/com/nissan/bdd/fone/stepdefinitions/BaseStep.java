package com.nissan.bdd.fone.stepdefinitions;

import java.util.Objects;

import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.nissan.automation.core.utils.ExecutionRecorder;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.databeans.GWTestDataBean;
import com.nissan.databeans.TestDataBean;
import com.nissan.driver.DriverManager;
import com.nissan.reports.ExtentLogger;
import com.nissan.reports.ExtentManager;
import com.nissan.reports.ExtentReport;
import com.nissan.utils.ROAPIUtil;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Scenario;

public class BaseStep {

	public static TestDataBean testData;
	
	public static TestDataBean getDataBean() {
		/*if(Objects.isNull(testData))
			testData = new TestDataBean();*/
		
		return testData;
	}
	
		
	public static void setDataBean(TestDataBean testDataBean) {
		
		testData = testDataBean;
		
	}
	
public TestDataBean testData1;
	
	public TestDataBean getDataBean1() {
		/*if(Objects.isNull(testData))
			testData = new TestDataBean();*/
		
		return testData1;
	}
	
		
	public void setDataBean1(TestDataBean testDataBean) {
		
		testData1 = testDataBean;
		
	}
	
	
	

		
	@Before(order=0)
	public void setUp(Scenario sceanrio) {

		//DriverManager.setDriver(ConfigurationManager.getBundle().getString("browser.name"));
		String scenarioName=sceanrio.getName();
		ExtentReport.createTest(scenarioName);
		/*try {
			ExecutionRecorder.startRecord(scenarioName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentLogger.info("Failed to start recording, Execution will not be recorded", true);
		}*/
		ExtentLogger.info(scenarioName,false);
		setDataBean(new TestDataBean());
		setDataBean1(new TestDataBean());

	}
	
	
	/*@AfterStep
	public void addScreenshot(Scenario scenario) {

		// validate if scenario has failed
		if (scenario.isFailed()) {
			ExtentLogger.failWithScreenShot("test case failed");
		}

	}
	
	@After(order=1)
	public void check(Scenario scenario) {
		
		if(scenario.getStatus().equals("Pass") && ExtentManager.getExtentTest().getStatus().equals("Fail")){
			Reporter.log("test case failed", true);
		}
	}*/

	@After(order = 0)
	public void teardown() {
		testData=null;
		/*try {
			ExecutionRecorder.stopRecord();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentLogger.info("Failed to Stop recording.", true);
			e.printStackTrace();
		}*/
	//	ROAPIUtil.setrONumber(null);
		//ExtentReport.flushReports();
	//	DriverManager.tearDown();
	}

}
