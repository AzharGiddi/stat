package com.nissan.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.nissan.automation.core.utils.APIUtil;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.utils.DCategoryByPFPOPCODEUtil.ComponentCategoryList.pxResults;


public class DCategoryByPFPOPCODEUtil {

	private DCategoryByPFPOPCODEUtil() {
		
		
	}
	
	
	
	public static List<pxResults> getComponentCategory(String pFP) {
		String baseUri = ConfigurationManager.getBundle().getString("base.uri");
		String url = baseUri+String.format("data/D_CategoryByPFPOPCODE_Automation?PFP=%s",pFP);
		String responseString = APIUtil.getResponseObject(url).asString();
		return getUniqueList(new Gson().fromJson(responseString, ComponentCategoryList.class).pxResults);

	}
	
	private static List<pxResults> getUniqueList(List<pxResults> repairList){
		
		List<pxResults> outPutList = new ArrayList<>();
		
		for(pxResults repair : repairList) {
			if(outPutList.contains(repair)) {
				continue;
			}
			outPutList.add(repair);
		}
		return outPutList;
		
		
		
	}
public class ComponentCategoryList{
		
		@SerializedName("pxResults")
		public List<pxResults> pxResults;
		
		public class pxResults{
			
			@SerializedName("ClaimType")
			public String componentCategory;
			
			@SerializedName("RepairTitle")
			public String repairTitle;
			
			@SerializedName("RequiresGoodwillReview")
			public Boolean requiresGoodwillReview;
			
			@SerializedName("RepairPartAuthorization")
			public Boolean repairPartAuthorization;
			
			@Override
			public boolean equals(Object obj) {
				
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				pxResults other = (pxResults) obj;
				if(this.componentCategory==null) {
					if(other.componentCategory!=null) {
						return false;
					}
				}else if(!this.componentCategory.equals(other.componentCategory)){
					return false;
				}
				
				if(this.repairTitle==null) {
					if(other.repairTitle!=null) {
						return false;
					}
				}else if(!this.repairTitle.equals(other.repairTitle)){
					return false;
				}
				
				if(this.repairPartAuthorization==null) {
					if(other.repairPartAuthorization!=null) {
						return false;
					}
				}else if(!this.repairPartAuthorization.equals(other.repairPartAuthorization)){
					return false;
				}
				
				if(this.requiresGoodwillReview==null) {
					if(other.requiresGoodwillReview!=null) {
						return false;
					}
				}else if(!this.requiresGoodwillReview.equals(other.requiresGoodwillReview)){
					return false;
				}
				
				return true;
			}
			
		}
		
	}


	
	
	
	@Test
	public void test() {
		
	//System.out.println(getVCAN("3121428x7c").get(1).requiresGoodwillReview);
	}
	
	
}
