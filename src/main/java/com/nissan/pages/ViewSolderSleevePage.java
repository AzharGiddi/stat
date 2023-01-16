package com.nissan.pages;

import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverFactory;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class ViewSolderSleevePage extends BasePage{

	@FindBy(xpath = "//img[@name='ViewSolderSleeve_ArtVideoKitPage_5']")
	public ExtWebElement imgSolderSleeve;

	
	@Override
	public void waitForPageToLoad() {

		try {
			
			getWindow();
			imgSolderSleeve.waitForVisible();
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

		}*/
		
		DriverManager.getDriver().close();
		DriverManager.getDriver().switchTo().window(DriverFactory.parentWindow);
	}
		
		
	public String getWindow() {
		String returnWindow = "";
		Set<String> whs = DriverManager.getDriver().getWindowHandles();
		for (String wh : whs) {
			
			DriverManager.getDriver().switchTo().window(wh);
			try {
				if (DriverManager.getDriver().getTitle().equals("View Solder Sleeve")) {
					returnWindow = wh;
					break;
				}
			} catch (NoSuchElementException e) {

			}

		}

		return returnWindow;
	}
	
}
