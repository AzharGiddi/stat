package com.nissan.pages.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.databeans.QuestionsDataBean;

public class TSBQuestionsComponent extends ExtWebComponent{

	
	@FindBy(xpath = "//tr[contains(@oaargs,'NSA-FW-STATFW-Data-TSBQuestions') or (@class)]//h2 | //table[@pl_prop='.TSBQuestions']//div[text()='No items']")
	public List<ExtWebElement> listQuestions;
	
	public String txtQuestionType= "//tr[contains(@id,'TSBQuestions$l%d')]//descendant::span[text()='Question type']/parent::span/following-sibling::div/span";
	
	public String txtQuestionText= "//tr[contains(@id,'TSBQuestions$l%d')]//descendant::span[text()='Question Text']/parent::span/following-sibling::div/span";
	
	public String txtOptions= "//tr[contains(@id,'TSBQuestions$l%d')]//descendant::span[text()='Option']/following-sibling::div/span[text()='%s']";
	
	public String listActions = "//tr[contains(@id,'TSBQuestions$l%d') and contains(@id,'Actions$l')]/descendant::div/span[text()='Option']";
	
	public String listOptions = "//tr[contains(@id,'TSBQuestions$l%d') and contains(@id,'Actions$l%d')]/descendant::div/span[text()='Option']//parent::div/parent::div/descendant::div[contains(@data-ui-meta,'TSBQuestionAction')]";
	
	@FindBy(xpath = "//tr[contains(@id,'TSBQuestions$l%d') and contains(@id,'Actions$l%d')]/descendant::div/span[text()='Option']//parent::div/parent::div/descendant::div[contains(@data-ui-meta,'TSBQuestionAction')]")
	public List<ExtWebElement> listOptionsElements;	
	
	
public List<QuestionsDataBean> getQuestionsList(){
		
		
		List<String> listTSBQuestions = listQuestions
				.stream().map(excludeEle -> excludeEle.getText())
				.collect(Collectors.toList());
		boolean isQuestionPresent = !(listTSBQuestions.get(0)
				.equals("No items"));
		List<QuestionsDataBean> questions = new ArrayList<>();
		if (isQuestionPresent) {
			int questionIndex = 0;
			
			Question: for (String question : listTSBQuestions) {
				questionIndex++;
				System.out.println("Question "+questionIndex+" data:");
				QuestionsDataBean questionData = new QuestionsDataBean();
				questionData.setQuestionType(
						ExtWebComponent.getExtWebElement(String.format(
								txtQuestionType,
								questionIndex)).getText().trim().replace(":", ""));
				System.out.println("Question type : "+questionData.getQuestionType());
				questionData.setQuestionText(
						ExtWebComponent.getExtWebElement(String.format(
								txtQuestionText,
								questionIndex)).getText().trim());
				System.out.println("Question text : "+questionData.getQuestionText());
				List<ExtWebElement> actions = ExtWebComponent
						.getExtWebElements(String.format(
								listActions,
								questionIndex));
				int actionIndex = 0;
				Actions: for (ExtWebElement action : actions) {
					actionIndex++;
					Map<String, String> optionsMap = new HashMap<>();
					//String optionsXpath = String.format(listOptions,questionIndex,actionIndex);
					String optionsXpath = String.format(
							listOptions,
							questionIndex, actionIndex) + "/span";
					String valuesXpath = String.format(
							listOptions,
							questionIndex, actionIndex)
							+ "/div/span";
					List<String> options = ExtWebComponent.getExtWebElements(optionsXpath).stream()
							.map(excludeEle -> excludeEle.getText().trim()).collect(Collectors.toList());
					List<String> values = ExtWebComponent.getExtWebElements(valuesXpath).stream()
							.map(excludeEle -> excludeEle.getText().trim()).collect(Collectors.toList());
/*																	for (String option : options) {
						String keyXpath = String.format(
								listOptions,
								questionIndex, actionIndex) + "/span";
						//String valueXpath = 

						String key = option;
						
						String key = ExtWebComponent
								.getExtWebElement(keyXpath).getText();
						String value = values.iterator().next();
							System.out.println("key is : "+key+"Value : "+value);
						optionsMap.put(key, value);
					}*/
					
					Iterator<String> optionsIterator = options.iterator();
					Iterator<String> valuesIterator = values.iterator();
					
					while(optionsIterator.hasNext() && valuesIterator.hasNext()) {
						
						optionsMap.put(optionsIterator.next(), valuesIterator.next());
						
					}
					
					/*if(optionsMap.get("Option").equals("Yes")) {
						questionData.setOptionYes(optionsMap);
					}else if(optionsMap.get("Option").equals("No")) {
						questionData.setOptionNo(optionsMap);
					}else {
						questionData.setOptionInputResponse(optionsMap);
					}*/
					
					questionData.setOptionMap(optionsMap);
					optionsMap.forEach((k, v) -> System.out.println("Key is "
							+ k + " value is " + v));
				}
				questions.add(questionData);
			}
		
		
	}
		return questions;
}
	
	
}
