package com.nissan.pages.components;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class FOMMyWorkListComponent extends ExtWebComponent {

	@FindBy(xpath = "//a[contains(@name,'MyWorklistGW_D_GoodwillWorklist')]")
	public ExtWebElement linkRequestId;
	
	@FindBy(xpath = "//*[text()='Request ID']/parent::div/following-sibling::span/a[@id='pui_filter']")
	public ExtWebElement buttonFilterRequestID;

	
	//Request ID
	@Override
	public void filterAndClick(String columnName, String filterText, String eleText) {

		String colHeaderXpath = String.format("//*[text()='%s']/parent::div/following-sibling::span/a[@id='pui_filter']", columnName);
		String eleToBeClicked = String.format("//a[contains(@name,'pxResults')  and text()='%s']", eleText);

		getExtWebElement(colHeaderXpath).click();
		txtboxSearchText.clearAndSendKeys(filterText);
		btnApply.click();
		WaitUtil.sleep(2000);
		getExtWebElement(eleToBeClicked,eleText).scrollAndClick();
	}
	
public void filterAndClick(String caseId) {

		
		buttonFilterRequestID.click();
		txtboxSearchText.waitForPresent(2000);
		txtboxSearchText.clearAndSendKeys(caseId + Keys.TAB);
		btnApply.scrollAndClick();
		WaitUtil.sleep(2000);
		linkRequestId.scrollAndClick();

	}

	@Override
	public void waitForComponentToLoad() {
		try {
			waitForFrameToLoad();
		} catch (Exception e) {
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));
		}
	}

	public FOMMyWorkListComponent() {

		waitForComponentToLoad();
	}
}
