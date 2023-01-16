package com.nissan.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.DateUtil;
import com.nissan.enums.Roles;
import com.nissan.enums.VCATCheckpoint;
import com.nissan.pages.EngManagerPage;
import com.nissan.pages.components.ManageSystemRulesComponent;
import com.nissan.pages.components.NAARulesComponent;

public class NAARulesUtil {

	private NAARulesUtil() {

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

	public static Map<VCATCheckpoint, Boolean> evaluateNAARules(Map<String, String> dataMap) {

		String dealerCode = dataMap.get("Dealer Code");
		String vehicleVIN = dataMap.get(VIN);
		String modelCode = dataMap.get("ModelLineCode");
		String component = dataMap.get(COMPONENT);
		String vehicleManufacturingDate = dataMap.get("ManufacturedDate");
		String vehicleInserviceDate = dataMap.get("InServiceDate");
		String vehicleMileage = dataMap.get(MILEAGE);
		EngManagerPage engManagerPage = new EngManagerPage();
		engManagerPage.waitForPageToLoad();
		engManagerPage.getLeftSideMenuElement("Manage System Rules").click();

		ManageSystemRulesComponent manageSystemRulesComponent = new ManageSystemRulesComponent();
		manageSystemRulesComponent.getMenuElement("No Automated Approval Rules").click();
		engManagerPage.closeTab("Manage System");

		Map<VCATCheckpoint, Boolean> nAARulesMap = new LinkedHashMap<>();

		NAARulesComponent nAARulesComponent = new NAARulesComponent();
		nAARulesMap.put(VCATCheckpoint.ARCDEALER,
				isArCDealer(nAARulesComponent, nAARulesComponent.listARCDealerExemption, dealerCode));

		nAARulesComponent.getHeader(ENGINEERINGREVIEW).click();
		nAARulesMap.put(VCATCheckpoint.MANUFACTURINGDATE,
				checkManDate(nAARulesComponent, modelCode, component, vehicleManufacturingDate));
		nAARulesMap.put(VCATCheckpoint.MONTHSINSERVICE,
				checkMonthInService(nAARulesComponent, modelCode, component, vehicleInserviceDate));
		nAARulesMap.put(VCATCheckpoint.MILEAGE, checkMileage(nAARulesComponent, modelCode, component, vehicleMileage));
		nAARulesMap.put(VCATCheckpoint.VIN, checkVIN(nAARulesComponent, vehicleVIN));
		Boolean setEngReviewTrue = nAARulesMap.get(VCATCheckpoint.MANUFACTURINGDATE)
				|| nAARulesMap.get(VCATCheckpoint.MONTHSINSERVICE) || nAARulesMap.get(VCATCheckpoint.MILEAGE)
				|| nAARulesMap.get(VCATCheckpoint.VIN);
		nAARulesMap.put(VCATCheckpoint.ENGINEERINGREVIEW, setEngReviewTrue);

		nAARulesComponent.getHeader(DEALERSPECIFIC).click();
		nAARulesMap.put(VCATCheckpoint.DEALERSPECIFIC,
				isDealerSpecific(nAARulesComponent.listDealerSpecific, dealerCode));

		nAARulesComponent.getHeader(ADDITIONALRULES).click();
		nAARulesMap.put(VCATCheckpoint.INSERVICEDATEUNAVAILABLE, checkInServiceDateUnavailable(nAARulesComponent,
				INSERVICEDATEUNAVAILABLE, dataMap.get("InServiceDate")));
		nAARulesMap.put(VCATCheckpoint.SERVICECONTRACTCOVERAGE, checkServiceContractCoverage(nAARulesComponent,
				SERVICECONTRACTCOVERAGE, dataMap.get("Payment Assumption")));
		nAARulesMap.put(VCATCheckpoint.PARTSWARRANTYCOVERAGE, checkPartsWarrantyCoverage(nAARulesComponent,
				PARTSWARRANTYCOVERAGE, dataMap.get("Payment Assumption")));
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

	public static Map<VCATCheckpoint, Boolean> evaluateNAARules(String dealerCode, String component,String paymentAssumption,
			Map<String, String> vehicleRefMap, boolean loginRequired, boolean logoutRequired) {

		// String dealerCode = vehicleRefMap.get("Dealer Code");
		String vehicleVIN = vehicleRefMap.get(VIN);
		String modelCode = vehicleRefMap.get("ModelLineCode");
		// String component = vehicleRefMap.get(COMPONENT);
		String vehicleManufacturingDate = vehicleRefMap.get("ManufacturedDate");
		String vehicleInserviceDate = vehicleRefMap.get("InServiceDate");
		String vehicleMileage = vehicleRefMap.get(MILEAGE);
		EngManagerPage engManagerPage = loginRequired ? new EngManagerPage(Roles.ENGINEERINGMANAGER)
				: new EngManagerPage();
		engManagerPage.waitForPageToLoad();
		engManagerPage.getLeftSideMenuElement("Manage System Rules").click();

		ManageSystemRulesComponent manageSystemRulesComponent = new ManageSystemRulesComponent();
		manageSystemRulesComponent.getMenuElement("No Automated Approval Rules").click();
		engManagerPage.closeTab("Manage System");

		Map<VCATCheckpoint, Boolean> nAARulesMap = new LinkedHashMap<>();

		NAARulesComponent nAARulesComponent = new NAARulesComponent();
		nAARulesMap.put(VCATCheckpoint.ARCDEALER,
				isArCDealer(nAARulesComponent, nAARulesComponent.listARCDealerExemption, dealerCode));

		nAARulesComponent.getHeader(ENGINEERINGREVIEW).click();
		nAARulesMap.put(VCATCheckpoint.MANUFACTURINGDATE,
				checkManDate(nAARulesComponent, modelCode, component, vehicleManufacturingDate));
		nAARulesMap.put(VCATCheckpoint.MONTHSINSERVICE,
				checkMonthInService(nAARulesComponent, modelCode, component, vehicleInserviceDate));
		nAARulesMap.put(VCATCheckpoint.MILEAGE, checkMileage(nAARulesComponent, modelCode, component, vehicleMileage));
		nAARulesMap.put(VCATCheckpoint.VIN, checkVIN(nAARulesComponent, vehicleVIN));
		Boolean setEngReviewTrue = nAARulesMap.get(VCATCheckpoint.MANUFACTURINGDATE)
				|| nAARulesMap.get(VCATCheckpoint.MONTHSINSERVICE) || nAARulesMap.get(VCATCheckpoint.MILEAGE)
				|| nAARulesMap.get(VCATCheckpoint.VIN);
		nAARulesMap.put(VCATCheckpoint.ENGINEERINGREVIEW, setEngReviewTrue);

		nAARulesComponent.getHeader(DEALERSPECIFIC).click();
		nAARulesMap.put(VCATCheckpoint.DEALERSPECIFIC,
				isDealerSpecific(nAARulesComponent.listDealerSpecific, dealerCode));

		nAARulesComponent.getHeader(ADDITIONALRULES).click();
		nAARulesMap.put(VCATCheckpoint.INSERVICEDATEUNAVAILABLE, checkInServiceDateUnavailable(nAARulesComponent,
				INSERVICEDATEUNAVAILABLE, vehicleRefMap.get("InServiceDate")));
		nAARulesMap.put(VCATCheckpoint.SERVICECONTRACTCOVERAGE, checkServiceContractCoverage(nAARulesComponent,
				SERVICECONTRACTCOVERAGE, paymentAssumption));
		nAARulesMap.put(VCATCheckpoint.PARTSWARRANTYCOVERAGE, checkPartsWarrantyCoverage(nAARulesComponent,
				PARTSWARRANTYCOVERAGE, paymentAssumption));
		Boolean setAdditionalRulesTrue = nAARulesMap.get(VCATCheckpoint.INSERVICEDATEUNAVAILABLE)
				|| nAARulesMap.get(VCATCheckpoint.SERVICECONTRACTCOVERAGE)
				|| nAARulesMap.get(VCATCheckpoint.PARTSWARRANTYCOVERAGE);
		nAARulesMap.put(VCATCheckpoint.ADDITIONALRULES, setAdditionalRulesTrue);

		Boolean setRouteToVCAT = nAARulesMap.get(VCATCheckpoint.ARCDEALER)
				|| nAARulesMap.get(VCATCheckpoint.ENGINEERINGREVIEW) || nAARulesMap.get(VCATCheckpoint.DEALERSPECIFIC)
				|| nAARulesMap.get(VCATCheckpoint.ADDITIONALRULES);
		nAARulesMap.put(VCATCheckpoint.ROUTETOVCAT, setRouteToVCAT);
		nAARulesMap.put(VCATCheckpoint.ROUTETONESNA, nAARulesMap.get(VCATCheckpoint.SERVICECONTRACTCOVERAGE));
		if (logoutRequired) {
			engManagerPage.logOut();
		}
		return nAARulesMap;

	}

	private static Boolean checkInServiceDateUnavailable(NAARulesComponent nAARulesComponent, String rule,
			String inServiceDate) {

		return nAARulesComponent.getCheckBoxValue(rule).isSelected() && inServiceDate.equals("");

	}

	private static Boolean checkServiceContractCoverage(NAARulesComponent nAARulesComponent, String rule,
			String paymentAssumption) {

		return nAARulesComponent.getCheckBoxValue(rule).isSelected()
				&& paymentAssumption.equals("Service Contract Claim");

	}

	private static Boolean checkPartsWarrantyCoverage(NAARulesComponent nAARulesComponent, String rule,
			String paymentAssumption) {

		return nAARulesComponent.getCheckBoxValue(rule).isSelected() && paymentAssumption.equals("Parts Warranty");

	}

	private static Boolean checkVIN(NAARulesComponent nAARulesComponent, String vIN) {

		List<ExtWebElement> rowElements = nAARulesComponent.listVIN;

		String tableName = "VIN";
		String vehicleRefStampingNum = vIN.substring(0, 11);
		int chassisNum = Integer.parseInt(vIN.substring(11));
		for (ExtWebElement rowElement : rowElements) {

			String stampingNum = nAARulesComponent.getValue(rowElement, tableName, "StampingNumber");
			if (stampingNum.equalsIgnoreCase(vehicleRefStampingNum)) {
				String operator = nAARulesComponent.getDropDownValue(rowElement, tableName, "Comparator");
				if (operator.equalsIgnoreCase("between")) {

					int chasisRangeFrom = Integer
							.parseInt(nAARulesComponent.getValue(rowElement, tableName, "ChasisRangeFrom"));
					int chasisRangeTo = Integer
							.parseInt(nAARulesComponent.getValue(rowElement, tableName, "ChasisNumberTo"));
					return isBetween(chassisNum, chasisRangeFrom, chasisRangeTo);

				} else if (operator.equalsIgnoreCase("equal")) {
					int chasisRangeFrom = Integer
							.parseInt(nAARulesComponent.getValue(rowElement, tableName, "ChasisRangeFrom"));
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

	private static Boolean checkMileage(NAARulesComponent nAARulesComponent, String modelCode, String vehicleComponent,
			String vehicleMileage) {

		List<ExtWebElement> rowElements = nAARulesComponent.listMileage;
		String tableName = MILEAGE;
		for (ExtWebElement rowElement : rowElements) {
			String modelName = nAARulesComponent.getDropDownValue(rowElement, tableName, MODEL);
			if (modelName.contains(modelCode)) {
				String component = nAARulesComponent.getDropDownValue(rowElement, tableName, COMPONENT);
				if (component.equalsIgnoreCase(vehicleComponent) || component.equals("Select")) {

					long mileage = Long.parseLong(nAARulesComponent.getValue(rowElement, tableName, MILEAGE));

					long vehicleRefMileage = Long.parseLong(vehicleMileage);

					if (vehicleRefMileage <= mileage) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static Boolean checkMonthInService(NAARulesComponent nAARulesComponent, String modelCode,
			String vehicleComponent, String inServiceDate) {
		String tableName = "MonthsInService";
		List<ExtWebElement> rowElements = nAARulesComponent.listMonthInService;
		Date vehicleRefInserviceDate = DateUtil
				.parseDate(DateUtil.getFormatedDate(inServiceDate, "yyyy-MM-dd", YYYYMMDD), YYYYMMDD);

		int diff = DateUtil.getMonthsDifference(vehicleRefInserviceDate, new Date());
		for (ExtWebElement rowElement : rowElements) {
			String modelName = nAARulesComponent.getDropDownValue(rowElement, tableName, MODEL);
			if (modelName.contains(modelCode)) {
				String component = nAARulesComponent.getDropDownValue(rowElement, tableName, COMPONENT);
				if (component.equalsIgnoreCase(vehicleComponent) || component.equals("Select")) {

					int monthsInService = Integer
							.parseInt(nAARulesComponent.getValue(rowElement, tableName, "MonthsInService"));

					if (diff <= monthsInService) {
						return true;
					}

				}
			}
		}
		return false;
	}

	private static Boolean checkManDate(NAARulesComponent nAARulesComponent, String modelCode, String vehicleComponent,
			String manufactureDate) {
		String tableName = "ManufacturingDate";
		for (ExtWebElement ele : nAARulesComponent.listManufacturingDates) {

			String modelName = nAARulesComponent.getDropDownValue(ele, tableName, MODEL);
			if (modelName.contains(modelCode)) {
				String component = nAARulesComponent.getDropDownValue(ele, tableName, COMPONENT);
				if (component.equals(vehicleComponent)) {
					String fromDate = DateUtil.getFormatedDate(nAARulesComponent.getValue(ele, tableName, "From"),
							"M/d/yyyy", YYYYMMDD);
					String toDate = DateUtil.getFormatedDate(nAARulesComponent.getValue(ele, tableName, "To"),
							"M/d/yyyy", YYYYMMDD);
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
			returnCondition= true;
		}

		return returnCondition;
	}

	private static Boolean isDealerSpecific(List<ExtWebElement> listDealerSpecific, String dealerCode) {

		return checkDealerInList(listDealerSpecific, dealerCode);
	}

	// TODO need to add logic to check if the dealer is arc dealer or not
	private static Boolean isArCDealer(NAARulesComponent nAARulesComponent, List<ExtWebElement> listARCDealer,
			String dealerCode) {

		boolean arcDealer = nAARulesComponent.isARCDealer(dealerCode, listARCDealer.size() + 1);
		if (arcDealer) {
			boolean isDealerInList = checkDealerInList(listARCDealer, dealerCode);
			if (!isDealerInList) {
				return true;
			}
		}

		return false;

	}

	private static Boolean checkDealerInList(List<ExtWebElement> listDealer, String dealerCode) {

		String value = "value";
		for (ExtWebElement ele : listDealer) {
			if (ele.getAttribute(value).startsWith(dealerCode)) {
				return true;
			}

		}

		return false;

	}

}
