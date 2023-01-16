package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class AllDealerCasesComponent extends ExtWebComponent {

	@FindBy(xpath = "//input[@name='$PpyDisplayHarness$pSearchCaseID']")
	public ExtWebElement txtboxCaseID;
	
	@FindBy(xpath = "//button[text()='Search By Case ID']")
	public ExtWebElement btnSearchByCaseId;

	@Override
	public void filterAndClick(String columnName, String filterText, String eleText) {

		String colHeaderXpath = String.format("//*[text()='%s']/parent::div/following-sibling::span/a[@id='pui_filter']", columnName);
		String eleToBeClicked = String.format("//a[contains(@name,'pxResults')  and text()='%s']", eleText);

		getExtWebElement(colHeaderXpath).scrollAndClick();
		txtboxSearchText.clearAndSendKeys(filterText);
		btnApply.click();
		WaitUtil.sleep(2000);
		getExtWebElement(eleToBeClicked).scrollAndClick();
	}

	@Override
	public void waitForComponentToLoad() {
		try {
			
			waitForFrameToLoad();
		} catch (Exception e) {
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));
		}
	}

	public AllDealerCasesComponent() {

		waitForComponentToLoad();
	}
}
