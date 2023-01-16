package com.nissan.utils;

import java.util.Objects;

import org.testng.annotations.Test;

import com.nissan.automation.core.utils.APIUtil;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.exceptions.CustomRuntimeException;

import io.restassured.response.Response;

public class DViewVCANUtil {

	private DViewVCANUtil() {
		
		
	}
	
	public static String getVCAN(String dealerCode,String vIN, String rOL, String rONumber) {
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+String.format("data/D_ViewVCAN?DealerNumber=%s&VIN=%s&ROL=%s&WONumber=%s",dealerCode,vIN,rOL,rONumber);
		Response response=null;
		try {
			response = APIUtil.getResponseObject(url);
		return response.jsonPath().getString("VCANRefNumber").trim();
		}catch(Exception e) {
			//throw new CustomRuntimeException("Unabe to fetch VCAN due to "+e.getClass().getName()+","+ e.getLocalizedMessage());
			String errorMsg = Objects.isNull(response)?e.getLocalizedMessage():response.jsonPath().getString("pyDescription").trim();
			throw new CustomRuntimeException("Unable to fetch VCAN due to "+errorMsg);
		}
		
	}
	
public static String getPFPPO(String dealerCode,String vIN, String rOL, String rONumber) {
	String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+String.format("data/D_ViewVCAN?DealerNumber=%s&VIN=%s&ROL=%s&WONumber=%s",dealerCode,vIN,rOL,rONumber);
		Response response=null;
		try {
			response = APIUtil.getResponseObject(url);
		return response.jsonPath().getString("PFPPO").trim();
		}catch(Exception e) {
			//throw new CustomRuntimeException("Unabe to fetch VCAN due to "+e.getClass().getName()+","+ e.getLocalizedMessage());
			String errorMsg = Objects.isNull(response)?e.getLocalizedMessage():response.jsonPath().getString("pyDescription").trim();
			throw new CustomRuntimeException("Unable to fetch VCAN due to "+errorMsg);
		}
		
	}
	
	
	
	@Test
	public void test() {
		
	System.out.println(getVCAN("5408","5N1AT2MV5GC840261","1","AT-397763"));
	}
	
	
}

