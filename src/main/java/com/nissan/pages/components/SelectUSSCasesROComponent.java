package com.nissan.pages.components;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class SelectUSSCasesROComponent extends ExtWebComponent{

	
	@FindBy(xpath="//h6[contains(text(),'USS Cases By RO')]")
	public ExtWebElement header;
	
	
	public void filterAndClick(String columnName, String filterText) {

		String colHeaderXpath = String
				.format("//*[text()='%s']/parent::div/following-sibling::span/a[@id='pui_filter']", columnName);
		String eleToBeClicked = String.format(
				"//span[text()='%s']//ancestor::td[1]//following-sibling::td//child::div/span/a",
				filterText);

		getExtWebElement(colHeaderXpath).click();
		// btnApply.scrollIntoView();
		txtboxSearchText.waitForPresent(2000);
		txtboxSearchText.clearAndSendKeys(filterText + Keys.TAB);
		//WaitUtil.sleep(500);
		btnApply.scrollAndClick();
		WaitUtil.sleep(1000);
		ExtWebElement eleToClick = getExtWebElement(eleToBeClicked);
		WaitUtil.sleep(1000);
		eleToClick.click();

	}
	
	@Override
	public void waitForComponentToLoad() {

		try {
			
			waitForFrameToLoad();
			header.waitForPresent();
			//ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					));

		}
	}
	
	
	public SelectUSSCasesROComponent() {
		waitForComponentToLoad();
	}
	
}
