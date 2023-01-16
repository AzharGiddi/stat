package com.nissan.bdd.fone.stepdefinitions;

import static com.nissan.databeans.TestDataBeanManager.getTestData;

import java.util.List;

import com.nissan.automation.core.utils.BDDDataTableUtil;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.enums.UserData;
import com.nissan.pages.CAManagerPage;
import com.nissan.pages.components.CAManagerGWCasesComponent;
import com.nissan.pages.components.CAManagerGWCouponDetailsComponent;
import com.nissan.pages.components.SearchbyVINCustomerInformationComponent;
import com.nissan.utils.DActiveGWCouponsListUtil;
import com.nissan.utils.NissanCouponAmountUtil;
import com.nissan.utils.ResolveGWCaseUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CAManagerSteps {

	private CAManagerPage cAManagerPage;

	public CAManagerSteps() {

		cAManagerPage = new CAManagerPage();

	}

	private SearchbyVINCustomerInformationComponent searchbyVINCustomerInformationComponent;
	
	private CAManagerGWCasesComponent cAManagerGWCasesComponent;
	
	private CAManagerGWCouponDetailsComponent cAManagerGWCouponDetailsComponent;

	@Given("Login into the application as {string}")
	public void login_into_the_application_as(String string) {
		cAManagerPage.login(string);
		cAManagerPage.waitForPageToLoad();
	}
	
	@Given("All the active gw cases are resolved")
	public void all_the_active_gw_cases_are_resolved() {
	    /*String gwCaseID = DActiveGWCouponsListUtil.getCouponLinkedGoodWillCaseId(getTestData().getUserData().get(UserData.VIN));
	    if(gwCaseID.equals("")) {
	    	return;
	    }*/
		List<String> gwCaseIDList = DActiveGWCouponsListUtil.getCouponLinkedGoodWillCaseId(getTestData().getUserData().get(UserData.VIN));
	   ResolveGWCaseUtil.resolveGWCases(gwCaseIDList); 
	    
	}

	@Given("CAMANAGER verifies Search by VIN Customer Information component is displayed")
	public void camanager_verifies_search_by_vin_customer_information_component_is_displayed() {
		searchbyVINCustomerInformationComponent = new SearchbyVINCustomerInformationComponent();
	}

	@Given("CAMANAGER clicks on {string} from left side menu")
	public void camanager_clicks_on_from_left_side_menu(String string) {
		cAManagerPage.getLeftSideMenuElement("New Goodwill Coupon").click();
	}

	@Given("CAMANAGER enters {string} in VIN textbox")
	public void camanager_enters_in_vin_textbox(String string) {
		searchbyVINCustomerInformationComponent.txtboxVIN.sendKeys(string);

	}
	
	@Then("CAMANAGER enters VIN in VIN textbox")
	public void camanager_enters_in_vin_textbox() {
		searchbyVINCustomerInformationComponent.txtboxVIN.sendKeys(getTestData().getUserData().get(UserData.VIN));
	}

	@Given("CAMANAGER clicks on Search by VIN button")
	public void camanager_clicks_on_search_by_vin_button() {
		searchbyVINCustomerInformationComponent.btnSearchByVIN.click();
	}

	@Given("CAMANAGER clicks on Select button on first record")
	public void camanager_clicks_on_select_button_on_first_record() {
		WaitUtil.sleep(5000);
		searchbyVINCustomerInformationComponent = new SearchbyVINCustomerInformationComponent();
		String dealerCode = getTestData().getUserData().get(UserData.DEALERCODE);
		searchbyVINCustomerInformationComponent.filter("Dealer Code", dealerCode, "Select");
		searchbyVINCustomerInformationComponent.filter("Customer Name", "Automation Company", "Select");
		searchbyVINCustomerInformationComponent.btnSelect.click();
	}

	@Given("CAMANAGER clicks on Continue Without Goodwill Case button")
	public void camanager_clicks_on_continue_without_goodwill_case_button() {
		WaitUtil.sleep(5000);
		cAManagerGWCasesComponent = new CAManagerGWCasesComponent();
		if(cAManagerGWCasesComponent.btnContinueWithGoodwillCase.isPresent())
			cAManagerGWCasesComponent.btnContinueWithGoodwillCase.scrollAndClick();
	}
	
	@Given("CAMANAGER verifies GoodWill Coupon Details is displayed")
	public void camanager_verifies_good_will_coupon_details_is_displayed() { 
		cAManagerGWCouponDetailsComponent = new CAManagerGWCouponDetailsComponent();
		cAManagerGWCouponDetailsComponent.txtGoodwillCouponDetails.scrollIntoView();
		cAManagerGWCouponDetailsComponent.txtGoodwillCouponDetails.verifyVisible("GoodWill Coupon Details header");
		String couponId = cAManagerGWCouponDetailsComponent.txtCouponId.getText();
		getTestData().setCouponId(couponId);
		
	}
		

	@Given("CAMANAGER selects Approved radio button under status")
	public void camanager_selects_approved_radio_button_under_status() {
		cAManagerGWCouponDetailsComponent.radiobtnApproved.click();
		getTestData().isApprovedCoupon=true;
	}
	
	
	@Given("CAMANAGER enters Nissan Contribution")
	public void camanager_enters_nissan_contribution(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		boolean nissanCpnAmtGTNissanAmt = Boolean.parseBoolean(getTestData().getUserData().get(UserData.NISSANCPNAMTGTNISSANAMT));
		double nissanCouponAmt = NissanCouponAmountUtil.getNissanCouponAmount(nissanCpnAmtGTNissanAmt,getTestData().getNissanShare());
		getTestData().setNissanCouponAmt(nissanCouponAmt);
		cAManagerGWCouponDetailsComponent.txtboxNissanContribution.clearAndSendKeys((String.valueOf(((Double) nissanCouponAmt).intValue())));
	}
	
	@Given("CAMANAGER enters Nissan Contribution as {string}")
	public void camanager_enters_nissan_contribution(String nissanCouponAmt) {
		cAManagerGWCouponDetailsComponent = new CAManagerGWCouponDetailsComponent();
		getTestData().setNissanCouponAmt(Double.parseDouble(nissanCouponAmt));
		cAManagerGWCouponDetailsComponent.txtboxNissanContribution.clearAndSendKeys(nissanCouponAmt);
	}
	
	@Given("CAMANAGER enter {string} in Concern text box")
	public void camanager_enter_in_concern_text_box(String string) {
		//if(!DActiveGWCouponsListUtil.isNewCouponAvailable)
			if(cAManagerGWCouponDetailsComponent.txtboxConcern.isPresent()) {
			cAManagerGWCouponDetailsComponent.txtboxConcern.clearAndSendKeys(string);
			}
	}
	@Given("CAMANAGER enter {string} in Internal Comments text box")
	public void camanager_enter_in_internal_comments_text_box(String string) {
	//	if(!DActiveGWCouponsListUtil.isNewCouponAvailable)
		if(cAManagerGWCouponDetailsComponent.txtboxInternalComments.isPresent()) {
			cAManagerGWCouponDetailsComponent.txtboxInternalComments.clearAndSendKeys(string);
		}
	}

	@Given("CAMANAGER click on Submit button")
	public void camanager_click_on_submit_button() {

		cAManagerGWCouponDetailsComponent.btnSubmit.click();
	}

	@Given("CAMANAGER verifies Thank You text is displayed")
	public void camanager_verifies_thank_you_text_is_displayed() {
		searchbyVINCustomerInformationComponent.txtThankYou.verifyVisible();
	}
	
	@Then("CAMANAGER verifies GoodWill Cases section is displayed")
	public void camanager_verifies_good_will_cases_section_is_displayed() {
		cAManagerGWCasesComponent = new CAManagerGWCasesComponent();
		cAManagerGWCasesComponent.headerGoodWillCases.scrollIntoView();
		cAManagerGWCasesComponent.headerGoodWillCases.assertVisible("GoodWill Cases section");
	}
	@Then("CAMANAGER selects the Goodwill Case created earlier")
	public void camanager_selects_the_goodwill_case_created_earlier() {
		
		
		String gwID = getTestData().getGWCaseID();
		//gwID = "GW-112021";
		cAManagerGWCasesComponent.filterAndClick("Goodwill Case ID", gwID, "Select");
	}

	@Given("CAMANAGER logs out")
	public void camanager_logs_out() {
		cAManagerPage.logOut();
	}

}
