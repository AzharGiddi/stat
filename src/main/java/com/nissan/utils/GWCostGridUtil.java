package com.nissan.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.testng.annotations.Test;

import com.nissan.automation.core.utils.APIUtil;
import com.nissan.automation.core.utils.DateUtil;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.reports.ExtentLogger;
import com.nissan.reports.ExtentReport;

import io.restassured.response.Response;

public class GWCostGridUtil {

	private GWCostGridUtil() {

	}

	private static final Log logger = LogFactory.getLog(GWCostGridUtil.class);
	
	
	private static List<Map<String, String>> getListOfMaps(String dataTableName) {

		Response resp = APIUtil.getResponse(dataTableName);

		return resp.jsonPath().get("pxResults");

	}

	private static List<Map<String, String>> getListOfMaps(String dataTableName, Map<String, String> params) {

		Response resp = APIUtil.getResponse(dataTableName, params);

		return resp.jsonPath().get("pxResults");

	}

	private static List<Map<String, String>> getListOfGrids(String dataTableName) {

		return getListOfMaps(dataTableName);

	}

	private static String getApplicableGrid(String make, String model) {
		String applicableModel = "";

		List<Map<String, String>> listOfGrids = getListOfGrids("D_DistinctMakeAndModel");
		for (Map<String, String> grid : listOfGrids) {
			String gridMake = grid.get("Make");
			if (gridMake.equals(make) && grid.containsKey("VehicleModelCode")) {
				String gridModel = grid.get("VehicleModelCode");
				if (gridModel.contains(model)) {
					applicableModel = gridModel;
					break;
				}
			}
		}

		logger.info("Model is " + applicableModel);

		return applicableModel;
	}
	
	private static List<String> getApplicableGrids(String make, String model) {
		List<String> applicableModelsList = new ArrayList<>();

		List<Map<String, String>> listOfGrids = getListOfGrids("D_DistinctMakeAndModel");
		for (Map<String, String> grid : listOfGrids) {
			String gridMake = grid.get("Make");
			if (gridMake.equals(make) && grid.containsKey("VehicleModelCode")) {
				String gridModel = grid.get("VehicleModelCode");
				if (gridModel.contains(model)) {
					applicableModelsList.add(gridModel);
					break;
				}
			}
		}
		applicableModelsList.add("");

		logger.info("Model(s) list is " + applicableModelsList);

		return applicableModelsList;
	}
	
	private static List<Map<String, String>> getListOfEffectiveDates(String make, String model) {

		Map<String, String> params = new LinkedHashMap<>();

		params.put("Make", make);
		if (!model.equals("")) {
			params.put("Model", model);
		}

		// Response resp = APIUtil.getResponse("D_DistinctEffectiveDates", params);

		return getListOfMaps("D_DistinctEffectiveDates", params);

	}

	private static List<Date> getEffectiveDateList(List<Map<String, String>> listOfMaps, String dateName) {
		List<Date> list = new ArrayList<>();
		for (Map<String, String> map : listOfMaps) {

			if (map.containsKey(dateName)) {
				Date date = DateUtil.parseDate(map.get(dateName), "yyyy-MM-dd");
				list.add(date);
			}
		}

		return list;
	}

	private static String getEffectiveDate(String make, String applicableGrid, Date roOpenDate) {

		//Date returnDate = null;

		List<Map<String, String>> listofEffectiveDateMap = getListOfEffectiveDates(make, applicableGrid);

		List<Date> list = getEffectiveDateList(listofEffectiveDateMap, "EffectiveDate");

		/*for (Date date : list) {
			int diff = DateUtil.getDateDifference(roOpenDate, date);
			if (diff > 0) {
				returnDate = date;
				break;
			}
		}
		String effectiveDate = Objects.isNull(returnDate) ? "" : DateUtil.getFormatedDate(returnDate, "yyyyMMdd");
		
		// return DateUtil.getFormatedDate(returnDate, "yyyyMMdd");;
		logger.info("Effective date is " + effectiveDate);
		return effectiveDate;*/
		
		return getEffectiveDateString(list,roOpenDate);
	}
	
	private static String getEffectiveDateString(List<Date> dateList, Date roOpenDate) {
		Date returnDate = null;
		
		for (Date date : dateList) {
			int diff = DateUtil.getDateDifference(roOpenDate, date);
			if (diff > 0) {
				returnDate = date;
				break;
			}
		}
		return Objects.isNull(returnDate) ? "" : getFormattedDate(returnDate, "yyyyMMdd");
		
		}
	
	private static String getFormattedDate(Date date, String format) {
		
		String formattedDate = DateUtil.getFormatedDate(date, format);
		
		logger.info("Effective date is: " +formattedDate);
		
		return formattedDate;
		
	}

	private static List<Map<String, String>> getListOfCostGrids(String coverageType, String make, String model,
			String roOpenDateString) {

		Map<String, String> params = new LinkedHashMap<>();

		params.put("CoverageType", coverageType);

		params.put("Make", make);

		params.put("Model", getApplicableGrid(make, model));

		Date roOpenDate = DateUtil.parseDate(roOpenDateString, "yyyy-MM-dd");

		params.put("EffectiveDate", getEffectiveDate(make, params.get("Model"), roOpenDate));

		return getListOfMaps("D_GWPercentage", params);

	}
	
	private static List<Map<String, String>> getListOfCostGrids_1(String coverageType, String make, String model,
			String roOpenDateString) {

		Map<String, String> params = new LinkedHashMap<>();

		params.put("CoverageType", coverageType);

		params.put("Make", make);

		params.put("Model", model);

		Date roOpenDate = DateUtil.parseDate(roOpenDateString, "yyyy-MM-dd");
		
		String effectiveDateString = getEffectiveDate(make, params.get("Model"), roOpenDate);
		
		if(effectiveDateString.equals("")) {
			return new ArrayList<>();
		}
		
		params.put("EffectiveDate", effectiveDateString);

		return getListOfMaps("D_GWPercentage", params);

	}

	public static Map<String, String> getCostGrid(String coverageType, String make, String model,
			String roOpenDateString, Map<String, String> vehicleWarranty) {
		
		Map<String, String> returnCostGrid = new LinkedHashMap<>();
		List<Map<String, String>> listOfCostGrids = getListOfCostGrids(coverageType, make, model, roOpenDateString);

		float mileageOutOfWarranty = Float.parseFloat(
				vehicleWarranty.containsKey("MILEAGE_OUT_WARR") ? vehicleWarranty.get("MILEAGE_OUT_WARR") : "0");
		float monthsOutOfWarranty = Float.parseFloat(
				vehicleWarranty.containsKey("MONTH_OUT_WARR") ? vehicleWarranty.get("MONTH_OUT_WARR") : "0");
		logger.info("vehicle warranty: mileageOW=" + mileageOutOfWarranty + " ,monthsOW=" + monthsOutOfWarranty);

		for (Map<String, String> map : listOfCostGrids) {
			float gridMileageOutOfWarranty = Float.parseFloat(map.containsKey("Mileage") ? map.get("Mileage") : "0.0");
			float gridMonthsOutOfWarranty = Float
					.parseFloat(map.containsKey("TimeInMonths") ? map.get("TimeInMonths") : "0.0");
			logger.info("Grid warranty details: mileageOW=" + gridMileageOutOfWarranty + " ,monthsOW="
					+ gridMonthsOutOfWarranty);
			if (mileageOutOfWarranty <= gridMileageOutOfWarranty && monthsOutOfWarranty <= gridMonthsOutOfWarranty) {
				returnCostGrid = map;
				break;
			}
		}

		return returnCostGrid;
	}
	
	public static Map<String, String> getCostGrid_1(String coverageType, String make, String model,	String roOpenDateString, Map<String, String> vehicleWarranty) {
		List<String> applicableGrids = getApplicableGrids(make, model);
		Map<String, String> returnCostGrid = new LinkedHashMap<>();
		
	applicablegrids:	for(String applicableGrid: applicableGrids) {
		List<Map<String, String>> listOfCostGrids = getListOfCostGrids_1(coverageType, make, applicableGrid, roOpenDateString);
		logger.info("Checking grid :"+applicableGrid);	
		if(listOfCostGrids.isEmpty()) {
				logger.info("No applicable effective date found in \"Make:"+make+":"+applicableGrid+"\", skipping to next grid...");
						
				continue;
			}
				
		float mileageOutOfWarranty = Float.parseFloat(
				vehicleWarranty.containsKey("MILEAGE_OUT_WARR") ? vehicleWarranty.get("MILEAGE_OUT_WARR") : "0");
		float monthsOutOfWarranty = Float.parseFloat(
				vehicleWarranty.containsKey("MONTH_OUT_WARR") ? vehicleWarranty.get("MONTH_OUT_WARR") : "0");
		logger.info("vehicle warranty: mileageOW=" + mileageOutOfWarranty + " ,monthsOW=" + monthsOutOfWarranty);

		for (Map<String, String> map : listOfCostGrids) {
			float gridMileageOutOfWarranty = Float.parseFloat(map.containsKey("Mileage") ? map.get("Mileage") : "0.0");
			float gridMonthsOutOfWarranty = Float
					.parseFloat(map.containsKey("TimeInMonths") ? map.get("TimeInMonths") : "0.0");
			logger.info("Current Grid details: mileageOW=" + gridMileageOutOfWarranty + " ,monthsOW="
					+ gridMonthsOutOfWarranty);
			if (mileageOutOfWarranty <= gridMileageOutOfWarranty && monthsOutOfWarranty <= gridMonthsOutOfWarranty) {
				returnCostGrid = map;
				logger.info("Applicable Grid details: mileageOW=" + gridMileageOutOfWarranty + " ,monthsOW="
					+ gridMonthsOutOfWarranty);
				return returnCostGrid;
			}
		}
		
		logger.info("No applicable effective date match found in \"Make:"+make+":"+applicableGrid+"\", skipping to next grid...");
	}
		if(returnCostGrid.isEmpty()) {
			throw new CustomRuntimeException("No Applicable Cost grid found");
		}
		return returnCostGrid;
	}

	public static List<String> getCostGridArray(Map<String, String> costGrid) {

		List<String> returnArray = new ArrayList<>();
		returnArray.add(costGrid.get("CustomerPercentage"));
		returnArray.add(costGrid.get("DealerPercentage"));
		returnArray.add(costGrid.get("NissanPercentage"));
		return returnArray;

	}

	@Test
	public void test() {

		Map<String, String> vehicleWarranty = DVehicleWarrantyUtil.getVehicleWarrantyMap("N", "2015", "62000", "JX56AA",
				"3121428X7C", "KNMAT2MT2FP529932", "20211022");

		Map<String, String> map = getCostGrid_1("Powertrain", "N", "T32", "2021-10-22", vehicleWarranty);

		logger.info(map + "map sixe is " + map.size());

		
	//	getApplicableGrids("N", "T32");
	}

}
