package com.nissan.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nissan.automation.core.utils.APIUtil;

import io.restassured.response.Response;

public class DVehicleWarrantyUtil {

	private DVehicleWarrantyUtil() {

	}

	public static Map<String, String> getVehicleWarrantyMap(Map<String, String> params) {

		Response response = APIUtil.getResponse("D_VehicleWarranty", params);
		return APIUtil.getMapFromResponse(response, "WARRANTY_DB_OUTPUT");

	}
	
	public static Map<String, String> getVehicleWarrantyMap(String make,String modelYear,String mileage,String opCode,String pFP,String vIN,String rOOpenDateString) {
		
		Map<String, String> params = new LinkedHashMap<>();
		params.put("Make", make);
		params.put("ModelYear", modelYear);
		params.put("Mileage", mileage);
		params.put("OPCODE", opCode);
		params.put("PFP", pFP);
		params.put("VIN", vIN);
		params.put("ROOpenDate", rOOpenDateString);
		
		return getVehicleWarrantyMap(params);

	}

}
