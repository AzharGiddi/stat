package com.nissan.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.APIUtil;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.utils.DActiveGWCouponsListUtil.ActiveGWCouponsList.Result;



public class DActiveGWCouponsListUtil {

	private DActiveGWCouponsListUtil() {
		
		
		
	}
	
	public static boolean isNewCouponAvailable=false;
	
	private static List<Result> getGoodWillCouponsList(String vIN){
		
		String url = ConfigurationManager.getBundle().getString("base.uri")+String.format("data/D_ActiveGWCouponsList?VIN=%s",vIN);
		
		String responseString = APIUtil.getResponseObject(url).asString();
		
		return new Gson().fromJson(responseString, ActiveGWCouponsList.class).results;
		
	}
	
	
	public static List<String> getCouponLinkedGoodWillCaseId(String vIN) {
		isNewCouponAvailable=false;
		List<Result> results = getGoodWillCouponsList(vIN);
		
		List<String> gwList = new ArrayList<>();
		int activeCouponCount =0;
		for(Result result: results) {
			if(Objects.isNull(result.pyStatusWork)) {
				
				if(Objects.isNull(result.gWCaseID)) {
					isNewCouponAvailable=true;
					activeCouponCount++;
					if(activeCouponCount>1) {
						throw new CustomRuntimeException("Can't have more than 1 active coupons for same VIN:"+gwList); 
					}
				}
				gwList.add(result.gWCaseID);
				
			}
		}
		/*int listSize = gwList.size();
		String returnCaseID = "";
		if(listSize==1) {
			if(Objects.isNull(gwList.get(0))) {
				
				isNewCouponAvailable=true;
				returnCaseID="";
			
			}else {
				returnCaseID=gwList.get(0);
			}
			
		}else if(listSize==0) {
			returnCaseID="";
		}else {
			
			throw new CustomRuntimeException("Can't have more than 1 active coupons for same VIN:"+gwList);
		}
		*/
		return gwList;

	}
	
	
	public class ActiveGWCouponsList{
			
		
		@SerializedName("pxResults")
		public List<Result> results;
		
		public class Result{
			
			@SerializedName("pyStatusWork")
			public String pyStatusWork;
			
			@SerializedName("GWCaseID")
			public String gWCaseID;
			
			@SerializedName("GWCaseKey")
			public String gWCaseKey;
			
			@SerializedName("CARequestID")
			public String couponId;
		}
	}
	
	
}
