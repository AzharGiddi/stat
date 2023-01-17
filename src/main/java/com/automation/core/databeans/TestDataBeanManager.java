package com.automation.core.databeans;

public final class TestDataBeanManager {

	private static ThreadLocal<TestDataBean> testData = new ThreadLocal<>();
	
	public static TestDataBean getTestData() {
		return testData.get();
	}

	

	public static void setTestData(TestDataBean testDataBean) {
		testData.set(testDataBean);
	}
	
	public static void unload() {
		testData.remove();
	  }


	private TestDataBeanManager() {
		
	}

}
