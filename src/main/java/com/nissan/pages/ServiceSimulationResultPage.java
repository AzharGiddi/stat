package com.nissan.pages;

import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;

public class ServiceSimulationResultPage extends BasePage {

	@FindBy(xpath = "//div[text()='Simulation Results for MQ Service STATServicePackage.Services.CreateUpdateRepairOrder']")
	public ExtWebElement textHeader;

	@FindBy(xpath = "(//td[text()='Success'])[1]")
	public ExtWebElement txtOverallResultSuccess;
	
	public void waitForPageToLoad() {

		try {
			
			textHeader.waitForPresent();

		} catch (Exception e) {

			throw new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ textHeader.toString(), e);

		}

	}
	
	public void getWindow(String window) {

		Set<String> whs = DriverManager.getDriver().getWindowHandles();
		for (String wh : whs) {
			if(wh.equals(window))
				continue;
			DriverManager.getDriver().switchTo().window(wh);
			
		}
	}
}
