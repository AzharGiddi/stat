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

public class SelectRepairOrderComponent extends ExtWebComponent {

	@FindBy(xpath = "//h6[contains(text(),'Select the repair order')] | //h6[contains(text(),'Select the Repair Order')]")
	public ExtWebElement header;

	@FindBy(xpath = "(//span[@class='errorText']//li)[1]")
	public ExtWebElement txtConcernedMappedToOtherROErrorMsg;

	@FindBy(xpath = "//*[text()='RO# / WO#']/parent::div/following-sibling::span/a[@id='pui_filter']")
	public ExtWebElement buttonFilterRO;

	@FindBy(xpath = "//button[text()='Select']")
	public ExtWebElement btnSelect;

	public void filterAndClick(String roNumber) {
		
		buttonFilterRO.click();
		txtboxSearchText.waitForPresent(2000);
		txtboxSearchText.clearAndSendKeys(roNumber + Keys.TAB);
		btnApply.click();
		WaitUtil.sleep(2000);
		btnSelect.click();
		
	}

	@Override
	public void waitForComponentToLoad() {

		try {

			waitForFrameToLoad();
			header.waitForPresent();
			//ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "));

		}
	}

	public SelectRepairOrderComponent() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		waitForComponentToLoad();
	}

}
