package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;



/***
 * 
 * @author AB00789853
 *
 */
public class DVehicleReferenceComponent extends ExtWebComponent{

	@FindBy(xpath = "//li[@aria-selected='true']//span[contains(text(),'D_VehicleRef')]")
	public ExtWebElement tabCaseListReport;
	
	/*@FindBy(xpath = "//iframe[@title='CaseListReport']")
	public ExtWebElement iframe;*/
	
	@FindBy(xpath = "//button[text()='Actions']")
	public ExtWebElement buttonActions;
	
	@FindBy(xpath = "//ul[contains(@style,'display: block')]//span[text()='Run']")
	public ExtWebElement linkRun;
	
	
	
	public void waitForComponentToLoad() {
		
		try {
			DriverManager.getDriver().switchTo().defaultContent();
			DriverManager.getDriver().switchTo().frame(getIFrame("D_VehicleRef"));
					
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail( new PageNotFoundException(
					this.getClass().getSimpleName()+" did not load, Waited 20 seconds for the visibility of the element located by xpath: "
							+ tabCaseListReport.toString()));

		}
		
	}
	
		public DVehicleReferenceComponent() {
			waitForComponentToLoad();
		}
}
