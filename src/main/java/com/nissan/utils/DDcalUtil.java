package com.nissan.utils;

import org.testng.annotations.Test;

import com.nissan.automation.core.utils.APIUtil;
import com.nissan.configuration.ConfigurationManager;

public class DDcalUtil {

	private DDcalUtil() {
		
		
	}
	
	/*public static int getDcalAmount(String dealerCode) {
		
		String url = "https://vcat.sit.nnanet.com/prweb/api/v1/data/D_GetDealerDCAL?DealerCode="+dealerCode;
		
		return APIUtil.getResponseString(url).jsonPath().getInt("pxResults[0].NewDCALValue");
		
	}*/
	
	public static double getGWDcalAmount(String dealerCode, String rOOpenDate) {
		
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		
		String url = baseUri+ String.format("data/D_GoodWillDCALList?DealerCode=%s&ROOpenDate=%s",dealerCode,rOOpenDate);
		
		return  APIUtil.getResponseObject(url).jsonPath().getDouble("pxResults[0].DCAL");
		
	}
	
	@Test
	public void test() {
		
		System.out.println(getGWDcalAmount("5408","20200102"));
	}
	
	
}
