package com.nissan.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;

import com.nissan.automation.core.utils.APIUtil;
import com.nissan.configuration.ConfigurationManager;

import io.restassured.response.Response;

public class DApplicableWarrantyUtil {

	private DApplicableWarrantyUtil() {

	}

	private static final Log logger = LogFactoryImpl.getLog(ConfigurationManager.class);

	public static Map<String, String> getApplicableWarrantyMap(String vIN, String component) {

		Map<String, String> params = new LinkedHashMap<>();
		params.put("VIN", vIN);
		List<Map<String, String>> applicableWarrantyMaps = APIUtil.getListOfMaps("D_ApplicableWarranty", params,
				"pxResults");

		return getApplicableWarrantyMap(component, applicableWarrantyMaps);

	}

	private static Map<String, String> getApplicableWarrantyMap(String componentDescription,
			List<Map<String, String>> applicableWarrantyList) {

		Map<String, String> returnMap = new LinkedHashMap<>();
		for (Map<String, String> applicableWarrantyMap : applicableWarrantyList) {

			String description = applicableWarrantyMap.get("pyDescription");
		//	logger.info(description);
			if (description.contains(componentDescription.toUpperCase())) {
				logger.info("matched");
				returnMap = applicableWarrantyMap;
				break;
			}

		}
		
		if(returnMap.isEmpty()) {
			logger.info("Applicable warranty not found for "+componentDescription);
		}

		return returnMap;
	}

}
