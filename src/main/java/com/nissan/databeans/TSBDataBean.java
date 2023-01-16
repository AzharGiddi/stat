package com.nissan.databeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TSBDataBean {

	
	
	private List<String> listTSB;
	
	public List<String> getListTSB() {
		if(Objects.isNull(listTSB)) {
			setListTSB( new ArrayList<>());
		}
		return listTSB;
	}

	public void setListTSB(List<String> listTSB) {
		this.listTSB = listTSB;
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
