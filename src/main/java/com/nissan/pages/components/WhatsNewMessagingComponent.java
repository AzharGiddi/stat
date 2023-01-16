package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class WhatsNewMessagingComponent extends ExtWebComponent{
	
	@FindBy(xpath="//span[@id='modaldialog_hd_title']")
	public ExtWebElement headerWhatsNewMessaging;
	
	@FindBy(xpath="//input[@type='checkbox' and @name=\"$PD_WhatsNewMessageSavable_pa1832998214371007pz$pEnableMessage\"]")
	public ExtWebElement chkboxEnableMessage;
	
	@FindBy(xpath="//div[@id='rte-default']/div")
	public ExtWebElement txtWhatsNewContent;
	
	
	
	public void waitForComponentToLoad() {

		try {
			//driver.switchTo().defaultContent();
			waitForFrameToLoad();
			headerWhatsNewMessaging.waitForPresent();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ headerWhatsNewMessaging.toString()));

		}
	}
	
	
	public WhatsNewMessagingComponent() {
		waitForComponentToLoad();
	}
}
