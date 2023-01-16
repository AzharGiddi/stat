package com.nissan.bdd.fone.stepdefinitions;

import org.openqa.selenium.Keys;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.pages.ConnectorLookupExternalPage;
import com.nissan.pages.GeneralRepairInstructionsPage;
import com.nissan.pages.SupplementalPartsPage;
import com.nissan.pages.ToolsAndConnectorDisassemblyPage;
import com.nissan.pages.components.ConnectorLookupComponent;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ConnectorLookupExternalSteps {
	
	private ConnectorLookupExternalPage  connectorLookupExternalPage;
	private ConnectorLookupComponent connectorLookupComponent;
	 public ConnectorLookupExternalSteps() {
		 connectorLookupExternalPage = new ConnectorLookupExternalPage();
		// connectorLookupComponent = connectorLookupExternalPage.getConnectorLookupComponent();
	}
	
	@Given("Launch Extenral User Connector search url")
	public void launch_extenral_user_connector_search_url() {
		connectorLookupExternalPage.invoke();
	}
	@Given("Connector lookup page is displayed to external user")
	public void connector_lookup_page_is_displayed_to_external_user() {
		connectorLookupExternalPage.waitForPageToLoad();
	}
	
	@When("External user clicks on Connector Look up from left side menu")
	public void external_user_clicks_on_connector_look_up_from_left_side_menu() {
		
		connectorLookupExternalPage.getLeftSideMenuElement("Connector Lookup").click();
	}
	
	@Then("On connector lookup ui external user selects {string} radio button")
	public void on_connector_lookup_ui_external_user_selects_radio_button(String string) {
		connectorLookupExternalPage = new ConnectorLookupExternalPage();
		connectorLookupExternalPage.getConnectorLookupComponent().getConnectorByRadioButton(string).click();
		
	}
	@Then("External user enters {string} in input textbox")
	public void external_user_enters_in_input_textbox(String keyword) {
		connectorLookupExternalPage.txtboxInputbox.clearAndSendKeys(keyword+Keys.TAB);
		//WaitUtil.sleep(2000);
	}
	@Then("External user clicks on go button")
	public void external_user_clicks_on_go_button() {
		connectorLookupExternalPage.getConnectorLookupComponent().btnSearch.click();
	}
	@Then("External user verify list of connectors are displayed")
	public void external_user_verify_list_of_connectors_are_displayed() {
		//connectorLookupExternalPage.tableResultsGrid.waitForVisible(20000);
	//	WaitUtil.sleep(2000);
		connectorLookupExternalPage.getConnectorLookupComponent().tableResultsGrid.verifyVisible("Connector List");
	}
	@Then("External user clicks on first record from the list of connectors")
	public void external_user_clicks_on_first_record_from_the_list_of_connectors() {
		//connectorLookupExternalPage.linkFirstConnectorRecord.waitForVisible(5000);
WaitUtil.sleep(2000);
		
		connectorLookupExternalPage.getConnectorLookupComponent().listConnectorRecord.get(0).click();
		
	}
	@Then("External user verify Connector Info screen is displayed")
	public void external_user_verify_connector_info_screen_is_displayed() {
		//WaitUtil.sleep(2000);
		connectorLookupExternalPage.getConnectorLookupComponent().txtConnectorInfo.verifyVisible("Connector Info");
	}
	@Then("External user click on back to search results button")
	public void external_user_click_on_back_to_search_results_link() {
		
		connectorLookupExternalPage.getConnectorLookupComponent().btnBackToSearchResults.click();
	}
	
	@Then("External user clicks on back to search button")
	public void external_user_clicks_on_back_to_search_button() {
		connectorLookupExternalPage.getConnectorLookupComponent().btnBackToSearch.click();
	}
	
	@Then("External User selects {string} from make drop down")
	public void external_user_selects_from_make_drop_down(String make) {
		connectorLookupExternalPage.getConnectorLookupComponent().dropdownMake.select(make);
	}
	@Then("External User selects {string} from model drop down")
	public void external_user_selects_from_model_drop_down(String model) {
		WaitUtil.sleep(2000);
		connectorLookupExternalPage.getConnectorLookupComponent().dropdownModel.selectByValue(model);
	}
	@Then("External User selects {string} from year drop down")
	public void external_user_selects_from_year_drop_down(String year) {
		WaitUtil.sleep(2000);
		connectorLookupExternalPage.getConnectorLookupComponent().dropdownYear.selectByValue(year);
	}
	
	@Then("External user clicks on back to search results link")
	public void external_user_clicks_on_back_to_search_results_link() {
		connectorLookupExternalPage.getConnectorLookupComponent().linkBackToSearch.click();
	}
	@Then("External user Verifies connector lookup ui is displayed")
	public void external_user_verifies_connector_lookup_ui_is_displayed() {
	//	WaitUtil.sleep(2000);
		connectorLookupExternalPage.getConnectorLookupComponent().textFindConnectorsBy.verifyVisible("Find Connector By");
	}
	

	@Then("External User verifies list of connectors are displayed")
	public void external_user_verifies_list_of_connectors_are_displayed() {
		connectorLookupExternalPage.getConnectorLookupComponent().tableResultsGrid.verifyVisible("Connector Table");
	}
	@Then("External User clicks on feedback button")
	public void external_user_clicks_on_feedback_button() {
		connectorLookupExternalPage.getConnectorLookupComponent().btnFeedback.scrollAndClick();
	}
	@Then("External User verifies feedback comments modal window is displayed")
	public void external_user_verifies_feedback_comments_modal_window_is_displayed() {
		
		connectorLookupExternalPage.getConnectorLookupComponent().getFeedbackComponent().header.verifyText("Feedback Comments", "Feedback Comments");
	}
	@Then("External User enters {string} in the External? field")
	public void external_user_enters_in_the_external_field(String string) {
		connectorLookupExternalPage.getConnectorLookupComponent().getFeedbackComponent().getElement("Dealer").sendKeys(string);
	}
	@Then("External User enters {string} in Names field")
	public void external_user_enters_in_names_field(String string) {
		connectorLookupExternalPage.getConnectorLookupComponent().getFeedbackComponent().getElement("Name").sendKeys(string);
	}
	@Then("External User enters {string} in Email id field")
	public void external_user_enters_in_email_id_field(String string) {
		connectorLookupExternalPage.getConnectorLookupComponent().getFeedbackComponent().getElement("Email").sendKeys(string);
	}
	@Then("External User verifies make\\/model\\/year field is autopopulated with {string}")
	public void external_user_verifies_make_model_year_field_is_autopopulated_with(String string) {
		connectorLookupExternalPage.getConnectorLookupComponent().getFeedbackComponent().getElement("Make/Model/Year").verifyAttribute("value", string,"Make/Model/Year");
	}
	@Then("External User enter {string} in additional comments text box")
	public void external_user_enter_in_additional_comments_text_box(String string) {
		connectorLookupExternalPage.getConnectorLookupComponent().getFeedbackComponent().txtboxAdditionalComments.sendKeys(string);
	}
	@Then("External User attaches a file")
	public void external_user_attaches_a_file() {
		WaitUtil.sleep(2000);
		connectorLookupExternalPage.getConnectorLookupComponent().getFeedbackComponent().inputSelectFile.sendKeys("C:\\Users\\ab00789853\\eclipse-workspace\\stat\\data\\TestAttachment.txt");
		WaitUtil.sleep(2000);
	}
	@Then("External User clicks on submit")
	public void external_user_clicks_on_submit() {
		connectorLookupExternalPage.getConnectorLookupComponent().getFeedbackComponent().btnSubmit.scrollAndClick();
	}
	@Then("External User verifies Thank you for your comments message is displayed")
	public void external_user_verifies_thank_you_for_your_comments_message_is_displayed() {
		connectorLookupExternalPage = new ConnectorLookupExternalPage();
		connectorLookupExternalPage.getConnectorLookupComponent().gethRKConnectorDetailsComponent().txtThankYouForYourComment.verifyText("Thank you for your comment. Your request will be considered for future connector lookup updates", "Thank You message");
	}
	
	@Then("External User clicks on connector general repair instructions button")
	public void external_user_clicks_on_connector_general_repair_instructions_button() {
		connectorLookupExternalPage.getConnectorLookupComponent().gethRKConnectorDetailsComponent().getConnectorInformationComponent().btnGeneralRepairInstructions.click();
		WaitUtil.sleep(2000);
	}
	
	private GeneralRepairInstructionsPage  generalRepairInstructionsPage;
	@Then("External User verifies GENERAL REPAIR INSTRUCTIONS page is displayed in a new window")
	public void external_user_verifies_general_repair_instructions_page_is_displayed_in_a_new_window() {
		generalRepairInstructionsPage = new GeneralRepairInstructionsPage();
		generalRepairInstructionsPage.waitForPageToLoad();
		
	}
	@Then("External User closes GENERAL REPAIR INSTRUCTIONS page")
	public void external_user_closes_general_repair_instructions_page() {
		generalRepairInstructionsPage.closeWindow();
	}
	@Then("External User clicks on Connector Disassembly Instructions button")
	public void external_user_clicks_on_connector_disassembly_instructions_button() {
		connectorLookupExternalPage = new ConnectorLookupExternalPage();
		connectorLookupExternalPage.getConnectorLookupComponent().gethRKConnectorDetailsComponent().getConnectorInformationComponent().btnConnectorDisassemblyInstructions.click();
		WaitUtil.sleep(2000);
	}
	private ToolsAndConnectorDisassemblyPage viewDisassembleInstructionsPage;
	@Then("External User verifies Connector Disassembly Instructions page is displayed with heading {string} in a new window")
	public void external_user_verifies_connector_disassembly_instructions_page_is_displayed_with_heading_in_a_new_window(String string) {
		viewDisassembleInstructionsPage = new ToolsAndConnectorDisassemblyPage();
	    viewDisassembleInstructionsPage.waitForPageToLoad();
	}
	
		@Then("External User closes Connector Disassembly Instructions page")
	public void external_user_closes_connector_disassembly_instructions_page() {
		viewDisassembleInstructionsPage.closeWindow();
	}
	
	@Then("External User clicks on Required Related Parts button")
	public void external_user_clicks_on_required_related_parts_button() {
		connectorLookupExternalPage = new ConnectorLookupExternalPage();
		connectorLookupExternalPage.getConnectorLookupComponent().gethRKConnectorDetailsComponent().getConnectorInformationComponent().btnRequiredRelatedParts.click();		
		WaitUtil.sleep(2000);
	}
	
	private SupplementalPartsPage supplementalPartsPage;
	@Then("External User verifies Suplemental parts page is displayed in a new window")
	public void external_user_verifies_suplemental_parts_page_is_displayed_in_a_new_window() {
		supplementalPartsPage = new SupplementalPartsPage();
		supplementalPartsPage.waitForPageToLoad();
	}
	
	@Then("External User uses smart search and search with {string}")
	public void external_user_uses_smart_search_and_search_with(String string) {
		connectorLookupExternalPage.getConnectorLookupComponent().txtboxFilterGrid.sendKeys(string+Keys.TAB);
		connectorLookupExternalPage.getConnectorLookupComponent().btnSearchFilterGrid.click();
		WaitUtil.sleep(2000);
	}
	
	@Then("External User verifies {string} column is displayed in {int} position")
	public void external_user_verifies_column_is_displayed_in_position(String headerName, Integer position) {
	    ExtWebElement columnHeader = connectorLookupExternalPage.getConnectorLookupComponent().listConnectorTableColumnHeaders.get(position-1);
	    columnHeader.scrollIntoView();
		String actualHeaderName = columnHeader.getAttribute("data-attribute-name");
		Validator.assertText(headerName, actualHeaderName, "Column Header");
	}
	
	@When("External User enter {string} in Connector Keyword Type Part textbox")
		public void external_user_enter_in_connector_keyword_type_part_textbox(String connectorId) {
		connectorLookupExternalPage.getConnectorLookupComponent().txtBoxEnterConnectorNo.sendKeys(connectorId);
		}
	
	
	
	
	
}
