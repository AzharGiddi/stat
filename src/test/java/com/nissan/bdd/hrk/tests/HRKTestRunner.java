package com.nissan.bdd.hrk.tests;

import com.nissan.base.CucumberTestRunner;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "resources/features/HRKFeatures.feature",
				glue = "com.nissan.bdd.fone.stepdefinitions",
				plugin = {"pretty", "html:target/cucumber-reports" },
				monochrome = false, 
				dryRun = false,
				tags= "@Regression and @HRK21TC04")
public class HRKTestRunner extends CucumberTestRunner {

	
	
	
}
