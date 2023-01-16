package com.nissan.pages.components;

import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class HRKConnectorDetailsComponent extends ExtWebComponent {

	private HRKConnectorInformationComponent connectorInformationComponent;
	
	private HRKAdditionalInformationComponent additionalInformationComponent;
	
	private HRKApplicableModelYearComponent hRKApplicableModelYearComponent;
	
	@FindBy(xpath = "//div[starts-with(text(),'Thank you for your comment')]")
	public ExtWebElement txtThankYouForYourComment;
	
	@FindBy(xpath = "//button[@class='Feedback pzhc pzbutton']")
	public ExtWebElement btnFeedback;
	
	public ExtWebElement getSectionHeader(String header) {
		
		String xpath = String.format("//h6[text()='%s']", header);
		
		return getExtWebElement(xpath);
		
	}

	

	@Override
	public void waitForComponentToLoad() {
		try {
			waitForFrameToLoad();
						
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}
	}

	public HRKConnectorDetailsComponent() {

		waitForComponentToLoad();
	}
	
	public HRKAdditionalInformationComponent getAdditionalInformationComponent() {
		if(Objects.isNull(additionalInformationComponent)) {
			setAdditionalInformationComponent(new HRKAdditionalInformationComponent());
		}
		
		return additionalInformationComponent;
		}

		public void setAdditionalInformationComponent(HRKAdditionalInformationComponent additionalInformationComponent) {
			this.additionalInformationComponent = additionalInformationComponent;
		}



		public HRKApplicableModelYearComponent gethRKApplicableModelYearComponent() {
			if(Objects.isNull(hRKApplicableModelYearComponent))
				sethRKApplicableModelYearComponent(new HRKApplicableModelYearComponent());
			
			return hRKApplicableModelYearComponent;
		}



		public void sethRKApplicableModelYearComponent(HRKApplicableModelYearComponent hRKApplicableModelYearComponent) {
			this.hRKApplicableModelYearComponent = hRKApplicableModelYearComponent;
		}



		public HRKConnectorInformationComponent getConnectorInformationComponent() {
			if(Objects.isNull(connectorInformationComponent)) {
				setConnectorInformationComponent(new HRKConnectorInformationComponent());
			}
			
			return connectorInformationComponent;
		}



		public void setConnectorInformationComponent(HRKConnectorInformationComponent connectorInformationComponent) {
			this.connectorInformationComponent = connectorInformationComponent;
		}


	
}
