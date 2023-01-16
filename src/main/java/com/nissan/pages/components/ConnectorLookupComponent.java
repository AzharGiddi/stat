package com.nissan.pages.components;

import java.util.List;
import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class ConnectorLookupComponent extends ExtWebComponent {

	@FindBy(xpath = "//div[text()='Find Connectors by']")
	public ExtWebElement textFindConnectorsBy;

	//@FindBy(xpath = "//img[contains(@name,'CaptureConnectors')]")
	@FindBy(xpath = "//a[contains(@name,'CaptureConnectors')]/i")
	public ExtWebElement btnSearch;

	@FindBy(xpath = "//div[@id='PEGA_GRID_CONTENT']/table[@id='gridLayoutTable']//div[text()='Connector Name']")
	public ExtWebElement tableResultsGrid;

	@FindBy(xpath = "//input[@type='text' and @name='$PpyDisplayHarness$pSearchByVIN']")
	public ExtWebElement txtBoxEnterVIN;

	@FindBy(xpath = "//input[@type='text' and @name='$PpyDisplayHarness$pConnectorType']")
	public ExtWebElement txtBoxEnterConnectorNo;

	@FindBy(xpath = "//input[@type='text' and @name='$PpyDisplayHarness$pRepairOrderNumber']")
	public ExtWebElement txtBoxEnterROWONo;

	@FindBy(xpath = "//input[@type='text' and contains(@name,'DisplayHarness')]")
	public ExtWebElement txtboxInputbox;

	@FindBy(xpath = "//tr[contains(@id,'GetConnectorData')]/td[@tabindex='0']//span")
	public ExtWebElement linkFirstConnectorRecord;
	
	@FindBy(xpath = "//tr[contains(@id,'GetConnectorData')]/td[@data-attribute-name='Connector Name']")
	public List<ExtWebElement> listConnectorRecord;

	@FindBy(xpath = "//div[text()='Connector Information']")
	public ExtWebElement txtConnectorInfo;

	@FindBy(xpath = "//button[text()='Back to Search']")
	public ExtWebElement btnBackToSearch;
	
	@FindBy(xpath = "//button[text()='Back to Search Results']")
	public ExtWebElement btnBackToSearchResults;

	@FindBy(xpath = "//a[text()='Back to Search']")
	public ExtWebElement linkBackToSearch;
	
	@FindBy(xpath = "//a[text()='Connector General Repair Instructions']")
	public ExtWebElement linkGeneralRepair;

	@FindBy(xpath = "//select[@name='$PpyDisplayHarness$pMake']")
	public ExtWebElement dropdownMake;

	@FindBy(xpath = "//select[@name='$PpyDisplayHarness$pModelName']")
	public ExtWebElement dropdownModel;

	@FindBy(xpath = "//select[@name='$PpyDisplayHarness$pModelYear']")
	public ExtWebElement dropdownYear;

	@FindBy(xpath = "//select[@name='$PpyDisplayHarness$pSearchByVIN']")
	public ExtWebElement dropDownSelectVIN;
	
	@FindBy(xpath = "//div[not(contains(@style,'visibility: hidden;'))]/ul[@class='pz-po-c-ul']//div[@class='cellIn']/span")
	public List<ExtWebElement> listRO;
	
	@FindBy(xpath = "//button[@class='Feedback pzhc pzbutton']")
	public ExtWebElement btnFeedback;
	
	@FindBy(xpath = "//input[@name='$PpyDisplayHarness$pConnectorType']")
	public ExtWebElement txtboxFilterGrid;
	
	@FindBy(xpath = "(//td//img[not(@style='display:none')])[1]")
			public ExtWebElement infoIconKitDescriptionFirstResult;
	
	@FindBy(xpath = "//button[text()='Search']")
	public ExtWebElement btnSearchFilterGrid;
	
	@FindBy(xpath="//div[starts-with(text(),'No Results Found')]")
	public ExtWebElement txtNoResultFound;
	
	@FindBy(xpath = "//th[@role='columnheader' and @title='Click to sort']")
	public List<ExtWebElement> listConnectorTableColumnHeaders;

	
	private FeedbackCommentsHRKConnectorLookupComponent feedbackComponent;
	
private HRKConnectorDetailsComponent hRKConnectorDetailsComponent;
	
	public HRKConnectorDetailsComponent gethRKConnectorDetailsComponent() {
		if(Objects.isNull(hRKConnectorDetailsComponent)) {
			sethRKConnectorDetailsComponent(new HRKConnectorDetailsComponent());
		}
		return hRKConnectorDetailsComponent;
	}

	public void sethRKConnectorDetailsComponent(HRKConnectorDetailsComponent hRKConnectorDetailsComponent) {
		this.hRKConnectorDetailsComponent = hRKConnectorDetailsComponent;
	}

	public ExtWebElement getConnectorByRadioButton(String radioBtnName) {

		String xPath = String.format("//label[text()='%s']/preceding-sibling::input[@type='radio' and not(@disabled)]",
				radioBtnName.trim());
		return ExtWebComponent.getExtWebElement(xPath);

	}

	public void waitForComponentToLoad() {
		try {
			waitForFrameToLoad();
			WaitUtil.sleep(2000);
			textFindConnectorsBy.waitForPresent();
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}
	}

	public ConnectorLookupComponent() {

		waitForComponentToLoad();
	}

	public FeedbackCommentsHRKConnectorLookupComponent getFeedbackComponent() {
		if(Objects.isNull(feedbackComponent)) {
			setFeedbackComponent(new FeedbackCommentsHRKConnectorLookupComponent());
		}
		
		return feedbackComponent;
	}

	public void setFeedbackComponent(FeedbackCommentsHRKConnectorLookupComponent feedbackComponent) {
		this.feedbackComponent = feedbackComponent;
	}
}
