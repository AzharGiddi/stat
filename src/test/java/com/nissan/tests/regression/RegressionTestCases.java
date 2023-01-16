package com.nissan.tests.regression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.automation.core.utils.StringMatcher;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.automation.core.utils.dataprovider.TestDataProvider;
import com.nissan.automation.core.utils.listeners.ConfigListener;
import com.nissan.databeans.TSBDataBean;
import com.nissan.enums.VCATCheckbox;
import com.nissan.enums.VCATCheckpoint;
import com.nissan.pages.AdminHomePage;
import com.nissan.pages.DVehicleReferenceDataPage;
import com.nissan.pages.EngManagerPage;
import com.nissan.pages.ServiceAdvisorPage;
import com.nissan.pages.TechnicianPage;
import com.nissan.pages.VCATPage;
import com.nissan.pages.components.AutomaticTransmissionCVTSymptomFormComponent;
import com.nissan.pages.components.AutomaticTransmissionCVTSymptomFormComponent.DropDownName;
import com.nissan.pages.components.CCCComponent;
import com.nissan.pages.components.QuestionsComponent;
import com.nissan.pages.components.VCATAutomaticTransmissionCVTSymtomFormComponent;
import com.nissan.pages.components.VCATAutomaticTransmissionCVTSymtomFormComponent.RadioBtnName;
import com.nissan.pages.components.VCATSupportComponent;
import com.nissan.tests.BaseTest;
import com.nissan.utils.ROUtil;
import com.nissan.utils.TSBUtil;

@Listeners(ConfigListener.class)
public class RegressionTestCases extends BaseTest {



	@Test(description = "No TSB, case gets submitted at technician and summary screen is displayed.", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test01(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		// creating RO
		String rONumber = ROUtil.createRO(adminHomePage, data);
		// Fetching vehicle ref details
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());

		AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
				false);
		List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
		Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Customer Symptom DropDowns"));
		List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
		List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
		Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Technician Symptom DropDowns"));
		List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerDropDownValues);
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianDropDownValues);

		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		// Precondition and fetching test data.

		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
				"Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Customer OtherSymptoms"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getDropDownWebElement(DropDownName.PAYMENTASSUMPTION)
				.select(data.get("Payment Assumption"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Technician OtherSymptoms"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 0, "Expected: 0 tsb Actual: " + tsbListSize + " tsb(s) matched");

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Modification");
		ccc.dropdownRecommendRepair.select("Vibration-checked cvt");
		ccc.textboxRepairJustification.clearAndSendKeys("Repair done");
		ccc.btnSubmit.scrollAndClick();

		// PCC Questions
		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		int questionIndex = 0;
		questionsComponent.respondToYesNoQuestion("No", ++questionIndex);
		questionsComponent.respondToInputResponseQuestion("Test", ++questionIndex);
		questionsComponent.respondToAttachmentQuestion(++questionIndex);
		questionsComponent.respondToInputResponseQuestion("Yes", ++questionIndex);
		questionsComponent.respondToInputResponseQuestion("2021-12-15", ++questionIndex);
		WaitUtil.sleep(5000);
		questionsComponent.btnSubmit.scrollIntoView();
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"), "No further questions");
		questionsComponent.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']").verifyText("Diagnostic Case Summary", "Summary Screen Header");
		Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");
	}
	
	@Test(description = "No TSB/ESM-Warranty", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test02(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		// creating RO
		String rONumber = ROUtil.createRO(adminHomePage, data);
		// Fetching vehicle ref details
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());
		AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
				false);
		List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
		Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Customer Symptom DropDowns"));
		List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent
				.getDropDownValues(customerDropDownMap);
		List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
		Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Technician Symptom DropDowns"));
		List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent
				.getDropDownValues(technicianDropDownMap);
		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerDropDownValues);
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianDropDownValues);
		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 0, "Expected: 0 tsb Actual: " + tsbListSize + " tsb(s) matched");
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		// Precondition and fetching test data.

		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader
				.verifyText("Please select the Dealership", "Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent()
				.getHeaderWebElement("Case ID").getText();

		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Customer OtherSymptoms"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent()
				.getDropDownWebElement(DropDownName.PAYMENTASSUMPTION).select(data.get("Payment Assumption"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou.verifyText(
				"Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent()
				.selectCheckBoxes(technicianSympCheckboxes);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent()
				.selectDropDownValue(technicianDropDownMap);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Technician OtherSymptoms"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm
				.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();



		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Modification");
		ccc.dropdownRecommendRepair.select("Vibration-checked cvt");
		ccc.textboxRepairJustification.clearAndSendKeys("Repair done");
		ccc.btnSubmit.scrollAndClick();

		// PCC Questions
		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		int questionIndex = 0;
		questionsComponent.respondToYesNoQuestion("No", ++questionIndex);
		questionsComponent.respondToInputResponseQuestion("Test", ++questionIndex);
		questionsComponent.respondToAttachmentQuestion(++questionIndex);
		questionsComponent.respondToInputResponseQuestion("Yes", ++questionIndex);
		questionsComponent.respondToInputResponseQuestion("2021-12-15", ++questionIndex);
		WaitUtil.sleep(5000);
		questionsComponent.btnSubmit.scrollIntoView();
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"),
				"No further questions");
		questionsComponent.btnSubmit.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtThankYou.verifyText(
				"Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		technicianPage.logOut();

		Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");
		VCATPage vCATPage = new VCATPage("pccf1analyst", "rules");
		vCATPage.waitForPageToLoad();
		vCATPage.getLeftSideMenuElement("VCAT Support").click();
		VCATSupportComponent vCATSupportComponent = new VCATSupportComponent();
		vCATSupportComponent.filterAndClick("Case ID", dCaseID, dCaseID);
		// verify checkboxes
		VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent = new VCATAutomaticTransmissionCVTSymtomFormComponent();
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PAYMNTASSMPTNCHNGDNWTOW)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW) ? "true" : "",
						"Payment assumption has been updated from Non Warranty to Warranty");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.TECHDISAGRDSYSRECMNDREPAIR)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR) ? "true" : "",
						"Technician Disagreed with System Recommended Repair");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ARCDEALER)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ARCDEALER).toString(), "ARC Dealer");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ENGINEERINGREVIEW)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ENGINEERINGREVIEW).toString(),
						"Engineering Review");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MANUFACTURINGDATE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MANUFACTURINGDATE).toString(),
						"Manufacturing date");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MILEAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MILEAGE).toString(), "Mileage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MONTHSINSERVICE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MONTHSINSERVICE).toString(),
						"Months in service");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.VIN).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.VIN).toString(), "VIN");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.DEALERSPECIFIC)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.DEALERSPECIFIC).toString(), "Dealer Specific");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ADDITIONALRULES)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ADDITIONALRULES).toString(), "Additional Rules");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.INSERVICEDATEUNAVAILABLE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.INSERVICEDATEUNAVAILABLE).toString(),
						"In-service date unavailable");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.SERVICECONTRACTCOVERAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.SERVICECONTRACTCOVERAGE).toString(),
						"Service Contract Coverage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PARTSWARRANTYCOVERAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PARTSWARRANTYCOVERAGE).toString(),
						"Parts Warranty Coverage");

		vCATAutomaticTransmissionCVTSymtomFormComponent.getRadioButton(RadioBtnName.SYSTEMRECOMMENDATIONRIGHT, "Yes")
				.click();
		vCATAutomaticTransmissionCVTSymtomFormComponent
				.getRadioButton(RadioBtnName.AGREETECHNICIANRECOMMENDATION, "Yes").click();
		WaitUtil.sleep(2000);
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtboxVCATSupportNotes.clearAndSendKeys("VCAT support");
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtPaymentType.verifyText(data.get("Payment Assumption"),
				"Payment Assumption");
		vCATAutomaticTransmissionCVTSymtomFormComponent.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']")
				.verifyText("Diagnostic Case Summary", "Summary Screen Header");
		vCATPage.logOut();
	}
	
	@Test(description = "Multiple TSB/ESM, Parts Warranty, case gets submitted at technician and summary screen is displayed.", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test03(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		// creating RO
		String rONumber = ROUtil.createRO(adminHomePage, data);
		// Fetching vehicle ref details
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());

		AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
				false);
		List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
		Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Customer Symptom DropDowns"));
		List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
		List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
		Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Technician Symptom DropDowns"));
		List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerDropDownValues);
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianDropDownValues);

		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize>1, "Expected: Multiple tsbs Actual: " + tsbListSize + " tsb(s) matched");
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		// Precondition and fetching test data.

		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
				"Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Customer OtherSymptoms"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getDropDownWebElement(DropDownName.PAYMENTASSUMPTION)
				.select(data.get("Payment Assumption"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Technician OtherSymptoms"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

		
		

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Modification");
		ccc.dropdownRecommendRepair.select("Vibration-checked cvt");
		ccc.textboxRepairJustification.clearAndSendKeys("Repair done");
		ccc.btnSubmit.scrollAndClick();

		// PCC Questions
		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		int questionIndex = 0;
		questionsComponent.respondToYesNoQuestion("No", ++questionIndex);
		questionsComponent.respondToInputResponseQuestion("Test", ++questionIndex);
		questionsComponent.respondToAttachmentQuestion(++questionIndex);
		questionsComponent.respondToInputResponseQuestion("Yes", ++questionIndex);
		questionsComponent.respondToInputResponseQuestion("2021-12-15", ++questionIndex);
		WaitUtil.sleep(5000);
		questionsComponent.btnSubmit.scrollIntoView();
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"), "No further questions");
		questionsComponent.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']").verifyText("Diagnostic Case Summary", "Summary Screen Header");
		Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");
	}
	
	@Test(description = "Multiple TSB/ESM, Warranty, case gets submitted at VCAT and summary screen is displayed.", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test04(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		// creating RO
		String rONumber = ROUtil.createRO(adminHomePage, data);
		// Fetching vehicle ref details
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());

		AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
				false);
		List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
		Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Customer Symptom DropDowns"));
		List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent
				.getDropDownValues(customerDropDownMap);
		List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
		Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Technician Symptom DropDowns"));
		List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent
				.getDropDownValues(technicianDropDownMap);

		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerDropDownValues);
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianDropDownValues);

		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize>1, "Expected: Multiple tsbs Actual: " + tsbListSize + " tsb(s) matched");
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		// Precondition and fetching test data.

		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader
				.verifyText("Please select the Dealership", "Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent()
				.getHeaderWebElement("Case ID").getText();

		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Customer OtherSymptoms"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent()
				.getDropDownWebElement(DropDownName.PAYMENTASSUMPTION).select(data.get("Payment Assumption"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou.verifyText(
				"Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent()
				.selectCheckBoxes(technicianSympCheckboxes);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent()
				.selectDropDownValue(technicianDropDownMap);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Technician OtherSymptoms"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm
				.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Modification");
		ccc.dropdownRecommendRepair.select("Vibration-checked cvt");
		ccc.textboxRepairJustification.clearAndSendKeys("Repair done");
		ccc.btnSubmit.scrollAndClick();

		// PCC Questions
		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		int questionIndex = 0;
		questionsComponent.respondToYesNoQuestion("No", ++questionIndex);
		questionsComponent.respondToInputResponseQuestion("Test", ++questionIndex);
		questionsComponent.respondToAttachmentQuestion(++questionIndex);
		questionsComponent.respondToInputResponseQuestion("Yes", ++questionIndex);
		questionsComponent.respondToInputResponseQuestion("2021-12-15", ++questionIndex);
		WaitUtil.sleep(5000);
		questionsComponent.btnSubmit.scrollIntoView();
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"),
				"No further questions");
		questionsComponent.btnSubmit.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtThankYou.verifyText(
				"Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		technicianPage.logOut();

		Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");
		VCATPage vCATPage = new VCATPage("pccf1analyst", "rules");
		vCATPage.waitForPageToLoad();
		vCATPage.getLeftSideMenuElement("VCAT Support").click();
		VCATSupportComponent vCATSupportComponent = new VCATSupportComponent();
		vCATSupportComponent.filterAndClick("Case ID", dCaseID, dCaseID);
		// verify checkboxes
		VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent = new VCATAutomaticTransmissionCVTSymtomFormComponent();
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PAYMNTASSMPTNCHNGDNWTOW)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW) ? "true" : "",
						"Payment assumption has been updated from Non Warranty to Warranty");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.TECHDISAGRDSYSRECMNDREPAIR)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR) ? "true" : "",
						"Technician Disagreed with System Recommended Repair");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ARCDEALER)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ARCDEALER).toString(), "ARC Dealer");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ENGINEERINGREVIEW)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ENGINEERINGREVIEW).toString(),
						"Engineering Review");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MANUFACTURINGDATE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MANUFACTURINGDATE).toString(),
						"Manufacturing date");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MILEAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MILEAGE).toString(), "Mileage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MONTHSINSERVICE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MONTHSINSERVICE).toString(),
						"Months in service");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.VIN).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.VIN).toString(), "VIN");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.DEALERSPECIFIC)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.DEALERSPECIFIC).toString(), "Dealer Specific");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ADDITIONALRULES)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ADDITIONALRULES).toString(), "Additional Rules");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.INSERVICEDATEUNAVAILABLE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.INSERVICEDATEUNAVAILABLE).toString(),
						"In-service date unavailable");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.SERVICECONTRACTCOVERAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.SERVICECONTRACTCOVERAGE).toString(),
						"Service Contract Coverage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PARTSWARRANTYCOVERAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PARTSWARRANTYCOVERAGE).toString(),
						"Parts Warranty Coverage");

		vCATAutomaticTransmissionCVTSymtomFormComponent.getRadioButton(RadioBtnName.SYSTEMRECOMMENDATIONRIGHT, "Yes")
				.click();
		vCATAutomaticTransmissionCVTSymtomFormComponent
				.getRadioButton(RadioBtnName.AGREETECHNICIANRECOMMENDATION, "Yes").click();
		WaitUtil.sleep(2000);
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtboxVCATSupportNotes.clearAndSendKeys("VCAT support");
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtPaymentType.verifyText(data.get("Payment Assumption"),
				"Payment Assumption");
		vCATAutomaticTransmissionCVTSymtomFormComponent.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']")
				.verifyText("Diagnostic Case Summary", "Summary Screen Header");
		vCATPage.logOut();
	}
	
	@Test(description = "1 TSB ,No ESM with QA and No repair - Customer Pay, case gets submitted at technician and summary screen is displayed.", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test05(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		// creating RO
		String rONumber = ROUtil.createRO(adminHomePage, data);
		// Fetching vehicle ref details
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());

		AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
				false);
		List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
		Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Customer Symptom DropDowns"));
		List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
		List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
		Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Technician Symptom DropDowns"));
		List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerDropDownValues);
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianDropDownValues);

		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		// Precondition and fetching test data.

		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
				"Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Customer OtherSymptoms"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getDropDownWebElement(DropDownName.PAYMENTASSUMPTION)
				.select(data.get("Payment Assumption"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Technician OtherSymptoms"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 1, "Expected: 1 tsb Actual: " + tsbListSize + " tsb(s) matched");

		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		questionsComponent.respondToYesNoQuestion("Yes", 1);
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"), "No further questions");
		questionsComponent.btnSubmit.scrollAndClick();

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Modification");
		ccc.dropdownRecommendRepair.select("Vibration-checked cvt");
		ccc.textboxRepairJustification.clearAndSendKeys("Repair done");
		ccc.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']").verifyText("Diagnostic Case Summary", "Summary Screen Header");
		Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");
	}
	
	@Test(description = "1 TSB with QA - Goodwill, repair not selected in questions, case gets submitted at VCAT and summary screen is displayed.", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test06(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		// creating RO
		String rONumber = ROUtil.createRO(adminHomePage, data);
		// Fetching vehicle ref details
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());

		AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
				false);
		List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
		Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Customer Symptom DropDowns"));
		List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
		List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
		Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Technician Symptom DropDowns"));
		List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerDropDownValues);
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianDropDownValues);

		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		// Precondition and fetching test data.

		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
				"Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Customer OtherSymptoms"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getDropDownWebElement(DropDownName.PAYMENTASSUMPTION)
				.select(data.get("Payment Assumption"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Technician OtherSymptoms"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 1, "Expected: 1 tsb Actual: " + tsbListSize + " tsb(s) matched");

		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		questionsComponent.respondToYesNoQuestion("Yes", 1);
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"), "No further questions");
		questionsComponent.btnSubmit.scrollAndClick();

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Modification");
		ccc.dropdownRecommendRepair.select("Vibration-checked cvt");
		ccc.textboxRepairJustification.clearAndSendKeys("Repair done");
		ccc.btnSubmit.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtThankYou.verifyText(
				"Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		technicianPage.logOut();

		Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");
		VCATPage vCATPage = new VCATPage("pccf1analyst", "rules");
		vCATPage.waitForPageToLoad();
		vCATPage.getLeftSideMenuElement("VCAT Support").click();
		VCATSupportComponent vCATSupportComponent = new VCATSupportComponent();
		vCATSupportComponent.filterAndClick("Case ID", dCaseID, dCaseID);
		// verify checkboxes
		VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent = new VCATAutomaticTransmissionCVTSymtomFormComponent();
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PAYMNTASSMPTNCHNGDNWTOW)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW) ? "true" : "",
						"Payment assumption has been updated from Non Warranty to Warranty");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.TECHDISAGRDSYSRECMNDREPAIR)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR) ? "true" : "",
						"Technician Disagreed with System Recommended Repair");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ARCDEALER)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ARCDEALER).toString(), "ARC Dealer");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ENGINEERINGREVIEW)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ENGINEERINGREVIEW).toString(),
						"Engineering Review");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MANUFACTURINGDATE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MANUFACTURINGDATE).toString(),
						"Manufacturing date");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MILEAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MILEAGE).toString(), "Mileage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MONTHSINSERVICE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MONTHSINSERVICE).toString(),
						"Months in service");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.VIN).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.VIN).toString(), "VIN");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.DEALERSPECIFIC)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.DEALERSPECIFIC).toString(), "Dealer Specific");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ADDITIONALRULES)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ADDITIONALRULES).toString(), "Additional Rules");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.INSERVICEDATEUNAVAILABLE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.INSERVICEDATEUNAVAILABLE).toString(),
						"In-service date unavailable");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.SERVICECONTRACTCOVERAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.SERVICECONTRACTCOVERAGE).toString(),
						"Service Contract Coverage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PARTSWARRANTYCOVERAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PARTSWARRANTYCOVERAGE).toString(),
						"Parts Warranty Coverage");

		vCATAutomaticTransmissionCVTSymtomFormComponent.getRadioButton(RadioBtnName.SYSTEMRECOMMENDATIONRIGHT, "Yes")
				.click();
		vCATAutomaticTransmissionCVTSymtomFormComponent
				.getRadioButton(RadioBtnName.AGREETECHNICIANRECOMMENDATION, "Yes").click();
		WaitUtil.sleep(2000);
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtboxVCATSupportNotes.clearAndSendKeys("VCAT support");
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtPaymentType.verifyText(data.get("Payment Assumption"),
				"Payment Assumption");
		vCATAutomaticTransmissionCVTSymtomFormComponent.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']")
				.verifyText("Diagnostic Case Summary", "Summary Screen Header");
		vCATPage.logOut();
		
	}	
	
	@Test(description = "1 TSB with QA Refer to PCC- Customer Pay, case gets submitted at technician and summary screen is displayed.", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test07(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		// creating RO
		String rONumber = ROUtil.createRO(adminHomePage, data);
		// Fetching vehicle ref details
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());
		AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
				false);
		List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
		Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Customer Symptom DropDowns"));
		List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
		List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
		Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Technician Symptom DropDowns"));
		List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerDropDownValues);
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianDropDownValues);

		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		// Precondition and fetching test data.

		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
				"Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Customer OtherSymptoms"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getDropDownWebElement(DropDownName.PAYMENTASSUMPTION)
				.select(data.get("Payment Assumption"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Technician OtherSymptoms"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 1, "Expected: 1 tsb Actual: " + tsbListSize + " tsb(s) matched");

		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		questionsComponent.respondToYesNoQuestion("No", 1);
		questionsComponent.respondToInputResponseQuestion("Input", 2);
		WaitUtil.sleep(2000);
		questionsComponent.txtNoUnableToDiagnose.scrollIntoView();
		questionsComponent.txtNoUnableToDiagnose.verifyText(StringMatcher.startsWith("Unable to"), "Unable to diagnose");
		questionsComponent.btnSubmit.scrollAndClick();

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Modification");
		ccc.dropdownRecommendRepair.select("Vibration-checked cvt");
		ccc.textboxRepairJustification.clearAndSendKeys("Repair done");
		ccc.btnSubmit.scrollAndClick();
		
		QuestionsComponent pccQuestions = new QuestionsComponent(evaluatedTSB);
		pccQuestions.waitForComponentToLoad();
		int questionIndex = 0;
		pccQuestions.respondToYesNoQuestion("No", ++questionIndex);
		pccQuestions.respondToInputResponseQuestion("Test", ++questionIndex);
		pccQuestions.respondToAttachmentQuestion(++questionIndex);
		pccQuestions.respondToInputResponseQuestion("Yes", ++questionIndex);
		pccQuestions.respondToInputResponseQuestion("2021-12-15", ++questionIndex);
		WaitUtil.sleep(5000);
		pccQuestions.btnSubmit.scrollIntoView();
		pccQuestions.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"), "No further questions");
		pccQuestions.btnSubmit.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtThankYou.verifyText(
				"Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		technicianPage.logOut();

		Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");
		VCATPage vCATPage = new VCATPage("pccf1analyst", "rules");
		vCATPage.waitForPageToLoad();
		vCATPage.getLeftSideMenuElement("VCAT Support").click();
		VCATSupportComponent vCATSupportComponent = new VCATSupportComponent();
		vCATSupportComponent.filterAndClick("Case ID", dCaseID, dCaseID);
		// verify checkboxes
		VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent = new VCATAutomaticTransmissionCVTSymtomFormComponent();
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PAYMNTASSMPTNCHNGDNWTOW)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW) ? "true" : "",
						"Payment assumption has been updated from Non Warranty to Warranty");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.TECHDISAGRDSYSRECMNDREPAIR)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR) ? "true" : "",
						"Technician Disagreed with System Recommended Repair");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ARCDEALER)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ARCDEALER).toString(), "ARC Dealer");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ENGINEERINGREVIEW)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ENGINEERINGREVIEW).toString(),
						"Engineering Review");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MANUFACTURINGDATE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MANUFACTURINGDATE).toString(),
						"Manufacturing date");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MILEAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MILEAGE).toString(), "Mileage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MONTHSINSERVICE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MONTHSINSERVICE).toString(),
						"Months in service");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.VIN).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.VIN).toString(), "VIN");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.DEALERSPECIFIC)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.DEALERSPECIFIC).toString(), "Dealer Specific");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ADDITIONALRULES)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ADDITIONALRULES).toString(), "Additional Rules");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.INSERVICEDATEUNAVAILABLE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.INSERVICEDATEUNAVAILABLE).toString(),
						"In-service date unavailable");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.SERVICECONTRACTCOVERAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.SERVICECONTRACTCOVERAGE).toString(),
						"Service Contract Coverage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PARTSWARRANTYCOVERAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PARTSWARRANTYCOVERAGE).toString(),
						"Parts Warranty Coverage");

		vCATAutomaticTransmissionCVTSymtomFormComponent.getRadioButton(RadioBtnName.SYSTEMRECOMMENDATIONRIGHT, "Yes")
				.click();
		vCATAutomaticTransmissionCVTSymtomFormComponent
				.getRadioButton(RadioBtnName.AGREETECHNICIANRECOMMENDATION, "Yes").click();
		WaitUtil.sleep(2000);
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtboxVCATSupportNotes.clearAndSendKeys("VCAT support");
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtPaymentType.verifyText(data.get("Payment Assumption"),
				"Payment Assumption");
		vCATAutomaticTransmissionCVTSymtomFormComponent.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']")
				.verifyText("Diagnostic Case Summary", "Summary Screen Header");
		vCATPage.logOut();
		
	}

	@Test(description = "1 TSB with QA have repair - Warranty, case gets submitted at technician and summary screen is displayed.", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test08(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		// creating RO
		String rONumber = ROUtil.createRO(adminHomePage, data);
		// Fetching vehicle ref details
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());

		AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
				false);
		List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
		Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Customer Symptom DropDowns"));
		List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
		List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
		Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Technician Symptom DropDowns"));
		List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerDropDownValues);
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianDropDownValues);

		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		// Precondition and fetching test data.

		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
				"Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Customer OtherSymptoms"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getDropDownWebElement(DropDownName.PAYMENTASSUMPTION)
				.select(data.get("Payment Assumption"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Technician OtherSymptoms"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 1, "Expected: 1 tsb Actual: " + tsbListSize + " tsb(s) matched");

		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		questionsComponent.respondToYesNoQuestion("No", 1);
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"), "No further questions");
		questionsComponent.btnSubmit.scrollAndClick();

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Modification");
		ccc.txtDefaultRepair.verifyText(evaluatedTSB.getDefaultRepair(), "Repair Recommendation");
		naaRulesMap.put(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR, ccc.chkboxTechnicianDisagrees.isSelected());
		ccc.btnSubmit.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtThankYou.verifyText(
				"Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		technicianPage.logOut();

		Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");
		VCATPage vCATPage = new VCATPage("pccf1analyst", "rules");
		vCATPage.waitForPageToLoad();
		vCATPage.getLeftSideMenuElement("VCAT Support").click();
		VCATSupportComponent vCATSupportComponent = new VCATSupportComponent();
		vCATSupportComponent.filterAndClick("Case ID", dCaseID, dCaseID);
		// verify checkboxes
		VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent = new VCATAutomaticTransmissionCVTSymtomFormComponent();
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PAYMNTASSMPTNCHNGDNWTOW)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW) ? "true" : "",
						"Payment assumption has been updated from Non Warranty to Warranty");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.TECHDISAGRDSYSRECMNDREPAIR)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR).toString(),
						"Technician Disagreed with System Recommended Repair");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ARCDEALER)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ARCDEALER).toString(), "ARC Dealer");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ENGINEERINGREVIEW)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ENGINEERINGREVIEW).toString(),
						"Engineering Review");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MANUFACTURINGDATE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MANUFACTURINGDATE).toString(),
						"Manufacturing date");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MILEAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MILEAGE).toString(), "Mileage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MONTHSINSERVICE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MONTHSINSERVICE).toString(),
						"Months in service");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.VIN).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.VIN).toString(), "VIN");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.DEALERSPECIFIC)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.DEALERSPECIFIC).toString(), "Dealer Specific");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ADDITIONALRULES)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ADDITIONALRULES).toString(), "Additional Rules");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.INSERVICEDATEUNAVAILABLE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.INSERVICEDATEUNAVAILABLE).toString(),
						"In-service date unavailable");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.SERVICECONTRACTCOVERAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.SERVICECONTRACTCOVERAGE).toString(),
						"Service Contract Coverage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PARTSWARRANTYCOVERAGE)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PARTSWARRANTYCOVERAGE).toString(),
						"Parts Warranty Coverage");

		vCATAutomaticTransmissionCVTSymtomFormComponent.getRadioButton(RadioBtnName.SYSTEMRECOMMENDATIONRIGHT, "Yes")
				.click();
		vCATAutomaticTransmissionCVTSymtomFormComponent
				.getRadioButton(RadioBtnName.OVERRIDESYSTEMRECOMMENDATION, "No").click();
		WaitUtil.sleep(2000);
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtboxVCATSupportNotes.clearAndSendKeys("VCAT support");
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtPaymentType.verifyText(data.get("Payment Assumption"),
				"Payment Assumption");
		vCATAutomaticTransmissionCVTSymtomFormComponent.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']")
				.verifyText("Diagnostic Case Summary", "Summary Screen Header");
		vCATPage.logOut();
		
	}
	
	@Test(description = "1 TSB with repair - Customer Pay, technician disagrees checkbox not checked, case gets submitted at technician and summary screen is displayed.", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test09(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		// creating RO
		String rONumber = ROUtil.createRO(adminHomePage, data);
		// Fetching vehicle ref details
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());

		AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
				false);
		List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
		Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Customer Symptom DropDowns"));
		List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
		List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
		Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Technician Symptom DropDowns"));
		List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerDropDownValues);
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianDropDownValues);

		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		// Precondition and fetching test data.

		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
				"Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Customer OtherSymptoms"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getDropDownWebElement(DropDownName.PAYMENTASSUMPTION)
				.select(data.get("Payment Assumption"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Technician OtherSymptoms"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 1, "Expected: 1 tsb Actual: " + tsbListSize + " tsb(s) matched");

		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		questionsComponent.respondToYesNoQuestion("No", 1);
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"), "No further questions");
		questionsComponent.btnSubmit.scrollAndClick();

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Modification");
		ccc.txtDefaultRepair.verifyText(evaluatedTSB.getDefaultRepair(), "Repair Recommendation");
		naaRulesMap.put(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR, ccc.chkboxTechnicianDisagrees.isSelected());
		ccc.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']").verifyText("Diagnostic Case Summary", "Summary Screen Header");
		Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT) || naaRulesMap.get(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR),
				"Expected false , Actual true");
	}
	
	@Test(description = "1 TSB with QA have repair - Warranty, case gets submitted at technician and summary screen is displayed.", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test10(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		// creating RO
		String rONumber = ROUtil.createRO(adminHomePage, data);
		// Fetching vehicle ref details
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());

		AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
				false);
		List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
		Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Customer Symptom DropDowns"));
		List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
		List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
		Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Technician Symptom DropDowns"));
		List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerDropDownValues);
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianDropDownValues);

		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		// Precondition and fetching test data.

		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
				"Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Customer OtherSymptoms"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getDropDownWebElement(DropDownName.PAYMENTASSUMPTION)
				.select(data.get("Payment Assumption"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Technician OtherSymptoms"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 1, "Expected: 1 tsb Actual: " + tsbListSize + " tsb(s) matched");

		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		questionsComponent.respondToYesNoQuestion("No", 1);
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"), "No further questions");
		questionsComponent.btnSubmit.scrollAndClick();

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Defect in factory workmanship");
		ccc.txtDefaultRepair.verifyText(evaluatedTSB.getDefaultRepair(), "Repair Recommendation");
		naaRulesMap.put(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR, ccc.chkboxTechnicianDisagrees.isSelected());
		ccc.btnSubmit.scrollAndClick();
		Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']")
		.verifyText("Diagnostic Case Summary", "Summary Screen Header");
		technicianPage.logOut();
	}
	
	@Test(description="1TSB ,No ESM without QA - default repair - Warranty", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test11(Map<String, String> data) {
			// Precondition and fetching test data.
				getDriver().manage().deleteAllCookies();
				AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
				adminHomePage.waitForPageToLoad();
				// creating RO
				String rONumber = ROUtil.createRO(adminHomePage, data);
				// Fetching vehicle ref details
				DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
				adminHomePage.logOut();
				data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());
				AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
						false);
				List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
				Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
						.getDropDownMap(data.get("Customer Symptom DropDowns"));
				List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
				List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
						.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
				Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
						.getDropDownMap(data.get("Technician Symptom DropDowns"));
				List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

				List<String> customerSymptomsSet = new ArrayList<>();
				customerSymptomsSet.addAll(customerSympCheckboxes);
				customerSymptomsSet.addAll(customerDropDownValues);
				List<String> technicianSymptomsSet = new ArrayList<>();
				technicianSymptomsSet.addAll(technicianSympCheckboxes);
				technicianSymptomsSet.addAll(technicianDropDownValues);

				EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
				TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
				int tsbListSize = evaluatedTSB.getListTSB().size();
				Assert.assertTrue(tsbListSize == 1, "Expected: 1 tsb Actual: " + tsbListSize + " tsb(s) matched");
				Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
				engManagerPage.logOut();
				
				// Precondition and fetching test data.
				ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
				serviceAdvisorPage.waitForPageToLoad();
				serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
						"Dashboard header");
				serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
				serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
				serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
				serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
				String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
				serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
				serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
				serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
				serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
						.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
				serviceAdvisorPage.logOut();

				TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
				technicianPage.waitForPageToLoad();
				technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
				technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

				CCCComponent ccc = new CCCComponent();
				ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
				ccc.dropdownWhyDoYouThinkPartFailed.scrollIntoView();
				ccc.dropdownWhyDoYouThinkPartFailed.select("Modification");
				ccc.txtDefaultRepair.verifyText(evaluatedTSB.getDefaultRepair(), "Repair Recommendation");
				ccc.btnSubmit.scrollAndClick();
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtThankYou.verifyText(
						"Thank you! The next step in this case has been routed appropriately.", "Thank You message");
				technicianPage.logOut();

				Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");
				VCATPage vCATPage = new VCATPage("pccf1analyst", "rules");
				vCATPage.waitForPageToLoad();
				vCATPage.getLeftSideMenuElement("VCAT Support").click();
				VCATSupportComponent vCATSupportComponent = new VCATSupportComponent();
				vCATSupportComponent.filterAndClick("Case ID", dCaseID, dCaseID);
				// verify checkboxes
				VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent = new VCATAutomaticTransmissionCVTSymtomFormComponent();
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PAYMNTASSMPTNCHNGDNWTOW)
						.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW) ? "true" : "",
								"Payment assumption has been updated from Non Warranty to Warranty");
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.TECHDISAGRDSYSRECMNDREPAIR)
						.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR).toString(),
								"Technician Disagreed with System Recommended Repair");
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ARCDEALER)
						.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ARCDEALER).toString(), "ARC Dealer");
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ENGINEERINGREVIEW)
						.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ENGINEERINGREVIEW).toString(),
								"Engineering Review");
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MANUFACTURINGDATE)
						.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MANUFACTURINGDATE).toString(),
								"Manufacturing date");
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MILEAGE)
						.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MILEAGE).toString(), "Mileage");
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MONTHSINSERVICE)
						.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.MONTHSINSERVICE).toString(),
								"Months in service");
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.VIN).verifyAttribute("alt",
						naaRulesMap.get(VCATCheckpoint.VIN).toString(), "VIN");
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.DEALERSPECIFIC)
						.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.DEALERSPECIFIC).toString(), "Dealer Specific");
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ADDITIONALRULES)
						.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.ADDITIONALRULES).toString(), "Additional Rules");
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.INSERVICEDATEUNAVAILABLE)
						.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.INSERVICEDATEUNAVAILABLE).toString(),
								"In-service date unavailable");
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.SERVICECONTRACTCOVERAGE)
						.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.SERVICECONTRACTCOVERAGE).toString(),
								"Service Contract Coverage");
				vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PARTSWARRANTYCOVERAGE)
						.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PARTSWARRANTYCOVERAGE).toString(),
								"Parts Warranty Coverage");

				vCATAutomaticTransmissionCVTSymtomFormComponent.getRadioButton(RadioBtnName.SYSTEMRECOMMENDATIONRIGHT, "Yes")
						.click();
				vCATAutomaticTransmissionCVTSymtomFormComponent
						.getRadioButton(RadioBtnName.OVERRIDESYSTEMRECOMMENDATION, "No").click();
				WaitUtil.sleep(2000);
				vCATAutomaticTransmissionCVTSymtomFormComponent.txtboxVCATSupportNotes.clearAndSendKeys("VCAT support");
				vCATAutomaticTransmissionCVTSymtomFormComponent.txtPaymentType.verifyText(data.get("Payment Assumption"),
						"Payment Assumption");
				vCATAutomaticTransmissionCVTSymtomFormComponent.btnSubmit.scrollAndClick();
				ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']")
						.verifyText("Diagnostic Case Summary", "Summary Screen Header");
				vCATPage.logOut();

	}
	
	@Test(description="1TSB ,No ESM without QA - default repair- Customer Pay,case gets submitted at technician and summary screen is displayed.", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test12(Map<String, String> data) {
			// Precondition and fetching test data.
				getDriver().manage().deleteAllCookies();
				AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
				adminHomePage.waitForPageToLoad();
				// creating RO
				String rONumber = ROUtil.createRO(adminHomePage, data);
				// Fetching vehicle ref details
				DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
				adminHomePage.logOut();
				data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());
				AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
						false);
				List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
				Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
						.getDropDownMap(data.get("Customer Symptom DropDowns"));
				List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
				List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
						.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
				Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
						.getDropDownMap(data.get("Technician Symptom DropDowns"));
				List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

				List<String> customerSymptomsSet = new ArrayList<>();
				customerSymptomsSet.addAll(customerSympCheckboxes);
				customerSymptomsSet.addAll(customerDropDownValues);
				List<String> technicianSymptomsSet = new ArrayList<>();
				technicianSymptomsSet.addAll(technicianSympCheckboxes);
				technicianSymptomsSet.addAll(technicianDropDownValues);

				EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
				TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
				int tsbListSize = evaluatedTSB.getListTSB().size();
				Assert.assertTrue(tsbListSize == 1, "Expected: 1 tsb Actual: " + tsbListSize + " tsb(s) matched");
				Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
				engManagerPage.logOut();
				
				// Precondition and fetching test data.
				ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
				serviceAdvisorPage.waitForPageToLoad();
				serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
						"Dashboard header");
				serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
				serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
				serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
				serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
				String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
				serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
				serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
				serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
				serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
						.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
				serviceAdvisorPage.logOut();

				TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
				technicianPage.waitForPageToLoad();
				technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
				technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

				CCCComponent ccc = new CCCComponent();
				ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
				ccc.dropdownWhyDoYouThinkPartFailed.scrollIntoView();
				ccc.dropdownWhyDoYouThinkPartFailed.select("Defect in factory materials");
				ccc.txtDefaultRepair.verifyText(evaluatedTSB.getDefaultRepair(), "Repair Recommendation");
				ccc.btnSubmit.scrollAndClick();
				ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']").verifyText("Diagnostic Case Summary", "Summary Screen Header");
				technicianPage.logOut();

				Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");

	}
	
	@Test(description="1TSB ,No ESM without QA - default repair - Warranty", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test13(Map<String, String> data) {
			// Precondition and fetching test data.
				getDriver().manage().deleteAllCookies();
				AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
				adminHomePage.waitForPageToLoad();
				// creating RO
				String rONumber = ROUtil.createRO(adminHomePage, data);
				// Fetching vehicle ref details
				DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
				adminHomePage.logOut();
				data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());
				AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
						false);
				List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
				Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
						.getDropDownMap(data.get("Customer Symptom DropDowns"));
				List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
				List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
						.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
				Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
						.getDropDownMap(data.get("Technician Symptom DropDowns"));
				List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

				List<String> customerSymptomsSet = new ArrayList<>();
				customerSymptomsSet.addAll(customerSympCheckboxes);
				customerSymptomsSet.addAll(customerDropDownValues);
				List<String> technicianSymptomsSet = new ArrayList<>();
				technicianSymptomsSet.addAll(technicianSympCheckboxes);
				technicianSymptomsSet.addAll(technicianDropDownValues);

				EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
				TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
				int tsbListSize = evaluatedTSB.getListTSB().size();
				Assert.assertTrue(tsbListSize == 1, "Expected: 1 tsb Actual: " + tsbListSize + " tsb(s) matched");
				Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
				engManagerPage.logOut();
				
				// Precondition and fetching test data.
				ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
				serviceAdvisorPage.waitForPageToLoad();
				serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
						"Dashboard header");
				serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
				serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
				serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
				serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
				String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
				serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
				serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
				serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
				serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
						.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
				serviceAdvisorPage.logOut();

				TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
				technicianPage.waitForPageToLoad();
				technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
				technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
				technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

				CCCComponent ccc = new CCCComponent();
				ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
				ccc.dropdownWhyDoYouThinkPartFailed.scrollIntoView();
				ccc.dropdownWhyDoYouThinkPartFailed.select("Defect in factory materials");
				ccc.txtDefaultRepair.verifyText(evaluatedTSB.getDefaultRepair(), "Repair Recommendation");
				ccc.btnSubmit.scrollAndClick();
				ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']").verifyText("Diagnostic Case Summary", "Summary Screen Header");
				technicianPage.logOut();
				Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");

	}
	
	@Test(description = "1TSB ,No ESM with QA have repair - Customer Pay, gets submitted at Technician.", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test14(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		// creating RO
		String rONumber = ROUtil.createRO(adminHomePage, data);
		// Fetching vehicle ref details
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());

		AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
				false);
		List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
		Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Customer Symptom DropDowns"));
		List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
		List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
		Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Technician Symptom DropDowns"));
		List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerDropDownValues);
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianDropDownValues);

		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 1, "Expected TSB list size=1 but Actual list size=" + tsbListSize);
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		// Precondition and fetching test data.

		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
				"Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Customer OtherSymptoms"));
		String paymentAssumption = data.get("Payment Assumption");
		String actualpaymentAssumption = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getDropDownWebElement(DropDownName.PAYMENTASSUMPTION)
				.getFirstSelectedOptionText();
		Assert.assertTrue(
				actualpaymentAssumption.equalsIgnoreCase(paymentAssumption),
				"Expected Payment Assumption: "+paymentAssumption+", Actual Payment Assumption: " + actualpaymentAssumption);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Technician OtherSymptoms"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

		

		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		questionsComponent.respondToYesNoQuestion("No", 1);
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"), "No further questions");
		questionsComponent.btnSubmit.scrollAndClick();

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Defect in factory materials");
		ccc.txtDefaultRepair.verifyText(evaluatedTSB.getDefaultRepair(), "Repair Recommendation");
		ccc.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']").verifyText("Diagnostic Case Summary", "Summary Screen Header");
		Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");
	}
	
	@Test(description = "1 TSB with QA have repair, technician disagrees checkbox checked, case routes to VCAT", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test15(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		// creating RO
		String rONumber = ROUtil.createRO(adminHomePage, data);
		// Fetching vehicle ref details
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());
		AutomaticTransmissionCVTSymptomFormComponent automaticTransmissionCVTSymptomFormComponent = new AutomaticTransmissionCVTSymptomFormComponent(
				false);
		List<String> customerSympCheckboxes = automaticTransmissionCVTSymptomFormComponent.getCheckBoxesList(data.get("Customer Symptom Checkboxes"));
		Map<String, String> customerDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Customer Symptom DropDowns"));
		List<String> customerDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(customerDropDownMap);
		List<String> technicianSympCheckboxes = automaticTransmissionCVTSymptomFormComponent
				.getCheckBoxesList(data.get("Technician Symptom Checkboxes"));
		Map<String, String> technicianDropDownMap = automaticTransmissionCVTSymptomFormComponent
				.getDropDownMap(data.get("Technician Symptom DropDowns"));
		List<String> technicianDropDownValues = automaticTransmissionCVTSymptomFormComponent.getDropDownValues(technicianDropDownMap);

		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerDropDownValues);
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianDropDownValues);

		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		// Precondition and fetching test data.

		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
				"Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(customerSympCheckboxes);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(customerDropDownMap);
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Customer OtherSymptoms"));
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectCheckBoxes(technicianSympCheckboxes);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectDropDownValue(technicianDropDownMap);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtboxOtherSymptomsRecentRepairs
				.clearAndSendKeys(data.get("Technician OtherSymptoms"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();
		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 1, "Expected TSB list size=1 but Actual listsize=" + tsbListSize);

		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		questionsComponent.respondToYesNoQuestion("No", 1);
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"), "No further questions");
		questionsComponent.btnSubmit.scrollAndClick();

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Modification");
		ccc.txtDefaultRepair.verifyText(evaluatedTSB.getDefaultRepair(), "Repair Recommendation");
		ccc.chkboxTechnicianDisagrees.click();
		ccc.dropdownRecommendRepair.select("Vibration-checked cvt");
		String tecnicianRecRepair = ccc.dropdownRecommendRepair.getFirstSelectedOptionText();
		ccc.txtboxTechnicianDisagreeComments.clearAndSendKeys("Technician Disagrees");
		naaRulesMap.put(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR, ccc.chkboxTechnicianDisagrees.isSelected());
		ccc.btnSubmit.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		Assert.assertTrue(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT) || naaRulesMap.get(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR),
				"Expected true , Actual false");
		technicianPage.logOut();

		VCATPage vCATPage = new VCATPage("pccf1analyst", "rules");
		vCATPage.waitForPageToLoad();
		vCATPage.getLeftSideMenuElement("VCAT Support").click();
		VCATSupportComponent vCATSupportComponent = new VCATSupportComponent();
		vCATSupportComponent.filterAndClick("Case ID", dCaseID, dCaseID);
		// verify checkboxes
		VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent = new VCATAutomaticTransmissionCVTSymtomFormComponent();
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PAYMNTASSMPTNCHNGDNWTOW).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW) ? "true" : "",
				"Payment assumption has been updated from Non Warranty to Warranty");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.TECHDISAGRDSYSRECMNDREPAIR).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR).toString(), "Technician Disagreed with System Recommended Repair");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ARCDEALER).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.ARCDEALER).toString(), "ARC Dealer");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ENGINEERINGREVIEW).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.ENGINEERINGREVIEW).toString(), "Engineering Review");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MANUFACTURINGDATE).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.MANUFACTURINGDATE).toString(), "Manufacturing date");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MILEAGE).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.MILEAGE).toString(), "Mileage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MONTHSINSERVICE).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.MONTHSINSERVICE).toString(), "Months in service");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.VIN).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.VIN).toString(), "VIN");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.DEALERSPECIFIC).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.DEALERSPECIFIC).toString(), "Dealer Specific");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ADDITIONALRULES).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.ADDITIONALRULES).toString(), "Additional Rules");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.INSERVICEDATEUNAVAILABLE).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.INSERVICEDATEUNAVAILABLE).toString(), "In-service date unavailable");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.SERVICECONTRACTCOVERAGE).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.SERVICECONTRACTCOVERAGE).toString(), "Service Contract Coverage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PARTSWARRANTYCOVERAGE).verifyAttribute("alt",
				naaRulesMap.get(VCATCheckpoint.PARTSWARRANTYCOVERAGE).toString(), "Parts Warranty Coverage");

		vCATAutomaticTransmissionCVTSymtomFormComponent.getRadioButton(RadioBtnName.SYSTEMRECOMMENDATIONRIGHT, "Yes").click();
		vCATAutomaticTransmissionCVTSymtomFormComponent.getRadioButton(RadioBtnName.AGREETECHNICIANRECOMMENDATION, "Yes").click();
		WaitUtil.sleep(2000);
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtboxVCATSupportNotes.clearAndSendKeys("VCAT support");
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtPaymentType.verifyText(data.get("Payment Assumption"), "Payment Assumption");
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtFinalRepair.verifyText(tecnicianRecRepair, "Final Repair");
		vCATAutomaticTransmissionCVTSymtomFormComponent.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']").verifyText("Diagnostic Case Summary", "Summary Screen Header");
		vCATPage.logOut();

	}
	
}
