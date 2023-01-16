package com.nissan.bdd.fone.stepdefinitions;

import static com.nissan.databeans.TestDataBeanManager.getTestData;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.APIUtil;
import com.nissan.automation.core.utils.BDDDataTableUtil;
import com.nissan.automation.core.utils.DateUtil;
import com.nissan.automation.core.utils.StringMatcher;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.driver.DriverManager;
import com.nissan.enums.Roles;
import com.nissan.enums.UserData;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.pages.BasePage;
import com.nissan.pages.ServiceManagerPage;
import com.nissan.pages.components.AddNewCouponPopUpComponent;
import com.nissan.pages.components.ManageGWDelegationComponent;
import com.nissan.pages.components.SystemSettingsComponent;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.DVehicleWarrantyUtil;
import com.nissan.utils.GWCostGridUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ServiceManagerSteps {

	private ServiceManagerPage serviceManagerPage;

	public ServiceManagerSteps() {
		serviceManagerPage = new ServiceManagerPage();

	}

	@When("Login as ServiceManager")
	public void login_as_service_manager() {
		serviceManagerPage = new ServiceManagerPage();
		serviceManagerPage.login(Roles.SERVICEMANAGER, getTestData().getUserData().get(UserData.DEALERCODE));
		serviceManagerPage.waitForPageToLoad();
		
	}

	@When("ServiceManager clicks on New GoodWill request")
	public void service_manager_clicks_on_new_good_will_request() {

		serviceManagerPage.getLeftSideMenuElement("New Goodwill Request").click();

	}

	@When("selects the ro from the SELECT THE REPAIR ORDER table")
	public void selects_the_ro_from_the_select_the_repair_order_table() {
		String rONumber = getTestData().getRoNumber();
		serviceManagerPage.getSelectROTableComponent().filterAndClick(rONumber);

	}

	@When("Select repair order screen it displayed and select repair line")
	public void select_repair_order_screen_it_displayed_and_select_repair_line() {
		serviceManagerPage = new ServiceManagerPage();
		serviceManagerPage.getSelectedRepairOrderComponent().selectROL(0);
		serviceManagerPage.getGoodWillRequestComponent().waitForComponentToLoad();
	}
	
	@When("Select repair order screen it displayed and select coupon {string} and select repair order line")
	public void select_repair_order_screen_it_displayed_and_select_coupon_and_select_repair_order_line(String string) {
		boolean applyCoupon = Boolean.parseBoolean("string");
		serviceManagerPage.getSelectedRepairOrderComponent().selectCoupon(applyCoupon);
		serviceManagerPage.getGoodWillRequestComponent().waitForComponentToLoad();
	}

	@When("ServiceManager enters symptom code and diagnosis code")
	public void service_manager_enters_symptom_code_and_diagnosis_code(io.cucumber.datatable.DataTable dataTable) {
		getTestData().setUserData(BDDDataTableUtil.getAsMap(dataTable));
		String gWCaseID = serviceManagerPage.getGoodWillRequestComponent().getHeaderWebElement("Case ID").getText();
		getTestData().setGWCaseId(gWCaseID);
		ExtentLogger.info("GoodWill Case created with case id:" + gWCaseID);
		serviceManagerPage.getGoodWillRequestComponent().getTextBox("Symptom Code").clearAndSendKeys("1234");
		WaitUtil.sleep(2000);
		serviceManagerPage.getGoodWillRequestComponent().getTextBox("Diagnosis Code").clearAndSendKeys("1234");

	}

	@When("ServiceManager clicks on Next")
	public void service_manager_clicks_on_next() {
		serviceManagerPage.getGoodWillRequestComponent().btnNext.click();
		serviceManagerPage.getGoodWillRequestComponent().waitForLoadingAnimationToComplete();
		//WaitUtil.sleep(5000);
	}

	@Then("Cost grid is displayed and Servicemanager verifies split percentages")
	public void cost_grid_is_displayed_and_servicemanager_verifies_split_percentages() {
		String[] costGrid = new String[] { "Expense", "Part", "Labor" };
		String coverageType = getTestData().getUserData().get(UserData.COVERAGETYPE);
		String make = getTestData().getVehicleRefDetails().get("Make");
		String model = getTestData().getVehicleRefDetails().get("ModelLineCode");
		String roOpenDateString = getTestData().getrOOpenDate();
		String mileage = getTestData().getMileage();
		String modelYear = getTestData().getVehicleRefDetails().get("ModelYearVersionNumber");
		String vin = getTestData().getUserData().get(UserData.VIN);
		String pfp = getTestData().getUserData().get(UserData.PFP);
		String opCode = getTestData().getUserData().get(UserData.OPCODE);
		// coverageType="Powertrain";
		Map<String, String> vehicleWarranty = DVehicleWarrantyUtil.getVehicleWarrantyMap(make, modelYear, mileage,
				opCode, pfp, vin, roOpenDateString.replace("-", ""));
		serviceManagerPage = new ServiceManagerPage();
		serviceManagerPage.getGoodWillRequestComponent().tableCostGrid.scrollIntoView();

		Map<String, String> costGridMap = GWCostGridUtil.getCostGrid_1(coverageType, make, model, roOpenDateString,
				vehicleWarranty);

		List<String> expectedCostList = GWCostGridUtil.getCostGridArray(costGridMap);
		int index = 0;
		for (String costListName : costGrid) {
			int j = 0;
			List<ExtWebElement> actualCostList = serviceManagerPage.getGoodWillRequestComponent()
					.getCostList(costListName);
			actualCostList.get(index).verifyAttribute("data-value", expectedCostList.get(j++),
					costListName + "Customer Share");
			actualCostList.get(index).verifyAttribute("data-value", expectedCostList.get(j++),
					costListName + "Dealer Share");
			actualCostList.get(index).verifyAttribute("data-value", expectedCostList.get(j),
					costListName + "Nissan Share");
			index++;
		}

	}

	@Then("Cost grid is displayed")
	public void cost_grid_is_displayed() {

		
		serviceManagerPage = new ServiceManagerPage();
		//WaitUtil.sleep(5000);
		
		serviceManagerPage.getGoodWillRequestComponent().btnUpdateCostSplit.scrollIntoView();
		serviceManagerPage.getGoodWillRequestComponent().txtHeaderCostGrid.scrollIntoView().assertVisible();

	}
	
	@Then("Servicemanager verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value")
	public void servicemanager_verifies_is_the_customer_likely_to_be_come_back_for_service_field_is_displayed_with_yes_or_no_as_autopopulated_value() {
		serviceManagerPage.getGoodWillRequestComponent().labelIsTheCustomerLikelyToBecomeBackForService.scrollIntoView().assertPresent();
		serviceManagerPage.getGoodWillRequestComponent().txtIsTheCustomerLikelyToBecomeBackForService.assertPresent();
		String value = serviceManagerPage.getGoodWillRequestComponent().txtIsTheCustomerLikelyToBecomeBackForService.getText();
		ExtentLogger.info("Is the customer likely to be come back for service? field is displayed with value="+value, true);
	}

	@Then("Servicemanager verifies split percentages")
	public void Servicemanager_verifies_split_percentages() {
		String[] costGrid = new String[] { "Expense", "Part", "Labor" };
		/*String coverageType = getTestData().getUserData().get(UserData.COVERAGETYPE);
		String make = getTestData().getVehicleRefDetails().get("Make");
		String model = getTestData().getVehicleRefDetails().get("ModelLineCode");
		String roOpenDateString = getTestData().getrOOpenDate();
		String mileage = getTestData().getMileage();
		String modelYear = getTestData().getVehicleRefDetails().get("ModelYearVersionNumber");
		String vin = getTestData().getUserData().get(UserData.VIN);
		String pfp = getTestData().getUserData().get(UserData.PFP);
		String opCode = getTestData().getUserData().get(UserData.OPCODE);
		// coverageType="Powertrain";

		Map<String, String> vehicleWarranty = DVehicleWarrantyUtil.getVehicleWarrantyMap(make, modelYear, mileage,
				opCode, pfp, vin, roOpenDateString.replace("-", ""));
		Map<String, String> costGridMap = GWCostGridUtil.getCostGrid_1(coverageType, make, model, roOpenDateString,
				vehicleWarranty);*/
		
		List<String> expectedCostList = GWCostGridUtil.getCostGridArray(getTestData().getgWCostGrid());
		for (String costListName : costGrid) {
			int j = 0;
			List<ExtWebElement> actualCostList = serviceManagerPage.getGoodWillRequestComponent()
					.getCostList(costListName);
			actualCostList.get(j).assertAttribute("data-value", expectedCostList.get(j++),
					costListName + " Customer Share");
			actualCostList.get(j).assertAttribute("data-value", expectedCostList.get(j++),
					costListName + " Dealer Share");
			actualCostList.get(j).assertAttribute("data-value", expectedCostList.get(j),
					costListName + " Nissan Share");

		}

		getTestData().setNissanShare(Double.parseDouble(
				serviceManagerPage.getGoodWillRequestComponent().txtNissanShare.getAttribute("data-value")));
		getTestData().setTotalAmount(
					Double.parseDouble(serviceManagerPage.getGoodWillRequestComponent().txtTotal.getText().replace(",", "")));
				
	}

	@Then("Servicemanager enters goodwill reason")
	public void servicemanager_enters_goodwill_reason() {
		String str = StringUtils.repeat("a", 100);
		serviceManagerPage.getGoodWillRequestComponent().txtboxGoodWillReason.clearAndSendKeys(str);
	}
	
	@Then("Open GW case from Pending SM Actions")
	public void open_gw_case_from_pending_sm_actions() {
		serviceManagerPage.getLeftSideMenuElement("Pending SM Action").click();
		String gWCaseID = getTestData().getGWCaseID();
		serviceManagerPage.getGoodWillWL().filterAndClick(gWCaseID);
	}
	
	@Then("Click on accept and continue")
	public void click_on_accept_and_continue() {
		DriverManager.getJavaScriptExecutor().executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
		//serviceManagerPage.getGoodWillRequestComponent().tableCostGrid.scrollIntoView();
		serviceManagerPage = new ServiceManagerPage();
		serviceManagerPage.getGoodWillRequestComponent().btnAcceptAndContinue.scrollAndClick();
	}

	@Then("Servicemanager selects purchase type as {string}")
	public void servicemanager_selects_purchase_type_as(String string) {
		serviceManagerPage.getGoodWillRequestComponent().radiobtnPurchaseTypeNew.waitForPresent();
		serviceManagerPage.getGoodWillRequestComponent().radiobtnPurchaseTypeNew.scrollAndClick();
		/*WaitUtil.sleep(4000);
		if(!serviceManagerPage.getGoodWillRequestComponent().radiobtnPurchaseTypeNew.isSelected())
			serviceManagerPage.getGoodWillRequestComponent().radiobtnPurchaseTypeNew.click();*/
	}
	
	@Then("Servicemanager enters {string} in customer pay amount textbox")
	public void servicemanager_enters_in_customer_pay_amount_textbox(String string) {
		serviceManagerPage.getGoodWillRequestComponent().txtboxCustomerPayAmount.clearAndSendKeys(string);
	}

	@Then("Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract")
	public void servicemanger_checks_we_certify_that_this_car_is_not_covered_by_nissan_or_3rd_party_extended_service_contract() {
		serviceManagerPage.getGoodWillRequestComponent().chkboxNoServiceContract.click();
		
	}
	
	@Then("Servicemanger checks We also certify that this vehicle does not have a branded title")
	public void servicemanger_checks_we_also_certify_that_this_vehicle_does_not_have_a_branded_title() {
		serviceManagerPage.getGoodWillRequestComponent().chkboxCertifyNotBrandedTitle.click();
	}

	@Then("Servicemanger clicks on Review and Submit button")
	public void servicemanger_clicks_on_review_and_submit_button() {
		
		serviceManagerPage.getGoodWillRequestComponent().btnReviewAndSubmit.waitForElementLocatedByToBeClickable();
		//serviceManagerPage.getGoodWillRequestComponent().btnReviewAndSubmit.scrollAndClick();
		int count = 0;
		//try to submit 5 times before failing the test case.
		boolean isErrorPresent = false;
		do {
			
			serviceManagerPage.getGoodWillRequestComponent().btnReviewAndSubmit.scrollAndClick();
			count++;
			serviceManagerPage = new ServiceManagerPage();
			isErrorPresent = serviceManagerPage.getGoodWillRequestComponent().txtError.isPresent();
		}
		while(isErrorPresent && count<5);
		/*{
			serviceManagerPage.getGoodWillRequestComponent().btnReviewAndSubmit.scrollAndClick();
			count++;
			serviceManagerPage = new ServiceManagerPage();
		}*/
	
	}
	
	//ServiceManager verifies Vehicle is under Warranty message is displayed
	
	@Then("ServiceManager verifies Vehicle is under Warranty message is displayed")
	public void serviceManager_verifies_vehicle_is_under_Warranty_message_is_displayed() {
		serviceManagerPage.getGoodWillRequestComponent().txtUnderWarranty.scrollIntoView();
		serviceManagerPage.getGoodWillRequestComponent().txtUnderWarranty.assertPresent();
		
		
	}
	
	@Then("Servicemanager clicks on save button")
	public void servicemanager_clicks_on_save_button() {
		
		serviceManagerPage.getGoodWillRequestComponent().btnSave.scrollAndClick();

	}

	@Then("Service manager verifies Your Goodwill Request has been submitted to Nissan message")
	public void service_manager_verifies_your_goodwill_request_has_been_submitted_to_nissan_message() {

		String expectedMsg = "Your Goodwill Request has been submitted to Nissan.";
		ExtWebComponent
				.getExtWebElement("//span[starts-with(text(),'Your Goodwill Request has been submitted to Nissan')]")
				.assertText(StringMatcher.startsWith(expectedMsg), "Summary Screen Header");

	}

	@Then("Servicemanager verifies following text message {string}")
	public void servicemanager_verifies_following_text_message(String string) {
		serviceManagerPage.getGoodWillRequestComponent().txtProvideInfo.assertText(string, "Info text");

	}

	@Then("Servicemanager adds attachments")
	public void servicemanager_adds_attachments() {
		serviceManagerPage.getGoodWillRequestComponent().btnAddAttachment.scrollAndClick();
		serviceManagerPage.getGoodWillRequestComponent().btnFileFromDevice.click();
		String filePath = System.getProperty("user.dir") + ConfigurationManager.getBundle().getString("attachment.path");
		//String filePath = ConfigurationManager.getBundle().getString("attachment.path");
		WaitUtil.sleep(1000);
		serviceManagerPage.getGoodWillRequestComponent().btnSelectFiles.sendKeys(filePath);
		serviceManagerPage.getGoodWillRequestComponent().tableAttachments.waitForPresent();
		serviceManagerPage.getGoodWillRequestComponent().btnAttach.waitForElementLocatedByToBeClickable();
		serviceManagerPage.getGoodWillRequestComponent().btnAttach.click();
		

	}

	@Then("Servicemanager Logs out")
	public void servicemanager_logs_out() {
		serviceManagerPage.logOut();
	}

	@When("GoodWill request ui is displayed")
	public void good_will_request_ui_is_displayed() {
		serviceManagerPage.getGoodWillRequestComponent();
		String gWCaseID = serviceManagerPage.getGoodWillRequestComponent().getHeaderWebElement("Case ID").getText();
		getTestData().setGWCaseId(gWCaseID);
		ExtentLogger.info("GoodWill Case created with case id : " + gWCaseID);
//4177688658
	}

	@When("enters symptom code and diagnosis code")
	public void enters_symptom_code_and_diagnosis_code(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		String symptomCode = getTestData().getUserData().get(UserData.SYMPTOMCODE);
		String diagnosisCode = getTestData().getUserData().get(UserData.DIAGNOSISCODE);
		serviceManagerPage.getGoodWillRequestComponent().txtboxSymptomCode.clearAndSendKeys(symptomCode + Keys.TAB);
		WaitUtil.sleep(2000);
		serviceManagerPage.getGoodWillRequestComponent().txtboxDiagnosisCode.clearAndSendKeys(diagnosisCode + Keys.TAB);
	}
	
	@When("ServiceManager verifies Diagnostic Cases table is displayed")
	public void service_manager_verifies_diagnostic_cases_table_is_displayed() {
		//WaitUtil.sleep(2000);
		
		serviceManagerPage.getGoodWillRequestComponent().txtHeaderDiagnosticCases.assertVisible("Diagnostic Cases");
	}
	@When("ServiceManager clicks on confirm and proceed button")
	public void service_manager_clicks_on_confirm_and_proceed_button() {
		WaitUtil.sleep(2000);
		serviceManagerPage.getGoodWillRequestComponent().btnConfirmAndProceed.scrollAndClick();
	}
	
	@Given("ServiceManager clicks on {string} from left side menu")
	public void service_manager_clicks_on_from_left_side_menu(String string) {
		serviceManagerPage.getLeftSideMenuElement(string).click();
	}
	
	private SystemSettingsComponent systemSettingsComponent;
	@Given("ServiceManager verifies System Setting Component is displayed")
	public void service_manager_verifies_system_setting_component_is_displayed() {
		systemSettingsComponent = new SystemSettingsComponent();
	}
	@Given("ServiceManager clicks on Manage GoodWill Delegation link")
	public void service_manager_clicks_on_manage_good_will_delegation_link() {
		systemSettingsComponent.linkManageGWDelegation.click();
	}
	
	private ManageGWDelegationComponent manageGWDelegationComponent; 
	@Given("ServiceManager verifies Manage GoodWill Delegation Component is displayed")
	public void service_manager_verifies_manage_good_will_delegation_component_is_displayed() {
		manageGWDelegationComponent = new ManageGWDelegationComponent();
	}
	
	@Given("ServiceManager select {string} and click on Save")
	public void service_manager_select(String string) {
		manageGWDelegationComponent.filterAndClick("NNANet User Name", string);
		manageGWDelegationComponent.btnSave.click();
	}
	@Given("Login as {string}")
	public void login_as(String string) {
	   serviceManagerPage.login(string);
	}
	@Given("Verify New Good Will Request option is displayed")
	public void verify_new_good_will_request_option_is_displayed() {
		serviceManagerPage.getLeftSideMenuElement("New Goodwill Request").assertPresent();  
	}
	
	@Given("Login as ServiceManager {string}")
	public void login_as_service_manager(String string) {
		serviceManagerPage.login(string);
		serviceManagerPage.waitForPageToLoad();
	}
	
	private AddNewCouponPopUpComponent addNewCouponPopUpComponent;
	
	@When("ServiceManager clicks on Add Coupon button")
	public void service_manager_clicks_on_add_coupon_button() {
		serviceManagerPage.getGoodWillRequestComponent().btnAddCoupon.click();
	}
	@When("ServiceManager verifies add coupon pop up is displayed")
	public void service_manager_verifies_add_coupon_pop_up_is_displayed() {
		addNewCouponPopUpComponent = new AddNewCouponPopUpComponent();
		addNewCouponPopUpComponent.headerAddNewCoupon.assertVisible();
		
	}
	@When("ServiceManager select first coupon from the list of coupons and click on submit button")
	public void service_manager_select_first_coupon_from_the_list_of_coupons_and_click_on_submit_button() {
		addNewCouponPopUpComponent.listAvailableCoupons.get(0).click();
		addNewCouponPopUpComponent.btnSubmitCoupon.click();
	}
	
	@When("ServiceManager verifies coupon is linked to goodwill case.")
	public void service_manager_verifies_coupon_is_linked_to_goodwill_case() {
		WaitUtil.sleep(2000);
		serviceManagerPage = new ServiceManagerPage();
		//serviceManagerPage.waitForPageToLoad();
		serviceManagerPage.getGoodWillRequestComponent().getHeaderWebElement("Coupon ID").assertText(getTestData().getCouponId());

	}
	
	@Then("ServiceManager verifies Diagnostic cases created within last {int} days are displayed")
	public void service_manager_verifies_diagnostic_cases_created_within_last_days_is_displayed(Integer days) {
		List<ExtWebElement> listDcases = serviceManagerPage.getGoodWillRequestComponent().listDcasesWithApprovedRepair;
		String dcaseDetailsUrl = ConfigurationManager.getBundle().getString("base.uri") + "cases/NSA-STAT-WORK-F1 %s";
		Date todaysDate = DateUtil.parseDate(LocalDate.now().toString(), "yyyy-MM-dd");
		Date roOpenDate = null;
		for (ExtWebElement dcase : listDcases) {
			dcase.scrollIntoView();
			dcaseDetailsUrl = String.format(dcaseDetailsUrl, dcase.getText());
			roOpenDate = DateUtil.parseDate(APIUtil.getResponseObject(dcaseDetailsUrl).jsonPath().getString("content.RepairOrderOpenDate"),"yyyy-MM-dd");
			Validator.assertTrue(DateUtil.getDateDifference(roOpenDate, todaysDate) <= days,dcase.getText() + " RO open date is within 30 days, Ro Open date is " + roOpenDate.toString(),dcase.getText() + " RO open date is greater than 30 days, Ro Open date is " + roOpenDate.toString());
		}
	}
		

@Given("ServiceManager clicks on pending SM actions and opend GW case {string}")
public void service_manager_clicks_on_pending_sm_actions_and_opend_gw_case(String gWCaseID) {
	serviceManagerPage.getLeftSideMenuElement("Pending SM Action").click();
	serviceManagerPage.getGoodWillWL().filterAndClick("Case ID", gWCaseID, gWCaseID);
	WaitUtil.sleep(4000);
	
}

@Given("ServiceManager selects the case with matching repair")
public void service_manager_selects_the_case_with_matching_repair() {
	String repair = "Automation Test Repair "+getTestData().getUserData().get(UserData.VEHICLECOMPONENT);
	String dcasesXpath = String.format("//span[text()='%s']/ancestor::td[@data-attribute-name='Final Recommendation']/preceding-sibling::td//input[@type='radio' and contains(@name,'$PD_DCasesWithApprovedRepair')]",repair);
   List<ExtWebElement> dcaseList = BasePage.getExtWebElements(dcasesXpath, "radioBtnDcase");
   if(dcaseList.isEmpty()) {
	   throw new CustomRuntimeException("No Dcase found with "+repair);
   }
   //select a random dcase everytime the script runs
   int index = new Random().nextInt(dcaseList.size());
   dcaseList.get(index).scrollAndClick();
	
}

@Given("ServiceManager selects the case with {string} repair")
public void service_manager_selects_the_case_with_repair(String repair) {
	//String repair = "Automation Test Data "+getTestData().getUserData().get(UserData.VEHICLECOMPONENT);
	String dcasesXpath = String.format("//span[text()='%s']/ancestor::td[@data-attribute-name='Final Recommendation']/preceding-sibling::td//input[@type='radio' and contains(@name,'$PD_DCasesWithApprovedRepair')]",repair);
   List<ExtWebElement> dcaseList = BasePage.getExtWebElements(dcasesXpath, "radioBtnDcase");
   if(dcaseList.isEmpty()) {
	   throw new CustomRuntimeException("No Dcase found with "+repair);
   }
   //select a random dcase everytime the script runs
   int index = new Random().nextInt(dcaseList.size());
   dcaseList.get(index).scrollAndClick();
	
}

@Given("ServiceManager selects the case with matching repair {string}")
public void service_manager_selects_the_case_with_matching_repair(String vehicleComponent) {
	String repair = "Automation Test Data "+vehicleComponent;
	String dcasesXpath = String.format("//span[text()='%s']/ancestor::td[@data-attribute-name='Final Recommendation']/preceding-sibling::td//input[@type='radio' and contains(@name,'$PD_DCasesWithApprovedRepair')]",repair);
   List<ExtWebElement> dcaseList = BasePage.getExtWebElements(dcasesXpath, "radioBtnDcase");
   if(dcaseList.isEmpty()) {
	   throw new CustomRuntimeException("No Dcase found with "+repair);
   }
   int index = new Random().nextInt(dcaseList.size());
   dcaseList.get(index).scrollAndClick();
   ExtentLogger.passWithScreenShot("");
   
   
	
}
		
	
	@Test
	public void test1() {
		LocalDate todaysDate = LocalDate.now();
		System.out.println(todaysDate.toString());
	}
	
}
