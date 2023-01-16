package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.BasePage;
import com.nissan.reports.ExtentLogger;



/***
 * 
 * @author AB00789853
 *
 */
public class CaseListReportComponent extends ExtWebComponent{

	@FindBy(xpath = "//li[@aria-selected='true']//span[contains(text(),'CaseListReport')]")
	public ExtWebElement tabCaseListReport;
	
	@FindBy(xpath = "//iframe[@title='CaseListReport']")
	public ExtWebElement iframe;
	
	@FindBy(xpath = "//button[contains(@data-click,'className\":\"Rule-Obj-Report-Definition') and text()='Actions']")
	public ExtWebElement buttonActions;
	
	@FindBy(xpath = "//ul[contains(@style,'display: block')]//span[text()='Run']")
	public ExtWebElement linkRun;
	
	
	
	public void waitForComponentToLoad() {
		
		try {
			//driver.switchTo().defaultContent();
			//WaitUtil.waitForElementToLoad(f);
			waitForFrameToLoad(iframe);
		//	driver.switchTo().frame(getIFrame("CaseListReport"));
			
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail( new PageNotFoundException(
					this.getClass().getSimpleName()+" did not load, Waited 20 seconds for the visibility of the element located by xpath: "
							+ tabCaseListReport.toString()));

		}
		
	}
	
		
}
