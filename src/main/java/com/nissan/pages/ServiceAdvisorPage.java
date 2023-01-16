package com.nissan.pages;

import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.components.AudioSymptomsSurveyFormComponent;
import com.nissan.pages.components.AutomaticTransmissionCVTSymptomFormComponent;
import com.nissan.pages.components.ConnectorLookupComponent;
import com.nissan.pages.components.DashboardComponent;
import com.nissan.pages.components.NewCVTSymptomFormComponent;
import com.nissan.pages.components.SelectRepairOrderComponent;
import com.nissan.pages.components.SelectedRepairOrderComponent;
import com.nissan.reports.ExtentLogger;

public class ServiceAdvisorPage extends BasePage {

	@FindBy(xpath = "//img[@name='PortalHeaderGW_pyDisplayHarness_1']")
	public ExtWebElement logoNissan;

	@FindBy(xpath = "//li[@role='tab' and @title='DASHBOARD']")
	public ExtWebElement tabDashboard;

	// instantiate components on the page
	public DashboardComponent dashboardComponent;

	public NewCVTSymptomFormComponent newCVTSymptomFormComponent;
	
	private AudioSymptomsSurveyFormComponent audioSymptomsSurveyFormComponent; 

	public AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent;

	public AutomaticTransmissionCVTSymptomFormComponent getAutomaticTransmissionCVTSymptomFormComponent() {

		if (Objects.isNull(automaticTransmissionCVTSymptomFormComponent)) {
			setAutomaticTransmissionCVTSymptomFormComponent(new AutomaticTransmissionCVTSymptomFormComponent());
		}
		return automaticTransmissionCVTSymptomFormComponent;
	}

	public void setAutomaticTransmissionCVTSymptomFormComponent(
			AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent) {
		this.automaticTransmissionCVTSymptomFormComponent = automaticTransmissionCVTSymptomFormComponent;
	}

	public DashboardComponent getDashboardComponent() {
		if (Objects.isNull(dashboardComponent)) {
			setDashboardComponent(new DashboardComponent());
		}

		return dashboardComponent;
	}

	public void setDashboardComponent(DashboardComponent dashboardComponent) {
		this.dashboardComponent = dashboardComponent;
	}

	public NewCVTSymptomFormComponent getNewCVTSymptomFormComponent() {
		if (Objects.isNull(newCVTSymptomFormComponent)) {
			setNewCVTSymptomFormComponent(new NewCVTSymptomFormComponent());
		}

		return newCVTSymptomFormComponent;
	}

	public void setNewCVTSymptomFormComponent(NewCVTSymptomFormComponent newCVTSymptomFormComponent) {
		this.newCVTSymptomFormComponent = newCVTSymptomFormComponent;
	}
	
	private ConnectorLookupComponent connectorLookupComponent;

	public ConnectorLookupComponent getConnectorLookupComponent() {
		if(Objects.isNull(connectorLookupComponent))
			setConnectorLookupComponent(new ConnectorLookupComponent());
		return connectorLookupComponent;
	}

	public void setConnectorLookupComponent(ConnectorLookupComponent connectorLookupComponent) {
		this.connectorLookupComponent = connectorLookupComponent;
	}
	
	private SelectRepairOrderComponent selectRepairOrderComponent;
	
	private SelectedRepairOrderComponent selectedRepairOrderComponent;
	
	public SelectRepairOrderComponent getSelectRepairOrderComponent() {
		if (Objects.isNull(selectRepairOrderComponent)) {
			setSelectRepairOrderComponent(new SelectRepairOrderComponent());
		}

		return selectRepairOrderComponent;
	}

	public void setSelectRepairOrderComponent(SelectRepairOrderComponent selectROTableComponent) {
		this.selectRepairOrderComponent = selectROTableComponent;
	}
	
	public SelectedRepairOrderComponent getSelectedRepairOrderComponent() {
		if (Objects.isNull(selectedRepairOrderComponent)) {
			setSelectedRepairOrderComponent(new SelectedRepairOrderComponent());
		}

		return selectedRepairOrderComponent;
	}

	public void setSelectedRepairOrderComponent(SelectedRepairOrderComponent selectedRepairOrderComponent) {
		this.selectedRepairOrderComponent = selectedRepairOrderComponent;
	}

	public void waitForPageToLoad() {

		try {
			
		logoNissan.waitForPresent();
		closeErrorMessageAndOpenTabs();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			throw new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ logoNissan.toString());

		}

	}
	
	public ServiceAdvisorPage() {
		
	}

	public ServiceAdvisorPage(String userName, String password) {
		login(userName, password);
	}

	public AudioSymptomsSurveyFormComponent getAudioSymptomsSurveyFormComponent() {
		
		if(Objects.isNull(audioSymptomsSurveyFormComponent)) {
			setAudioSymptomsSurveyFormComponent(new AudioSymptomsSurveyFormComponent());
		}
		return audioSymptomsSurveyFormComponent;
	}

	public void setAudioSymptomsSurveyFormComponent(AudioSymptomsSurveyFormComponent audioSymptomsSurveyFormComponent) {
		this.audioSymptomsSurveyFormComponent = audioSymptomsSurveyFormComponent;
	}
}
