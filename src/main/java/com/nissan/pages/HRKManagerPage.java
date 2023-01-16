package com.nissan.pages;

import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.components.HRKConnectorDetailsComponent;
import com.nissan.pages.components.HRKManageConnectorsComponent;
import com.nissan.pages.components.HRKManageSystemRulesComponent;
import com.nissan.pages.components.HRKReportsComponent;
import com.nissan.reports.ExtentLogger;

public class HRKManagerPage extends BasePage{

	
	@FindBy(xpath = "//img[@name='PortalHeaderGW_pyDisplayHarness_1']")
	public ExtWebElement logoNissan;

	@FindBy(xpath = "//li[@role='tab' and @title='DASHBOARD']")
	public ExtWebElement tabDashboard;
	
	private HRKManageSystemRulesComponent hRKManageSystemRulesComponent;
	
	private HRKReportsComponent hRKReportsComponent;
	
	private HRKManageConnectorsComponent hRKManageConnectorsComponent;
	
	private HRKConnectorDetailsComponent connectorInfoComponent;

	public HRKManageSystemRulesComponent getManageSystemRulesComponent() {
		
		if(Objects.isNull(hRKManageSystemRulesComponent)) {
			setManageSystemRulesComponent(new HRKManageSystemRulesComponent());
		}
		
		return hRKManageSystemRulesComponent;
	}

	public void setManageSystemRulesComponent(HRKManageSystemRulesComponent hRKManageSystemRulesComponent) {
		this.hRKManageSystemRulesComponent = hRKManageSystemRulesComponent;
	}

	public HRKReportsComponent gethRKReportsComponent() {
		if(Objects.isNull(hRKReportsComponent)) {
			sethRKReportsComponent(new HRKReportsComponent());
		}
		return hRKReportsComponent;
	}

	public void sethRKReportsComponent(HRKReportsComponent hRKReportsComponent) {
		this.hRKReportsComponent = hRKReportsComponent;
	} 
	
	public void waitForPageToLoad() {

		try {
			DriverManager.getDriver().switchTo().defaultContent();
			closeErrorMessageAndOpenTabs();
			logoNissan.waitForVisible();

			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "));

		}

	}

	public HRKManageConnectorsComponent getHRKManageConnectorsComponent() {
		if(Objects.isNull(hRKManageConnectorsComponent)) {
			setHRKManageConnectorsComponent(new HRKManageConnectorsComponent());
		}
		
		return hRKManageConnectorsComponent;
	}

	public void setHRKManageConnectorsComponent(HRKManageConnectorsComponent hRKManageConnectorsComponent) {
		this.hRKManageConnectorsComponent = hRKManageConnectorsComponent;
	}

	public HRKConnectorDetailsComponent getConnectorInfoComponent() {
		if(Objects.isNull(connectorInfoComponent))
			setConnectorInfoComponent(new HRKConnectorDetailsComponent());
		
		return connectorInfoComponent;
	}

	public void setConnectorInfoComponent(HRKConnectorDetailsComponent connectorInfoComponent) {
		this.connectorInfoComponent = connectorInfoComponent;
	}
	
}
