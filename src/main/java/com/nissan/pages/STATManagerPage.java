package com.nissan.pages;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class STATManagerPage extends BasePage{

	@FindBy(xpath = "//img[@name='pyPortalHeader_pyDisplayHarness_1']")
	public ExtWebElement logoNissan;
	
	@FindBy(xpath = "//a[@id='appview-nav-toggle-one']")
	public ExtWebElement linkHamburgerMenu;
	
	@FindBy(xpath = "//a//span[text()='MQ Message Search']")
	public ExtWebElement linkMQMessageSearch;
	
	
	public void waitForPageToLoad() {
		try {

			logoNissan.waitForPresent();
			closeErrorMessageAndOpenTabs();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ logoNissan.toString()));

		}
	}
	
	
	
	public STATManagerPage() {
		
		
		ElementFactory.initElements(DriverManager.getDriver(), this);
		
	}
	
}
