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

public class CAManagerGWCasesComponent extends ExtWebComponent{

	@FindBy(xpath="//div[text()='Goodwill Cases']")
	public ExtWebElement headerGoodWillCases;
	
	@FindBy(xpath = "//button[text()='Apply']")
	protected ExtWebElement btnApply;
	
	@FindBy(xpath = "//label[text()='Search Text']/following-sibling::div/span/input")
	protected ExtWebElement txtboxFilterSearchText;
	
	@FindBy(xpath="//button[text()='Continue Without Goodwill Case']")
	public ExtWebElement btnContinueWithGoodwillCase;
	
	@Override
	public void waitForComponentToLoad() {
		try {
		waitForFrameToLoad();
		headerGoodWillCases.waitForPresent();
		ExtentLogger.passWithScreenShot(this.getClass().getSimpleName() + " loaded successfully.");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}
		
	}
	
	@Override
	public void filterAndClick(String columnName, String filterText, String eleText) {

		String colHeaderXpath = String
				.format("//*[text()='%s']/parent::div/following-sibling::span/a[@id='pui_filter']", columnName);
		String eleToBeClicked = String.format(
				"//span[text()='%s']//ancestor::td[1]//following-sibling::td//child::div/span/*[text()='%s']", filterText,
				eleText);
		WaitUtil.sleep(2000);
		getExtWebElement(colHeaderXpath).click();
		//btnApply.scrollIntoView();
		WaitUtil.sleep(2000);
		txtboxFilterSearchText.clearAndSendKeys(filterText+Keys.TAB);
		WaitUtil.sleep(2000);
		btnApply.scrollAndClick();
		WaitUtil.sleep(2000);
		getExtWebElement(eleToBeClicked).scrollAndClick();
	}
	
	
	public CAManagerGWCasesComponent() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		//waitForComponentToLoad();
	}
}
