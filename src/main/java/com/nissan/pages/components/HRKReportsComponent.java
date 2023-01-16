package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class HRKReportsComponent extends ExtWebComponent{

	@FindBy(xpath = "//div[text()='Report']")
	public ExtWebElement headerReport;
	
	public void waitForComponentToLoad() {

		try {
			waitForFrameToLoad();
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}

	}

	public HRKReportsComponent() {
		waitForComponentToLoad();
	}
	
	
}
