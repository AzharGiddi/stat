package com.nissan.utils;

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

import com.nissan.automation.core.utils.DateUtil;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.ControlUnits;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.ESMDTCList;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.EngineDetails;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.ModelDetails;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBConditions;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBCriteria;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBExclusions;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBMultiselect;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.VINDetails;
import com.nissan.databeans.ESMAPIDataBean;
import com.nissan.databeans.ESMDataBean;
import com.nissan.databeans.TSBAPIDataBean;
import com.nissan.databeans.TSBDataBean;
import com.nissan.databeans.TestDataBean;
import com.nissan.enums.UserData;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.ApplicableESMsUtil.ApplicableESMListObject.pxResults;

public class ESMAPIUtil {

	private ESMAPIUtil() {

	}

	/**
	 * Updates logs(Date format MM/DD/YYYY) 08/06/2022Updated isRegionMethod to
	 * include all three regions.
	 */
	private static final Log logger = LogFactory.getLog(ESMAPIUtil.class);

	public static ESMDataBean evaluateESM(TestDataBean testData) {

		ESMDataBean esmData = new ESMDataBean();
		String vIN = testData.getUserData().get(UserData.VIN);
		Map<String, String> vehicleRefMap = testData.getVehicleRefDetails(vIN);
		String expModelCode = vehicleRefMap.get("ModelLineCode");
		String expModelYear = String.valueOf(
				DateUtil.getCalendarDate(DateUtil.parseDate(vehicleRefMap.get("ManufacturedDate"), "yyyy-MM-dd"))
						.get(Calendar.YEAR));
		String expectedComponent = testData.getUserData().get(UserData.COMPONENT);
		List<String> evaluatedESMList = new ArrayList<>();
		Map<String, String> repairMap = new LinkedHashMap<>();
		List<pxResults> applicableESMs = ApplicableESMsUtil.getApplicableESM(expModelCode, expModelYear,
				expectedComponent,"Legacy Rule");
		long startTime = System.currentTimeMillis();
		for (pxResults applicableESM : applicableESMs) {

			String currentESMId = applicableESM.pzInsKey;
			System.out.println(currentESMId);
			if(currentESMId.contains("SM-37128")) {
				System.out.println("debug");
			}
			JsonToJavaObjectUtil currentESM = ApplicableESMsUtil.getESMContentAsObject(currentESMId);
			ESMAPIDataBean esmDetails = new ESMAPIDataBean(currentESM);
			boolean isESMApplicable = false;
			try {
				isESMApplicable = isESMApplicable(esmDetails, testData);
				if (isESMApplicable) {
					ExtentLogger.info("ESM: " + currentESMId + " matched", false);
					evaluatedESMList.add(currentESMId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomRuntimeException(
						"Unable to evaluate " + currentESMId + " due to :" + e.getLocalizedMessage());
			}

		}

		esmData.setListESM(evaluatedESMList);
		esmData.setRepairMap(repairMap);
		long endTime = System.currentTimeMillis();
		long elapsedTime = (endTime - startTime);
		ExtentLogger.info("elapsed time : " + getElapsedTime(elapsedTime), false);
		return esmData;

	}

	private static String getElapsedTime(long timeInMillis) {

		long seconds = (TimeUnit.MILLISECONDS.toSeconds(timeInMillis));

		return seconds + " seconds";

	}

	private static String esmId;

	public static boolean isESMApplicable(ESMAPIDataBean esmDetails, TestDataBean testData) {

		esmId = esmDetails.getESMId();
		return isEngineCodeMatch(testData.getVehicleRefDetails().get("EngineModelCode"),esmDetails.getEngineCode()) 
				&&isRegionMatch(esmDetails.getIsNNARegion(), esmDetails.getIsNMXRegion(), esmDetails.getIsNCIRegion(),"NNA") 
				&& isModelMatch(esmDetails.getModelList(), testData)
				&& isVINMatch(esmDetails.getvINList(), testData.getUserData().get(UserData.VIN))
				&& !isExclusionMatch(esmDetails.getESMExclusionsList(), testData)
				&& isConditionsMatch(esmDetails.gettSBCriteriaList(), testData);

	}
	
	private static boolean isEngineCodeMatch(String expectedEngineCode, String actualEngineCode) {
		return expectedEngineCode.equalsIgnoreCase(actualEngineCode);
	}
	
	private static boolean isDTCMatch(List<ESMDTCList> expDTCList, List<String> actDTCList) {
		boolean isMatch = false;
		
		for(ESMDTCList dtc:expDTCList) {
			
			isMatch =   actDTCList.contains(dtc.dtc);
			if(isMatch) {
				break;
			}
		}
		
		
		return isMatch;
		
	}
	
	private static boolean isControlUnitMatch(List<ControlUnits> expDTCList, List<String> actDTCList) {
		boolean isMatch = false;
		
		for(ControlUnits controlUnit:expDTCList) {
			
			isMatch =   actDTCList.contains(controlUnit.description);
			if(isMatch) {
				break;
			}
		}
		
		
		return isMatch;
		
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
						ExtentLogger.info(String.format("Exiting %s as conditions not matched", esmId), false);
						dNotApply = false;
					} else if (dNotApply) {
						ExtentLogger.info(
								String.format("Exiting %s as conditions matched but doesNotApply is true", esmId),
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

						ExtentLogger.info(String.format("Exiting %s as exclusion matched", esmId), false);
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
					ExtentLogger.info(String.format("Exiting %s as engineCode not matched", esmId), false);
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

					ExtentLogger.info(String.format("Exiting %s as vin not matched", esmId), false);
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
					ExtentLogger.info(String.format("Exiting %s as model not matched", esmId), false);
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
			ExtentLogger.info(String.format("Exiting %s as region not matched", esmId), false);
		}
		return isMatch;
	}

	private static boolean isRegionMatch(String isNNA, String isNMX, String isNCI, String region) {
		try {
			boolean isNNARegion = Boolean.parseBoolean(isNNA);
			boolean isNMXRegion = Boolean.parseBoolean(isNMX);
			boolean isNCIRegion = Boolean.parseBoolean(isNCI);
			if ((isNNARegion && isNMXRegion && isNCIRegion) || (!isNNARegion && !isNMXRegion && !isNCIRegion)
					|| region.equalsIgnoreCase("NNA") && isNNARegion || isNMXRegion && region.equalsIgnoreCase("NMX")
					|| isNCIRegion && region.equalsIgnoreCase("NCI")) {
				return true;
			}
		} catch (Exception e) {
			throw new CustomRuntimeException("Exception caught in isRegionMatch method: " + e.getLocalizedMessage());
		}
		ExtentLogger.info(String.format("Exiting %s as region not matched", esmId), false);
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
		ExtentLogger.info("Inside " + esmId + " Applicable for: " + applicableFor, false);
		if (applicableFor.equals("B") || applicableFor.equals("C")) {
			returnList.add(testData.getCustomerSymptom().getSymptomSet());
		}

		if (applicableFor.equals("B") || applicableFor.equals("T")) {
			returnList.add(testData.getTechnicianSymptom().getSymptomSet());
		}

		return returnList;
	}

}
