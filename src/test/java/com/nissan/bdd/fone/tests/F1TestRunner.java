package com.nissan.bdd.fone.tests;


import org.testng.annotations.DataProvider;

import com.nissan.base.CucumberTestRunner;


import io.cucumber.testng.CucumberOptions;
 //resources/features/dummy.feature
@CucumberOptions(tags = " @Regression and @Dummy", features = "resources/features/f1Legacy/"
	, plugin = {"pretty", "html:target/cucumber-reports", "rerun:target/F1failedtcs.txt"},
	glue = "com.nissan.bdd.fone.stepdefinitions",dryRun=false)
 public class F1TestRunner extends CucumberTestRunner{

	//features = "resources/features/f1Legacy/"
	//features = "@target/GWfailedtcs.txt"
	
}
