package com.nissan.utils;

import java.util.List;
import java.util.Objects;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.reports.ExtentLogger;

public class ResolveGWCaseUtil {

	private ResolveGWCaseUtil() {
		
		
	}
	
	public static void resolveGWCase(String caseId) {
		
		String activityUrl = ConfigurationManager.getBundle().getString("app.url")+"PRAuth/Automation?pyActivity=NSA-STAT-Work-GoodWill.ResolveGWCase_AT&ID="+caseId;		
		
		DriverManager.getDriver().get(activityUrl);
		
		String status = ExtWebComponent.getExtWebElement("//label[text()='Status:']/parent::div").getText();
		
		if (!status.contains("good")) {
			throw new CustomRuntimeException("Error in resolving goodwill case: "+caseId);
		}
		
		String caseStatus =GetGWCaseDetailsUtil.getCaseStatus(caseId);
		
		if(caseStatus.equals("Resolved-ATClosed")) {
			ExtentLogger.info(caseId+" is Resolved-Closed", false);
		}
		
		
		
	}
	
public static void resolveGWCases(List<String> caseIdList) {
		
		for(String caseID: caseIdList) {
			if(Objects.nonNull(caseID)) {
				resolveGWCase(caseID);
			}
		}
		
		
	}
	
	
	
	
	
	
}
