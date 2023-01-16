package com.nissan.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.FrameNotFoundException;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.BasePage;
import com.nissan.reports.ExtentLogger;

public class ServiceMQComponent extends ExtWebComponent{

	@FindBy(xpath = "//td/span[text()='Service MQ']")
	public ExtWebElement tabServiceMQ;
	
	@FindBy(xpath = "//iframe[@title='Service MQ']")
	public ExtWebElement iframe;
	
	
	
	
	
	public void waitForComponentToLoad() {
		
		try {
			
			waitForFrameToLoad();
			
		ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch(NoSuchFrameException e) {
			throw new FrameNotFoundException(
					this.getClass().getSimpleName()+" did not load, Waited 20 seconds for the visibility of the element located by xpath: "
							+ tabServiceMQ.toString(),
					e);
		}catch (Exception e) {

			throw new PageNotFoundException(
					this.getClass().getSimpleName()+" did not load, Waited 20 seconds for the visibility of the element located by xpath: "
							+ tabServiceMQ.toString(),
					e);

		}
		
	}
	
	public void openRuleSet(String ruleSetVersion) {
		
		String xpath = String.format("//div[text()='CreateUpdateRepairOrder ']/parent::td/following-sibling::td/child::div[contains(text(),'%s')]",ruleSetVersion);
		
		
		ExtWebElement extWebElement = ExtWebComponent.getExtWebElement(xpath);
		
		extWebElement.click();
	}
	
	
}
