package com.nissan.utils;

//import static com.nissan.bdd.fone.stepdefinitions.BaseStep.testData;
import static com.nissan.driver.DriverManager.getDriver;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.APIUtil;
import com.nissan.automation.core.utils.DateUtil;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.automation.core.utils.XMLUtil;
import com.nissan.automation.core.utils.dataprovider.PFPOpCodeTestData;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.databeans.TestDataBean;
import com.nissan.databeans.TestDataBeanManager;
import com.nissan.enums.Roles;
import com.nissan.enums.UserData;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.exceptions.ROCreationException;
import com.nissan.pages.EngManagerPage;
import com.nissan.pages.components.DatabaseQueryRunnerComponent;
import com.nissan.pages.components.RepairOptionsComponent;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.DCategoryByPFPOPCODEUtil.ComponentCategoryList.pxResults;
import com.nissan.utils.ROAPIUtil.RODetails.PXResults;

import io.restassured.path.json.JsonPath;

public class ROAPIUtil {

	private ROAPIUtil() {

	}

	private static final Log logger = LogFactory.getLog(ROAPIUtil.class);
		
	public static String getrONumber(String dealerCode, String vin, String date) {

		return generateUniqueRONumber(dealerCode, vin, date,false).trim();
	}
	
	public static String getrONumber(String dealerCode, String vin, String date, boolean isWarranty) {

		return generateUniqueRONumber(dealerCode, vin, date,isWarranty).trim();
	}

	public static String getRONumber() {
		return TestDataBeanManager.getTestData().getRoNumber();
	}

	public static void setrONumber(String rONumber) {
		TestDataBeanManager.getTestData().setRoNumber(rONumber);
	}

	public static String getDateTimeString() {

		//return dateTimeString;
		return TestDataBeanManager.getTestData().getrOOpenDate();
	}

	private static String getTimeString() {

		return LocalDate.now().minus(Period.ofDays(new Random().nextInt(299))).toString();

	}

	private static String getTimeString(int days) {
		int max = days * days;
		int min = days;
		int noOfDays = (int) ((Math.random() * ((max - min) + 1)) + min);
		return LocalDate.now().minus(Period.ofDays(noOfDays)).toString();

	}

	private static String getTimeString(String date, int days) {
		int max = days * days;
		int min = days;
		int noOfDays = (int) ((Math.random() * ((max - min) + 1)) + min);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);

		LocalDate localDate = LocalDate.parse(date, format);

		return localDate.minus(Period.ofDays(noOfDays)).toString();

	}
	
	private static String getFutureTimeString(String date, int days) {
		int max = days * days;
		int min = days;
		int noOfDays = (int) ((Math.random() * ((max - min) + 1)) + min);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);

		LocalDate localDate = LocalDate.parse(date, format);

		return localDate.plus(Period.ofDays(noOfDays)).toString();

	}

	public static void setDateTimeString(String dateTimeString) {
		
		TestDataBeanManager.getTestData().setrOOpenDate(dateTimeString);
		//ROAPIUtil.dateTimeString = dateTimeString;
	}

	// TODO update time statement in below method to take dynamic inputs for
	// dateInsideWarranty
	private static String generateUniqueRONumber(String dealerCode, String vin, String date,boolean isWarranty) {
		String rONumberToReturn = "";

		int resultCount;
		String time = date;
		boolean isUnique = false;
		boolean isDateUnique = false;
		boolean isROUnique = false;
		int dateDiff = 2;
		while (!isUnique) {
			rONumberToReturn = isROUnique? rONumberToReturn: getUniqueNum(isROUnique);
			if(isWarranty) {
				time = getUniqueFutureDate(isUnique, date.replace("-", ""), dateDiff++);
			}else {
				time = date.equals("") ? getUniqueDate(isUnique, dateDiff++)
						: getUniqueDate(isUnique, date.replace("-", ""), dateDiff++);
			}
			
			JsonPath getRORequestResponse = getROCount(dealerCode, vin, time.replace("-", ""), rONumberToReturn);
			resultCount = getRORequestResponse.getInt("pxResultCount");
			if (resultCount == 0) {
				isUnique = true;
				break;
			}
			Map<String, Object> resultMap = getRORequestResponse.get("pxResults[0]");
			String rOFromResponse = String.valueOf(resultMap.get("RepairOrderNumber"));
			String responseROOpenDate = String.valueOf(resultMap.get("RepairOrderOpenDate"));

			if (rOFromResponse.equals(rONumberToReturn)) {
				isROUnique = false;
				logger.info(rONumberToReturn + " already exists in the system.");
			} else {
				isROUnique = true;
			}

			if (time.equals(responseROOpenDate)) {
				isDateUnique = false;
				logger.info("A record with RO open date: " + time + " already exists in the system.");
			} else {
				isDateUnique = true;
			}

		}

		setDateTimeString(time);
		return rONumberToReturn;

	}

	private static String getUniqueNum(boolean isUnique) {
		String roToReturn = "";
		Random random;
		if (!isUnique) {

			random = getRandomObject();
			int randInt = 0;
			randInt = random.nextInt(999999);
			String roPrefix = ConfigurationManager.getBundle().getString("ro.prefix");
			roToReturn = roPrefix + "-" + String.valueOf(randInt);

			// setrONumber(rONumber);

		}

		return roToReturn;

	}
	
	private static Random getRandomObject() {
		
		Random random;
		try {
			random = SecureRandom.getInstanceStrong();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new CustomRuntimeException(e.getLocalizedMessage());
		}
		
		return random;
	}

	/*private static String getUniqueDate(boolean isUnique) {

		if (!isUnique) {

			setDateTimeString(getTimeString());

		}

		return dateTimeString;
	}*/

	private static String getUniqueDate(boolean isUnique, int days) {

		
		
		if (!isUnique) {

			setDateTimeString(getTimeString(days));

		}

		return getDateTimeString();
	}

	private static String getUniqueDate(boolean isUnique, String date, int days) {

		if (!isUnique) {

			setDateTimeString(getTimeString(date, days));

		}

		return getDateTimeString();
	}
	
	private static String getUniqueFutureDate(boolean isUnique, String date, int days) {

		if (!isUnique) {

			setDateTimeString(getFutureTimeString(date, days));

		}

		return getDateTimeString();
	}

	private static JsonPath getROCount(String dealerCode, String vin, String date, String rONumber) {
		Map<String, String> params = new LinkedHashMap<>();
		params.put("RONumber", rONumber);
		params.put("DealerCode", dealerCode);
		params.put("VIN", vin);
		params.put("ROOpenDate", date.replace("-", ""));

		String url =ConfigurationManager.getBundle().getString("base.uri")+ String.format(
				"data/D_CaseListReport_Automation?RONumber=%s&DealerCode=%s&VIN=%s&ROOpenDate=%s",
				rONumber, dealerCode, vin, date);

		// return APIUtil.getResponseString("D_CaseListReport_Automation",
		// params).jsonPath();

		return APIUtil.getResponseObject(url).jsonPath();

	}

	private static String getROResponseString(String dealerCode, String vin, String date, String rONumber) {
		Map<String, String> params = new LinkedHashMap<>();
		params.put("RONumber", rONumber);
		params.put("DealerCode", dealerCode);
		params.put("VIN", vin);
		params.put("ROOpenDate", date.replace("-", ""));
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+String.format(	"data/D_CaseListReport_Automation?RONumber=%s&DealerCode=%s&VIN=%s&ROOpenDate=%s",
				rONumber, dealerCode, vin, date);

		// return APIUtil.getResponseString("D_CaseListReport_Automation",
		// params).jsonPath();

		return APIUtil.getResponseObject(url).asString();

	}

	public static String getUniqueNumber(String dealerCode, String vin, String date) {
		String roNumber = "";

		String rOQuery = "Select count(*) from pegadata.pc_nsa_stat_work_grp1 where dealercode = '%s' and vin = '%s' and repairordernumber = '%s' and repairorderopendate = '%s'";
		boolean isUnique = false;
		int resultCount = 0;
		int randInt = 0;
		while (!isUnique) {
			randInt = getRandomObject().nextInt(999999);
			roNumber = "AT-" + String.valueOf(randInt);
			DatabaseQueryRunnerComponent queryRunner = new DatabaseQueryRunnerComponent();
			// queryRunner.waitForComponentToLoad();
			queryRunner.txtboxQueryToRun
					.clearAndSendKeys(String.format(rOQuery, dealerCode, vin, roNumber, date.replace("-", "")));
			queryRunner.btnRun.click();
			queryRunner.txtResultCount.moveToElement();
			resultCount = Integer.parseInt(queryRunner.txtResultCount.getText());
			if (resultCount == 0) {
				isUnique = true;
			}

		}

		return roNumber;

	}

	public static boolean hasDcase(String dealerCode, String vin, String rOnumber, String date) {

		String dCaseQuery = "Select count(*) from pegadata.pr_NSA_FW_STATFW_Data_ROToDiag where dealercode = '%s' and vin = '%s' and (repairordernumber = '%s' or repairorderopendate = '%s')";

		DatabaseQueryRunnerComponent queryRunner = new DatabaseQueryRunnerComponent();
		// queryRunner.waitForComponentToLoad();
		queryRunner.txtboxQueryToRun
				.clearAndSendKeys(String.format(dCaseQuery, dealerCode, vin, rOnumber, date.replace("-", "")));
		queryRunner.btnRun.click();
		queryRunner.txtResultCount.moveToElement();
		int resultCount = Integer.parseInt(queryRunner.txtResultCount.getText());
		if (resultCount == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static String getUniqueTimeString(String dealerCode, String vin, String date, boolean dateInsideWarranty) {

		String timeQuery = "Select count(*) from pegadata.pc_nsa_stat_work_grp1 where dealercode = '%s' and vin = '%s' and repairorderopendate = '%s'";
		boolean isUnique = false;
		int resultCount = 0;
		String time = date;
		DatabaseQueryRunnerComponent queryRunner = new DatabaseQueryRunnerComponent();
		while (!isUnique) {
			time = Objects.isNull(time) || time.equals("") ? getTimeString(dateInsideWarranty)
					: getTimeString(time.replace("-", ""), dateInsideWarranty);

			// queryRunner.waitForComponentToLoad();
			queryRunner.txtboxQueryToRun
					.clearAndSendKeys(String.format(timeQuery, dealerCode, vin, time.replace("-", "")) + Keys.TAB);
			queryRunner.btnRun.scrollAndClick();
			WaitUtil.sleep(2000);
			queryRunner.txtResultCount.moveToElement();
			resultCount = Integer.parseInt(queryRunner.txtResultCount.getText());
			if (resultCount == 0) {
				isUnique = true;
			}

		}
		return time;
	}

	public static String getUniqueTimeString(String dealerCode, String vin, String date) {

		String timeQuery = "Select count(*) from pegadata.pc_nsa_stat_work_grp1 where dealercode = '%s' and vin = '%s' and repairorderopendate = '%s'";
		boolean isUnique = false;
		int resultCount = 0;
		String time = date;
		DatabaseQueryRunnerComponent queryRunner = new DatabaseQueryRunnerComponent();
		while (!isUnique) {
			time = Objects.isNull(time) || time.equals("") ? getTimeString(true)
					: getTimeString(time.replace("-", ""), false);

			// queryRunner.waitForComponentToLoad();
			queryRunner.txtboxQueryToRun
					.clearAndSendKeys(String.format(timeQuery, dealerCode, vin, time.replace("-", "")) + Keys.TAB);
			queryRunner.btnRun.scrollAndClick();
			WaitUtil.sleep(2000);
			queryRunner.txtResultCount.moveToElement();
			resultCount = Integer.parseInt(queryRunner.txtResultCount.getText());
			if (resultCount == 0) {
				isUnique = true;
			}

		}
		return time;
	}

	private static String getTimeString(String date, boolean dateInsideWarranty) {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);

		LocalDate localDate = LocalDate.parse(date, format);

		logger.info(localDate);

		return getTimeString(localDate, dateInsideWarranty);
	}

	private static String getTimeString(LocalDate date, boolean dateInsideWarranty) {
		String dateToReturn = "";
		if (dateInsideWarranty) {
			dateToReturn = date.minus(Period.ofDays(new Random().nextInt(299))).toString();
		} else {
			dateToReturn = date.plus(Period.ofDays(new Random().nextInt(299))).toString();
		}

		return dateToReturn;
	}

	private static String getTimeString(boolean dateInsideWarranty) {

		String dateToReturn = "";

		if (dateInsideWarranty) {
			dateToReturn = LocalDate.now().minus(Period.ofDays(new Random().nextInt(299))).toString();
		} else {
			dateToReturn = LocalDate.now().plus(Period.ofDays(new Random().nextInt(299))).toString();
		}

		return dateToReturn;

	}

	private static String getMileage(Map<String, String> vehicleRef, String pFP, String opCode,String roOpenDate) {

		String make = vehicleRef.get("Make");
		String modelYear = vehicleRef.get("ModelYearVersionNumber");
		String vIN = vehicleRef.get("VIN");
		Map<String, String> vehicleWarranty = new LinkedHashMap<>();
		vehicleWarranty.put("WARRANTY_FLAG", "");
		vehicleWarranty.put("MONTH_LMT_QT", "0");
		vehicleWarranty.put("MILE_LMT_QT", "0");
		String expCoverageType=getCoverageType(TestDataBeanManager.getTestData().getUserData().get(UserData.COVERAGETYPE));
		String mileage = getInitialMileage(expCoverageType);
		//ExtentLogger.info("RO Open date is "+roOpenDate);
		ExtentLogger.info("Fetching vehicle warranty for Make:"+make+",Model:"+ modelYear+",Mileage:"+ mileage+",Opcode:"+
				opCode+",pfp:"+ pFP+",vin:" +vIN+",Ro open date:"+ roOpenDate.replace("-", ""), true);
		vehicleWarranty = DVehicleWarrantyUtil.getVehicleWarrantyMap(make, modelYear, mileage, opCode, pFP, vIN,
				roOpenDate);
		String actualCoverageType=getCoverageType(vehicleWarranty.get("CVG_ITM_CD").trim());
		Validator.assertTrue(expCoverageType.equalsIgnoreCase(actualCoverageType), "Expected covereage type: "+expCoverageType+" Actual coverage type: "+actualCoverageType);
		try {
			while (!vehicleWarranty.get("WARRANTY_FLAG").equals("OUT OF COVERAGE")) {
				//roOpenDate = DateUtil.getDate(roOpenDate, "yyyyMMdd", "yyyyMMdd", months);
				//roOpenDate=DateUtil.getDate(dateDiff--, "yyyyMMdd");
				mileage = String.valueOf(Long.parseLong(vehicleWarranty.get("MILE_LMT_QT")) +10000);
				vehicleWarranty = DVehicleWarrantyUtil.getVehicleWarrantyMap(make, modelYear, mileage, opCode, pFP, vIN,roOpenDate);
			}
		}catch (Exception e) {
			throw new CustomRuntimeException(e.getLocalizedMessage());
		}
		return mileage;
	}
	
	private static String getMileage(Map<String, String> vehicleRef, String pFP, String opCode,String roOpenDate, boolean isWarranty) {

		String make = vehicleRef.get("Make");
		String modelYear = vehicleRef.get("ModelYearVersionNumber");
		String vIN = vehicleRef.get("VIN");
		Map<String, String> vehicleWarranty = new LinkedHashMap<>();
		vehicleWarranty.put("WARRANTY_FLAG", "");
		vehicleWarranty.put("MONTH_LMT_QT", "0");
		vehicleWarranty.put("MILE_LMT_QT", "0");
		String expCoverageType=getCoverageType(TestDataBeanManager.getTestData().getUserData().get(UserData.COVERAGETYPE));
		String mileage = isWarranty? "1000":getInitialMileage(expCoverageType);
		//ExtentLogger.info("RO Open date is "+roOpenDate);
		ExtentLogger.info("Fetching vehicle warranty for Make:"+make+",Model:"+ modelYear+",Mileage:"+ mileage+",Opcode:"+
				opCode+",pfp:"+ pFP+",vin:" +vIN+",Ro open date:"+ roOpenDate.replace("-", ""), true);
		vehicleWarranty = DVehicleWarrantyUtil.getVehicleWarrantyMap(make, modelYear, mileage, opCode, pFP, vIN,
				roOpenDate);
		String actualCoverageType=getCoverageType(vehicleWarranty.get("CVG_ITM_CD").trim());
		Validator.assertTrue(expCoverageType.equalsIgnoreCase(actualCoverageType), "Expected covereage type: "+expCoverageType+" Actual coverage type: "+actualCoverageType);
		String coverageFlag = isWarranty? "Coverage": "OUT OF COVERAGE";
		try {
			while (!vehicleWarranty.get("WARRANTY_FLAG").equalsIgnoreCase(coverageFlag)) {
				//roOpenDate = DateUtil.getDate(roOpenDate, "yyyyMMdd", "yyyyMMdd", months);
				//roOpenDate=DateUtil.getDate(dateDiff--, "yyyyMMdd");
				//mileage = String.valueOf(Long.parseLong(vehicleWarranty.get("MILE_LMT_QT")) +10000);
				mileage = String.valueOf(Long.parseLong(mileage) +10000);
				vehicleWarranty = DVehicleWarrantyUtil.getVehicleWarrantyMap(make, modelYear, mileage, opCode, pFP, vIN,roOpenDate);
			}
		}catch (Exception e) {
			throw new CustomRuntimeException(e.getLocalizedMessage());
		}
		return mileage;
	}
	
	private static String getInitialMileage(String coverageType) {

		switch (coverageType) {

		case "CVT":
			return "84000";

		case "Powertrain":
			return "60000";
		case "Basic":
			return "36000";
		case "OOW":
			return "120000";

		default:
			return "84000";

		}

	}
	
	private static String getCoverageType(String coverageType) {

		switch (coverageType) {

		case "PWRTRN":
			return "Powertrain";
		
		case "Powertrain":
			return "Powertrain";
		
		case "BASIC":
			return "Basic";
		
		case "Basic":
			return "Basic";
		
		case "CVT":
			return "CVT";
		
		case "CV3EXTN":
			return "CVT";
		
		case "CV2EXTN":
			return "CVT";
		
		case "CVTEXTN":
			return "CVT";

		default:
			return "CVT";
		}

	}
	
	@Test
	public void test() {
		
		int i=0;
		i--;
		System.out.println(i);
	}

	private static String getMileage(String mileage) {

		int intmileage = Integer.parseInt(mileage) + 1000;

		String returnMileage = String.valueOf(intmileage);

		//setMileage(returnMileage);

		return returnMileage;
	}

	/*public static String getMileage() {
		return mileage;
	}

	public static void setMileage(String mileage) {
		ROAPIUtil.mileage = mileage;
	}*/

	public static String createRO(TestDataBean testData) {

		String vin = testData.getUserData().get(UserData.VIN);
		Map<String, String> vehicleReference = testData.getVehicleRefDetails(vin);
		String dealerCode = testData.getUserData().get(UserData.DEALERCODE);
		String rOXmlPath = System.getProperty("user.dir") + ConfigurationManager.getBundle().getString("roxml.path");
		boolean isWarranty = Boolean.parseBoolean(testData.getUserData().get(UserData.WARRANTY));
		//
		//String manufactureDate = testData.getVehicleRefDetails().get("ManufacturedDate");
		String manufactureDate = testData.getVehicleRefDetails().get("InServiceDate");
		String date = isWarranty ? manufactureDate : "";
		String mileage = testData.getUserData().get(UserData.MILEAGE);
		String rONumber = getrONumber(dealerCode, vin, date,isWarranty);
		logger.info(rONumber);
		String timeString = getDateTimeString() + "T00:00:00.000Z";
		ExtentLogger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " + timeString);
		// logger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " +
		// timeString);
		String rOString = XMLUtil.getXMLString(rOXmlPath).replace("%DEALER_CODE%", dealerCode)
				.replace("%RONUMBER%", rONumber).replace("%TIMESTRING%", timeString).replace("%VIN%", vin)
				.replace("%mileage%", mileage);
		// String url =
		// "https://vcat.sit.nnanet.com/prweb/PRAuth/Automation?pyActivity=NSA-STAT-IntSvc.CreateRO_Automation&ROData=";
		String url = ConfigurationManager.getBundle().getString("rosubmission.url");
		getDriver().get(url + rOString);
		String status = ExtWebComponent.getExtWebElement("//label[text()='Status:']/parent::div").getText();
		if (!status.contains("good")) {
			throw new CustomRuntimeException("RO Creation Fails");
		}
		date = getDateTimeString().replace("-", "");
		hasROCreated(dealerCode, vin, date, rONumber);
		getDriver().manage().deleteAllCookies();

		return rONumber;

	}
	
	public enum CostGridShare{
		NISSAN("NissanPercentage"),DEALER("DealerPercentage"),CUSTOMER("CustomerPercentage");
		
		private String name;

		public String getCostGridShareName() {
			return name;
		}

		private CostGridShare(String shareName) {
			name = shareName;
		}
	}
//To-Do update below logic to remove
	public static Map<String, String> getNissanShare(TestDataBean testData) {
		boolean isWarranty = Boolean.parseBoolean(testData.getUserData().get(UserData.WARRANTY));
		Map<String, String> retunCostGrid = new HashMap<>();
		
		if(isWarranty) {
			retunCostGrid.put(CostGridShare.NISSAN.getCostGridShareName(),"100");
			retunCostGrid.put(CostGridShare.DEALER.getCostGridShareName(),"0");
			retunCostGrid.put(CostGridShare.CUSTOMER.getCostGridShareName(),"0");
			//return retunCostGrid;
		}else {
		String coverageType = testData.getUserData().get(UserData.COVERAGETYPE);
		String make = testData.getVehicleRefDetails().get("Make");
		String model = testData.getVehicleRefDetails().get("ModelLineCode");
		String roOpenDateString = testData.getrOOpenDate();
		String mileage = testData.getMileage();
		String modelYear = testData.getVehicleRefDetails().get("ModelYearVersionNumber");
		String vin = testData.getUserData().get(UserData.VIN);
		String pfp = testData.getUserData().get(UserData.PFP);
		String opCode = testData.getUserData().get(UserData.OPCODE);

		Map<String, String> vehicleWarranty = DVehicleWarrantyUtil.getVehicleWarrantyMap(make, modelYear, mileage,
				opCode, pfp, vin, roOpenDateString.replace("-", ""));
		ExtentLogger.info("Make:"+make+",Model:"+ modelYear+",Mileage:"+ mileage+",Opcode:"+
				opCode+",pfp:"+ pfp+",vin:" +vin+",Ro open date:"+ roOpenDateString.replace("-", ""), true);
		retunCostGrid =  GWCostGridUtil.getCostGrid_1(coverageType, make, model, roOpenDateString,vehicleWarranty); 
				
	/*	try{
			costGridMap = GWCostGridUtil.getCostGrid_1(coverageType, make, model, roOpenDateString,vehicleWarranty);
		}catch(CustomRuntimeException e) {
			System.out.println("exception in g");
		}*/
		
		int nissanShare = Objects.isNull(retunCostGrid.get("NissanPercentage")) ? 0
				: Integer.parseInt(retunCostGrid.get("NissanPercentage"));
		if (nissanShare == 0) {
			throw new CustomRuntimeException("Nissan share can't be 0");
		}
		}
		TestDataBeanManager.getTestData().setgWCostGrid(retunCostGrid);
		return retunCostGrid;
	}

	private static double getTotalAmount(int nissanShare, double nissanAmount) {

		return (nissanAmount * 100) / nissanShare;
	}

	private static double getPercenatgeShare(int percentage, double value) {

		return (value * percentage) / 100;
	}
	
	//create ro for hrk test cases
	public static void createROForHRK(String dealerCode, String roNumber, String vin) {
		String rOXmlPath = System.getProperty("user.dir") + ConfigurationManager.getBundle().getString("hrkroxml.path");
		String timeString = DateUtil.getDate(-1, "yyyy-MM-dd") + "T00:00:00.000Z";
		String rOString = XMLUtil.getXMLString(rOXmlPath).replace("%DEALER_CODE%", dealerCode)
				.replace("%RONUMBER%", roNumber).replace("%TIMESTRING%", timeString).replace("%VIN%", vin);
		String url = ConfigurationManager.getBundle().getString("rosubmission.url");
		getDriver().get(url + rOString);
		String status = ExtWebComponent.getExtWebElement("//label[text()='Status:']/parent::div").getText();
		if (!status.contains("good")) {
			throw new ROCreationException("RO Creation Failed");
		}
		String date = timeString.replace("-","").replace("T00:00:00.000Z", "");
		hasROCreated_HRK(dealerCode, vin, date, roNumber);
	}

	public static String createGWRO() {
		boolean isWarranty = Boolean.parseBoolean(TestDataBeanManager.getTestData().getUserData().get(UserData.WARRANTY));
		String coverageType = TestDataBeanManager.getTestData().getUserData().get(UserData.COVERAGETYPE);
		PFPOpCodeTestData pfpOpCode = new PFPOpCodeTestData(coverageType);
		String pFP = TestDataBeanManager.getTestData().getUserData().get(UserData.PFP);
		String componentCategory = TestDataBeanManager.getTestData().getUserData().get(UserData.VEHICLECOMPONENT);
		//createOrUpdateRepairOptions(pFP,componentCategory);
		String vin = TestDataBeanManager.getTestData().getUserData().get(UserData.VIN);
		String dealerCode = TestDataBeanManager.getTestData().getUserData().get(UserData.DEALERCODE);
		String rOXmlPath = System.getProperty("user.dir") + ConfigurationManager.getBundle().getString("gwroxml.path");
		String opCode = TestDataBeanManager.getTestData().getUserData().get(UserData.OPCODE);
		String inserviceDate = TestDataBeanManager.getTestData().getVehicleRefDetails(vin).get("InServiceDate");
		String initialROOpenDate =  isWarranty?inserviceDate:DateUtil.getDate(0, "yyyyMMdd");
		TestDataBeanManager.getTestData().setRoNumber(getrONumber(dealerCode, vin, initialROOpenDate,isWarranty));
		System.out.println("RO is "+TestDataBeanManager.getTestData().getRoNumber());
		String date = getDateTimeString();
		TestDataBeanManager.getTestData().setrOOpenDate(date);
		String mileage = getMileage(String.valueOf(getMileage(TestDataBeanManager.getTestData().getVehicleRefDetails(vin), pFP, opCode,date.replace("-", ""),isWarranty)));
		TestDataBeanManager.getTestData().setMileage(mileage);
		String coverageCode = TestDataBeanManager.getTestData().getUserData().get(UserData.COVERAGECODE);
		
		boolean nissanAmtGTDcal = Boolean.parseBoolean(TestDataBeanManager.getTestData().getUserData().get(UserData.NISSANAMOUNTGTDCAL));
		double dCal = DDcalUtil.getGWDcalAmount(dealerCode, date.replace("-",""));
		TestDataBeanManager.getTestData().setdCal(dCal);
		double nissanShareAmount = 0;
		if (nissanAmtGTDcal) {
			nissanShareAmount = dCal + getPercenatgeShare(15, dCal);
		} else {
			nissanShareAmount = ConfigurationManager.getBundle().getInt("nissan.min");
		}
		//BaseStep.testData.setNissanShare(nissanShareAmount);
		TestDataBeanManager.getTestData().setNissanShare(nissanShareAmount);
		int nissanPercent = Integer.parseInt(getNissanShare(TestDataBeanManager.getTestData()).get(CostGridShare.NISSAN.getCostGridShareName()));
		double totalAmount = getTotalAmount(nissanPercent, nissanShareAmount);
		String sharePerAmountType = new DecimalFormat("#.00").format(totalAmount / 3);
		ExtentLogger.info(String.format(
				"Amount Deatils before RO creation, NissanPercent : %d, NissanAmount : %f,SharePerAmountType : %s, Total Amount : %f",
				nissanPercent, nissanShareAmount, sharePerAmountType, totalAmount), false);
		String expenseAmount = String.valueOf(sharePerAmountType);
		String laborAmount = String.valueOf(sharePerAmountType);
		String partAmount = String.valueOf(sharePerAmountType);
		logger.info(TestDataBeanManager.getTestData().getRoNumber());
		String timeString = getDateTimeString() + "T00:00:00.000Z";
		// timeString = "2022-05-03T00:00:00.000Z";
		// setDateTimeString("2022-05-03");
		ExtentLogger.info("Submitting RO XML with RONumber: " + TestDataBeanManager.getTestData().getRoNumber() + ", time: " + timeString);
		// logger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " +
		// timeString);
		String rOString = XMLUtil.getXMLString(rOXmlPath).replace("%DEALER_CODE%", dealerCode)
				.replace("%RONUMBER%", TestDataBeanManager.getTestData().getRoNumber()).replace("%TIMESTRING%", timeString).replace("%VIN%", vin)
				.replace("%mileage%", mileage).replace("%coverage_code%", coverageCode).replace("%PFP%", pFP)
				.replace("%OPCODE%", opCode).replace("%OPCODE%", opCode).replace("%Expense_Amount%", expenseAmount)
				.replace("%Labor_Amount%", laborAmount).replace("%Part_Amount%", partAmount);
		String url = ConfigurationManager.getBundle().getString("rosubmission.url");
		getDriver().get(url + rOString);
		String status = ExtWebComponent.getExtWebElement("//label[text()='Status:']/parent::div").getText();
		if (!status.contains("good")) {
			throw new ROCreationException("RO Creation Failed");
		}
		date = getDateTimeString().replace("-", "");
		hasROCreated_1(dealerCode, vin, date, TestDataBeanManager.getTestData().getRoNumber());
		getDriver().manage().deleteAllCookies();
		return TestDataBeanManager.getTestData().getRoNumber();

	}

	private static void createOrUpdateRepairOptions(String pFP, String componentCategory) {
		
		List<pxResults> pfpStartsWithResults = DCategoryByPFPOPCODEUtil.getComponentCategory(pFP.substring(0,5));
		List<pxResults> fullPFPResults = DCategoryByPFPOPCODEUtil.getComponentCategory(pFP);
		
		if(pfpStartsWithResults.size()==1&&pfpStartsWithResults.equals(fullPFPResults) && pfpStartsWithResults.get(0).componentCategory.equalsIgnoreCase(componentCategory) && pfpStartsWithResults.get(0).requiresGoodwillReview && pfpStartsWithResults.get(0).repairPartAuthorization) {
			return;
		}
		/*if(fullPFPResults.size()==1 && pfpStartsWithResults.size()==1 && pfpStartsWithResults.get(0).componentCategory.equalsIgnoreCase(componentCategory) && pfpStartsWithResults.get(0).requiresGoodwillReview && pfpStartsWithResults.get(0).repairPartAuthorization) {
			return;
		}*/
		EngManagerPage engManagerPage=null;
		RepairOptionsComponent repairOptionsComponent=null;
		engManagerPage = new EngManagerPage(Roles.ENGINEERINGMANAGER);
		engManagerPage.waitForPageToLoad();
		engManagerPage.getLeftSideMenuElement("Manage System Rules").click();
		engManagerPage.getManageSystemRulesComponent().getMenuElement("Repair Options").click();
		repairOptionsComponent = new RepairOptionsComponent();
		
		String repairTitle = "Automation Test Repair "+(componentCategory.contains("CVT") || componentCategory.contains("Audio") || componentCategory.contains("Restricted Parts")? componentCategory:"USS");
		boolean isRepairWithPFPPresent = false;
		if (!pfpStartsWithResults.isEmpty()) {
			isRepairWithPFPPresent=deletePFPs(pfpStartsWithResults,pFP,componentCategory,repairTitle);
		}
		if(!isRepairWithPFPPresent) {
			addRecord(pFP,componentCategory,repairTitle);
		}
		repairOptionsComponent.btnSave.click();
		repairOptionsComponent.txtSaveSuccessful.waitForText("Repair Options saved successfully");
		repairOptionsComponent.txtSaveSuccessful.assertText("Repair Options saved successfully", "Save Successful");
		engManagerPage.logOut();

	}

	private static void deletePFP(List<pxResults> results, String pFP) {
		
		for (pxResults result : results) {
			
			String repairXpath=String.format("//input[contains(@name,'ClaimType') and @value='%s']//ancestor::tr//input[contains(@name,'RepairTitle') and @value='%s']/ancestor::tr[contains(@id,'$PpyDisplayHarness$pFieldReviewRepairs$') and contains(@oaargs,'RepairID')]", result.componentCategory,result.repairTitle);
			ExtWebElement repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
			repair.scrollIntoView();
			ExtWebElement expandButton = ExtWebComponent.getChildElement("Expand pane",repair, "//td[starts-with(@class,'expandPane')]/span");
			expandButton.click();
			expandButton.waitForAttribute("class", "collapseRowDetails");
			ExtWebElement repairDetails = ExtWebComponent.getExtWebElement("//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]");
			String deletePFPRowXpath=String.format("//input[contains(@name,'PFPList') and @type='text' and starts-with(@value,'%s')]/ancestor::tr[contains(@id,'PFPList')]//td[@headers='a2']//i",pFP);
			ExtWebElement deletePFPRow = ExtWebComponent.getChildElement("PFP delete button",repairDetails,deletePFPRowXpath);
			deletePFPRow.click();
			//WaitUtil.sleep(2000);
			List<ExtWebElement> pfpList = ExtWebComponent.getChildElements(repairDetails, "//tr[contains(@id,'PFPList')]");
			if (pfpList.isEmpty()) {
				ExtWebComponent.getChildElement("linkAddPFP",repairDetails,"//a[@title='Add item' and text()='Add New Part Number']").click();
				ExtWebElement txtBoxPFP = ExtWebComponent.getChildElement("txtBoxPFP",repairDetails, "//input[contains(@name,'PFPList') and @type='text' and @value='']");
				txtBoxPFP.sendKeys("1234567abc");
			}
			expandButton.click();
			expandButton.waitForAttribute("class", "expandRowDetails");
			
		}
		
	}
	
	
	/***
	 * This methods deletes input pfp from the repair options not matching Repair Component and Title
	 * @param results - list of repair option having matching pfp
	 * @param pFP - PFP
	 * @param component - Component of the expected repair
	 * @param repairTitle - Title of the expected repair
	 * @return - returns true if matching pfp is present and false if pfp is not present under matching Repair component and Title
	 */
	
private static boolean deletePFPs(List<pxResults> results, String pFP, String component, String repairTitle) {
		boolean isPFPPresent=false;
		for (pxResults result : results) {
			
			String repairXpath=String.format("//input[contains(@name,'ClaimType') and @value='%s']//ancestor::tr//input[contains(@name,'RepairTitle') and @value='%s']/ancestor::tr[contains(@id,'$PpyDisplayHarness$pFieldReviewRepairs$') and contains(@oaargs,'RepairID')]", result.componentCategory,result.repairTitle);
			ExtWebElement repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
			repair.scrollIntoView();
			ExtWebElement expandButton = repair.getChildElement("//td[starts-with(@class,'expandPane')]/span","Expand pane");
			expandButton.click();
			expandButton.waitForAttribute("class", "collapseRowDetails");
			ExtWebElement repairDetails = ExtWebComponent.getExtWebElement("//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]");
			String pFPRowsXpath=String.format("//input[contains(@name,'PFPList') and @type='text' and starts-with(@value,'%s')]/ancestor::tr[contains(@id,'PFPList')]",pFP.substring(0,5));
			List<ExtWebElement> pFPRows = repairDetails.getChildElements(pFPRowsXpath,"PFP Row");
			for(ExtWebElement pFPRow: pFPRows) {
				String partNumber = pFPRow.getChildElement("/td//input", "Part Number").getAttribute("value"); 
				if(partNumber.equalsIgnoreCase(pFP) && component.equals(result.componentCategory) && repairTitle.equals(result.repairTitle)){
					isPFPPresent=true;
					continue;
			}
				pFPRow.getChildElement("/td//i", "Delete PFP").click();
			}
			WaitUtil.sleep(2000);
			List<ExtWebElement> pfpList = repairDetails.getChildElements("//tr[contains(@id,'PFPList')]","PFP Rows");
			if(pfpList.isEmpty()) {
				repairDetails.getChildElement("//a[@title='Add item' and text()='Add New Part Number']","linkAddPFP").click();
				ExtWebElement txtBoxPFP = repairDetails.getChildElement("//input[contains(@name,'PFPList') and @type='text' and @value='']","txtBoxPFP");
				txtBoxPFP.sendKeys("1234567abc");
			}
			//expandButton = repair.getChildElement("//td[starts-with(@class,'expandPane')]/span","Expand pane");
			
			expandButton.click();
			expandButton.waitForAttribute("class", "expandRowDetails");
			
		}
		
		return isPFPPresent;
		
	}
	
/*	private static void deletePFP(RepairOptionsComponent repairOptionsComponent, List<pxResults> results, String pFP) {
		for (pxResults result : results) {
			
			
			
			repairOptionsComponent = new RepairOptionsComponent();
			List<ExtWebElement> listRepairs = repairOptionsComponent.listRepairs;
			
			
			
			for (ExtWebElement repair : listRepairs) {
			
				repair.scrollIntoView();
				String repairCategory = ExtWebComponent.getChildElement(repair, "//input[contains(@name,'ClaimType')]").getAttribute("value");
				ExtentLogger.info(repairCategory, false);
				String repairTitle = ExtWebComponent.getChildElement(repair, "//input[contains(@name,'RepairTitle')]")
						.getAttribute("value");
				
				ExtentLogger.info(repairTitle, false);
				if ( repairCategory.equalsIgnoreCase(result.componentCategory) && repairTitle.equals(result.repairTitle)) {
					ExtWebComponent.getChildElement("Expand pane",repair, "//td[starts-with(@class,'expandPane')]").click();
					ExtWebComponent.getChildElement("Expand pane",repair, "//td[starts-with(@class,'expandPane')]/span[@class='collapseRowDetails']").waitForVisible();	
					ExtWebElement repairDetails = ExtWebComponent.getExtWebElement(
							"//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]");
					String deletePFPRowXpath=String.format("//input[contains(@name,'PFPList') and @type='text' and @value='%s']/ancestor::tr[contains(@id,'PFPList')]//td[@headers='a2']//i",pFP);
					ExtWebElement deletePFPRow = ExtWebComponent.getChildElement("PFP delete button",repairDetails,deletePFPRowXpath);
					deletePFPRow.click();
					List<ExtWebElement> pfpList = ExtWebComponent.getChildElements(repairDetails,
							"//tr[contains(@id,'PFPList')]");
					
					for (ExtWebElement pfpEle : pfpList) {
						String pfpNum = ExtWebComponent
								.getChildElement(pfpEle, "//input[contains(@name,'PFPList') and @type='text']")
								.getAttribute("value");
						if (pfpNum.equalsIgnoreCase(pFP)) {
							ExtWebComponent.getChildElement("delete icon",pfpEle, "//td[@headers='a2']//i").click();
						}
					}
					WaitUtil.sleep(2000);
					List<ExtWebElement> pfpList = ExtWebComponent.getChildElements(repairDetails, "//tr[contains(@id,'PFPList')]");
					if (pfpList.isEmpty()) {
						ExtWebComponent.getChildElement(repairDetails,
								"//a[@title='Add item' and text()='Add New Part Number']").click();
						ExtWebElement newPFP = ExtWebComponent.getChildElement(repairDetails,
								"//tr[contains(@id,'PFPList')]");
						ExtWebComponent.getChildElement(newPFP, "//input[contains(@name,'PFPList') and @type='text']")
								.sendKeys("1234567abc");
						ExtWebElement txtBoxPFP = ExtWebComponent.getChildElement("txtBoxPFP",repairDetails, "//input[contains(@name,'PFPList') and @type='text' and @value='']");
						txtBoxPFP.sendKeys("1234567abc");
					}
					WaitUtil.sleep(2000);
					ExtWebComponent.getChildElement(repair, "//td[starts-with(@class,'expandPane')]").click();
					ExtWebComponent.getChildElement(repair, "//td[starts-with(@class,'expandPane')]/span[@class='expandRowDetails']").verifyVisible();
				}
			}
		}
	}
*/


	
	
	
	
	private static void addRecord(String pfp, String component) {

		
		String repairTitle = "Automation Test Repair " + component;
		//String repairXpath=String.format("//input[contains(@name,'ClaimType') and @value='%s']//ancestor::tr//input[contains(@name,'RepairTitle') and @value='%s']/ancestor::tr[contains(@id,'$PpyDisplayHarness$pFieldReviewRepairs$') and contains(@oaargs,'RepairID')]", component,repairTitle);
		String repairXpath=String.format("//tr//input[contains(@name,'RepairTitle') and @value='%s']/ancestor::tr[contains(@id,'$PpyDisplayHarness$pFieldReviewRepairs$') and contains(@oaargs,'RepairID')]", repairTitle);
		ExtWebElement repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
		ExtWebElement repairComponent = ExtWebComponent.getChildElement(repair, String.format("//input[contains(@name,'ClaimType') and @value='%s']", component));
		if(!repairComponent.isPresent()) {
			repairComponent=ExtWebComponent.getChildElement("repair component",repair, "//td[@data-attribute-name='Vehicle Component']//input");
			repairComponent.clearAndSendKeys(component);
		}
		if (repair.isPresent()) {
			repair.scrollIntoView();
			ExtWebElement requireGWReviewCheckBox = ExtWebComponent.getChildElement(repair,
					"//input[@type='checkbox' and contains(@name,'RequiresGoodwillReview')]");
			if (!requireGWReviewCheckBox.isSelected()) {
				requireGWReviewCheckBox.click();
				WaitUtil.sleep(2000);
			}
			repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
			ExtWebElement repairPartOpcodeRequireVCANCheckBox = ExtWebComponent.getChildElement(repair,
					"//input[@type='checkbox' and contains(@name,'RepairPartAuthorization')]");
			if (!repairPartOpcodeRequireVCANCheckBox.isSelected()) {
				repairPartOpcodeRequireVCANCheckBox.click();
				WaitUtil.sleep(2000);
			}

			repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
			repair.scrollIntoView();
			ExtWebElement expandButton = ExtWebComponent.getChildElement("Expand pane", repair,
					"//td[starts-with(@class,'expandPane')]/span");
			expandButton.click();
			WaitUtil.sleep(2000);
			expandButton.waitForAttribute("class", "collapseRowDetails");
			ExtWebElement repairDetails = ExtWebComponent.getExtWebElement(
					"//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]");
			ExtWebComponent.getChildElement("linkAddPFP", repairDetails,
					"//a[@title='Add item' and text()='Add New Part Number']").click();
			ExtWebElement txtBoxPFP = ExtWebComponent.getChildElement("txtBoxPFP", repairDetails,
					"//input[contains(@name,'PFPList') and @type='text' and @value='']");
			txtBoxPFP.sendKeys(pfp);
			return;

		}
		 
		RepairOptionsComponent repairOptionsComponent = new RepairOptionsComponent();
		WaitUtil.sleep(5000);
		
		repairOptionsComponent.linkAddRecord.scrollAndClick();
		WaitUtil.sleep(2000);
		List<ExtWebElement> listRepairs = repairOptionsComponent.listRepairs;
		ExtWebElement newRecord = listRepairs.get(listRepairs.size() - 1);
		ExtWebComponent.getChildElement(newRecord, "//td[starts-with(@class,'expandPane')]/span").click();
		ExtWebComponent.getChildElement(newRecord, "//input[contains(@name,'ClaimType')]").sendKeys(Keys.chord(component) + Keys.TAB);
		ExtWebComponent.getChildElement(newRecord, "//input[contains(@name,'RepairTitle')]").sendKeys("Automation Test Data " + component+ Keys.TAB);
		ExtWebComponent.getChildElement(newRecord, "//input[contains(@name,'RepairNote')]").sendKeys("Automation Test Data " + component+ Keys.TAB);
		ExtWebComponent.getChildElement(newRecord, "//input[@type='checkbox' and contains(@name,'RepairPartAuthorization')]").click();
		newRecord = listRepairs.get(listRepairs.size() - 1);
		ExtWebComponent.getChildElement(newRecord, "//input[@type='checkbox' and contains(@name,'RequiresGoodwillReview')]").click();
		
		WaitUtil.sleep(5000);
		ExtWebElement repairDetails = ExtWebComponent.getExtWebElement("//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]");
		ExtWebComponent.getChildElement(repairDetails, "//input[contains(@name,'PartsAmount')]").sendKeys("1.00");
		ExtWebComponent.getChildElement(repairDetails, "//input[contains(@name,'LaborAmount')]").sendKeys("1.00");
		ExtWebComponent.getChildElement(repairDetails, "//input[contains(@name,'ExpenseAmount')]").sendKeys("1.00");
		ExtWebComponent.getChildElement(repairDetails, "//a[@title='Add item' and text()='Add New Part Number']").click();
		ExtWebComponent.getChildElement(repairDetails, "//input[contains(@name,'PFPList') and @type='text']").sendKeys(pfp);

	}
	
private static void addRecord(String pfp, String component, String repairTitle) {

		String repairXpath=String.format("//tr//input[contains(@name,'RepairTitle') and @value='%s']/ancestor::tr[contains(@id,'$PpyDisplayHarness$pFieldReviewRepairs$') and contains(@oaargs,'RepairID')]", repairTitle);
		ExtWebElement repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
		ExtWebElement repairComponent = repair.getChildElement(String.format("//input[contains(@name,'ClaimType') and @value='%s']", component),"repairComponent");
		if(!repairComponent.isPresent()) {
			repairComponent=ExtWebComponent.getChildElement("repair component",repair, "//td[@data-attribute-name='Vehicle Component']//input");
			repairComponent.clearAndSendKeys(component);
		}
		if (repair.isPresent()) {
			repair.scrollIntoView();
			ExtWebElement requireGWReviewCheckBox = ExtWebComponent.getChildElement(repair,
					"//input[@type='checkbox' and contains(@name,'RequiresGoodwillReview')]");
			if (!requireGWReviewCheckBox.isSelected()) {
				requireGWReviewCheckBox.click();
				WaitUtil.sleep(2000);
			}
			repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
			ExtWebElement repairPartOpcodeRequireVCANCheckBox = ExtWebComponent.getChildElement(repair,
					"//input[@type='checkbox' and contains(@name,'RepairPartAuthorization')]");
			if (!repairPartOpcodeRequireVCANCheckBox.isSelected()) {
				repairPartOpcodeRequireVCANCheckBox.click();
				WaitUtil.sleep(2000);
			}

			repair = ExtWebComponent.getExtWebElement(repairXpath, "repair");
			//repair.scrollIntoView();
			ExtWebElement expandButton = repair.getChildElement("//td[starts-with(@class,'expandPane')]/span","Expand pane");
			expandButton.click();
			//WaitUtil.sleep(2000);
			expandButton.waitForAttribute("class", "collapseRowDetails");
			ExtWebElement repairDetails = ExtWebComponent.getExtWebElement("//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]");
			repairDetails.getChildElement("//a[@title='Add item' and text()='Add New Part Number']","linkAddPFP").click();
			ExtWebElement txtBoxPFP = repairDetails.getChildElement("//input[contains(@name,'PFPList') and @type='text' and @value='']","txtBoxPFP");
			txtBoxPFP.sendKeys(pfp);
			expandButton.click();
			expandButton.waitForAttribute("class", "expandRowDetails");
			return;

		}
		 
		RepairOptionsComponent repairOptionsComponent = new RepairOptionsComponent();
		WaitUtil.sleep(5000);
		
		repairOptionsComponent.linkAddRecord.scrollAndClick();
		WaitUtil.sleep(2000);
		List<ExtWebElement> listRepairs = repairOptionsComponent.listRepairs;
		ExtWebElement newRecord = listRepairs.get(listRepairs.size() - 1);
		newRecord.getChildElement("//td[starts-with(@class,'expandPane')]/span","Expand Pane button").click();
		newRecord.getChildElement("//input[contains(@name,'ClaimType')]","component").sendKeys(Keys.chord(component) + Keys.TAB);
		newRecord.getChildElement("//input[contains(@name,'RepairTitle')]","repair Title").sendKeys("Automation Test Data " + component+ Keys.TAB);
		newRecord.getChildElement("//input[contains(@name,'RepairNote')]","Repair Note").sendKeys("Automation Test Data " + component+ Keys.TAB);
		newRecord.getChildElement("//input[@type='checkbox' and contains(@name,'RepairPartAuthorization')]","RepairPartAuthorization").click();
		newRecord = listRepairs.get(listRepairs.size() - 1);
		newRecord.getChildElement("//input[@type='checkbox' and contains(@name,'RequiresGoodwillReview')]","RequiresGoodwillReview").click();
		
		WaitUtil.sleep(5000);
		ExtWebElement repairDetails = ExtWebComponent.getExtWebElement("//tr[@expanded='true' and not(@style='display: none;')]//div[contains(@full_base_ref,'pyDisplayHarness.FieldReviewRepairs')]");
		repairDetails.getChildElement("//input[contains(@name,'PartsAmount')]","Part Amount textbox").sendKeys("1.00");
		repairDetails.getChildElement("//input[contains(@name,'LaborAmount')]","Labour Amount textbox").sendKeys("1.00");
		repairDetails.getChildElement("//input[contains(@name,'ExpenseAmount')]","Expense Amount textbox").sendKeys("1.00");
		repairDetails.getChildElement("//a[@title='Add item' and text()='Add New Part Number']","linkAddPartNumber").click();
		repairDetails.getChildElement("//input[contains(@name,'PFPList') and @type='text']","txtBoxPFP").sendKeys(pfp);

	}

	public static String createRO(String dealerCode, String vin, String mileage, String inWarranty) {

		Map<String, String> vehicleReference = DVehicleReferenceDetailsUtil.getVehicleReferenceMap(vin);
		String rOXmlPath = System.getProperty("user.dir") + ConfigurationManager.getBundle().getString("roxml.path");
		boolean isWarranty = Boolean.parseBoolean(inWarranty);
		String manufactureDate = vehicleReference.get("InServiceDate");
		String date = isWarranty ? manufactureDate : "";
		String rONumber = getrONumber(dealerCode, vin, date);
		// logger.info(rONumber);
		String timeString = getDateTimeString() + "T00:00:00.000Z";
		// ExtentLogger.info("Submitting RO XML with RONumber: " + rONumber + ", time: "
		// + timeString);
		// logger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " +
		// timeString);
		String rOString = XMLUtil.getXMLString(rOXmlPath).replace("%DEALER_CODE%", dealerCode)
				.replace("%RONUMBER%", rONumber).replace("%TIMESTRING%", timeString).replace("%VIN%", vin)
				.replace("%mileage%", mileage);
		// String url =
		// "https://vcat.sit.nnanet.com/prweb/PRAuth/Automation?pyActivity=NSA-STAT-IntSvc.CreateRO_Automation&ROData=";
		String url = ConfigurationManager.getBundle().getString("rosubmission.url");
		getDriver().get(url + rOString);
		String status = ExtWebComponent.getExtWebElement("//label[text()='Status:']/parent::div").getText();
		if (!status.contains("good")) {
			throw new CustomRuntimeException("RO Creation Fails");
		}
		date = getDateTimeString().replace("-", "");
		hasROCreated(dealerCode, vin, date, rONumber);
		getDriver().manage().deleteAllCookies();
		// logger.info(rONumber);
		return rONumber;

	}

	private static boolean hasROCreated(String dealerCode, String vin, String date, String rONumber) {
		int time = 0;
		int result = 0;
		int endtime = ConfigurationManager.getBundle().getInt("timout.ro");
		while (time <= endtime) {
			time += 5;
			JsonPath jp = getROCount(dealerCode, vin, date, rONumber);
			if (jp.getInt("pxResultCount") > 0) {

				ExtentLogger.pass(rONumber + " created with RO Opendate: " + getDateTimeString());
				return true;
			}
			WaitUtil.sleep(3000);
			ExtentLogger.info("Waiting for RO to create", false);
		}
		throw new CustomRuntimeException("RO creation took longer than expected");

	}
	
	private static boolean hasROCreated_HRK(String dealerCode, String vin, String date, String rONumber) {
		long timeOut = ConfigurationManager.getBundle().getInt("timout.ro");
		long startTime = System.currentTimeMillis();
		long endtime=System.currentTimeMillis();;
		
		while ((endtime-startTime)<timeOut) {
			
			String responseString = getROResponseString(dealerCode, vin, date, rONumber);
			RODetails roDetails = new Gson().fromJson(responseString, RODetails.class);

			if (!roDetails.listROs.isEmpty()) {

				for (PXResults result : roDetails.listROs) {

					if (result.roNumber.equals(rONumber)) {
						ExtentLogger.pass(rONumber + " created with RO Opendate: " + date+" and RO Case Id: "+result.caseId);
						
						return true;
					}
				}
			}
			endtime = 	System.currentTimeMillis();
			ExtentLogger.info("Waiting for RO to create", false);
		}
		throw new ROCreationException("RO creation took longer than expected");

	}

	private static boolean hasROCreated_1(String dealerCode, String vin, String date, String rONumber) {
		long timeOut = ConfigurationManager.getBundle().getInt("timout.ro");
		long startTime = System.currentTimeMillis();
		long endtime=System.currentTimeMillis();;
		
		while ((endtime-startTime)<timeOut) {
			
			String responseString = getROResponseString(dealerCode, vin, date, rONumber);
			RODetails roDetails = new Gson().fromJson(responseString, RODetails.class);

			if (!roDetails.listROs.isEmpty()) {

				for (PXResults result : roDetails.listROs) {

					if (result.roNumber.equals(rONumber)) {
						TestDataBeanManager.getTestData().setDCaseId(result.caseId);
						ExtentLogger.pass(rONumber + " created with RO Opendate: " + getDateTimeString()+" and RO Case Id: "+result.caseId);
						
						return true;
					}
				}
			}
			endtime = 	System.currentTimeMillis();
			ExtentLogger.info("Waiting for RO to create", false);
		}
		throw new ROCreationException("RO creation took longer than expected");

	}

	public class RODetails {

		@SerializedName("pxResults")
		public List<PXResults> listROs;

		public class PXResults {

			@SerializedName("pyID")
			public String caseId;

			@SerializedName("RepairOrderNumber")
			public String roNumber;

		}

	}

	@Test
	public void datetime() {

		/*
		 * Map<String,String> vehicleWarrantys =
		 * DVehicleReferenceDetailsUtil.getVehicleReferenceMap("5N1AR2MM6EC698652");
		 * 
		 * 
		 * logger.info(getROOpenDate(vehicleWarrantys,"3121428X7C","JX56AA"));
		 */
		String rOXMLPath = System.getProperty("user.dir") + ConfigurationManager.getBundle().getString("roxml.path");
		String rOString = XMLUtil.getXMLString(rOXMLPath);
		logger.info(rOString);
	}

}
