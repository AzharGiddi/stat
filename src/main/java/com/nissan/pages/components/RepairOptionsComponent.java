package com.nissan.pages.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.BDDDataTableUtil;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.DCategoryByPFPOPCODEUtil;
import com.nissan.utils.DCategoryByPFPOPCODEUtil.ComponentCategoryList.pxResults;

import io.cucumber.datatable.DataTable;

public class RepairOptionsComponent extends ExtWebComponent {

	@FindBy(xpath = "//h2[text()='Repair Options']")
	public ExtWebElement headerRepairOptions;

	@FindBy(xpath = "//tr[contains(@id,'$PpyDisplayHarness$pFieldReviewRepairs$') and contains(@oaargs,'RepairID')]")
	public List<ExtWebElement> listRepairs;

	@FindBy(xpath = "//a[@title='Add item' and text()='Add Record']")
	public ExtWebElement linkAddRecord;

	@FindBy(xpath = "//div[text()='Save']")
	public ExtWebElement btnSave;

	@FindBy(xpath = "//div[text()='Repair Options saved successfully']")
	public ExtWebElement txtSaveSuccessful;

	public void addPFPs(DataTable table, String componentCategory) {

		String repairTitle = "Automation Test Repair " + componentCategory;
		List<String> pFPList = new ArrayList<>();
		Map<String, Boolean> pfpToAddMap = new HashMap<>();
		for (Map<String, String> map : BDDDataTableUtil.getAsListOfMap(table)) {
			pFPList.add(map.get("PFP"));
		}
		deletePFPs(pFPList, componentCategory, repairTitle);
		addRecord(pFPList,componentCategory,repairTitle);
		saveRepairOptions();
	}

	public void saveRepairOptions() {
		btnSave.click();
		txtSaveSuccessful.waitForText("Repair Options saved successfully");
		txtSaveSuccessful.assertText("Repair Options saved successfully", "Save Successful");
	}

	public void updateComponent(ExtWebElement repair, String component) {
		ExtWebElement repairComponent = repair.getChildElement(
				String.format("//input[contains(@name,'ClaimType') and @value='%s']", component), "repairComponent");
		if (!repairComponent.isPresent()) {
			repairComponent = ExtWebComponent.getChildElement("repair component", repair,
					"//td[@data-attribute-name='Vehicle Component']//input");
			repairComponent.clearAndSendKeys(component);
		}
	}

	public void isCheckBoxSelected(ExtWebElement checkBoxElement) {
		if (!checkBoxElement.isSelected()) {
			checkBoxElement.click();
			WaitUtil.sleep(2000);
		}
	}

	public void isrequireGWReviewCheckBoxSelected(ExtWebElement repair) {

		ExtWebElement requireGWReviewCheckBox = ExtWebComponent.getChildElement(repair,
				"//input[@type='checkbox' and contains(@name,'RequiresGoodwillReview')]");
		isCheckBoxSelected(requireGWReviewCheckBox);
	}

	public void isrepairPartOpcodeRequireVCANCheckBoxSelected(ExtWebElement repair) {
		ExtWebElement repairPartOpcodeRequireVCANCheckBox = ExtWebComponent.getChildElement(repair,
				"//input[@type='checkbox' and contains(@name,'RepairPartAuthorization')]");
		isCheckBoxSelected(repairPartOpcodeRequireVCANCheckBox);
	}

	public void addRecord(List<String> pFPList, String component, String repairTitle) {

		String repairXpath = String.format(
				"//tr//input[contains(@name,'RepairTitle') and @value='%s']/ancestor::tr[contains(@id,'$PpyDisplayHarness$pFieldReviewRepairs$') and contains(@oaargs,'RepairID')]",
				repairTitle);
		ExtWebElement repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
		// to handle the situation where Repair is present but repair component has
		// changed
		updateComponent(repair, component);
		if (repair.isPresent()) {
			repair.scrollIntoView();
			isrequireGWReviewCheckBoxSelected(repair);
			repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
			isrepairPartOpcodeRequireVCANCheckBoxSelected(repair);
			repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
			ExtWebElement expandButton = repair.getChildElement("//td[starts-with(@class,'expandPane')]/span",
					"Expand pane");
			expandButton.click();
			expandButton.waitForAttribute("class", "collapseRowDetails");
			ExtWebElement repairDetails = ExtWebComponent.getExtWebElement(
					"//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]");
			String pFPRowsXpath ="//input[contains(@name,'PFPList') and @type='text']/ancestor::tr[contains(@id,'PFPList')]";
			//tr[contains(@id,'PFPList')]
			List<String> pFPRows = repairDetails.getChildElements(pFPRowsXpath, "PFP Row").stream().map(e->e.getChildElement("/td//input", "Part Number").getAttribute("value")).collect(Collectors.toList());
			
			for (String pfp : pFPList) {
				if(pFPRows.contains(pfp)) {
					continue;
				}
					repairDetails
							.getChildElement("//a[@title='Add item' and text()='Add New Part Number']", "linkAddPFP")
							.click();
					WaitUtil.sleep(2000);
					ExtWebElement txtBoxPFP = repairDetails.getChildElement(
							"//input[contains(@name,'PFPList') and @type='text' and @value='']", "txtBoxPFP");
					txtBoxPFP.sendKeys(pfp);
				
			}
			WaitUtil.sleep(2000);
			expandButton.click();
			expandButton.waitForAttribute("class", "expandRowDetails");
		} else {
			addNewRepair(pFPList, component);
		}

	}

	/*
	 * public void addRecord(List<String> pFPList, String component, String
	 * repairTitle) {
	 * 
	 * String repairXpath = String.format(
	 * "//tr//input[contains(@name,'RepairTitle') and @value='%s']/ancestor::tr[contains(@id,'$PpyDisplayHarness$pFieldReviewRepairs$') and contains(@oaargs,'RepairID')]"
	 * , repairTitle); ExtWebElement repair =
	 * ExtWebComponent.getExtWebElement(repairXpath, "repair"); // ExtWebElement
	 * repairComponent = //
	 * repair.getChildElement(String.format("//input[contains(@name,'ClaimType') and
	 * // @value='%s']", component),"repairComponent"); // to handle the situation
	 * where Repair is present but repair component has // changed
	 * updateComponent(repair, component);
	 * 
	 * if(!repairComponent.isPresent()) {
	 * repairComponent=ExtWebComponent.getChildElement("repair component",repair,
	 * "//td[@data-attribute-name='Vehicle Component']//input");
	 * repairComponent.clearAndSendKeys(component); }
	 * 
	 * if (repair.isPresent()) { repair.scrollIntoView();
	 * isrequireGWReviewCheckBoxSelected(repair);
	 * 
	 * ExtWebElement requireGWReviewCheckBox =
	 * ExtWebComponent.getChildElement(repair,
	 * "//input[@type='checkbox' and contains(@name,'RequiresGoodwillReview')]"); if
	 * (!requireGWReviewCheckBox.isSelected()) { requireGWReviewCheckBox.click();
	 * WaitUtil.sleep(2000); }
	 * 
	 * repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
	 * isrepairPartOpcodeRequireVCANCheckBoxSelected(repair);
	 * 
	 * ExtWebElement repairPartOpcodeRequireVCANCheckBox =
	 * ExtWebComponent.getChildElement(repair,
	 * "//input[@type='checkbox' and contains(@name,'RepairPartAuthorization')]");
	 * if (!repairPartOpcodeRequireVCANCheckBox.isSelected()) {
	 * repairPartOpcodeRequireVCANCheckBox.click(); WaitUtil.sleep(2000); }
	 * 
	 * repair = ExtWebComponent.getExtWebElement(repairXpath, "repair"); //
	 * repair.scrollIntoView(); ExtWebElement expandButton =
	 * repair.getChildElement("//td[starts-with(@class,'expandPane')]/span",
	 * "Expand pane"); expandButton.click(); // WaitUtil.sleep(2000);
	 * expandButton.waitForAttribute("class", "collapseRowDetails"); ExtWebElement
	 * repairDetails = ExtWebComponent.getExtWebElement(
	 * "//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]"
	 * ); for (String pfp : pFPList) { repairDetails.
	 * getChildElement("//a[@title='Add item' and text()='Add New Part Number']",
	 * "linkAddPFP") .click(); WaitUtil.sleep(2000); ExtWebElement txtBoxPFP =
	 * repairDetails.getChildElement(
	 * "//input[contains(@name,'PFPList') and @type='text' and @value='']",
	 * "txtBoxPFP"); txtBoxPFP.sendKeys(pfp); } WaitUtil.sleep(2000);
	 * expandButton.click(); expandButton.waitForAttribute("class",
	 * "expandRowDetails"); } else { addNewRepair(pFPList, component); }
	 * 
	 * 
	 * RepairOptionsComponent repairOptionsComponent = new RepairOptionsComponent();
	 * WaitUtil.sleep(5000);
	 * 
	 * repairOptionsComponent.linkAddRecord.scrollAndClick(); WaitUtil.sleep(2000);
	 * List<ExtWebElement> listRepairs = repairOptionsComponent.listRepairs;
	 * ExtWebElement newRecord = listRepairs.get(listRepairs.size() - 1);
	 * newRecord.getChildElement(
	 * "//td[starts-with(@class,'expandPane')]/span","Expand Pane button").click();
	 * newRecord.getChildElement("//input[contains(@name,'ClaimType')]","component")
	 * .sendKeys(Keys.chord(component) + Keys.TAB); newRecord.getChildElement(
	 * "//input[contains(@name,'RepairTitle')]","repair Title").
	 * sendKeys("Automation Test Data " + component+ Keys.TAB);
	 * newRecord.getChildElement(
	 * "//input[contains(@name,'RepairNote')]","Repair Note").
	 * sendKeys("Automation Test Data " + component+ Keys.TAB); newRecord.
	 * getChildElement("//input[@type='checkbox' and contains(@name,'RepairPartAuthorization')]"
	 * ,"RepairPartAuthorization").click(); newRecord =
	 * listRepairs.get(listRepairs.size() - 1); newRecord.
	 * getChildElement("//input[@type='checkbox' and contains(@name,'RequiresGoodwillReview')]"
	 * ,"RequiresGoodwillReview").click();
	 * 
	 * WaitUtil.sleep(5000); ExtWebElement repairDetails = ExtWebComponent.
	 * getExtWebElement("//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]"
	 * ); repairDetails.getChildElement(
	 * "//input[contains(@name,'PartsAmount')]","Part Amount textbox").sendKeys(
	 * "1.00"); repairDetails.getChildElement(
	 * "//input[contains(@name,'LaborAmount')]","Labour Amount textbox").sendKeys(
	 * "1.00"); repairDetails.getChildElement(
	 * "//input[contains(@name,'ExpenseAmount')]","Expense Amount textbox").sendKeys
	 * ("1.00"); for(String pfp:pFPList) { repairDetails.
	 * getChildElement("//a[@title='Add item' and text()='Add New Part Number']"
	 * ,"linkAddPartNumber").click(); repairDetails.
	 * getChildElement("//input[contains(@name,'PFPList') and @type='text']"
	 * ,"txtBoxPFP").sendKeys(pfp); }
	 * 
	 * 
	 * }
	 */

	public void addNewRepair(Map<String, Boolean> pFPList, String component) {
		RepairOptionsComponent repairOptionsComponent = new RepairOptionsComponent();
		WaitUtil.sleep(5000);

		repairOptionsComponent.linkAddRecord.scrollAndClick();
		WaitUtil.sleep(2000);
		List<ExtWebElement> listRepairOptions = repairOptionsComponent.listRepairs;
		int newRepairIndex = listRepairOptions.size() - 1;
		ExtWebElement newRepair = listRepairOptions.get(newRepairIndex);
		newRepair.getChildElement("//td[starts-with(@class,'expandPane')]/span", "Expand Pane button").click();
		updateComponent(newRepair, component);
		newRepair.getChildElement("//input[contains(@name,'ClaimType')]", "component")
				.sendKeys(Keys.chord(component) + Keys.TAB);
		newRepair.getChildElement("//input[contains(@name,'RepairTitle')]", "repair Title")
				.sendKeys("Automation Test Repair " + component + Keys.TAB);
		newRepair.getChildElement("//input[contains(@name,'RepairNote')]", "Repair Note")
				.sendKeys("Automation Test Repair " + component + Keys.TAB);
		newRepair.getChildElement("//input[@type='checkbox' and contains(@name,'RepairPartAuthorization')]",
				"RepairPartAuthorization").click();
		newRepair = listRepairOptions.get(newRepairIndex);
		newRepair.getChildElement("//input[@type='checkbox' and contains(@name,'RequiresGoodwillReview')]",
				"RequiresGoodwillReview").click();
		WaitUtil.sleep(5000);
		ExtWebElement repairDetails = ExtWebComponent.getExtWebElement(
				"//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]");
		repairDetails.getChildElement("//input[contains(@name,'PartsAmount')]", "Part Amount textbox").sendKeys("1.00");
		repairDetails.getChildElement("//input[contains(@name,'LaborAmount')]", "Labour Amount textbox")
				.sendKeys("1.00");
		repairDetails.getChildElement("//input[contains(@name,'ExpenseAmount')]", "Expense Amount textbox")
				.sendKeys("1.00");
		for (Map.Entry<String, Boolean> map : pFPList.entrySet()) {
			repairDetails
					.getChildElement("//a[@title='Add item' and text()='Add New Part Number']", "linkAddPartNumber")
					.click();
			repairDetails.getChildElement("//input[contains(@name,'PFPList') and @type='text']", "txtBoxPFP")
					.sendKeys(map.getKey());
		}

	}

	public void addNewRepair(List<String> pFPList, String component) {
		RepairOptionsComponent repairOptionsComponent = new RepairOptionsComponent();
		WaitUtil.sleep(5000);

		repairOptionsComponent.linkAddRecord.scrollAndClick();
		WaitUtil.sleep(2000);
		List<ExtWebElement> listRepairOptions = repairOptionsComponent.listRepairs;
		int newRepairIndex = listRepairOptions.size() - 1;
		ExtWebElement newRepair = listRepairOptions.get(newRepairIndex);
		newRepair.getChildElement("//td[starts-with(@class,'expandPane')]/span", "Expand Pane button").click();
		updateComponent(newRepair, component);
		newRepair.getChildElement("//input[contains(@name,'ClaimType')]", "component")
				.sendKeys(Keys.chord(component) + Keys.TAB);
		newRepair.getChildElement("//input[contains(@name,'RepairTitle')]", "repair Title")
				.sendKeys("Automation Test Repair " + component + Keys.TAB);
		newRepair.getChildElement("//input[contains(@name,'RepairNote')]", "Repair Note")
				.sendKeys("Automation Test Repair " + component + Keys.TAB);
		newRepair.getChildElement("//input[@type='checkbox' and contains(@name,'RepairPartAuthorization')]",
				"RepairPartAuthorization").click();
		newRepair = listRepairOptions.get(newRepairIndex);
		newRepair.getChildElement("//input[@type='checkbox' and contains(@name,'RequiresGoodwillReview')]",
				"RequiresGoodwillReview").click();
		WaitUtil.sleep(5000);
		ExtWebElement repairDetails = ExtWebComponent.getExtWebElement(
				"//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]");
		repairDetails.getChildElement("//input[contains(@name,'PartsAmount')]", "Part Amount textbox").sendKeys("1.00");
		repairDetails.getChildElement("//input[contains(@name,'LaborAmount')]", "Labour Amount textbox")
				.sendKeys("1.00");
		repairDetails.getChildElement("//input[contains(@name,'ExpenseAmount')]", "Expense Amount textbox")
				.sendKeys("1.00");
		for (String pfp : pFPList) {
			repairDetails
					.getChildElement("//a[@title='Add item' and text()='Add New Part Number']", "linkAddPartNumber")
					.click();
			repairDetails.getChildElement("//input[contains(@name,'PFPList') and @type='text']", "txtBoxPFP")
					.sendKeys(pfp);
		}

	}

	public void deletePFPs(List<String> pFPList, String component, String repairTitle) {

		//Map<String, Boolean> returnMap = new HashMap<>();
		for (String pFP : pFPList) {
			
			if(pFP.equals("AB1236789")) {
				System.out.println("debug");
			}
			List<pxResults> pfpStartsWithResults = DCategoryByPFPOPCODEUtil.getComponentCategory(pFP.substring(0, 5));
			List<pxResults> fullPFPResults = DCategoryByPFPOPCODEUtil.getComponentCategory(pFP);

			if (pfpStartsWithResults.isEmpty()) {
				//returnMap.put(pFP, false);
				continue;
			}
			if (pfpStartsWithResults.size() == 1 && fullPFPResults.size() == 1
					&& pfpStartsWithResults.equals(fullPFPResults)
					&& pfpStartsWithResults.get(0).componentCategory.equalsIgnoreCase(component)
					&& pfpStartsWithResults.get(0).requiresGoodwillReview
					&& pfpStartsWithResults.get(0).repairPartAuthorization) {
				//returnMap.put(pFP, true);
				continue;
			}
			
			 for (pxResults result : pfpStartsWithResults) {

				String repairXpath = String.format(
						"//input[contains(@name,'ClaimType') and @value='%s']//ancestor::tr//input[contains(@name,'RepairTitle') and @value='%s']/ancestor::tr[contains(@id,'$PpyDisplayHarness$pFieldReviewRepairs$') and contains(@oaargs,'RepairID')]",
						result.componentCategory, result.repairTitle);
				ExtWebElement repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
				repair.scrollIntoView();
				ExtWebElement expandButton = repair.getChildElement("//td[starts-with(@class,'expandPane')]/span",
						"Expand pane");
				expandButton.click();
				expandButton.waitForAttribute("class", "collapseRowDetails");
				ExtWebElement repairDetails = ExtWebComponent.getExtWebElement(
						"//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]");
				String pFPRowsXpath = String.format(
						"//input[contains(@name,'PFPList') and @type='text' and starts-with(@value,'%s')]/ancestor::tr[contains(@id,'PFPList')]",
						pFP.substring(0, 5));

				List<ExtWebElement> pFPRows = repairDetails.getChildElements(pFPRowsXpath, "PFP Row");
				Iterator<ExtWebElement> itr = pFPRows.iterator();
				while(itr.hasNext()) {
					ExtWebElement	pFPRow = itr.next();
					pFPRow.getChildElement("/td//i", "Delete PFP").click();
					WaitUtil.sleep(2000);
					pFPRows = repairDetails.getChildElements(pFPRowsXpath, "PFP Row");
					itr = pFPRows.iterator();
				}
				
				List<ExtWebElement> pfpList = repairDetails.getChildElements("//tr[contains(@id,'PFPList')]",
						"PFP Rows");
				if (pfpList.isEmpty()) {
					repairDetails
							.getChildElement("//a[@title='Add item' and text()='Add New Part Number']", "linkAddPFP")
							.click();
					ExtWebElement txtBoxPFP = repairDetails.getChildElement(
							"//input[contains(@name,'PFPList') and @type='text' and @value='']", "txtBoxPFP");
					txtBoxPFP.sendKeys("1234567abc");
				}
				
				expandButton.click();
				expandButton.waitForAttribute("class", "expandRowDetails");

			}
		}

		//return returnMap;

	}

	/***
	 * This methods deletes input pfp from the repair options not matching Repair
	 * Component and Title
	 * 
	 * @param results
	 *            - list of repair option having matching pfp
	 * @param pFP
	 *            - PFP
	 * @param component
	 *            - Component of the expected repair
	 * @param repairTitle
	 *            - Title of the expected repair
	 * @return - returns true if matching pfp is present and false if pfp is not
	 *         present under matching Repair component and Title
	 */
	public boolean deletePFPs(String pFP, String component, String repairTitle) {
		boolean isPFPPresent = false;

		List<pxResults> pfpStartsWithResults = DCategoryByPFPOPCODEUtil.getComponentCategory(pFP.substring(0, 5));
		List<pxResults> fullPFPResults = DCategoryByPFPOPCODEUtil.getComponentCategory(pFP);

		if (pfpStartsWithResults.isEmpty())
			return false;

		if (pfpStartsWithResults.size() == 1 && fullPFPResults.size() == 1
				&& pfpStartsWithResults.equals(fullPFPResults)
				&& pfpStartsWithResults.get(0).componentCategory.equalsIgnoreCase(component)
				&& pfpStartsWithResults.get(0).requiresGoodwillReview
				&& pfpStartsWithResults.get(0).repairPartAuthorization) {
			return true;
		}
		for (pxResults result : pfpStartsWithResults) {

			String repairXpath = String.format(
					"//input[contains(@name,'ClaimType') and @value='%s']//ancestor::tr//input[contains(@name,'RepairTitle') and @value='%s']/ancestor::tr[contains(@id,'$PpyDisplayHarness$pFieldReviewRepairs$') and contains(@oaargs,'RepairID')]",
					result.componentCategory, result.repairTitle);
			ExtWebElement repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
			repair.scrollIntoView();
			ExtWebElement expandButton = repair.getChildElement("//td[starts-with(@class,'expandPane')]/span",
					"Expand pane");
			expandButton.click();
			expandButton.waitForAttribute("class", "collapseRowDetails");
			ExtWebElement repairDetails = ExtWebComponent.getExtWebElement(
					"//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]");
			String pFPRowsXpath = String.format(
					"//input[contains(@name,'PFPList') and @type='text' and starts-with(@value,'%s')]/ancestor::tr[contains(@id,'PFPList')]",
					pFP.substring(0, 5));
			List<ExtWebElement> pFPRows = repairDetails.getChildElements(pFPRowsXpath, "PFP Row");
			for (ExtWebElement pFPRow : pFPRows) {
				String partNumber = pFPRow.getChildElement("/td//input", "Part Number").getAttribute("value");
				if (partNumber.equalsIgnoreCase(pFP) && component.equals(result.componentCategory)
						&& repairTitle.equals(result.repairTitle)) {
					return true;
				}
				pFPRow.getChildElement("/td//i", "Delete PFP").click();
			}
			WaitUtil.sleep(2000);
			List<ExtWebElement> pfpList = repairDetails.getChildElements("//tr[contains(@id,'PFPList')]", "PFP Rows");
			if (pfpList.isEmpty()) {
				repairDetails.getChildElement("//a[@title='Add item' and text()='Add New Part Number']", "linkAddPFP")
						.click();
				ExtWebElement txtBoxPFP = repairDetails.getChildElement(
						"//input[contains(@name,'PFPList') and @type='text' and @value='']", "txtBoxPFP");
				txtBoxPFP.sendKeys("1234567abc");
			}
			// expandButton =
			// repair.getChildElement("//td[starts-with(@class,'expandPane')]/span","Expand
			// pane");

			expandButton.click();
			expandButton.waitForAttribute("class", "expandRowDetails");

		}

		return isPFPPresent;

	}

	@Override
	public void waitForComponentToLoad() {

		try {

			waitForFrameToLoad();
			headerRepairOptions.waitForVisible();
			ExtentLogger.info(this.getClass().getSimpleName() + "loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "));

		}
	}

	public RepairOptionsComponent() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		waitForComponentToLoad();
	}

}
