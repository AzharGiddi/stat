package com.automation.core.reports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentManager {

	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	public static ExtentTest getExtentTest() {
		return extentTest.get();
	}

	static void setExtentTest(ExtentTest testRef) {
		extentTest.set(testRef);
	}

	static void unload() {
		extentTest.remove();
	}

	private ExtentManager() {

	}
}
