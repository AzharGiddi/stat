package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class SearchMQMessageComponent extends ExtWebComponent{

	@FindBy(xpath = "//h2[text()='Repair Options']")
	public ExtWebElement headerSearchMQMessage;
	
	@FindBy(xpath = "//select[@name='$PMQMessageSearch$pMQMsgSearchType']")
	public ExtWebElement dropDownMessageType;
	
	@FindBy(xpath = "//input[@name='$PMQMessageSearch$pRODetail$pFilterRO']")
	public ExtWebElement txtboxRepairOrderNumber;
	
	@FindBy(xpath = "//div[text()='Search']")
	public ExtWebElement btnSearch;
	
	@FindBy(xpath = "//input[@name='$PMQMessageSearch$pRODetail$pFilterStartDate']")
	public ExtWebElement txtBoxStartDate;
	
	@Override
	public void waitForComponentToLoad() {

		try {
			
			waitForFrameToLoad();
			headerSearchMQMessage.waitForVisible();
			ExtentLogger.info(this.getClass().getSimpleName()+ "loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load, Waited 20 seconds for the visibility of the element located by xpath: "));

		}
	}
	
	public SearchMQMessageComponent() {
		
		ElementFactory.initElements(DriverManager.getDriver(), this);
		waitForComponentToLoad();
	}
}
