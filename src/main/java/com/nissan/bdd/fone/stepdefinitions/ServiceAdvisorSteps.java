package com.nissan.bdd.fone.stepdefinitions;

import static com.nissan.databeans.TestDataBeanManager.getTestData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.testng.Assert;

import com.nissan.automation.core.utils.BDDDataTableUtil;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.enums.DropDownName;
import com.nissan.enums.Roles;
import com.nissan.enums.UserData;
import com.nissan.pages.ServiceAdvisorPage;
import com.nissan.pages.components.CustomerConcernsComponent;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.SymptomsUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ServiceAdvisorSteps {

	private ServiceAdvisorPage serviceAdvisorPage;
	
	String rONumber;
	
	public ServiceAdvisorSteps() {
		serviceAdvisorPage = new ServiceAdvisorPage();
	}
	
	@Given("Login as service advisor")
	public void login_as_service_advisor() {
	   
		serviceAdvisorPage.login(Roles.SERVICEADVISOR, getTestData().getUserData().get(UserData.DEALERCODE));
		serviceAdvisorPage.waitForPageToLoad();
		
	}
	
	@Given("Login as service advisor of {string} dealer")
	public void login_as_service_advisor(String dealer) {
		   
		serviceAdvisorPage.login(Roles.SERVICEADVISOR, dealer);
		serviceAdvisorPage.waitForPageToLoad();
		
	}
	
	@Given("Login as service advisor {string}")
	public void login_as_service_advisor_with_id(String userName) {
	   
		serviceAdvisorPage.login(userName);
		serviceAdvisorPage.waitForPageToLoad();
		
	}
	
	
	@When("service advisor clicks on New CVT Symptom Form")
	public void service_advisor_clicks_on_new_cvt_symptom_form() {
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
	}
	
	@When("service advisor creates Dcase for created RO")
	public void service_advisor_creates_dcase_for_created_ro() {
		rONumber = getTestData().getRoNumber();
		String rONumber = getTestData().getRoNumber();
		serviceAdvisorPage.getSelectRepairOrderComponent().filterAndClick(rONumber);
		serviceAdvisorPage.getSelectedRepairOrderComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent()
				.getHeaderWebElement("Case ID").getText();
		getTestData().setDCaseId(dCaseID);
		ExtentLogger.info("Diagnostics case created with case id: "+getTestData().getDCaseID(),true);
		
	}

	@When("service advisor clicks on skip rol")
	public void service_advisor_clicks_on_skip_rol() {
	   
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
	}

	@When("service advisor selects symptoms")
	public void service_advisor_selects_symptoms() {
	   serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectSymptoms(getTestData().getCustomerSymptom());
	   serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
	   serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
	}
	
	@When("service advisor selects below symptoms")
	public void service_advisor_selects_below_symptoms(DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.CUSTOMERSYMPTOMCHECKBOXES);
		String dropDownString = map.get(UserData.CUSTOMERSYMPTOMDROPDOWNS);
		String comments = map.get(UserData.CUSTOMEROTHERSYMPTOMS);
		String paymentAssumption = map.get(UserData.PAYMENTASSUMPTION);
		getTestData().setPaymentAssumption(paymentAssumption);
		getTestData().setCustomerSymptom(checkBoxString, dropDownString, comments, paymentAssumption);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectSymptoms(getTestData().getCustomerSymptom());
		   serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
			
	    
	}
	
	@When("service advisor selects below symptoms under General Symptoms")
	public void service_advisor_selects_below_symptoms_under_general_symptoms(DataTable dataTable) {
	    
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.GENERALSYMPTOMS);
		
		
		
	}
	
	@Given("Click on {string} from left side menu")
	public void click_on_from_left_side_menu(String string) {
	    serviceAdvisorPage.getLeftSideMenuElement(string).click();
	}
	
	@Given("Verify connector lookup ui is displayed")
	public void verify_connector_lookup_ui_is_displayed() {
		WaitUtil.sleep(2000);
		serviceAdvisorPage.getConnectorLookupComponent().textFindConnectorsBy.verifyVisible("Find Connector By");
	}
	@Given("On connector lookup ui select {string} radio button")
	public void on_connector_lookup_ui_select(String string) {
	 serviceAdvisorPage.getConnectorLookupComponent().getConnectorByRadioButton(string).click();
	}
	
	@Given("Enter {string} in Enter VIN textbox")
	public void enter_vin(String string) {
		serviceAdvisorPage.getConnectorLookupComponent().txtBoxEnterVIN.clearAndSendKeys(string);
	}
	@When("Click on go button")
	public void click_on_go_button() {
		WaitUtil.sleep(2000);
		serviceAdvisorPage.getConnectorLookupComponent().btnSearch.click();
	}
	@Then("Verify list of connectors matching the vin are displayed")
	public void verify_list_of_connectors_matching_the_vin_are_displayed() {
		serviceAdvisorPage.getConnectorLookupComponent().tableResultsGrid.verifyVisible("Connector Table");
	}
	
	@Given("Enter {string} in Enter RO\\/Wo Number")
	public void enter_in_enter_ro_wo_number(String string) {
		serviceAdvisorPage.getConnectorLookupComponent().txtBoxEnterROWONo.clearAndSendKeys(string);
	}
	@Then("Verify list of connectors are displayed")
	public void verify_list_of_connectors_are_displayed() {
		WaitUtil.sleep(5000);
		serviceAdvisorPage.getConnectorLookupComponent().tableResultsGrid.verifyVisible("Connector Table");
	}
	@Then("Click on first record from the list of connectors")
	public void click_on_first_record_from_the_list_of_connectors() {
		WaitUtil.sleep(2000);
		serviceAdvisorPage.getConnectorLookupComponent().linkFirstConnectorRecord.click();
	}
	@Then("Verify Connector Info screen is displayed")
	public void verify_connector_info_screen_is_displayed() {
		serviceAdvisorPage.getConnectorLookupComponent().txtConnectorInfo.verifyVisible("Connector Information");
	}
	@Then("Click on back to search button")
	public void click_on_back_to_search_button() {
		serviceAdvisorPage.getConnectorLookupComponent().btnBackToSearch.click();
	}
	
	@Then("Click on back to search results button")
	public void click_on_back_to_search_results_button() {
		serviceAdvisorPage.getConnectorLookupComponent().btnBackToSearchResults.click();
	}
		
	@Given("Enter {string} in input textbox")
	public void enter_in_input_textbox(String string) {
		serviceAdvisorPage.getConnectorLookupComponent().txtboxInputbox.clearAndSendKeys(string);
		/*serviceAdvisorPage.getConnectorLookupComponent().txtboxInputbox.clearAndSendKeys("AT-");
		serviceAdvisorPage.getConnectorLookupComponent().txtboxInputbox.sendKeys(Keys.DOWN);
		serviceAdvisorPage.getConnectorLookupComponent().txtboxInputbox.sendKeys(Keys.DOWN);
		WaitUtil.sleep(1000);
		serviceAdvisorPage.getConnectorLookupComponent().txtboxInputbox.sendKeys(Keys.ENTER);*/
	}
	
	@Given("Enter {string} in Enter Connector Keyword Type Part textbox")
	public void enter_in_enter_connector_keyword_type_part_textbox(String string) {
		serviceAdvisorPage.getConnectorLookupComponent().txtBoxEnterConnectorNo.clearAndSendKeys(string);
	}
	
	@When("Thank you screen is displayed to service advisor")
	public void thank_you_screen_is_displayed_to_service_advisor() {
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
		.assertText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
	}
	
	@When("service advisor logs out")
	public void service_advisor_logs_out() {
	    serviceAdvisorPage.logOut();
	}
	
	@Given("Service Advisor clicks on {string} from left side menu")
	public void service_advisor_clicks_on_from_left_side_menu(String string) {
		 serviceAdvisorPage.getLeftSideMenuElement(string).click();
	}
	@Given("Service Advisor verifies connector lookup ui is displayed")
	public void service_advisor_verifies_connector_lookup_ui_is_displayed() {
		WaitUtil.sleep(2000);
		serviceAdvisorPage.getConnectorLookupComponent().textFindConnectorsBy.verifyVisible("Find Connector By");
	}
	@When("On connector lookup ui Service Advisor selects {string} radio button")
	public void on_connector_lookup_ui_service_advisor_selects_radio_button(String string) {
		serviceAdvisorPage.getConnectorLookupComponent().getConnectorByRadioButton(string).click();
	}
	@When("Service Advisor selects {string} from make drop down")
	public void service_advisor_selects_from_make_drop_down(String make) {
		serviceAdvisorPage.getConnectorLookupComponent().dropdownMake.select(make);
	}
	@When("Service Advisor selects {string} from model drop down")
	public void service_advisor_selects_from_model_drop_down(String model) {
		serviceAdvisorPage.getConnectorLookupComponent().dropdownModel.selectByValue(model);
	}
	@When("Service Advisor selects {string} from year drop down")
	public void service_advisor_selects_from_year_drop_down(String year) {
		serviceAdvisorPage.getConnectorLookupComponent().dropdownYear.selectByValue(year);
		
	}
	@Then("Service Advisor verifies list of connectors are displayed")
	public void service_advisor_verifies_list_of_connectors_are_displayed() {
		WaitUtil.sleep(4000);
		//serviceAdvisorPage.getConnectorLookupComponent().tableResultsGrid.waitForVisible(2000);
		serviceAdvisorPage.getConnectorLookupComponent().tableResultsGrid.verifyVisible("Connector Table");
	}
	@Then("Service Advisor click on first record from the list of connectors")
	public void service_advisor_click_on_first_record_from_the_list_of_connectors() {
		WaitUtil.sleep(2000);
		serviceAdvisorPage.getConnectorLookupComponent().linkFirstConnectorRecord.click();
	}
	@Then("Service Advisor verifies Connector Info screen is displayed")
	public void service_advisor_verifies_connector_info_screen_is_displayed() {
		WaitUtil.sleep(2000);
		serviceAdvisorPage.getConnectorLookupComponent().txtConnectorInfo.verifyVisible("Connector Information");
	}
	@Then("Service Advisor clicks on back to search results button")
	public void service_advisor_clicks_on_back_to_search_results_button() {
		
		serviceAdvisorPage.getConnectorLookupComponent().btnBackToSearch.click();
	}
	@Then("Service Advisor clicks on back to search results link")
	public void service_advisor_clicks_on_back_to_search_results_link() {
		serviceAdvisorPage.getConnectorLookupComponent().linkBackToSearch.click();
	}
	@Then("Select first VIN from the dropdown")
	public void select_first_vin_from_the_dropdown() {
		WaitUtil.sleep(2000);
		serviceAdvisorPage.getConnectorLookupComponent().dropDownSelectVIN.selectByIndex(1);
	}	
	@When("service advisor clicks on New Audio Symptom Form")
	public void service_advisor_clicks_on_new_audio_symptom_form() {
		serviceAdvisorPage.getLeftSideMenuElement("New Audio Symptom Form").click();
	}
	@When("service advisor selects Audio plays with ignition off check box")
	public void service_advisor_selects_audio_plays_with_ignition_off_check_box() {
		serviceAdvisorPage.getAudioSymptomsSurveyFormComponent().chkboxAudioPlaysWIthIgnitionOff.scrollAndClick();
	}
	@When("service advisor submits the dcase")
	public void service_advisor_submits_the_dcase() {
		serviceAdvisorPage.getAudioSymptomsSurveyFormComponent().btnSubmit.scrollAndClick();
	}
	
	@When("Service advisor clicks on Symptom Survey Beta link")
	public void service_advisor_clicks_on_symptom_survey_beta_link() {
		serviceAdvisorPage.getLeftSideMenuElement("Universal Symptom Survey").click();
	}
	
	private CustomerConcernsComponent customerConcernsComponent;
	
	@Then("Service advisor verifies Customer Concerns component is displayed")
	public void service_advisor_verifies_customer_concerns_component_is_displayed() {
		customerConcernsComponent = new CustomerConcernsComponent();
	}
	@Then("Service advisor enters VIN on customer concerns component and click on go")
	public void service_advisor_enters_vin_on_customer_concerns_component_and_click_on_go() {
		customerConcernsComponent.txtBoxVIN.sendKeys(getTestData().getUserData().get(UserData.VIN));
		customerConcernsComponent.btnGo.click();
	}
	@Then("Service advisor enters {string} as VIN on customer concerns component and click on go")
	public void service_advisor_enters_on_customer_concerns_component_and_click_on_go(String vin) {
		customerConcernsComponent.txtBoxVIN.sendKeys(vin);
		customerConcernsComponent.btnGo.click();
	}
	@Then("Service advisor clicks on add concern button")
	public void service_advisor_clicks_on_add_concern_button() {
		customerConcernsComponent.btnAddAConcern.click();
	}
	@Then("Service advisor clicks on I see link")
	public void service_advisor_clicks_on_i_see_link() {
		customerConcernsComponent.linkISee.scrollAndClick();
	}
	@Then("Service advisor selects {string} check box and clicks ok")
	public void service_advisor_selects_check_box_and_clicks_ok(String string) {
		customerConcernsComponent.chkboxPoorAppearance.click();
		customerConcernsComponent.btnOk.scrollAndClick();
	}
	@Then("Service advisor clicks on add to summary")
	public void service_advisor_clicks_on_add_to_summary() {
		customerConcernsComponent.btnAddToSUmaryScreen.click();
	}
	@Then("Service advisor verifies {string} is displayed")
	public void service_advisor_verifies_is_displayed(String string) {
	    
	}
	@Then("Service advisor clicks on next button")
	public void service_advisor_clicks_on_next_button() {
		customerConcernsComponent = new CustomerConcernsComponent();
		customerConcernsComponent.btnNext.scrollAndClick();
	}
	@Then("Service advisor verifies Coverages Component is displayed")
	public void service_advisor_verifies_coverages_component_is_displayed() {
	   
	}
	@Then("Service advisor clicks on next")
	public void service_advisor_clicks_on_next() {
		WaitUtil.sleep(2000);
		customerConcernsComponent.btnNext.scrollAndClick();
	}
	@Then("Service advisor selects paytype as Customer Pay")
	public void service_advisor_selects_paytype_as_customer_pay() {
		customerConcernsComponent = new CustomerConcernsComponent();
		WaitUtil.sleep(2000);
		customerConcernsComponent.dropDownPaytype.select("Customer Pay");
	}
	@Then("Service advisor clicks on done button")
	public void service_advisor_clicks_on_done_button() {
		customerConcernsComponent.btnDone.scrollAndClick();
	}
	
	@Then("Service advisor verifies Congratulations message")
	public void service_advisor_verifies_congratulations_message() {
		customerConcernsComponent.txtCongratulations.assertText("Congratulations!");
	}
	
	@When("service advisor creates Dcase for {string}")
	public void service_advisor_creates_dcase_for(String roNumber) {
		rONumber = roNumber;
		getTestData().setRoNumber(roNumber);
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent()
				.getHeaderWebElement("Case ID").getText();
		getTestData().setDCaseId(dCaseID);
		String vIN=serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent()
				.getHeaderWebElement("VIN").getText();
		Map<String,String> map = new HashMap<>();
		map.put("VIN", vIN);
		getTestData().setUserData(map);
		ExtentLogger.info("Diagnostics case created with case id: "+getTestData().getDCaseID(),true);
		
	}
	
	@Given("Service Advisor selects below symptoms under General Symptoms")
	public void serviceAdvisor_selects_below_symptoms_under_general_symptoms(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.GENERALSYMPTOMS);
		serviceAdvisorPage.getAudioSymptomsSurveyFormComponent().headerGeneralSymptoms.scrollIntoView();
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getCustomerSymptom().addToSymptomSet(symptoms);
	}
	
	@Given("Service Advisor selects below symptoms under Systems and components")
	public void serviceAdvisor_selects_below_symptoms_under_systems_and_components(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.SYSTEMSCOMPONENTS);
		serviceAdvisorPage.getAudioSymptomsSurveyFormComponent().headerSystemsComponents.scrollIntoView();
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getCustomerSymptom().addToSymptomSet(symptoms);
	}
	@Given("Service Advisor selects below symptoms under Occurs when")
	public void serviceAdvisor_selects_below_symptoms_under_occurs_when(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.OCCURSWHEN);
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getCustomerSymptom().addToSymptomSet(symptoms);
	}
	@Given("Service Advisor selects below symptoms under Occurs where")
	public void serviceAdvisor_selects_below_symptoms_under_occurs_where(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.OCCURSWHERE);
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getCustomerSymptom().addToSymptomSet(symptoms);
	}
	
	@Given("Service Advisor selects below symptoms under Occurs condition")
	public void serviceAdvisor_selects_below_symptoms_under_occurs_condition(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.OCCURSCONDITION);
		serviceAdvisorPage.getAudioSymptomsSurveyFormComponent().headerOccursCondition.scrollIntoView();
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getCustomerSymptom().addToSymptomSet(symptoms);
	}
	@Given("Service Advisor selects below symptoms under when did the concern begin dropdown")
	public void serviceAdvisor_selects_below_symptoms_under_when_did_the_concern_begin_dropdown(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String symptom = SymptomsUtil.selectDropDownValue("CustomerConcern", map.get(UserData.WHENDIDTHECONCERNBEGIN));
		getTestData().getCustomerSymptom().addToSymptomSet(symptom);
		
	}
	@Given("Service Advisor selects below symptoms under frequency dropdown")
	public void serviceAdvisor_selects_below_symptoms_under_frequency_dropdown(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String symptom = SymptomsUtil.selectDropDownValue("Frequency", map.get(UserData.FREQUENCY));
		getTestData().getCustomerSymptom().addToSymptomSet(symptom);
	}
	
	@When("Service Advisor enters following comments in comments textbox {string}")
	public void service_advisor_enter_following_comments_in_comments_textbox(String comments) {
		serviceAdvisorPage.getAudioSymptomsSurveyFormComponent().txtboxOtherSymptomsRecentRepairs.sendKeys(comments);
		getTestData().getCustomerSymptom().setComments(comments);
	}
	@When("Service Advisor selects {string} as payment type")
	public void service_advisor_selects_as_payment_type(String paymentType) {
		getTestData().getUserData().put(UserData.PAYMENTASSUMPTION,paymentType);
		SymptomsUtil.selectDropDownValue("PaymentType", paymentType);
			
	}
	
	@When("service advisor selects below symptom from Vehicle does not move drop down")
	public void service_advisor_selects_below_symptom_from_vehicle_does_not_move_drop_down(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String symptom = SymptomsUtil.selectDropDownValue(DropDownName.VEHICLEDOESNOTMOVE.getDropDownName(), map.get(UserData.VEHICLEDOESNOTMOVE));
		getTestData().getCustomerSymptom().addToSymptomSet(symptom);
	}
	@When("service advisor selects below symptom from vibration drop down")
	public void service_advisor_selects_below_symptom_from_vibration_drop_down(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String symptom = SymptomsUtil.selectDropDownValue(DropDownName.VIBRATION.getDropDownName(), map.get(UserData.VIBRATION));
		getTestData().getCustomerSymptom().addToSymptomSet(symptom);
	    
	}
	@When("service advisor selects below symptom from Engine stalls when drop down")
	public void service_advisor_selects_below_symptom_from_engine_stalls_when_drop_down(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String symptom = SymptomsUtil.selectDropDownValue(DropDownName.ENGINESTALLSWHEN.getDropDownName(), map.get(UserData.ENGINESTALLSWHEN));
		getTestData().getCustomerSymptom().addToSymptomSet(symptom);
	}
	@When("service advisor selects below symptoms under Poor Shift Quality section")
	public void service_advisor_selects_below_symptoms_under_poor_shift_quality_section(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.POORSHIFTQUALITY);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().headerPoorShiftQuality.scrollIntoView();
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getCustomerSymptom().addToSymptomSet(symptoms);
	}
	@When("service advisor selects below symptoms under Noise section")
	public void service_advisor_selects_below_symptoms_under_noise_section(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.NOISE);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().headerNoise.scrollIntoView();
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getCustomerSymptom().addToSymptomSet(symptoms);
	}
	@When("service advisor selects below symptoms under Occurs when section")
	public void service_advisor_selects_below_symptoms_under_occurs_when_section(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.OCCURSWHEN);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().headerOccursWhen.scrollIntoView();
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getCustomerSymptom().addToSymptomSet(symptoms);
	}
	
	@When("Service Advisor clicks on submit button")
	public void service_advisor_clicks_on_submit_button() {
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
	} 
	
	@Given("Add testData")
	public void add_test_data(io.cucumber.datatable.DataTable dataTable) {
		//getTestData().addUserData(dataTable);
		Assert.fail();
	}
	
	@Given("Service advisor clicks on Universal Symptom Survey")
	public void service_advisor_clicks_on_universal_symptom_survey() {
	    serviceAdvisorPage.getLeftSideMenuElement("Universal Symptom Survey").click();
	}
	@Given("Service advisor clicks on {string} under Observation Malfunction section and selects below concerns")
	public void service_advisor_clicks_on_under_observation_malfunction_section_and_selects_below_concerns(String string, io.cucumber.datatable.DataTable dataTable) {
	    List<String> listOption = BDDDataTableUtil.getAsList(dataTable);
	    customerConcernsComponent.selectConcerns(string, listOption);
	    }
	
	@Given("Service advisor clicks on {string} under when It Occurs section and selects below concerns")
	public void service_advisor_clicks_on_under_when_it_occurs_section_and_selects_below_concerns(String string, io.cucumber.datatable.DataTable dataTable) {
		List<String> listOption = BDDDataTableUtil.getAsList(dataTable);
	    customerConcernsComponent.selectConcerns(string, listOption);
	}
	
	@Given("Service advisor clicks on {string} hotspot	under Component Details and selects below values")
	public void service_advisor_clicks_on_hotspot_under_component_details_and_selects_below_values(String string, io.cucumber.datatable.DataTable dataTable) {
		List<String> listOption = BDDDataTableUtil.getAsList(dataTable);
	    customerConcernsComponent.selectHotSpot(string, listOption);
	}
	
	@Given("Service advisor clicks on {string} under Observation Malfunction section and verify below concerns are displayed")
	public void service_advisor_clicks_on_under_observation_malfunction_section_and_verify_below_concerns_are_displayed(String string, io.cucumber.datatable.DataTable dataTable) {
		List<String> listOption = BDDDataTableUtil.getAsList(dataTable);
		customerConcernsComponent.verifyAllConcernsPresent(string, listOption);
	}
	
	@Given("Service advisor clicks on {string} under Observation Malfunction section and get all concerns")
	public void service_advisor_clicks_on_under_observation_malfunction_section_and_get_all_concerns(String string) {
		
		customerConcernsComponent.getAllOptions(string).stream().forEach(e->System.out.println("|"+e.getText()+"|"));
	}

	
	
	
}
