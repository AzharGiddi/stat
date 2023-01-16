package com.nissan.pages.components;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class DatabaseQueryRunnerComponent extends ExtWebComponent {

	@FindBy(xpath = "//iframe[@title='System: Database']")
	public ExtWebElement iframe;

	@FindBy(xpath = "//textarea[@name='$PpyLanding$ppzSQLQueryToRun']")
	public ExtWebElement txtboxQueryToRun;

	@FindBy(xpath = "//button[text()='Run']")
	public ExtWebElement btnRun;

	@FindBy(xpath = "//span[@data-test-id='20160701062717023179568']")
	public ExtWebElement txtResultCount;

	@FindBy(xpath = "//tr[@id='Grid_NoResults']//div[text()='No items'] | //span[@data-test-id='20160701062717023179568']")
	public ExtWebElement txtResult;

	@FindBy(xpath = "//button[@id='close']")
	public ExtWebElement btnClose;
	
	@FindBy(xpath = "//div[text()='Refresh']")
	public ExtWebElement btnRefresh;

	@Override
	public void waitForComponentToLoad() {

		try {
			DriverManager.getDriver().switchTo().defaultContent();
			DriverManager.getDriver().switchTo().frame(iframe);
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ iframe.toString()));

		}

	}

	public String runQuery(String query) {
		
		txtboxQueryToRun.clearAndSendKeys(query+Keys.TAB);
		btnRun.click();
		WaitUtil.sleep(5000);
		txtResult.scrollIntoView();
		return txtResult.getText();

	}
	
	
	
	
}
