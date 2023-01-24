package com.automation.core.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.testng.annotations.Test;

import com.automation.core.configuration.ConfigurationManager;
import com.automation.core.exceptions.CustomRuntimeException;
import com.automation.core.reports.ExtentLogger;
import com.google.gson.Gson;
import com.utils.LoginAPI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIUtil {

	private APIUtil() {

	}

	/*
	 * private static RequestSpecification getRequestSpecification() {
	 * 
	 * try { RequestSpecification resp =
	 * RestAssured.given().auth().basic("vardhanib", "rules");
	 * 
	 * return resp; } catch (Exception e) { RequestSpecification resp =
	 * RestAssured.given().auth().basic("vardhanib", "rules");
	 * 
	 * return resp; }
	 * 
	 * }
	 * 
	 * private static RequestSpecification getRequestSpecification(Map<String,
	 * String> params) {
	 * 
	 * RequestSpecification resp = getRequestSpecification().queryParams(params);
	 * 
	 * return resp;
	 * 
	 * }
	 * 
	 * public static Response getResponse(String dataPageName, Map<String, String>
	 * params) { try { RestAssured.baseURI =
	 * ConfigurationManager.getBundle().getString("base.uri"); RestAssured.basePath
	 * = "/data/" + dataPageName;
	 * 
	 * return
	 * getRequestSpecification(params).when().get().then().statusCode(200).extract()
	 * .response(); } catch (Exception e) { RestAssured.baseURI =
	 * ConfigurationManager.getBundle().getString("base.uri"); RestAssured.basePath
	 * = "/data/" + dataPageName;
	 * 
	 * return
	 * getRequestSpecification(params).when().get().then().statusCode(200).extract()
	 * .response(); } }
	 * 
	 * public static Response getResponseObject(String url) { try { return
	 * getRequestSpecification().when().get(url).then().statusCode(200).extract().
	 * response(); } catch (Exception e) { return getResponseObject(url); } }
	 * 
	 * public static String getResponseAsString(String url) {
	 * 
	 * return
	 * getRequestSpecification().when().get(url).then().statusCode(200).extract().
	 * response().asString();
	 * 
	 * }
	 * 
	 * public static Response getResponse(String dataPageName) {
	 * 
	 * RestAssured.baseURI = ConfigurationManager.getBundle().getString("base.uri");
	 * RestAssured.basePath = "/data/" + dataPageName; long startTime =
	 * System.currentTimeMillis();
	 * 
	 * while (true) { long endTime = System.currentTimeMillis(); if (endTime -
	 * startTime > 30000) { throw new CustomRuntimeException("Request Timed out"); }
	 * try {
	 * 
	 * return
	 * getRequestSpecification().when().get().then().statusCode(200).extract().
	 * response(); } catch (Exception e) { ExtentLogger.info("Exception occured :" +
	 * e.getClass().getName() + " trying again...", false);
	 * 
	 * } }
	 * 
	 * }
	 * 
	 * 
	 *//***
		 * 
		 * @param caseType - Type of the case
		 * @param caseId
		 * @return
		 */
	/*
	 * public static Response getResponse(String caseType, String caseId) {
	 * 
	 * RestAssured.baseURI = ConfigurationManager.getBundle().getString("base.uri");
	 * RestAssured.basePath = "/" + caseType + "/" + caseId;
	 * 
	 * return
	 * getRequestSpecification().when().get().then().statusCode(200).extract().
	 * response();
	 * 
	 * }
	 * 
	 * public static Map<String, String> getMapFromResponse(Response response,
	 * String path) {
	 * 
	 * return response.jsonPath().getMap(path); }
	 * 
	 * public static Response getResponseObject(String dataType, String caseType,
	 * Map<String, String> params) {
	 * 
	 * RestAssured.baseURI = ConfigurationManager.getBundle().getString("base.uri");
	 * RestAssured.basePath = "/" + dataType + "/" + caseType; return
	 * getRequestSpecification(params).when().get().then().statusCode(200).extract()
	 * .response(); }
	 * 
	 *//***
		 * Get List of Map of Key value pairs matching given json path
		 * 
		 * @param response - Response object
		 * @param path     - JSON path to the object
		 * @return List of Map of Key value pairs matching the given JSON Path.
		 */

	/*
	 * public static List<Map<String, String>> getListOfMaps(Response response,
	 * String path) {
	 * 
	 * return response.jsonPath().getJsonObject(path); }
	 * 
	 *//***
		 * Get List of Map of Key value pairs matching given json path
		 * 
		 * @param dataPageName - Name of the data table e.g D_ApplicableTSBs, D_
		 * @param params       - Map<String,String>, input parameters map of key value
		 *                     pair
		 * @param path         - JSON path to the object
		 * @return List of Map of Key value pairs matching the given JSON Path.
		 *//*
			 * public static List<Map<String, String>> getListOfMaps(String dataPageName,
			 * Map<String, String> params, String path) {
			 * 
			 * return getResponse(dataPageName, params).jsonPath().getJsonObject(path); }
			 */

	public static Response post(String uri, Map<String, String> params, String reqBody) {

		String accessToken = getToken();
		params = Objects.isNull(params) ? new HashMap<>() : params;
		return RestAssured.given().auth().oauth2(accessToken).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).params(params).body(reqBody)
				.when().post(uri)
				.then().extract().response();
	}

	public static Response post(String uri, Map<String, String> params, File reqBody) {
		String accessToken = getToken();
		return RestAssured.given().auth().oauth2(accessToken).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).params(params)
				.when().body(reqBody).post(uri)
				.then().extract().response();
	}

	public static Response get(String uri, Map<String, String> params) {
		String accessToken = getToken();
		params = Objects.isNull(params) ? new HashMap<>() : params;
		return RestAssured.given().auth().oauth2(accessToken).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).params(params)
				.when().get(uri)
				.then().extract()
				.response();
	}
	
	public static Response put(String uri, Map<String, String> params, String reqBody) {

		String accessToken = getToken();
		params = Objects.isNull(params) ? new HashMap<>() : params;
		return RestAssured.given().auth().oauth2(accessToken).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).params(params).body(reqBody)
				.when().put(uri)
				.then().extract().response();
	}


	public static Response delete(String uri, Map<String, String> params) {

		String accessToken = getToken();
		params = Objects.isNull(params) ? new HashMap<>() : params;
		return RestAssured.given().auth().oauth2(accessToken).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).params(params)
				.when().delete(uri)
				.then().extract()
				.response();
	}

	public static String getToken() {

		String uri = ConfigurationManager.getBundle().getString("base.uri") + "auth/login";
		String filePath = "./data/Login.json";
		File reqBody = new File(filePath);
		Response response = RestAssured.given().auth().none().header("Content-Type", "application/json")
				.contentType(ContentType.JSON)
				.when().body(reqBody).post(uri)
				.then().statusCode(200).extract()
				.response();
		Gson gson = new Gson();
		LoginAPI loginApi = gson.fromJson(response.asString(), LoginAPI.class);
		return loginApi.data.accessToken;

	}

	@Test
	public void getResp() {

	}

}
