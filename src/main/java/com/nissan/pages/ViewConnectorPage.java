package com.nissan.pages;

import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverFactory;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class ViewConnectorPage extends BasePage{

	@FindBy(xpath = "//div[@class='content-item content-label item-1 remove-left-spacing remove-right-spacing flex flex-row heading_6_dataLabelWrite text-size-x-large centered padding-b-1x dataLabelWrite heading_6_dataLabelWrite']")
	public ExtWebElement header;

	
	@Override
	public void waitForPageToLoad() {

		try {
			
			getWindow();
			//header.waitForVisible();
			ExtentLogger.passWithScreenShot(this.getClass().getSimpleName()+ " loaded successfully");

		} catch (Exception e) {

			throw new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					, e);

		}

	}
	
public void closeWindow() {
		
		DriverManager.getDriver().close();
		DriverManager.getDriver().switchTo().window(DriverFactory.parentWindow);
	}
		
	public String getWindow() {
		String returnWindow = "";
		Set<String> whs = DriverManager.getDriver().getWindowHandles();
		for (String wh : whs) {
			
			DriverManager.getDriver().switchTo().window(wh);
			try {
				if (DriverManager.getDriver().getTitle().equals("View Connector")) {
					returnWindow = wh;
					break;
				}
			} catch (NoSuchElementException e) {

			}

		}

		return returnWindow;
	}
	
}
