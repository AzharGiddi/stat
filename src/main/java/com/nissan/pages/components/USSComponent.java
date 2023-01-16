package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class USSComponent extends ExtWebComponent{

	@FindBy(xpath = "//div[text()='Customer Concerns']")
	public ExtWebElement headerCustomerConcerns;
	
	@FindBy(xpath = "//input[@name='$PpyWorkPage$pVIN']")
	public ExtWebElement txtBoxVIN;
	
	@FindBy(xpath = "//button[text()='Go']")
	public ExtWebElement btnGo;
	
	@FindBy(xpath = "//button[text()='Add a Concern']")
	public ExtWebElement btnAddAConcern;
	
	@FindBy(xpath = "//a[text()='[ I See ]']")
	public ExtWebElement linkISee;
	
	@FindBy(xpath = "//label[text()='Poor Appearance']/preceding-sibling::input[@type='checkbox']")
	public ExtWebElement chkboxPoorAppearance;
	
	@FindBy(xpath = "//button[normalize-space(text())='OK' and @class='buttonTdButton']")
	public ExtWebElement btnOk;
	
	@FindBy(xpath = "//button[text()='Add to Summary']")
	public ExtWebElement btnAddToSUmaryScreen;
	
	@FindBy(xpath = "//button[normalize-space(text())='Next']")
	public ExtWebElement btnNext;
	
	//@FindBy(xpath = "//select[contains(@name='$PpyWorkPage$pUSSCustomerVehicleConcern']")
	@FindBy(xpath = "//select[contains(@name,'$PpyWorkPage$pUSSCustomerVehicleConcern')]")
	public ExtWebElement dropDownPaytype;
	
	@FindBy(xpath = "//button[text()='DONE']")
	public ExtWebElement btnDone;
	
	@FindBy(xpath = "//div[text()='Congratulations!']")
	public ExtWebElement txtCongratulations;
	
	
	
	
	
	
	
	

	
	@Override
	public void waitForComponentToLoad() {
		try {
			waitForFrameToLoad();
			headerCustomerConcerns.waitForVisible();
			
		} catch (Exception e) {
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));
		}
	}
	
	
	public USSComponent() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		waitForComponentToLoad();
	}
}
