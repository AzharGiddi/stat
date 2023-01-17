package com.automation.core.utils.listeners;

import java.util.concurrent.TimeUnit;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.automation.core.configuration.ConfigurationManager;
import com.automation.core.driver.DriverManager;
import com.automation.core.driver.StartHub;
import com.automation.core.reports.ExtentLogger;
import com.automation.core.reports.ExtentManager;
import com.automation.core.reports.ExtentReport;
import com.aventstack.extentreports.Status;
import com.constants.NAFConstants;
import com.pages.BasePage;

public class ConfigListener implements ITestListener, ISuiteListener, IInvokedMethodListener {
	
	private static int onTestFailureCallCount;
	private static boolean onTestSuccess;
	private static long start;

	public static int getOnTestFailureCallCount() {
		return onTestFailureCallCount;
	}

	public static void setOnTestFailureCallCount(int onTestFailureCallCount) {
		ConfigListener.onTestFailureCallCount = onTestFailureCallCount;
	}
public static boolean isGridExecution() {
		return isGridExecution;
	}

	public static void setGridExecution(boolean isGridExecution) {
		ConfigListener.isGridExecution = isGridExecution;
	}
private static boolean isGridExecution;
	@Override
	public void onStart(ISuite suite) {

		start=System.currentTimeMillis();
		//Start grid server
		setGridExecution(ConfigurationManager.getBundle().getBoolean("grid.execution"));
		if(isGridExecution())
			StartHub.startHub();
		
		/*try {
			Field field= suite.getAllMethods().get(0).getClass().getSuperclass().getDeclaredField("m_instance");
			field.setAccessible(true);
		Object obj = field.get(suite.getAllMethods().get(0));
		
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//System.out.println(suite.getAllMethods());

	}

	@Override
	public void onFinish(ISuite suite) {
		long end = System.currentTimeMillis()-start;
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(end),
			    TimeUnit.MILLISECONDS.toMinutes(end) % TimeUnit.HOURS.toMinutes(1),
			    TimeUnit.MILLISECONDS.toSeconds(end) % TimeUnit.MINUTES.toSeconds(1));
		ExtentLogger.info("Total time elapsed "+hms, false);
		ExtentLogger.info("Extent Report can be access at: "+NAFConstants.getExtentReportFilePath(), false);
		ExtentReport.flushReports();
		
		//DriverManager.tearDown();

	}

	@Override
	public void onTestStart(ITestResult result) {
		
		String browserName = ConfigurationManager.getBundle().getString("browser.name");
		boolean headless = ConfigurationManager.getBundle().getBoolean("browser.headless");
		DriverManager.setDriver(browserName,headless,isGridExecution());
		ExtentReport.initReports();
		ExtentLogger.info(result.getTestContext().getCurrentXmlTest().getName(),false);
		
		//System.out.println(scenarioCount);
	//	ExtentReport.createTest(result.getTestContext().getCurrentXmlTest().getName());
	//	ROAPIUtil.setrONumber(null);

		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentLogger.info("test case Passed",false);
		DriverManager.tearDown();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		try {
			//ExtentLogger.failWithScreenShot("test case failed due to: "+ result.getThrowable().getLocalizedMessage());
			ExtentLogger.fail(result.getThrowable());
			ExtentLogger.info("tear down started",false);
			new BasePage().logOutOnFailure();
		
		}catch(Exception e) {
			ExtentLogger.fail("Exception caught: "+e.getLocalizedMessage());
			e.printStackTrace();
		}finally {
			
			DriverManager.tearDown();
			ExtentLogger.info("tear down completed",false);
		}
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		DriverManager.tearDown();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("context");

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public static boolean isOnTestSuccess() {
		return onTestSuccess;
	}

	public static void setOnTestSuccess(boolean onTestSuccess) {
		ConfigListener.onTestSuccess = onTestSuccess;
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

	}

	/**
	 * Temporary implementation: Need to remove below method once a permanent
	 * solution is found to use assertion in a better way. Below implementation
	 * manipulates the ITestResult if TestNG returns ITestResult.SUCCESS and Extent report return Status.FAIL
	 */
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod() && testResult.getStatus() == ITestResult.SUCCESS
				&& ExtentManager.getExtentTest().getStatus().equals(Status.FAIL)) {
			testResult.setStatus(ITestResult.FAILURE);
		}

	}

}
