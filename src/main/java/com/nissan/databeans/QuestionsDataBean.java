package com.nissan.databeans;

import java.util.Map;

public class QuestionsDataBean {


	private Map<String, String> optionInputResponse;
	
	private Map<String, String> optionAttachment;
	
	private Map<String, String> optionAutoEvaluate;
	
	private Map<String, String> optionFalse;
	
	private Map<String, String> optionTrue;
	
	private Map<String, String> optionNotGood;
	
	private Map<String, String> optionOk;
	
	private Map<String, String> ifCondition;
	
	private Map<String, String> optionNo;

	private Map<String, String> optionYes;
	
	private String contentToDisplay;
	
	private Map<String, String> option;

	private String questionType;
	
	private String questionText;
	
	private String nextStep;
	
	private String options;

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public Map<String, String> getOptionNo() {
		return optionNo;
	}

	public void setOptionNo(Map<String, String> optionNo) {
		this.optionNo = optionNo;
	}

	public Map<String, String> getOptionYes() {
		return optionYes;
	}

	public void setOptionYes(Map<String, String> optionYes) {
		this.optionYes = optionYes;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public Map<String, String> getOption() {
		return option;
	}

	public void setOption(Map<String, String> option) {
		this.option = option;
	}

	public Map<String, String> getOptionInputResponse() {
		return optionInputResponse;
	}

	public void setOptionInputResponse(Map<String, String> optionInputResponse) {
		this.optionInputResponse = optionInputResponse;
	}

	public Map<String, String> getOptionAttachment() {
		return optionAttachment;
	}

	public void setOptionAttachment(Map<String, String> optionAttachment) {
		this.optionAttachment = optionAttachment;
	}

	public Map<String, String> getOptionAutoEvaluate() {
		return optionAutoEvaluate;
	}

	public void setOptionAutoEvaluate(Map<String, String> optionAutoEvaluate) {
		this.optionAutoEvaluate = optionAutoEvaluate;
	}

	public Map<String, String> getOptionFalse() {
		return optionFalse;
	}

	public void setOptionFalse(Map<String, String> optionFalse) {
		this.optionFalse = optionFalse;
	}

	public Map<String, String> getOptionTrue() {
		return optionTrue;
	}

	public void setOptionTrue(Map<String, String> optionTrue) {
		this.optionTrue = optionTrue;
	}

	public Map<String, String> getOptionNotGood() {
		return optionNotGood;
	}

	public void setOptionNotGood(Map<String, String> optionNotGood) {
		this.optionNotGood = optionNotGood;
	}

	public Map<String, String> getOptionOk() {
		return optionOk;
	}

	public void setOptionOk(Map<String, String> optionOk) {
		this.optionOk = optionOk;
	}

	public Map<String, String> getIfCondition() {
		return ifCondition;
	}

	public void setIfCondition(Map<String, String> ifCondition) {
		this.ifCondition = ifCondition;
	}

	public String getContentToDisplay() {
		return contentToDisplay;
	}

	public void setContentToDisplay(String contentToDisplay) {
		this.contentToDisplay = contentToDisplay;
	}


public void setOptionMap(Map<String, String> optionMap){
		
		//Map<String, String> actionMap = new LinkedHashMap();
	String action = optionMap.get("Option").trim();
		switch(action.toUpperCase()) {
		
		case "YES":
			
			this.setOptionYes(optionMap);
			break;
		case "NO":
			
			this.setOptionNo(optionMap);
			break;
		case "INPUT RESPONSE":
			
			this.setOptionInputResponse(optionMap); 
		break;
		
		}
		
		
		//return actionMap;
	}
		

	

}
