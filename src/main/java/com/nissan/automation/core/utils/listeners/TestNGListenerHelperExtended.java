package com.nissan.automation.core.utils.listeners;

import java.util.List;

import org.testng.IConfigurationListener;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

public class TestNGListenerHelperExtended {

	public static void runPostConfigurationListeners(ITestResult tr, List<IConfigurationListener> listeners) {
        for (IConfigurationListener icl : listeners) {
            switch (tr.getStatus()) {
                case ITestResult.SKIP:
                    icl.onConfigurationSkip(tr);
                    break;
                case ITestResult.FAILURE:
                    icl.onConfigurationFailure(tr);
                    break;
                case ITestResult.SUCCESS:
                    icl.onConfigurationSuccess(tr);
                    break;
                default:
                    throw new AssertionError("Unexpected value: " + tr.getStatus());
            }
        }
    }
	
	public static void runTestListeners(ITestResult tr, List<ITestNGListener> listeners) {
	        for (ITestNGListener itl : listeners) {
	            switch (tr.getStatus()) {
	                case ITestResult.SKIP:
	                    ((ITestListener) itl).onTestSkipped(tr);
	                    break;
	                case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
	                    ((ITestListener) itl).onTestFailedButWithinSuccessPercentage(tr);
	                    break;
	                case ITestResult.FAILURE:
	                    ((ITestListener) itl).onTestFailure(tr);
	                    break;
	                case ITestResult.SUCCESS:
	                    ((ITestListener) itl).onTestSuccess(tr);
	                    break;
	                case ITestResult.STARTED:
	                    ((ITestListener) itl).onTestStart(tr);
	                    break;
	                default:
	                    throw new AssertionError("Unknown status: " + tr.getStatus());
	            }
	        }
	    }
	 
	
	
}
