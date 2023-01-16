package com.nissan.pages.components;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class PendingTechActionsComponent extends ExtWebComponent{

	@FindBy(xpath = "//div[@aria-hidden='false']//iframe[@title]")
	public ExtWebElement iframe;
	
	@FindBy(xpath = "//h6[text()='Pending Tech Action']")
	public ExtWebElement headerPendingTechAction;
	
	@FindBy(xpath = "//a[contains(@name,'OpenCases_D_OpenCases')]")
	public ExtWebElement linkDCase;
	
	@FindBy(xpath = "//*[text()='RO# / WO#']/parent::div/following-sibling::span/a[@id='pui_filter']")
	public ExtWebElement buttonFilterRO;
	
	@FindBy(xpath = "//*[text()='Case ID']/parent::div/following-sibling::span/a[@id='pui_filter']")
	public ExtWebElement buttonFilterCaseID;
	
	@Override
	public void waitForComponentToLoad() {
		try {
			/*driver.switchTo().defaultContent();
			driver.switchTo().frame(iframe);
			headerPendingTechAction.waitForPresent();
			headerPendingTechAction.waitForVisible();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");*/
			waitForFrameToLoad();
		} catch (Exception e) {
			
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));
			
		}
	}
	
	public void filterAndClick(String dcaseId) {

		
		buttonFilterCaseID.click();
		txtboxSearchText.waitForPresent(2000);
		txtboxSearchText.clearAndSendKeys(dcaseId + Keys.TAB);
		btnApply.scrollAndClick();
		WaitUtil.sleep(2000);
		linkDCase.scrollAndClick();

	}
	
	public PendingTechActionsComponent() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		waitForComponentToLoad();
	}
	
}
