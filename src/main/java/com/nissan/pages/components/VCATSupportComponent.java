package com.nissan.pages.components;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class VCATSupportComponent extends ExtWebComponent {

	@Override
	public void filterAndClick(String columnName, String filterText, String eleText) {

		String colHeaderXpath = String.format("//*[text()='%s']/parent::div/following-sibling::span/a[@id='pui_filter']", columnName);
		String eleToBeClicked = String.format("//a[contains(@name,'pxResults')  and text()='%s']", eleText);

		getExtWebElement(colHeaderXpath).click();
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

	public VCATSupportComponent() {

		waitForComponentToLoad();
	}
}
