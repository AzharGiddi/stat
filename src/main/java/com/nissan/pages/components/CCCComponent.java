package com.nissan.pages.components;

import java.util.Objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class CCCComponent extends AutomaticTransmissionCVTSymptomFormComponent {

	@FindBy(xpath = "//h6[text()='Concern, Cause, Correction & PFP']")
	public ExtWebElement headerCCC;

	@FindBy(xpath = "//input[@name='$PpyWorkPage$pPFPFailed']")
	public ExtWebElement txtBoxFailedFirstPFP;

	@FindBy(xpath = "//select[@name='$PpyWorkPage$pPFPFailedOptions']")
	public ExtWebElement dropdownWhyDoYouThinkPartFailed;

	@FindBy(xpath = "//select[@name='$PpyWorkPage$pRecommendedRepairID']")
	public ExtWebElement dropdownRecommendRepair;

	@FindBy(xpath = "//textarea[@name='$PpyWorkPage$pAdditionalRepairNotes']")
	public ExtWebElement textboxRepairJustification;

	@FindBy(xpath = "//textarea[@name='$PpyWorkPage$pDisagreeWithComments']")
	public ExtWebElement txtboxTechnicianDisagreeComments;

	@FindBy(xpath = "//textarea[@name='$PpyWorkPage$pDisagreeWithComments' or  @name='$PpyWorkPage$pAdditionalRepairNotes']")
	public ExtWebElement txtboxTechnicianComments;

	@FindBy(xpath = "//button[text()='Submit']")
	public ExtWebElement btnSubmit;

	@FindBy(xpath = "//button[text()='RE-DIAGNOSE']")
	public ExtWebElement btnReDiagnose;

	@FindBy(xpath = "//input[@type='checkbox' and @name='$PpyWorkPage$pDisagreeWithSysRecommendation']")
	public ExtWebElement chkboxTechnicianDisagrees;

	@FindBy(xpath = "//div[@aria-hidden='false']//iframe[@title]")
	public ExtWebElement iframe;

	@FindBy(xpath = "//span[text()='Repair Recommendation']/following-sibling::div[@class='field-item dataValueRead']/span")
	public ExtWebElement txtDefaultRepair;

	@Override
	public void waitForComponentToLoad() {

		try {
			
			waitForFrameToLoad();
			//ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "));

		}
	}

	public CCCComponent() {
		waitForComponentToLoad();
	}
}
