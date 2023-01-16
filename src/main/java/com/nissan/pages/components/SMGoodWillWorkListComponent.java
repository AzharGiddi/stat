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

public class SMGoodWillWorkListComponent extends ExtWebComponent {

	@FindBy(xpath = "//table[@pl_prop='D_OpenGWAssignments.pxResults']//*[text()='Case ID']/parent::div/following-sibling::span/a[@id='pui_filter']")
	public ExtWebElement buttonFilterCaseID;
	
	@FindBy(xpath = "//table[@pl_prop='D_OpenGWAssignments.pxResults']//a[contains(@name,'pxResults')]")
	public ExtWebElement linkGWCase;
	
	@Override
	public void filterAndClick(String columnName, String filterText, String eleText) {

		String colHeaderXpath = String.format("//table[@pl_prop='D_OpenGWAssignments.pxResults']//*[text()='%s']/parent::div/following-sibling::span/a[@id='pui_filter']", columnName);
		String eleToBeClicked = String.format("//table[@pl_prop='D_OpenGWAssignments.pxResults']//a[contains(@name,'pxResults')  and text()='%s']", eleText);

		getExtWebElement(colHeaderXpath).click();
		txtboxSearchText.clearAndSendKeys(filterText);
		btnApply.click();
		//WaitUtil.sleep(2000);
		getExtWebElement(eleToBeClicked).scrollAndClick();
	}
	
	
	public void filterAndClick(String caseId) {

		buttonFilterCaseID.scrollAndClick();
		txtboxSearchText.waitForPresent(2000);
		txtboxSearchText.clearAndSendKeys(caseId + Keys.TAB);
		btnApply.scrollAndClick();
		WaitUtil.sleep(2000);
		linkGWCase.scrollAndClick();
	}

	@Override
	public void waitForComponentToLoad() {
		try {
			waitForFrameToLoad();
		} catch (Exception e) {
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));
		}
	}

	public SMGoodWillWorkListComponent() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		waitForComponentToLoad();
	}
}
