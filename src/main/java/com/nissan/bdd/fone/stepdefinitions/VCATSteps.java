package com.nissan.bdd.fone.stepdefinitions;


import static com.nissan.databeans.TestDataBeanManager.getTestData;

import java.util.Objects;

import org.testng.Assert;

import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.APIUtil;
import com.nissan.automation.core.utils.BDDDataTableUtil;
import com.nissan.automation.core.utils.StringMatcher;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.databeans.NAARulesDataBean;
import com.nissan.enums.Roles;
import com.nissan.enums.UserData;
import com.nissan.enums.VCATCheckbox;
import com.nissan.pages.VCATPage;
import com.nissan.pages.components.VCATAutomaticTransmissionCVTSymtomFormComponent;
import com.nissan.pages.components.VCATAutomaticTransmissionCVTSymtomFormComponent.RadioBtnName;
import com.nissan.pages.components.VCATSupportComponent;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.WarrantyLineAPIUtil;

import io.cucumber.java.en.Then;

public class VCATSteps {

	private VCATPage vCATPage;

	public VCATSteps() {
		vCATPage = new VCATPage();
	}

	@Then("Login as PCCF1Analyst")
	public void login_as_pccf1analyst() {
		vCATPage = new VCATPage();
		vCATPage.login(Roles.VCATSUPPORT);
		vCATPage.waitForPageToLoad();
	//	vCATPage.getGoodWillRequestComponent(true);
	}

	@Then("PCCF1Analyst Opens dcase")
	public void open_dcase() {
		NAARulesDataBean naaRulesMap = getTestData().getnAARulesDataBean();
		String dCaseID = getTestData().getDCaseID();
		vCATPage.waitForPageToLoad();
		vCATPage.getLeftSideMenuElement("VCAT Support").click();
		VCATSupportComponent vCATSupportComponent = new VCATSupportComponent();
		vCATSupportComponent.filterAndClick("Case ID", dCaseID, dCaseID);
	}
	
	@Then("Open dcase and verify evaluated NAA rules again populated fields on dcase")
	public void open_dcase_and_verify_evaluated_naa_rules_again_populated_fields_on_dcase() {
		NAARulesDataBean naaRulesMap = getTestData().getnAARulesDataBean();
		String dCaseID = getTestData().getDCaseID();
		vCATPage.waitForPageToLoad();
		vCATPage.getLeftSideMenuElement("VCAT Support").click();
		VCATSupportComponent vCATSupportComponent = new VCATSupportComponent();
		vCATSupportComponent.filterAndClick("Case ID", dCaseID, dCaseID);
		// verify checkboxes
		VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent = vCATPage.getvCATAutomaticTransmissionCVTSymtomFormComponent();
		boolean paymntChangedFromWtoNW = Boolean.parseBoolean(vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PAYMNTASSMPTNCHNGDNWTOW).getAttribute("alt"));
		boolean techDisRegrd = Boolean.parseBoolean(vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.TECHDISAGRDSYSRECMNDREPAIR).getAttribute("alt"));
		//vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PAYMNTASSMPTNCHNGDNWTOW).verifyAttribute("alt",String.valueOf(naaRulesMap.getPaymntAssmptnChngdNWtoW()),"Payment assumption has been updated from Non Warranty to Warranty");
		//vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.TECHDISAGRDSYSRECMNDREPAIR).verifyAttribute("alt",String.valueOf(naaRulesMap.getTechDisagrdSysRecmndRepair()),"Technician Disagreed with System Recommended Repair");
		Validator.assertTrue(paymntChangedFromWtoNW==naaRulesMap.getPaymntAssmptnChngdNWtoW(), "Expected Payment assumption has been updated from Non Warranty to Warranty attribute should be "+naaRulesMap.getPaymntAssmptnChngdNWtoW()+" : Actual Payment assumption has been updated from Non Warranty to Warranty attribute is"+paymntChangedFromWtoNW,"Expected Payment assumption has been updated from Non Warranty to Warranty attribute should be "+naaRulesMap.getPaymntAssmptnChngdNWtoW()+" : Actual Payment assumption has been updated from Non Warranty to Warranty attribute is"+paymntChangedFromWtoNW);
		Validator.assertTrue(techDisRegrd==naaRulesMap.getTechDisagrdSysRecmndRepair(), "Expected Technician Disagreed with System Recommended Repair attribute should be "+naaRulesMap.getPaymntAssmptnChngdNWtoW()+" : Actual Technician Disagreed with System Recommended Repair attribute is"+paymntChangedFromWtoNW,"Expected Technician Disagreed with System Recommended Repair attribute should be "+naaRulesMap.getPaymntAssmptnChngdNWtoW()+" : Actual Technician Disagreed with System Recommended Repair attribute is"+paymntChangedFromWtoNW);
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ARCDEALER).verifyAttribute("alt", naaRulesMap.getArcDealer(), "ARC Dealer");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ENGINEERINGREVIEW).verifyAttribute("alt", naaRulesMap.getEngineeringReview(), "Engineering Review");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MANUFACTURINGDATE).verifyAttribute("alt", naaRulesMap.getManufacturingDate(), "Manufacturing date");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MILEAGE).verifyAttribute("alt", naaRulesMap.getMileage(), "Mileage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MONTHSINSERVICE).verifyAttribute("alt", naaRulesMap.getMonthsInService(), "Months in service");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.VIN).verifyAttribute("alt",naaRulesMap.getVin(), "VIN");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.DEALERSPECIFIC).verifyAttribute("alt", naaRulesMap.getDealerSpecific(), "Dealer Specific");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ADDITIONALRULES).verifyAttribute("alt", naaRulesMap.getAdditionalRules(), "Additional Rules");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.INSERVICEDATEUNAVAILABLE).verifyAttribute("alt", naaRulesMap.getInServiceDateUnavailable(), "In-service date unavailable");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.SERVICECONTRACTCOVERAGE).verifyAttribute("alt", naaRulesMap.getServiceContractCoverage(), "Service Contract Coverage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PARTSWARRANTYCOVERAGE).verifyAttribute("alt", naaRulesMap.getPartsWarrantyCoverage(), "Parts Warranty Coverage");

	}

	@Then("VCAT User enters below details and submit dcase")
	public void vcat_user_enters_below_details_and_submit_dcase(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent = vCATPage
				.getvCATAutomaticTransmissionCVTSymtomFormComponent();
		vCATAutomaticTransmissionCVTSymtomFormComponent.getRadioButton(RadioBtnName.SYSTEMRECOMMENDATIONRIGHT, "Yes")
				.click();
		vCATAutomaticTransmissionCVTSymtomFormComponent
				.getRadioButton(RadioBtnName.AGREETECHNICIANRECOMMENDATION, "Yes").click();
		WaitUtil.sleep(2000);
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtboxVCATSupportNotes.clearAndSendKeys("VCAT support");
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtPaymentType
				.verifyText(getTestData().getUserData().get(UserData.PAYMENTASSUMPTION), "Payment Assumption");
		vCATAutomaticTransmissionCVTSymtomFormComponent.btnSubmit.scrollAndClick();

	}

	@Then("VCAT User selects System make the right recommendation as {string}")
	public void vcat_user_selects_system_make_the_right_recommendation_as(String string) {
		vCATPage.getvCATAutomaticTransmissionCVTSymtomFormComponent()
				.getRadioButton(RadioBtnName.SYSTEMRECOMMENDATIONRIGHT, string).click();
	}

	@Then("VCAT User selects Agree with the technician recommendation as {string}")
	public void vcat_user_selects_agree_with_the_technician_recommendation_as(String string) {
		vCATPage.getvCATAutomaticTransmissionCVTSymtomFormComponent()
				.getRadioButton(RadioBtnName.AGREETECHNICIANRECOMMENDATION, string).click();
	}

	@Then("VCAT User selects Want to override the system recommendation as {string}")
	public void vcat_user_selects_want_to_override_the_system_recommendation_as(String string) {
		vCATPage.getvCATAutomaticTransmissionCVTSymtomFormComponent()
				.getRadioButton(RadioBtnName.OVERRIDESYSTEMRECOMMENDATION, string).click();
	}

	@Then("VCAT User selects Want to override the technician recommendation as {string}")
	public void vcat_user_selects_want_to_override_the_technician_recommendation_as(String string) {

	}

	@Then("VCATuser clicks on submit")
	public void vca_tuser_clicks_on_submit() {
		vCATPage.getvCATAutomaticTransmissionCVTSymtomFormComponent().btnSubmit.scrollAndClick();
	}

	@Then("VCAT user enters VCAT suport notes {string}")
	public void vcat_user_enters_vcat_suport_notes(String string) {
		WaitUtil.sleep(2000);
		vCATPage.getvCATAutomaticTransmissionCVTSymtomFormComponent().txtboxVCATSupportNotes.clearAndSendKeys("VCAT support");

	}

	@Then("VCAT user verifies payment type")
	public void vcat_user_verifies_payment_type() {
		vCATPage.getvCATAutomaticTransmissionCVTSymtomFormComponent().txtPaymentType
				.verifyText(getTestData().getUserData().get(UserData.PAYMENTASSUMPTION), "Payment Assumption");
	}

	@Then("Open GoodWill case from VCAT support WL")
	public void open_good_will_case_from_vcat_support_wl() {

		String gWCaseID = getTestData().getGWCaseID();
		vCATPage.getLeftSideMenuElement("VCAT Support").click();
		VCATSupportComponent vCATSupportComponent = new VCATSupportComponent();
		vCATSupportComponent.filterAndClick("Case ID", gWCaseID, gWCaseID);

	}

	@Then("Enter vcat comments {string}")
	public void enter_vcat_comments(String string) {

		vCATPage.getGoodWillRequestComponent().txtboxVCATComments.clearAndSendKeys(string);

	}

	@Then("Click on Approve")
	public void click_on_approve() {
		//DriverManager.getJavaScriptExecutor().executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
		vCATPage.getGoodWillRequestComponent().btnApprove.scrollAndClick();
	}

	
	@Then("Verify Thank You text is dislayed.")
	public void verify_thank_you_text_is_dislayed() {
		vCATPage = new VCATPage();
		vCATPage.getGoodWillRequestComponent().txtThankYou
				.assertText(StringMatcher.startsWith("Thank"), "Thank You message");
	}

	@Then("Submit WarrantyLine with below values")
	public void submit_warranty_line_with_below_values(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		WarrantyLineAPIUtil.submitWarrantyLine(getTestData());
	}

	@Then("VCAT user verifies coupon is linked to GW Case")
	public void vcat_user_verifies_coupon_is_linked_to_gw_case() {
		vCATPage.getGoodWillRequestComponent().getHeaderWebElement("Coupon ID").verifyText(getTestData().getCouponId(), "Coupon ID");
		
	}
	

	@Then("VCAT user logs out")
	public void vcat_user_logs_out() {
		vCATPage.logOut();
	}
}
