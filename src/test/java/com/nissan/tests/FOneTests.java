package com.nissan.tests;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.automation.core.utils.APIUtil;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil;
import com.nissan.automation.core.utils.StringMatcher;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.automation.core.utils.dataprovider.TestDataProvider;
import com.nissan.automation.core.utils.listeners.ConfigListener;
import com.nissan.databeans.NAARulesDataBean;
import com.nissan.databeans.TSBAPIDataBean;
import com.nissan.databeans.TSBDataBean;
import com.nissan.databeans.TestDataBean;
import com.nissan.enums.Roles;
import com.nissan.enums.UserData;
import com.nissan.enums.VCATCheckbox;
import com.nissan.enums.VCATCheckpoint;
import com.nissan.pages.AdminHomePage;
import com.nissan.pages.BasePage;
import com.nissan.pages.DVehicleReferenceDataPage;
import com.nissan.pages.EngManagerPage;
import com.nissan.pages.ServiceAdvisorPage;
import com.nissan.pages.TechnicianPage;
import com.nissan.pages.VCATPage;
import com.nissan.pages.components.ActiveTSBComponent;
import com.nissan.pages.components.AutomaticTransmissionCVTSymptomFormComponent;
import com.nissan.pages.components.CCCComponent;
import com.nissan.pages.components.QuestionsComponent;
import com.nissan.pages.components.TSBComponent;
import com.nissan.pages.components.VCATAutomaticTransmissionCVTSymtomFormComponent;
import com.nissan.pages.components.VCATAutomaticTransmissionCVTSymtomFormComponent.RadioBtnName;
import com.nissan.pages.components.VCATSupportComponent;
import com.nissan.utils.ApplicableTSBsUtil;
import com.nissan.utils.ApplicableTSBsUtil.ApplicableTSBListObject.pxResults;
import com.nissan.utils.DApplicableWarrantyUtil;
import com.nissan.utils.ROUtil;
import com.nissan.utils.TSBUtil;
import io.restassured.RestAssured;

@Listeners(ConfigListener.class)
public final class FOneTests extends BaseTest {

	/*@Test
	public void test04() {
		getDriver().manage().deleteAllCookies();
		String rONumber = "";
		String modelNo = "L32";
		List<String> customerSymptomsSet;
		List<String> technicianSymptomsSet;
		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		rONumber = adminHomePage.getRONumber(System.getProperty("user.dir") + "/resources/roxmls/RO_Xml_Sample.txt",
				"STATIntSvc:01-06-03");
		modelNo = adminHomePage.getVehicleModel("1N4BL2AP6AN487528")[0];
		adminHomePage.logOut();
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
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent()
				.getDropDownWebElement(DropDownName.ENGINESTALLWHEN).select("P-to-R");
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent()
				.getDropDownWebElement(DropDownName.VIBRATION).select("Judder");
		customerSymptomsSet = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getSymptomsList();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou.verifyText(
				"Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent()
				.getDropDownWebElement(DropDownName.VIBRATION).select("Judder");
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm
				.click();
		technicianSymptomsSet = technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent()
				.getSymptomsList();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();
		technicianPage.logOut();
		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		int listSize = evaluatedTSB.getListTSB().size();
		String outcome = "";
		if (listSize > 0) {
			if (listSize == 1) {
				outcome = "1 TSB evaluated";
				System.out.println("1 TSB evaluated");
			} else {
				outcome = "Multiple TSBs evaluated";
				System.out.println("Multiple TSBs evaluated");
			}
		} else {
			outcome = "No TSB evaluated";
			System.out.println("No TSB evaluated");
		}
		TechnicianPage technicianPage2 = new TechnicianPage("DWATAC64", "rules");
		technicianPage2.waitForPageToLoad();
		technicianPage2.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage2.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		if (listSize == 1) {
			QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
			questionsComponent.waitForComponentToLoad();
			questionsComponent.respondToQuestion();
			questionsComponent.btnSubmit.scrollAndClick();
		}
		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.sendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Modification");
		// boolean hasRepair = evaluatedTSB.getQuestionsDataBean()
		if (evaluatedTSB.isRepairFlag()) {
			// boolean techDisagree = new Random().nextBoolean();
			boolean techDisagree = true;
			ccc.txtDefaultRepair.verifyText(evaluatedTSB.getDefaultRepair(), "Repair Recommendation");
			if (techDisagree) {
				ccc.chkboxTechnicianDisagrees.click();
				ccc.dropdownRecommendRepair.select("Vibration-checked cvt");
				ccc.txtboxTechnicianDisagreeComments.sendKeys("Technician Disagrees");
			}

		} else {
			ccc.dropdownRecommendRepair.select("Vibration-checked cvt");
			ccc.textboxRepairJustification.sendKeys("Repair");
		}
		// boolean reDiagnoseDCase = new Random().nextBoolean();
		
		 * boolean reDiagnoseDCase = true; if(reDiagnoseDCase) {
		 * ccc.btnReDiagnose.scrollAndClick();
		 * 
		 * }
		 
		ccc.btnSubmit.scrollAndClick();

		if ((listSize != 1)) {
			// PCC question
		}

	}*/

	@Test(description = "1 TSB with repair, technician disagrees checkbox checked, case routes to VCAT", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
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
		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 1, "Expected TSB list size=1 but Actual listsize=" + tsbListSize);

		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		questionsComponent.respondToYesNoQuestion("No", 1);
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"),
				"No further questions");
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
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtThankYou.verifyText(
				"Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		Assert.assertTrue(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT)
				|| naaRulesMap.get(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR), "Expected true , Actual false");
		technicianPage.logOut();

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
				.getRadioButton(RadioBtnName.AGREETECHNICIANRECOMMENDATION, "Yes").click();
		WaitUtil.sleep(2000);
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtboxVCATSupportNotes.clearAndSendKeys("VCAT support");
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtPaymentType.verifyText(data.get("Payment Assumption"),
				"Payment Assumption");
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtFinalRepair.verifyText(tecnicianRecRepair, "Final Repair");
		vCATAutomaticTransmissionCVTSymtomFormComponent.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']")
				.verifyText("Diagnostic Case Summary", "Summary Screen Header");
		vCATPage.logOut();

	}

	@Test(description = "1 TSB with repair, technician disagrees checkbox checked, case routes to VCAT", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test101(Map<String, String> data) {

		AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.waitForPageToLoad();
		DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.getVehicleReferenceMap(data.get("VIN")));
		adminHomePage.logOut();
		data.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());
		/*
		 * AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		 * adminHomePage.waitForPageToLoad();
		 * DVehicleReferenceDataPage.setVehicleReferenceMap(adminHomePage.
		 * getVehicleReferenceMap(data.get("VIN"))); //
		 * adminHomePage.waitForPageToLoad(); adminHomePage.logOut(); Map<String,
		 * String> dataMap = new LinkedHashMap<>(); dataMap.putAll(data);
		 * 
		 * dataMap.putAll(DVehicleReferenceDataPage.getVehicleReferenceMap());
		 * 
		 * dataMap.put("ManufacturedDate", "2013-05-07");// mandate false
		 * 
		 * dataMap.put("vehicleRefMileage", "9000");
		 * 
		 * dataMap.put("InServiceDate", "2021-12-21");// months in Service true
		 * 
		 * dataMap.put("ModelLineCode", "A35");
		 * 
		 * NAARulesUtil.evaluateNAARules(dataMap).forEach((k, v) -> System.out.println(k
		 * + ":" + v));
		 */
		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		// TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet,
		// technicianSymptomsSet);
		Map<VCATCheckpoint, Boolean> naaRulesMap = engManagerPage.getNAARules(data);
		engManagerPage.logOut();
		String dCaseID = "D-189502";
		VCATPage vCATPage = new VCATPage("pccf1analyst", "rules");
		vCATPage.waitForPageToLoad();
		vCATPage.getLeftSideMenuElement("VCAT Support").click();
		VCATSupportComponent vCATSupportComponent = new VCATSupportComponent();
		vCATSupportComponent.filterAndClick("Case ID", dCaseID, dCaseID);
		// verify checkboxes
		VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent = new VCATAutomaticTransmissionCVTSymtomFormComponent();

		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PAYMNTASSMPTNCHNGDNWTOW)
				.verifyAttribute("alt", naaRulesMap.get(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW).toString(),
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
				.getRadioButton(RadioBtnName.AGREETECHNICIANRECOMMENDATION, "Yes").click();
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtboxVCATSupportNotes.clearAndSendKeys("VCAT support");
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtPaymentType.verifyText(data.get("Payment Assumption"),
				"Payment Assumption");
		// vCATAutomaticTransmissionCVTSymtomFormComponent.txtFinalRepair.verifyText(tecnicianRecRepair,
		// "Final Repair");
		vCATAutomaticTransmissionCVTSymtomFormComponent.btnSubmit.scrollAndClick();

	}

	/*@Test(description = "1TSB scenario with warranty, gets submitted at Technician", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
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
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm
				.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();

		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 1, "Expected: 1 tsb Actual: " + tsbListSize + " tsb(s) matched");

		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		questionsComponent.respondToYesNoQuestion("No", 1);
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"),
				"No further questions");
		questionsComponent.btnSubmit.scrollAndClick();

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.sendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Defect in factory materials");
		ccc.txtDefaultRepair.verifyText(evaluatedTSB.getDefaultRepair(), "Repair Recommendation");
		ccc.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']")
				.verifyText("Diagnostic Case Summary", "Summary Screen Header");
		technicianPage.logOut();

		Assert.assertFalse(naaRulesMap.get(VCATCheckpoint.ROUTETOVCAT), "Expected false , Actual true");

	}*/

/*
	@Test(dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
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
		List<String> customerSympCheckboxes = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent()
				.selectCheckBoxes(data.get("Customer Symptom Checkboxes"));
		List<String> customerSymptomDropDowns = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent()
				.selectDropDownValue(data.get("Customer Symptom DropDowns"));
		List<String> customerSymptomsSet = new ArrayList<>();
		customerSymptomsSet.addAll(customerSympCheckboxes);
		customerSymptomsSet.addAll(customerSymptomDropDowns);

		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou.verifyText(
				"Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		List<String> technicianSympCheckboxes = technicianPage
				.getTechnicianAutomaticTransmissionCVTSymptomFormComponent()
				.selectCheckBoxes(data.get("Technician Symptom Checkboxes"));
		List<String> technicianSymptomDropDowns = technicianPage
				.getTechnicianAutomaticTransmissionCVTSymptomFormComponent()
				.selectDropDownValue(data.get("Technician Symptom DropDowns"));
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm
				.click();
		List<String> technicianSymptomsSet = new ArrayList<>();
		technicianSymptomsSet.addAll(technicianSympCheckboxes);
		technicianSymptomsSet.addAll(technicianSymptomDropDowns);

		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();
		technicianPage.logOut();

		TSBDataBean evaluatedTSB = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet);
		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 1, "Expected: 1 tsb Actual: " + tsbListSize + " tsb(s) matched");
		TechnicianPage technicianPage2 = new TechnicianPage("DWATAC64", "rules");
		technicianPage2.waitForPageToLoad();
		technicianPage2.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage2.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);

		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		questionsComponent.respondToYesNoQuestion("No", 1);
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"),
				"No further questions");
		questionsComponent.btnSubmit.scrollAndClick();

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.sendKeys("Engine");
		ccc.dropdownWhyDoYouThinkPartFailed.select("Defect in factory materials");
		ccc.txtDefaultRepair.verifyText(evaluatedTSB.getDefaultRepair(), "Repair Recommendation");
		ccc.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']")
				.verifyText("Diagnostic Case Summary", "Summary Screen Header");
		technicianPage2.logOut();
		NAARulesUtil.evaluateNAARules(data).forEach((k, v) -> System.out.println(k + ":" + v));

	}*/
	
	@Test(description = "1 TSB with QA have repair, technician disagrees checkbox checked, case routes to VCAT", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test15(Map<String, String> data) {
		// Precondition and fetching test data.
		getDriver().manage().deleteAllCookies();
		TestDataBean testData = new TestDataBean(data);
		TSBDataBean evaluatedTSB = testData.getTsbDataBean();
		int tsbListSize = evaluatedTSB.getListTSB().size();
		Assert.assertTrue(tsbListSize == 1, "Expected TSB list size=1 but Actual listsize=" + tsbListSize);
		NAARulesDataBean nAARulesDataBean = testData.getnAARulesDataBean();
		// Precondition and fetching test data.
		String rONumber = testData.getRoNumber();
		ServiceAdvisorPage serviceAdvisorPage = new ServiceAdvisorPage("DSCHER49", "rules");
		serviceAdvisorPage.waitForPageToLoad();
		serviceAdvisorPage.getDashboardComponent().getPleaseSelectDealershipComponent().txtDashboardHeader.verifyText("Please select the Dealership",
				"Dashboard header");
		serviceAdvisorPage.getDashboardComponent().getWhatsNewComponent().txtWhatsNew.verifyText("What's New!");
		serviceAdvisorPage.getLeftSideMenuElement("New CVT Symptom Form").click();
		serviceAdvisorPage.getNewCVTSymptomFormComponent().filterAndClick("RO# / WO#", rONumber, "Select");
		serviceAdvisorPage.getNewCVTSymptomFormComponent().btnSkipROL.scrollAndClick();
		String dCaseID = serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().getHeaderWebElement("Case ID").getText();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().selectSymptoms(testData.getCustomerSymptom());
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().btnSubmit.scrollAndClick();
		serviceAdvisorPage.getAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		serviceAdvisorPage.logOut();

		TechnicianPage technicianPage = new TechnicianPage("DWATAC64", "rules");
		technicianPage.waitForPageToLoad();
		technicianPage.getLeftSideMenuElement("Pending Tech Action").click();
		technicianPage.getPendingTechActionsComponent().filterAndClick("RO# / WO#", rONumber, dCaseID);
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().selectSymptoms(testData.getTechnicianSymptom());
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().expandDTC.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().getdTCListComponent().chkboxIConfirm.click();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().btnDiagnose.scrollAndClick();
		

		QuestionsComponent questionsComponent = new QuestionsComponent(evaluatedTSB);
		questionsComponent.waitForComponentToLoad();
		questionsComponent.respondToYesNoQuestion("No", 1);
		questionsComponent.txtNoFurtherQuestions.verifyText(StringMatcher.startsWith("No further"), "No further questions");
		questionsComponent.btnSubmit.scrollAndClick();

		CCCComponent ccc = new CCCComponent();
		ccc.txtBoxFailedFirstPFP.clearAndSendKeys(testData.getUserData().get(UserData.CCCPARTFILEDFIRST));
		ccc.dropdownWhyDoYouThinkPartFailed.select(testData.getUserData().get(UserData.CCCWHYPARTFAILED));
		ccc.txtDefaultRepair.verifyText(evaluatedTSB.getDefaultRepair(), "Repair Recommendation");
		ccc.chkboxTechnicianDisagrees.click();
		ccc.dropdownRecommendRepair.select(testData.getUserData().get(UserData.CCCREPAIRRECOMMENDATION));

		String tecnicianRecRepair = ccc.dropdownRecommendRepair.getFirstSelectedOptionText();
		ccc.txtboxTechnicianDisagreeComments.clearAndSendKeys(testData.getUserData().get(UserData.CCCREPAIRJUSTIFICATIONCOMMENTS));
		//testData.getnAARulesDataBean().setTechDisagrdSysRecmndRepair(ccc.chkboxTechnicianDisagrees.isSelected());
		ccc.btnSubmit.scrollAndClick();
		technicianPage.getTechnicianAutomaticTransmissionCVTSymptomFormComponent().txtThankYou
				.verifyText("Thank you! The next step in this case has been routed appropriately.", "Thank You message");
		//Assert.assertTrue(nAARulesDataBean.isRouteToVCAT() || nAARulesDataBean.getTechDisagrdSysRecmndRepair().equalsIgnoreCase("true"),
			//	"Expected true , Actual false");
		technicianPage.logOut();

		VCATPage vCATPage = new VCATPage("pccf1analyst", "rules");
		vCATPage.waitForPageToLoad();
		vCATPage.getLeftSideMenuElement("VCAT Support").click();
		VCATSupportComponent vCATSupportComponent = new VCATSupportComponent();
		vCATSupportComponent.filterAndClick("Case ID", dCaseID, dCaseID);
		// verify checkboxes
		VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent = new VCATAutomaticTransmissionCVTSymtomFormComponent();
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PAYMNTASSMPTNCHNGDNWTOW).verifyAttribute("alt",
				String.valueOf(nAARulesDataBean.getPaymntAssmptnChngdNWtoW()),
				"Payment assumption has been updated from Non Warranty to Warranty");
	//	vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.TECHDISAGRDSYSRECMNDREPAIR).verifyAttribute("alt",
		//		nAARulesDataBean.getTechDisagrdSysRecmndRepair(), "Technician Disagreed with System Recommended Repair");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ARCDEALER).verifyAttribute("alt",
				nAARulesDataBean.getArcDealer(), "ARC Dealer");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ENGINEERINGREVIEW).verifyAttribute("alt",
				nAARulesDataBean.getEngineeringReview(), "Engineering Review");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MANUFACTURINGDATE).verifyAttribute("alt",
				nAARulesDataBean.getManufacturingDate(), "Manufacturing date");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MILEAGE).verifyAttribute("alt",
				nAARulesDataBean.getMileage(), "Mileage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.MONTHSINSERVICE).verifyAttribute("alt",
				nAARulesDataBean.getMonthsInService(), "Months in service");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.VIN).verifyAttribute("alt",
				nAARulesDataBean.getVin(), "VIN");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.DEALERSPECIFIC).verifyAttribute("alt",
				nAARulesDataBean.getDealerSpecific(), "Dealer Specific");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.ADDITIONALRULES).verifyAttribute("alt",
				nAARulesDataBean.getAdditionalRules(), "Additional Rules");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.INSERVICEDATEUNAVAILABLE).verifyAttribute("alt",
				nAARulesDataBean.getInServiceDateUnavailable(), "In-service date unavailable");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.SERVICECONTRACTCOVERAGE).verifyAttribute("alt",
				nAARulesDataBean.getServiceContractCoverage(), "Service Contract Coverage");
		vCATAutomaticTransmissionCVTSymtomFormComponent.getCheckBoxWebElement(VCATCheckbox.PARTSWARRANTYCOVERAGE).verifyAttribute("alt",
				nAARulesDataBean.getPartsWarrantyCoverage(), "Parts Warranty Coverage");

		vCATAutomaticTransmissionCVTSymtomFormComponent.getRadioButton(RadioBtnName.SYSTEMRECOMMENDATIONRIGHT, testData.getUserData().get(UserData.VCATSYSTEMMAKETHERIGHTRECOMMENDATION)).click();
		vCATAutomaticTransmissionCVTSymtomFormComponent.getRadioButton(RadioBtnName.AGREETECHNICIANRECOMMENDATION, testData.getUserData().get(UserData.VCATAGREEWITHTHETECHNICIANRECOMMENDATION)).click();
		WaitUtil.sleep(2000);
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtboxVCATSupportNotes.clearAndSendKeys(testData.getUserData().get(UserData.VCATSUPPORTNOTES));
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtPaymentType.verifyText(testData.getPaymentAssumption(), "Payment Assumption");
		vCATAutomaticTransmissionCVTSymtomFormComponent.txtFinalRepair.verifyText(tecnicianRecRepair, "Final Repair");
		vCATAutomaticTransmissionCVTSymtomFormComponent.btnSubmit.scrollAndClick();
		ExtWebComponent.getExtWebElement("//div[text()='Diagnostic Case Summary']").verifyText("Diagnostic Case Summary", "Summary Screen Header");
		vCATPage.logOut();

	}
	
	private static final Log oLog = LogFactoryImpl.getLog(FOneTests.class);
	
	@Test
	public void test01() {
		
		//PoiExcelUtil.getExcelDataAsMap("/resources/testdata/ConnectorData.xlsx", "SPEC NOTE BLOCK 11");
		
		try

        {

                        String f =      System.getProperty("user.dir")+"/resources/testdata/ConnectorData.xlsx";                 
            org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(new FileInputStream(f));

            //Get first/desired sheet from the workbook

            org.apache.poi.xssf.usermodel.XSSFSheet sheet = workbook.getSheetAt(1);

            //Iterate through each rows one by one

          //  java.util.Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet.iterator();

            

           for(Row row:sheet)

            {

                // oLog.error("total number of merged regions: " + row.getNumMergedRegions());

                //For each row, iterate through all the columns

              /*  java.util.Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();

                

                while (cellIterator.hasNext())

                {

                    org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();

                    //Check the cell type and format accordingly

                                                                                switch (cell.getCellType()) {

                                                                                                case NUMERIC:

                                                                                                oLog.error(cell.getNumericCellValue());                   

                                                                                                break;

                                                                                               

                                                                                                case BOOLEAN:

                                                                                                oLog.error(cell.getBooleanCellValue());

                                                                                                break;

                                                                                               

                                                                                                case BLANK:

            oLog.error(cell.getStringCellValue()+ "from blank");

                                                                                                break;

                                                                                               

                                                                                                case STRING:

                                                                                                oLog.error(cell.getStringCellValue());

                                                                                                break;

                                                                                               

                                                                                                default:

            oLog.error(cell.getStringCellValue()+ "from default");

                                                                                                break;

                                                                                }           

                   

                } */

               

            }

          

                                                oLog.error("total number of merged regions: " + sheet.getNumMergedRegions());

                                                List<CellRangeAddress> listOfAddr = new ArrayList<>(); 
                                                for (int i = 0;   i < sheet.getNumMergedRegions();  i++)

                                                                {

                                                                oLog.error("inside for loop");

                                                                org.apache.poi.ss.util.CellRangeAddress region = sheet.getMergedRegion(i);
                                                               
                                                                listOfAddr.add(region);
                                                                
                                                               
                                                                int colIndex = region.getFirstColumn();

                                                                int rowNum = region.getFirstRow();

                                                                int lastcol = region.getLastColumn();

                                                                int lastrow = region.getLastRow();

                                                                oLog.error("first column: " + colIndex + "; last column: " + lastcol + ";first row: " + rowNum + "; last row: " + lastrow);

                                                                }

                                                oLog.error("before sorting");
                                                listOfAddr.stream().forEach(e->oLog.error(e.getFirstRow()));
                                                Collections.sort(listOfAddr,new SortByRowID());
                                                oLog.error("after sorting");
                                                listOfAddr.stream().forEach(e->oLog.error(e.getFirstRow()));
           // file.close();

        }

        catch (Exception e)

        {

            oLog.error("inside catch block");

        }
		
		
		
	}

	class SortByRowID implements Comparator<CellRangeAddress>{

		@Override
		public int compare(CellRangeAddress cell1, CellRangeAddress cell2) {
			
			
			
			return cell1.getFirstRow()-cell2.getFirstRow();
				
			
		}
		
		
	}
	
	@Test
	public void test02() {
		
		String dataPage = "D_ApplicableWarranty";
		//Make=N&Mileage=86000&ModelYear=2014&OPCODE=JX56AA&PFP=3121428X7C&VIN=5N1AR2MM6EC698652&ROOpenDate=20210813"
		Map<String, String> params = new HashMap<String, String>();
		
		/*params.put("Make", "N");
		params.put("ModelYear", "2014");
		params.put("Mileage", "86000");
		params.put("OPCODE", "JX56AA");
		params.put("PFP", "3121428X7C");
		params.put("VIN", "5N1AR2MM6EC698652");
		params.put("ROOpenDate", "20210813");*/
		/*params.put("VIN", "5N1AR2MM6EC698652");
		Response resp = APIUtil.getResponse(dataPage,params);
		List<Map<String, String>> obj = APIUtil.getListOfMaps(resp, "pxResults");*/
		
		
		//Map<String,String> map =resp.jsonPath().getMap("");
		/*
		 * 
		 */
		params.put("VIN", "5N1AR2MM6EC698652");
		List<Map<String, String>> obj = APIUtil.getListOfMaps("CVT",params, "pxResults");
		for(Map<String,String> json: obj) {
			
			String desc = json.get("pyDescription");
			System.out.println(desc);
			
		}
		Map<String, String> returnMap = DApplicableWarrantyUtil.getApplicableWarrantyMap("5N1AR2MM6EC698652", "POWERTRAIN");
		System.out.println(returnMap);
		
		
		
		//System.out.println(resp.asPrettyString());
		
		
		
				
		
	}
	
	@Test(description = "No TSB, case gets submitted at technician and summary screen is displayed.", dataProvider = "GetExcelDataWithTestCaseID", dataProviderClass = TestDataProvider.class)
	public void test01(Map<String, String> data) {
		
		//Precondition and fetching test data.
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
		
	}
	
	@Test
	public void test12345() {
		
		String path = "https://vcat.sit.nnanet.com/prweb/api/v1/data/D_ApplicableTSBs_Automation?ModelCode=L32&ModelYear=2010&ComponentCategory=CVT";
		
		
		
		//String resp = RestAssured.given().auth().basic("vardhanib", "rules").when().get(path).asPrettyString();
		String resp = RestAssured.given().auth().basic("vardhanib", "rules").when().get(path).then().statusCode(200).extract().response().asPrettyString();
		
		System.out.println(resp);
		
	}
	
	
	
	
	public void delete() {
		ExtWebComponent.getExtWebElement("//div[@role='button' and text()='Actions ']").click();
		ExtWebComponent.getExtWebElement("//span[text()='Delete TSB & Tribal Knowl...']").click();
	}
	
	public void searchTSB(String tsbID) {
		
		AdminHomePage adminHomePage = new AdminHomePage();
		adminHomePage.closeTab(tsbID);
		
		adminHomePage.textboxSearch.clearAndSendKeys(tsbID + Keys.ENTER);
		String xPath = String.format("//tr[contains(@id,'SearchResults')]//a[text()='%s']",tsbID);
		ExtWebComponent.getExtWebElement(xPath).click();
	}


@Test
public void test() {
	
	
	/*//getPageObject("SERVICEADVISOR").loginWithRole("SERVICEADVISOR", "3900");
	//String expectedPaidDate = new LocalDate(DateTimeZone.UTC).toString();
	
	String oldAmount = new DecimalFormat("#.00").format(100/3);
	System.out.println(oldAmount);*/
	/*getDriver().manage().deleteAllCookies();
	AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
	adminHomePage.waitForPageToLoad();	
	adminHomePage.logOut();
	adminHomePage.login(Roles.ADMIN);
	adminHomePage.waitForPageToLoad();	*/
	
	String s="test";
	String s1 = s.concat("Webdriver");
	System.out.println(s+" "+s1);
	
}

public <T extends BasePage> T getPageObject(String roleName){
	
	
	
	if(roleName.equals("SERVICEADVISOR")){
		return (T) new ServiceAdvisorPage();
		
	}
	
	return null;
}

@Test
public void test123() {
	EngManagerPage engManagerPage1 = new EngManagerPage(Roles.ENGINEERINGMANAGER);
	engManagerPage1.waitForPageToLoad();
	
	
	
}
	private FOneTests() {

	}
}
