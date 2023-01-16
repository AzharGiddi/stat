package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.WebElement.Validator;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class HotspotComponent extends ExtWebComponent{

	@FindBy(xpath="//*[@id='psv-marker-audio']")
	public ExtWebElement touchScreenArea;
	
	@FindBy(xpath="//*[text()='Component Details']")
	public ExtWebElement checkBoxComponentHeader;
	
	
	
	public void waitForComponentToLoad() {
		checkBoxComponentHeader.scrollIntoView();
		checkBoxComponentHeader.waitForPresent();
		checkBoxComponentHeader.waitForVisible();
		try {
		//Validator.assertText(checkBoxComponentHeader.getText().trim(), "checkBoxHeader");
		}catch(Exception e){
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getName()+" did not load"));
		}
	}
	
	
	public HotspotComponent() {
		waitForComponentToLoad();
	}
	
}
