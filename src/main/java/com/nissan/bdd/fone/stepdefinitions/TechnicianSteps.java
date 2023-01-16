package com.nissan.bdd.fone.stepdefinitions;

import static com.nissan.databeans.TestDataBeanManager.getTestData;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.BDDDataTableUtil;
import com.nissan.automation.core.utils.StringMatcher;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.enums.DropDownName;
import com.nissan.enums.Roles;
import com.nissan.enums.UserData;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.pages.TechnicianPage;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.SymptomsUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TechnicianSteps {

	private TechnicianPage technicianPage;

	public TechnicianSteps() {

		technicianPage = new TechnicianPage();
	}

	@When("login as Technician")
	public void login_as_technician() {
		technicianPage = new TechnicianPage();
		technicianPage.login(Roles.TECHNICIAN, getTestData().getUserData().get(UserData.DEALERCODE));
		technicianPage.waitForPageToLoad();
	}
	
	@When("login as Technician {string}")
	public void login_as_technician(String user) {
		technicianPage = new TechnicianPage();
		technicianPage.login(user);
		technicianPage.waitForPageToLoad();
	}

	@When("Technician opens dcase")
	public void technician_opens_dcase() {
		/*technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", getTestData().getRoNumber(),
				getTestData().getDCaseID());*/
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick(getTestData().getDCaseID());
			

	}
	
	@When("Technician opens Audio dcase")
	public void technician_opens_audio_dcase() {
		/*technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", getTestData().getRoNumber(),
				getTestData().getDCaseID());*/
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick(getTestData().getDCaseID());
		technicianPage.getAudioSymptomsSurveyFormComponent().waitForComponentToLoad();
		

	}

	
	@When("Technician opens CVT dcase")
	public void technician_opens_cvt_dcase() {
		/*technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", getTestData().getRoNumber(),
				getTestData().getDCaseID());*/
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick(getTestData().getDCaseID());
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().waitForComponentToLoad();
		

	}
	
	@When("Technician opens dcase {string} with RONumber {string}")
	public void technician_opens_dcase_with_ronumber(String dcase, String roNumber) {
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick(roNumber);

	}

	@When("Technician selects symptoms")
	public void technician_selects_symptoms() {
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent()
				.selectSymptoms(getTestData().getTechnicianSymptom());
	}

	@When("Technician selects below symptoms")
	public void technician_selects_below_symptoms(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		Map<UserData, String> map = getTestData().getUserData();
		String techCheckBoxString = map.get(UserData.TECHNICIANSYMPTOMCHECKBOXES);
		String techDropDownString = map.get(UserData.TECHNICIANSYMPTOMDROPDOWNS);
		String techComments = map.get(UserData.TECHNICIANOTHERSYMPTOMS);
		getTestData().settechnicianSymptom(techCheckBoxString, techDropDownString, techComments, "");
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent()
				.selectSymptoms(getTestData().getTechnicianSymptom());
	}

	
	@When("Technician selects the {string} from event drop down")
	public void technician_selects_the_from_event_drop_down(String event) {
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getAudioSymptomsSurveyFormComponent().getdTCListComponent().getDropDownWebElement(DropDownName.EVENT).select(event);
		WaitUtil.sleep(10000);
	}
	
	@When("Technician clicks on diagnose")
	public void Technician_clicks_on_diagnose() {
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		if(technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.isPresent()) {
			technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.scrollAndClick();
		}
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();
	}
	
	@When("Technician checks Iconfirm checkbox")
	public void technician_checks_iconfirm_checkbox(){
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
	}

	@When("Technician clicks on diagnose button")
	public void Technician_clicks_on_diagnose_button() {
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();
	} 
	@Then("Technician responds to Yes\\/No question with {string}")
	public void technician_responds_to_yes_no_question_with(String string) {

 		technicianPage.getQuestionsComponent().respondToYesNoQuestion(string, 1);

	}

	@Then("CCC screen is displayed")
	public void ccc_screen_is_displayed() {
		technicianPage.getcCCCOmponent();

	}

	@Then("Technician selects below values on ccc screen and clicks on submit button")
	public void Technician_selects_below_values_on_ccc_screen_and_clicks_on_submit_button(
			io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		technicianPage.getcCCCOmponent().txtBoxFailedFirstPFP
				.clearAndSendKeys(getTestData().getUserData().get(UserData.CCCPARTFILEDFIRST));
		technicianPage.getcCCCOmponent().dropdownWhyDoYouThinkPartFailed
				.select(getTestData().getUserData().get(UserData.CCCWHYPARTFAILED));
		technicianPage.getcCCCOmponent().dropdownRecommendRepair.scrollIntoView();
		technicianPage.getcCCCOmponent().dropdownRecommendRepair
				.select(getTestData().getUserData().get(UserData.CCCREPAIRRECOMMENDATION));
		technicianPage.getcCCCOmponent().textboxRepairJustification
				.clearAndSendKeys(getTestData().getUserData().get(UserData.CCCREPAIRJUSTIFICATIONCOMMENTS));
		technicianPage.getcCCCOmponent().btnSubmit.scrollAndClick();
	}

	@Then("Technician selects below values on ccc screen")
	public void Technician_selects_below_values_on_ccc_screen(io.cucumber.datatable.DataTable dataTable) {
		boolean isExpanded = technicianPage.getcCCCOmponent().headerConcern.getAttribute("title").equals("Hide Concern")
				? true
				: false;
		if (isExpanded) {
			technicianPage.getcCCCOmponent().headerConcern.click();
		}
		getTestData().addUserData(dataTable);
		technicianPage.getcCCCOmponent().txtBoxFailedFirstPFP
				.clearAndSendKeys(getTestData().getUserData().get(UserData.CCCPARTFILEDFIRST));
		technicianPage.getcCCCOmponent().dropdownWhyDoYouThinkPartFailed
				.select(getTestData().getUserData().get(UserData.CCCWHYPARTFAILED));
		technicianPage.getcCCCOmponent().dropdownRecommendRepair.scrollIntoView();
		technicianPage.getcCCCOmponent().dropdownRecommendRepair
				.select(getTestData().getUserData().get(UserData.CCCREPAIRRECOMMENDATION));
		technicianPage.getcCCCOmponent().textboxRepairJustification
				.clearAndSendKeys(getTestData().getUserData().get(UserData.CCCREPAIRJUSTIFICATIONCOMMENTS));

	}

	@Then("Technician selects below values in Cause section on ccc screen")
	public void Technician_selects_below_values_in_cause_section_on_ccc_screen(
			io.cucumber.datatable.DataTable dataTable) {
		boolean isExpanded = technicianPage.getcCCCOmponent().headerConcern.getAttribute("title").equals("Hide Concern");
		if (isExpanded) {
			technicianPage.getcCCCOmponent().headerConcern.click();
		}
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		technicianPage.getcCCCOmponent().headerCause.scrollIntoView();
		technicianPage.getcCCCOmponent().txtBoxFailedFirstPFP
				.clearAndSendKeys(getTestData().getUserData().get(UserData.CCCPARTFILEDFIRST));
		technicianPage.getcCCCOmponent().dropdownWhyDoYouThinkPartFailed
				.select(getTestData().getUserData().get(UserData.CCCWHYPARTFAILED));

	}

	@Then("Technician selects below values in Correction section with below repair recmmendation on ccc screen")
	public void Technician_selects_below_values_in_correction_section_with_below_repair_recmmendation_on_ccc_screen(
			io.cucumber.datatable.DataTable dataTable) {

		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		String expActualDefaultRepair = getTestData().getUserData().get(UserData.CCCREPAIRRECOMMENDATION);

		if (Objects.nonNull(getTestData().getUserData().get(UserData.CCCTECHNICIANDISAGREEDREPAIR))) {

			if (Boolean.parseBoolean(getTestData().getUserData().get(UserData.CCCTECHNICIANDISAGREEDREPAIR))) {
				technicianPage.getcCCCOmponent().txtDefaultRepair.scrollIntoView().verifyText(expActualDefaultRepair,
						"Repair Recommendation");
				technicianPage.getcCCCOmponent().chkboxTechnicianDisagrees.scrollAndClick();
				technicianPage.getcCCCOmponent().dropdownRecommendRepair.scrollIntoView()
				.select(getTestData().getUserData().get(UserData.CCCTECHNICIANREPAIRRECOMMENDATION));
				technicianPage.getcCCCOmponent().txtboxTechnicianComments
				.clearAndSendKeys(getTestData().getUserData().get(UserData.CCCREPAIRJUSTIFICATIONCOMMENTS));
				
				/*if (Objects.nonNull(getTestData().getUserData().get(UserData.CCCTECHNICIANREPAIRRECOMMENDATION))) {

					technicianPage.getcCCCOmponent().dropdownRecommendRepair.scrollIntoView()
							.select(getTestData().getUserData().get(UserData.CCCTECHNICIANREPAIRRECOMMENDATION));

				}

				if (Objects.nonNull(getTestData().getUserData().get(UserData.CCCREPAIRJUSTIFICATIONCOMMENTS))) {

					// technicianPage.getcCCCOmponent().txtboxTechnicianDisagreeComments.moveToElement();
					technicianPage.getcCCCOmponent().txtboxTechnicianComments
							.clearAndSendKeys(getTestData().getUserData().get(UserData.CCCREPAIRJUSTIFICATIONCOMMENTS));

				}*/

			}else {
				technicianPage.getcCCCOmponent().txtDefaultRepair.scrollIntoView().verifyText(expActualDefaultRepair,
						"Repair Recommendation");
			}
			return;
		}
		if (Objects.nonNull(getTestData().getUserData().get(UserData.CCCTECHNICIANREPAIRRECOMMENDATION))) {

			technicianPage.getcCCCOmponent().dropdownRecommendRepair.scrollIntoView()
					.select(getTestData().getUserData().get(UserData.CCCTECHNICIANREPAIRRECOMMENDATION));

		}
		if (Objects.nonNull(getTestData().getUserData().get(UserData.CCCREPAIRJUSTIFICATIONCOMMENTS))) {

					technicianPage.getcCCCOmponent().txtboxTechnicianComments
					.clearAndSendKeys(getTestData().getUserData().get(UserData.CCCREPAIRJUSTIFICATIONCOMMENTS));

		}

	}

	@Then("Technician clicks on submit and PCC questions screen is displayed")
	public void Technician_clicks_on_submit_and_pcc_questions_screen_is_displayed() {
		WaitUtil.sleep(2000);
		technicianPage.getcCCCOmponent().btnSubmit.scrollAndClick();
		technicianPage.getQuestionsComponent();
		if (getTestData().getTsbDataBean().getListTSB().size() == 1) {
			getTestData().setQAReferToPCC(true);
		}

	}

	@Then("Technician reponds to PCC Question with below responses")
	public void Technician_selects_below_reponds_to_pcc_question_with_below_responses(
			io.cucumber.datatable.DataTable dataTable) {

		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		int questionIndex = 0;
		if (getTestData().getTsbDataBean().getListTSB().size() == 1) {
			getTestData().setQAReferToPCC(true);
		}
		technicianPage.getQuestionsComponent()
				.respondToYesNoQuestion(getTestData().getUserData().get(UserData.PCCANYOILLEAKSPRESENT), ++questionIndex);
		technicianPage.getQuestionsComponent().respondToInputResponseQuestion(
				getTestData().getUserData().get(UserData.PCCLISTALLIFANYVEHICLEMODIFICATIONS), ++questionIndex);
		technicianPage.getQuestionsComponent()
				.respondToAttachmentQuestion(getTestData().getUserData().get(UserData.PCCATTACHMENTPATH), ++questionIndex);
		technicianPage.getQuestionsComponent().respondToInputResponseQuestion(
				getTestData().getUserData().get(UserData.PCCTECHLINECONTACTED), ++questionIndex);
		technicianPage.getQuestionsComponent().respondToInputResponseQuestion(
				getTestData().getUserData().get(UserData.PCCWHENWASTECHLINECONTACTED), ++questionIndex);
		WaitUtil.sleep(2000);
		technicianPage.getQuestionsComponent().btnSubmit.scrollIntoView();
		technicianPage.getQuestionsComponent().txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"),
				"No further questions");
	}
	
	@Then("Technical verifies pcc questions screen is displayed")
	public void technical_verifies_pcc_questions_screen_is_displayed() {
	   
		technicianPage.getQuestionsComponent();
		int tsbListSize = Objects.isNull(getTestData().getTsbDataBean())?0:getTestData().getTsbDataBean().getListTSB().size();
		if (tsbListSize == 1) {
			getTestData().setQAReferToPCC(true);
		}
	}
	
	@Then("Technician reponds to question {int} of {string} type with {string}")
	public void technician_reponds_to_question_of_type_with(Integer questionIndex, String questionType, String questionResponse) {
	   
		switch(questionType.toUpperCase()) {
		case "YES NO":
			technicianPage.getQuestionsComponent().respondToYesNoQuestion(questionResponse, questionIndex);
			break;
		case "INPUT RESPONSE":
			technicianPage.getQuestionsComponent().respondToInputResponseQuestion(questionResponse, questionIndex);
			break;
		case "ATTACHMENT":
			technicianPage.getQuestionsComponent().respondToAttachmentQuestion(questionResponse, questionIndex);
			break;
			
			default: throw new CustomRuntimeException("Question Type "+questionType+" is not valid");
			
		}
		
		
		
	}

	@Then("Technician responds to input response question with {string}")
	public void technician_responds_to_input_response_question_with(String string) {
		technicianPage.getQuestionsComponent().respondToInputResponseQuestion("Input", 2);
	}

	@Then("Technician selects below values in Correction section")
	public void Technician_selects_below_values_in_correction_section(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		technicianPage.getcCCCOmponent().dropdownRecommendRepair.scrollIntoView();
		technicianPage.getcCCCOmponent().dropdownRecommendRepair
				.select(getTestData().getUserData().get(UserData.CCCTECHNICIANREPAIRRECOMMENDATION));
		technicianPage.getcCCCOmponent().textboxRepairJustification
				.clearAndSendKeys(getTestData().getUserData().get(UserData.CCCREPAIRJUSTIFICATIONCOMMENTS));

	}

	@Then("Verify PCC Questions screen is displayed")
	public void verify_pcc_questions_screen_is_displayed() {
		technicianPage.getQuestionsComponent();
	}

	@Then("Technician verifies text No Further Questions")
	public void technician_verifies_text_no_further_questions() {
		technicianPage.getQuestionsComponent().btnSubmit.scrollIntoView();
		technicianPage.getQuestionsComponent().txtNoFurtherQuestions.assertText(StringMatcher.startsWith("No further"),
				"No further questions");

	}

	@Then("Technician verifies Unable to diagnose message is displayed")
	public void technician_verifies_unable_to_diagnose_message_is_displayed() {
		WaitUtil.sleep(2000);
		technicianPage.getQuestionsComponent().txtNoUnableToDiagnose.scrollIntoView();
		technicianPage.getQuestionsComponent().txtNoUnableToDiagnose.verifyText(StringMatcher.startsWith("Unable to"),"Unable to diagnose");
	}

	@Then("Technician clicks on submit")
	public void technician_clicks_on_submit() {

		technicianPage.getQuestionsComponent().btnSubmit.scrollAndClick();

	}

	@Then("Technician clicks on submit on ccc screen")
	public void Technician_clicks_on_submit_on_ccc_screen() {
		// Write code here that turns the phrase above into concrete actions
		technicianPage.getcCCCOmponent().btnSubmit.scrollAndClick();
	}

	@Then("Technician clicks on submit on questions screen")
	public void technician_clicks_on_submit_on_questions_screen() {

		technicianPage.getQuestionsComponent().btnSubmit.scrollAndClick();
	}

	@Then("Thank you screen is displayed to technician")
	public void thank_you_screen_is_displayed_to_technician() {

		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtThankYou.assertText(
				"Thank you! The next step in this case has been routed appropriately.", "Thank You message");
	}

	@Then("Technician logs out")
	public void technician_logs_out() {

		technicianPage.logOut();

	}

	@Then("Technician clicks on submits the case and logs out")
	public void technician_clicks_on_submits_the_case_and_logs_out() {
		technicianPage.getcCCCOmponent().btnSubmit.scrollAndClick();
		technicianPage.logOut();
	}

	@When("Technician selects Audio plays with ignition off check box")
	public void technician_selects_audio_plays_with_ignition_off_check_box() {
		technicianPage.getAudioSymptomsSurveyFormComponent().chkboxAudioPlaysWIthIgnitionOff.scrollAndClick();
	}
	
	
	@Then("Technician clicks on Start Universal Symptom from left side menu")
	public void technician_clicks_on_start_universal_symptom_from_left_side_menu() {
		technicianPage.getLeftSideMenuElement("Start Universal Symptom").click();
	}
	
	@Then("Technician opens RO from the RO table")
	public void technician_opens_ro_from_the_ro_table() {
		String rONumber = getTestData().getRoNumber();
		technicianPage.getSelectROTableComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		if(technicianPage.getSelectROTableComponent().txtConcernedMappedToOtherROErrorMsg.isPresent()) {
			String errorMsg = technicianPage.getSelectROTableComponent().txtConcernedMappedToOtherROErrorMsg.getText();
			String[] strArray = errorMsg.split("\\s+"); 
			String newRO = strArray[strArray.length-1];
			technicianPage.getLeftSideMenuElement("View all Universal Symptoms").click();
			technicianPage.getSelectUSSCasesROComponent().filterAndClick("RO# / WO#", newRO);
			getTestData().setRoNumber(newRO);
		}
		
	/*	String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent()
				.getHeaderWebElement("Case ID").getText();
		getTestData().setDCaseId(dCaseID);
		ExtentLogger.info("Diagnostics case created with case id: "+getTestData().getDCaseID(),true);	*/
	}
	
	@Then("Technician selects customer concerns and clicks on go")
	public void technician_selects_customer_concerns_and_clicks_on_go() {
		technicianPage.getSelectRepairOrderComponent().chkboxCustomerCOncern.scrollAndClick();
		
		technicianPage.getSelectRepairOrderComponent().btnGO.scrollAndClick();
	}
	
	@Then("Technician verifies Dcase is created")
	public void technician_verifies_dcase_is_created() {
		String dcase = technicianPage.getSelectRepairOrderComponent().listDcase.get(0).getText();
		Validator.assertTrue(Objects.nonNull(dcase) && dcase.startsWith("D-"), "Dcase created with case id"+dcase, "Dcase not created");
		getTestData().setDCaseId(dcase);
		
	}
	
	@Given("Technician opens dcase with dcaseID {string} and RO number {string}")
	public void technician_opens_dcase(String dcaseID, String roNumber) {
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick(dcaseID);
	}
	
	@Given("Technician selects below symptoms under General Symptoms")
	public void technician_selects_below_symptoms_under_general_symptoms(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.GENERALSYMPTOMS);
		technicianPage.getAudioSymptomsSurveyFormComponent().headerGeneralSymptoms.scrollIntoView();
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getTechnicianSymptom().addToSymptomSet(symptoms);
	}
	
	@Given("Technician selects below symptoms under Systems and components")
	public void technician_selects_below_symptoms_under_systems_and_components(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.SYSTEMSCOMPONENTS);
		technicianPage.getAudioSymptomsSurveyFormComponent().headerSystemsComponents.scrollIntoView();
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getTechnicianSymptom().addToSymptomSet(symptoms);
	}
	@Given("Technician selects below symptoms under Occurs when")
	public void technician_selects_below_symptoms_under_occurs_when(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.OCCURSWHEN);
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getTechnicianSymptom().addToSymptomSet(symptoms);
	}
	@Given("Technician selects below symptoms under Occurs where")
	public void technician_selects_below_symptoms_under_occurs_where(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.OCCURSWHERE);
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getTechnicianSymptom().addToSymptomSet(symptoms);
	}
	
	@Given("Technician selects below symptoms under Occurs condition")
	public void technician_selects_below_symptoms_under_occurs_condition(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.OCCURSCONDITION);
		technicianPage.getAudioSymptomsSurveyFormComponent().headerOccursCondition.scrollIntoView();
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getTechnicianSymptom().addToSymptomSet(symptoms);
	}
	@Given("Technician selects below symptoms under when did the concern begin dropdown")
	public void technician_selects_below_symptoms_under_when_did_the_concern_begin_dropdown(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		Map<UserData, String> map = getTestData().getUserData();
		SymptomsUtil.selectDropDownValue("CustomerConcern", map.get(UserData.WHENDIDTHECONCERNBEGIN));
		
		
	}
	@Given("Technician selects below symptoms under frequency dropdown")
	public void technician_selects_below_symptoms_under_frequency_dropdown(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		Map<UserData, String> map = getTestData().getUserData();
		SymptomsUtil.selectDropDownValue("Frequency", map.get(UserData.FREQUENCY));
		
	}
	

	@When("Technician selects below symptom from Vehicle does not move drop down")
	public void technician_selects_below_symptom_from_vehicle_does_not_move_drop_down(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String symptom = SymptomsUtil.selectDropDownValue(DropDownName.VEHICLEDOESNOTMOVE.getDropDownName(), map.get(UserData.VEHICLEDOESNOTMOVE));
		getTestData().getTechnicianSymptom().addToSymptomSet(symptom);
	}
	@When("Technician selects below symptom from vibration drop down")
	public void technician_selects_below_symptom_from_vibration_drop_down(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String symptom = SymptomsUtil.selectDropDownValue(DropDownName.VIBRATION.getDropDownName(), map.get(UserData.VIBRATION));
		getTestData().getTechnicianSymptom().addToSymptomSet(symptom);
	    
	}
	@When("Technician selects below symptom from Engine stalls when drop down")
	public void technician_selects_below_symptom_from_engine_stalls_when_drop_down(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String symptom = SymptomsUtil.selectDropDownValue(DropDownName.ENGINESTALLSWHEN.getDropDownName(), map.get(UserData.ENGINESTALLSWHEN));
		getTestData().getTechnicianSymptom().addToSymptomSet(symptom);
	}
	@When("Technician selects below symptoms under Poor Shift Quality section")
	public void technician_selects_below_symptoms_under_poor_shift_quality_section(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.POORSHIFTQUALITY);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().headerPoorShiftQuality.scrollIntoView();
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getTechnicianSymptom().addToSymptomSet(symptoms);
	}
	@When("Technician selects below symptoms under Noise section")
	public void technician_selects_below_symptoms_under_noise_section(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.NOISE);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().headerNoise.scrollIntoView();
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getTechnicianSymptom().addToSymptomSet(symptoms);
	}
	@When("Technician selects below symptoms under Occurs when section")
	public void technician_selects_below_symptoms_under_occurs_when_section(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		Map<UserData, String> map = getTestData().getUserData();
		String checkBoxString = map.get(UserData.OCCURSWHEN);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().headerOccursWhen.scrollIntoView();
		List<String> symptoms=SymptomsUtil.selectCheckBox(checkBoxString);
		getTestData().getTechnicianSymptom().addToSymptomSet(symptoms);
	}
@When("Technician enters following comments in comments textbox {string}")
public void technician_enters_following_comments_in_comments_textbox(String comments) {
	technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs.sendKeys(comments);
	getTestData().getTechnicianSymptom().setComments(comments);
}
	}
