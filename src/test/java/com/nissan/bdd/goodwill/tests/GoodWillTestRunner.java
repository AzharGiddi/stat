package com.nissan.bdd.goodwill.tests;



import com.nissan.base.CucumberTestRunner;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "resources/features/GoodWill/",
					glue = "com.nissan.bdd.fone.stepdefinitions", 
					plugin = {"pretty", "html:target/cucumber-reports", "rerun:target/GWfailedtcs.txt" }, 
					monochrome = true, 
					dryRun = false, 
					tags = "@TestDUmmy and @Warranty")

public class GoodWillTestRunner extends CucumberTestRunner {
	//features = "resources/features/GoodWill/",
	//features = "@target/GWfailedtcs.txt",
	
}
