package com.nissan.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.DateUtil;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.databeans.QuestionsDataBean;
import com.nissan.databeans.TSBDataBean;
import com.nissan.enums.Roles;
import com.nissan.pages.EngManagerPage;
import com.nissan.pages.components.ActiveTSBComponent;
import com.nissan.pages.components.TSBTableComponent;

public class TSBUtil {

	private TSBUtil() {

	}

	private static final Log logger = LogFactory.getLog(TSBUtil.class);

	// public static String evaluateTSB
	public static TSBDataBean evaluateTSB(List<String> listCustomerSymptom, List<String> listTechnicianSymptom) {

		long startTime = System.currentTimeMillis();
		String region = "NNA";
		// DVehicleReferenceDataPage.getVehicleReferenceMap().forEach((k, v) ->
		// logger.info(k + ":" + v));
		String model = "L32";
		String year = "2010";
		String engineCode = "VQ35";
		String vIN = "1N4BL2AP6AN487528";
		String stampingNum = vIN.substring(0, 11);
		String chassisNum = vIN.substring(11);
		List<String> evaluatedTSBList = new ArrayList<>();
		TSBDataBean tsbData = new TSBDataBean();

		EngManagerPage engManagerPage = new EngManagerPage();
		engManagerPage.waitForPageToLoad();
		engManagerPage.getLeftSideMenuElement("TSB & Tribal Knowledge").click();
		engManagerPage.gettSBComponent().dropdownModel.selectByValue(model);
		engManagerPage.gettSBComponent().dropdownYear.select(year);
		WaitUtil.sleep(2000);

		// below: newly added code for pagination
		// total number of pages with list of TSBs
		TSBTableComponent table = new TSBTableComponent();
		int totalPages = table.getPaginationSize();
		logger.info("Total no. of pages: " + totalPages);
		int currentpage = 1;
		while (currentpage <= totalPages) {
			// engManagerPage.getTabInFocus("TSB & Tribal");
			// TSBTableComponent table2 = new TSBTableComponent();
			if ((currentpage != 1 && currentpage <= totalPages && totalPages > 1)) {
				// WaitUtil.sleep(5000);
				table.clickNextLink();
				WaitUtil.sleep(2000);
				logger.info("Clicking on page:" + String.valueOf(currentpage));
				TSBTableComponent table2 = new TSBTableComponent();
				table = table2;
			}

			// List<ExtWebElement> activeTSBList =
			// engManagerPage.gettSBComponent().getActiveTSBsList();
			List<ExtWebElement> activeTSBList = table.getActiveTSBsList();
			// above: newly added code for pagination
			if ((!activeTSBList.isEmpty())) {
				ActiveTSB: for (ExtWebElement ele : activeTSBList) {
					// ele.waitForElementToBeCLickable(20000);
					String tSBNumber = ele.getText();

					if (tSBNumber.equals("Work queue is empty"))
						continue;
					logger.info("Evaluating TSB: " + tSBNumber);

					// WaitUtil.sleep(2000);
					ele.waitForElementToBeClickable(5000);
					ele.click();
					ActiveTSBComponent activeTSBComponent = new ActiveTSBComponent(tSBNumber);
					// engManagerPage.getTabInFocus(tSBNumber);
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
									// logger.info(rowText);
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
										// logger.info(vInRowText);
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
										List<String> exclusionCriterias = activeTSBComponent.listExclusionCriterias
												.stream().map(excludeEle -> excludeEle.getText())
												.collect(Collectors.toList());
										boolean isExclusionPresent = !(exclusionCriterias.get(0).equals("No items"));
										// if (!exclusionCriterias.isEmpty()) {
										if (isExclusionPresent) {
											int tSBExclusionsIndex = 0;
											Exclusions: for (String exclusionCriteria : exclusionCriterias) {
												logger.info("Checking exclusion in : " + exclusionCriteria);
												boolean skipToNextExclusion = false;
												tSBExclusionsIndex++;
												List<List<String>> selectedSymptoms = new ArrayList<>();
												String applicableFor = ExtWebComponent.getExtWebElement(String.format(
														"//tr[contains(@id,'TSBExclusions$l%d')]//td[@class='dataValueRead']//span[text()]",
														tSBExclusionsIndex)).getText();
												if (applicableFor.equals("Both")) {
													selectedSymptoms.add(listCustomerSymptom);
													selectedSymptoms.add(listTechnicianSymptom);

												} else if (applicableFor.equals("Customer")) {
													selectedSymptoms.add(listCustomerSymptom);
												} else if (applicableFor.equals("Technician")) {
													selectedSymptoms.add(listTechnicianSymptom);
												}
												String exclusionsFieldNamesXpath = String.format(
														"//tr[contains(@id,'TSBExclusions$l%d') and contains(@id,'TSBCondition')]//td[@data-attribute-name='Field Name']//span",
														tSBExclusionsIndex);
												List<String> listExclusionsFieldNames = ExtWebComponent
														.getExtWebElements(exclusionsFieldNamesXpath).stream()
														.map(excludeEle -> excludeEle.getText())
														.collect(Collectors.toList());

												boolean excludeTSB = false;
												SelectedSymptoms: for (List<String> selectedSymptom : selectedSymptoms) {
													skipToNextExclusion = false;
													int tSBExclusionConditionIndex = 0;
													Fields: for (String fieldName : listExclusionsFieldNames) {
														logger.info("Checking exclusion condition in " + fieldName
																+ " under " + exclusionCriteria);
														tSBExclusionConditionIndex++;
														if (fieldName.equalsIgnoreCase("DTC")) {
															logger.info("Skipping exclusion for DTC");
															continue;
														}
														// String exclusionFieldName = fieldName.getText().trim();

														String operatorXpath = String.format(
																"//tr[contains(@id,'TSBExclusions$l%d') and contains(@id,'TSBCondition$l%d')]//td[@data-attribute-name='Operator']//span",
																tSBExclusionsIndex, tSBExclusionConditionIndex);
														String operator = ExtWebComponent
																.getExtWebElement(operatorXpath).getText();
														String valuesXpath = activeTSBComponent.getExclusionValuesXpath(
																operator, tSBExclusionsIndex,
																tSBExclusionConditionIndex);
														List<String> listExclusion = ExtWebComponent
																.getExtWebElements(valuesXpath).stream()
																.map(excludeEle -> excludeEle.getText())
																.collect(Collectors.toList());
														boolean skipToNextField = false;
														ExclusionValues: for (String exclusion : listExclusion) {
															logger.info(
																	"Checking exclusion condition with : " + exclusion);
															/*
															 * excludeTSB = activeTSBComponent
															 * .evaluateCondition(selectedSymptom, exclusion);
															 */

															skipToNextField = activeTSBComponent
																	.evaluateCondition(selectedSymptom, exclusion);

															if (skipToNextField) {

																logger.info(
																		"Conditions match found for : " + exclusion);
																break ExclusionValues;
															}
														}
														if (!skipToNextField) {

															skipToNextExclusion = true;
															continue SelectedSymptoms;
														}

													}
													if (!skipToNextExclusion) {
														checkConditions = false;
														logger.info("Exiting TSB as exclusion condition matched for : "
																+ "Exclusion Criteria" + tSBExclusionsIndex);
														break Exclusions;
													}
												}

											}

										}
										if (checkConditions) {
											activeTSBComponent.headerConditions.scrollAndClick();

											List<String> listConditionsCriterias = activeTSBComponent.listTSBConditions
													.stream().map(excludeEle -> excludeEle.getText())
													.collect(Collectors.toList());
											boolean isConditionPresent = !(listConditionsCriterias.get(0)
													.equals("No items"));
											// if (!listConditionsCriterias.isEmpty()) {
											if (isConditionPresent) {
												for (ExtWebElement doesNotApply : activeTSBComponent.listChkboxDoesNotApply) {
													if (doesNotApply.getAttribute("alt").equals("True")) {
														logger.info("Does not apply is checked, hence exiting TSB");
														engManagerPage.closeTab(tSBNumber);
														engManagerPage.getTabInFocus("TSB & Tribal");
														continue ActiveTSB;
													}
												}

												int tsbConditionsIndex = 0;
												Conditions: for (String conditionsCriteria : listConditionsCriterias) {
													tsbConditionsIndex++;

													boolean skipToNextCondition = false;
													// logger.info("Checking including in " +
													// conditionsCriteria);
													List<List<String>> selectedSymptoms = new ArrayList<>();
													String applicableFor = ExtWebComponent.getExtWebElement(String
															.format("//tr[contains(@id,'TSBCriteria$l%d')]//td[@class='dataValueRead']//span[text()]",
																	tsbConditionsIndex))
															.getText();
													if (applicableFor.equals("Both")) {
														selectedSymptoms.add(listCustomerSymptom);
														selectedSymptoms.add(listTechnicianSymptom);

													} else if (applicableFor.equals("Customer")) {
														selectedSymptoms.add(listCustomerSymptom);
													} else if (applicableFor.equals("Technician")) {
														selectedSymptoms.add(listTechnicianSymptom);
													}
													String conditionsFieldNamesXpath = String.format(
															"//tr[contains(@id,'TSBCriteria$l%d') and contains(@id,'TSBCondition')]//td[@data-attribute-name='Field Name']//span",
															tsbConditionsIndex);
													List<String> listConditionsFieldNames = ExtWebComponent
															.getExtWebElements(conditionsFieldNamesXpath).stream()
															.map(excludeEle -> excludeEle.getText())
															.collect(Collectors.toList());

													SelectedSymptoms: for (List<String> selectedSymptom : selectedSymptoms) {
														skipToNextCondition = false;
														int conditionsConditionIndex = 0;

														Fields: for (String fieldName : listConditionsFieldNames) {

															conditionsConditionIndex++;
															logger.info("Checking " + fieldName + " under "
																	+ conditionsCriteria);
															// String conditionsFieldName = fieldName.getText().trim();
															String conditionsOperatorXpath = String.format(
																	"//tr[contains(@id,'TSBCriteria$l%d') and contains(@id,'TSBCondition$l%d')]//td[@data-attribute-name='Operator']//span",
																	tsbConditionsIndex, conditionsConditionIndex);
															String conditionsOperator = ExtWebComponent
																	.getExtWebElement(conditionsOperatorXpath)
																	.getText();
															String conditionsValuesXpath = activeTSBComponent
																	.getConditionValuesXpath(conditionsOperator,
																			tsbConditionsIndex,
																			conditionsConditionIndex);
															List<String> listConditions = ExtWebComponent
																	.getExtWebElements(conditionsValuesXpath).stream()
																	.map(excludeEle -> excludeEle.getText())
																	.collect(Collectors.toList());
															boolean skipToNextField = false;

															CondtionValues: for (String condition : listConditions) {
																logger.info("Checking includes condition for : "
																		+ condition);
																skipToNextField = activeTSBComponent
																		.evaluateCondition(selectedSymptom, condition);
																if (skipToNextField) {
																	logger.info("Conditions match found for : "
																			+ condition);
																	break CondtionValues;
																} else {
																	logger.info("Conditions match not found for : "
																			+ condition);
																}
															}

															if (!skipToNextField) {
																skipToNextCondition = true;
																continue SelectedSymptoms;
															}

														}

														if (!skipToNextCondition) {
															break SelectedSymptoms;
														}

														// skipToNextCondition = true;

													}

													if (!skipToNextCondition) {
														evaluatedTSBList.add(tSBNumber);
														if (evaluatedTSBList.size() == 1) {
															// TODO add code for capturing questions and repair
															activeTSBComponent.headerQuestions.scrollAndClick();
															List<String> listQuestions = activeTSBComponent.listQuestions
																	.stream().map(excludeEle -> excludeEle.getText())
																	.collect(Collectors.toList());
															boolean isQuestionPresent = !(listQuestions.get(0)
																	.equals("No items"));
															if (isQuestionPresent) {
																int questionIndex = 0;
																List<QuestionsDataBean> questions = new ArrayList<>();
																Question: for (String question : listQuestions) {
																	questionIndex++;
																	logger.info("Question " + questionIndex + " data:");
																	QuestionsDataBean questionData = new QuestionsDataBean();
																	questionData.setQuestionType(ExtWebComponent
																			.getExtWebElement(String.format(
																					activeTSBComponent.txtQuestionType,
																					questionIndex))
																			.getText().trim().replace(":", ""));
																	logger.info("Question type : "
																			+ questionData.getQuestionType());
																	questionData.setQuestionText(ExtWebComponent
																			.getExtWebElement(String.format(
																					activeTSBComponent.txtQuestionText,
																					questionIndex))
																			.getText().trim());
																	logger.info("Question text : "
																			+ questionData.getQuestionText());
																	List<ExtWebElement> actions = ExtWebComponent
																			.getExtWebElements(String.format(
																					activeTSBComponent.listActions,
																					questionIndex));
																	int actionIndex = 0;
																	Actions: for (ExtWebElement action : actions) {
																		actionIndex++;
																		Map<String, String> optionsMap = new HashMap<>();
																		// String optionsXpath =
																		// String.format(activeTSBComponent.listOptions,questionIndex,actionIndex);
																		String optionsXpath = String.format(
																				activeTSBComponent.listOptions,
																				questionIndex, actionIndex) + "/span";
																		String valuesXpath = String.format(
																				activeTSBComponent.listOptions,
																				questionIndex, actionIndex)
																				+ "/div/span";
																		List<String> options = ExtWebComponent
																				.getExtWebElements(optionsXpath)
																				.stream()
																				.map(excludeEle -> excludeEle.getText()
																						.trim())
																				.collect(Collectors.toList());
																		List<String> values = ExtWebComponent
																				.getExtWebElements(valuesXpath).stream()
																				.map(excludeEle -> excludeEle.getText()
																						.trim())
																				.collect(Collectors.toList());
																		/*
																		 * for (String option : options) { String
																		 * keyXpath = String.format(
																		 * activeTSBComponent.listOptions,
																		 * questionIndex, actionIndex) + "/span";
																		 * //String valueXpath =
																		 * 
																		 * String key = option;
																		 * 
																		 * String key = ExtWebComponent
																		 * .getExtWebElement(keyXpath).getText(); String
																		 * value = values.iterator().next();
																		 * logger.info("key is : "+key+"Value : "
																		 * +value); optionsMap.put(key, value); }
																		 */

																		Iterator<String> optionsIterator = options
																				.iterator();
																		Iterator<String> valuesIterator = values
																				.iterator();

																		while (optionsIterator.hasNext()
																				&& valuesIterator.hasNext()) {

																			optionsMap.put(optionsIterator.next(),
																					valuesIterator.next());

																		}

																		/*
																		 * if(optionsMap.get("Option").equals("Yes")) {
																		 * questionData.setOptionYes(optionsMap); }else
																		 * if(optionsMap.get("Option").equals("No")) {
																		 * questionData.setOptionNo(optionsMap); }else {
																		 * questionData.setOptionInputResponse(
																		 * optionsMap); }
																		 */

																		questionData.setOptionMap(optionsMap);
																		optionsMap.forEach((k, v) -> logger.info(
																				"Key is " + k + " value is " + v));
																	}
																	questions.add(questionData);
																}

																tsbData.setQuestionsDataBean(questions);

															}
															activeTSBComponent.headerRepairs.scrollAndClick();
															tsbData.setDefaultRepair(
																	activeTSBComponent.txtDefaultRepair.getText());
															tsbData.setRepairMap(activeTSBComponent.getRepairMap());
															tsbData.getRepairMap()
																	.forEach((k, v) -> logger.info("Repair title is "
																			+ k + "repair description is " + v));

														}
														break Conditions;
													} else {
														logger.info("exiting TSB as condition did not match");
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
					engManagerPage.getTabInFocus("TSB & Tribal");
					// WaitUtil.sleep(2000);
				}

				if (evaluatedTSBList.isEmpty()) {
					logger.info("No TSBs evaluated");

				} else {
					evaluatedTSBList.forEach(System.out::println);
				}

			} else {
				logger.info("No TSBs found");

			}
			// below: newly added code for pagination

			currentpage++;

		} // above: newly added code for pagination
		long endTime = System.currentTimeMillis();
		logger.info("elapsed time : " + (endTime - startTime) / 1000 + "secs.");
		// engManagerPage.logOut();
		engManagerPage.gettSBComponent().buttonClear.scrollAndClick();
		WaitUtil.sleep(2000);
		engManagerPage.closeTab("TSB & Tribal");
		tsbData.setListTSB(evaluatedTSBList);

		if (tsbData.getListTSB().size() != 1) {
			// fetch PCC questions;

		}
		return tsbData;
	}

	// TODO Parameterize region, currently default value is NNA
	public static TSBDataBean evaluateTSB(List<String> listCustomerSymptom, List<String> listTechnicianSymptom,
			Map<String, String> vehicleRefMap, boolean loginRequired, boolean logoutRequired) {

		long startTime = System.currentTimeMillis();
		String region = "NNA";
		String model = vehicleRefMap.get("ModelLineCode");
		String year = String.valueOf(
				DateUtil.getCalendarDate(DateUtil.parseDate(vehicleRefMap.get("ManufacturedDate"), "yyyy-MM-dd"))
						.get(Calendar.YEAR));
		String engineCode = vehicleRefMap.get("EngineModelCode");
		String vIN = vehicleRefMap.get("VIN");

		String stampingNum = vIN.substring(0, 11);
		String chassisNum = vIN.substring(11);
		List<String> evaluatedTSBList = new ArrayList<>();
		TSBDataBean tsbData = new TSBDataBean();

		EngManagerPage engManagerPage = loginRequired ? new EngManagerPage(Roles.ENGINEERINGMANAGER)
				: new EngManagerPage();
		engManagerPage.waitForPageToLoad();
		engManagerPage.getLeftSideMenuElement("TSB & Tribal Knowledge").click();
		engManagerPage.gettSBComponent().dropdownModel.selectByValue(model);
		engManagerPage.gettSBComponent().dropdownYear.select(year);
		WaitUtil.sleep(2000);

		// below: newly added code for pagination
		// total number of pages with list of TSBs
		TSBTableComponent table = new TSBTableComponent();
		int totalPages = table.getPaginationSize();
		logger.info("Total no. of pages: " + totalPages);
		int currentpage = 1;
		while (currentpage <= totalPages) {

			if ((currentpage != 1 && currentpage <= totalPages && totalPages > 1)) {

				table.clickNextLink();
				WaitUtil.sleep(2000);
				// logger.info("Clicking on page:" + String.valueOf(currentpage));
				TSBTableComponent table2 = new TSBTableComponent();
				table = table2;
			}

			List<ExtWebElement> activeTSBList = table.getActiveTSBsList();
			// above: newly added code for pagination
			if ((!activeTSBList.isEmpty())) {
				ActiveTSB: for (ExtWebElement ele : activeTSBList) {
					boolean checkVINs = true;
					String tSBNumber = ele.getText();
					if (tSBNumber.equals("Work queue is empty"))
						continue ActiveTSB;
					logger.info("Evaluating TSB: " + tSBNumber);

					ele.waitForElementToBeClickable(5000);
					ele.click();
					ActiveTSBComponent activeTSBComponent = new ActiveTSBComponent(tSBNumber);
					if (compareComponent(activeTSBComponent.txtComponentCategory.getText().trim(), "CVT")) {
						// validate region
						if (isRegionSelected(region)) {
							// validate model,year and engine code
							activeTSBComponent.tableApplicableModel.moveToElement();
							List<ExtWebElement> modelRows = (activeTSBComponent.listApplicableModel);
							if (isListEmpty(getListOfString(modelRows), "No items")) {
								ApplicableModels: for (ExtWebElement modelRow : modelRows) {

									String modelXpath = "//td[@data-attribute-name='Model']";
									// logger.info(activeTSBComponent.getChildElement(modelRow, "Model").getText());
									// logger.info(activeTSBComponent.getChildElement(modelRow,
									// "Operator").getText());

									// compare model number
									String modelName = ExtWebComponent
											.getChildElement(modelRow, activeTSBComponent.fieldsXpath, "Model")
											.getText();
									if (modelName.startsWith(model)) {
										// compare year
										boolean yearMatch = isMatch(modelRow, year);
										if (yearMatch) {
											// compare engine code
											String actualEngineCode;
											try {
												actualEngineCode = ExtWebComponent.getChildElement(modelRow,
														activeTSBComponent.fieldsXpath, "Engine Code").getText();
											} catch (Exception e) {
												actualEngineCode = "";
											}
											if (isEqualOrBlank(actualEngineCode, engineCode)) {
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

								List<ExtWebElement> vINRows = activeTSBComponent.listApplicableVINs;
								boolean checkEngineCodes = true;
								if (isListEmpty(getListOfString(vINRows), "No items")) {
									ApplicableVINs: for (ExtWebElement vINRow : vINRows) {

										// String[] columns = vINRow.split("\\n+");
										// compare stamping number
										String actualStampingNum = ExtWebComponent.getChildElement(vINRow,
												activeTSBComponent.fieldsXpath, "Stamping Number").getText();
										if (stampingNum.equals(actualStampingNum)) {
											// compare chassis number
											checkEngineCodes = isMatch(vINRow, chassisNum);
											if (checkEngineCodes) {
												break ApplicableVINs;
											}
										}

									}
								}
								if (checkEngineCodes) {
									// TODO need to confirm below logic
									activeTSBComponent.tableApplicableEngineCodes.moveToElement();
									boolean checkExclusions = true;
									List<String> engineCodeRows = getListOfString(
											activeTSBComponent.listApplicableEngCodes);
									if (isListEmpty(engineCodeRows, "No items")) {
										ApplicableEngineCodes: for (String engineCodeRow : engineCodeRows) {
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
										List<String> exclusionCriterias = activeTSBComponent.listExclusionCriterias
												.stream().map(excludeEle -> excludeEle.getText())
												.collect(Collectors.toList());
										boolean isExclusionPresent = !(exclusionCriterias.get(0).equals("No items"));
										if (isExclusionPresent) {
											int tSBExclusionsIndex = 0;
											Exclusions: for (String exclusionCriteria : exclusionCriterias) {
												logger.info("Checking exclusion in : " + exclusionCriteria);
												boolean skipToNextExclusion = false;
												tSBExclusionsIndex++;
												List<List<String>> selectedSymptoms = new ArrayList<>();
												String applicableFor = ExtWebComponent.getExtWebElement(String.format(
														"//tr[contains(@id,'TSBExclusions$l%d')]//td[@class='dataValueRead']//span[text()]",
														tSBExclusionsIndex)).getText();
												if (applicableFor.equals("Both")) {
													selectedSymptoms.add(listCustomerSymptom);
													selectedSymptoms.add(listTechnicianSymptom);

												} else if (applicableFor.equals("Customer")) {
													selectedSymptoms.add(listCustomerSymptom);
												} else if (applicableFor.equals("Technician")) {
													selectedSymptoms.add(listTechnicianSymptom);
												}

												String exclusionsFieldNamesXpath = String.format(
														"//tr[contains(@id,'TSBExclusions$l%d') and contains(@id,'TSBCondition')]//td[@data-attribute-name='Field Name']//span",
														tSBExclusionsIndex);
												List<String> listExclusionsFieldNames = ExtWebComponent
														.getExtWebElements(exclusionsFieldNamesXpath).stream()
														.map(excludeEle -> excludeEle.getText())
														.collect(Collectors.toList());

												boolean excludeTSB = false;
												SelectedSymptoms: for (List<String> selectedSymptom : selectedSymptoms) {
													skipToNextExclusion = false;
													int tSBExclusionConditionIndex = 0;
													Fields: for (String fieldName : listExclusionsFieldNames) {
														logger.info("Checking exclusion condition in " + fieldName
																+ " under " + exclusionCriteria);
														tSBExclusionConditionIndex++;
														if (fieldName.equalsIgnoreCase("DTC")) {
															logger.info("Skipping exclusion for DTC");
															continue;
														}
														// String exclusionFieldName = fieldName.getText().trim();

														String operatorXpath = String.format(
																"//tr[contains(@id,'TSBExclusions$l%d') and contains(@id,'TSBCondition$l%d')]//td[@data-attribute-name='Operator']//span",
																tSBExclusionsIndex, tSBExclusionConditionIndex);
														String operator = ExtWebComponent
																.getExtWebElement(operatorXpath).getText();
														String valuesXpath = activeTSBComponent.getExclusionValuesXpath(
																operator, tSBExclusionsIndex,
																tSBExclusionConditionIndex);
														List<String> listExclusion = ExtWebComponent
																.getExtWebElements(valuesXpath).stream()
																.map(excludeEle -> excludeEle.getText())
																.collect(Collectors.toList());
														boolean skipToNextField = false;
														ExclusionValues: for (String exclusion : listExclusion) {
															logger.info(
																	"Checking exclusion condition with : " + exclusion);
															/*
															 * excludeTSB = activeTSBComponent
															 * .evaluateCondition(selectedSymptom, exclusion);
															 */

															skipToNextField = activeTSBComponent
																	.evaluateCondition(selectedSymptom, exclusion);

															if (skipToNextField) {

																logger.info(
																		"Conditions match found for : " + exclusion);
																break ExclusionValues;
															}
														}
														if (!skipToNextField) {

															skipToNextExclusion = true;
															continue SelectedSymptoms;
														}

													}
													if (!skipToNextExclusion) {
														checkConditions = false;
														logger.info("Exiting TSB as exclusion condition matched for : "
																+ "Exclusion Criteria" + tSBExclusionsIndex);
														break Exclusions;
													}
												}

											}

										}
										if (checkConditions) {
											activeTSBComponent.headerConditions.scrollAndClick();

											List<String> listConditionsCriterias = activeTSBComponent.listTSBConditions
													.stream().map(excludeEle -> excludeEle.getText())
													.collect(Collectors.toList());
											boolean isConditionPresent = !(listConditionsCriterias.get(0)
													.equals("No items"));
											// if (!listConditionsCriterias.isEmpty()) {
											if (isConditionPresent) {
												for (ExtWebElement doesNotApply : activeTSBComponent.listChkboxDoesNotApply) {
													if (doesNotApply.getAttribute("alt").equals("True")) {
														logger.info("Does not apply is checked, hence exiting TSB");
														engManagerPage.closeTab(tSBNumber);
														engManagerPage.getTabInFocus("TSB & Tribal");
														continue ActiveTSB;
													}
												}

												int tsbConditionsIndex = 0;
												Conditions: for (String conditionsCriteria : listConditionsCriterias) {
													tsbConditionsIndex++;

													boolean skipToNextCondition = false;
													// logger.info("Checking including in " +
													// conditionsCriteria);
													List<List<String>> selectedSymptoms = new ArrayList<>();
													String applicableFor = ExtWebComponent.getExtWebElement(String
															.format("//tr[contains(@id,'TSBCriteria$l%d')]//td[@class='dataValueRead']//span[text()]",
																	tsbConditionsIndex))
															.getText();
													if (applicableFor.equals("Both")) {
														selectedSymptoms.add(listCustomerSymptom);
														selectedSymptoms.add(listTechnicianSymptom);

													} else if (applicableFor.equals("Customer")) {
														selectedSymptoms.add(listCustomerSymptom);
													} else if (applicableFor.equals("Technician")) {
														selectedSymptoms.add(listTechnicianSymptom);
													}
													String conditionsFieldNamesXpath = String.format(
															"//tr[contains(@id,'TSBCriteria$l%d') and contains(@id,'TSBCondition')]//td[@data-attribute-name='Field Name']//span",
															tsbConditionsIndex);
													List<String> listConditionsFieldNames = ExtWebComponent
															.getExtWebElements(conditionsFieldNamesXpath).stream()
															.map(excludeEle -> excludeEle.getText())
															.collect(Collectors.toList());

													SelectedSymptoms: for (List<String> selectedSymptom : selectedSymptoms) {
														skipToNextCondition = false;
														int conditionsConditionIndex = 0;

														Fields: for (String fieldName : listConditionsFieldNames) {

															conditionsConditionIndex++;
															logger.info("Checking " + fieldName + " under "
																	+ conditionsCriteria);
															// String conditionsFieldName = fieldName.getText().trim();
															String conditionsOperatorXpath = String.format(
																	"//tr[contains(@id,'TSBCriteria$l%d') and contains(@id,'TSBCondition$l%d')]//td[@data-attribute-name='Operator']//span",
																	tsbConditionsIndex, conditionsConditionIndex);
															String conditionsOperator = ExtWebComponent
																	.getExtWebElement(conditionsOperatorXpath)
																	.getText();
															String conditionsValuesXpath = activeTSBComponent
																	.getConditionValuesXpath(conditionsOperator,
																			tsbConditionsIndex,
																			conditionsConditionIndex);
															List<String> listConditions = ExtWebComponent
																	.getExtWebElements(conditionsValuesXpath).stream()
																	.map(excludeEle -> excludeEle.getText())
																	.collect(Collectors.toList());
															boolean skipToNextField = false;

															CondtionValues: for (String condition : listConditions) {
																logger.info("Checking includes condition for : "
																		+ condition);
																skipToNextField = activeTSBComponent
																		.evaluateCondition(selectedSymptom, condition);
																if (skipToNextField) {
																	logger.info("Conditions match found for : "
																			+ condition);
																	break CondtionValues;
																} else {
																	logger.info("Conditions match not found for : "
																			+ condition);
																}
															}

															if (!skipToNextField) {
																skipToNextCondition = true;
																continue SelectedSymptoms;
															}

														}

														if (!skipToNextCondition) {
															break SelectedSymptoms;
														}

														// skipToNextCondition = true;

													}

													if (!skipToNextCondition) {
														evaluatedTSBList.add(tSBNumber);
														if (evaluatedTSBList.size() == 1) {
															// TODO add code for capturing questions and repair
															activeTSBComponent.headerQuestions.scrollAndClick();
															List<String> listQuestions = activeTSBComponent.listQuestions
																	.stream().map(excludeEle -> excludeEle.getText())
																	.collect(Collectors.toList());
															boolean isQuestionPresent = !(listQuestions.get(0)
																	.equals("No items"));
															if (isQuestionPresent) {
																int questionIndex = 0;
																List<QuestionsDataBean> questions = new ArrayList<>();
																Question: for (String question : listQuestions) {
																	questionIndex++;
																	logger.info("Question " + questionIndex + " data:");
																	QuestionsDataBean questionData = new QuestionsDataBean();
																	questionData.setQuestionType(ExtWebComponent
																			.getExtWebElement(String.format(
																					activeTSBComponent.txtQuestionType,
																					questionIndex))
																			.getText().trim().replace(":", ""));
																	logger.info("Question type : "
																			+ questionData.getQuestionType());
																	questionData.setQuestionText(ExtWebComponent
																			.getExtWebElement(String.format(
																					activeTSBComponent.txtQuestionText,
																					questionIndex))
																			.getText().trim());
																	logger.info("Question text : "
																			+ questionData.getQuestionText());
																	List<ExtWebElement> actions = ExtWebComponent
																			.getExtWebElements(String.format(
																					activeTSBComponent.listActions,
																					questionIndex));
																	int actionIndex = 0;
																	Actions: for (ExtWebElement action : actions) {
																		actionIndex++;
																		Map<String, String> optionsMap = new HashMap<>();
																		// String optionsXpath =
																		// String.format(activeTSBComponent.listOptions,questionIndex,actionIndex);
																		String optionsXpath = String.format(
																				activeTSBComponent.listOptions,
																				questionIndex, actionIndex) + "/span";
																		String valuesXpath = String.format(
																				activeTSBComponent.listOptions,
																				questionIndex, actionIndex)
																				+ "/div/span";
																		List<String> options = ExtWebComponent
																				.getExtWebElements(optionsXpath)
																				.stream()
																				.map(excludeEle -> excludeEle.getText()
																						.trim())
																				.collect(Collectors.toList());
																		List<String> values = ExtWebComponent
																				.getExtWebElements(valuesXpath).stream()
																				.map(excludeEle -> excludeEle.getText()
																						.trim())
																				.collect(Collectors.toList());
																		/*
																		 * for (String option : options) { String
																		 * keyXpath = String.format(
																		 * activeTSBComponent.listOptions,
																		 * questionIndex, actionIndex) + "/span";
																		 * //String valueXpath =
																		 * 
																		 * String key = option;
																		 * 
																		 * String key = ExtWebComponent
																		 * .getExtWebElement(keyXpath).getText(); String
																		 * value = values.iterator().next();
																		 * logger.info("key is : "+key+"Value : "
																		 * +value); optionsMap.put(key, value); }
																		 */

																		Iterator<String> optionsIterator = options
																				.iterator();
																		Iterator<String> valuesIterator = values
																				.iterator();

																		while (optionsIterator.hasNext()
																				&& valuesIterator.hasNext()) {

																			optionsMap.put(optionsIterator.next(),
																					valuesIterator.next());

																		}

																		/*
																		 * if(optionsMap.get("Option").equals("Yes")) {
																		 * questionData.setOptionYes(optionsMap); }else
																		 * if(optionsMap.get("Option").equals("No")) {
																		 * questionData.setOptionNo(optionsMap); }else {
																		 * questionData.setOptionInputResponse(
																		 * optionsMap); }
																		 */

																		questionData.setOptionMap(optionsMap);
																		optionsMap.forEach((k, v) -> logger.info(
																				"Key is " + k + " value is " + v));
																	}
																	questions.add(questionData);
																}

																tsbData.setQuestionsDataBean(questions);

															}
															activeTSBComponent.headerRepairs.scrollAndClick();
															tsbData.setDefaultRepair(
																	activeTSBComponent.txtDefaultRepair.getText());
															tsbData.setRepairMap(activeTSBComponent.getRepairMap());
															tsbData.getRepairMap()
																	.forEach((k, v) -> logger.info("Repair title is "
																			+ k + "repair description is " + v));

														}
														break Conditions;
													} else {
														logger.info("Exiting TSB as condition did not match");
													}

												}
											}
										}
									}
								}
							}
						} else {
							logger.info("Exiting TSB as component did not match");
						}
					} else {
						logger.info("Exiting TSB as component did not match");
					}
					engManagerPage.closeTab(tSBNumber);
					engManagerPage.getTabInFocus("TSB & Tribal");
					// WaitUtil.sleep(2000);
				}

				if (evaluatedTSBList.isEmpty()) {
					logger.info("No TSBs evaluated");

				} else {
					evaluatedTSBList.forEach(System.out::println);
				}

			} else {
				logger.info("No TSBs found");

			}
			// below: newly added code for pagination

			currentpage++;

		} // above: newly added code for pagination
		long endTime = System.currentTimeMillis();
		logger.info("elapsed time : " + (endTime - startTime) / 1000 + "secs.");
		engManagerPage.gettSBComponent().buttonClear.scrollAndClick();
		WaitUtil.sleep(2000);
		engManagerPage.closeTab("TSB & Tribal");
		tsbData.setListTSB(evaluatedTSBList);

		if (tsbData.getListTSB().size() != 1) {
			// fetch PCC questions;

		}
		if (logoutRequired) {
			engManagerPage.logOut();
		}
		return tsbData;
	}

	public static TSBDataBean evaluateTSB(List<String> listCustomerSymptom, List<String> listTechnicianSymptom,
			Map<String, String> vehicleRefMap, String expectedComponent, boolean loginRequired,
			boolean logoutRequired) {

		long startTime = System.currentTimeMillis();
		String region = "NNA";
		String model = vehicleRefMap.get("ModelLineCode");
		String year = String.valueOf(
				DateUtil.getCalendarDate(DateUtil.parseDate(vehicleRefMap.get("ManufacturedDate"), "yyyy-MM-dd"))
						.get(Calendar.YEAR));
		String engineCode = vehicleRefMap.get("EngineModelCode");
		String vIN = vehicleRefMap.get("VIN");

		String stampingNum = vIN.substring(0, 11);
		String chassisNum = vIN.substring(11);
		List<String> evaluatedTSBList = new ArrayList<>();
		TSBDataBean tsbData = new TSBDataBean();

		EngManagerPage engManagerPage = loginRequired ? new EngManagerPage(Roles.ENGINEERINGMANAGER)
				: new EngManagerPage();
		engManagerPage.waitForPageToLoad();
		engManagerPage.getLeftSideMenuElement("TSB & Tribal Knowledge").click();
		engManagerPage.gettSBComponent().dropdownModel.selectByValue(model);
		engManagerPage.gettSBComponent().dropdownYear.select(year);
		WaitUtil.sleep(2000);

		// below: newly added code for pagination
		// total number of pages with list of TSBs
		TSBTableComponent table = new TSBTableComponent();
		int totalPages = table.getPaginationSize();
		logger.info("Total no. of pages: " + totalPages);
		int currentPage = 1;
		while (currentPage <= totalPages) {

			if ((currentPage != 1 && currentPage <= totalPages && totalPages > 1)) {

				table.clickNextLink();
				WaitUtil.sleep(2000);
				logger.info("Clicking on page:" + currentPage);
				TSBTableComponent table2 = new TSBTableComponent();
				table = table2;
			}

			List<ExtWebElement> activeTSBList = table.getActiveTSBsList();
			// above: newly added code for pagination
			if ((!activeTSBList.isEmpty())) {
				ActiveTSB: for (ExtWebElement ele : activeTSBList) {
					boolean checkVINs = true;
					String tSBNumber = ele.getText();
					if (tSBNumber.equals("Work queue is empty"))
						continue ActiveTSB;
					logger.info("Evaluating TSB: " + tSBNumber);

					ele.waitForElementToBeClickable(5000);
					ele.click();
					ActiveTSBComponent activeTSBComponent = new ActiveTSBComponent(tSBNumber);
					String actualComponent = activeTSBComponent.txtComponentCategory.getText().trim();
					if (compareComponent(actualComponent, expectedComponent)) {
						// validate region
						if (isRegionSelected(region)) {
							// validate model,year and engine code
							activeTSBComponent.tableApplicableModel.moveToElement();
							List<ExtWebElement> modelRows = (activeTSBComponent.listApplicableModel);
							if (isListEmpty(getListOfString(modelRows), "No items")) {
								ApplicableModels: for (ExtWebElement modelRow : modelRows) {

									String modelXpath = "//td[@data-attribute-name='Model']";
									// logger.info(activeTSBComponent.getChildElement(modelRow, "Model").getText());
									// logger.info(activeTSBComponent.getChildElement(modelRow,
									// "Operator").getText());

									// compare model number
									String modelName = ExtWebComponent
											.getChildElement(modelRow, activeTSBComponent.fieldsXpath, "Model")
											.getText();
									if (modelName.startsWith(model)) {
										// compare year
										boolean yearMatch = isMatch(modelRow, year);
										if (yearMatch) {
											// compare engine code
											String actualEngineCode;
											try {
												actualEngineCode = ExtWebComponent.getChildElement(modelRow,
														activeTSBComponent.fieldsXpath, "Engine Code").getText();
											} catch (Exception e) {
												actualEngineCode = "";
											}
											if (isEqualOrBlank(actualEngineCode, engineCode)) {
												checkVINs = true;
												break ApplicableModels;
											}
										}
									} else {

									}
									checkVINs = false;
								}
							}
							if (checkVINs) {
								// Applicable VINs validation
								activeTSBComponent.tableApplicableVINs.moveToElement();

								List<ExtWebElement> vINRows = activeTSBComponent.listApplicableVINs;
								boolean checkEngineCodes = true;
								if (isListEmpty(getListOfString(vINRows), "No items")) {
									ApplicableVINs: for (ExtWebElement vINRow : vINRows) {

										// String[] columns = vINRow.split("\\n+");
										// compare stamping number
										String actualStampingNum = ExtWebComponent.getChildElement(vINRow,
												activeTSBComponent.fieldsXpath, "Stamping Number").getText();
										if (stampingNum.equals(actualStampingNum)) {
											// compare chassis number
											checkEngineCodes = isMatch(vINRow, chassisNum);
											if (checkEngineCodes) {
												break ApplicableVINs;
											}
										}

									}
								}
								if (checkEngineCodes) {
									// TODO need to confirm below logic
									activeTSBComponent.tableApplicableEngineCodes.moveToElement();
									boolean checkExclusions = true;
									List<String> engineCodeRows = getListOfString(
											activeTSBComponent.listApplicableEngCodes);
									if (isListEmpty(engineCodeRows, "No items")) {
										ApplicableEngineCodes: for (String engineCodeRow : engineCodeRows) {
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
										List<String> exclusionCriterias = activeTSBComponent.listExclusionCriterias
												.stream().map(excludeEle -> excludeEle.getText())
												.collect(Collectors.toList());
										boolean isExclusionPresent = !(exclusionCriterias.get(0).equals("No items"));
										if (isExclusionPresent) {
											int tSBExclusionsIndex = 0;
											Exclusions: for (String exclusionCriteria : exclusionCriterias) {
												logger.info("Checking exclusion in : " + exclusionCriteria);
												boolean skipToNextExclusion = false;
												tSBExclusionsIndex++;
												List<List<String>> selectedSymptoms = new ArrayList<>();
												String applicableFor = ExtWebComponent.getExtWebElement(String.format(
														"//tr[contains(@id,'TSBExclusions$l%d')]//td[@class='dataValueRead']//span[text()]",
														tSBExclusionsIndex)).getText();
												if (applicableFor.equals("Both")) {
													selectedSymptoms.add(listCustomerSymptom);
													selectedSymptoms.add(listTechnicianSymptom);

												} else if (applicableFor.equals("Customer")) {
													selectedSymptoms.add(listCustomerSymptom);
												} else if (applicableFor.equals("Technician")) {
													selectedSymptoms.add(listTechnicianSymptom);
												}

												String exclusionsFieldNamesXpath = String.format(
														"//tr[contains(@id,'TSBExclusions$l%d') and contains(@id,'TSBCondition')]//td[@data-attribute-name='Field Name']//span",
														tSBExclusionsIndex);
												List<String> listExclusionsFieldNames = ExtWebComponent
														.getExtWebElements(exclusionsFieldNamesXpath).stream()
														.map(excludeEle -> excludeEle.getText())
														.collect(Collectors.toList());

												boolean excludeTSB = false;
												SelectedSymptoms: for (List<String> selectedSymptom : selectedSymptoms) {
													skipToNextExclusion = false;
													int tSBExclusionConditionIndex = 0;
													Fields: for (String fieldName : listExclusionsFieldNames) {
														logger.info("Checking exclusion condition in " + fieldName
																+ " under " + exclusionCriteria);
														tSBExclusionConditionIndex++;
														if (fieldName.equalsIgnoreCase("DTC")) {
															logger.info("Skipping exclusion for DTC");
															continue;
														}
														// String exclusionFieldName = fieldName.getText().trim();

														String operatorXpath = String.format(
																"//tr[contains(@id,'TSBExclusions$l%d') and contains(@id,'TSBCondition$l%d')]//td[@data-attribute-name='Operator']//span",
																tSBExclusionsIndex, tSBExclusionConditionIndex);
														String operator = ExtWebComponent
																.getExtWebElement(operatorXpath).getText();
														String valuesXpath = activeTSBComponent.getExclusionValuesXpath(
																operator, tSBExclusionsIndex,
																tSBExclusionConditionIndex);
														List<String> listExclusion = ExtWebComponent
																.getExtWebElements(valuesXpath).stream()
																.map(excludeEle -> excludeEle.getText())
																.collect(Collectors.toList());
														boolean skipToNextField = false;
														ExclusionValues: for (String exclusion : listExclusion) {
															logger.info(
																	"Checking exclusion condition with : " + exclusion);
															/*
															 * excludeTSB = activeTSBComponent
															 * .evaluateCondition(selectedSymptom, exclusion);
															 */

															skipToNextField = activeTSBComponent
																	.evaluateCondition(selectedSymptom, exclusion);

															if (skipToNextField) {

																logger.info(
																		"Conditions match found for : " + exclusion);
																break ExclusionValues;
															}
														}
														if (!skipToNextField) {

															skipToNextExclusion = true;
															continue SelectedSymptoms;
														}

													}
													if (!skipToNextExclusion) {
														checkConditions = false;
														logger.info("Exiting TSB as exclusion condition matched for : "
																+ "Exclusion Criteria" + tSBExclusionsIndex);
														break Exclusions;
													}
												}

											}

										}
										if (checkConditions) {
											activeTSBComponent.headerConditions.scrollAndClick();

											List<String> listConditionsCriterias = activeTSBComponent.listTSBConditions
													.stream().map(excludeEle -> excludeEle.getText())
													.collect(Collectors.toList());
											boolean isConditionPresent = !(listConditionsCriterias.get(0)
													.equals("No items"));
											// if (!listConditionsCriterias.isEmpty()) {
											if (isConditionPresent) {
												for (ExtWebElement doesNotApply : activeTSBComponent.listChkboxDoesNotApply) {
													if (doesNotApply.getAttribute("alt").equals("True")) {
														logger.info("Does not apply is checked, hence exiting TSB");
														engManagerPage.closeTab(tSBNumber);
														engManagerPage.getTabInFocus("TSB & Tribal");
														continue ActiveTSB;
													}
												}

												int tsbConditionsIndex = 0;
												Conditions: for (String conditionsCriteria : listConditionsCriterias) {
													tsbConditionsIndex++;

													boolean skipToNextCondition = false;
													// logger.info("Checking including in " +
													// conditionsCriteria);
													List<List<String>> selectedSymptoms = new ArrayList<>();
													String applicableFor = ExtWebComponent.getExtWebElement(String
															.format("//tr[contains(@id,'TSBCriteria$l%d')]//td[@class='dataValueRead']//span[text()]",
																	tsbConditionsIndex))
															.getText();
													if (applicableFor.equals("Both")) {
														selectedSymptoms.add(listCustomerSymptom);
														selectedSymptoms.add(listTechnicianSymptom);

													} else if (applicableFor.equals("Customer")) {
														selectedSymptoms.add(listCustomerSymptom);
													} else if (applicableFor.equals("Technician")) {
														selectedSymptoms.add(listTechnicianSymptom);
													}
													String conditionsFieldNamesXpath = String.format(
															"//tr[contains(@id,'TSBCriteria$l%d') and contains(@id,'TSBCondition')]//td[@data-attribute-name='Field Name']//span",
															tsbConditionsIndex);
													List<String> listConditionsFieldNames = ExtWebComponent
															.getExtWebElements(conditionsFieldNamesXpath).stream()
															.map(excludeEle -> excludeEle.getText())
															.collect(Collectors.toList());

													SelectedSymptoms: for (List<String> selectedSymptom : selectedSymptoms) {
														skipToNextCondition = false;
														int conditionsConditionIndex = 0;

														Fields: for (String fieldName : listConditionsFieldNames) {

															conditionsConditionIndex++;
															logger.info("Checking " + fieldName + " under "
																	+ conditionsCriteria);
															// String conditionsFieldName = fieldName.getText().trim();
															String conditionsOperatorXpath = String.format(
																	"//tr[contains(@id,'TSBCriteria$l%d') and contains(@id,'TSBCondition$l%d')]//td[@data-attribute-name='Operator']//span",
																	tsbConditionsIndex, conditionsConditionIndex);
															String conditionsOperator = ExtWebComponent
																	.getExtWebElement(conditionsOperatorXpath)
																	.getText();
															String conditionsValuesXpath = activeTSBComponent
																	.getConditionValuesXpath(conditionsOperator,
																			tsbConditionsIndex,
																			conditionsConditionIndex);
															List<String> listConditions = ExtWebComponent
																	.getExtWebElements(conditionsValuesXpath).stream()
																	.map(excludeEle -> excludeEle.getText())
																	.collect(Collectors.toList());
															boolean skipToNextField = false;

															CondtionValues: for (String condition : listConditions) {
																logger.info("Checking includes condition for : "
																		+ condition);
																skipToNextField = activeTSBComponent
																		.evaluateCondition(selectedSymptom, condition);
																if (skipToNextField) {
																	logger.info("Conditions match found for : "
																			+ condition);
																	break CondtionValues;
																} else {
																	logger.info("Conditions match not found for : "
																			+ condition);
																}
															}

															if (!skipToNextField) {
																skipToNextCondition = true;
																continue SelectedSymptoms;
															}

														}

														if (!skipToNextCondition) {
															break SelectedSymptoms;
														}

														// skipToNextCondition = true;

													}

													if (!skipToNextCondition) {
														evaluatedTSBList.add(tSBNumber);
														if (evaluatedTSBList.size() == 1) {
															// TODO add code for capturing questions and repair
															activeTSBComponent.headerQuestions.scrollAndClick();
															List<String> listQuestions = activeTSBComponent.listQuestions
																	.stream().map(excludeEle -> excludeEle.getText())
																	.collect(Collectors.toList());
															boolean isQuestionPresent = !(listQuestions.get(0)
																	.equals("No items"));
															if (isQuestionPresent) {
																int questionIndex = 0;
																List<QuestionsDataBean> questions = new ArrayList<>();
																Question: for (String question : listQuestions) {
																	questionIndex++;
																	logger.info("Question " + questionIndex + " data:");
																	QuestionsDataBean questionData = new QuestionsDataBean();
																	questionData.setQuestionType(ExtWebComponent
																			.getExtWebElement(String.format(
																					activeTSBComponent.txtQuestionType,
																					questionIndex))
																			.getText().trim().replace(":", ""));
																	logger.info("Question type : "
																			+ questionData.getQuestionType());
																	questionData.setQuestionText(ExtWebComponent
																			.getExtWebElement(String.format(
																					activeTSBComponent.txtQuestionText,
																					questionIndex))
																			.getText().trim());
																	logger.info("Question text : "
																			+ questionData.getQuestionText());
																	List<ExtWebElement> actions = ExtWebComponent
																			.getExtWebElements(String.format(
																					activeTSBComponent.listActions,
																					questionIndex));
																	int actionIndex = 0;
																	Actions: for (ExtWebElement action : actions) {
																		actionIndex++;
																		Map<String, String> optionsMap = new HashMap<>();
																		// String optionsXpath =
																		// String.format(activeTSBComponent.listOptions,questionIndex,actionIndex);
																		String optionsXpath = String.format(
																				activeTSBComponent.listOptions,
																				questionIndex, actionIndex) + "/span";
																		String valuesXpath = String.format(
																				activeTSBComponent.listOptions,
																				questionIndex, actionIndex)
																				+ "/div/span";
																		List<String> options = ExtWebComponent
																				.getExtWebElements(optionsXpath)
																				.stream()
																				.map(excludeEle -> excludeEle.getText()
																						.trim())
																				.collect(Collectors.toList());
																		List<String> values = ExtWebComponent
																				.getExtWebElements(valuesXpath).stream()
																				.map(excludeEle -> excludeEle.getText()
																						.trim())
																				.collect(Collectors.toList());
																		/*
																		 * for (String option : options) { String
																		 * keyXpath = String.format(
																		 * activeTSBComponent.listOptions,
																		 * questionIndex, actionIndex) + "/span";
																		 * //String valueXpath =
																		 * 
																		 * String key = option;
																		 * 
																		 * String key = ExtWebComponent
																		 * .getExtWebElement(keyXpath).getText(); String
																		 * value = values.iterator().next();
																		 * logger.info("key is : "+key+"Value : "
																		 * +value); optionsMap.put(key, value); }
																		 */

																		Iterator<String> optionsIterator = options
																				.iterator();
																		Iterator<String> valuesIterator = values
																				.iterator();

																		while (optionsIterator.hasNext()
																				&& valuesIterator.hasNext()) {

																			optionsMap.put(optionsIterator.next(),
																					valuesIterator.next());

																		}

																		/*
																		 * if(optionsMap.get("Option").equals("Yes")) {
																		 * questionData.setOptionYes(optionsMap); }else
																		 * if(optionsMap.get("Option").equals("No")) {
																		 * questionData.setOptionNo(optionsMap); }else {
																		 * questionData.setOptionInputResponse(
																		 * optionsMap); }
																		 */

																		questionData.setOptionMap(optionsMap);
																		optionsMap.forEach((k, v) -> logger.info(
																				"Key is " + k + " value is " + v));
																	}
																	questions.add(questionData);
																}

																tsbData.setQuestionsDataBean(questions);

															}
															activeTSBComponent.headerRepairs.scrollAndClick();
															tsbData.setDefaultRepair(
																	activeTSBComponent.txtDefaultRepair.getText());
															tsbData.setRepairMap(activeTSBComponent.getRepairMap());
															tsbData.getRepairMap()
																	.forEach((k, v) -> logger.info("Repair title is "
																			+ k + "repair description is " + v));

														}
														break Conditions;
													} else {
														logger.info("Exiting TSB as condition did not match");
													}

												}
											}
										}
									}
								}
							}
						} else {
							logger.info("Exiting TSB as region did not match " + region);
						}
					} else {
						logger.info("Exiting TSB as component did not match, Expected: " + expectedComponent
								+ ", Actual: " + actualComponent);
					}
					engManagerPage.closeTab(tSBNumber);
					engManagerPage.getTabInFocus("TSB & Tribal");
					// WaitUtil.sleep(2000);
				}

				if (evaluatedTSBList.isEmpty()) {
					logger.info("No TSBs evaluated");

				} else {
					evaluatedTSBList.forEach(System.out::println);
				}

			} else {
				logger.info("No TSBs found");

			}
			// below: newly added code for pagination

			currentPage++;

		} // above: newly added code for pagination
		long endTime = System.currentTimeMillis();
		logger.info("elapsed time : " + (endTime - startTime) / 1000 + "secs.");
		engManagerPage.gettSBComponent().buttonClear.scrollAndClick();
		WaitUtil.sleep(2000);
		// engManagerPage.closeTab("TSB & Tribal");
		tsbData.setListTSB(evaluatedTSBList);

		if (tsbData.getListTSB().size() != 1) {
			// fetch PCC questions;

		}
		if (logoutRequired) {
			engManagerPage.logOut();
		}
		return tsbData;
	}

	public static boolean compareComponent(String expected, String actual) {

		return expected.equalsIgnoreCase(actual);

	}

	public static boolean isRegionSelected(String region) {

		String regionRadiobutton = String.format("//label[text()='%s']/preceding-sibling::img", region);
		ExtWebElement regionRadioButtonEle = ExtWebComponent.getExtWebElement(regionRadiobutton);
		return regionRadioButtonEle.getAttribute("alt").equals("true");

	}

	public static boolean isListEmpty(List<String> list, String emptyString) {

		return !(list.get(0).equalsIgnoreCase(emptyString));
	}

	public static List<String> getListOfString(List<ExtWebElement> listElements) {

		return listElements.stream().map(WebElement::getText).collect(Collectors.toList());

	}

	public static boolean isMatch(String[] array, String stringToMatch) {

		String matchCondition = array[0];
		boolean isMatch = false;
		switch (matchCondition.toUpperCase()) {

		case "BETWEEN":

			int from = getInt(array[1].trim());
			int to = getInt(array[3].trim());
			int yr = Integer.parseInt(stringToMatch);
			if ((from <= yr && yr <= to)) {
				isMatch = true;
			}

			break;
		case "EQUALS":

			String actualYear = array[1].trim();
			if (actualYear.equals(stringToMatch)) {
				isMatch = true;
			}

			break;

		default:
			logger.info(
					"match condition " + matchCondition + " is not valid. Valid conditions are Between and Equals.");

		}
		return isMatch;
	}

	public static boolean isMatch(ExtWebElement row, String stringToMatch) {

		String matchCondition = ActiveTSBComponent.getChildElement(row, "//td[@data-attribute-name='%s']", "Operator")
				.getText().trim();
		boolean isMatch = false;
		switch (matchCondition.toUpperCase()) {

		case "BETWEEN":
			int from = getInt(
					ExtWebComponent.getChildElement(row, "//td[contains(@data-ui-meta,'%s')]//span", "From").getText());
			int to = getInt(
					ExtWebComponent.getChildElement(row, "//td[contains(@data-ui-meta,'%s')]//span", "To").getText());
			int yr = Integer.parseInt(stringToMatch);
			if ((from <= yr && yr <= to)) {
				isMatch = true;
			}

			break;
		case "EQUALS":

			String actualYear = ExtWebComponent.getChildElement(row, "//td[contains(@data-ui-meta,'%s')]//span", "From")
					.getText();
			if (actualYear.equals(stringToMatch)) {
				isMatch = true;
			}

			break;

		default:
			logger.info(
					"match condition " + matchCondition + " is not valid. Valid conditions are Between and Equals.");

		}
		return isMatch;
	}

	

	public static int getInt(String num) {

		return Integer.parseInt(num);

	}

	public static String[] getSubArray(String[] array, int startIndex, int endIndex) {

		return ArrayUtils.subarray(array, startIndex, endIndex);

	}

	public static boolean isEqualOrBlank(String actual, String expected) {

		return actual.equals(expected) || actual.equals("");

	}

}