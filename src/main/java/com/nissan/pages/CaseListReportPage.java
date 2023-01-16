package com.nissan.pages;

import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

/***
 * 
 * @author AB00789853
 *
 */
public class CaseListReportPage extends BasePage {

	@FindBy(xpath = "//span[text()='Case List Report']")
	public ExtWebElement txtHeader;

	@FindBy(xpath = "//a[text()=' Repair Order Number ']")
	public ExtWebElement linkRepairOrderNumber;

	@FindBy(xpath = "//input[@class='autocomplete_input']")
	public ExtWebElement txtboxRONumber;

	@FindBy(xpath = "//button[text()='Apply changes']")
	public ExtWebElement buttonApplyChanges;

	@FindBy(xpath = "//div[@class='standard_(label)_dataLabelRead']")
	public ExtWebElement txtResultHeader;

	@FindBy(xpath = "//td[@data-attribute-name='Case ID']//div")
	public ExtWebElement txtCaseId;

	@FindBy(xpath = "//td[@data-attribute-name='Case status']//div")
	public ExtWebElement txtCaseStatus;

	@FindBy(xpath = "//td[@data-attribute-name='RO Status']//div")
	public ExtWebElement txtROStatus;

	public void waitForPageToLoad() {

		try {
			// getWindow(textHeader);
			// driver.switchTo().defaultContent();
			txtHeader.waitForPresent();

		} catch (Exception e) {

			throw new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ txtHeader.toString(), e);

		}

	}

	public void getWindow(String window) {

		Set<String> whs = DriverManager.getDriver().getWindowHandles();
		for (String wh : whs) {
			if (wh.equals(window))
				continue;
			DriverManager.getDriver().switchTo().window(wh);
			try {
				if (txtHeader.isDisplayed()) {
					return;
				}
			} catch (NoSuchElementException e) {

				continue;

			}

		}
	}

	public void filter(String roNumber) {
		int time=0;
		int endtime = ConfigurationManager.getBundle().getInt("timout.ro");
		String header = applyFilter(roNumber);
		while (header.equals("Displaying 0 records") && time != endtime) {
			time += 5;
			header = applyFilter(roNumber);
		}
		if (time < 30) {
			// logger.info("RO created with case id: " + textCaseId.getText());
			ExtentLogger.pass(roNumber + " created with case id: " + txtCaseId.getText());
		} else {
			// logger.error("RO creation took longer than expected");
			
			try {
				throw new TimeoutException();
			} catch (TimeoutException e) {

				e.printStackTrace();
				ExtentLogger.fail(new TimeoutException("RO creation took longer than expected"));
				throw new CustomRuntimeException(e.getLocalizedMessage());
			}
		}
	}

	public String applyFilter(String roNumber) {

		linkRepairOrderNumber.click();
		txtboxRONumber.clear();
		txtboxRONumber.clearAndSendKeys(roNumber);
		buttonApplyChanges.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();

		}
		return txtResultHeader.getText();
	}
}
