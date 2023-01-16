package com.nissan.automation.core.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

import com.nissan.configuration.ConfigurationManager;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.reports.ExtentLogger;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIUtil {

	private APIUtil() {

	}

	private static RequestSpecification getRequestSpecification() {

		try {
			RequestSpecification resp = RestAssured.given().auth().basic("vardhanib", "rules");

			return resp;
		} catch (Exception e) {
			RequestSpecification resp = RestAssured.given().auth().basic("vardhanib", "rules");

			return resp;
		}

	}

	private static RequestSpecification getRequestSpecification(Map<String, String> params) {

		RequestSpecification resp = getRequestSpecification().queryParams(params);

		return resp;

	}

	public static Response getResponse(String dataPageName, Map<String, String> params) {
		try {
			RestAssured.baseURI = ConfigurationManager.getBundle().getString("base.uri");
			RestAssured.basePath = "/data/" + dataPageName;

			return getRequestSpecification(params).when().get().then().statusCode(200).extract().response();
		} catch (Exception e) {
			RestAssured.baseURI = ConfigurationManager.getBundle().getString("base.uri");
			RestAssured.basePath = "/data/" + dataPageName;

			return getRequestSpecification(params).when().get().then().statusCode(200).extract().response();
		}
	}

	public static Response getResponseObject(String url) {
		try {
			return getRequestSpecification().when().get(url).then().statusCode(200).extract().response();
		} catch (Exception e) {
			return getResponseObject(url);
		}
	}

	public static String getResponseAsString(String url) {

		return getRequestSpecification().when().get(url).then().statusCode(200).extract().response().asString();

	}
	
	public static Response getResponse(String dataPageName) {

		RestAssured.baseURI = ConfigurationManager.getBundle().getString("base.uri");
		RestAssured.basePath = "/data/" + dataPageName;
		long startTime = System.currentTimeMillis();

		while (true) {
			long endTime = System.currentTimeMillis();
			if (endTime - startTime > 30000) {
				throw new CustomRuntimeException("Request Timed out");
			}
			try {
				
				return getRequestSpecification().when().get().then().statusCode(200).extract().response();
			} catch (Exception e) {
				ExtentLogger.info("Exception occured :" + e.getClass().getName() + " trying again...", false);

			}
		}

	}
	
	
/***
 * 
 * @param caseType - Type of the case
 * @param caseId
 * @return
 *//*
	public static Response getResponse(String caseType, String caseId) {

		RestAssured.baseURI = ConfigurationManager.getBundle().getString("base.uri");
		RestAssured.basePath = "/" + caseType + "/" + caseId;

		return getRequestSpecification().when().get().then().statusCode(200).extract().response();

	}*/

	public static Map<String, String> getMapFromResponse(Response response, String path) {

		return response.jsonPath().getMap(path);
	}

	public static Response getResponseObject(String dataType, String caseType, Map<String, String> params) {

		RestAssured.baseURI = ConfigurationManager.getBundle().getString("base.uri");
		RestAssured.basePath = "/" + dataType + "/" + caseType;
		return getRequestSpecification(params).when().get().then().statusCode(200).extract().response();
	}

	/***
	 * Get List of Map of Key value pairs matching given json path
	 * 
	 * @param response - Response object
	 * @param path - JSON path to the object
	 * @return List of Map of Key value pairs matching the given JSON Path.
	 */
	public static List<Map<String, String>> getListOfMaps(Response response, String path) {

		return response.jsonPath().getJsonObject(path);
	}

	/***
	 * Get List of Map of Key value pairs matching given json path
	 * 
	 * @param dataPageName - Name of the data table e.g D_ApplicableTSBs, D_
	 * @param params - Map<String,String>, input parameters map of key value pair
	 * @param path - JSON path to the object
	 * @return List of Map of Key value pairs matching the given JSON Path.
	 */
	public static List<Map<String, String>> getListOfMaps(String dataPageName, Map<String, String> params,
			String path) {

		return getResponse(dataPageName, params).jsonPath().getJsonObject(path);
	}

	@Test
	public void getResp() {

		

	}

}
