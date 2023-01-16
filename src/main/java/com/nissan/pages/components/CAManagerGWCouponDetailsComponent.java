package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class CAManagerGWCouponDetailsComponent extends ExtWebComponent{

	@FindBy(xpath="//div[text()='Goodwill Coupon Details']")
	public ExtWebElement txtGoodwillCouponDetails;
	
	@FindBy(xpath="//span[text()='Coupon ID']/following-sibling::div/span")
	public ExtWebElement txtCouponId;
	
	@FindBy(xpath="//input[@type='radio' and @value='Approved']")
	public ExtWebElement radiobtnApproved;
	
	@FindBy(xpath="//input[@type='radio' and @value='Recommended']")
	public ExtWebElement radiobtnRecommended;
	
	@FindBy(xpath="//input[@name='$PpyWorkPage$pGoodwillCouponPage$pNissanContribution']")
	public ExtWebElement txtboxNissanContribution;
	
	@FindBy(xpath="//textarea[@name='$PpyWorkPage$pGoodwillCouponPage$pCAComments']")
	public ExtWebElement txtboxConcern;
	
	@FindBy(xpath="//textarea[@name='$PpyWorkPage$pGoodwillCouponPage$pCAInternalComments']")
	public ExtWebElement txtboxInternalComments;
	
	@FindBy(xpath="//button[text()='Submit']")
	public ExtWebElement btnSubmit;
	
	public void waitForComponentToLoad() {
		try {
			waitForFrameToLoad();
			txtGoodwillCouponDetails.waitForPresent();
			ExtentLogger.passWithScreenShot(this.getClass().getSimpleName() + " loaded successfully.");
			} catch (Exception e) {

				ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

			}
		
		
	}
	
	
	
	
	public CAManagerGWCouponDetailsComponent() {
		
		ElementFactory.initElements(DriverManager.getDriver(), this);
		waitForComponentToLoad();
		
	}
}
