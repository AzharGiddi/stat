package com.nissan.utils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.api.jsontojavaobjects.ArcDealer;
import com.nissan.automation.core.api.jsontojavaobjects.NAARules;
import com.nissan.automation.core.api.jsontojavaobjects.NAARules.ManufacturingDate;
import com.nissan.automation.core.api.jsontojavaobjects.NAARules.Mileage;
import com.nissan.automation.core.api.jsontojavaobjects.NAARules.MonthsInService;
import com.nissan.automation.core.api.jsontojavaobjects.NAARules.VIN;
import com.nissan.automation.core.utils.APIUtil;
import com.nissan.automation.core.utils.DateUtil;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.databeans.TestDataBean;
import com.nissan.enums.UserData;
import com.nissan.enums.VCATCheckpoint;
import com.nissan.pages.components.NAARulesComponent;

public class NAARulesAPIUtil {

	private NAARulesAPIUtil() {

	}

	private static final String ARCDEALER = "ARC Dealer";
	private static final String ENGINEERINGREVIEW = "Engineering Review";
	private static final String MANUFACTURINGDATE = "Manufacturing Date";
	private static final String MONTHSINSERVICE = "Month In Service";
	private static final String MILEAGE = "Mileage";
	private static final String VIN = "VIN";
	private static final String DEALERSPECIFIC = "Dealer Specific";
	private static final String ADDITIONALRULES = "Additional Rules";
	private static final String INSERVICEDATEUNAVAILABLE = "In-service date unavailable";
	private static final String SERVICECONTRACTCOVERAGE = "Service Contract Coverage";
	private static final String PARTSWARRANTYCOVERAGE = "Parts Warranty Coverage";
	private static final String COMPONENT = "Component";
	private static final String YYYYMMDD = "yyyyMMdd";
	private static final String MODEL = "Model";

	public static Map<VCATCheckpoint, Boolean> evaluateNAARules(TestDataBean testData) {

		String dealerCode = testData.getUserData().get(UserData.DEALERCODE);
		Map<String, String> vehicleRefMap = testData.getVehicleRefDetails();
		String vehicleVIN = testData.getUserData().get(UserData.VIN);
		String modelCode = vehicleRefMap.get("ModelLineCode");
		String component = testData.getUserData().get(UserData.COMPONENT);
		String vehicleManufacturingDate = vehicleRefMap.get("ManufacturedDate");
		String vehicleInserviceDate = vehicleRefMap.get("InServiceDate");
		String vehicleMileage = testData.getMileage();
		String paymentAssumption = testData.getUserData().get(UserData.PAYMENTASSUMPTION);
		Map<VCATCheckpoint, Boolean> nAARulesMap = new LinkedHashMap<>();
		String nAARulesListUrl = ConfigurationManager.getBundle().getString("base.uri")+"data/D_NAARulesList";
		String gUIId = APIUtil.getResponseObject(nAARulesListUrl).jsonPath().getString("pxResults.pyGUID").replace("[", "").replace("]","");
		String nAARulesContentUrl = ConfigurationManager.getBundle().getString("base.uri")+String.format("data/D_NAARules?pyGUID=%s",gUIId);
		String nAARulesContent = APIUtil.getResponseAsString(nAARulesContentUrl);
		Gson gson = new Gson();
		NAARules nAARules = gson.fromJson(nAARulesContent, NAARules.class);
		nAARulesMap.put(VCATCheckpoint.ARCDEALER, isArCDealerExemption(nAARules.getArcDealerExemptionList(), dealerCode));
		nAARulesMap.put(VCATCheckpoint.MANUFACTURINGDATE,
				checkManDate(modelCode, component, vehicleManufacturingDate, nAARules.manufacturingDateList));
		nAARulesMap.put(VCATCheckpoint.MONTHSINSERVICE,
				checkMonthInService(nAARules.monthsInServiceList, modelCode, component, vehicleInserviceDate));
		nAARulesMap.put(VCATCheckpoint.MILEAGE,
				checkMileage(nAARules.mileageList, modelCode, component, vehicleMileage));
		nAARulesMap.put(VCATCheckpoint.VIN, checkVIN(nAARules.vINList, vehicleVIN));
		Boolean setEngReviewTrue = nAARulesMap.get(VCATCheckpoint.MANUFACTURINGDATE)
				|| nAARulesMap.get(VCATCheckpoint.MONTHSINSERVICE) || nAARulesMap.get(VCATCheckpoint.MILEAGE)
				|| nAARulesMap.get(VCATCheckpoint.VIN);
		nAARulesMap.put(VCATCheckpoint.ENGINEERINGREVIEW, setEngReviewTrue);
		nAARulesMap.put(VCATCheckpoint.DEALERSPECIFIC, isDealerSpecific(nAARules.getDealerSpecificList(), dealerCode));
		nAARulesMap.put(VCATCheckpoint.INSERVICEDATEUNAVAILABLE,
				checkInServiceDateUnavailable(nAARules.inServiceDateAvailability, vehicleRefMap.get("InServiceDate")));
		nAARulesMap.put(VCATCheckpoint.SERVICECONTRACTCOVERAGE,
				checkServiceContractCoverage(nAARules.serviceContractCoverage, paymentAssumption));
		nAARulesMap.put(VCATCheckpoint.PARTSWARRANTYCOVERAGE,
				checkPartsWarrantyCoverage(nAARules.partsWarrantyCoverage, paymentAssumption));
		Boolean setAdditionalRulesTrue = nAARulesMap.get(VCATCheckpoint.INSERVICEDATEUNAVAILABLE)
				|| nAARulesMap.get(VCATCheckpoint.SERVICECONTRACTCOVERAGE)
				|| nAARulesMap.get(VCATCheckpoint.PARTSWARRANTYCOVERAGE);
		nAARulesMap.put(VCATCheckpoint.ADDITIONALRULES, setAdditionalRulesTrue);
		Boolean setRouteToVCAT = nAARulesMap.get(VCATCheckpoint.ARCDEALER)
				|| nAARulesMap.get(VCATCheckpoint.ENGINEERINGREVIEW) || nAARulesMap.get(VCATCheckpoint.DEALERSPECIFIC)
				|| nAARulesMap.get(VCATCheckpoint.ADDITIONALRULES);
		nAARulesMap.put(VCATCheckpoint.ROUTETOVCAT, setRouteToVCAT);
		nAARulesMap.put(VCATCheckpoint.ROUTETONESNA, nAARulesMap.get(VCATCheckpoint.SERVICECONTRACTCOVERAGE));
		return nAARulesMap;

	}

	private static Boolean checkInServiceDateUnavailable(String inServiceDateFlag, String inServiceDate) {

		return Boolean.parseBoolean(inServiceDateFlag) && inServiceDate.equals("");

	}

	private static Boolean checkServiceContractCoverage(String serviceContractCoverage,String paymentAssumption) {

		return Boolean.parseBoolean(serviceContractCoverage) && paymentAssumption.equals("Service Contract Claim");

	}

	private static Boolean checkPartsWarrantyCoverage(String partsWarrantyCoverage, String paymentAssumption) {

		return Boolean.parseBoolean(partsWarrantyCoverage) && paymentAssumption.equals("Parts Warranty");

	}

	private static Boolean isDealerSpecific(List<String> listDealerSpecific, String dealerCode) {

		return checkDealerInList(listDealerSpecific, dealerCode);
	}

	private static Boolean checkVIN(List<VIN> listVIN, String vIN) {

		String vehicleRefStampingNum = vIN.substring(0, 11);
		int chassisNum = Integer.parseInt(vIN.substring(11));
		for (VIN vin : listVIN) {

			String stampingNum = vin.stampingNumber;
			if (stampingNum.equalsIgnoreCase(vehicleRefStampingNum)) {
				String operator = Objects.isNull(vin.comparator)?"":vin.comparator;
				if (operator.equalsIgnoreCase("between")) {

					int chasisRangeFrom = Integer.parseInt(vin.chasisRangeFrom);
					int chasisRangeTo = Integer.parseInt(vin.chasisNumberTo);
					return isBetween(chassisNum, chasisRangeFrom, chasisRangeTo);

				} else if (operator.equalsIgnoreCase("equal")) {
					int chasisRangeFrom = Integer.parseInt(vin.chasisRangeFrom);
					return isEqual(chassisNum, chasisRangeFrom);
				}

			}
		}

		return false;

	}

	private static Boolean isBetween(int number, int lowerBound, int upperBound) {

		return (lowerBound <= number && number <= upperBound);

	}

	private static Boolean isEqual(int number1, int number2) {

		return (number1 == number2);
	}

	private static Boolean checkMileage(List<Mileage> mileageList, String modelCode, String vehicleComponent,
			String vehicleMileage) {

		for (Mileage mileage : mileageList) {
			String modelName = Objects.isNull(mileage.modelName)?"":mileage.modelName;
			if (modelName.contains(modelCode)) {
				String component = Objects.isNull(mileage.componentCategory)?"Select":mileage.componentCategory;
				if (component.equalsIgnoreCase(vehicleComponent) || component.equals("Select")) {

					long mileageNAA = Long.parseLong(mileage.mileage);

					long vehicleRefMileage = Long.parseLong(vehicleMileage);

					return vehicleRefMileage <= mileageNAA;
				}
			}
		}
		return false;
	}

	private static Boolean checkMonthInService(List<MonthsInService> listMonthInService, String modelCode,
			String vehicleComponent, String inServiceDate) {

		Date vehicleRefInserviceDate = DateUtil
				.parseDate(DateUtil.getFormatedDate(inServiceDate, "yyyy-MM-dd", YYYYMMDD), YYYYMMDD);

		int diff = DateUtil.getMonthsDifference(vehicleRefInserviceDate, new Date());
		for (MonthsInService monthsInService : listMonthInService) {
			String modelName = Objects.isNull(monthsInService.modelName)?"":monthsInService.modelName;
			if (modelName.contains(modelCode)) {
				String component = monthsInService.componentCategory;
				if (Objects.isNull(component) || component.equalsIgnoreCase(vehicleComponent)) {

					int monthsInServiceNAA = Integer.parseInt(monthsInService.inServiceDate);

					if (diff <= monthsInServiceNAA) {
						return true;
					}

				}
			}
		}
		return false;
	}

	private static Boolean checkManDate(String modelCode, String vehicleComponent, String manufactureDate,
			List<ManufacturingDate> listManufacturingDates) {

		for (ManufacturingDate manufacturingDate : listManufacturingDates) {

			String modelName = Objects.isNull(manufacturingDate.modelName)?"":manufacturingDate.modelName;
			if (modelName.contains(modelCode)) {
				String component = manufacturingDate.componentCategory;
				if (component.equals(vehicleComponent)) {
					String fromDate = DateUtil.getFormatedDate(manufacturingDate.fromDate, "M/d/yyyy", YYYYMMDD);
					String toDate = DateUtil.getFormatedDate(manufacturingDate.toDate, "M/d/yyyy", YYYYMMDD);
					String date = DateUtil.getFormatedDate(manufactureDate, "yyyy-MM-dd", YYYYMMDD);
					return compareDates(date, toDate, fromDate);

				}
			}

		}

		return false;
	}

	private static Boolean compareDates(String date, String toDate, String fromDate) {
		boolean returnCondition = false;
		Date dateLocal = DateUtil.parseDate(date, YYYYMMDD);
		Date fromDateLocal = DateUtil.parseDate(fromDate, YYYYMMDD);
		Date toDateLocal = DateUtil.parseDate(toDate, YYYYMMDD);
		int d1 = fromDateLocal.compareTo(dateLocal);
		int d2 = toDateLocal.compareTo(dateLocal);
		if (d1 <= 0 && d2 >= 0) {
			returnCondition = true;
		}

		return returnCondition;
	}

	// TODO need to add logic to check if the dealer is arc dealer or not
	private static Boolean isArCDealerExemption(List<String> listARCDealer, String dealerCode) {

		boolean arcDealer = isARCDealer(dealerCode);
		if (arcDealer) {
			boolean isDealerInList = checkDealerInList(listARCDealer, dealerCode);
			if (!isDealerInList) {
				return true;
			}
		}

		return false;

	}

	private static Boolean isARCDealer(String dealerCode) {

		String url = ConfigurationManager.getBundle().getString("base.uri")+"data/D_FormDealersList?DealerType=ARC&CountryCode=US";
		String response = APIUtil.getResponseAsString(url);
		Gson gson = new Gson();
		ArcDealer arcDealerList = gson.fromJson(response, ArcDealer.class);

		return arcDealerList.isArcDealer(dealerCode);

	}

	private static Boolean checkDealerInList(List<String> listDealer, String dealerCode) {

		for (String dealer : listDealer) {
			if (dealer.equals(dealerCode)) {
				return true;
			}

		}

		return false;

	}

}
