package com.nissan.pages.components;

import java.util.List;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class HRKConnectorInformationComponent extends ExtWebComponent{
		
		@FindBy(xpath = "//button[text()='General Repair Instructions']")
		public ExtWebElement btnGeneralRepairInstructions;
		
		@FindBy(xpath = "//button[text()='Tools and Connector Disassembly']")
		public ExtWebElement btnConnectorDisassemblyInstructions;
		
		@FindBy(xpath = "//button[contains(text(),'Required') and contains(text(),'Related Parts')]")
		public ExtWebElement btnRequiredRelatedParts;
		
		@FindBy(xpath = "//div[text()='Kit Description']/following-sibling::div//img")
		public ExtWebElement infoIconKitDescription;
		
		@FindBy(xpath = "//div[text()='AWG Wire']/following-sibling::div//img")
		public ExtWebElement infoIconAWGWire;
		
		@FindBy(xpath = "//div[text()='Solder Sleeve']/following-sibling::div//img")
		public ExtWebElement infoIconSolderSleeve;
		
		@FindBy(xpath="//div[@node_name='DisplayConnectorInformation']//div[contains(@class,'content-item content-label item') and @string_type='label']")
		public List<ExtWebElement> listConnectorInfoFields;
		
		public ExtWebElement getConnectorInfoField(String fieldName) {
			
			String xpath=String.format("//div[text()='%s']", fieldName);
			ExtWebElement ele = getExtWebElement(xpath);
			ele.scrollIntoView();
			return ele;
			
			}
		
		
		
		
		public void waitForComponentToLoad() {

			try {
				
				waitForFrameToLoad();
				
			} catch (Exception e) {

				ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

			}

		}
		public HRKConnectorInformationComponent() {
			
			waitForComponentToLoad();
		}
		
		
	
}
