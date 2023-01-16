package com.nissan.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.lang.StringUtils;

import com.nissan.configuration.ConfigurationManager;

public final class NAFConstants {

	// restrict the creation of object of NAFConstatnts
	private NAFConstants() {

	}

	private static final String USER_DIR = System.getProperty("user.dir");

	private static final String EXTENT_REPORT_FOLDER_PATH = USER_DIR + "\\extent-output";

	private static String extentReportFilePath = "";
	
	private static String SCREENSHOT_PATH="./img";

	public static String getExtentReportFilePath() {
		if (extentReportFilePath.isEmpty()) {
			extentReportFilePath = createExtentReportPath();
		}

		return extentReportFilePath;
	}
	
	public static String getExtentReportFilePath(String reportName) {
		if (extentReportFilePath.isEmpty()) {
			extentReportFilePath = createExtentReportPath(reportName);
		}

		return extentReportFilePath;
	}

	private static String createExtentReportPath() {

		if (ConfigurationManager.getBundle().getString("dynamic.report.name").equalsIgnoreCase("yes")) {
			String dateTime = StringUtils.substringBefore(LocalDateTime.now().toString().replace(":", "_"), ".");
			return EXTENT_REPORT_FOLDER_PATH + "\\" + "extentReport_" +dateTime+ ".html";
		} else {
			return EXTENT_REPORT_FOLDER_PATH + "\\extentReport.html";
		}

	}
	
	private static String createExtentReportPath(String reportName) {

		if (ConfigurationManager.getBundle().getString("dynamic.report.name").equalsIgnoreCase("yes")) {
			String dateTime = StringUtils.substringBefore(LocalDateTime.now().toString().replace(":", "_"), ".");
			return EXTENT_REPORT_FOLDER_PATH + "\\" + "ExecutionReport_"+reportName.replace(" ","_") +dateTime+ ".html";
		} else {
			return EXTENT_REPORT_FOLDER_PATH + "\\extentReport.html";
		}

	}

	public static String getSCREENSHOT_PATH() {
		String dateTime = StringUtils.substringBefore(LocalDateTime.now().toString().replace(":", "_"), ".");
		return SCREENSHOT_PATH+"//Screenshot_"+dateTime;
	}

	public static void setSCREENSHOT_PATH(String sCREENSHOT_PATH) {
		SCREENSHOT_PATH = sCREENSHOT_PATH;
	}

	
	

}
