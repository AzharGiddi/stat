package com.nissan.bdd.fone.stepdefinitions;

import static com.nissan.databeans.TestDataBeanManager.getTestData;

import com.nissan.enums.Roles;
import com.nissan.enums.UserData;
import com.nissan.pages.RAMPage;
import com.nissan.pages.components.FOMMyWorkListComponent;

import io.cucumber.java.en.Then;

public class RAMSteps {

	private RAMPage rAMPage;

	public RAMSteps() {
		rAMPage = new RAMPage();
		//rAMPage.getGoodWillRequestComponent(true);
	}
	
	@Then("Login as RAM")
	public void login_as_ram() {
		rAMPage = new RAMPage();
		rAMPage.login(Roles.RAM, getTestData().getUserData().get(UserData.DEALERCODE));
		rAMPage.waitForPageToLoad();
	}
	
	@Then("Open GW case from Pending RAM Actions")
	public void open_gw_case_from_pending_ram_actions() {
		String gWCaseID = getTestData().getGWCaseID();
		rAMPage.getLeftSideMenuElement("Pending RAM Action").click();
		FOMMyWorkListComponent fOMMyWorkListComponent = new FOMMyWorkListComponent();
		fOMMyWorkListComponent.filterAndClick("Request ID", gWCaseID, gWCaseID);
		
	}
	@Then("Enter RAM Comments as {string}")
	public void enter_ram_comments_as(String string) {
		rAMPage.getGoodWillRequestComponent().tableCostGrid.scrollIntoView();
		rAMPage.getGoodWillRequestComponent().txtboxRAMComments.sendKeys(string);
	}
	@Then("Click on Deny Exception button")
	public void click_on_deny_exception_button() {
		rAMPage.getGoodWillRequestComponent().btnDenyException.scrollAndClick();
	}
	@Then("Click on Approve Exception button")
	public void click_on_approve__button() {
		rAMPage.getGoodWillRequestComponent().btnApproveException.scrollAndClick();
	}
	
	@Then("Click on Conditionally Approve button")
	public void click_on_conditionally_approve_exception_button() {
		rAMPage.getGoodWillRequestComponent().btnConditionallyApprove.scrollAndClick();
	}
	@Then("RAM logs out")
	public void ram_logs_out() {
		rAMPage.logOut();
	}
	

	

}
