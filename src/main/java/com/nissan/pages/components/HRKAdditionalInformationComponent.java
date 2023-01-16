package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class HRKAdditionalInformationComponent extends ExtWebComponent{
		
		@FindBy(xpath = "//i[@title='Edit']")
		public ExtWebElement linkEdit;
		
		@FindBy(xpath = "//input[@type='checkbox' and contains(@name,'DelistConnector')]")
		public ExtWebElement chkboxDelistConnector;
		
		@FindBy(xpath = "//button[@title='Cancel changes']")
		public ExtWebElement btnCancel;
		
		@FindBy(xpath = "//button[text()='Save']")
		public ExtWebElement btnSave;
		
		@FindBy(xpath = "//input[contains(@name,'KeyWords')]")
		public ExtWebElement txtBoxKeyword;
		
		@FindBy(xpath = "//div[text()='Keywords']/following-sibling::div/span")
		public ExtWebElement txtBoxKeywordReadOnly;
		
		public ExtWebElement getAddItionalInfoField(String fieldName) {
			
			String xpath=String.format("//div[text()='%s']", fieldName);
			
			return getExtWebElement(xpath);
			
			}
		
		
		
		
		public void waitForComponentToLoad() {

			try {
				
				waitForFrameToLoad();
				
			} catch (Exception e) {

				ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

			}

		}
		public HRKAdditionalInformationComponent() {
			
			waitForComponentToLoad();
		}
		
		
	
}
