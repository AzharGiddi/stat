package com.nissan.pages.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;

import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class UpdateVCANComponent extends ExtWebComponent {

	@FindBy(xpath = "//div/span[normalize-space(text())='Update VCAN']")
	public ExtWebElement headerUpdateVCAN;

	@FindBy(xpath = "//input[@name='$PpyWorkPage$pUpdateVCANDetails$pLaborAmount']")
	public ExtWebElement txtboxLaborAmount;

	@FindBy(xpath = "//input[@name='$PpyWorkPage$pUpdateVCANDetails$pExpenseAmount']")
	public ExtWebElement txtboxExpenseAmount;
	
	@FindBy(xpath = "//input[@name='$PpyWorkPage$pUpdateVCANDetails$pExpenseAmount']")
	public WebElement txtboxExpenseAmountWebElement;
	
	
	@FindBy(xpath = "//span[text()='Total Amount']/following-sibling::div//span[@data-ctl='Text']")
	public WebElement txtTotalAmount;

	@FindBy(xpath = "//input[@name='$PpyWorkPage$pUpdateVCANDetails$pPartsAmount']")
	public ExtWebElement txtboxPartAmount;

	@FindBy(xpath = "//textarea[@name='$PpyWorkPage$pUpdateVCANDetails$pComments']")
	public ExtWebElement txtboxComments;

	@FindBy(xpath = "//button[normalize-space(text())='Submit']")
	public ExtWebElement btnSubmit;

	@Override
	public void waitForComponentToLoad() {
		try {
			headerUpdateVCAN.waitForVisible();
			// waitForFrameToLoad();
		} catch (Exception e) {
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));
		}
	}

	public UpdateVCANComponent() {

		waitForComponentToLoad();
	}
}
