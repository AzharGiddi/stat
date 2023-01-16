package com.nissan.pages.components;

import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class TechnicianAutomaticTransmissionCVTSymtomFormComponent
		extends AutomaticTransmissionCVTSymptomFormComponent {

	@FindBy(xpath = "//button[text()='Diagnose']")
	public ExtWebElement btnDiagnose;
	
	@FindBy(xpath = "//h6[text()='Technician Observation Form']")
	public ExtWebElement headerTechnicianObservationForm;
	
	@FindBy(xpath = "//h6[text()='Customer Symptoms Summary']")
	public ExtWebElement headerCustomerSymptomsSummary;
	
	@FindBy(xpath = "//h6[text()='DTC List']")
	public ExtWebElement headerDTCList;
	
	@FindBy(xpath = "//h6[text()='DTC List']/parent::div/preceding-sibling::div/i")
	public ExtWebElement expandDTC;
	
	
	
	

	public VCATSupportComponent vCATSupportComponent;

	public VCATSupportComponent getvCATSupportComponent() {

		if (Objects.isNull(vCATSupportComponent))
			vCATSupportComponent = new VCATSupportComponent();

		return vCATSupportComponent;
	}

	public void setvCATSupportComponent(VCATSupportComponent vCATSupportComponent) {
		this.vCATSupportComponent = vCATSupportComponent;
	}

	public DTCListComponent dTCListComponent;

	public DTCListComponent getdTCListComponent() {
		if (Objects.isNull(dTCListComponent))
			dTCListComponent = new DTCListComponent();

		return dTCListComponent;
	}

	public void setdTCListComponent(DTCListComponent dTCListComponent) {
		this.dTCListComponent = dTCListComponent;
	}

	

	public TechnicianAutomaticTransmissionCVTSymtomFormComponent() {
		//waitForComponentToLoad();
	}

}
