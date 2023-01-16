package com.nissan.pages;

import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.components.AudioSymptomsSurveyFormComponent;
import com.nissan.pages.components.CCCComponent;
import com.nissan.pages.components.DashboardComponent;
import com.nissan.pages.components.PCCQuestionsComponent;
import com.nissan.pages.components.PendingTechActionsComponent;
import com.nissan.pages.components.QuestionsComponent;
import com.nissan.pages.components.SelectRepairOrderComponent;
import com.nissan.pages.components.SelectedRepairOrderComponent;
import com.nissan.pages.components.SelectUSSCasesROComponent;
import com.nissan.pages.components.TechnicianAutomaticTransmissionCVTSymtomFormComponent;
import com.nissan.reports.ExtentLogger;

public class TechnicianPage extends BasePage {

	@FindBy(xpath = "//img[@name='PortalHeaderGW_pyDisplayHarness_1']")
	public ExtWebElement logoNissan;

	@FindBy(xpath = "//li[@role='tab' and @title='DASHBOARD']")
	public ExtWebElement tabDashboard;

	@FindBy(xpath = "//h6[@id='headerlabel9632']")
	public ExtWebElement headerCustomerSymptomsSummary;

	@FindBy(xpath = "//h6[@id='headerlabel293']")
	public ExtWebElement headerTechnicianObservationForm;

	@FindBy(xpath = "//h6[@id='headerlabel4112']")
	public ExtWebElement headerDTCList;
	
	

	public DashboardComponent dashboardComponent;

	public TechnicianAutomaticTransmissionCVTSymtomFormComponent technicianAutomaticTransmissionCVTSymtomFormComponent;

	public PendingTechActionsComponent pendingTechActionsComponent;
	
	public CCCComponent cCCCOmponent;
	
	public QuestionsComponent questionsComponent;
	
	public QuestionsComponent getQuestionsComponent() {
		//if (Objects.isNull(questionsComponent))
			setQuestionsComponent(new QuestionsComponent());

		
		return questionsComponent;
	}

	public void setQuestionsComponent(QuestionsComponent questionsComponent) {
		this.questionsComponent = questionsComponent;
	}

	public CCCComponent getcCCCOmponent() {
		if (Objects.isNull(cCCCOmponent))
			setcCCCOmponent(new CCCComponent());

		
		return cCCCOmponent;
	}

	public void setcCCCOmponent(CCCComponent cCCCOmponent) {
		this.cCCCOmponent = cCCCOmponent;
	}

	public PendingTechActionsComponent getPendingTechActionsComponent() {
		if (Objects.isNull(pendingTechActionsComponent))
			setPendingTechActionsComponent(new PendingTechActionsComponent());

		return pendingTechActionsComponent;
	}

	public void setPendingTechActionsComponent(PendingTechActionsComponent pendingTechActionsComponent) {
		this.pendingTechActionsComponent = pendingTechActionsComponent;
	}

	public TechnicianAutomaticTransmissionCVTSymtomFormComponent getTechnicianAutomaticTransmissionCVTSymptomFormComponent() {

		if (Objects.isNull(technicianAutomaticTransmissionCVTSymtomFormComponent)) {
			setTechnicianAutomaticTransmissionCVTSymptomFormComponent(
					new TechnicianAutomaticTransmissionCVTSymtomFormComponent());
		}
		return technicianAutomaticTransmissionCVTSymtomFormComponent;
	}

	public void setTechnicianAutomaticTransmissionCVTSymptomFormComponent(
			TechnicianAutomaticTransmissionCVTSymtomFormComponent automaticTransmissionCVTSymptomFormComponent) {
		this.technicianAutomaticTransmissionCVTSymtomFormComponent = automaticTransmissionCVTSymptomFormComponent;
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
	private AudioSymptomsSurveyFormComponent audioSymptomsSurveyFormComponent;
public AudioSymptomsSurveyFormComponent getAudioSymptomsSurveyFormComponent() {
		
		if(Objects.isNull(audioSymptomsSurveyFormComponent)) {
			setAudioSymptomsSurveyFormComponent(new AudioSymptomsSurveyFormComponent());
		}
		return audioSymptomsSurveyFormComponent;
	}

	public void setAudioSymptomsSurveyFormComponent(AudioSymptomsSurveyFormComponent audioSymptomsSurveyFormComponent) {
		this.audioSymptomsSurveyFormComponent = audioSymptomsSurveyFormComponent;
	}

	private SelectRepairOrderComponent selectROTableComponent;
	
	public SelectRepairOrderComponent getSelectROTableComponent() {
		if (Objects.isNull(selectROTableComponent)) {
			setSelectROTableComponent(new SelectRepairOrderComponent());
		}

		return selectROTableComponent;
	}

	public void setSelectROTableComponent(SelectRepairOrderComponent selectROTableComponent) {
		this.selectROTableComponent = selectROTableComponent;
	}
	
	private SelectedRepairOrderComponent selectRepairOrderComponent;
	
	public SelectedRepairOrderComponent getSelectRepairOrderComponent() {
		if (Objects.isNull(selectRepairOrderComponent)) {
			setSelectRepairOrderComponent(new SelectedRepairOrderComponent());
		}

		return selectRepairOrderComponent;
	}

	public void setSelectRepairOrderComponent(SelectedRepairOrderComponent selectRepairOrderComponent) {
		this.selectRepairOrderComponent = selectRepairOrderComponent;
	}
	
	private SelectUSSCasesROComponent selectUSSCasesROComponent;
	
	public SelectUSSCasesROComponent getSelectUSSCasesROComponent() {
		if (Objects.isNull(selectUSSCasesROComponent)) {
			setSelectUSSCasesROComponent(new SelectUSSCasesROComponent());
		}
		return selectUSSCasesROComponent;
	}

	public void setSelectUSSCasesROComponent(SelectUSSCasesROComponent selectUSSCasesROComponent) {
		this.selectUSSCasesROComponent = selectUSSCasesROComponent;
	}
	
	public void waitForPageToLoad() {

		try {

			logoNissan.waitForPresent();
			closeErrorMessageAndOpenTabs();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ logoNissan.toString()));

		}

	}

	public TechnicianPage() {

	}

	public TechnicianPage(String userName, String password) {
		login(userName, password);
	}

	

}
