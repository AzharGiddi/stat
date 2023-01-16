package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class ManageSystemRulesComponent extends ExtWebComponent {

	@FindBy(xpath = "//div[@data-node-id='ManageSystemRules']")
	private ExtWebElement componentManageSystemRules;
	
	@FindBy(xpath="//div[@id='modaldialog_hd']/span[contains(text(),'New Messaging')]")
	public ExtWebElement headerWhatsNewMessaging;
	
	@FindBy(xpath = "//iframe[@class='cke_wysiwyg_frame cke_reset']")
	public ExtWebElement iframeWhatsNewMsgContent;
	
	@FindBy(xpath = "//body[starts-with(@class,'cke_editable cke_editable')]")
	public ExtWebElement txtWhatsNewMsgContent;
	

	public ExtWebElement getMenuElement(String eleName) {

		String xPath = "//a[contains(text(),'" + eleName + "')]";

		return getExtWebElement(xPath);

	}
	
	

	public void waitForComponentToLoad() {

		try {
			/*driver.switchTo().defaultContent();
			driver.switchTo().frame(getIFrame("Manage System Rules"));
			componentManageSystemRules.waitForPresent();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");*/
			waitForFrameToLoad();
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}

	}

	public ManageSystemRulesComponent() {
		waitForComponentToLoad();
	}

}
