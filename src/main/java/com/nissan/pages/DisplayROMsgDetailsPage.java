package com.nissan.pages;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class DisplayROMsgDetailsPage extends BasePage{

	
	@FindBy(xpath = "//span[text()='XML Message']")
	public ExtWebElement headerXMLMessage;
	
	@FindBy(xpath = "//span[@data-test-id='2017071101491109485205']")
	public ExtWebElement textROXMLString;
	
	public void waitForPageToLoad() {
		try {

			headerXMLMessage.waitForPresent();
			closeErrorMessageAndOpenTabs();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ headerXMLMessage.toString()));

		}
	}
	
	
	public DisplayROMsgDetailsPage() {
		
		ElementFactory.initElements(DriverManager.getDriver(), this);
	}
}
