package com.nissan.pages;

import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.components.ConnectorLookupComponent;
import com.nissan.pages.components.DashboardComponent;
import com.nissan.pages.components.FeedbackCommentsHRKConnectorLookupComponent;
import com.nissan.pages.components.WhatsNewComponent;
import com.nissan.reports.ExtentLogger;

public class ConnectorLookupExternalPage extends BasePage{

	@FindBy(xpath = "//img[@name='PortalHeaderGW_pyDisplayHarness_1']")
	public ExtWebElement logoNissan;
	
	@FindBy(xpath = "//div[text()='Connector Lookup']")
	public ExtWebElement headerConnectorLookup;

	@FindBy(xpath = "//div[text()='Find Connectors by']")
	public ExtWebElement textFindConnectorsBy;

	@FindBy(xpath = "//a[contains(@name,'CaptureConnectors')]/i")
	public ExtWebElement btnGoArrorw;

	@FindBy(xpath = "//table[@id='gridLayoutTable']")
	public ExtWebElement tableResultsGrid;

	@FindBy(xpath = "//input[@type='text' and contains(@name,'DisplayHarness')]")
	public ExtWebElement txtboxInputbox;

	@FindBy(xpath = "//tr[contains(@id,'GetConnectorData')]/td[@tabindex='0']//span")
	public ExtWebElement linkFirstConnectorRecord;

	@FindBy(xpath = "//div[text()='Connector Information']")
	public ExtWebElement txtConnectorInfo;

	@FindBy(xpath = "//button[text()='Back to Search']")
	public ExtWebElement btnBackToSearch;
	
	@FindBy(xpath = "//select[@name='$PpyDisplayHarness$pMake']")
	public ExtWebElement dropdownMake;

	@FindBy(xpath = "//select[@name='$PpyDisplayHarness$pModelName']")
	public ExtWebElement dropdownModel;

	@FindBy(xpath = "//select[@name='$PpyDisplayHarness$pModelYear']")
	public ExtWebElement dropdownYear;
	
	@FindBy(xpath = "//a[text()='Back to Search']")
	public ExtWebElement linkBackToSearch;
	
	private DashboardComponent dashboardComponent;
	
	private ConnectorLookupComponent connectorLookupComponent;

	public ConnectorLookupComponent getConnectorLookupComponent() {
		//connectorLookupComponent=null;
		if (Objects.isNull(connectorLookupComponent))
			setConnectorLookupComponent(new ConnectorLookupComponent());
		return connectorLookupComponent;
	}

	public void setConnectorLookupComponent(ConnectorLookupComponent connectorLookupComponent) {
		this.connectorLookupComponent = connectorLookupComponent;
	}
	
	public ExtWebElement getConnectorByRadioButton(String radioBtnName) {

		String xPath = String.format("//label[text()='%s']/preceding-sibling::input[@type='radio']",radioBtnName.trim());
		return ExtWebComponent.getExtWebElement(xPath);

	}
	
private FeedbackCommentsHRKConnectorLookupComponent feedbackComponent;
	
	public FeedbackCommentsHRKConnectorLookupComponent getFeedbackComponent() {
		return feedbackComponent;
	}

	public void setFeedbackComponent(FeedbackCommentsHRKConnectorLookupComponent feedbackComponent) {
		this.feedbackComponent = feedbackComponent;
	}

	
	public void invoke() {
		String url = baseUrl+"PRAuth/Connectors";
		DriverManager.getDriver().get(url);
	}
	
	
	public void waitForPageToLoad() {

		try {
		//	driver.switchTo().defaultContent();
		//	closeErrorMessageAndOpenTabs();
			logoNissan.waitForVisible();

			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ logoNissan.toString()));

		}

	}

	public DashboardComponent getDashboardComponent() {
		return dashboardComponent;
	}

	public void setDashboardComponent(DashboardComponent dashboardComponent) {
		this.dashboardComponent = dashboardComponent;
	}
}
