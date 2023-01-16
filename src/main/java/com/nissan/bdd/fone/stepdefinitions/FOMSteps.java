package com.nissan.bdd.fone.stepdefinitions;

import static com.nissan.databeans.TestDataBeanManager.getTestData;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;

import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.StringMatcher;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.enums.Roles;
import com.nissan.enums.UserData;
import com.nissan.pages.FOMPage;
import com.nissan.pages.components.FOMMyWorkListComponent;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.WarrantyLineAPIUtil;

import io.cucumber.java.en.Then;

public class FOMSteps {

	private FOMPage fOMPage;

	public FOMSteps() {
		fOMPage = new FOMPage();
		
		//fOMPage.getGoodWillRequestComponent(true);
	}

	@Then("Login as FOM")
	public void login_as_fom() {
		fOMPage = new FOMPage();
		fOMPage.login(Roles.FOM, getTestData().getUserData().get(UserData.DEALERCODE));
		fOMPage.waitForPageToLoad();
		//fOMPage.getGoodWillRequestComponent(true);

	}

	@Then("Login as FOM {string}")
	public void login_as_fom(String string) {

		fOMPage.login(Roles.FOM, string);
		fOMPage.waitForPageToLoad();

	}

	@Then("Open GW case from Pending FOM Actions")
	public void open_gw_case_from_pending_fom_actions() {
		String gWCaseID = getTestData().getGWCaseID();
		fOMPage.getLeftSideMenuElement("Pending FOM Action").click();
		FOMMyWorkListComponent fOMMyWorkListComponent = new FOMMyWorkListComponent();
		fOMMyWorkListComponent.filterAndClick(gWCaseID);
	}
	
	@Then("Click on Modify button")
	public void click_on_modify_button() {
		fOMPage.getGoodWillRequestComponent().btnModify.scrollAndClick();
	}
	@Then("Click on Visible to dealer checkbox")
	public void click_on_visible_to_dealer_checkbox() {
		fOMPage.getGoodWillRequestComponent().chkboxVisibleToDealer.click();
	}
	@Then("Click on Route to dealer button")
	public void click_on_route_to_dealer_button() {
		WaitUtil.sleep(2000);
	fOMPage.getGoodWillRequestComponent().btnRouteToDealer.click();
	}

	@Then("Enter FOM comments")
	public void enter_fom_comments() {
		WaitUtil.sleep(2000);
		fOMPage.getGoodWillRequestComponent().txtboxVCATComments.sendKeys("fom comments");
	}

	@Then("Click on Approve button")
	public void click_on_approve_button() {
		fOMPage.getGoodWillRequestComponent().btnApprove.scrollAndClick();
	}

	@Then("Click on Reject button")
	public void click_on_reject_button() {
		fOMPage.getGoodWillRequestComponent().btnReject.scrollAndClick();
	}

	@Then("FOM verifies Thank You text is dislayed")
	public void verify_thank_you_text_is_dislayed() {
		WaitUtil.sleep(2000);
		fOMPage = new FOMPage();
		fOMPage.getGoodWillRequestComponent().txtThankYou.assertText(StringMatcher.startsWith("Thank you for your"),
				"Thank You message");
	}
	
	@Then("FOM verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value")
	public void servicemanager_verifies_is_the_customer_likely_to_be_come_back_for_service_field_is_displayed_with_yes_or_no_as_autopopulated_value() {
		fOMPage.getGoodWillRequestComponent().labelIsTheCustomerLikelyToBecomeBackForService.assertPresent();
		fOMPage.getGoodWillRequestComponent().txtIsTheCustomerLikelyToBecomeBackForService.assertPresent();
		String value = fOMPage.getGoodWillRequestComponent().txtIsTheCustomerLikelyToBecomeBackForService.getText();
		ExtentLogger.info("Is the customer likely to be come back for service? field is displayed with value="+value, true);
	}

	@Then("Open goodwill case from All Dealer Cases")
	public void open_goodwill_case_from_all_dealer_cases() {
		fOMPage.closeErrorMessageAndOpenTabs();
		String gWCaseID = getTestData().getGWCaseID();
		fOMPage.getLeftSideMenuElement("All Dealer Cases").click();
		fOMPage.getAllDealerCaseComponent().txtboxCaseID.clearAndSendKeys(gWCaseID);
		fOMPage.getAllDealerCaseComponent().btnSearchByCaseId.click();
		WaitUtil.sleep(2000);
		fOMPage.getAllDealerCaseComponent().filterAndClick("Case ID", gWCaseID, gWCaseID);
	}

	@Then("Open goodwill case {string} from All Dealer Cases")
	public void open_goodwill_case_from_all_dealer_cases(String string) {
		// String gWCaseID = getTestData().getCaseID();
		String gWCaseID = string;
		fOMPage.getLeftSideMenuElement("All Dealer Cases").click();
		fOMPage.getAllDealerCaseComponent().txtboxCaseID.clearAndSendKeys(gWCaseID);
		fOMPage.getAllDealerCaseComponent().btnSearchByCaseId.click();
		WaitUtil.sleep(2000);
		fOMPage.getAllDealerCaseComponent().filterAndClick("Case ID", gWCaseID, gWCaseID);
	}

	@Then("Click on Update VCAN on goodwill case")
	public void click_on_update_vcan_on_goodwill_case() {
		fOMPage = new FOMPage();
		fOMPage.getGoodWillRequestComponent().btnUpdateVCAN.click();
	}

	@Then("On Update VCAN ui, part amount, labor amount and Expense amount with below values")
	public void on_update_vcan_ui_part_amount_labor_amount_and_expense_amount_with_below_values() {
		double partAmount = Double.parseDouble(
				fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().txtboxPartAmount.getAttribute("value"))
				+ 100.0;
		// double partAmount = 200.0;
		getTestData().setUpdatedAmount(String.valueOf(partAmount));

		String updatedAMounts = getTestData().getUpdatedAmount();

		/*fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().txtboxPartAmount.setAttribute("value",
				updatedAMounts);

		fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().txtboxLaborAmount.setAttribute("value",
				updatedAMounts);

		fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().txtboxExpenseAmount.setAttribute("value",
				updatedAMounts);
*/		
		fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().txtboxPartAmount.clear();
		WaitUtil.sleep(5000);
		fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().txtboxPartAmount.sendKeys(updatedAMounts+Keys.TAB);
		
		fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().txtboxLaborAmount.clear();
		WaitUtil.sleep(5000);
		fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().txtboxLaborAmount.sendKeys(updatedAMounts+Keys.TAB);
		
		fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().txtboxExpenseAmount.clear();
		WaitUtil.sleep(5000);
		fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().txtboxExpenseAmount.sendKeys(updatedAMounts+Keys.TAB);
		
		WaitUtil.sleep(5000);

		double totalAmount = Double
				.parseDouble(fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().txtTotalAmount.getText()
						.replace(",", ""));
		double totalExpectedAmount = partAmount*3; 
		Validator.assertTrue(totalAmount == totalExpectedAmount, "Total amount "+totalAmount+" not matching expected total amount "+totalExpectedAmount);

		WarrantyLineAPIUtil.setTotalPaidAmount(partAmount * 3);
	}

	@Then("Enter {string} in comments field")
	public void enter_in_comments_field(String string) {
		WaitUtil.sleep(2000);
		fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().txtboxComments.sendKeys(string);
	}

	@Then("Click on submit button")
	public void click_on_submit_button() {
		fOMPage.getGoodWillRequestComponent().getUpdateVCANComponent().btnSubmit.scrollAndClick();
	}
	
	@Then("Click on Request Grid Exception Checkbox")
	public void click_on_request_grid_exception_checkbox() {
		WaitUtil.sleep(2000);
		fOMPage.getGoodWillRequestComponent().tableCostGrid.scrollIntoView();
		//fOMPage.getGoodWillRequestComponent().chkboxRequestGridException.scrollIntoView();
		fOMPage.getGoodWillRequestComponent().chkboxRequestGridException.scrollAndClick();
	}
	
	@Then("Enter Requested Nissan Contribution")
	public void enter_requested_nissan_contribution() {
		WaitUtil.sleep(2000);
		String nissanContribution = String.valueOf(Integer.parseInt(fOMPage.getGoodWillRequestComponent().txtboxRequestedNissanContribution.getAttribute("value"))+2);
		//fOMPage.getGoodWillRequestComponent().txtboxRequestedNissanContribution.clear();
		fOMPage.getGoodWillRequestComponent().txtboxRequestedNissanContribution.setAttribute("value", nissanContribution);
		
	}
	
	@Then("Enter Exception reason as {string}")
	public void enter_exception_reason_as(String string) {
	    WaitUtil.sleep(2000);
		fOMPage.getGoodWillRequestComponent().txtboxExceptionReason.sendKeys(StringUtils.repeat("a", 128));
		
	}
	
	@Then("FOM user verifies coupon is linked to GW Case")
	public void vcat_user_verifies_coupon_is_linked_to_gw_case() {
		fOMPage.getGoodWillRequestComponent().getHeaderWebElement("Coupon ID").verifyText(getTestData().getCouponId(), "Coupon ID");
		
	}

	
	@Then("Click on Update")
	public void click_on_update() {
		fOMPage.getGoodWillRequestComponent().btnUpdate.click();
	}
	
	@Then("Verify Updated Cost Split Grid Successfully is displayed")
	public void verify_updated_cost_split_grid_successfully_is_displayed() {
		fOMPage.getGoodWillRequestComponent().txtUpdateCost.verifyVisible("Updated Cost message");
	}
	
	@Then("Enter FOM comments {string}")
	public void enter_fom_comments(String string) {
		WaitUtil.sleep(2000);
		fOMPage.getGoodWillRequestComponent().tableCostGrid.scrollIntoView();
		fOMPage.getGoodWillRequestComponent().txtboxVCATComments.sendKeys(string);
	}
	
	@Then("Click on Route to RAM button")
	public void click_on_route_to_ram_button() {
		fOMPage.getGoodWillRequestComponent().btnRouteToRAM.scrollAndClick();
	}
	
	

	@Then("FOm user logs out")
	public void f_om_user_logs_out() {
		fOMPage.logOut();
	}

}
