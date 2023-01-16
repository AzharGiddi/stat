package com.nissan.automation.core.api.jsontojavaobjects;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class NAARules {

	@SerializedName("ARCDealerExemptionList")
	public List<ARCDealerExemptionList> aRCDealerExemptionList;
	
	@SerializedName("DealerSpecificList")
	public List<DealerSpecificList> dealerSpecificList;
	
	@SerializedName("ManufacturingDateList")
	public List<ManufacturingDate> manufacturingDateList;
	
	@SerializedName("MileageList")
	public List<Mileage> mileageList;
	
	@SerializedName("MonthsInServiceList")
	public List<MonthsInService> monthsInServiceList;
	
	@SerializedName("InServiceDateAvailability")
	public String inServiceDateAvailability;
	
	@SerializedName("PartsWarrantyCoverage")
	public String partsWarrantyCoverage;
	
	@SerializedName("ServiceContractCoverage")
	public String serviceContractCoverage;
	
	@SerializedName("VINList")
	public List<VIN> vINList;
	
	public class VIN{
		
		@SerializedName("ChasisRangeFrom")
		public String chasisRangeFrom;
		
		@SerializedName("ChasisNumberTo")
		public String chasisNumberTo;
		
		@SerializedName("Comparator")
		public String comparator;
		
		@SerializedName("StampingNumber")
		public String stampingNumber;
	}
	
	public class MonthsInService{
		
		@SerializedName("InServiceDate")
		public String inServiceDate;
		
		@SerializedName("ModelName")
		public String modelName;
		
		@SerializedName("ComponentCategory")
		public String componentCategory;
	}
	
	public class Mileage{
		
		@SerializedName("ComponentCategory")
		public String componentCategory;
		
		@SerializedName("Mileage_NAAR")
		public String mileage;
		
		@SerializedName("ModelName")
		public String modelName;
		
		
		
	}
	
	public class ManufacturingDate{
		
		@SerializedName("ComponentCategory")
		public String componentCategory;
		
		@SerializedName("FromDate")
		public String fromDate;
		
		@SerializedName("ToDate")
		public String toDate;
		
		@SerializedName("ModelName")
		public String modelName;
	}
	
	
	
	
	
	
	public List<String> getArcDealerExemptionList(){
		List<String> returnList = new ArrayList<>();
		
		for(ARCDealerExemptionList arcDealer: aRCDealerExemptionList) {
			
			returnList.add(arcDealer.dealerCode);
		}
		
		return returnList;
		
		
	}
	
	public List<String> getDealerSpecificList(){
		List<String> returnList = new ArrayList<>();
		
		for(DealerSpecificList dealer: dealerSpecificList) {
			
			returnList.add(dealer.dealerCode);
		}
		
		return returnList;
		
		
	}
	
	
	public class ARCDealerExemptionList{
		
		@SerializedName("DealerCode")
		public String dealerCode;
				
	}
	
public class DealerSpecificList{
		
	@SerializedName("DealerCode")
	public String dealerCode;
		
	}
}
