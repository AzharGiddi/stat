package com.nissan.databeans;

import java.util.List;
import java.util.Objects;

import com.nissan.automation.core.utils.JsonToJavaObjectUtil;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.Content;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.ControlUnits;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.EngineDetails;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.ModelDetails;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.PreviousVersionsData;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBCriteria;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBExclusions;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBQuestions;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.Repairs;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.VINDetails;

public class TSBAPIDataBean {

	private String tsbId;
	
	public String getTsbId() {
		return tsbId;
	}


	public void setTsbId(String tsbId) {
		this.tsbId = tsbId;
	}


	private String isNNARegion;
	
	private String isNMXRegion;
	
	private String isNCIRegion;

	
	public String getIsNNARegion() {
		return isNNARegion;
	}


	public void setIsNNARegion(String isNNARegion) {
		this.isNNARegion = isNNARegion;
	}


	public String getIsNMXRegion() {
		return isNMXRegion;
	}


	public void setIsNMXRegion(String isNMXRegion) {
		this.isNMXRegion = isNMXRegion;
	}


	public String getIsNCIRegion() {
		return isNCIRegion;
	}


	public void setIsNCIRegion(String isNCIRegion) {
		this.isNCIRegion = isNCIRegion;
	}


	public List<ModelDetails> getModelList() {
		return modelList;
	}


	public void setModelList(List<ModelDetails> modelList) {
		this.modelList = modelList;
	}


	public List<VINDetails> getvINList() {
		return vINList;
	}


	public void setvINList(List<VINDetails> vINList) {
		this.vINList = vINList;
	}


	public List<EngineDetails> getEngineList() {
		return engineList;
	}


	public void setEngineList(List<EngineDetails> engineList) {
		this.engineList = engineList;
	}


	public List<TSBExclusions> gettSBExclusionsList() {
		return tSBExclusionsList;
	}


	public void settSBExclusionsList(List<TSBExclusions> tSBExclusionsList) {
		this.tSBExclusionsList = tSBExclusionsList;
	}


	public List<TSBCriteria> gettSBCriteriaList() {
		return tSBCriteriaList;
	}


	public void settSBCriteriaList(List<TSBCriteria> tSBCriteriaList) {
		this.tSBCriteriaList = tSBCriteriaList;
	}


	public List<TSBQuestions> gettSBQuestionsList() {
		return tSBQuestionsList;
	}


	public void settSBQuestionsList(List<TSBQuestions> tSBQuestionsList) {
		this.tSBQuestionsList = tSBQuestionsList;
	}


	public List<Repairs> getTSBRepairs() {
		return tSBRepairs;
	}


	public void setTSBRepairs(List<Repairs> tSBRepairs) {
		tSBRepairs = tSBRepairs;
	}

	private List<ControlUnits> controlUnitList;

	private List<ModelDetails> modelList;
	
	
	private List<VINDetails> vINList;
	
	
	private List<EngineDetails> engineList;

	
	private List<TSBExclusions> tSBExclusionsList;
	
	
	private List<TSBCriteria> tSBCriteriaList;

	
	private List<TSBQuestions> tSBQuestionsList;
	
	
	private List<Repairs> tSBRepairs;
	
	private void setContentObject(Content content) {
		
		setIsNNARegion(content.isNNARegion);
		setIsNCIRegion(content.isNCIRegion);
		setIsNMXRegion(content.isNMXRegion);
		setModelList(content.modelList);
		setvINList(content.vINList);
		setEngineList(content.engineList);
		settSBExclusionsList(content.tSBExclusionsList);
		settSBCriteriaList(content.tSBCriteriaList);
		settSBQuestionsList(content.tSBQuestionsList);
		setTSBRepairs(content.tSBRepairsList);
		
	}
	
private void setPreviousDataObject(PreviousVersionsData content) {
		
	setIsNNARegion(content.isNNARegion);
	setIsNCIRegion(content.isNCIRegion);
	setIsNMXRegion(content.isNMXRegion);
	setModelList(content.modelList);
	setvINList(content.vINList);
	setEngineList(content.engineList);
	settSBExclusionsList(content.tSBExclusionsList);
	settSBCriteriaList(content.tSBCriteriaList);
	settSBQuestionsList(content.tSBQuestionsList);
	setTSBRepairs(content.tSBRepairsList);
		
	}
	
	public TSBAPIDataBean(JsonToJavaObjectUtil object) {
		
		String status = object.getStatus();
		setTsbId(object.content.pyID);
		if(status.equals("Active")) {
			setContentObject(object.content);
		}else {
			List<PreviousVersionsData> dataList = object.content.previousVersionsData;
			if(Objects.isNull(dataList) || dataList.isEmpty()) {
				setContentObject(object.content);
			}else {
			setPreviousDataObject(dataList.get(dataList.size()-1));
			}
		}
				
	}


	public List<ControlUnits> getControlUnitList() {
		return controlUnitList;
	}


	public void setControlUnitList(List<ControlUnits> controlUnitList) {
		this.controlUnitList = controlUnitList;
	}

	
	
}
