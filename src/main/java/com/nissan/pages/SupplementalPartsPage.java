package com.nissan.pages;

import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverFactory;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class SupplementalPartsPage extends BasePage{

	@FindBy(xpath = "//h6[text()='Supplemental Parts']")
	public ExtWebElement header;
	
	@Override
	public void waitForPageToLoad() {

		try {
			
			this.windowHandle=getWindow();
			//DriverManager.getDriver().switchTo().window(windowHandle);
			//DriverManager.getDriver().switchTo().defaultContent();
			header.waitForVisible();
			ExtentLogger.passWithScreenShot(this.getClass().getSimpleName()+ " loaded successfully");

		} catch (Exception e) {

			throw new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					, e);

		}

	}
	
	private String windowHandle;
	private String parentWindow;
	
	public void closeWindow() {

		String currentWindowHandle = DriverManager.getDriver().getWindowHandle();
		for (String handle : DriverManager.getDriver().getWindowHandles()) {

			if (currentWindowHandle.equals(handle)) {
				DriverManager.getDriver().close();
				continue;
			}
			DriverManager.getDriver().switchTo().window(handle);

		}
	}
	
	public String getWindow() {
		String returnWindow = "";
		if (DriverManager.getDriver().getTitle().equals("Supplemental Parts")) 
			return DriverManager.getDriver().getWindowHandle();
		Set<String> whs = DriverManager.getDriver().getWindowHandles();
		for (String wh : whs) {
			
			DriverManager.getDriver().switchTo().window(wh);
			try {
				if (DriverManager.getDriver().getTitle().equals("Supplemental Parts")) {
					returnWindow = wh;
					break;
				}
			} catch (NoSuchElementException e) {

			}

		}

		return returnWindow;
	}
	
}
