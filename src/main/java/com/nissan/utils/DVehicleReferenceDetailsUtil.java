package com.nissan.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.openqa.selenium.Keys;

import com.nissan.automation.core.utils.APIUtil;
import com.nissan.automation.core.utils.StringUtil;
import com.nissan.driver.DriverFactory;
import com.nissan.driver.DriverManager;
import com.nissan.enums.Roles;
import com.nissan.pages.AdminHomePage;
import com.nissan.pages.DVehicleReferenceDataPage;

import io.restassured.response.Response;

public class DVehicleReferenceDetailsUtil {

	private DVehicleReferenceDetailsUtil() {

	}
	
	private static final Log logger = LogFactoryImpl.getLog(DVehicleReferenceDetailsUtil.class);
	
	public static Map<String, String> getVehicleReferenceMap(String vin, boolean adminLoginRequired,
			boolean logOutRequired) {

		AdminHomePage adminPage;

		if (adminLoginRequired) {
			adminPage = new AdminHomePage(Roles.ADMIN);
		} else {
			adminPage = new AdminHomePage();
		}
		Map<String, String> vehicleDetails = new HashMap<>();
		adminPage.textboxSearch.clearAndSendKeys("d_vehiclereference" + Keys.ENTER);
		adminPage.linkDVehicleRef.click();
		adminPage.getdVehicleReferenceComponent().buttonActions.click();
		adminPage.getdVehicleReferenceComponent().linkRun.click();

		DVehicleReferenceDataPage dVehicleReferenceDataPage = new DVehicleReferenceDataPage();
		dVehicleReferenceDataPage.waitForPageToLoad();
		dVehicleReferenceDataPage.textboxVIN.clearAndSendKeys(vin + Keys.TAB);
		dVehicleReferenceDataPage.btnRun.click();
		dVehicleReferenceDataPage.headerResults.waitForPresent();

		Iterator<String> propertiesIterator = StringUtil
				.getStringList(dVehicleReferenceDataPage.listVehicleRefProperties).iterator();
		Iterator<String> valuesIterator = StringUtil.getStringList(dVehicleReferenceDataPage.listVehicleRefValues)
				.iterator();

		while (propertiesIterator.hasNext() && valuesIterator.hasNext()) {

			vehicleDetails.put(propertiesIterator.next(), valuesIterator.next());

		}
		dVehicleReferenceDataPage.close();
		DriverManager.getDriver().switchTo().window(DriverFactory.parentWindow);
		if (logOutRequired) {
			adminPage.logOut();
		}
		return vehicleDetails;

	}

	public static Map<String, String> getVehicleReferenceMap(String vIN) {

		Map<String, String> params = new LinkedHashMap<>();
		params.put("VIN", vIN);
		Response response = APIUtil.getResponse("D_VehicleReference", params);
		Map<String, String> returnMap = APIUtil.getMapFromResponse(response, "");
		//logger.info(returnMap);
		return returnMap;

	}
}
