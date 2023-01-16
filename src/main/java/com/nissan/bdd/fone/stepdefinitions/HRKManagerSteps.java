package com.nissan.bdd.fone.stepdefinitions;

import org.openqa.selenium.By;

import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.driver.DriverManager;
import com.nissan.enums.Roles;
import com.nissan.pages.ConnectorLookupExternalPage;
import com.nissan.pages.HRKManagerPage;
import com.nissan.pages.components.HRKAdditionalInformationComponent;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class HRKManagerSteps {
	
	private HRKManagerPage hRKManagerPage;
	
	private ConnectorLookupExternalPage connectorLookupExternalPage;
	
	public HRKManagerSteps() {
		hRKManagerPage = new HRKManagerPage();
	}
	
	@Given("Login to the application as HRK Manager")
	public void login_to_the_application_as_hrk_manager() {
		hRKManagerPage = new HRKManagerPage();
		hRKManagerPage.login(Roles.HRKMANAGER);
		hRKManagerPage.waitForPageToLoad();
	
	}
	
	@Then("HRK Manager verifies {string} and {string} are displayed under Navigation section")
	public void hrk_manager_verifies_and_are_displayed_under_navigation_section(String string, String string2) {
		hRKManagerPage.getLeftSideMenuElement(string).verifyPresent(string+" link");
		hRKManagerPage.getLeftSideMenuElement(string2).verifyPresent(string2+" link");
		
	}
	@Then("HRK Manager clicks on {string}")
	public void hrk_manager_clicks_on(String string) {
	
		hRKManagerPage.getLeftSideMenuElement(string).click();
	}
	@Then("HRK Manager verifies Manage System Rules tab is displayed")
	public void hrk_manager_verifies_manage_system_rules_tab_is_displayed() {
	   hRKManagerPage.getManageSystemRulesComponent().getMenuElement("HRK - Whats New Messaging").verifyPresent("HRK - Whats New Messaging");
	}
	@Then("HRK Manager verifies Reports tab is displayed")
	public void hrk_manager_verifies_reports_tab_is_displayed() {
	  
		 hRKManagerPage.gethRKReportsComponent().headerReport.verifyPresent("Reports");
	}
	
	@Then("HRK Manager click on {string}")
	public void hrk_manager_click_on(String string) {
		hRKManagerPage.getLeftSideMenuElement(string).click();
	}
	@Then("HRK Manager unchecks the Enable Message button")
	public void hrk_manager_unchecks_the_enable_message_button() {
		hRKManagerPage.getManageSystemRulesComponent().getWhatsNewMessagingComponent().chkboxEnableMessage
				.moveToElement();

		if (!hRKManagerPage.getManageSystemRulesComponent().getWhatsNewMessagingComponent().chkboxEnableMessage
				.isSelected()) {
			hRKManagerPage.getManageSystemRulesComponent().getWhatsNewMessagingComponent().chkboxEnableMessage.click();
		}
	}
	@Then("HRK Manager logs out")
	public void hrk_manager_logs_out() {
		hRKManagerPage.logOut();
	}
	@Then("Extenral User Verfies Whats new message is not displayed")
	public void extenral_user_verfies_whats_new_message_is_not_displayed() {
		connectorLookupExternalPage.getDashboardComponent().getWhatsNewComponent().textWhatsNew.verifyNotPresent("Whats New Text");
	}
	
	@Then("HRK Manager click on {string} under Manage system rules")
	public void hrk_manager_click_on_under_manage_system_rules(String string) {
		hRKManagerPage.getManageSystemRulesComponent().getMenuElement(string).click();
		WaitUtil.sleep(5000);
	}
	
	@Then("HRK Manager verifies Manage Connector tab is opened with heading {string}")
	public void hrk_manager_verifies_manage_connector_tab_is_opened_with_heading(String string) {
		hRKManagerPage.getHRKManageConnectorsComponent().header.verifyText(string, "Manage Connector header");
	}
	@Then("On Manage Connector tab, HRK Manager verifies table with connector data is displayed")
	public void on_manage_connector_tab_hrk_manager_verifies_table_with_connector_data_is_displayed() {
	   
	}
	@Then("HRK Manager verifies table header has {string} as column header")
	public void hrk_manager_verifies_table_header_has_as_column_header(String string) {
		hRKManagerPage.getHRKManageConnectorsComponent().getTableColumnHeaders(string).verifyVisible(string);
	}
	@Then("HRK Manager verifies {int} rows are displayed per page")
	public void hrk_manager_verifies_rows_are_displayed_per_page(Integer int1) {
		Validator.assertTrue(hRKManagerPage.getHRKManageConnectorsComponent().listConnectorData.size()<=int1, "Size not equal");
	}
	@Then("HRK Manager clicks on record no. {int} from the list of connectors")
	public void hrk_manager_clicks_on_record_no_from_the_list_of_connectors(Integer int1) {
		hRKManagerPage.getHRKManageConnectorsComponent().openConnectorDataAtIndex(int1);
		hRKManagerPage.closeTab("Manage Connec...");
	}
	
	@Then("HRK Manager verifies Connector Info page is displayed")
	public void hrk_manager_verifies_connector_info_page_is_displayed() {
		hRKManagerPage.getConnectorInfoComponent();
	}
	@Then("HRK Manager verifies {string} section is displayed")
	public void hrk_manager_verifies_section_is_displayed(String string) {
	    hRKManagerPage.getConnectorInfoComponent().getSectionHeader(string).verifyPresent(string);
	}
	@Then("HRK Manager verifies {string} section is exapndable\\/collapsable")
	public void hrk_manager_verifies_section_is_exapndable_collapsable(String string) {
	    
		// hRKManagerPage.getConnectorInfoComponent().getSectionHeader(string).verifyAttribute(attr, value, label)
		
	}
	@Then("HRK Manager verifies {string} is displayed under Additional Information section")
	public void hrk_manager_verifies_is_displayed_under_additional_information_section(String string) {
		hRKManagerPage.getConnectorInfoComponent().getAdditionalInformationComponent().getAddItionalInfoField(string).verifyText(string, string);
		}
	
	@Then("HRK Manager verifies {string} is displayed under Connector Information section")
	public void hrk_manager_verifies_is_displayed_under_connector_information_section(String string) {
		hRKManagerPage.getConnectorInfoComponent().getConnectorInformationComponent().getConnectorInfoField(string).verifyText(string, string);
		}
	
	@Then("HRK Manager click on Delist Connector check box and verifies Delist Date is visible under Additional Information section")
	public void hrk_manager_click_on_delist_connector_check_box_and_verifies_delist_date_is_visible_under_additional_information_section() {
	    
		hRKManagerPage = new HRKManagerPage();
		//AdditionalInformationComponent aic =  hRKManagerPage.getConnectorInfoComponent().getAdditionalInformationComponent();
		if(!hRKManagerPage.getConnectorInfoComponent().getAdditionalInformationComponent().chkboxDelistConnector.isSelected())
			hRKManagerPage.getConnectorInfoComponent().getAdditionalInformationComponent().chkboxDelistConnector.click();
		hRKManagerPage.getConnectorInfoComponent().getAdditionalInformationComponent().getAddItionalInfoField("Delist Date").verifyPresent("Delist Date");
		
	}
	
	@Then("HRK Manager click on Edit button under Additional Information section")
	public void hrk_manager_click_on_edit_button_under_additional_information_section() {
		//WaitUtil.sleep(5000);
		//DriverManager.getDriver().findElement(By.xpath("//i[@title='Edit']")).click();
		hRKManagerPage.getConnectorInfoComponent().getAdditionalInformationComponent().linkEdit.click();
	}
	
	@Then("HRK Manager verifies Model\\/Year list is displayed")
	public void hrk_manager_verifies_model_year_list_is_displayed() {
	  Validator.verifyTrue(hRKManagerPage.getConnectorInfoComponent().gethRKApplicableModelYearComponent().listApplicableModels.size()>0, "Applicable Model/Year List visible");
	  }
	@Then("HRK Manager enter {string} in Keywords field")
	public void hrk_manager_enter_in_field(String string) {
		hRKManagerPage.getConnectorInfoComponent().getAdditionalInformationComponent().txtBoxKeyword.sendKeys(string);
	}
	@Then("HRK Manager clicks on Save button")
	public void hrk_manager_clicks_on_save_button() {
	    
		hRKManagerPage.getConnectorInfoComponent().getAdditionalInformationComponent().btnSave.click();
		
	}
	
	@Then("HRK Manager clicks on Cancel button")
	public void hrk_manager_clicks_on_cancel_button() {
		hRKManagerPage.getConnectorInfoComponent().getAdditionalInformationComponent().btnCancel.click();
		WaitUtil.sleep(2000);
	}
	@Then("HRK Manager verifies keyword value is not saved.")
	public void hrk_manager_verifies_keyword_value_is_not_saved() {
		hRKManagerPage.getConnectorInfoComponent().getAdditionalInformationComponent().txtBoxKeywordReadOnly.verifyNotText("A", "Keywod field");
	}
}
