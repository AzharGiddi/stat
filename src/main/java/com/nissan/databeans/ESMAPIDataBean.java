package com.nissan.databeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.nissan.automation.core.utils.JsonToJavaObjectUtil;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.Content;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.ControlUnits;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.ESMDTCList;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.ModelDetails;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.PreviousVersionsData;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.Repairs;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBCriteria;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBExclusions;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBQuestions;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.TSBReferenceList;
import com.nissan.automation.core.utils.JsonToJavaObjectUtil.VINDetails;

public class ESMAPIDataBean {

	private String esmId;
	
	
	
	public String getESMId() {
		return esmId;
	}


	public void setESMId(String tsbId) {
		this.esmId = tsbId;
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


	private String engineCode;
	
	public String getEngineCode() {
		return engineCode;
	}


	public void setEngineCode(String engineCode) {
		this.engineCode = engineCode;
	}


	public List<ControlUnits> getControlUnitsList() {
		return controlUnitsList;
	}


	public void setControlUnitsList(List<ControlUnits> controlUnitsList) {
		this.controlUnitsList = controlUnitsList;
	}


	public List<ESMDTCList> getDtcList() {
		return dtcList;
	}


	public void setDtcList(List<ESMDTCList> dtcList) {
		this.dtcList = dtcList;
	}


	public List<ModelDetails> getModelList() {
		return Objects.isNull(this.modelList)?new ArrayList<>():this.modelList;
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


	/*public List<EngineDetails> getEngineList() {
		return engineList;
	}


	public void setEngineList(List<EngineDetails> engineList) {
		this.engineList = engineList;
	}*/


	public List<TSBExclusions> getESMExclusionsList() {
		return tSBExclusionsList;
	}


	public void setESMExclusionsList(List<TSBExclusions> tSBExclusionsList) {
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
		this.tSBRepairs = tSBRepairs;
	}

	private List<ESMDTCList> dtcList= new ArrayList<>();
	
	private List<ControlUnits> controlUnitsList= new ArrayList<>();
	
	private List<ModelDetails> modelList= new ArrayList<>();
	
	private List<VINDetails> vINList= new ArrayList<>();
	
	private List<TSBExclusions> tSBExclusionsList= new ArrayList<>();
	
	private List<TSBCriteria> tSBCriteriaList= new ArrayList<>();
	
	private List<TSBQuestions> tSBQuestionsList= new ArrayList<>();
	
	private List<Repairs> tSBRepairs= new ArrayList<>();
	
	private List<TSBReferenceList> tsbReferenceList= new ArrayList<>();
	
	private void setContentObject(Content content) {
		
		setIsNNARegion(content.isNNARegion);
		setIsNCIRegion(content.isNCIRegion);
		setIsNMXRegion(content.isNMXRegion);
		setEngineCode(content.engineCode);
		setControlUnitsList(content.controlUnitList);
		setDtcList(content.esmDTCList);
		setvINList(content.vINList);
		setESMExclusionsList(content.tSBExclusionsList);
		settSBCriteriaList(content.tSBCriteriaList);
		settSBQuestionsList(content.tSBQuestionsList);
		setTSBRepairs(content.tSBRepairsList);
		
	}
	
private void setPreviousDataObject(PreviousVersionsData content) {
		
	setIsNNARegion(content.isNNARegion);
	setIsNCIRegion(content.isNCIRegion);
	setIsNMXRegion(content.isNMXRegion);
	setEngineCode(content.engineCode);
	setControlUnitsList(content.controlUnitList);
	setDtcList(content.esmDTCList);
	setvINList(content.vINList);
	setESMExclusionsList(content.tSBExclusionsList);
	settSBCriteriaList(content.tSBCriteriaList);
	settSBQuestionsList(content.tSBQuestionsList);
	setTSBRepairs(content.tSBRepairsList);
		
	}
	
	public ESMAPIDataBean(JsonToJavaObjectUtil object) {
		
		String status = object.getStatus();
		setESMId(object.content.pyID);
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


	public List<TSBReferenceList> getTsbReferenceList() {
		return tsbReferenceList;
	}


	public void setTsbReferenceList(List<TSBReferenceList> tsbReferenceList) {
		this.tsbReferenceList = tsbReferenceList;
	}

	
	
}
