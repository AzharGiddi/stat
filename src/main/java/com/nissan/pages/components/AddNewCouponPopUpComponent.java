package com.nissan.pages.components;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class AddNewCouponPopUpComponent extends ExtWebComponent {

	@FindBy(xpath = "//div/span[normalize-space(text())='Add New Coupon']")
	public ExtWebElement headerAddNewCoupon;
	
	@FindBy(xpath = "//input[@type='radio' and contains(@name,'AvailableGWCoupons')]")
	public List<ExtWebElement> listAvailableCoupons;
	
	@FindBy(xpath = "//button[normalize-space(text())='Submit']")
	public ExtWebElement btnSubmitCoupon;
	

	@Override
	public void waitForComponentToLoad() {
		try {
			headerAddNewCoupon.waitForVisible();
			ExtentLogger.logWithScreenshot(this.getClass().getSimpleName() + " loaded successfully.");
			// waitForFrameToLoad();
		} catch (Exception e) {
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));
		}
	}

	public AddNewCouponPopUpComponent() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		waitForComponentToLoad();
	}
}
