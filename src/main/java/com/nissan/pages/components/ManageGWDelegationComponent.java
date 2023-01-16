package com.nissan.pages.components;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class ManageGWDelegationComponent extends ExtWebComponent{

	
	@FindBy(xpath = "//h2[text()='Please Select Delegated Users']")
	public ExtWebElement headerPleaseSelectDelegatedUsers;
	
	@FindBy(xpath = "//button[text()='Save']")
	public ExtWebElement btnSave;
	
	
	
	public void filterAndClick(String columnName, String filterText) {

		String colHeaderXpath = String
				.format("//*[text()='%s']/parent::div/following-sibling::span/a[@id='pui_filter']", columnName);
		String eleToBeClicked = String.format(
				"//tr[contains(@oaargs,'%s')]//input[@type='checkbox']", filterText);

		getExtWebElement(colHeaderXpath).click();
		btnApply.scrollIntoView();
		txtboxSearchText.waitForPresent(2000);
		txtboxSearchText.clearAndSendKeys(filterText+Keys.TAB);
		//WaitUtil.sleep(2000);
		btnApply.scrollAndClick();
		WaitUtil.sleep(2000);
		ExtWebElement ele = getExtWebElement(eleToBeClicked); 
		if(ele.isSelected())
			return;
		ele.scrollAndClick();
	}
	
	@Override
	public void waitForComponentToLoad() {
		try {
			waitForFrameToLoad();
			headerPleaseSelectDelegatedUsers.waitForPresent();
		} catch (Exception e) {
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));
		}
	}
	
	public ManageGWDelegationComponent() {
		
		waitForComponentToLoad();
		
	}
	
	
}
