package com.nissan.pages.components;

import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.components.AutomaticTransmissionCVTSymptomFormComponent.DropDownName;
import com.nissan.reports.ExtentLogger;

public class AudioSymptomsSurveyFormComponent extends ExtWebComponent{

	
	@FindBy(xpath="//input[@name='$PpyWorkPage$pCustomerHandset$pDeviceBrand']")
	public ExtWebElement txtboxDeviceBrand;
	
	@FindBy(xpath="//input[@name='$PpyWorkPage$pCustomerHandset$pDeviceModel']")
	public ExtWebElement txtboxDeviceModel;
	
	@FindBy(xpath="//input[@name='$PpyWorkPage$pCustomerHandset$pSoftwareVersion']")
	public ExtWebElement txtboxSoftwarreVersion;
	
	@FindBy(xpath="//input[@name='$PpyWorkPage$pCustomerHandset$pSoftwareVersion']")
	public ExtWebElement txtboxCarier;
	
	@FindBy(xpath="//input[@type='checkbox' and contains(@name,'GeneralSymptoms$l1$ppySelected')]")
	public ExtWebElement chkboxAudioPlaysWIthIgnitionOff;
	
	@FindBy(xpath="//input[@id='57d307d4']")
	public ExtWebElement chkboxButtonAppearance;
	
	@FindBy(xpath="//input[@id='8a45de51']")
	public ExtWebElement chkboxNoClock;
	
	@FindBy(xpath="//input[@id='b01ab877']")
	public ExtWebElement chkboxOther;
	
	@FindBy(xpath="//select[@name='$PpyWorkPage$pSymptom$pCustomerConcern']")
	public ExtWebElement dropdownWhenDidConcernBegin;
	
	@FindBy(xpath = "//button[text()='Save for Later']")
	public ExtWebElement btnSaveForLater;

	@FindBy(xpath = "//button[text()='Submit']")
	public ExtWebElement btnSubmit;
	
	@FindBy(xpath = "//h6[text()='General Symptoms']")
	public ExtWebElement headerGeneralSymptoms;
	
	@FindBy(xpath = "//h6[text()='Systems / Components']")
	public ExtWebElement headerSystemsComponents;
	
	@FindBy(xpath = "//h6[text()='OCCURS WHEN']")
	public ExtWebElement headerOccursWhen;
	
	@FindBy(xpath = "//h6[text()='Occurs Where']")
	public ExtWebElement headerOccursWhere;
	
	@FindBy(xpath = "//h6[text()='Occurs Condition']")
	public ExtWebElement headerOccursCondition;
	
	@FindBy(xpath = "//textarea[contains(@name,'OtherSymptoms')]")
	public ExtWebElement txtboxOtherSymptomsRecentRepairs;
	
	
	
	private DTCListComponent dTCListComponent;

	public DTCListComponent getdTCListComponent() {
		if (Objects.isNull(dTCListComponent))
			dTCListComponent = new DTCListComponent();

		return dTCListComponent;
	}

	public void setdTCListComponent(DTCListComponent dTCListComponent) {
		this.dTCListComponent = dTCListComponent;
	}
	
	/**
	 * Use this method to fetch drop down elements on Automatic Transmission CVT
	 * Symptom Form.
	 * 
	 * Please use only for to fetch following dropdown elements: <b>Vehicle does not
	 * move,Vibration, Engine stalls when</b>
	 * 
	 * @param eleName
	 *            Name of the dropdown element
	 * @return ExtWebElement
	 */
	public ExtWebElement getDropDownWebElement(DropDownName eleName) {

		String xPath = "//div/descendant::Select[contains(@name,'%s')]";
		ExtWebElement extWebElement = ExtWebComponent.getExtWebElement(String.format(xPath, eleName.getDropDownName()));
		extWebElement.moveToElement();
		return extWebElement;
	}
	
	@Override
	public void waitForComponentToLoad() {

		try {
		
			waitForFrameToLoad();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}
	}
	
	public AudioSymptomsSurveyFormComponent() {
		waitForComponentToLoad();
	}
}
