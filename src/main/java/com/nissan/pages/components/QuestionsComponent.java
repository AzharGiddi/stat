package com.nissan.pages.components;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.StringMatcher;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.databeans.QuestionsDataBean;
import com.nissan.databeans.TSBDataBean;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class QuestionsComponent extends AutomaticTransmissionCVTSymptomFormComponent {

	private List<QuestionsDataBean> questionsDataBean;

	public List<QuestionsDataBean> getQuestionsDataBean() {
		return questionsDataBean;
	}

	public void setQuestionsDataBean(List<QuestionsDataBean> questionsDataBean) {
		this.questionsDataBean = questionsDataBean;
	}

	@FindBy(xpath = "//h6[text()='Questions']")
	public ExtWebElement headerQuestions;

	@FindBy(xpath = "//button[text()='Submit']")
	public ExtWebElement btnSubmit;

	@FindBy(xpath = "//div[@aria-hidden='false']//iframe[@title]")
	public ExtWebElement iframe;

	@FindBy(xpath = "//div[contains(text(),'No further questions.')]")
	public ExtWebElement txtNoFurtherQuestions;

	@FindBy(xpath = "//div[contains(text(),'Unable to diagnose')]")
	public ExtWebElement txtNoUnableToDiagnose;

	@FindBy(xpath = "//input[@id='$PpyAttachmentPage$ppxAttachName']")
	public ExtWebElement btnSelectFiles;

	@FindBy(xpath = "//button[normalize-space(text())='Attach']")
	public ExtWebElement btnAttach;

	private String radioBtnYesNo = "//input[contains(@name,'TSBQuestions$l%d') and @value='%s']";

	private String question = "//div[@data-node-id='QuestionDescription' and @index=%d]//div[contains(@class,'item-2')]/span";

	private String questionType = "//div[@data-node-id='DisplayQuestions' and @index=%d]//div[@node_name]";
	
	// public String btnNext = "((//div[@data-node-id='DisplayQuestions' and
	// @index=%d]//div[@style]//button[normalize-space(text()='NEXT')]))[1]";

	//public String btnNext = "//div[ not(@style) and contains(@data-context,'TSBQuestions(%d)')]//button[normalize-space(text()='NEXT')]";
	//public String btnNext = "//button[ not(@style) and contains(@data-click,'TSBQuestions(%d)') and normalize-space(text()='Next')]";
	//public String btnNext = "//button[contains(@name,'TSBQuestionsDone_pyWorkPage.SelectedTSBQuestions(%d)')]";
	public String btnNext = "//button[contains(@name,'TSBQuestionsDone') and contains(@name,'SelectedTSBQuestions(%d)')]";
	
	public String txtboxInputResponse = "//textarea[contains(@name,'TSBQuestions$l%d')]";

	public String btnAttachFiles = "//button[contains(@name,'SelectedTSBQuestions(%d)') and text()='Attach Content']";

	public void verifyQuestionType(int questionIndex) {

		ExtWebComponent.getExtWebElement(String.format(questionType, questionIndex)).verifyAttribute("node_name", StringMatcher.containsIgnoringCase(questionsDataBean.get(questionIndex - 1).getQuestionType()), "Question Type");

	}

	public void verifyQuestionText(int questionIndex) {

		ExtWebComponent.getExtWebElement(String.format(question, questionIndex)).verifyText(StringMatcher.containsIgnoringCase(questionsDataBean.get(questionIndex - 1).getQuestionText()), "Question Text");
	}

	public void validateQuestionData(QuestionsDataBean questions, int questionIndex) {

		this.verifyQuestionType(questionIndex);
		this.verifyQuestionText(questionIndex);
		// validateActions(questions, questionIndex);

	}

	public void respondToQuestion(int... qIndex) {
		int questionIndex = qIndex != null && qIndex.length > 0 ? qIndex[0] : 1;
		this.verifyQuestionType(questionIndex);
		this.verifyQuestionText(questionIndex);
		String action = performAction(getQuestionsDataBean().get(questionIndex - 1).getQuestionType(), questionIndex);
		ExtWebComponent.getExtWebElement(String.format(this.btnNext, questionIndex)).click();
		validateNextSteps(action, questionIndex);

	}

	public void respondToYesNoQuestion(String selectedOption, int... qIndex) {
		int questionIndex = qIndex != null && qIndex.length > 0 ? qIndex[0] : 1;
		// this.verifyQuestionType(questionIndex);
		// this.verifyQuestionText(questionIndex);
		ExtWebComponent.getExtWebElement(String.format(this.radioBtnYesNo, questionIndex, selectedOption)).click();
	//	WaitUtil.sleep(2000);
		ExtWebComponent.getExtWebElement(String.format(this.btnNext, questionIndex)).scrollAndClick();
		// validateNextSteps(action,questionIndex);

	}

	public void respondToAttachmentQuestion(int... qIndex) {
		int questionIndex = qIndex != null && qIndex.length > 0 ? qIndex[0] : 1;
		// this.verifyQuestionType(questionIndex);
		// this.verifyQuestionText(questionIndex);

		ExtWebComponent.getExtWebElement(String.format(this.btnAttachFiles, questionIndex)).scrollAndClick();
		btnSelectFiles.clearAndSendKeys("C:\\Users\\ab00789853\\Downloads\\DiagnosticOutput_D-180531.pdf");
		WaitUtil.sleep(2000);
		btnAttach.click();
		WaitUtil.sleep(2000);
		ExtWebComponent.getExtWebElement(String.format(this.btnNext, questionIndex)).scrollAndClick();
		// validateNextSteps(action,questionIndex);

	}
	
	@FindBy(xpath = "//table[@grid_ref_page='dragDropFileUpload']")
	public ExtWebElement tableAttatchmentTable;
	
	public void respondToAttachmentQuestion(String filePath,int... qIndex) {
		
		int questionIndex = qIndex != null && qIndex.length > 0 ? qIndex[0] : 1;
		ExtWebComponent.getExtWebElement(String.format(this.btnAttachFiles, questionIndex)).scrollAndClick();
		btnSelectFiles.sendKeys(filePath);
		//ExtWebComponent.getExtWebElement("//input[@id='$PpyAttachmentPage$ppxAttachName']").sendKeys(filePath);
		//ExtWebComponent.getExtWebElement("//table[@grid_ref_page='dragDropFileUpload']").waitForPresent();
		tableAttatchmentTable.waitForPresent();
		btnAttach.waitForElementLocatedByToBeClickable(20000);
		btnAttach.click();
		ExtWebElement nextButton = ExtWebComponent.getExtWebElement(String.format(this.btnNext, questionIndex));
		nextButton.waitForElementLocatedByToBeClickable(20000);
		nextButton.scrollAndClick();

	}

	public void respondToInputResponseQuestion(String inputResponse, int... qIndex) {
		
		int questionIndex = qIndex != null && qIndex.length > 0 ? qIndex[0] : 1;
		ExtWebComponent.getExtWebElement(String.format(txtboxInputResponse, questionIndex)).clearAndSendKeys(inputResponse);
		ExtWebComponent.getExtWebElement(String.format(this.btnNext, questionIndex)).scrollAndClick();

	}

	public String performAction(String qType, int qIndex) {
		String action = "";
		switch (qType.toUpperCase()) {

		case "YESNO":
			Random rand = new Random();
			String yesNo = "No";// rand.nextBoolean() ? "Yes" : "No";
			ExtWebComponent.getExtWebElement(String.format(this.radioBtnYesNo, qIndex, yesNo)).click();
			action = yesNo.toUpperCase();
			break;

		case "INPUT RESPONSE":
			ExtWebComponent.getExtWebElement(String.format(txtboxInputResponse, qIndex)).clearAndSendKeys("Input Response");
			action = "INPUTRESPONSE";
			break;

		case "ATTACHMENT":

			break;

		case "AUTO EVALUATE":

			break;

		case "COMPARE AND RESPOND":

			break;

		case "INSTRUCTION":

			break;

		}
		return action;

	}

	private void validateNextSteps(String action, int questionIndex) {
		Map<String, String> optionMap = getOptionMap(action, questionIndex);
		String nextStep = optionMap.get("Next Step");
		switch (nextStep.toUpperCase()) {

		case "SELECT REPAIR":
			String txtRepairNextStep = optionMap.get("Repair Next Step");
			optionMap.put("Next Step", txtRepairNextStep);
			// optionMap.put("repair flag", "true");
		//	TSBDataBean.setRepairFlag(true);
			this.setOptionMap(action, questionIndex, optionMap);
			validateNextSteps(action, questionIndex);
			break;
		case "NEXT QUESTION":

			int nextQuestionIndex = Integer.parseInt(optionMap.get("Question #"));
			respondToQuestion(nextQuestionIndex);
			break;

		case "REFER TO VCAT":

			txtNoFurtherQuestions.verifyText("No further questions. Please click on submit to proceed with the diagnosis", "No Further Questions text");
			break;

		case "END OF QUESTION":
			txtNoFurtherQuestions.verifyText("No further questions. Please click on submit to proceed with the diagnosis", "No Further Questions text");

			break;

		default:
			System.out.println(nextStep + " is not a valid option");

		}

	}

	private Map<String, String> getOptionMap(String action, int index) {

		Map<String, String> actionMap = new LinkedHashMap<>();
		switch (action.toUpperCase()) {

		case "YES":

			actionMap = getQuestionsDataBean().get(index - 1).getOptionYes();
			break;
		case "NO":

			actionMap = getQuestionsDataBean().get(index - 1).getOptionNo();
			break;
		case "INPUTRESPONSE":

			actionMap = getQuestionsDataBean().get(index - 1).getOptionInputResponse();
			break;
		default:
			System.out.println(action + "is not a proper input");
		}

		return actionMap;
	}

	private void setOptionMap(String action, int index, Map<String, String> optionMap) {

		// Map<String, String> actionMap = new LinkedHashMap();
		switch (action.toUpperCase()) {

		case "YES":

			getQuestionsDataBean().get(index - 1).setOptionYes(optionMap);

		case "NO":

			getQuestionsDataBean().get(index - 1).setOptionNo(optionMap);

		case "INPUTRESPONSE":

			getQuestionsDataBean().get(index - 1).setOptionInputResponse(optionMap);

		}

		// return actionMap;
	}

	@Override
	public void waitForComponentToLoad() {

		try {
			waitForFrameToLoad();
			headerQuestions.waitForPresent();
		} catch (Exception e) {

			throw new PageNotFoundException(this.getClass().getSimpleName() + " did not load, Waited 20 seconds for the visibility of the element located by xpath: ");

		}
	}

	public QuestionsComponent() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		waitForComponentToLoad();
	}

	public QuestionsComponent(TSBDataBean tsbData) {
		this.questionsDataBean = tsbData.getQuestionsDataBean();
		ElementFactory.initElements(DriverManager.getDriver(), this);
		waitForComponentToLoad();
	}
}
