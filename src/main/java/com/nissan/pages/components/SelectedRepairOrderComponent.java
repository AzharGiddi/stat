package com.nissan.pages.components;

import java.util.List;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class SelectedRepairOrderComponent extends ExtWebComponent{

	@FindBy(xpath = "//button[text()='Skip ROL']")
	public ExtWebElement btnSkipROL;
	
	@FindBy(xpath = "//button[text()='Select']")
	public List<ExtWebElement> btnSelectList;
	
	@FindBy(xpath = "//h3[text()='Selected Repair Order']")
	public ExtWebElement header;
	
	@FindBy(xpath = "//button[text()='Continue without Coupon']")
	public ExtWebElement btnContinueWithoutCoupon;
	
	@FindBy(xpath = "//span/ancestor::td//input[@type='hidden' and contains(@name,'$PpyDisplayHarness$pDCaseCustomerConcernList') and not(@disabled)]/following-sibling::input")
	public ExtWebElement chkboxCustomerCOncern;
	
	@FindBy(xpath = "//button[text()='Go']")
	public ExtWebElement btnGO;
	
	@FindBy(xpath = "//a[contains(@name,'CustomerConcerns_pyDisplayHarness.DCaseCustomerConcernList')]")
	public List<ExtWebElement> listDcase;
	
	
	
	
	
	public void selectROL(int index) {
		
		btnSelectList.get(index).scrollAndClick();
	}
	
	public void selectCoupon(boolean applyCoupon) {
		
		
		if(btnContinueWithoutCoupon.isPresent()) {
			if(applyCoupon) {
			btnSelectList.get(0).click();
			}else {
				btnContinueWithoutCoupon.click();
			}
			
		}
		
		SelectedRepairOrderComponent selectRepairOrderComponent	 = new SelectedRepairOrderComponent();
		selectRepairOrderComponent.btnSelectList.get(0).scrollAndClick();
	}
	
	
	@Override
	public void waitForComponentToLoad() {
		try {
			WaitUtil.sleep(2000);
			waitForFrameToLoad();
			header.waitForPresent();
			//header.waitForVisible();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}
	}
	
	public SelectedRepairOrderComponent() {
		waitForComponentToLoad();
	}
	
}
