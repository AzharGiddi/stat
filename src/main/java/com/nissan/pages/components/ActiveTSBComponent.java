package com.nissan.pages.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.WebElement.ExtWebElementImpl;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.EngManagerPage;
import com.nissan.reports.ExtentLogger;

public class ActiveTSBComponent extends ExtWebComponent {

	@FindBy(xpath = "//table[@pl_prop_class='NSA-Data-MakeModelYear']")
	public ExtWebElement tableApplicableModel;

	@FindBy(xpath = "//table[@pl_prop_class='NSA-Data-VIN' and @pl_prop='.VINList']")
	public ExtWebElement tableApplicableVINs;

	@FindBy(xpath = "//table[@pl_prop_class='NSA-Data-VIN' and @pl_prop='.EngineList']")
	public ExtWebElement tableApplicableEngineCodes;

	// @FindBy(xpath =
	// "//table[@pl_prop_class='NSA-Data-MakeModelYear']/tbody/tr[@pl_index]")
	@FindBy(xpath = "//table[@pl_prop_class='NSA-Data-MakeModelYear']/tbody/tr[@pl_index] | //table[@pl_prop_class='NSA-Data-MakeModelYear' and @pl_prop='.ModelList']/tbody/tr[@id='Grid_NoResults']//div[text()='No items']")
	public List<ExtWebElement> listApplicableModel;

	// @FindBy(xpath = "//table[@pl_prop_class='NSA-Data-VIN']/tbody/tr[@pl_index
	// and contains(@id,'VINList')]")
	@FindBy(xpath = "//table[@pl_prop_class='NSA-Data-VIN']/tbody/tr[@pl_index and contains(@id,'VINList')] |//table[@pl_prop_class='NSA-Data-VIN' and @pl_prop='.VINList']/tbody/tr[@id='Grid_NoResults']//div[text()='No items']")
	public List<ExtWebElement> listApplicableVINs;

	// @FindBy(xpath = "//table[@pl_prop_class='NSA-Data-VIN' and
	// @pl_prop='.EngineList']//tr[@pl_index]")
	@FindBy(xpath = "//table[@pl_prop_class='NSA-Data-VIN' and @pl_prop='.EngineList']//tr[@pl_index] | //table[@pl_prop_class='NSA-Data-VIN' and @pl_prop='.EngineList']/tbody/tr[@id='Grid_NoResults']//div[text()='No items']")
	public List<ExtWebElement> listApplicableEngCodes;

	@FindBy(xpath = "//div[text()='Component Category']/following-sibling::div/div/span[@class='standard']")
	public ExtWebElement txtComponentCategory;

	@FindBy(xpath = "//table[@pl_prop_class='NSA-Data-MakeModelYear']//td[@data-attribute-name='Engine Code']//span")
	public ExtWebElement txtEngineCode;

	@FindBy(xpath = "//h3[@class='layout-group-item-title' and text()='Exclusions']")
	public ExtWebElement headerExclusions;

	@FindBy(xpath = "//h3[@class='layout-group-item-title' and text()='Conditions']")
	public ExtWebElement headerConditions;

	@FindBy(xpath = "//h3[@class='layout-group-item-title' and text()='Questions']")
	public ExtWebElement headerQuestions;

	@FindBy(xpath = "//h3[@class='layout-group-item-title' and text()='Repairs']")
	public ExtWebElement headerRepairs;

	// @FindBy(xpath =
	// "//tr[contains(@id,'TSBExclusions')]//label[contains(text(),'Criteria')]")
	@FindBy(xpath = "//tr[contains(@id,'TSBExclusions')]//label[contains(text(),'Criteria')] | //table[@pl_prop='.TSBExclusions']//div[text()='No items']")
	public List<ExtWebElement> listExclusionCriterias;
	
	
	@FindBy(xpath = "//div[contains(@param_name,'TSBExclusionCriteria')]")
	public List<ExtWebElement> listExclusions;
	// @FindBy(xpath =
	// "//tr[contains(@id,'TSBCriteria')]//label[contains(text(),'Criteria')]")
	@FindBy(xpath = "//tr[contains(@id,'TSBCriteria')]//label[contains(text(),'Criteria')] | //table[@pl_prop='.TSBCriteria']//div[text()='No items']")
	public List<ExtWebElement> listTSBConditions;

	//@FindBy(xpath = "//select[contains(@name,'TSBRepairs')]")
	@FindBy(xpath = "//td[@data-attribute-name='Repair Title']//span")
	public List<ExtWebElement> listRepairTitles;

	//@FindBy(xpath = "//input[@data-ctl='[\"TextInput\"]' and contains(@name,'TSBRepairs')]")
	@FindBy(xpath = "//td[@data-attribute-name='Repair Description']//span")
	public List<ExtWebElement> listRepairDesc;

	//@FindBy(xpath = "//input[@checked]/ancestor::tr[contains(@id,'TSBRepairs')]/child::td//select[contains(@name,'TSBRepairs')]/option[@selected]")
	@FindBy(xpath = "//img[@alt='True']/ancestor::td[@data-attribute-name='Default Repair']/following-sibling::td[@data-attribute-name='Repair Title']//span")
	public ExtWebElement txtDefaultRepair;

	@FindBy(xpath = "//span/label[text()='Does Not Apply']/preceding-sibling::img")
	public List<ExtWebElement> listChkboxDoesNotApply;
	
	//Questions locators
	@FindBy(xpath = "//tr[contains(@oaargs,'NSA-FW-STATFW-Data-TSBQuestions')]//h2 | //table[@pl_prop='.TSBQuestions']//div[text()='No items']")
	public List<ExtWebElement> listQuestions;
	
	public String txtQuestionType= "//tr[contains(@id,'TSBQuestions$l%d')]//descendant::span[text()='Question type']/parent::span/following-sibling::div/span";
	
	public String txtQuestionText= "//tr[contains(@id,'TSBQuestions$l%d')]//descendant::span[text()='Question Text']/parent::span/following-sibling::div/span";
	
	public String txtOptions= "//tr[contains(@id,'TSBQuestions$l%d')]//descendant::span[text()='Option']/following-sibling::div/span[text()='%s']";
	
	public String listActions = "//tr[contains(@id,'TSBQuestions$l%d') and contains(@id,'Actions$l')]/descendant::div/span[text()='Option']";
	
	public String listOptions = "//tr[contains(@id,'TSBQuestions$l%d') and contains(@id,'Actions$l%d')]/descendant::div/span[text()='Option']//parent::div/parent::div/descendant::div[contains(@data-ui-meta,'TSBQuestionAction')]";
	
	@FindBy(xpath = "//tr[contains(@id,'TSBQuestions$l%d') and contains(@id,'Actions$l%d')]/descendant::div/span[text()='Option']//parent::div/parent::div/descendant::div[contains(@data-ui-meta,'TSBQuestionAction')]")
	public List<ExtWebElement> listOptionsElements;	
	
	public String fieldsXpath = "//td[@data-attribute-name='%s']";
	
	public String rangeXpath = "//td[contains(@data-ui-meta,'From')]//span";
	
	@FindBy(xpath = "//div[contains(text(),'Other actions')]")
	public ExtWebElement btnOtherActions;
	
	@FindBy(xpath = "//span[text()='Delete TSB & Tribal Knowl...']")
	public ExtWebElement btnDeleteTSB;
	
	@FindBy(xpath = "//button[text()='Continue']")
	public ExtWebElement btnContinue;
	
	@FindBy(xpath = "//textarea[@name='$PpyWorkPage$pDeleteReason']")
	public ExtWebElement txtboxDeleteReason;
	
	@FindBy(xpath = "//div[@data-node-id=\"pyCaseHeader\"]//div[@class=\"content-item content-field item-3\"]/span")
	public ExtWebElement txtStatus;
	
	public ExtWebElement getChElement(ExtWebElement rowElement, String fieldName) {

		String xPath = String.format(".//td[@data-attribute-name='%s']", fieldName);

		return new ExtWebElementImpl(rowElement.findElement(By.xpath(xPath)));
	}
	
	public String getValue(ExtWebElement rowElement, String xPath) {

		String customXPath ="."+xPath;

		return new ExtWebElementImpl(rowElement.findElement(By.xpath(customXPath))).getText().trim();
	}
	
	
	
	
	
		
	
	

	public String evaluateTSB(List<String> listCustomerSymptom, List<String> listTechnicianSymptom) {

		long startTime = System.currentTimeMillis();
		String region = "NNA";
		String model = "L32";
		String year = "2010";
		String engineCode = "VQ35";
		String vIN = "1N4BL2AP6AN487528";
		String stampingNum = vIN.substring(0, 11);
		String chassisNum = vIN.substring(11);
		String outcome = "";
		List<String> selectedFieldNames = new ArrayList<>();
		selectedFieldNames.add("Engine Stalls");
		List<String> selectedSymptoms = new ArrayList<>();
		selectedSymptoms.add("N-to-R");
		selectedSymptoms.add("Engine Cold");
		selectedSymptoms.add("Reverse");
		selectedSymptoms.add("Judder");
		selectedSymptoms.add("Going uphill, heavy throttle");
		selectedSymptoms.add("Excessive Noise");

		EngManagerPage engManagerPage = new EngManagerPage("engmanager", "rules");
		engManagerPage.waitForPageToLoad();
		engManagerPage.getLeftSideMenuElement("TSB & Tribal Knowledge").click();
		engManagerPage.gettSBComponent().dropdownModel.selectByValue(model);
		engManagerPage.gettSBComponent().dropdownYear.select(year);
		WaitUtil.sleep(2000);
		List<ExtWebElement> activeTSBList = engManagerPage.gettSBComponent().listTSBNumbers;
		List<String> evaluatedTSBList = new ArrayList<>();

		// if (!activeTSBList.isEmpty()) {
		if ((!activeTSBList.isEmpty())) {
			ActiveTSB: for (ExtWebElement ele : activeTSBList) {
				engManagerPage.getTabInFocus("TSB & Tribal");
				if (ele.getText().equals("Work queue is empty"))
					continue;

				String tSBNumber = ele.getText();
				// System.out.println(tSBNumber);
				ele.click();
				// ActiveTSBComponent activeTSBComponent = new ActiveTSBComponent(tSBNumber);
				ActiveTSBComponent activeTSBComponent = engManagerPage.getActiveTSBComponent(tSBNumber);
				engManagerPage.getTabInFocus(tSBNumber);
				// validate comp category
				if (activeTSBComponent.txtComponentCategory.getText().trim().equals("CVT")) {
					String regionRadiobutton = String.format("//label[text()='%s']/preceding-sibling::img", region);
					// validate region
					ExtWebElement regionRadioButtonEle = ExtWebComponent.getExtWebElement(regionRadiobutton);
					if (regionRadioButtonEle.getAttribute("alt").equals("true")) {
						// validate model,year and engine code
						activeTSBComponent.tableApplicableModel.moveToElement();
						List<String> modelRows = activeTSBComponent.listApplicableModel.stream()
								.map(excludeEle -> excludeEle.getText()).collect(Collectors.toList());
						// flag to indicate if there is any matching row
						boolean checkVINs = true;
						boolean modelRowsPresent = !(modelRows.get(0).equals("No items"));
						// if (!modelRows.isEmpty()) {
						if (modelRowsPresent) {
							ApplicableModels: for (String modelRow : modelRows) {

								// String rowText = modelRow.getText();
								// System.out.println(rowText);
								String[] columns = modelRow.split("\\n+");
								// compare model number
								String modelName = columns[0].trim();
								if (modelName.startsWith(model)) {
									// compare year
									boolean yearMatch = false;
									String operator = columns[1].trim();
									if (operator.equals("Between")) {
										int from = Integer.parseInt(columns[2].trim());
										int to = Integer.parseInt(columns[4].trim());
										int yr = Integer.parseInt(year);
										if ((from <= yr && yr <= to)) {
											yearMatch = true;
										}
									} else if (operator.equals("Equals")) {
										String from = columns[2].trim();
										if (from.equals(year)) {
											yearMatch = true;
										}
									}
									if (yearMatch) {
										// compare engine code
										String actualEngineCode;
										try {
											actualEngineCode = columns[5].trim();
										} catch (Exception e) {
											actualEngineCode = "";
										}
										if ((actualEngineCode.equals(engineCode) || actualEngineCode.equals(""))) {
											checkVINs = true;
											break ApplicableModels;
										}
									}
								}
								checkVINs = false;
							}
						}
						if (checkVINs) {
							// Applicable VINs validation
							activeTSBComponent.tableApplicableVINs.moveToElement();
							boolean checkEngineCodes = true;
							List<String> vINRows = activeTSBComponent.listApplicableVINs.stream()
									.map(excludeEle -> excludeEle.getText()).collect(Collectors.toList());
							boolean isVINPresent = !(vINRows.get(0).equals("No items"));
							// if (!vINRows.isEmpty()) {
							if (isVINPresent) {
								ApplicableVINs: for (String vINRow : vINRows) {
									// String vInRowText = vINRow.getText();
									// System.out.println(vInRowText);
									String[] columns = vINRow.split("\\n+");
									// compare stamping number
									String actualStampingNum = columns[0].trim();
									if (stampingNum.equals(actualStampingNum)) {
										// compare chassis number
										String operator = columns[1].trim();
										if (operator.equals("Between")) {
											int from = Integer.parseInt(columns[2].trim());
											int to = Integer.parseInt(columns[4].trim());
											int intChassisNum = Integer.parseInt(chassisNum);
											if (from <= intChassisNum && intChassisNum <= to) {
												checkEngineCodes = true;
												break;
											}
										} else if (operator.equals("Equals")) {
											String from = columns[2].trim();
											if (from.equals(chassisNum)) {
												checkEngineCodes = true;
												break ApplicableVINs;
											}
										}
									}
									checkEngineCodes = false;
								}
							}
							if (checkEngineCodes) {
								// Applicable Engine Code validation
								// TODO need to confirm below logic
								activeTSBComponent.tableApplicableEngineCodes.moveToElement();
								boolean checkExclusions = true;
								List<String> engineCodeRows = activeTSBComponent.listApplicableEngCodes.stream()
										.map(excludeEle -> excludeEle.getText()).collect(Collectors.toList());
								boolean isEngCodePresent = !(engineCodeRows.get(0).equals("No items"));
								// if (!engineCodeRows.isEmpty()) {
								if (isEngCodePresent) {
									ApplicableEngineCodes: for (String engineCodeRow : engineCodeRows) {
										// String engineCodeRowText = engineCodeRow;
										if (engineCodeRow.equals(engineCode)) {
											checkExclusions = true;
											break ApplicableEngineCodes;
										}
										checkExclusions = false;
									}
								}
								if (checkExclusions) {
									// validate exclusion
									boolean checkConditions = true;
									activeTSBComponent.headerExclusions.scrollAndClick();
									List<String> exclusionCriterias = activeTSBComponent.listExclusionCriterias.stream()
											.map(excludeEle -> excludeEle.getText()).collect(Collectors.toList());
									boolean isExclusionPresent = !(exclusionCriterias.get(0).equals("No items"));
									// if (!exclusionCriterias.isEmpty()) {
									if (isExclusionPresent) {
										int tSBExclusionsIndex = 0;
										Exclusions: for (String exclusionCriteria : exclusionCriterias) {
											System.out.println("Checking exclusion in : " + exclusionCriteria);
											tSBExclusionsIndex++;
											String exclusionsFieldNamesXpath = String.format(
													"//tr[contains(@id,'TSBExclusions$l%d') and contains(@id,'TSBCondition')]//td[@data-attribute-name='Field Name']//span",
													tSBExclusionsIndex);
											List<String> listExclusionsFieldNames = ExtWebComponent
													.getExtWebElements(exclusionsFieldNamesXpath).stream()
													.map(excludeEle -> excludeEle.getText())
													.collect(Collectors.toList());
											;
											int tSBExclusionConditionIndex = 0;
											Fields: for (String fieldName : listExclusionsFieldNames) {
												System.out.println("Checking exclusion condition in " + fieldName
														+ " under " + exclusionCriteria);
												tSBExclusionConditionIndex++;
												// String exclusionFieldName = fieldName.getText().trim();

												String operatorXpath = String.format(
														"//tr[contains(@id,'TSBExclusions$l%d') and contains(@id,'TSBCondition$l%d')]//td[@data-attribute-name='Operator']//span",
														tSBExclusionsIndex, tSBExclusionConditionIndex);
												String operator = ExtWebComponent.getExtWebElement(operatorXpath)
														.getText();
												String valuesXpath = activeTSBComponent.getExclusionValuesXpath(
														operator, tSBExclusionsIndex, tSBExclusionConditionIndex);
												List<String> listExclusion = ExtWebComponent
														.getExtWebElements(valuesXpath).stream()
														.map(excludeEle -> excludeEle.getText())
														.collect(Collectors.toList());
												ExclusionValues: for (String exclusion : listExclusion) {
													System.out.println(
															"Checking exclusion condition with : " + exclusion);
													boolean excludeTSB = activeTSBComponent
															.evaluateCondition(selectedSymptoms, exclusion);
													if (excludeTSB) {
														checkConditions = false;
														System.out.println(
																"Exiting TSB as exclusion condition matched for : "
																		+ exclusion);
														break Exclusions;
													}

												}

											}
										}

									}
									if (checkConditions) {
										activeTSBComponent.headerConditions.scrollAndClick();
										for (ExtWebElement doesNotApply : activeTSBComponent.listChkboxDoesNotApply) {
											if (doesNotApply.getAttribute("alt").equals("True")) {
												System.out.println("Does not apply is checked, hence exiting TSB");
												engManagerPage.closeTab(tSBNumber);
												continue ActiveTSB;
											}

										}
										List<String> listConditionsCriterias = activeTSBComponent.listTSBConditions
												.stream().map(excludeEle -> excludeEle.getText())
												.collect(Collectors.toList());
										boolean isConditionPresent = !(listConditionsCriterias.get(0)
												.equals("No items"));
										// if (!listConditionsCriterias.isEmpty()) {
										if (isConditionPresent) {

											int tsbConditionsIndex = 0;
											Conditions: for (String conditionsCriteria : listConditionsCriterias) {
												tsbConditionsIndex++;
												boolean skipToNextCondition = false;
												System.out.println("Checking including in " + conditionsCriteria);
												String conditionsFieldNamesXpath = String.format(
														"//tr[contains(@id,'TSBCriteria$l%d') and contains(@id,'TSBCondition')]//td[@data-attribute-name='Field Name']//span",
														tsbConditionsIndex);
												List<String> listConditionsFieldNames = ExtWebComponent
														.getExtWebElements(conditionsFieldNamesXpath).stream()
														.map(excludeEle -> excludeEle.getText())
														.collect(Collectors.toList());

												int conditionsConditionIndex = 0;
												Fields: for (String fieldName : listConditionsFieldNames) {
													conditionsConditionIndex++;
													System.out.println("Checking including in " + fieldName + " under "
															+ conditionsCriteria);
													// String conditionsFieldName = fieldName.getText().trim();
													String conditionsOperatorXpath = String.format(
															"//tr[contains(@id,'TSBCriteria$l%d') and contains(@id,'TSBCondition$l%d')]//td[@data-attribute-name='Operator']//span",
															tsbConditionsIndex, conditionsConditionIndex);
													String conditionsOperator = ExtWebComponent
															.getExtWebElement(conditionsOperatorXpath).getText();
													String conditionsValuesXpath = activeTSBComponent
															.getConditionValuesXpath(conditionsOperator,
																	tsbConditionsIndex, conditionsConditionIndex);
													List<String> listConditions = ExtWebComponent
															.getExtWebElements(conditionsValuesXpath).stream()
															.map(excludeEle -> excludeEle.getText())
															.collect(Collectors.toList());
													boolean skipToNextField = false;

													CondtionValues: for (String condition : listConditions) {
														System.out.println(
																"Checking inclusion condition with : " + condition);
														skipToNextField = activeTSBComponent
																.evaluateCondition(selectedSymptoms, condition);
														if (skipToNextField) {
															System.out.println(
																	"Conditions match found for : " + condition);
															break CondtionValues;
														}

													}

													if (!skipToNextField) {
														skipToNextCondition = true;
														break Fields;
													}

												}

												if (!skipToNextCondition) {
													evaluatedTSBList.add(tSBNumber);
													if (evaluatedTSBList.size() == 1) {
														// TODO add code for capturing questions and repair

														activeTSBComponent.headerRepairs.scrollAndClick();
														activeTSBComponent.getRepairMap()
																.forEach((k, v) -> System.out.println("Repair title is "
																		+ k + "repair description is " + v));

													}
												}

											}
										}
									}
								}
							}
						}
					}
				}
				engManagerPage.closeTab(tSBNumber);
			}
			if (evaluatedTSBList.isEmpty()) {
				System.out.println("No TSBs evaluated");
				outcome = "No TSBs evaluated";
			} else {
				evaluatedTSBList.forEach(ele -> System.out.println(ele));
			}

		} else {
			System.out.println("No TSBs found");
			outcome = "No TSBs evaluated";
		}

		long endTime = System.currentTimeMillis();
		// System.out.println("elapsed time : " + (endTime - startTime) / 1000 +
		// "secs.");
		return outcome;
	}

	public Map<String, String> getRepairMap() {
		Map<String, String> repairMap = new HashMap<>();

		for (int i = 0; i < listRepairTitles.size(); i++) {
			repairMap.put(listRepairTitles.get(i).getText(), listRepairDesc.get(i).getText());
		}

		return repairMap;

	}

	public boolean evaluateCondition(List<String> itemsList, String item) {
		boolean retVal = false;
		if (itemsList.indexOf(item) != -1) {
			
			retVal = true;
		}

		return retVal;
	}

	public boolean evaluateCondition(Set<String> itemsList, String item) {

		return itemsList.contains(item);

	}

	public String getExclusionValuesXpath(String operator, int fieldIndex, int conditionIndex) {

		String xPath = "";

		if (operator.equals("Includes")) {
			xPath = String.format(
					"//tr[contains(@id,'TSBExclusions$l%d$pTSBCondition$l%d$pTSBMultiselect')]//img[@alt='True']/ancestor::td[contains(@data-ui-meta,'Checkbox')]/following-sibling::td//span",
					fieldIndex, conditionIndex);
		} else {
			xPath = String.format(
					"//tr[contains(@id,'TSBExclusions$l%d$pTSBCondition$l%d$pTSBMultiselect')]//img[@alt='False']/ancestor::td[contains(@data-ui-meta,'Checkbox')]/following-sibling::td//span",
					fieldIndex, conditionIndex);
		}

		return xPath;
	}
	
	public String getValuesXpath(String operator) {

		String xPath = "";

		if (operator.equals("Includes")) {
			xPath =".//tr[contains(@id,'TSBMultiselect')]//img[@alt='True']/ancestor::td[contains(@data-ui-meta,'Checkbox')]/following-sibling::td//span";
		} else {
			xPath = ".//tr[contains(@id,'TSBMultiselect')]//img[@alt='False']/ancestor::td[contains(@data-ui-meta,'Checkbox')]/following-sibling::td//span";
		}

		return xPath;
	}

	public String getConditionValuesXpath(String operator, int fieldIndex, int conditionIndex) {

		String xPath = "";

		if (operator.equals("Includes")) {
			xPath = String.format(
					"//tr[contains(@id,'TSBCriteria$l%d$pTSBCondition$l%d$pTSBMultiselect')]//img[@alt='True']/ancestor::td[contains(@data-ui-meta,'Checkbox')]/following-sibling::td//span",
					fieldIndex, conditionIndex);
		} else {
			xPath = String.format(
					"//tr[contains(@id,'TSBCriteria$l%d$pTSBCondition$l%d$pTSBMultiselect')]//img[@alt='False']/ancestor::td[contains(@data-ui-meta,'Checkbox')]/following-sibling::td//span",
					fieldIndex, conditionIndex);
		}

		return xPath;
	}

	public void waitForComponentToLoad(String iframe) {
		try {
			
			DriverManager.getDriver().switchTo().defaultContent();
			waitForFrameToLoad(getIFrame(iframe));
			/*DriverManager.getDriver().switchTo().defaultContent();
			DriverManager.getDriver().switchTo().frame(getIFrame(iframe));
			headerTSB.waitForPresent();
			headerTSB.waitForVisible();*/
		//	ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}
	}

	public ActiveTSBComponent(String iframe) {

		waitForComponentToLoad(iframe);

	}

	public ActiveTSBComponent() {
		// TODO Auto-generated constructor stub
	}

}
