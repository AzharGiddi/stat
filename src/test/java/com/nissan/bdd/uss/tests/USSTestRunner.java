package com.nissan.bdd.uss.tests;

import com.nissan.base.CucumberTestRunner;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "resources/features/USS/", glue = "com.nissan.bdd.fone.stepdefinitions", plugin = {
		"pretty", "html:target/cucumber-reports",
		"rerun:target/GWfailedtcs.txt" }, monochrome = true, dryRun = false, tags = "@USS100")

public class USSTestRunner extends CucumberTestRunner {
	// features = "resources/features/GoodWill/",
	// features = "@target/GWfailedtcs.txt",

}
