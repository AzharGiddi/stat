package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class WhatsNewComponent extends ExtWebComponent{
	
	@FindBy(xpath="//div[starts-with(@class,' flex content layout-content-stacked')][2]")
	public ExtWebElement componentWhatsNew;
	
	@FindBy(xpath="//div[contains(text(),'What')]")
	public ExtWebElement txtWhatsNew;
	
	@FindBy(xpath="//div[@id='rte-default']/div")
	public ExtWebElement txtWhatsNewContent;
	
	@FindBy(xpath="//div[@class='rteReadOnlyWithoutTB']")
	public ExtWebElement textWhatsNew;
	
	
	
	public void waitForComponentToLoad() {

		try {
			DriverManager.getDriver().switchTo().defaultContent();
			componentWhatsNew.waitForPresent();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ componentWhatsNew.toString()));

		}
	}
	
	
	public WhatsNewComponent() {
		waitForComponentToLoad();
	}
}
