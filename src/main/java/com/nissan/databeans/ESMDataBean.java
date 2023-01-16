package com.nissan.databeans;

import java.util.List;
import java.util.Map;

public class ESMDataBean {

	
	
	private List<String> listESM;
	
	public List<String> getListESM() {
		return listESM;
	}

	public void setListESM(List<String> listESM) {
		this.listESM = listESM;
	}

	private List<QuestionsDataBean> questionsDataBean;

	public List<QuestionsDataBean> getQuestionsDataBean() {
		return questionsDataBean;
	}

	public void setQuestionsDataBean(List<QuestionsDataBean> questionsDataBean) {
		this.questionsDataBean = questionsDataBean;
	}
	
	private Map<String, String> repairMap;

	public Map<String, String> getRepairMap() {
		return repairMap;
	}

	public void setRepairMap(Map<String, String> repairMap) {
		this.repairMap = repairMap;
	}
	
	private  String defaultRepair;

	public  String getDefaultRepair() {
		return defaultRepair;
	}

	public  void setDefaultRepair(String defaultRepair) {
		this.defaultRepair = defaultRepair;
	}

	private  boolean repairFlag;

	public  boolean isRepairFlag() {
		return repairFlag;
	}

	public  void setRepairFlag(boolean repairFlag) {
		this.repairFlag = repairFlag;
	}
	
}
