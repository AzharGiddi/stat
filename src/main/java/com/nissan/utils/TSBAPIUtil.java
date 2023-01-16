package com.nissan.utils;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nissan.automation.core.utils.DateUtil;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.ControlUnits;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.EngineDetails;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.ModelDetails;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBConditions;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBCriteria;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBExclusions;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBMultiselect;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.VINDetails;
import com.nissan.databeans.QuestionsDataBean;
import com.nissan.databeans.TSBAPIDataBean;
import com.nissan.databeans.TSBDataBean;
import com.nissan.databeans.TestDataBean;
import com.nissan.enums.UserData;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.ApplicableTSBsUtil.ApplicableTSBListObject.pxResults;

public class TSBAPIUtil {

	private TSBAPIUtil() {

	}

	/**
	 * Updates logs(Date format MM/DD/YYYY) 08/06/2022Updated isRegionMethod to
	 * include all three regions.
	 */
	private static final Log logger = LogFactory.getLog(TSBAPIUtil.class);

	public static TSBDataBean evaluateLegacyTSBs(TestDataBean testData) {

		TSBDataBean tsbData = new TSBDataBean();
		String vIN = testData.getUserData().get(UserData.VIN);
		Map<String, String> vehicleRefMap = testData.getVehicleRefDetails(vIN);
		String expModelCode = vehicleRefMap.get("ModelLineCode");
		String expModelYear = String.valueOf(
				DateUtil.getCalendarDate(DateUtil.parseDate(vehicleRefMap.get("ManufacturedDate"), "yyyy-MM-dd"))
						.get(Calendar.YEAR));
		String expectedComponent = testData.getUserData().get(UserData.COMPONENT);
		List<String> evaluatedTSBList = new ArrayList<>();
		Map<String, String> repairMap = new LinkedHashMap<>();
		List<pxResults> applicableTSBs = ApplicableTSBsUtil.getApplicableTSB(expModelCode, expModelYear,
				expectedComponent,"Legacy Rule");
		long startTime = System.currentTimeMillis();
		for (pxResults applicableTSB : applicableTSBs) {

			String currentTSBId = applicableTSB.pzInsKey;
			if(currentTSBId.contains("SB-9214")) {
				System.out.println("Test");
			}
			JsonToJavaObjectUtil currentTSB = ApplicableTSBsUtil.getTSBContentAsObject(currentTSBId);
			TSBAPIDataBean tsbDetails = new TSBAPIDataBean(currentTSB);
			boolean isTSBApplicable = false;
			try {
				isTSBApplicable = isTSBApplicable(tsbDetails, testData);
				if (isTSBApplicable) {
					ExtentLogger.info("TSB: " + currentTSBId + " matched", false);
					evaluatedTSBList.add(currentTSBId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomRuntimeException(
						"Unable to evaluate " + currentTSBId + " due to :" + e.getLocalizedMessage());
			}

		}

		tsbData.setListTSB(evaluatedTSBList);
		tsbData.setRepairMap(repairMap);
		long endTime = System.currentTimeMillis();
		long elapsedTime = (endTime - startTime);
		ExtentLogger.info("elapsed time : " + getElapsedTime(elapsedTime), false);
		return tsbData;

	}

	private static String getElapsedTime(long timeInMillis) {

		long seconds = (TimeUnit.MILLISECONDS.toSeconds(timeInMillis));

		return seconds + " seconds";

	}

	private static String tsbId;

	public static boolean isTSBApplicable(TSBAPIDataBean tsbDetails, TestDataBean testData) {

		tsbId = tsbDetails.getTsbId();
		return isRegionMatch(tsbDetails.getIsNNARegion(), tsbDetails.getIsNMXRegion(), tsbDetails.getIsNCIRegion(),
				"NNA") && isModelMatch(tsbDetails.getModelList(), testData)
				&& isVINMatch(tsbDetails.getvINList(), testData.getUserData().get(UserData.VIN))
				&& isEngineCodeMatch(tsbDetails.getEngineList(), testData.getVehicleRefDetails().get("EngineModelCode"))
				&& !isExclusionMatch(tsbDetails.gettSBExclusionsList(), testData)
				&& isConditionsMatch(tsbDetails.gettSBCriteriaList(), testData);

	}

	private static boolean isConditionsMatch(List<TSBCriteria> tsbCriteriaList, TestDataBean testData) {
		boolean isMatch = false;
		boolean dNotApply = false;

		try {
			if (!tsbCriteriaList.isEmpty()) {

				Collections.sort(tsbCriteriaList);

				for (TSBCriteria tsbcriteria : tsbCriteriaList) {
					dNotApply = Boolean.parseBoolean(tsbcriteria.doesNotApply);
					ExtentLogger.info("Does Not Apply: " + dNotApply, false);
					isMatch = doesCriteriaMatch(tsbcriteria, testData);

					if (!isMatch) {
						ExtentLogger.info(String.format("Exiting %s as conditions not matched", tsbId), false);
						dNotApply = false;
					} else if (dNotApply) {
						ExtentLogger.info(
								String.format("Exiting %s as conditions matched but doesNotApply is true", tsbId),
								false);
						isMatch = false;
					} else {
						break;
					}
				}
			} else {
				isMatch = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomRuntimeException(
					"Exception caught in isConditionsMatch method: " + e.getLocalizedMessage());
		}

		return isMatch;
	}

	private static boolean doesCriteriaMatch(TSBCriteria tSBCriteria, TestDataBean testData) {

		boolean isMatch = false;

		String applicableFor = tSBCriteria.evaluationCriteria;

		if (Objects.isNull(applicableFor)) {

			return false;
		}

		List<TSBConditions> conditionsList = tSBCriteria.tSBConditionsList;

		for (TSBConditions condition : conditionsList) {

			List<List<String>> symptomList = getSymptomList(testData, applicableFor);
			String operator = condition.operator;
			List<String> tsbSymptomsList = getStringSymptomsList(condition.tSBMultiselectList, operator);
			isMatch = checkConditions(symptomList, tsbSymptomsList);

			if (!isMatch) {

				break;
			}

		}

		return isMatch;
	}

	private static boolean isExclusionMatch(List<TSBExclusions> tsbExclusionsList, TestDataBean testData) {
		boolean isMatch = false;
		try {
			if (!tsbExclusionsList.isEmpty()) {
				for (TSBExclusions tSBExclusion : tsbExclusionsList) {
					isMatch = doesCriteriaMatch(tSBExclusion, testData);

					if (isMatch) {

						ExtentLogger.info(String.format("Exiting %s as exclusion matched", tsbId), false);
						break;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomRuntimeException("Exception caught in isExclusionMatch method: " + e.getLocalizedMessage());
		}

		return isMatch;

	}

	private static boolean doesCriteriaMatch(TSBExclusions tSBExclusion, TestDataBean testData) {

		boolean isMatch = false;

		String applicableFor = tSBExclusion.evaluationCriteria;

		if (Objects.isNull(applicableFor)) {

			return false;
		}

		List<TSBConditions> conditionsList = tSBExclusion.tSBConditionsList;

		for (TSBConditions condition : conditionsList) {

			List<List<String>> symptomList = getSymptomList(testData, applicableFor);
			String operator = condition.operator;
			List<String> tsbSymptomsList = getStringSymptomsList(condition.tSBMultiselectList, operator);
			isMatch = checkConditions(symptomList, tsbSymptomsList);

			if (!isMatch) {

				break;
			}

		}

		return isMatch;
	}

	private static List<String> getStringSymptomsList(List<TSBMultiselect> tSBMultiselectList, String operator) {

		List<String> returnList = new ArrayList<>();
		String op = operator.equals("In") ? "true" : "false";
		for (TSBMultiselect tSBMultiselect : tSBMultiselectList) {
			if (tSBMultiselect.pySelected.equals(op)) {
				returnList.add(tSBMultiselect.lineNumber);
			}

		}

		return returnList;

	}

	private static boolean checkConditions(List<List<String>> symptomsList, List<String> conditionList) {
		boolean isMatch = false;
		try {
			for (List<String> symptomList : symptomsList) {

				isMatch = checkCondition(symptomList, conditionList);
				if (isMatch) {
					break;
				}

			}
		} catch (Exception e) {
			throw new CustomRuntimeException("Exception caught in checkConditions method: " + e.getLocalizedMessage());
		}
		return isMatch;
	}

	private static boolean checkCondition(List<String> symptomList, List<String> conditionList) {

		boolean isMatch = false;

		for (String condition : conditionList) {

			if (symptomList.contains(condition)) {
				isMatch = true;
				break;

			}

		}

		return isMatch;
	}

	private static boolean isEngineCodeMatch(List<EngineDetails> engineCodeList, String expEngineCode) {
		boolean isMatch = false;
		try {
			if (!engineCodeList.isEmpty()) {
				for (EngineDetails engineDetail : engineCodeList) {
					isMatch = hasEngineCode(engineDetail.engineCode, expEngineCode);
					if (isMatch) {
						break;
					}
				}
				if (!isMatch) {
					ExtentLogger.info(String.format("Exiting %s as engineCode not matched", tsbId), false);
				}
			} else {
				isMatch = true;
			}

		} catch (Exception e) {
			throw new CustomRuntimeException(
					"Exception caught in isEngineCodeMatch method: " + e.getLocalizedMessage());
		}
		return isMatch;
	}

	private static boolean hasEngineCode(String actEngineCode, String expEngineCode) {

		return isEqualOrBlank(actEngineCode, expEngineCode);

	}

	private static boolean isVINMatch(List<VINDetails> vINList, String vIN) {
		boolean isMatch = false;
		try {
			if (!vINList.isEmpty()) {
				for (VINDetails vINDetails : vINList) {
					String expStampingNum = vIN.substring(0, 11);
					String expChassisNum = vIN.substring(11);
					isMatch = hasVIN(vINDetails, expStampingNum, expChassisNum);
					if (isMatch) {
						break;
					}
				}
				if (!isMatch) {

					ExtentLogger.info(String.format("Exiting %s as vin not matched", tsbId), false);
				}
			} else {
				isMatch = true;
			}
		} catch (Exception e) {
			throw new CustomRuntimeException("Exception caught in isVINMatch method: " + e.getLocalizedMessage());
		}

		return isMatch;
	}

	private static boolean hasVIN(VINDetails vinDetails, String expStampingNum, String expChassisNum) {

		boolean vINMatched = false;

		// compare stamping number
		String actualStampingNum = vinDetails.stampingNumber;
		if (expStampingNum.equals(actualStampingNum)) {
			// compare chassis number
			vINMatched = isVinMatch(vinDetails, expChassisNum);

		}

		return vINMatched;
	}

	public static boolean isVinMatch(VINDetails vinDetails, String stringToMatch) {
		String matchCondition = vinDetails.operator;
		boolean isMatch = false;
		switch (matchCondition.toUpperCase()) {

		case "BETWEEN":
			int from = Integer.parseInt(vinDetails.chasisRangeFrom);
			int to = Integer.parseInt(vinDetails.chasisRangeTo);
			int yr = Integer.parseInt(stringToMatch);
			if ((from <= yr && yr <= to)) {
				isMatch = true;
			}

			break;
		case "EQUALS":

			String actualYear = vinDetails.chasisRangeFrom;
			if (actualYear.equals(stringToMatch)) {
				isMatch = true;
			}

			break;

		default:
			ExtentLogger.info(
					"match condition " + matchCondition + " is not valid. Valid conditions are Between and Equals.",
					false);

		}
		return isMatch;
	}

	private static boolean isModelMatch(List<ModelDetails> modelList, TestDataBean testData) {
		boolean isMatch = false;
		try {
			if (!modelList.isEmpty()) {
				String expModelCode = testData.getVehicleRefDetails().get("ModelLineCode");
				String expModelYear = String
						.valueOf(DateUtil
								.getCalendarDate(DateUtil.parseDate(
										testData.getVehicleRefDetails().get("ManufacturedDate"), "yyyy-MM-dd"))
								.get(Calendar.YEAR));
				String expEngineModelCode = testData.getVehicleRefDetails().get("EngineModelCode");
				isMatch = hasModel(modelList, expModelCode, expModelYear, expEngineModelCode);

				if (!isMatch) {
					ExtentLogger.info(String.format("Exiting %s as model not matched", tsbId), false);
				}
			} else {
				isMatch = true;
			}
		} catch (Exception e) {
			throw new CustomRuntimeException("Exception caught in isModelMatch method: " + e.getLocalizedMessage());
		}
		return isMatch;
	}

	private static boolean hasModel(List<ModelDetails> listModels, String expModel, String expYear,
			String expEngineCode) {
		boolean modelMatched = false;

		for (ModelDetails model : listModels) {
			String actualModel = model.model;
			if (actualModel.startsWith(expModel)) {
				// compare year
				boolean yearMatch = isMatch(model, expYear);
				String actualEngineCode = model.engineModelCode;
				if (yearMatch && isEqualOrBlank(actualEngineCode, expEngineCode)) {
					modelMatched = true;
					break;
				}
			}

		}

		return modelMatched;
	}

	public static boolean isMatch(ModelDetails modelDetails, String stringToMatch) {
		String matchCondition = modelDetails.operator;
		boolean isMatch = false;
		switch (matchCondition.toUpperCase()) {

		case "BETWEEN":
			int from = Integer.parseInt(modelDetails.modelYearFrom);
			int to = Integer.parseInt(modelDetails.modelYearTo);
			int yr = Integer.parseInt(stringToMatch);
			if ((from <= yr && yr <= to)) {
				isMatch = true;
			}

			break;
		case "EQUALS":

			String actualYear = modelDetails.modelYearFrom;
			if (actualYear.equals(stringToMatch)) {
				isMatch = true;
			}

			break;

		default:
			ExtentLogger.info(
					"match condition " + matchCondition + " is not valid. Valid conditions are Between and Equals.",
					false);

		}
		return isMatch;
	}

	private static boolean isRegionMatch(String tsbDetails, String region) {

		String rgn = String.format("Is%sRegion", region);
		String isRegion = tsbDetails;
		boolean isMatch = Boolean.parseBoolean(isRegion);

		if (!isMatch) {
			ExtentLogger.info(String.format("Exiting %s as region not matched", tsbId), false);
		}
		return isMatch;
	}

	private static boolean isRegionMatch(String isNNA, String isNMX, String isNCI, String region) {
		try {
			boolean isNNARegion = Boolean.parseBoolean(isNNA);
			boolean isNMXRegion = Boolean.parseBoolean(isNMX);
			boolean isNCIRegion = Boolean.parseBoolean(isNCI);
			if ((isNNARegion && isNMXRegion && isNCIRegion) || (!isNNARegion && !isNMXRegion && !isNCIRegion)
					|| (region.equalsIgnoreCase("NNA") && isNNARegion) || (isNMXRegion && region.equalsIgnoreCase("NMX"))
					|| (isNCIRegion && region.equalsIgnoreCase("NCI"))) {
				return true;
			}
		} catch (Exception e) {
			throw new CustomRuntimeException("Exception caught in isRegionMatch method: " + e.getLocalizedMessage());
		}
		ExtentLogger.info(String.format("Exiting %s as region not matched", tsbId), false);
		return false;
	}
	
	private static boolean isCOntrolUnitMatch(List<ControlUnits> controlUnitList) {
		
		
		
		
		return false;
	}
	

	public static int getInt(String num) {

		return Integer.parseInt(num);

	}

	public static String[] getSubArray(String[] array, int startIndex, int endIndex) {

		return ArrayUtils.subarray(array, startIndex, endIndex);

	}

	public static boolean isEqualOrBlank(String actual, String expected) {

		return Objects.isNull(actual) || actual.equals(expected) || actual.equals("");

	}

	private static List<List<String>> getSymptomList(TestDataBean testData, String applicableFor) {

		List<List<String>> returnList = new ArrayList<>();
		ExtentLogger.info("Inside " + tsbId + " Applicable for: " + applicableFor, false);
		if (applicableFor.equals("B") || applicableFor.equals("C")) {
			returnList.add(testData.getCustomerSymptom().getSymptomSet());
		}

		if (applicableFor.equals("B") || applicableFor.equals("T")) {
			returnList.add(testData.getTechnicianSymptom().getSymptomSet());
		}

		return returnList;
	}

}
