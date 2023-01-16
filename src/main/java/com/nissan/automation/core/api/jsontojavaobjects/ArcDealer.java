package com.nissan.automation.core.api.jsontojavaobjects;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ArcDealer {

	@SerializedName("pxResults")
	private List<Result> results;
	
	public boolean isArcDealer(String dealerCode) {
		
		return getArcDealerList(this.results).contains(dealerCode);
	}
	
	private List<String> getArcDealerList(List<Result> results){
		List<String> returnList = new ArrayList<>();
		for(Result result: results) {
			
			returnList.add(result.dealerCode);
		}
		
		
		return returnList;
	}
	
	public class Result{
		
		@SerializedName("DealerCode")
		private String dealerCode;
		
	}
}
