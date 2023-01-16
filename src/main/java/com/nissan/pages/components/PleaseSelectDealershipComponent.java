package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class PleaseSelectDealershipComponent extends ExtWebComponent {

	@FindBy(xpath = "//div[starts-with(@class,' flex content layout-content-stacked')][1]")
	public ExtWebElement componentPleaseSelectDealership;

	@FindBy(xpath = "//div[text()='Please select the Dealership']")
	public ExtWebElement txtDashboardHeader;

	@FindBy(xpath = "//div[text()='Dealer Code :']")
	public ExtWebElement txtDealerCode;

	@FindBy(xpath = "//button[text()='Set Dealer']")
	public ExtWebElement btnSetDealer;

	@FindBy(xpath = "//div[text()='The current Dealership is :']")
	public ExtWebElement txtCurrentDealer;

	@FindBy(xpath = "//span[@data-template and @class]")
	public ExtWebElement txtCurrentDealerCode;

	public void waitForComponentToLoad() {

		try {
			DriverManager.getDriver().switchTo().defaultContent();
			componentPleaseSelectDealership.waitForPresent();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ componentPleaseSelectDealership.toString()));

		}
	}

	public PleaseSelectDealershipComponent() {
		waitForComponentToLoad();
	}
}
