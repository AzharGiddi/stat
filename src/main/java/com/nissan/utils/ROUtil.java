package com.nissan.utils;

import static com.nissan.driver.DriverManager.getDriver;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.nissan.automation.core.utils.DateUtil;
import com.nissan.automation.core.utils.PoiExcelUtil;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.automation.core.utils.XMLUtil;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.databeans.TestDataBean;
import com.nissan.enums.UserData;
import com.nissan.enums.Roles;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.pages.AdminHomePage;
import com.nissan.pages.CaseListReportPage;
import com.nissan.pages.ServiceSimulationResultPage;
import com.nissan.pages.SimulateMQServiceExecutionPage;
import com.nissan.pages.components.CaseListReportComponent;
import com.nissan.pages.components.DatabaseQueryRunnerComponent;
import com.nissan.pages.components.ServiceMQComponent;
import com.nissan.pages.components.StatServicePackageComponent;
import com.nissan.reports.ExtentLogger;

public final class ROUtil {

	private ROUtil() {

	}

	private static final Log logger = LogFactoryImpl.getLog(ROUtil.class);

	public static void createRO(String rOXmlPath, String ruleSetVersion, AdminHomePage adminHomePage) {

		String rONumber = getrONumber();
		logger.info(rONumber);
		String parentWindow = getDriver().getWindowHandle();
		adminHomePage.iconRecords.click();
		adminHomePage.expandDropDown("Integration-Services");
		adminHomePage.linkServiceMQ_SideMenu.click();

		ServiceMQComponent serviceMQComponent = new ServiceMQComponent();
		serviceMQComponent.waitForComponentToLoad();
		serviceMQComponent.openRuleSet(ruleSetVersion);
		WaitUtil.sleep(3000);
		adminHomePage.closeTab("Service MQ");

		StatServicePackageComponent statServicePackageComponent = new StatServicePackageComponent();
		statServicePackageComponent.waitForComponentToLoad();
		statServicePackageComponent.buttonActions.click();
		statServicePackageComponent.linkRun.click();
		adminHomePage.closeTab("STATService");

		SimulateMQServiceExecutionPage simulateMQServiceExecutionPage = new SimulateMQServiceExecutionPage();
		simulateMQServiceExecutionPage.getWindow(parentWindow);
		simulateMQServiceExecutionPage.waitForPageToLoad();
		String timeString = getDateTimeString() + "T00:00:00.000Z";
		ExtentLogger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " + timeString);
		logger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " + timeString);
		String rOString = XMLUtil.getXMLString(rOXmlPath).replace("%RONUMBER%", rONumber).replace("%TIMESTRING%",
				timeString);
		simulateMQServiceExecutionPage.textBoxAllChars.clearAndSendKeys(rOString);
		simulateMQServiceExecutionPage.buttonExecute.click();
		getDriver().close();

		ServiceSimulationResultPage serviceSimulationResultPage = new ServiceSimulationResultPage();
		serviceSimulationResultPage.getWindow(parentWindow);
		serviceSimulationResultPage.waitForPageToLoad();
		serviceSimulationResultPage.txtOverallResultSuccess.assertText("Success", "Overall Status");
		getDriver().close();

		getDriver().switchTo().window(parentWindow);
		adminHomePage.textboxSearch.clearAndSendKeys("CaseListReport" + Keys.ENTER);
		adminHomePage.linkCaseListReport.click();
		// adminHomePage.closeTab("Service MQ");

		CaseListReportComponent caseListReportComponent = new CaseListReportComponent();
		caseListReportComponent.waitForComponentToLoad();
		caseListReportComponent.buttonActions.click();
		caseListReportComponent.linkRun.click();
		adminHomePage.closeTab("CaseListReport");

		CaseListReportPage caseListReportPage = new CaseListReportPage();
		caseListReportPage.getWindow(parentWindow);
		caseListReportPage.waitForPageToLoad();
		caseListReportPage.filter(rONumber);
		getDriver().close();
		getDriver().switchTo().window(parentWindow);
		// adminHomePage.logOut();

	}

	public static void createRO(String rOXmlPath, String ruleSetVersion, AdminHomePage adminHomePage, String date) {

		String rONumber = getrONumber(date);
		logger.info(rONumber);
		String parentWindow = getDriver().getWindowHandle();
		// logger.info("parent window is " + parentWindow);
		// Thread.sleep(3000);
		adminHomePage.iconRecords.click();
		// WaitUtil.sleep(3000);
		adminHomePage.expandDropDown("Integration-Services");
		// WaitUtil.sleep(2000);
		adminHomePage.linkServiceMQ_SideMenu.click();

		ServiceMQComponent serviceMQComponent = new ServiceMQComponent();
		serviceMQComponent.waitForComponentToLoad();
		serviceMQComponent.openRuleSet(ruleSetVersion);
		WaitUtil.sleep(3000);
		adminHomePage.closeTab("Service MQ");

		StatServicePackageComponent statServicePackageComponent = new StatServicePackageComponent();
		statServicePackageComponent.waitForComponentToLoad();
		statServicePackageComponent.buttonActions.click();
		statServicePackageComponent.linkRun.click();
		adminHomePage.closeTab("STATService");

		SimulateMQServiceExecutionPage simulateMQServiceExecutionPage = new SimulateMQServiceExecutionPage();
		simulateMQServiceExecutionPage.getWindow(parentWindow);
		simulateMQServiceExecutionPage.waitForPageToLoad();
		String timeString = getDateTimeString() + "T00:00:00.000Z";
		ExtentLogger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " + timeString);
		logger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " + timeString);
		String rOString = XMLUtil.getXMLString(rOXmlPath).replace("%RONUMBER%", rONumber).replace("%TIMESTRING%",
				timeString);
		simulateMQServiceExecutionPage.textBoxAllChars.clearAndSendKeys(rOString);
		simulateMQServiceExecutionPage.buttonExecute.click();
		getDriver().close();

		ServiceSimulationResultPage serviceSimulationResultPage = new ServiceSimulationResultPage();
		serviceSimulationResultPage.getWindow(parentWindow);
		serviceSimulationResultPage.waitForPageToLoad();
		serviceSimulationResultPage.txtOverallResultSuccess.assertText("Success", "Overall Status");
		getDriver().close();

		getDriver().switchTo().window(parentWindow);
		adminHomePage.textboxSearch.clearAndSendKeys("CaseListReport" + Keys.ENTER);
		adminHomePage.linkCaseListReport.click();
		// adminHomePage.closeTab("Service MQ");

		CaseListReportComponent caseListReportComponent = new CaseListReportComponent();
		caseListReportComponent.waitForComponentToLoad();
		caseListReportComponent.buttonActions.click();
		caseListReportComponent.linkRun.click();
		adminHomePage.closeTab("CaseListReport");

		CaseListReportPage caseListReportPage = new CaseListReportPage();
		caseListReportPage.getWindow(parentWindow);
		caseListReportPage.waitForPageToLoad();
		caseListReportPage.filter(rONumber);
		getDriver().close();
		getDriver().switchTo().window(parentWindow);
		// adminHomePage.logOut();

	}

	private static String rONumber;
	private static Random rand;
	private static String dateTimeString;
	private static String mileage;

	public static String getMileage() {
		return mileage;
	}

	public static void setMileage(String mileage) {
		ROUtil.mileage = mileage;
	}

	public static String getDateTimeString() {
		if (Objects.isNull(dateTimeString)) {
			setDateTimeString(getTimeString());
		}

		return dateTimeString;
	}

	public static void setDateTimeString(String dateTimeString) {
		ROUtil.dateTimeString = dateTimeString;
	}

	public static void setrONumber(String rONumber) {
		ROUtil.rONumber = rONumber;
	}

	public static String getrONumber() {
		if (Objects.isNull(rONumber)) {
			setrONumber(generateRONumber());
		}
		return rONumber.trim();
	}

	public static String getrONumber(String dealerCode, String vin, String date) {
		if (Objects.isNull(rONumber)) {
			setrONumber(generateUniqueRONumber(dealerCode, vin, date));
		}
		return rONumber.trim();
	}

	public static String getrONumber(String dealerCode, String vin, String date, boolean dateInsideWarranty) {

		if (Objects.isNull(rONumber)) {
			setrONumber(generateUniqueRONumber(dealerCode, vin, date, dateInsideWarranty));
		}
		return rONumber.trim();
	}

	public static String getrONumber(String dateString) {

		if (Objects.isNull(rONumber)) {
			setrONumber(generateRONumber(dateString));
		}
		return rONumber.trim();
	}

	private static String generateRONumber() {
		String randomNumber = "";
		boolean unique = false;
		try {
			rand = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		int randInt;
		String time = "";
		while (!unique) {
			randInt = rand.nextInt(999999);
			time = getTimeString();

			randomNumber = "AT-" + String.valueOf(randInt);
			unique = isUnique(randomNumber, "3900", "1N4BL2AP6AN487528", time.replace("-", ""));
		}
		setDateTimeString(time);
		return randomNumber;

	}

	private static String generateRONumber(String date) {
		String randomNumber = "";
		boolean unique = false;
		try {
			rand = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		int randInt;
		String time = "";
		while (!unique) {
			randInt = rand.nextInt(999999);
			time = date.equals("") || Objects.isNull(date) ? getTimeString() : getTimeString(date);

			randomNumber = "AT-" + String.valueOf(randInt);
			unique = isUnique(randomNumber, "3900", "1N4BL2AP6AN487528", time.replace("-", ""));
		}
		setDateTimeString(time);
		return randomNumber;

	}

	// TODO Need to refactor below method: need to optimize steps.
	private static boolean isUnique(String rONumber, String dealerCode, String vin, String date) {

		AdminHomePage adminHomePage = new AdminHomePage();
		adminHomePage.waitForPageToLoad();
		adminHomePage.linkConfigureDevStudio.click();
		WaitUtil.sleep(1000);
		adminHomePage.hoverOnDDElement("System");
		adminHomePage.hoverOnDDElement("Database");
		adminHomePage.clickOnDDElement("Query runner");

		DatabaseQueryRunnerComponent queryRunner = new DatabaseQueryRunnerComponent();
		queryRunner.waitForComponentToLoad();

		String queries[] = new String[2];

		queries[0] = String.format(
				"Select count(*) from pegadata.pc_nsa_stat_work_grp1 where dealercode = '%s' and vin = '%s' and (repairordernumber = '%s' or repairorderopendate = '%s')",
				dealerCode, vin, rONumber, date);
		queries[1] = String.format(
				"Select count(*) from pegadata.pr_NSA_FW_STATFW_Data_ROToDiag where dealercode = '%s' and vin = '%s' and (repairordernumber = '%s' or repairorderopendate = '%s')",
				dealerCode, vin, rONumber, date);
		int resultCount = 0;
		boolean result = true;
		for (String query : queries) {
			queryRunner.txtboxQueryToRun.clearAndSendKeys(query);
			queryRunner.btnRun.click();
			queryRunner.txtResultCount.moveToElement();
			resultCount = Integer.parseInt(queryRunner.txtResultCount.getText());
			if (resultCount != 0) {
				result = false;
				ExtentLogger.info(String.format(
						"combination of dealer code = %s, vin=%s,rONumber=%s and RO open date=%s is not unique",
						dealerCode, vin, rONumber, date));
				logger.info(String.format(
						"combination of dealer code = %s, vin=%s,rONumber=%s and RO open date=%s is not unique",
						dealerCode, vin, rONumber, date));
				break;
			}
		}
		queryRunner.txtboxQueryToRun.clear();
		getDriver().switchTo().defaultContent();
		adminHomePage.closeTab("System: Database");
		// adminHomePage.btnOk.click();
		return result;
	}

	private static String generateUniqueRONumber(String dealerCode, String vin, String date) {
		String rONumber = "";
		boolean unique = false;
		try {
			rand = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		int randInt;
		String time = date;
		String resetDate = date;
		AdminHomePage adminHomePage = new AdminHomePage();
		adminHomePage.waitForPageToLoad();
		adminHomePage.linkConfigureDevStudio.click();
		adminHomePage.hoverOnDDElement("System");
		adminHomePage.hoverOnDDElement("Database");
		adminHomePage.clickOnDDElement("Query runner");

		DatabaseQueryRunnerComponent queryRunner = new DatabaseQueryRunnerComponent();
		queryRunner.waitForComponentToLoad();

		time = getUniqueTimeString(dealerCode, vin, date);
		rONumber = getUniqueNumber(dealerCode, vin, time);
		boolean isDcase = hasDcase(dealerCode, vin, rONumber, time);
		if (isDcase) {
			return generateUniqueRONumber(dealerCode, vin, date);
		}
		setDateTimeString(time);
		queryRunner.txtboxQueryToRun.clear();
		getDriver().switchTo().defaultContent();
		adminHomePage.closeTab("System: Database");
		return rONumber;

	}

	private static String generateUniqueRONumber(String dealerCode, String vin, String date,
			boolean dateInsideWarranty) {
		String rONumber = "";
		boolean unique = false;
		try {
			rand = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		int randInt;
		String time = date;
		String resetDate = date;
		AdminHomePage adminHomePage = new AdminHomePage();
		adminHomePage.waitForPageToLoad();
		adminHomePage.linkConfigureDevStudio.click();
		adminHomePage.hoverOnDDElement("System");
		adminHomePage.hoverOnDDElement("Database");
		adminHomePage.clickOnDDElement("Query runner");

		DatabaseQueryRunnerComponent queryRunner = new DatabaseQueryRunnerComponent();
		queryRunner.waitForComponentToLoad();

		time = getUniqueTimeString(dealerCode, vin, date, dateInsideWarranty);
		rONumber = getUniqueNumber(dealerCode, vin, time);
		boolean isDcase = hasDcase(dealerCode, vin, rONumber, time);
		if (isDcase) {
			return generateUniqueRONumber(dealerCode, vin, date);
		}
		setDateTimeString(time);
		// queryRunner.txtboxQueryToRun.clear();
		queryRunner.btnRefresh.scrollAndClick();
		getDriver().switchTo().defaultContent();
		adminHomePage.closeTab("System: Database");
		return rONumber;

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

	public static String getUniqueTimeString(String dealerCode, String vin, String date) {

		String timeQuery = "Select count(*) from pegadata.pc_nsa_stat_work_grp1 where dealercode = '%s' and vin = '%s' and repairorderopendate = '%s'";
		boolean isUnique = false;
		int resultCount = 0;
		String time = date;
		DatabaseQueryRunnerComponent queryRunner = new DatabaseQueryRunnerComponent();
		while (!isUnique) {
			time = Objects.isNull(time) || time.equals("") ? getTimeString() : getTimeString(time.replace("-", ""));

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

	public static String getUniqueNumber(String dealerCode, String vin, String date) {
		String roNumber = "";

		String rOQuery = "Select count(*) from pegadata.pc_nsa_stat_work_grp1 where dealercode = '%s' and vin = '%s' and repairordernumber = '%s' and repairorderopendate = '%s'";
		boolean isUnique = false;
		int resultCount = 0;
		int randInt = 0;
		while (!isUnique) {
			randInt = rand.nextInt(999999);
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

	private static String getTimeString() {

		return LocalDate.now().minus(Period.ofDays(new Random().nextInt(299))).toString();

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

	private static String getTimeString(String date) {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);

		LocalDate localDate = LocalDate.parse(date, format);

		logger.info(localDate);

		return localDate.minus(Period.ofDays(new Random().nextInt(299))).toString();

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

	public static String createRO(AdminHomePage adminHomePage, Map<String, String> data) {

		String vin = data.get("VIN");
		String dealerCode = data.get("Dealer Code");
		String rOXmlPath = System.getProperty("user.dir") + data.get("ROXml Path");
		String ruleSetVersion = data.get("RuleSet Version");
		String isWarranty = data.get("Warranty");
		String manufactureDate = data.get("Manufacture Date");
		String date = isWarranty.equalsIgnoreCase("yes") ? manufactureDate : "";
		String mileage = data.get("Mileage");
		String rONumber = getrONumber(dealerCode, vin, date);
		logger.info(rONumber);

		String parentWindow = getDriver().getWindowHandle();
		// AdminHomePage adminHomePage = new AdminHomePage("vardhanib", "rules");
		adminHomePage.iconRecords.click();
		adminHomePage.expandDropDown("Integration-Services");
		adminHomePage.linkServiceMQ_SideMenu.click();

		ServiceMQComponent serviceMQComponent = new ServiceMQComponent();
		serviceMQComponent.waitForComponentToLoad();
		serviceMQComponent.openRuleSet(ruleSetVersion);
		WaitUtil.sleep(3000);
		adminHomePage.closeTab("Service MQ");

		StatServicePackageComponent statServicePackageComponent = new StatServicePackageComponent();
		statServicePackageComponent.waitForComponentToLoad();
		statServicePackageComponent.buttonActions.click();
		statServicePackageComponent.linkRun.click();
		adminHomePage.closeTab("STATService");

		SimulateMQServiceExecutionPage simulateMQServiceExecutionPage = new SimulateMQServiceExecutionPage();
		simulateMQServiceExecutionPage.getWindow(parentWindow);
		simulateMQServiceExecutionPage.waitForPageToLoad();
		String timeString = getDateTimeString() + "T00:00:00.000Z";
		ExtentLogger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " + timeString);
		logger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " + timeString);
		String rOString = XMLUtil.getXMLString(rOXmlPath).replace("%RONUMBER%", rONumber)
				.replace("%TIMESTRING%", timeString).replace("%VIN%", vin).replace("%mileage%", mileage);
		simulateMQServiceExecutionPage.textBoxAllChars.clearAndSendKeys(rOString);
		simulateMQServiceExecutionPage.buttonExecute.click();
		getDriver().close();

		getDriver().switchTo().window(parentWindow);
		adminHomePage.textboxSearch.clearAndSendKeys("CaseListReport" + Keys.ENTER);
		adminHomePage.linkCaseListReport.click();
		// adminHomePage.closeTab("Service MQ");
		ServiceSimulationResultPage serviceSimulationResultPage = new ServiceSimulationResultPage();
		serviceSimulationResultPage.getWindow(parentWindow);
		serviceSimulationResultPage.waitForPageToLoad();
		serviceSimulationResultPage.txtOverallResultSuccess.assertText("Success", "Overall Status");
		getDriver().close();
		getDriver().switchTo().window(parentWindow);
		CaseListReportComponent caseListReportComponent = new CaseListReportComponent();
		caseListReportComponent.waitForComponentToLoad();
		caseListReportComponent.buttonActions.click();
		caseListReportComponent.linkRun.click();
		adminHomePage.closeTab("CaseListReport");

		CaseListReportPage caseListReportPage = new CaseListReportPage();
		caseListReportPage.getWindow(parentWindow);
		caseListReportPage.waitForPageToLoad();
		caseListReportPage.filter(rONumber);
		getDriver().close();
		getDriver().switchTo().window(parentWindow);
		// adminHomePage.logOut();
		return rONumber;

	}

	public static String createRO(Map<UserData, String> data, boolean adminLoginRequired,
			boolean logOutRequired) {

		AdminHomePage adminHomePage;
		adminHomePage = adminLoginRequired ? new AdminHomePage(Roles.ADMIN) : new AdminHomePage();
		adminHomePage.waitForPageToLoad();

		String vin = data.get(UserData.VIN);
		String dealerCode = data.get(UserData.DEALERCODE);
		String rOXmlPath = System.getProperty("user.dir") + ConfigurationManager.getBundle().getString("roxml.path");
		String ruleSetVersion = ConfigurationManager.getBundle().getString("ruleset.version");
		String isWarranty = data.get(UserData.WARRANTY);
		String manufactureDate = data.get(UserData.MANUFACTUREDATE);
		String date = isWarranty.equalsIgnoreCase("yes") ? manufactureDate : "";
		String mileage = data.get(UserData.MILEAGE);
		String rONumber = getrONumber(dealerCode, vin, date);
		logger.info(rONumber);

		String parentWindow = getDriver().getWindowHandle();

		adminHomePage.iconRecords.click();
		adminHomePage.expandDropDown("Integration-Services");
		adminHomePage.linkServiceMQ_SideMenu.click();

		ServiceMQComponent serviceMQComponent = new ServiceMQComponent();
		serviceMQComponent.waitForComponentToLoad();
		serviceMQComponent.openRuleSet(ruleSetVersion);
		WaitUtil.sleep(3000);
		adminHomePage.closeTab("Service MQ");

		StatServicePackageComponent statServicePackageComponent = new StatServicePackageComponent();
		statServicePackageComponent.waitForComponentToLoad();
		statServicePackageComponent.buttonActions.click();
		statServicePackageComponent.linkRun.click();
		adminHomePage.closeTab("STATService");

		SimulateMQServiceExecutionPage simulateMQServiceExecutionPage = new SimulateMQServiceExecutionPage();
		simulateMQServiceExecutionPage.getWindow(parentWindow);
		simulateMQServiceExecutionPage.waitForPageToLoad();
		String timeString = getDateTimeString() + "T00:00:00.000Z";
		ExtentLogger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " + timeString);
		logger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " + timeString);
		String rOString = XMLUtil.getXMLString(rOXmlPath).replace("%DEALER_CODE%", dealerCode).replace("%RONUMBER%", rONumber)
				.replace("%TIMESTRING%", timeString).replace("%VIN%", vin).replace("%mileage%", mileage);
		simulateMQServiceExecutionPage.textBoxAllChars.clearAndSendKeys(rOString);
		simulateMQServiceExecutionPage.buttonExecute.click();
		getDriver().close();

		getDriver().switchTo().window(parentWindow);
		adminHomePage.textboxSearch.clearAndSendKeys("CaseListReport" + Keys.ENTER);
		adminHomePage.linkCaseListReport.click();
		// adminHomePage.closeTab("Service MQ");
		ServiceSimulationResultPage serviceSimulationResultPage = new ServiceSimulationResultPage();
		serviceSimulationResultPage.getWindow(parentWindow);
		serviceSimulationResultPage.waitForPageToLoad();
		serviceSimulationResultPage.txtOverallResultSuccess.assertText("Success", "Overall Status");
		getDriver().close();
		getDriver().switchTo().window(parentWindow);
		CaseListReportComponent caseListReportComponent = new CaseListReportComponent();
		caseListReportComponent.waitForComponentToLoad();
		caseListReportComponent.buttonActions.click();
		caseListReportComponent.linkRun.click();
		adminHomePage.closeTab("CaseListReport");

		CaseListReportPage caseListReportPage = new CaseListReportPage();
		caseListReportPage.getWindow(parentWindow);
		caseListReportPage.waitForPageToLoad();
		caseListReportPage.filter(rONumber);
		getDriver().close();
		getDriver().switchTo().window(parentWindow);
		if (logOutRequired) {
			adminHomePage.logOut();
		}
		return rONumber;

	}

	public static String createROForGoodWill(TestDataBean testData, boolean dateInsideWarranty,
			boolean adminLoginRequired, boolean logOutRequired) {

		AdminHomePage adminHomePage=getAdminObject(adminLoginRequired);
		Map<UserData, String> data = testData.getUserData();
		String vin = data.get(UserData.VIN);
		String dealerCode = data.get(UserData.DEALERCODE);
		String rOXmlPath = System.getProperty("user.dir") + ConfigurationManager.getBundle().getString("gwroxml.path");
		String ruleSetVersion = ConfigurationManager.getBundle().getString("ruleset.version");
		String pFP = data.get(UserData.PFP);
		String opCode = data.get(UserData.OPCODE);
		Map<String,Object> getDateMileageMap = getROOpenDate(testData.getVehicleRefDetails(vin),pFP,opCode);
		String date = String.valueOf(getDateMileageMap.get("roOpenDate"));
		String mileage = getMileage(String.valueOf(getDateMileageMap.get("mileage")));
		String coverageCode = data.get(UserData.COVERAGECODE);
		String rONumber = getrONumber(dealerCode, vin, date, dateInsideWarranty);
		logger.info(rONumber);

		String parentWindow = getDriver().getWindowHandle();

		adminHomePage.iconRecords.click();
		adminHomePage.expandDropDown("Integration-Services");
		adminHomePage.linkServiceMQ_SideMenu.click();

		ServiceMQComponent serviceMQComponent = new ServiceMQComponent();
		serviceMQComponent.waitForComponentToLoad();
		serviceMQComponent.openRuleSet(ruleSetVersion);
		WaitUtil.sleep(3000);
		adminHomePage.closeTab("Service MQ");

		StatServicePackageComponent statServicePackageComponent = new StatServicePackageComponent();
		statServicePackageComponent.waitForComponentToLoad();
		statServicePackageComponent.buttonActions.click();
		statServicePackageComponent.linkRun.click();
		adminHomePage.closeTab("STATService");

		SimulateMQServiceExecutionPage simulateMQServiceExecutionPage = new SimulateMQServiceExecutionPage();
		simulateMQServiceExecutionPage.getWindow(parentWindow);
		simulateMQServiceExecutionPage.waitForPageToLoad();
		String timeString = getDateTimeString() + "T00:00:00.000Z";
		ExtentLogger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " + timeString);
		logger.info("Submitting RO XML with RONumber: " + rONumber + ", time: " + timeString);
		String rOString = XMLUtil.getXMLString(rOXmlPath).replace("%DEALER_CODE%", dealerCode).replace("%RONUMBER%", rONumber)
				.replace("%TIMESTRING%", timeString).replace("%VIN%", vin).replace("%mileage%", mileage)
				.replace("%coverage_code%", coverageCode).replace("%PFP%", pFP).replace("%OPCODE%", opCode);
		simulateMQServiceExecutionPage.textBoxAllChars.clearAndSendKeys(rOString);
		simulateMQServiceExecutionPage.buttonExecute.click();
		getDriver().close();

		getDriver().switchTo().window(parentWindow);
		// adminHomePage.textboxSearch.sendKeys("CaseListReport" + Keys.ENTER);
		// adminHomePage.linkCaseListReport.click();
		// adminHomePage.closeTab("Service MQ");
		ServiceSimulationResultPage serviceSimulationResultPage = new ServiceSimulationResultPage();
		serviceSimulationResultPage.getWindow(parentWindow);
		serviceSimulationResultPage.waitForPageToLoad();
		serviceSimulationResultPage.txtOverallResultSuccess.assertText("Success", "Overall Status");
		getDriver().close();
		getDriver().switchTo().window(parentWindow);
		/*
		 * CaseListReportComponent caseListReportComponent = new
		 * CaseListReportComponent(); caseListReportComponent.waitForComponentToLoad();
		 * caseListReportComponent.buttonActions.click();
		 * caseListReportComponent.linkRun.click();
		 * adminHomePage.closeTab("CaseListReport");
		 * 
		 * CaseListReportPage caseListReportPage = new CaseListReportPage();
		 * caseListReportPage.getWindow(parentWindow);
		 * caseListReportPage.waitForPageToLoad(); caseListReportPage.filter(rONumber);
		 * getDriver().close(); getDriver().switchTo().window(parentWindow);
		 */

		// adminHomePage.getQueryRunner();
		hasROCreated(getrONumber());

		if (logOutRequired) {
			adminHomePage.logOut();
		}
		return rONumber;

	}

	public static void hasROCreated(String roNumber) {
		int time = 0;
		int endtime = ConfigurationManager.getBundle().getInt("timout.ro");
		AdminHomePage adminHomePage = new AdminHomePage();
		adminHomePage.waitForPageToLoad();
		adminHomePage.linkConfigureDevStudio.click();
		adminHomePage.hoverOnDDElement("System");
		adminHomePage.hoverOnDDElement("Database");
		adminHomePage.clickOnDDElement("Query runner");

		DatabaseQueryRunnerComponent queryRunner = new DatabaseQueryRunnerComponent();
		queryRunner.waitForComponentToLoad();

		String query = String.format("Select pxinsname from pegadata.pc_nsa_stat_work_grp1 where repairordernumber = '%s'",roNumber);

		
				
		String result = "No items";
		while ((result.equals("No items") || result.equals("0")) && time <= endtime) {
			time += 2;
			queryRunner.txtboxQueryToRun.clearAndSendKeys(query + Keys.TAB);
			queryRunner.btnRun.scrollAndClick();
			WaitUtil.sleep(2000);
			queryRunner.txtResult.moveToElement();
			result = queryRunner.txtResult.getText();
			logger.info("Case id is:" + result);
		}
		if (time <= endtime) {
			 logger.info("RO created with case id: " + result);
			ExtentLogger.pass(roNumber + " created with case id: " + result);
		} else {
			// logger.error("RO creation took longer than expected");

			throw new CustomRuntimeException("RO creation took longer than expected");

		}

	}

	private static String getCaseId(String roNumber) {

		String query = String.format(
				"Select pxinsname from pegadata.pc_nsa_stat_work_grp1 where repairordernumber = '%s'", roNumber);

		return new DatabaseQueryRunnerComponent().runQuery(query);

	}

	private static String getMileage(String mileage) {

		int intmileage = Integer.parseInt(mileage) + 1000;

		String returnMileage = String.valueOf(intmileage);

		setMileage(returnMileage);

		return returnMileage;
	}
	
	private static AdminHomePage getAdminObject(boolean loginRequired) {
		AdminHomePage admin;
		if (loginRequired) {
			admin = new AdminHomePage(Roles.ADMIN);
			admin.waitForPageToLoad();
		} else {
			admin = new AdminHomePage();
		}
		return admin;
	}
	
	
	
	private static Map<String, Object> getROOpenDate(Map<String, String> vehicleRef, String pFP, String opCode) {

		Map<String, Object> returnMap = new LinkedHashMap<>();
			String roOpenDate = vehicleRef.get("InServiceDate").replace("-", "");
			String make = vehicleRef.get("Make");
			String modelYear = vehicleRef.get("ModelYearVersionNumber");
			String vIN = vehicleRef.get("VIN");
			Map<String,String> vehicleWarranty=new LinkedHashMap<>();
			vehicleWarranty.put("WARRANTY_FLAG", "");
			vehicleWarranty.put("MONTH_LMT_QT", "0");
			vehicleWarranty.put("MILE_LMT_QT", "0");
			String mileage="";
			while(!vehicleWarranty.get("WARRANTY_FLAG").equals("OUT OF COVERAGE")) {
			int months = Integer.parseInt(vehicleWarranty.get("MONTH_LMT_QT"))+1;
			roOpenDate = DateUtil.getDate(roOpenDate, "yyyyMMdd", "yyyyMMdd",months);
			mileage = String.valueOf(Long.parseLong(vehicleWarranty.get("MILE_LMT_QT"))+1000);
			vehicleWarranty = DVehicleWarrantyUtil.getVehicleWarrantyMap(make, modelYear, mileage, opCode, pFP, vIN, roOpenDate);
			}
			returnMap.put("roOpenDate", roOpenDate);
			returnMap.put("mileage", mileage);
			return returnMap;

	}

	@Test
	public void datetime() {
		
		/*Map<String,String> vehicleWarrantys = DVehicleReferenceDetailsUtil.getVehicleReferenceMap("5N1AR2MM6EC698652");
		
		
		logger.info(getROOpenDate(vehicleWarrantys,"3121428X7C","JX56AA"));*/
		String rOXMLPath=System.getProperty("user.dir") + ConfigurationManager.getBundle().getString("roxml.path");
		String rOString = XMLUtil.getXMLString(rOXMLPath);
		logger.info(rOString);
	}

}
