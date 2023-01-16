package com.nissan.pages.components;

import java.util.List;
import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class GoodWillRequestComponent extends ExtWebComponent {

	@FindBy(xpath = "//div[text()='Goodwill Request']")
	public ExtWebElement header;

	@FindBy(xpath = "//button[normalize-space(text())='Next']")
	public ExtWebElement btnNext;

	@FindBy(xpath = "//label[text()='Symptom Code']/following-sibling::div//input")
	public ExtWebElement txtboxSymptomCode;

	@FindBy(xpath = "//label[text()='Diagnosis Code']/following-sibling::div//input")
	public ExtWebElement txtboxDiagnosisCode;

	@FindBy(xpath = "//table[@pg_prop='.GoodWillCostList']")
	public ExtWebElement tableCostGrid;
	
	@FindBy(xpath = "//button[text()='Update Cost Split %']")
	public ExtWebElement btnUpdateCostSplit;

	@FindBy(xpath = "//textarea[@name='$PpyWorkPage$pGoodWillReason']")
	public ExtWebElement txtboxGoodWillReason;

	@FindBy(xpath = "//input[@name='$PpyWorkPage$pVehiclePurchased' and @value='New']")
	public ExtWebElement radiobtnPurchaseTypeNew;

	@FindBy(xpath = "//input[@name='$PpyWorkPage$pNotABrandedVehicle' and @type='checkbox']")
	public ExtWebElement chkboxCertifyNotBrandedTitle;

	@FindBy(xpath = "//input[@name='$PpyWorkPage$pNoServiceContracts' and @type='checkbox']")
	public ExtWebElement chkboxNoServiceContract;

	@FindBy(xpath = "//button[text()='Review & Submit']")
	public ExtWebElement btnReviewAndSubmit;

	
	@FindBy(xpath = "//div[text()='Save']")
	public ExtWebElement btnSave;

	// @FindBy(xpath = "//textarea[@name='$PpyWorkPage$pPCCComments']")
	@FindBy(xpath = "//textarea[contains(@name,'Comments')]")
	public ExtWebElement txtboxVCATComments;

	@FindBy(xpath = "//button[text()='Approve']")
	public ExtWebElement btnApprove;
	
	@FindBy(xpath = "//button[text()='Approve' and @name='CommentsSectionforFOM_pyWorkPage_15']")
	public ExtWebElement btnFOMApprove;
	
	@FindBy(xpath = "//button[text()='Modify']")
	public ExtWebElement btnModify;

	@FindBy(xpath = "//button[text()='Reject']")
	public ExtWebElement btnReject;

	@FindBy(xpath = "//span[starts-with(text(),'Thank you for your Input')]")
	public ExtWebElement txtThankYouForInput;

	@FindBy(xpath = "//div[starts-with(text(),'Please provide as much information')]")
	public ExtWebElement txtProvideInfo;

	@FindBy(xpath = "//button[text()='Add Attachments']")
	public ExtWebElement btnAddAttachment;

	@FindBy(xpath = "//span[text()='File from device']")
	public ExtWebElement btnFileFromDevice;

	@FindBy(xpath = "//input[@id='$PpyAttachmentPage$ppxAttachName']")
	public ExtWebElement btnSelectFiles;

	@FindBy(xpath = "//button[normalize-space(text())='Attach']")
	public ExtWebElement btnAttach;

	@FindBy(xpath = "//input[@name='$PpyWorkPage$pGoodWillCostList$gZ_Total$pNissanAmount']")
	public ExtWebElement txtNissanShare;

	//@FindBy(xpath = "//*[@name='$PpyWorkPage$pGoodWillCostList$gZ_Total$pTotalAmount']")
	@FindBy(xpath = "//*[ @id='CV' and @name='$PpyWorkPage$pGoodWillCostList$gZ_Total$pTotalAmount' and contains(@rhp_name,'pxNumber')]")
	public ExtWebElement txtTotal;

	@FindBy(xpath = "//*[starts-with(text(),'Thank')]")
	public ExtWebElement txtThankYou;

	@FindBy(xpath = "//button[text()='Update VCAN']")
	public ExtWebElement btnUpdateVCAN;

	@FindBy(xpath = "//input[@name='$PpyWorkPage$pCustomerPayAmount']")
	public ExtWebElement txtboxCustomerPayAmount;

	@FindBy(xpath = "//input[@type='checkbox' and @name='$PpyWorkPage$pRequestGridException']")
	public ExtWebElement chkboxRequestGridException;

	@FindBy(xpath = "//input[@name='$PpyWorkPage$pRequestedNissanPercentage']")
	public ExtWebElement txtboxRequestedNissanContribution;

	@FindBy(xpath = "//textarea[@name='$PpyWorkPage$pExceptionReason']")
	public ExtWebElement txtboxExceptionReason;

	@FindBy(xpath = "//button[text()='Update']")
	public ExtWebElement btnUpdate;

	@FindBy(xpath = "//div[text()='Updated Cost Split Grid Successfully.']")
	public ExtWebElement txtUpdateCost;

	@FindBy(xpath = "//button[normalize-space(text())='Route to RAM']")
	public ExtWebElement btnRouteToRAM;

	@FindBy(xpath = "//textarea[@name='$PpyWorkPage$pRAMComments']")
	public ExtWebElement txtboxRAMComments;

	@FindBy(xpath = "//div[starts-with(text(),'Your RAM has denied')]")
	public ExtWebElement txtRAMDenied;

	@FindBy(xpath = "//input[@type='checkbox' and @name='$PpyWorkPage$pVisibletoDealer']")
	public ExtWebElement chkboxVisibleToDealer;

	@FindBy(xpath = "//button[text()='Route to Dealer']")
	public ExtWebElement btnRouteToDealer;
		
	@FindBy(xpath = "//button[text()='Accept and Continue']")
	public ExtWebElement btnAcceptAndContinue;
	
	
	@FindBy(xpath = "//button[text()='Deny Exception']")
	public ExtWebElement btnDenyException;
	
	@FindBy(xpath = "//button[text()='Approve Exception']")
	public ExtWebElement btnApproveException;
	
	@FindBy(xpath = "//button[text()='Conditionally Approve']")
	public ExtWebElement btnConditionallyApprove;
	
	@FindBy(xpath = "//div[text()='DIAGNOSTIC CASES']")
	public ExtWebElement txtHeaderDiagnosticCases;
	
	@FindBy(xpath = "//h6[text()='Cost Split']")
	public ExtWebElement txtHeaderCostGrid;
	
	@FindBy(xpath = "//button[text()='Confirm & Proceed']")
	public ExtWebElement btnConfirmAndProceed;
	
	@FindBy(xpath = "//button[text()='Add Coupon']")
	public ExtWebElement btnAddCoupon;
	
	@FindBy(xpath = "//a[contains(@name,'DisplayDiagnosticCases_D_DCasesWithApprovedRepair')]")
	public List<ExtWebElement> listDcasesWithApprovedRepair;
	
	@FindBy(xpath = "//span[text()='Is the customer likely to be come back for service?']")
	public ExtWebElement labelIsTheCustomerLikelyToBecomeBackForService;
	
	@FindBy(xpath = "//span[text()='Is the customer likely to be come back for service?']/following-sibling::div/span[text()='YES' or text()='NO']")
	public ExtWebElement txtIsTheCustomerLikelyToBecomeBackForService;
	
	@FindBy(xpath = "//div[text()='Error']")
	public ExtWebElement txtError;
	
	@FindBy(xpath = "//div[text()='Note: Per system determination the vehicle is covered under warranty']")
	public ExtWebElement txtUnderWarranty;
	
	@FindBy(xpath = "//table[@grid_ref_page='dragDropFileUpload']")
	public ExtWebElement tableAttachments;

	private UpdateVCANComponent updateVCANComponent;

	public UpdateVCANComponent getUpdateVCANComponent() {
		if (Objects.isNull(updateVCANComponent))
			setUpdateVCANComponent(new UpdateVCANComponent());

		return updateVCANComponent;
	}

	public void setUpdateVCANComponent(UpdateVCANComponent updateVCANComponent) {
		this.updateVCANComponent = updateVCANComponent;
	}

	private String textBoxXpath = "//label[text()='%s']/following-sibling::div//input";

	public List<ExtWebElement> getCostList(String costListName) {

		String xPath = String.format(
				"//input[contains(@name,'GoodWillCostList$g%s') and @disabled='' and @data-format-method='inclFormattedVal_5']",
				costListName);
		return ExtWebComponent.getExtWebElements(xPath);

	}

	public ExtWebElement getTextBox(String textboxName) {
		String xPath = String.format(textBoxXpath, textboxName);
		return ExtWebComponent.getExtWebElement(xPath,textboxName).moveToElement();

	}

	/**
	 * Use this method to fetch header elements.
	 * 
	 * Please use only for to fetch following elements: <b>Case ID,Pending
	 * With,Payment Assumption,Dealer,RO # /WO #,Customer,VIN,Model,Year,Mileage</b>
	 * 
	 * @param eleName
	 *            Name of the header element
	 * @return ExtWebElement
	 */
	public ExtWebElement getHeaderWebElement(String eleName) {

		String xPath = "//span[text()='%s']/following-sibling::div/child::*";

		return getExtWebElement(String.format(xPath, eleName), eleName);
	}

	@Override
	public void waitForComponentToLoad() {
		try {

			//DriverManager.getDriver().switchTo().defaultContent();
			waitForFrameToLoad();
			header.waitForPresent();

		} catch (Exception e) {

			throw new PageNotFoundException(this.getClass().getSimpleName() + " did not load.");

		}
	}

	public GoodWillRequestComponent() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		waitForComponentToLoad();
	}
}
