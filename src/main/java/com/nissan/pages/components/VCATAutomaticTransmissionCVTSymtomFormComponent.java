package com.nissan.pages.components;

import java.util.Objects;
import org.openqa.selenium.support.FindBy;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.enums.VCATCheckbox;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class VCATAutomaticTransmissionCVTSymtomFormComponent
		extends AutomaticTransmissionCVTSymptomFormComponent {

	@FindBy(xpath = "//button[text()='Diagnose']")
	public ExtWebElement btnDiagnose;
	
	@FindBy(xpath = "//h6[text()='VCAT REVIEW']")
	public ExtWebElement headerVCATReview;
	
	@FindBy(xpath = "//h6[text()='TSB/ESM Q&A']")
	public ExtWebElement headerTSBESMQA;
	
	@FindBy(xpath = "//h6[text()='CONCERN, CAUSE, CORRECTION & PFP']")
	public ExtWebElement CONCERNCAUSECORRECTIONPFP;
	
	@FindBy(xpath = "//textarea[@name='$PpyWorkPage$pAdditionalNotes']")
	public ExtWebElement txtboxVCATSupportNotes;

	//@FindBy(xpath = "//div[@node_name='FinalPaymentType']/descendant::div//span")
	@FindBy(xpath = "//div[@node_name='FinalPaymentType']/descendant::div//span[@class='standard']")
	public ExtWebElement txtPaymentType;

	@FindBy(xpath = "//h6[text()='Final Recommendation']/ancestor::div[@id='EXPAND-OUTERFRAME'][1]//descendant::div//span")
	public ExtWebElement txtFinalRepair;

	@FindBy(xpath = "//button[@title='Complete this assignment']")
	public ExtWebElement btnSubmit;

	@FindBy(xpath = "//button[@title='Cancel the current action and review this work item']")
	public ExtWebElement btnCancel;

	public ExtWebElement getCheckBoxWebElement(VCATCheckbox eleName) {

		String xPath = "//label[contains(text(),'%s')]/preceding-sibling::img";
		ExtWebElement extWebElement = getExtWebElement(String.format(xPath, eleName.getchkboxName()));
		extWebElement.moveToElement();
		return extWebElement;

	}

	public ExtWebElement getRadioButton(RadioBtnName eleName, String value) {

		String xPath = "//input[@type = 'radio' and contains(@name,'%s') and @value='%s']";
		ExtWebElement extWebElement = getExtWebElement(String.format(xPath, eleName.getRadioBtnName(), value));
		extWebElement.moveToElement();
		return extWebElement;

	}

	public enum RadioBtnName {

		SYSTEMRECOMMENDATIONRIGHT("DiagnosticOutcomeCorrect"), AGREETECHNICIANRECOMMENDATION("AgreeTechRecomm"), OVERRIDESYSTEMRECOMMENDATION("VCATRecommendation");

		private String name;

		public String getRadioBtnName() {
			return this.name;
		}

		private RadioBtnName(String name) {
			this.name = name;
		}

	}
	

	@Override
	public void waitForComponentToLoad() {

		try {
			/*
			 * driver.switchTo().defaultContent(); driver.switchTo().frame(iframe);
			 * txtHeader.waitForPresent(); // txtHeader.waitForVisible();
			 */
			waitForFrameToLoad();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}
	}

	public VCATAutomaticTransmissionCVTSymtomFormComponent() {
		waitForComponentToLoad();
	}

}
