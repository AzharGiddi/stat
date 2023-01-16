package com.nissan.bdd.fone.stepdefinitions;

import static com.nissan.databeans.TestDataBeanManager.getTestData;

import java.util.Objects;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.APIUtil;
import com.nissan.automation.core.utils.BDDDataTableUtil;
import com.nissan.automation.core.utils.DateUtil;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.enums.Roles;
import com.nissan.enums.UserData;
import com.nissan.pages.EngManagerPage;
import com.nissan.pages.ServiceAdvisorPage;
import com.nissan.pages.components.RepairOptionsComponent;
import com.nissan.pages.components.USSComponent;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.DViewVCANUtil;
import com.nissan.utils.ROAPIUtil;
import com.nissan.utils.WarrantyLineAPIUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class CommonSteps {

	
	private EngManagerPage engManagerPage = new EngManagerPage(); 
	private RepairOptionsComponent repairOptionsComponent=null;
	private static boolean isRepairOrderSet=false;
	
	@Given("Login as EngManager")
	public void login_as_eng_manager() {
		
	}
	
	
	@Given("EngManager adds below PFPs to the Restricted Parts repair options")
	public void eng_manager_adds_below_pf_ps_to_the_repair_options(io.cucumber.datatable.DataTable dataTable) {
		if(isRepairOrderSet)
			return;
		engManagerPage.login(Roles.ENGINEERINGMANAGER);
		engManagerPage.waitForPageToLoad();
		String componentCategory = "Restricted Parts";
		engManagerPage.getLeftSideMenuElement("Manage System Rules").click();
		engManagerPage.getManageSystemRulesComponent().getMenuElement("Repair Options").click();
		repairOptionsComponent = new RepairOptionsComponent();
		repairOptionsComponent.addPFPs(dataTable, componentCategory);
		engManagerPage.logOut();	
		isRepairOrderSet=true;
	}
	
	
	@Given("EngManager adds below PFPs to the Audio repair options")
	public void eng_manager_adds_below_pf_ps_to_the_audio_repair_options(io.cucumber.datatable.DataTable dataTable) {
		if(isRepairOrderSet)
			return;
		engManagerPage.login(Roles.ENGINEERINGMANAGER);
		engManagerPage.waitForPageToLoad();
		String componentCategory = "Audio";
		engManagerPage.getLeftSideMenuElement("Manage System Rules").click();
		engManagerPage.getManageSystemRulesComponent().getMenuElement("Repair Options").click();
		repairOptionsComponent = new RepairOptionsComponent();
		repairOptionsComponent.addPFPs(dataTable, componentCategory);
		engManagerPage.logOut();	
		isRepairOrderSet=true;
	}
	
	@Given("EngManager adds below PFPs to the CVT repair options")
	public void eng_manager_adds_below_pf_ps_to_the_cvt_repair_options(io.cucumber.datatable.DataTable dataTable) {
		if(isRepairOrderSet)
			return;
		engManagerPage.login(Roles.ENGINEERINGMANAGER);
		engManagerPage.waitForPageToLoad();
		String componentCategory = "CVT";
		engManagerPage.getLeftSideMenuElement("Manage System Rules").click();
		engManagerPage.getManageSystemRulesComponent().getMenuElement("Repair Options").click();
		repairOptionsComponent = new RepairOptionsComponent();
		repairOptionsComponent.addPFPs(dataTable, componentCategory);
		engManagerPage.logOut();	
		isRepairOrderSet=true;
	}
	
	
	
	@Then("Dcase is resolved and summary screen is displayed")
	public void dcase_is_resolved_and_summary_screen_is_displayed() {

		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']").verifyText("Diagnostic Case Summary", "Summary Screen Header");
		

	}
	
	@Then("Verify goodWill Coverage Type")
	public void verify_good_will_coverage_is() {
		String expectedCoverageType = getTestData().getUserData().get(UserData.COVERAGETYPE);
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+"cases/NSA-STAT-WORK " + getTestData().getGWCaseID();
		String actualCoverageType = APIUtil.getResponseObject(url).jsonPath().getString("content.GWCoverageType");
		Validator.assertTrue(expectedCoverageType.equals(actualCoverageType), "Expected coverage Type: "+expectedCoverageType +"matches Actual Coverage Type: "+actualCoverageType,"Expected coverage Type: "+expectedCoverageType +"does not match Actual Coverage Type: "+actualCoverageType);
	}
	
	
	@Then("GoodWill Case is assigned to {string}")
	public void good_will_case_is_assigned_to(String string) {
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+"cases/NSA-STAT-WORK " + getTestData().getGWCaseID();
		String status = APIUtil.getResponseObject(url).jsonPath().getString("content.PendingWith");
		
		long starTime = System.currentTimeMillis();
		while (Objects.isNull(status) || !status.equals(string)) {
			long actualTime = System.currentTimeMillis();
			if(actualTime-starTime>30000)
				break;
			ExtentLogger.info("Case is with: "+status);
			status = APIUtil.getResponseObject(url).jsonPath().getString("content.PendingWith");
		}
		Assert.assertTrue(status.equals(string), "Expected: " + string + ",Actual: " + status);
	}
	
	@Then("Dcase is assigned to {string}")
	public void dcase_is_assigned_to(String string) {
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+"cases/NSA-STAT-WORK-F1 " + getTestData().getDCaseID();
		String status = APIUtil.getResponseObject(url).jsonPath().getString("content.PendingWith");
		long starTime = System.currentTimeMillis();
		while (!status.equals(string)) {
			long actualTime = System.currentTimeMillis();
			if(actualTime-starTime>30000)
				break;
			ExtentLogger.info("Case is with: "+status);
			status = APIUtil.getResponseObject(url).jsonPath().getString("content.PendingWith");
		}
		Assert.assertTrue(status.equals(string), "Expected: " + string + ",Actual: " + status);
	}

	
	@Then("Verify latest claim status is updated to {string}")
	public void verify_latest_claim_status_is_updated_to(String string) {
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+"cases/NSA-STAT-WORK " + getTestData().getGWCaseID();
		WaitUtil.sleep(2000);
		// String lastClaimStatus =
		// APIUtil.getResponseString(url).jsonPath().getString("content.LatestClaimStatus");
		String latestClaimStatus = "";
		String workObjectStatus = "";
		String expWorkObjectStatus = "";
		//int count = 29;
		boolean routeToFOM = false;

		if (string.equals("Submitted") && Boolean.parseBoolean((getTestData().getUserData().get(UserData.WLGTDCAL)))) {
			expWorkObjectStatus = "Pending-ClaimAmountMismatch";
			routeToFOM = true;

		} else {
			expWorkObjectStatus = "Resolved-Completed";
		}
		long start = System.currentTimeMillis();
		while (!workObjectStatus.equals(expWorkObjectStatus)) {
			workObjectStatus = APIUtil.getResponseObject(url).jsonPath().getString("content.pyStatusWork");
			ExtentLogger.info("WO status:" + workObjectStatus + ", LatestClaimStatus:" + latestClaimStatus, false);
			long end = System.currentTimeMillis();
			// if (count-- == 0)
			// break;

			if ((end - start) > 60000) {
				break;
			}
		}

		start = System.currentTimeMillis();
		//count = 29;
		while (Objects.isNull(latestClaimStatus) || !latestClaimStatus.equals(string)) {
			latestClaimStatus = APIUtil.getResponseObject(url).jsonPath().getString("content.LatestClaimStatus");
			ExtentLogger.info(latestClaimStatus, false);
			long end = System.currentTimeMillis();
			// if (count-- == 0)
			// break;

			if ((end - start) > 60000) {
				break;
			}
		}

		Validator.assertText(string, latestClaimStatus,
				"Expected LatestClaimStatus: " + string + ", Actual LatestClaimStatus: " + latestClaimStatus);

		if (routeToFOM) {

		}
	}
	
	@Given("Verify VCAN creation")
	public void verify_vcan_created() {
	   boolean expectedVCANCreation = getTestData().getUserData().get(UserData.COVERAGECODE).equals("FG");
	   String dealerCode=getTestData().getUserData().get(UserData.DEALERCODE);
	   String vIN = getTestData().getUserData().get(UserData.VIN);
	   String rOL="1";
	   String rONumber = getTestData().getRoNumber();
	   String vcanRef = "";
	   String pFPPO = "";
	   /*getTestData().setVCANRefNum(DViewVCANUtil.getVCAN(dealerCode, vIN, rOL, rONumber));
	   String vcanRef = getTestData().getVCANRefNum();*/
	 if(expectedVCANCreation) {
		 vcanRef = DViewVCANUtil.getVCAN(dealerCode, vIN, rOL, rONumber);
		 pFPPO = DViewVCANUtil.getPFPPO(dealerCode, vIN, rOL, rONumber);
		 Validator.assertTrue(Objects.nonNull(vcanRef),"VCAN created, with VCANREFNUM: "+vcanRef,"VCAN not created");
	 }
	 getTestData().setVCANRefNum(vcanRef);
	 getTestData().setVCANPFPPO(pFPPO);
	}
	
	@Then("Verify VCAN deletion")
	public void verify_vcan_deletion() {
		//boolean deleteVCAN = Boolean.parseBoolean(getTestData().getUserData().get(UserData.WLGTNISSANAMOUNT));
		boolean retainVCAN = WarrantyLineAPIUtil.getTotalPaidAmount()>getTestData().getdCal();
		String coverageCode = getTestData().getUserData().get(UserData.COVERAGECODE);
		 String dealerCode=getTestData().getUserData().get(UserData.DEALERCODE);
		   String vIN = getTestData().getUserData().get(UserData.VIN);
		   String rOL="1";
		   String rONumber = getTestData().getRoNumber();
		  // String oldVCAN = Objects.isNull(getTestData().getVCANRefNum())?"":getTestData().getVCANRefNum();
		   String oldVCANPFPPO = Objects.isNull(getTestData().getVCANPFPPO())?"":getTestData().getVCANPFPPO();
		 //getTestData().setVCANRefNum(DViewVCANUtil.getVCAN(dealerCode, vIN, rOL, rONumber));
		   getTestData().setVCANPFPPO(DViewVCANUtil.getPFPPO(dealerCode, vIN, rOL, rONumber));
		 //String vcanRef = getTestData().getVCANRefNum();
		 String vCANPFPPO = getTestData().getVCANPFPPO();
		if(retainVCAN) {
			//Validator.assertTrue((Objects.nonNull(vcanRef) && vcanRef.equals(oldVCAN)) || (coverageCode.equals("FW") && oldVCAN.equals("")),"VCAN retained, VCAN before submitting WL: "+oldVCAN+" VCAN after submitting WL: "+vcanRef,"");		 
			Validator.assertTrue((Objects.nonNull(vCANPFPPO) && vCANPFPPO.equals(oldVCANPFPPO)) || (coverageCode.equals("FW") && oldVCANPFPPO.equals("")),"VCAN retained, VCAN before submitting WL: "+oldVCANPFPPO+" VCAN after submitting WL: "+vCANPFPPO,"");
		}else {
			 
			Validator.assertTrue(Objects.isNull(vCANPFPPO) || vCANPFPPO.equals(""),"VCAN deleted, VCAN before submitting WL: "+oldVCANPFPPO+" VCAN after submitting WL: "+vCANPFPPO,"VCAN not deleted, VCAN before submitting WL: "+oldVCANPFPPO+" VCAN after submitting WL: "+vCANPFPPO);

		 }
	}
	
	@Then("Verify temp VCAN deletion and approved VCAN creation")
	public void verify_temp_vcan_deletion_and_approved_vcan_creation() {
		//boolean deleteVCAN = Boolean.parseBoolean(getTestData().getUserData().get(UserData.WLGTNISSANAMOUNT));
		boolean retainVCAN = WarrantyLineAPIUtil.getTotalPaidAmount()>getTestData().getdCal();
		String coverageCode = getTestData().getUserData().get(UserData.COVERAGECODE);
		 String dealerCode=getTestData().getUserData().get(UserData.DEALERCODE);
		   String vIN = getTestData().getUserData().get(UserData.VIN);
		   String rOL="1";
		   String rONumber = getTestData().getRoNumber();
		   String oldVCANPFPPO = Objects.isNull(getTestData().getVCANPFPPO())?"":getTestData().getVCANPFPPO();
		   String pFP = getTestData().getUserData().get(UserData.PFP);
		   getTestData().setVCANPFPPO(DViewVCANUtil.getPFPPO(dealerCode, vIN, rOL, rONumber));
		   String vCANPFPPO = getTestData().getVCANPFPPO();
		  /* if(coverageCode.equals("FW") && oldVCANPFPPO.equals("")) {
			   
		   }*/
		  // Validator.assertTrue((!vCANPFPPO.equals("PEGADMYPRT")),"Temp VCAN deleted, VCAN PFPPO before submitting WL: "+oldVCANPFPPO+" VCAN PFPPO after submitting WL: "+vCANPFPPO,"Temp VCAN not deleted, VCAN PFPPO before submitting WL: "+oldVCANPFPPO+" VCAN PFPPO after submitting WL: "+vCANPFPPO);
		   /*if(retainVCAN) {
				 
			//Validator.assertTrue((Objects.nonNull(vCANPFPPO) && vCANPFPPO.equals(pFP)) || (coverageCode.equals("FW") && oldVCANPFPPO.equals("")),"VCAN retained, VCAN before submitting WL: "+oldVCANPFPPO+" VCAN after submitting WL: "+vCANPFPPO,"");
			   Validator.assertTrue((vCANPFPPO.equals("PEGADMYPRT")),"Temp VCAN retained, VCAN PFPPO before submitting WL: "+oldVCANPFPPO+" VCAN PFPPO after submitting WL: "+vCANPFPPO,"Temp VCAN not retained, VCAN PFPPO before submitting WL: "+oldVCANPFPPO+" VCAN PFPPO after submitting WL: "+vCANPFPPO);
		}else {
			 
			Validator.assertTrue(vCANPFPPO.equals(pFP),"Temp VCAN deleted, VCAN PFPPO before submitting WL: "+oldVCANPFPPO+" VCAN PFPPO after submitting WL: "+vCANPFPPO,"VCAN not deleted, VCAN PFPPO before submitting WL: "+oldVCANPFPPO+" VCAN PFPPO after submitting WL: "+vCANPFPPO);

		 }*/
		   
		   Validator.assertTrue(vCANPFPPO.equals(pFP),"VCAN PFPPO before submitting WL: "+oldVCANPFPPO+" VCAN PFPPO after submitting WL: "+vCANPFPPO,"VCAN not deleted, VCAN PFPPO before submitting WL: "+oldVCANPFPPO+" VCAN PFPPO after submitting WL: "+vCANPFPPO);
	}
	
	@Given("Create RO for good will request with below set of details")
	public void create_ro_for_good_will_request_with_below_set_of_details(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		//creating RO and adding it to test data bean
		//getTestData().setdCal(DDcalUtil.getDcalAmount(getTestData().getUserData().get(UserData.DEALERCODE)));
		getTestData().setRoNumber(ROAPIUtil.createGWRO());
	}
	
	@Then("Verify case status is changed to {string} and latest claim status is updated to {string}")
	public void verify_latest_claim_status_is_updated_to(String expWorkObjectStatus, String expLatestClaimStatus) {
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+"cases/NSA-STAT-WORK " + getTestData().getGWCaseID();
		WaitUtil.sleep(2000);
		String actLatestClaimStatus = "";
		String actWorkObjectStatus = "";
		int count = 29;
		while (!actWorkObjectStatus.equals(expWorkObjectStatus)) {
			actWorkObjectStatus = APIUtil.getResponseObject(url).jsonPath().getString("content.pyStatusWork");
			ExtentLogger.info("WO status:" + actWorkObjectStatus + ", LatestClaimStatus:" + actLatestClaimStatus,
					false);
			if (count-- == 0)
				break;
		}
		Validator.assertText(expWorkObjectStatus, actWorkObjectStatus,"case status");
		count = 29;
		while (Objects.isNull(actLatestClaimStatus) || !actLatestClaimStatus.equals(expLatestClaimStatus)) {
			actLatestClaimStatus = APIUtil.getResponseObject(url).jsonPath().getString("content.LatestClaimStatus");
			ExtentLogger.info(actLatestClaimStatus, false);
			if (count-- == 0)
				break;
		}

		Validator.assertText(expLatestClaimStatus, actLatestClaimStatus, "LatestClaimStatus");

	}
	
	@Then("Verify dcase case status is changed to {string} and latest claim status is updated to {string}")
	public void verify_dcase_latest_claim_status_is_updated_to(String expWorkObjectStatus, String expLatestClaimStatus) {
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+"cases/NSA-STAT-WORK-F1 " + getTestData().getDCaseID();
		WaitUtil.sleep(2000);
		String actLatestClaimStatus = "";
		String actWorkObjectStatus = "";
		long startTime=System.currentTimeMillis();
		long endTime=System.currentTimeMillis();
		while (!actWorkObjectStatus.equals(expWorkObjectStatus)) {
			actWorkObjectStatus = APIUtil.getResponseObject(url).jsonPath().getString("content.pyStatusWork");
			ExtentLogger.info("WO status:" + actWorkObjectStatus + ", LatestClaimStatus:" + actLatestClaimStatus,
					false);
			endTime=System.currentTimeMillis();
			if (endTime-startTime>30000)
				break;
		}
		Validator.assertText(expLatestClaimStatus, actLatestClaimStatus, "LatestClaimStatus");
	}
	
	

	@Then("Verify PaidAmount, PaidDate and PaidFY")
	public void verify_paid_amount_paid_date_and_paid_fy() {
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+"cases/NSA-STAT-WORK " + getTestData().getGWCaseID();
		Response response = APIUtil.getResponseObject(url);
/*		int totalAmount = (int)Math.round(WarrantyLineAPIUtil.getTotalPaidAmount());
		int paidAmount = (int) Math.round(response.jsonPath().getDouble("content.PaidAmount"));*/
		double totalAmount = WarrantyLineAPIUtil.getTotalPaidAmount();
		double paidAmount = response.jsonPath().getDouble("content.PaidAmount");
		Validator.verifyTrue(totalAmount,paidAmount, "PaidAmount");
		String paidDate = response.jsonPath().getString("content.PaidDate");
		String expectedPaidDate = new LocalDate(DateTimeZone.UTC).toString();
		Validator.verifyTrue(paidDate.startsWith(expectedPaidDate), "PaidDate");
		int expectedFYYear = DateUtil.getCurrentFiscalYear();
		int actualFYYear = response.jsonPath().getInt("content.PaidFY");
		Validator.verifyTrue(expectedFYYear,actualFYYear, "PaidFY");
				
	}
	
	@Then("Update Warranty line xml with new part, labor and expense amounts and submit with below status")
	public void update_warranty_line_xml_with_new_part_labor_and_expense_amounts_and_submit_with_below_status(io.cucumber.datatable.DataTable dataTable) {
	   
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		WarrantyLineAPIUtil.submitWarrantyLine(getTestData());
		
		
		
	}
	
	private USSComponent uSSComponent;
	private ServiceAdvisorPage serviceAdvisor;
	@Given("Login as Service advisor {string}")
	public void login_as_service_advisor(String string) {
		serviceAdvisor = new ServiceAdvisorPage();
		serviceAdvisor.login(string);
	}
	@When("Click on Symptom Survey Beta link")
	public void click_on_symptom_survey_beta_link() {
		
		serviceAdvisor.getLeftSideMenuElement("Symptom Survey Beta").click();
	}
	@Then("Customer Concerns component is displayed")
	public void customer_concerns_component_is_displayed() {
		uSSComponent = new USSComponent();
	}
	@Then("Enter VIN {string} on customer concerns component and click on go")
	public void enter_vin_on_customer_concerns_component(String string) {
		uSSComponent = new USSComponent();
		uSSComponent.txtBoxVIN.sendKeys(string);
		uSSComponent.btnGo.click();
	}
	@Then("Click on add concern button")
	public void click_on_add_concern_button() {
		uSSComponent.btnAddAConcern.click();
	}
	@Then("Click on I see link")
	public void click_on_i_see_link() {
		uSSComponent.linkISee.scrollAndClick();
	}
	@Then("Select {string} check box and click ok")
	public void select_check_box_and_click_ok(String string) {
		uSSComponent.chkboxPoorAppearance.click();
		uSSComponent.btnOk.scrollAndClick();
	}
	@Then("Click on add to summary")
	public void click_on_add_to_summary() {
		uSSComponent.btnAddToSUmaryScreen.click();
	}
	@Then("{string} is displayed")
	public void is_displayed(String string) {
	    		
	    
	}
	@Then("CLick on next button")
	public void c_lick_on_next_button() {
		uSSComponent = new USSComponent();
		uSSComponent.btnNext.scrollAndClick();
	}
	@Then("Coverages Component is displayed")
	public void coverages_component_is_displayed() {
	   
	}
	@Then("Click on next")
	public void click_on_next() {
		WaitUtil.sleep(2000);
		uSSComponent.btnNext.scrollAndClick();
	}
	@Then("Select paytype as Factory Warranty")
	public void select_paytype_as_factory_warranty() {
		uSSComponent = new USSComponent();
		WaitUtil.sleep(2000);
		uSSComponent.dropDownPaytype.select("Factory Warranty");
		WaitUtil.sleep(10000);
	}
	
	@Then("Submit WarrantyLine for dcase with below values")
	public void submit_warranty_line_for_dcase_with_below_values(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		WarrantyLineAPIUtil.submitWarrantyLineForDcase(getTestData());
	}
	
	@Then("Verify Claimed amount in WL is over the approved repair is updated in VCAN approval comments")
	public void verify_claimed_amount_in_wl_is_over_the_approved_repair_is_updated_in_vcan_approval_comments() {
		
		String dCaseId = getTestData().getDCaseID();
		String approvalComments = String.format("Claimed amount in WL is over the approved repair amount setup in F1 App (%s)",dCaseId);
		String url = ConfigurationManager.getBundle().getString("base.uri")+"cases/NSA-STAT-WORK-F1 "+ dCaseId;
		Response response = APIUtil.getResponseObject(url);
		String actualComments = response.jsonPath().getString("content.DiagnosticVCANInfo.ApprovalComments");
		Validator.assertTrue(approvalComments.equals(actualComments), "Expected comments "+approvalComments+" matches actual comments "+actualComments,"Expected comments "+approvalComments+" does matches actual comments "+actualComments);
		
	}
	
	@Then("Verify Approved by F1 msg is updated in VCAN approval comments")
	public void verify_approved_by_f1_msg_is_updated_in_vcan_approval_comments() {
		String dCaseId = getTestData().getDCaseID();
		String approvalComments = String.format("Approved by F1. %s",dCaseId);
		String url = ConfigurationManager.getBundle().getString("base.uri")+"cases/NSA-STAT-WORK-F1 "+ dCaseId;
		Response response = APIUtil.getResponseObject(url);
		String actualComments = response.jsonPath().getString("content.DiagnosticVCANInfo.ApprovalComments");
		Validator.assertTrue(approvalComments.equals(actualComments), "Expected comments "+approvalComments+" matches actual comments "+actualComments,"Expected comments "+approvalComments+" does matches actual comments "+actualComments);
		
	}
	
	@Test
	public void test() {
		/*String dcase="D-64422";
		String approvalComments = String.format("Claimed amount in WL is over the approved repair amount setup in F1 App (%s)",dcase);
		String url = ConfigurationManager.getBundle().getString("base.uri")+"cases/NSA-STAT-WORK-F1 "+ dcase;
		Response response = APIUtil.getResponseString(url);
		String actualComments = response.jsonPath().getString("content.DiagnosticVCANInfo.ApprovalComments");*/
		
		
		
		
	}
	
	
	
}
