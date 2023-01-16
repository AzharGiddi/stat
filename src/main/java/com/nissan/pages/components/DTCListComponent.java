package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.enums.DropDownName;
import com.nissan.reports.ExtentLogger;

public class DTCListComponent extends ExtWebComponent {

	@FindBy(xpath="//input[@id='4b040df2']")
	public ExtWebElement chkboxIConfirm;
	
	
	
	/**
	 * Use this method to fetch drop down elements on Automatic Transmission CVT
	 * Symptom Form.
	 * 
	 * Please use only for to fetch following dropdown elements: <b>Vehicle does not
	 * move,Vibration, Engine stalls when</b>
	 * 
	 * @param event
	 *            Name of the dropdown element
	 * @return ExtWebElement
	 */
	public ExtWebElement getDropDownWebElement(DropDownName dropDownName) {

		String xPath = "//div/descendant::Select[contains(@name,'%s')]";
		ExtWebElement extWebElement = ExtWebComponent.getExtWebElement(String.format(xPath, dropDownName.getDropDownName()));
		extWebElement.moveToElement();
		return extWebElement;
	}	
	
	public void waitForComponentToLoad() {

		try {
			chkboxIConfirm.waitForPresent();
			//	ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}
	}
	
	
	
	
	
	public DTCListComponent() {
		
	}
}
