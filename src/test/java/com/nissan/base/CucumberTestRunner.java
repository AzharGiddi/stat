package com.nissan.base;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;

public class CucumberTestRunner extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {

		Object[][] scenarios = super.scenarios();
		setScenarioCount(scenarios.length);
		setScenarios(scenarios);
		String str = scenarios.length>0?scenarios[0][0].toString():"";
		return scenarios;

	}

	public static Object[][] getScenarios() {
		return scenarios;
	}

	public static void setScenarios(Object[][] scenarios) {
		CucumberTestRunner.scenarios = scenarios;
	}

	private static int scenarioCount;

	private static Object[][] scenarios;
	
	public static int getScenarioCount() {
		return scenarioCount;
	}

	public static void setScenarioCount(int scenarioCount) {
		CucumberTestRunner.scenarioCount = scenarioCount;
	}

}
