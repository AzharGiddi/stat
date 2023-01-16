package com.nissan.automation.core.utils;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBCriteria;

public class JsonToJavaObjectUtil {

	

	@SerializedName("status")
	private String status;

	@SerializedName("content")
	public Content content;

	public String getStatus() {
		return this.status;
	}

	public class Content {

		@SerializedName("pyID")
		public String pyID;

		@SerializedName("IsNNARegion")
		public String isNNARegion;
		
		@SerializedName("IsNMXRegion")
		public String isNMXRegion;
		
		@SerializedName("IsNCIRegion")
		public String isNCIRegion;
		
		@SerializedName("EngineCode")
		public String engineCode;
		
		@SerializedName("DTCComponents")
		public List<ControlUnits> controlUnitList;
		
		@SerializedName("ESMDTCList")
		public List<ESMDTCList> esmDTCList;

		@SerializedName("ModelList")
		public List<ModelDetails> modelList;
		
		@SerializedName("VINList")
		public List<VINDetails> vINList;
		
		@SerializedName("EngineList")
		public List<EngineDetails> engineList;

		@SerializedName("TSBExclusions")
		public List<TSBExclusions> tSBExclusionsList;
		
		@SerializedName("TSBCriteria")
		public List<TSBCriteria> tSBCriteriaList;

		@SerializedName("TSBQuestions")
		public List<TSBQuestions> tSBQuestionsList;
		
		@SerializedName("TSBRepairs")
		public List<Repairs> tSBRepairsList;
		
		@SerializedName("PreviousVersionsData")
		public List<PreviousVersionsData> previousVersionsData;

	}
	
public class PreviousVersionsData {
		
		@SerializedName("IsNNARegion")
		public String isNNARegion;
		
		@SerializedName("IsNMXRegion")
		public String isNMXRegion;
		
		@SerializedName("IsNCIRegion")
		public String isNCIRegion;
		
		@SerializedName("EngineCode")
		public String engineCode;
		
		@SerializedName("ESMDTCList")
		public List<ESMDTCList> esmDTCList;
		
		@SerializedName("DTCComponents")
		public List<ControlUnits> controlUnitList;

		@SerializedName("ModelList")
		public List<ModelDetails> modelList;
		
		@SerializedName("VINList")
		public List<VINDetails> vINList;
		
		@SerializedName("EngineList")
		public List<EngineDetails> engineList;
		
		@SerializedName("TSBExclusions")
		public List<TSBExclusions> tSBExclusionsList;

		@SerializedName("TSBCriteria")
		public List<TSBCriteria> tSBCriteriaList;
		
		@SerializedName("TSBQuestions")
		public List<TSBQuestions> tSBQuestionsList;
		
		@SerializedName("TSBRepairs")
		public List<Repairs> tSBRepairsList;
		
		@SerializedName("TSBReferenceList")
		public List<TSBReferenceList> tsbReferenceList;

		
	}
	
	public class ControlUnits{
		
		@SerializedName("Description")
		public String description;
		
	}
	
public class ESMDTCList{
		
		@SerializedName("FFD_DTC")
		public String dtc;
		
	}

	public class ModelDetails {

		@SerializedName("EngineModelCode")
		public String engineModelCode;

		@SerializedName("Model")
		public String model;

		@SerializedName("ModelYearFrom")
		public String modelYearFrom;
		
		@SerializedName("ModelYearTo")
		public String modelYearTo;

		@SerializedName("Operator")
		public String operator;

	}
	
	public class VINDetails{
		
		@SerializedName("ChasisRangeFrom")
		public String chasisRangeFrom;
		
		@SerializedName("ChasisRangeTo")
		public String chasisRangeTo;
		
		@SerializedName("Operator")
		public String operator;
		
		@SerializedName("StampingNumber")
		public String stampingNumber;
		
		
	}
	
	public class EngineDetails{
		
		
		@SerializedName("EngineCode")
		public String engineCode;
		
	}

	public class TSBExclusions {

		@SerializedName("EvaluationCriteria")
		public String evaluationCriteria;

		@SerializedName("TSBCondition")
		public List<TSBConditions> tSBConditionsList;

	}

	public class TSBCriteria implements Comparable<TSBCriteria>{

		@SerializedName("EvaluationCriteria")
		public String evaluationCriteria;

		@SerializedName("DoesNotApply")
		public String doesNotApply;

		@SerializedName("TSBCondition")
		public List<TSBConditions> tSBConditionsList;
		
		public int compareTo(TSBCriteria criteria) {
			
			boolean dNotApply = Boolean.parseBoolean(criteria.doesNotApply);
			
			return dNotApply?1:-1;
		}

	}

	public class TSBConditions {

		@SerializedName("FieldName")
		public String fieldName;

		@SerializedName("Operator")
		public String operator;

		@SerializedName("TSBMultiselect")
		public List<TSBMultiselect> tSBMultiselectList;
	}

	public class TSBMultiselect {

		@SerializedName("LineNumber")
		public String lineNumber;

		@SerializedName("Make")
		public String make;

		@SerializedName("pySelected")
		public String pySelected;

	}

	public class TSBQuestions{
		
		public List<Map<String,Object>> engineList;
		
	}
	
	public class Repairs{
		
		
		@SerializedName("pySelected")
		public String pySelected;
		
		@SerializedName("RepairID")
		public String repairID;
		
		@SerializedName("RepairNote")
		public String repairNote;
		
		@SerializedName("RepairTitle")
		public String repairTitle;
		
		
	}
	
	public class TSBReferenceList{
		
		@SerializedName("TSBReference")
		public String tsbReference;
		
	}
	
	

}
