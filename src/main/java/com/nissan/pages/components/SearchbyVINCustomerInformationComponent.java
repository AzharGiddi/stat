package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class SearchbyVINCustomerInformationComponent extends ExtWebComponent{

	@FindBy(xpath="//div[text()='Search by VIN / Customer Information']")
	public ExtWebElement header;
	
	@FindBy(xpath="//button[text()='Select']")
	public ExtWebElement btnSelect;
	
	@FindBy(xpath="//input[@name='$PpyWorkPage$pSearchByVIN']")
	public ExtWebElement txtboxVIN;
	
	@FindBy(xpath="//button[text()='Search By VIN']")
	public ExtWebElement btnSearchByVIN;
	
	@FindBy(xpath="//div[starts-with(text(),'Thank you')]")
	public ExtWebElement txtThankYou;
	
	public void waitForComponentToLoad() {
		try {
			waitForFrameToLoad();
			header.waitForPresent();
			ExtentLogger.passWithScreenShot(this.getClass().getSimpleName() + " loaded successfully.");
			} catch (Exception e) {

				ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

			}
		
		
	}
	
	public SearchbyVINCustomerInformationComponent() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		waitForComponentToLoad();
		
	}
	
}
