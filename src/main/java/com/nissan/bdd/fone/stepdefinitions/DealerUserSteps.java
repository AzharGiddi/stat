package com.nissan.bdd.fone.stepdefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.driver.DriverFactory;
import com.nissan.driver.DriverManager;
import com.nissan.enums.Roles;
import com.nissan.pages.DealerUserPage;
import com.nissan.pages.GeneralRepairInstructionsPage;
import com.nissan.pages.SupplementalPartsPage;
import com.nissan.pages.ViewConnectorPage;
import com.nissan.pages.ToolsAndConnectorDisassemblyPage;
import com.nissan.pages.ViewSolderSleevePage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DealerUserSteps {

	private DealerUserPage dealerUserPage;

	String rONumber;

	public DealerUserSteps() {
		dealerUserPage = new DealerUserPage();
	}

	@Given("Login as User {string}")
	public void login_as_dealer_user_of_dealer(String user) {
		dealerUserPage.login(user);
		dealerUserPage.waitForPageToLoad();
	}

	@Given("User clicks on {string} from left side menu")
	public void dealer_user_clicks_on_from_left_side_menu(String string) {
		dealerUserPage.getLeftSideMenuElement(string).click();
	}

	@Given("User verifies connector lookup ui is displayed")
	public void dealer_user_verifies_connector_lookup_ui_is_displayed() {
		WaitUtil.sleep(1000);
		dealerUserPage.getConnectorLookupComponent().textFindConnectorsBy.verifyVisible("Find Connector By");
	}

	@When("On connector lookup ui User selects {string} radio button")
	public void on_connector_lookup_ui_dealer_user_selects_radio_button(String string) {
		dealerUserPage.getConnectorLookupComponent().getConnectorByRadioButton(string).click();
	}

	@When("User selects {string} from make drop down")
	public void dealer_user_selects_from_make_drop_down(String make) {
		WaitUtil.sleep(2000);
		dealerUserPage.getConnectorLookupComponent().dropdownMake.select(make);
	}

	@When("User selects {string} from model drop down")
	public void dealer_user_selects_from_model_drop_down(String model) {
		WaitUtil.sleep(2000);
		dealerUserPage.getConnectorLookupComponent().dropdownModel.selectByValue(model);
	}

	@When("User selects {string} from year drop down")
	public void dealer_user_selects_from_year_drop_down(String year) {
		WaitUtil.sleep(2000);
		dealerUserPage.getConnectorLookupComponent().dropdownYear.selectByValue(year);
	}

	@Then("User verifies list of connectors are displayed")
	public void dealer_user_verifies_list_of_connectors_are_displayed() {
		// WaitUtil.sleep(2000);
		// dealerUserPage.getConnectorLookupComponent().tableResultsGrid.waitForVisible(2000);
		dealerUserPage = new DealerUserPage();
		dealerUserPage.getConnectorLookupComponent().tableResultsGrid.verifyVisible("Connector Table");
	}

	@Then("User click on first record from the list of connectors")
	public void dealer_user_click_on_first_record_from_the_list_of_connectors() {
		WaitUtil.sleep(1000);
		dealerUserPage.getConnectorLookupComponent().linkFirstConnectorRecord.click();
	}

	@Then("User verifies Connector Info screen is displayed")
	public void dealer_user_verifies_connector_info_screen_is_displayed() {
		WaitUtil.sleep(1000);
		dealerUserPage.getConnectorLookupComponent().txtConnectorInfo.verifyVisible("Connector Information");
	}

	@Then("User clicks on back to search results button")
	public void dealer_user_clicks_on_back_to_search_results_button() {
		dealerUserPage.getConnectorLookupComponent().btnBackToSearchResults.click();
	}

	/*
	 * @Then("User clicks on back to search button") public void
	 * user_clicks_on_back_to_search_button() {
	 * dealerUserPage.getConnectorLookupComponent().btnBackToSearch.click(); }
	 */

	@Then("User logs out")
	public void dealer_user_logs_out() {

		dealerUserPage.logOut();
	}

	@Then("User enter {string} in input textbox")
	public void dealer_user_enter_in_input_textbox(String string) {
		dealerUserPage.getConnectorLookupComponent().txtboxInputbox.clearAndSendKeys(Keys.chord(string));
	}

	@Then("User enters four characters {string} in input textbox")
	public void dealer_user_enters_four_characters_in_input_textbox(String string) {
		dealerUserPage.getConnectorLookupComponent().txtboxInputbox
				.clearAndSendKeys(Keys.chord(string) + Keys.ARROW_DOWN);
	}

	@Then("User clicks on search icon")
	public void dealer_user_clicks_on_go_button() {
		WaitUtil.sleep(2000);
		dealerUserPage.getConnectorLookupComponent().btnSearch.click();

	}

	@Then("User clicks on back to search button")
	public void dealer_user_clicks_on_back_to_search_button() {
		dealerUserPage.getConnectorLookupComponent().btnBackToSearch.scrollAndClick();
	}

	@Then("User clicks on first record from the list of connectors")
	public void dealer_user_clicks_on_first_record_from_the_list_of_connectors() {
		WaitUtil.sleep(2000);
		dealerUserPage.getConnectorLookupComponent().linkFirstConnectorRecord.click();
	}

	@Then("User verifies list of ROs is displayed")
	public void dealer_user_verifies_list_of_r_os_is_displayed() {
		Validator.assertTrue(!dealerUserPage.getConnectorLookupComponent().listRO.isEmpty(), "RO List size is empty");
		dealerUserPage.getConnectorLookupComponent().listRO.get(0).click();
	}

	@Then("User verifies list of ROs is not displayed")
	public void dealer_user_verifies_list_of_r_os_is_not_displayed() {
		Validator.assertTrue(dealerUserPage.getConnectorLookupComponent().listRO.isEmpty(),
				"RO List size is not empty");

	}

	@Given("User verifies Connector General Repair Instructions link is displayed")
	public void dealer_user_verifies_connector_general_repair_instructions_link_is_displayed() {
		dealerUserPage.getConnectorLookupComponent().linkGeneralRepair.verifyVisible("Genral Repair link");
	}

	@Given("User clicks on connector general repair instructions link")
	public void dealer_user_clicks_on_connector_general_repair_instructions_link() {
		dealerUserPage.getConnectorLookupComponent().linkGeneralRepair.click();
	}

	private GeneralRepairInstructionsPage generalRepairInstructionsPage;

	@Given("User verifies GENERAL REPAIR INSTRUCTIONS page is displayed in a new window")
	public void dealer_user_verifies_general_repair_instructions_page_is_displayed_in_a_new_window() {
		generalRepairInstructionsPage = new GeneralRepairInstructionsPage();
		generalRepairInstructionsPage.waitForPageToLoad();

	}

	@Then("User verifies No Matching record text is displayed")
	public void user_verifies_no_matching_record_text_is_displayed() {
		dealerUserPage.getConnectorLookupComponent().txtNoResultFound.assertVisible("No Result Found");
	}

	@Then("User clicks on feedback button")
	public void dealer_user_clicks_on_feedback_button() {
		dealerUserPage.getConnectorLookupComponent().btnFeedback.scrollAndClick();
	}

	@Then("User verifies feedback comments modal window is displayed")
	public void dealer_user_verifies_feedback_comments_modal_window_is_displayed() {
		dealerUserPage.getConnectorLookupComponent().getFeedbackComponent().header.verifyText("Feedback Comments",
				"Feedback Comments");
	}

	@Then("User enters {string} in the Dealer? field")
	public void dealer_user_enters_in_the_dealer_field(String string) {
		dealerUserPage.getConnectorLookupComponent().getFeedbackComponent().getElement("Dealer?").sendKeys(string);
	}

	@Then("User verifies Dealer field is autopopulated with {string}")
	public void dealer_user_verifies_dealer_field_is_autopopulated_with(String string) {
		dealerUserPage.getConnectorLookupComponent().getFeedbackComponent().getElement("Dealer")
				.verifyAttribute("value", string, "Name");
	}

	@Then("User verifies Name field is autopopulated with {string}")
	public void dealer_user_verifies_name_field_is_autopopulated_with(String string) {
		dealerUserPage.getConnectorLookupComponent().getFeedbackComponent().getElement("Name").verifyAttribute("value",
				string, "Name");
	}

	@Then("User verifies Email field is autopopulated with {string}")
	public void dealer_user_verifies_email_field_is_autopopulated_with(String string) {
		dealerUserPage.getConnectorLookupComponent().getFeedbackComponent().getElement("Email").verifyAttribute("value",
				string, "Email");
	}

	@Then("User verifies make\\/model\\/year field is autopopulated with {string} {string} {string}")
	public void dealer_user_verifies_make_model_year_field_is_autopopulated_with(String string, String string2,
			String string3) {
		String expectedString = string + " - " + string2 + " - " + string3;
		dealerUserPage.getConnectorLookupComponent().getFeedbackComponent().getElement("Make/Model/Year")
				.verifyAttribute("value", expectedString, "Make/Model/Year");
	}

	@Then("User attaches a file")
	public void dealer_user_attaches_a_file() {
		WaitUtil.sleep(2000);
		dealerUserPage.getConnectorLookupComponent().getFeedbackComponent().inputSelectFile
				.sendKeys("C:\\Users\\ab00789853\\eclipse-workspace\\stat\\data\\TestAttachment.txt");
		WaitUtil.sleep(2000);
	}

	@Then("User clicks on submit")
	public void dealer_user_clicks_on_submit() {
		dealerUserPage.getConnectorLookupComponent().getFeedbackComponent().btnSubmit.scrollAndClick();
	}

	@Then("User verifies Thank you for your comments message is displayed")
	public void dealer_user_verifies_thank_you_for_your_comments_message_is_displayed() {
		dealerUserPage = new DealerUserPage();
		dealerUserPage.getConnectorLookupComponent().gethRKConnectorDetailsComponent().txtThankYouForYourComment
				.verifyText(
						"Thank you for your comment. Your request will be considered for future connector lookup updates",
						"Thank You message");
	}

	@Then("User enter {string} in additional comments text box")
	public void dealer_user_enter_in_additional_comments_text_box(String string) {
		dealerUserPage.getConnectorLookupComponent().getFeedbackComponent().txtboxAdditionalComments.sendKeys(string);
	}

	@Then("User uses smart search and search with {string}")
	public void dealer_user_uses_smart_search_and_search_with(String string) {
		dealerUserPage.getConnectorLookupComponent().txtboxFilterGrid.sendKeys(string + Keys.TAB);
		dealerUserPage.getConnectorLookupComponent().btnSearchFilterGrid.click();
		WaitUtil.sleep(2000);

	}

	@Then("User clicks on connector general repair instructions button")
	public void dealer_user_clicks_on_connector_general_repair_instructions_button() {
		dealerUserPage.getConnectorLookupComponent().gethRKConnectorDetailsComponent()
				.getConnectorInformationComponent().btnGeneralRepairInstructions.click();
		WaitUtil.sleep(2000);
	}

	@Then("User closes GENERAL REPAIR INSTRUCTIONS page")
	public void dealer_user_closes_general_repair_instructions_page() {
		generalRepairInstructionsPage.closeWindow();
	}

	@Then("User closes Connector Disassembly Instructions page")
	public void dealer_user_closes_connector_disassembly_instructions_page() {

		viewDisassembleInstructionsPage.closeWindow();
	}

	@Then("User clicks on Connector Disassembly Instructions button")
	public void dealer_user_clicks_on_connector_disassembly_instructions_button() {
		dealerUserPage = new DealerUserPage();
		dealerUserPage.getConnectorLookupComponent().gethRKConnectorDetailsComponent()
				.getConnectorInformationComponent().btnConnectorDisassemblyInstructions.click();
		WaitUtil.sleep(2000);
	}

	private ToolsAndConnectorDisassemblyPage viewDisassembleInstructionsPage;

	@Then("User verifies Connector Disassembly Instructions page is displayed with heading {string} in a new window")
	public void dealer_user_verifies_connector_disassembly_instructions_page_is_displayed_with_heading_in_a_new_window(
			String string) {
		viewDisassembleInstructionsPage = new ToolsAndConnectorDisassemblyPage();
		viewDisassembleInstructionsPage.waitForPageToLoad();
		// viewDisassembleInstructionsPage.header.verifyText(string, "Page header");

	}

	@Then("User clicks on Required Related Parts button")
	public void dealer_user_clicks_on_required_related_parts_button() {
		dealerUserPage = new DealerUserPage();
		dealerUserPage.getConnectorLookupComponent().gethRKConnectorDetailsComponent()
				.getConnectorInformationComponent().btnRequiredRelatedParts.click();
		WaitUtil.sleep(2000);
	}

	private SupplementalPartsPage supplementalPartsPage;

	@Then("User verifies Suplemental parts page is displayed in a new window")
	public void dealer_user_verifies_suplemental_parts_page_is_displayed_in_a_new_window() {
		supplementalPartsPage = new SupplementalPartsPage();
		supplementalPartsPage.waitForPageToLoad();
	}

	@When("User enters {string} in vin textbox")
	public void dealer_user_enters_in_vin_textbox(String string) {
		dealerUserPage.getConnectorLookupComponent().txtBoxEnterVIN.sendKeys(string);
	}

	@When("User clicks on search button")
	public void dealer_user_clicks_on_search_button() {
		dealerUserPage.getConnectorLookupComponent().btnSearch.click();
	}

	@Then("User clicks on i icon in the Kit description of first record from the list of connectors")
	public void dealer_user_clicks_on_i_icon_in_the_kit_description_of_first_record_from_the_list_of_connectors() {
		dealerUserPage.getConnectorLookupComponent().infoIconKitDescriptionFirstResult.click();
		WaitUtil.sleep(2000);
	}

	ViewConnectorPage viewConnectorPage;

	@Then("User verifies View Connector Page is displayed in a new window")
	public void dealer_user_verifies_view_connector_page_is_displayed_in_a_new_window() {
		viewConnectorPage = new ViewConnectorPage();
		viewConnectorPage.waitForPageToLoad();
	}

	@Then("User clicks on i icon of the Kit description under connector info section")
	public void dealer_user_clicks_on_i_icon_of_the_kit_description_under_connector_info_section() {
		dealerUserPage.getConnectorLookupComponent().gethRKConnectorDetailsComponent()
				.getConnectorInformationComponent().infoIconKitDescription.click();
		WaitUtil.sleep(2000);
	}

	@Then("User closes View Connector Page")
	public void dealer_user_closes_view_connector_page() {
		viewConnectorPage.closeWindow();
	}

	@Then("User clicks on i icon of the AWG Wire under connector info section")
	public void dealer_user_clicks_on_i_icon_of_the_awg_wire_under_connector_info_section() {
		dealerUserPage = new DealerUserPage();
		dealerUserPage.getConnectorLookupComponent().gethRKConnectorDetailsComponent()
				.getConnectorInformationComponent().infoIconAWGWire.click();
		WaitUtil.sleep(2000);
	}

	ViewSolderSleevePage viewSolderSleevePage;

	@Then("User verifies View Solder Sleeve Page is displayed in a new window")
	public void dealer_user_verifies_view_solder_sleeve_page_is_displayed_in_a_new_window() {
		viewSolderSleevePage = new ViewSolderSleevePage();
		viewSolderSleevePage.waitForPageToLoad();
	}

	@Then("User closes View Solder Sleeve Page")
	public void dealer_user_closes_view_solder_sleeve_page() {
		viewSolderSleevePage.closeWindow();
	}

	@Then("User clicks on i icon of the Solder Sleeve under connector info section")
	public void dealer_user_clicks_on_i_icon_of_the_solder_sleeve_under_connector_info_section() {
		dealerUserPage = new DealerUserPage();
		dealerUserPage.getConnectorLookupComponent().gethRKConnectorDetailsComponent()
				.getConnectorInformationComponent().infoIconSolderSleeve.click();
		WaitUtil.sleep(2000);
	}
	
	@Then("User verifies {string} column is displayed in {int} position")
	public void user_verifies_column_is_displayed_in_position(String headerName, Integer position) {
	    ExtWebElement columnHeader = dealerUserPage.getConnectorLookupComponent().listConnectorTableColumnHeaders.get(position-1);
	    columnHeader.scrollIntoView();
		String actualHeaderName = columnHeader.getAttribute("data-attribute-name");
		Validator.assertText(headerName, actualHeaderName, "Column Header");
	}
	
	@When("User enter {string} in Connector Keyword Type Part textbox")
	public void user_enter_in_connector_keyword_type_part_textbox(String connectorId) {
		dealerUserPage.getConnectorLookupComponent().txtBoxEnterConnectorNo.sendKeys(connectorId);
	}
	
	@Then("User clicks on {string} file label")
	public void user_clicks_on_file_label(String string) {
		generalRepairInstructionsPage.getLinkElementWith(string).scrollAndClick();
	    
	}

	@Then("User verifies pdf file is opened in reading frame")
	public void user_verifies_pdf_file_is_opened_in_reading_frame() {
		generalRepairInstructionsPage.readingFramePDF.assertVisible();
	}
	
	@Then("User verifies video file is opened in reading frame")
	public void user_verifies_video_file_is_opened_in_reading_frame() {
		generalRepairInstructionsPage.readingFrameVideo.assertVisible();
	}
	

@Then("User clicks on {string} file label on TOOLS AND CONNECTOR DISASSEMBLY page")
public void user_clicks_on_file_label_on_tools_and_connector_disassembly_page(String string) {
	viewDisassembleInstructionsPage.getLinkElementWith(string).click();
	String readyState = (String) DriverManager.getJavaScriptExecutor().executeScript("return document.readyState");
	System.out.println("Ready state is : "+readyState);
}

@Then("User verifies file is opened in reading frame on TOOLS AND CONNECTOR DISASSEMBLY page")
public void user_verifies_file_is_opened_in_reading_frame_on_tools_and_connector_disassembly_page() {
	viewDisassembleInstructionsPage.readingFrame.waitForFrameToLoad();
	viewDisassembleInstructionsPage.readingFrame.assertVisible();
}

	

}
