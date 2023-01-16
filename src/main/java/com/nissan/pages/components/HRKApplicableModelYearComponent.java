package com.nissan.pages.components;

import java.util.List;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class HRKApplicableModelYearComponent extends ExtWebComponent{
		
		@FindBy(xpath = "//div[@node_name='ApplicableModelCodeList']")
		public List<ExtWebElement> listApplicableModels;
		
		
		
		
		
		
		public void waitForComponentToLoad() {

			try {
				
				waitForFrameToLoad();
				
			} catch (Exception e) {

				ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

			}

		}
		public HRKApplicableModelYearComponent() {
			
			waitForComponentToLoad();
		}
		
		
	
}
