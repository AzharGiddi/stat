package com.nissan.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.nissan.automation.core.utils.APIUtil;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.utils.ApplicableESMsUtil.ApplicableESMListObject.pxResults;

import io.restassured.path.json.JsonPath;

public class ApplicableESMsUtil {

	private ApplicableESMsUtil() {

	}

	/*public static List<String> getApplicableTSBs(String modelCode, String modelYear, String componentCategory) {

		Map<String, String> params = new LinkedHashMap<>();
		params.put("ModelCode", modelCode);
		params.put("ModelYear", modelYear);
		params.put("ComponentCategory", componentCategory);

		return APIUtil.getResponse("D_ApplicableESMs", params).jsonPath().getList("pxResults.pzInsKey");

	}*/

	public static List<pxResults> getApplicableESM(String modelCode, String modelYear, String componentCategory,String typeOfRule) {
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+String.format(
				"data/D_ApplicableESMsList_Automation?ModelCode=%s&ModelYear=%s&ComponentCategory=%s&TypeOfRule=%s",
				modelCode, modelYear, componentCategory,typeOfRule);
		String responseString = APIUtil.getResponseObject(url).asString();
		return new Gson().fromJson(responseString, ApplicableESMListObject.class).pxResults;

	}

	/*public static Map<String, Object> getTSBContent(String tSBId) {

		return APIUtil.getResponse("cases", tSBId).jsonPath().getMap("content");

	}*/

/*	public static JsonPath getTSBContentAsResponse(String tSBId) {
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+String.format("cases/%s", tSBId);
		return APIUtil.getResponseString(url).jsonPath();

	}*/

	public static JsonToJavaObjectUtil getESMContentAsObject(String tSBId) {
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+String.format("cases/%s", tSBId);
		String responseString = APIUtil.getResponseObject(url).asString(); 
		
		return new Gson().fromJson(responseString, JsonToJavaObjectUtil.class);

	}
	
	public class ApplicableESMListObject{
		
		@SerializedName("pxResults")
		public List<pxResults> pxResults;
		
		public class pxResults{
			
			@SerializedName("pyStatusWork")
			public String pyStatusWork;
			
			@SerializedName("pzInsKey")
			public String pzInsKey;
			
			@SerializedName("pyID")
			public String tSBID;
		}
		
		
		
	}

	@Test
	public void test() {

		// System.out.println(getApplicableTSBs("L32", "2010",
		// "CVT").jsonPath().getList("pxResults.pzInsKey"));
		// System.out.println(getTSBContent("NSA-STAT-WORK-F1-TSB SB-46024"));

		JsonToJavaObjectUtil map = getESMContentAsObject("NSA-STAT-WORK-F1-TSB SB-30030");
		//Object obj = TSBAPIUtil.isTSBApplicable(map, null);
		

		

		// List<Map<String,Object>> prevMap = (List<Map<String, Object>>)
		// map.get("PreviousVersionsData");

		// System.out.println("prevmap size is "+prevMap.get(prevMap.size()-1));

	}

}
