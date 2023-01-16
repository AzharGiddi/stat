package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class FeedbackCommentsHRKConnectorLookupComponent extends ExtWebComponent{

	@FindBy(xpath = "//span[@id='modaldialog_hd_title' and normalize-space(text())='Feedback Comments']")
	public ExtWebElement header;
	
	@FindBy(xpath = "//textarea[@name='$PFeedbackComments$pCustomerComments']")
	public ExtWebElement txtboxAdditionalComments;
	
	@FindBy(xpath = "//input[@name='$PpyAttachmentPage$ppyTemplateInputBox']")
	public ExtWebElement inputSelectFile;
	
	@FindBy(xpath = "//button[normalize-space(text())='Submit']")
	public ExtWebElement btnSubmit;
	
	@FindBy(xpath = "//button[@ title='Click here to cancel work']")
	public ExtWebElement btnCancel;
	
	@FindBy(xpath = "//div[starts-with(text(),'For immediate assistance')]")
	public ExtWebElement txtForImmediateAssistance;
	
	
	
	public ExtWebElement getElement(String fieldName) {
		
		String xPath = String.format("//label[normalize-space(text())='%s']/following-sibling::div//input", fieldName);
		
		return ExtWebComponent.getExtWebElement(xPath);
		
	}
	
	
	
	
	public void waitForComponentToLoad() {
		try {
			waitForFrameToLoad();
			//WaitUtil.sleep(2000);
			header.waitForPresent();
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}
	}
	
	public FeedbackCommentsHRKConnectorLookupComponent() {
		waitForComponentToLoad();
	}
}
