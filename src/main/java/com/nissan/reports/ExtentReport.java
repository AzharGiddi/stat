package com.nissan.reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.nissan.automation.core.utils.PropertyUtil;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.constants.NAFConstants;
import com.nissan.exceptions.RuntimeIOException;

public class ExtentReport {

	private static ExtentReports extent;
	private static ExtentTest test;
	
	public static void initReports() {
		if (Objects.isNull(extent)) {
			extent = new ExtentReports();
			ExtentSparkReporter spark = new ExtentSparkReporter(NAFConstants.getExtentReportFilePath());
			extent.attachReporter(spark);
			spark.config().setTheme(Theme.DARK);
			spark.config().setDocumentTitle("NISSAN STAT Automation Report");
			spark.config().setEncoding("utf-8");
			String env = ConfigurationManager.getBundle().getString("app.env");
			spark.config().setReportName("Automation Report "+env);
		}

	}
	
	public static void initReports(String reportName) {
		if (Objects.isNull(extent)) {
			extent = new ExtentReports();
			ExtentSparkReporter spark = new ExtentSparkReporter(NAFConstants.getExtentReportFilePath(reportName));
			extent.attachReporter(spark);
			spark.config().setTheme(Theme.DARK);
			spark.config().setDocumentTitle("NISSAN STAT Automation Report");
			spark.config().setEncoding("utf-8");
			String env = ConfigurationManager.getBundle().getString("app.env");
			spark.config().setReportName("Automation Report "+env);
		}

	}


	public static void flushReports() {
		if (Objects.nonNull(extent)) {
			extent.flush();
			try {
				if (ConfigurationManager.getBundle().getString("local.execution").equals("true"))
					Desktop.getDesktop().browse(new File(NAFConstants.getExtentReportFilePath()).toURI());
			} catch (IOException e) {

				e.printStackTrace();
				throw new RuntimeIOException(e.getLocalizedMessage());
			}
		}

	}

	public static void createTest(String testName) {
		
			
			ExtentManager.setExtentTest(extent.createTest(testName));
		
		
	}
	
	public static void createTest(String testName, boolean isBDD) {
		
		if(isBDD) {
			createBDDTest(testName);
		}else {
		createTest(testName);
		}	
	
	
}
	
	public static void createBDDTest(String testName) {
		
		
		try {
			ExtentManager.setExtentTest(extent.createTest(new GherkinKeyword("Feature"), "BDD"));
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			
		}
	
	
}

	private ExtentReport() {
	}
}
