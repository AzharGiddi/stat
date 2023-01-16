package com.nissan.pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverFactory;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class GeneralRepairInstructionsPage extends BasePage{

	@FindBy(xpath = "//h6[text()='General Repair Instructions']")
	public ExtWebElement header;
	
	@FindBy(xpath = "//span//div[@node_name='ViewFile']//iframe")
	public ExtWebElement readingFramePDF;
	
	@FindBy(xpath = "//span//div[@node_name='ViewFile']//video")
	public ExtWebElement readingFrameVideo;
	
	
	
	
	
	@Override
	public void waitForPageToLoad() {

		try {
			
			getWindow();
			header.waitForVisible();
			ExtentLogger.passWithScreenShot(this.getClass().getSimpleName()+ " loaded successfully");

		} catch (Exception e) {

			throw new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					, e);

		}

	}
	
	public void closeWindow() {
		
		/*String currentWindowHandle = DriverManager.getDriver().getWindowHandle();
		for (String handle : DriverManager.getDriver().getWindowHandles()) {

			if (currentWindowHandle.equals(handle)) {
				DriverManager.getDriver().close();
				continue;
			}
			DriverManager.getDriver().switchTo().window(handle);
			//DriverManager.getDriver().close();

		}*/
		
		DriverManager.getDriver().close();
		DriverManager.getDriver().switchTo().window(DriverFactory.parentWindow);
		
	}
	
	public ExtWebElement getLinkElementWith(String labelName) {
		 By labelLink = By.linkText(labelName) ;
		  return  getExtWebElement(labelLink,"labelLink");
	}
		
		
	public String getWindow() {
		String returnWindow = "";
		Set<String> whs = DriverManager.getDriver().getWindowHandles();
		for (String wh : whs) {
			
			DriverManager.getDriver().switchTo().window(wh);
			try {
				if (DriverManager.getDriver().getTitle().equalsIgnoreCase("General Repair Instructions")) {
					returnWindow = wh;
					break;
				}
			} catch (NoSuchElementException e) {

			}

		}

		return returnWindow;
	}
	
}
