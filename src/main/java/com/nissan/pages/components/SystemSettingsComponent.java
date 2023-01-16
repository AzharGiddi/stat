package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class SystemSettingsComponent extends ExtWebComponent{

	
	@FindBy(xpath = "//a[text()='Manage Goodwill Delegation']")
	public ExtWebElement linkManageGWDelegation;
	
	
	@Override
	public void waitForComponentToLoad() {
		try {
			waitForFrameToLoad();
		} catch (Exception e) {
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));
		}
	}
	
	public SystemSettingsComponent() {
		
		waitForComponentToLoad();
		
	}
	
	
}
